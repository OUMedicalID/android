package edu.oaklandstudent.medicalid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AccidentInformation2 extends AppCompatActivity implements View.OnClickListener{
    LinearLayout layoutList;
    Button buttonAdd;
    Button buttonSubmitList;
    ArrayList<String> accidentsList = new ArrayList<String>();
    int counter = 0;

    /*
        Idea for later?
        To make our own collapsible list:

        As a user:
            1. I will press add
            2. The accident view will appear
            3. There is a collapsible arrow on the left and a delete x on the right
                    MAYBE FOR LATER 3B. I cannot press the collapsible arrow until text is filled in
            4. I can fill in the text area
            5. When i press the collapsible arrow, more text boxes appear
                5A. Who caused the injury?
                5B. Where did it happen?
                5C. When did it happen?
                5D. Stuck by something? "Yes/No dropdown"
                    5C. If yes, will ask what it was
                6. Pain Levels? Maybe a adjustable meter from Kforms or something like that


            1. Make what was in Conditions Page

        Can't save same values in sets or saved preferences
    */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accident_information2);

        layoutList = findViewById(R.id.layout_list);
        buttonAdd = findViewById(R.id.button_add);
        buttonSubmitList = findViewById(R.id.button_submit_list);

        buttonAdd.setOnClickListener(this);
        buttonSubmitList.setOnClickListener(this);

        restore();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.button_add:

                addView();

                break;

            case R.id.button_submit_list:

                if(checkIfValidAndRead()){
                    SharedPreferences prefs = getSharedPreferences("edu.oaklandstudent.medicalid", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    HashMap<String,String> myMap = new HashMap<>();
                    for(int j = 0;j < accidentsList.size();j++) {
                        myMap.put(Integer.toString(j),accidentsList.get(j));
                    }
                    saveMap(myMap);
                    editor.commit();
                }

                break;

        }

    }

    private boolean checkIfValidAndRead() {
        accidentsList.clear();
        boolean result = true;

        for(int i=0;i<layoutList.getChildCount();i++){

            View accidentsView = layoutList.getChildAt(i);

            EditText accidentName = (EditText)accidentsView.findViewById(R.id.row_add_accidentInfo);

            if(!accidentName.getText().toString().equals("")){
                accidentsList.add(String.valueOf(accidentName.getText()));

                EditText accidentLocation = (EditText) accidentsView.findViewById(R.id.row_add_accidentLocation);
                if(accidentLocation.getText().toString().equals("")) {
                    accidentsList.add("None");
                }
                else accidentsList.add(String.valueOf(accidentLocation.getText()));

                EditText accidentAtFault = (EditText)accidentsView.findViewById(R.id.row_add_accidentAtFault);
                if(accidentAtFault.getText().toString().equals("")) {
                    accidentsList.add("None");
                }
                else accidentsList.add(String.valueOf(accidentAtFault.getText()));

                EditText accidentStruckBy = (EditText)accidentsView.findViewById(R.id.row_add_accidentStruckBy);
                if(accidentStruckBy.getText().toString().equals("")) {
                    accidentsList.add("None");
                }
                else accidentsList.add(String.valueOf(accidentStruckBy.getText()));

            }else {
                result = false;
                break;
            }

        }

        if(accidentsList.size()==0){
            SharedPreferences prefs = getSharedPreferences("edu.oaklandstudent.medicalid", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove("MID_Injuries");
            editor.apply();
            result = false;

            //Keep this or Nah?
            Toast.makeText(this, "Add Accident Info First!", Toast.LENGTH_SHORT).show();
        }else if(!result){
            Toast.makeText(this, "Enter All Details Correctly!", Toast.LENGTH_SHORT).show();
        }

        return result;
    }

    private void addView() {

        final View accidentsView = getLayoutInflater().inflate(R.layout.add_row_accidentinformation,null,false);

        TextView title = (TextView)accidentsView.findViewById(R.id.accidentTitle);
        title.setText("Accident Incident");

        TextView informationTitle = (TextView)accidentsView.findViewById(R.id.accidentInformationTitle);
        informationTitle.setText("Accident Information");
        EditText editText1 = (EditText)accidentsView.findViewById(R.id.row_add_accidentInfo);
        editText1.setSelection(editText1.getText().length());

        EditText accidentLocation = (EditText)accidentsView.findViewById(R.id.row_add_accidentLocation);
        accidentLocation.setText("");
        TextView textView2 = (TextView)accidentsView.findViewById(R.id.accidentInfo2Title);
        textView2.setText("Location:");

        EditText accidentAtFault = (EditText)accidentsView.findViewById(R.id.row_add_accidentAtFault);
        accidentAtFault.setText("");
        TextView textView3 = (TextView)accidentsView.findViewById(R.id.accidentInfo3Title);
        textView3.setText("At fault:");

        EditText accidentStruckBy = (EditText)accidentsView.findViewById(R.id.row_add_accidentStruckBy);
        accidentStruckBy.setText("");
        TextView textView4 = (TextView)accidentsView.findViewById(R.id.accidentInfo4Title);
        textView4.setText("Struck by:");

        ImageView imageClose = (ImageView)accidentsView.findViewById(R.id.image_remove);

        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(accidentsView);
            }
        });

        layoutList.addView(accidentsView);

    }

    private void removeView(View view){

        layoutList.removeView(view);

    }
    public void restore(){
        SharedPreferences prefs = getSharedPreferences("edu.oaklandstudent.medicalid", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        Map<String,String> restoreMap = new HashMap<>();
        restoreMap = loadMap();

        if(restoreMap == null) return;
        else{
            int restoreSize = restoreMap.size()/4;
            int index = 0;

            for(int i = 0;i < restoreSize;i++) {
                final View accidentsView = getLayoutInflater().inflate(R.layout.add_row_accidentinformation, null, false);

                TextView title = (TextView) accidentsView.findViewById(R.id.accidentTitle);
                title.setText("Accident Incident");

                TextView informationTitle = (TextView) accidentsView.findViewById(R.id.accidentInformationTitle);
                informationTitle.setText("Accident Information");
                EditText accidentTitle = (EditText) accidentsView.findViewById(R.id.row_add_accidentInfo);
                accidentTitle.setText(restoreMap.get(Integer.toString(index)));
                index++;

                EditText accidentLocation = (EditText) accidentsView.findViewById(R.id.row_add_accidentLocation);
                accidentLocation.setText(restoreMap.get(Integer.toString(index)));
                TextView textView2 = (TextView) accidentsView.findViewById(R.id.accidentInfo2Title);
                textView2.setText("Location:");
                index++;

                EditText accidentAtFault = (EditText) accidentsView.findViewById(R.id.row_add_accidentAtFault);
                accidentAtFault.setText(restoreMap.get(Integer.toString(index)));
                TextView textView3 = (TextView) accidentsView.findViewById(R.id.accidentInfo3Title);
                textView3.setText("At fault:");
                index++;

                EditText accidentStruckBy = (EditText) accidentsView.findViewById(R.id.row_add_accidentStruckBy);
                accidentStruckBy.setText(restoreMap.get(Integer.toString(index)));
                TextView textView4 = (TextView) accidentsView.findViewById(R.id.accidentInfo4Title);
                textView4.setText("Struck by:");
                index++;

                ImageView imageClose = (ImageView) accidentsView.findViewById(R.id.image_remove);

                imageClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeView(accidentsView);
                    }
                });

                layoutList.addView(accidentsView);
            }
        }
    }
    private void saveMap(Map<String,String> inputMap){
        SharedPreferences pSharedPref = getApplicationContext().getSharedPreferences("edu.oaklandstudent.medicalid", Context.MODE_PRIVATE);
        if (pSharedPref != null){
            JSONObject jsonObject = new JSONObject(inputMap);
            String jsonString = jsonObject.toString();
            SharedPreferences.Editor editor = pSharedPref.edit();
            editor.remove("MID_Injuries").commit();
            editor.putString("MID_Injuries", jsonString);
            editor.commit();
        }
    }

    private Map<String,String> loadMap(){
        Map<String,String> outputMap = new HashMap<String,String>();
        SharedPreferences pSharedPref = getApplicationContext().getSharedPreferences("edu.oaklandstudent.medicalid", Context.MODE_PRIVATE);
        try{
            if (pSharedPref != null){
                String jsonString = pSharedPref.getString("MID_Injuries", (new JSONObject()).toString());
                JSONObject jsonObject = new JSONObject(jsonString);
                Iterator<String> keysItr = jsonObject.keys();
                while(keysItr.hasNext()) {
                    String key = keysItr.next();
                    String value = (String) jsonObject.get(key);
                    outputMap.put(key, value);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return outputMap;
    }
}
