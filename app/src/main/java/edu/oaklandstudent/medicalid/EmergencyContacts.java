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
import java.util.Iterator;
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

import org.json.JSONArray;
import org.json.JSONException;

import static edu.oaklandstudent.medicalid.AESEncryption.convertHexToStringValue;
import static edu.oaklandstudent.medicalid.AESEncryption.hexadecimal;


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
                String key = prefs.getString("sha512Key", "");

                if(name1.getValue() != null && phone1.getValue() != null && rel1.getValue() != null) {
                    ArrayList<String> arrayList = new ArrayList<String>();
                    arrayList.add(name1.getValue());
                    arrayList.add(phone1.getValue());
                    arrayList.add(rel1.getValue());
                    JSONArray eContact1JSON = new JSONArray(arrayList);
                    editor.putString("MID_EContact1", AESEncryption.encrypt(hexadecimal(eContact1JSON.toString(), "utf-8"), key));
                }
                if(name2.getValue() != null && phone2.getValue() != null && rel2.getValue() != null) {
                    ArrayList<String> arrayList2 = new ArrayList<String>();
                    arrayList2.add(name2.getValue());
                    arrayList2.add(phone2.getValue());
                    arrayList2.add(rel2.getValue());
                    JSONArray eContact2JSON = new JSONArray(arrayList2);
                    editor.putString("MID_EContact2", AESEncryption.encrypt(hexadecimal(eContact2JSON.toString(), "utf-8"), key));
                }


                editor.apply();
                Log.v("Main", "The button was pressed.");

                ExportData export = new ExportData();
                export.getSavedPrefsData(getApplicationContext());

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
        String key = prefs.getString("sha512Key", "");
        String eContact1JSON = AESEncryption.decrypt(prefs.getString("MID_EContact1", null), key);
        eContact1JSON = convertHexToStringValue(eContact1JSON);
        if (eContact1JSON != null){
            try {
                JSONArray jsonArray = new JSONArray(eContact1JSON);
                if(jsonArray.getString(0) == null || jsonArray.getString(1) == null || jsonArray.getString(2) == null) return;
                name1.setValue(jsonArray.getString(0));
                phone1.setValue(jsonArray.getString(1));
                rel1.setValue(jsonArray.getString(2));
            } catch (JSONException e) {
                e.printStackTrace();
            }
    }


        String eContact2JSON = AESEncryption.decrypt(prefs.getString("MID_EContact2", null), key);
        eContact2JSON = convertHexToStringValue(eContact2JSON);
        if (eContact2JSON != null){
            try {
                JSONArray jsonArray2 = new JSONArray(eContact2JSON);
                if(jsonArray2.getString(0) == null || jsonArray2.getString(1) == null || jsonArray2.getString(2) == null) return;
                name2.setValue(jsonArray2.getString(0));
                phone2.setValue(jsonArray2.getString(1));
                rel2.setValue(jsonArray2.getString(2));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }











    }


}