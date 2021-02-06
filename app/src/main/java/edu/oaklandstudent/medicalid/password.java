package edu.oaklandstudent.medicalid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.oaklandstudent.medicalid.R;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

import com.google.android.material.snackbar.Snackbar;
import com.thejuki.kformmaster.item.ListItem;
import com.thejuki.kformmaster.listener.OnFormElementValueChangedListener;
import com.thejuki.kformmaster.model.BaseFormElement;
import com.thejuki.kformmaster.model.FormButtonElement;
import com.thejuki.kformmaster.model.FormHeader;
import com.thejuki.kformmaster.model.FormLabelElement;
import com.thejuki.kformmaster.model.FormPasswordEditTextElement;
import com.thejuki.kformmaster.model.FormPhoneEditTextElement;
import com.thejuki.kformmaster.model.FormPickerDateElement;
import com.thejuki.kformmaster.model.FormPickerDropDownElement;
import com.thejuki.kformmaster.model.FormSegmentedElement;
import com.thejuki.kformmaster.model.FormSingleLineEditTextElement;
import com.thejuki.kformmaster.helper.FormBuildHelper;


public class password extends AppCompatActivity{

    final FormPasswordEditTextElement password = new FormPasswordEditTextElement(1);
    final FormPasswordEditTextElement confPassword = new FormPasswordEditTextElement(1);
    public boolean isSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.informationcontainer);



        // initialize variables
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewic);

        FormBuildHelper formBuilder = new FormBuildHelper(new OnFormElementValueChangedListener() {
            @Override
            public void onValueChanged(BaseFormElement baseFormElement) {
                Log.i("FirstForm", "Something in the form was changed.");
            }
        }, mRecyclerView);

        formBuilder.attachRecyclerView(mRecyclerView);

        // Declare our array list of elements
        List<BaseFormElement<?>> elements = new ArrayList<>();


        // First contact
        final FormHeader header1 = new FormHeader("Emergency Contact 1");


        password.setValue(password.getValueAsString());
        password.setTitle("Password");


        confPassword.setValue(confPassword.getValueAsString());
        confPassword.setTitle("Confirm Password");


        FormButtonElement save = new FormButtonElement(4);
        save.setValue("Save Password");
            save.getValueObservers().add(new Function2<String, BaseFormElement<String>, Unit>() {
                @Override
                public Unit invoke(String newValue, BaseFormElement<String> element) {
                    if(password.getValueAsString().equals(confPassword.getValueAsString())) {
                        isSet = true;
                        SharedPreferences prefs = getSharedPreferences("edu.oaklandstudent.medicalid", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();

                        editor.putString("password", AESEncryption.encrypt(password.getValue()));
                        editor.putString("confPassword", AESEncryption.encrypt(confPassword.getValue()));

                        editor.apply();
                        Log.v("saved","Password saved");
                        Snackbar.make(findViewById(android.R.id.content), "Password Set!", Snackbar.LENGTH_SHORT).show();
                    }
                    else{
                        isSet = false;
                        Log.v("error","Passwords don't match");
                        Snackbar.make(findViewById(android.R.id.content), "Passwords do not match!", Snackbar.LENGTH_SHORT).show();

                    }
                    Log.v("Main", "The button was pressed.");
                    return Unit.INSTANCE;
                }
            });


        elements.add(header1);


        elements.add(password);

        elements.add(confPassword);
        elements.add(save);

        Log.v("Main","password length: " + password.toString().length());
        formBuilder.addFormElements(elements);
        // formBuilder.

        SharedPreferences prefs = getSharedPreferences("edu.oaklandstudent.medicalid", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        password.setValue(AESEncryption.decrypt(prefs.getString("password", null)));
        confPassword.setValue(AESEncryption.decrypt(prefs.getString("confPassword", null)));



    }

    public FormPasswordEditTextElement getPassword(){
        return password;
    }
    public boolean getIsSet(){
        return isSet;
    }


}