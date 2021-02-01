package edu.oaklandstudent.medicalid;

import android.content.Intent;
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


import edu.oaklandstudent.medicalid.R;
import com.thejuki.kformmaster.listener.OnFormElementValueChangedListener;
import com.thejuki.kformmaster.model.BaseFormElement;
import com.thejuki.kformmaster.model.FormButtonElement;
import com.thejuki.kformmaster.model.FormHeader;
import com.thejuki.kformmaster.helper.FormBuildHelper;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;


public class Settings extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View RootView = inflater.inflate(R.layout.settings, container, false);
        RecyclerView mRecyclerView = (RecyclerView) RootView.findViewById(R.id.settingView);

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
        FormHeader header = new FormHeader("Settings");

        FormButtonElement personal = new FormButtonElement(4);
        personal.setValue("Set Password");
        personal.getValueObservers().add(new Function2<String, BaseFormElement<String>, Unit>() {
            @Override
            public Unit invoke(String newValue, BaseFormElement<String> element) {

                Intent myIntent = new Intent(Settings.this.getContext(), password.class);
                startActivity(myIntent);

                return Unit.INSTANCE;
            }
        });



        elements.add(header);
        elements.add(personal);


        formBuilder.addFormElements(elements);



        return RootView;
    }
}