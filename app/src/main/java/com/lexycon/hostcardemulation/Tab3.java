package com.lexycon.hostcardemulation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import me.riddhimanadib.formmaster.FormBuilder;
import me.riddhimanadib.formmaster.listener.OnFormElementValueChangedListener;
import me.riddhimanadib.formmaster.model.BaseFormElement;
import me.riddhimanadib.formmaster.model.FormElementPickerDate;
import me.riddhimanadib.formmaster.model.FormElementTextSingleLine;
import me.riddhimanadib.formmaster.model.FormHeader;


public class Tab3 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View RootView = inflater.inflate(R.layout.fragment_tab3, container, false);

        RecyclerView mRecyclerView = (RecyclerView) RootView.findViewById(R.id.recyclerViewX);

        FormBuilder mFormBuilder = new FormBuilder(getContext(), mRecyclerView, new OnFormElementValueChangedListener() {
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



        return RootView;
    }
}