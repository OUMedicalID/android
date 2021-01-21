package com.lexycon.hostcardemulation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import me.riddhimanadib.formmaster.FormBuilder;
import me.riddhimanadib.formmaster.listener.OnFormElementValueChangedListener;
import me.riddhimanadib.formmaster.model.BaseFormElement;
import me.riddhimanadib.formmaster.model.FormElementPickerDate;
import me.riddhimanadib.formmaster.model.FormElementTextEmail;
import me.riddhimanadib.formmaster.model.FormElementTextSingleLine;
import me.riddhimanadib.formmaster.model.FormHeader;


public class Questions extends AppCompatActivity{




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstform);


        // initialize variables
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        FormBuilder mFormBuilder = new FormBuilder(this, mRecyclerView, new OnFormElementValueChangedListener() {
            @Override
            public void onValueChanged(BaseFormElement baseFormElement) {
                Log.i("FirstForm", "Something in the form was changed.");
            }
        }
        );

         // Declare our array list of elements
        List<BaseFormElement> formItems = new ArrayList<>();

        // declare first section (header +  various elements)
        FormHeader header = FormHeader.createInstance("Personal Info");

        FormElementTextSingleLine name = FormElementTextSingleLine.createInstance().setTitle("Name").setHint("Enter Full Name");
        FormElementPickerDate dob = FormElementPickerDate.createInstance().setTitle("Date of Birth").setDateFormat("MMM dd, yyyy");
        FormElementTextSingleLine street = FormElementTextSingleLine.createInstance().setTitle("Street").setHint("Enter Street Address");

        formItems.add(header);
        formItems.add(name);
        formItems.add(dob);
        formItems.add(street);



        mFormBuilder.addFormElements(formItems);
    }


}