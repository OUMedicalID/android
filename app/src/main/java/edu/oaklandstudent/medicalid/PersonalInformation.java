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

import com.thejuki.kformmaster.item.ListItem;
import com.thejuki.kformmaster.listener.OnFormElementValueChangedListener;
import com.thejuki.kformmaster.model.BaseFormElement;
import com.thejuki.kformmaster.model.FormButtonElement;
import com.thejuki.kformmaster.model.FormHeader;
import com.thejuki.kformmaster.model.FormPickerDateElement;
import com.thejuki.kformmaster.model.FormPickerDropDownElement;
import com.thejuki.kformmaster.model.FormSegmentedElement;
import com.thejuki.kformmaster.model.FormSingleLineEditTextElement;
import com.thejuki.kformmaster.helper.FormBuildHelper;


public class PersonalInformation extends AppCompatActivity{


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


        // declare first section (header +  various elements)
        FormHeader header = new FormHeader("Personal Info");


        final FormSingleLineEditTextElement name = new FormSingleLineEditTextElement(1);
        name.setHint("Full Name");
        name.setTitle("Name");




        final FormPickerDateElement dob = new FormPickerDateElement(2);
        //dob.setDateValue(new Date());
        // dob.setDateFormat(new SimpleDateFormat("MM/dd/yyyy", Locale.US));
        dob.setTitle("Date of Birth");
        dob.setHint("Click to Select");



        final FormSingleLineEditTextElement street = new FormSingleLineEditTextElement(3);
        street.setHint("Address Line 1");
        street.setTitle("Address Line 1");



        final FormPickerDropDownElement<ListItem> gender = new FormPickerDropDownElement<>(1);
        gender.setDialogTitle("Select Gender");
        gender.setOptions(Arrays.asList(new ListItem(1L, "Male"), new ListItem(2L, "Female"), new ListItem(3L, "Other")));
        gender.setTitle("Select Gender");
        gender.setHint("Click to Select");


        FormButtonElement save = new FormButtonElement(4);
        save.setValue("Save Personal Information");
        save.getValueObservers().add(new Function2<String, BaseFormElement<String>, Unit>() {
            @Override
            public Unit invoke(String newValue, BaseFormElement<String> element) {


                // Save all of the information.

                SharedPreferences prefs = getSharedPreferences("edu.oaklandstudent.medicalid", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();

                editor.putString("name", name.getValue());
                editor.putString("dob", dob.getValueAsString());
                editor.putString("street1", street.getValue());
                editor.putString("gender", gender.getValueAsString());

                //Log.wtf("OUS45", "Gender ID is "+gender.getId());
                //Log.wtf("OUS45", "Gender Tag is "+gender.getTag());
                //Log.wtf("OUS45", "Gender val is "+gender.getValue());
                //Log.wtf("OUS45", "Gender valas is "+gender.getValueAsString());

                editor.apply();
                Log.v("Main", "The button was pressed.");
                return Unit.INSTANCE;
            }
        });



        elements.add(header);
        elements.add(name);
        elements.add(dob);
        elements.add(street);
        elements.add(gender);




        elements.add(save);
        formBuilder.addFormElements(elements);
        // formBuilder.






         // Restore all information here.
        SharedPreferences prefs = getSharedPreferences("edu.oaklandstudent.medicalid", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        name.setValue(prefs.getString("name", null));
        street.setValue(prefs.getString("street1", null));

       if(prefs.getString("gender", "").equals("Male")) gender.setValue(new ListItem(1L, "Male"));
       if(prefs.getString("gender", "").equals("Female")) gender.setValue(new ListItem(2L, "Female"));
       if(prefs.getString("gender", "").equals("Other")) gender.setValue(new ListItem(3L, "Other"));

        // Code to restore dob.
        String dobAsString = prefs.getString("dob", null);
        if(dobAsString != null) {
            DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
            try {
                Date date = format.parse(dobAsString);
                dob.setDateValue(date);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            dob.setDateFormat(new SimpleDateFormat("MM/dd/yyyy", Locale.US));
        }


    }


}