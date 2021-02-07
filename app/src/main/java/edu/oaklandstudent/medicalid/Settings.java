package edu.oaklandstudent.medicalid;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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
import com.thejuki.kformmaster.model.FormSingleLineEditTextElement;
import com.thejuki.kformmaster.model.FormSwitchElement;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;


public class Settings extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View RootView = inflater.inflate(R.layout.settings, container, false);
        RecyclerView mRecyclerView = (RecyclerView) RootView.findViewById(R.id.settingView);

        FormBuildHelper formBuilder = new FormBuildHelper();

        formBuilder.attachRecyclerView(mRecyclerView);
        List<BaseFormElement<?>> elements = new ArrayList<>();
        FormHeader header = new FormHeader("Settings");



        FormSwitchElement<String> switchElement = new FormSwitchElement<>(1);
        switchElement.setValue("false");
        switchElement.setOnValue("true");
        switchElement.setOffValue("false");
        switchElement.setTitle("Enable Bometric Authentication");

        switchElement.getValueObservers().add(new Function2<String, BaseFormElement<String>, Unit>() {
            @Override
            public Unit invoke(String newValue, BaseFormElement<String> element) {


                return Unit.INSTANCE;
            }
        });





        FormButtonElement personal = new FormButtonElement(2);
        personal.setValue("Password Authentication");
        personal.getValueObservers().add(new Function2<String, BaseFormElement<String>, Unit>() {
            @Override
            public Unit invoke(String newValue, BaseFormElement<String> element) {

                Intent myIntent = new Intent(Settings.this.getContext(), password.class);
                startActivity(myIntent);

                return Unit.INSTANCE;
            }
        });


        FormButtonElement clear = new FormButtonElement(3);
        clear.setValue("Clear Application Data");
        clear.getValueObservers().add(new Function2<String, BaseFormElement<String>, Unit>() {
            @Override
            public Unit invoke(String newValue, BaseFormElement<String> element) {



                return Unit.INSTANCE;
            }
        });


        // Application Information...

        FormHeader header2 = new FormHeader("Application Information");

        FormButtonElement about = new FormButtonElement(20);
        about.setValue("About the App");
        about.getValueObservers().add(new Function2<String, BaseFormElement<String>, Unit>() {
            @Override
            public Unit invoke(String newValue, BaseFormElement<String> element) {


                return Unit.INSTANCE;
            }
        });

        FormButtonElement credits = new FormButtonElement(10);
        credits.setValue("Credits");
        credits.getValueObservers().add(new Function2<String, BaseFormElement<String>, Unit>() {
            @Override
            public Unit invoke(String newValue, BaseFormElement<String> element) {


                return Unit.INSTANCE;
            }
        });


        String version = "";
        try {
            PackageInfo pInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
             version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            version = "Unknown";
        }

        FormSingleLineEditTextElement appVersion = new FormSingleLineEditTextElement(6);
        appVersion.setTitle("App Version");
        appVersion.setValue(version);
        appVersion.setFocusable(false);



        elements.add(header);
        elements.add(switchElement);
        elements.add(personal);
        elements.add(clear);
        elements.add(header2);
        elements.add(about);
        elements.add(credits);
        elements.add(appVersion);

        formBuilder.addFormElements(elements);



        return RootView;
    }
}