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
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class MedicalConditions extends AppCompatActivity implements View.OnClickListener{

    LinearLayout layoutList;
    Button buttonAdd;
    Button buttonSubmitList;
    ArrayList<String> conditionsList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medical_conditions);

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
                    //Set<String> mConditions = new HashSet<String>();
                    for(int j = 0;j < conditionsList.size();j++) {
                        myMap.put(Integer.toString(j),conditionsList.get(j));
                        //mConditions.add(conditionsList.get(j));
                    }
                    saveMap(myMap);
                    //editor.putStringSet("mConditions",mConditions);
                    editor.commit();
                    Snackbar.make(findViewById(android.R.id.content), "Information Saved!", Snackbar.LENGTH_SHORT).show();
                }

                break;

        }


    }

    private boolean checkIfValidAndRead() {
        conditionsList.clear();
        boolean result = true;

        for(int i=0;i<layoutList.getChildCount();i++){

            View conditionsView = layoutList.getChildAt(i);

            EditText editTextName = (EditText)conditionsView.findViewById(R.id.row_add);

            if(!editTextName.getText().toString().equals("")){
                conditionsList.add(editTextName.getText().toString());
            }else {
                result = false;
                break;
            }

        }

        if(conditionsList.size()==0){
            SharedPreferences prefs = getSharedPreferences("edu.oaklandstudent.medicalid", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove("conditionInformation");
            editor.apply();
            result = false;

            //Keep this or Nah?
            Toast.makeText(this, "Add Condition First!", Toast.LENGTH_SHORT).show();
        }else if(!result){
            Toast.makeText(this, "Enter All Details Correctly!", Toast.LENGTH_SHORT).show();
        }

        return result;
    }

    private void addView() {

        final View conditionsView = getLayoutInflater().inflate(R.layout.row_add,null,false);

        EditText editText = (EditText)conditionsView.findViewById(R.id.row_add);
        ImageView imageClose = (ImageView)conditionsView.findViewById(R.id.image_remove);

        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(conditionsView);
            }
        });

        layoutList.addView(conditionsView);

    }

    private void removeView(View view){

        layoutList.removeView(view);

    }
    public void restore(){
        SharedPreferences prefs = getSharedPreferences("edu.oaklandstudent.medicalid", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        final Set<String> mConditions = prefs.getStringSet("mConditions", null);

        Map<String,String> restoreMap = new HashMap<>();
        restoreMap = loadMap();

        if(restoreMap == null) return;
        else{
            //ArrayList<String> restoreConditions = new ArrayList<String>(mConditions);
            //Collections.sort(restoreConditions, String.CASE_INSENSITIVE_ORDER);
            for(int i = 0;i < restoreMap.size();i++) {
                final View conditionsView = getLayoutInflater().inflate(R.layout.row_add, null, false);

                EditText editText = (EditText) conditionsView.findViewById(R.id.row_add);
                editText.setText(restoreMap.get(Integer.toString(i)));
                ImageView imageClose = (ImageView) conditionsView.findViewById(R.id.image_remove);

                imageClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeView(conditionsView);
                    }
                });

                layoutList.addView(conditionsView);
            }
        }
    }
    private void saveMap(Map<String,String> inputMap){
        SharedPreferences pSharedPref = getApplicationContext().getSharedPreferences("edu.oaklandstudent.medicalid", Context.MODE_PRIVATE);
        if (pSharedPref != null){
            JSONObject jsonObject = new JSONObject(inputMap);
            String jsonString = jsonObject.toString();
            SharedPreferences.Editor editor = pSharedPref.edit();
            editor.remove("conditionInformation").commit();
            editor.putString("conditionInformation", jsonString);
            editor.commit();
        }
    }

    private Map<String,String> loadMap(){
        Map<String,String> outputMap = new HashMap<String,String>();
        SharedPreferences pSharedPref = getApplicationContext().getSharedPreferences("edu.oaklandstudent.medicalid", Context.MODE_PRIVATE);
        try{
            if (pSharedPref != null){
                String jsonString = pSharedPref.getString("conditionInformation", (new JSONObject()).toString());
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