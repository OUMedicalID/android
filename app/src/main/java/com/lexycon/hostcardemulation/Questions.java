package com.lexycon.hostcardemulation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import com.thejuki.kformmaster.listener.OnFormElementValueChangedListener;
import com.thejuki.kformmaster.model.BaseFormElement;
import com.thejuki.kformmaster.model.FormButtonElement;
import com.thejuki.kformmaster.model.FormHeader;
import com.thejuki.kformmaster.model.FormPickerDateElement;
import com.thejuki.kformmaster.model.FormSingleLineEditTextElement;
import com.thejuki.kformmaster.helper.FormBuildHelper;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class Questions extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View RootView = inflater.inflate(R.layout.questions, container, false);
        RecyclerView mRecyclerView = (RecyclerView) RootView.findViewById(R.id.recyclerViewX);

        FormBuildHelper formBuilder = new FormBuildHelper(new OnFormElementValueChangedListener() {
            @Override
            public void onValueChanged(BaseFormElement baseFormElement) {
                Log.i("FirstForm", "Something in the form was changed.");
            }
        }, mRecyclerView);
        formBuilder.attachRecyclerView(mRecyclerView);
        // Declare our array list of elements
        List<BaseFormElement<?>> elements = new ArrayList<>();
        // declare first section (header +  various elements)
        FormHeader header = new FormHeader("Choose Information To Edit");

        FormButtonElement personal = new FormButtonElement(4);
        personal.setValue("Personal Information");
        personal.getValueObservers().add(new Function2<String, BaseFormElement<String>, Unit>() {
            @Override
            public Unit invoke(String newValue, BaseFormElement<String> element) {

                Intent myIntent = new Intent(Questions.this.getContext(), PersonalInformation.class);
                startActivity(myIntent);

                return Unit.INSTANCE;
            }
        });

        FormButtonElement emergencyContacts = new FormButtonElement(5);
        emergencyContacts.setValue("Emergency Contact Information");
        emergencyContacts.getValueObservers().add(new Function2<String, BaseFormElement<String>, Unit>() {
            @Override
            public Unit invoke(String newValue, BaseFormElement<String> element) {
                Log.v("Main", "The button was pressed.");
                return Unit.INSTANCE;
            }
        });


        FormButtonElement medicalConditions = new FormButtonElement(6);
        medicalConditions.setValue("Medical Conditions");
        medicalConditions.getValueObservers().add(new Function2<String, BaseFormElement<String>, Unit>() {
            @Override
            public Unit invoke(String newValue, BaseFormElement<String> element) {
                Log.v("Main", "The button was pressed.");
                return Unit.INSTANCE;
            }
        });

        elements.add(header);
        elements.add(personal);
        elements.add(emergencyContacts);
        elements.add(medicalConditions);

        formBuilder.addFormElements(elements);



        return RootView;
    }
}