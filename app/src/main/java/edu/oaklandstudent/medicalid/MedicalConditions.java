package edu.oaklandstudent.medicalid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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
                    Set<String> mConditions = new HashSet<String>();
                    for(int j = 0;j < conditionsList.size();j++) {
                        mConditions.add(conditionsList.get(j));
                    }
                    editor.putStringSet("mConditions",mConditions);
                    editor.commit();
                }

                break;

        }


    }

    private boolean checkIfValidAndRead() {
        conditionsList.clear();
        boolean result = true;

        for(int i=0;i<layoutList.getChildCount();i++){

            View conditionsView = layoutList.getChildAt(i);

            EditText editTextName = (EditText)conditionsView.findViewById(R.id.edit_conditions_name);

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
            editor.remove("mConditions");
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

        final View conditionsView = getLayoutInflater().inflate(R.layout.row_add_condition,null,false);

        EditText editText = (EditText)conditionsView.findViewById(R.id.edit_conditions_name);
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

        if(mConditions == null) return;
        else{
            ArrayList<String> restoreConditions = new ArrayList<String>(mConditions);
            Collections.sort(restoreConditions, String.CASE_INSENSITIVE_ORDER);
            for(int i = 0;i < restoreConditions.size();i++) {
                final View conditionsView = getLayoutInflater().inflate(R.layout.row_add_condition, null, false);

                EditText editText = (EditText) conditionsView.findViewById(R.id.edit_conditions_name);
                editText.setText(restoreConditions.get(i));
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
}