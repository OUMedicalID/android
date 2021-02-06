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
import com.thejuki.kformmaster.model.FormPhoneEditTextElement;
import com.thejuki.kformmaster.model.FormPickerDateElement;
import com.thejuki.kformmaster.model.FormPickerDropDownElement;
import com.thejuki.kformmaster.model.FormSegmentedElement;
import com.thejuki.kformmaster.model.FormSingleLineEditTextElement;
import com.thejuki.kformmaster.helper.FormBuildHelper;


public class EmergencyContacts extends AppCompatActivity{


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

        final FormSingleLineEditTextElement name1 = new FormSingleLineEditTextElement(1);
        name1.setHint("Full Name");
        name1.setTitle("Name");

        final FormPhoneEditTextElement phone1 = new FormPhoneEditTextElement(2);
        phone1.setHint("Contact Phone");
        phone1.setTitle("Phone");

        final FormSingleLineEditTextElement rel1 = new FormSingleLineEditTextElement(3);
        rel1.setHint("Relationship to Patient");
        rel1.setTitle("Relationship");


        // First contact
        final FormHeader header2 = new FormHeader("Emergency Contact 2");

        final FormSingleLineEditTextElement name2 = new FormSingleLineEditTextElement(4);
        name2.setHint("Full Name");
        name2.setTitle("Name");

        final FormPhoneEditTextElement phone2 = new FormPhoneEditTextElement(5);
        phone2.setHint("Contact Phone");
        phone2.setTitle("Phone");

        final FormSingleLineEditTextElement rel2 = new FormSingleLineEditTextElement(6);
        rel2.setHint("Relationship to Patient");
        rel2.setTitle("Relationship");





        elements.add(header1);
        elements.add(name1);
        elements.add(phone1);
        elements.add(rel1);
        elements.add(header2);
        elements.add(name2);
        elements.add(phone2);
        elements.add(rel2);



        FormHeader header3 = new FormHeader("Save Information");
        FormButtonElement save = new FormButtonElement(4);
        save.setValue("Save Personal Information");
        save.getValueObservers().add(new Function2<String, BaseFormElement<String>, Unit>() {
            @Override
            public Unit invoke(String newValue, BaseFormElement<String> element) {

                SharedPreferences prefs = getSharedPreferences("edu.oaklandstudent.medicalid", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();

                editor.putString("header1", AESEncryption.encrypt(header1.getValue()));
                editor.putString("name1", AESEncryption.encrypt(name1.getValue()));
                editor.putString("phone1", AESEncryption.encrypt(phone1.getValue()));
                editor.putString("rel1", AESEncryption.encrypt(rel1.getValue()));
                editor.putString("header2", AESEncryption.encrypt(header2.getValue()));
                editor.putString("name2", AESEncryption.encrypt(name2.getValue()));
                editor.putString("phone2", AESEncryption.encrypt(phone2.getValue()));
                editor.putString("rel2", AESEncryption.encrypt(rel2.getValue()));

                editor.apply();
                Log.v("Main", "The button was pressed.");

                Snackbar.make(findViewById(android.R.id.content), "Information Saved!", Snackbar.LENGTH_SHORT).show();
                return Unit.INSTANCE;
            }
        });
        elements.add(header3);
        elements.add(save);
        formBuilder.addFormElements(elements);
        // formBuilder.

        SharedPreferences prefs = getSharedPreferences("edu.oaklandstudent.medicalid", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        header1.setValue(AESEncryption.decrypt(prefs.getString("header1", null)));
        name1.setValue(AESEncryption.decrypt(prefs.getString("name1", null)));
        phone1.setValue(AESEncryption.decrypt(prefs.getString("phone1", null)));
        rel1.setValue(AESEncryption.decrypt(prefs.getString("rel1", null)));
        header2.setValue(AESEncryption.decrypt(prefs.getString("header2", null)));
        name2.setValue(AESEncryption.decrypt(prefs.getString("name2", null)));
        phone2.setValue(AESEncryption.decrypt(prefs.getString("phone2", null)));
        rel2.setValue(AESEncryption.decrypt(prefs.getString("rel2", null)));










    }


}