package edu.oaklandstudent.medicalid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;

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


import kotlin.Unit;
import kotlin.jvm.functions.Function2;

import com.google.android.material.snackbar.Snackbar;
import com.thejuki.kformmaster.item.ListItem;
import com.thejuki.kformmaster.listener.OnFormElementValueChangedListener;
import com.thejuki.kformmaster.model.BaseFormElement;
import com.thejuki.kformmaster.model.FormButtonElement;
import com.thejuki.kformmaster.model.FormHeader;
import com.thejuki.kformmaster.model.FormPickerDateElement;
import com.thejuki.kformmaster.model.FormPickerDropDownElement;
import com.thejuki.kformmaster.model.FormSingleLineEditTextElement;
import com.thejuki.kformmaster.helper.FormBuildHelper;


public class PersonalInformation extends AppCompatActivity{
    public List<BaseFormElement<?>> elements = new ArrayList<>();
    public final FormHeader header1 = new FormHeader("Accident/Injury Info");
    public final FormHeader header2 = new FormHeader("Other Personal Information");
    public int newID = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.informationcontainer);


        // initialize variables
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewic);

        final FormBuildHelper formBuilder = new FormBuildHelper(new OnFormElementValueChangedListener() {
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
        dob.setDateFormat(new SimpleDateFormat("MM/dd/yyyy", Locale.US));
        dob.setTitle("Date of Birth");
        dob.setHint("Click to Select");


        final FormSingleLineEditTextElement street = new FormSingleLineEditTextElement(3);
        street.setHint("Address Line 1");
        street.setTitle("Address Line 1");

        final FormSingleLineEditTextElement street2 = new FormSingleLineEditTextElement(22);
        street2.setHint("Address Line 2");
        street2.setTitle("Address Line 2");


        final FormPickerDropDownElement<ListItem> gender = new FormPickerDropDownElement<>(1);
        gender.setDialogTitle("Select Gender");
        gender.setOptions(Arrays.asList(new ListItem(1L, "Male"), new ListItem(2L, "Female"), new ListItem(3L, "Other")));
        gender.setTitle("Gender");
        gender.setHint("Click to Select");

        final FormSingleLineEditTextElement city = new FormSingleLineEditTextElement(6);
        city.setHint("City");
        city.setTitle("City");

        final FormPickerDropDownElement<ListItem> state = new FormPickerDropDownElement<>(7);
        state.setDialogTitle("Select State");
        state.setOptions(Arrays.asList(new ListItem(1L, "Alabama"), new ListItem(2L, "Alaska"), new ListItem(3L, "Arizona"),
                new ListItem(4L, "Arkansas"), new ListItem(5L, "California"), new ListItem(6L, "Colorado"), new ListItem(7L, "Connecticut"),
                new ListItem(8L, "Delaware"), new ListItem(9L, "Florida"), new ListItem(10L, "Georgia"), new ListItem(11L, "Hawaii"),
                new ListItem(12L, "Idaho"), new ListItem(13L, "Illinois"), new ListItem(14L, "Indiana"), new ListItem(15L, "Iowa"),
                new ListItem(16L, "Kansas"), new ListItem(17L, "Kentucky"), new ListItem(18L, "Louisiana"), new ListItem(19L, "Maine"),
                new ListItem(20L, "Maryland"), new ListItem(21L, "Massachusetts"), new ListItem(22L, "Michigan"), new ListItem(23L, "Minnesota"),
                new ListItem(24L, "Mississippi"), new ListItem(25L, "Missouri"), new ListItem(26L, "Montana"), new ListItem(27L, "Nebraska"),
                new ListItem(28L, "Nevada"), new ListItem(29L, "New Hampshire"), new ListItem(30L, "New Jersey"), new ListItem(31L, "New Mexico"),
                new ListItem(32L, "New York"), new ListItem(33L, "North Carolina"), new ListItem(34L, "North Dakota"), new ListItem(35L, "Ohio"),
                new ListItem(36L, "Oklahoma"), new ListItem(37L, "Oregon"), new ListItem(38L, "Pennsylvania"), new ListItem(39L, "Rhode Island"),
                new ListItem(40L, "South Carolina"), new ListItem(41L, "South Dakota"), new ListItem(42L, "Tennessee"), new ListItem(43L, "Texas"),
                new ListItem(44L, "Utah"), new ListItem(45L, "Vermont"), new ListItem(46L, "Virginia"), new ListItem(47L, "Washington"),
                new ListItem(48L, "West Virginia"), new ListItem(49L, "Wisconsin"), new ListItem(50L, "Wyoming"), new ListItem(51L, "Other")
        ));
        state.setTitle("State");
        state.setHint("Click to Select");

        final FormSingleLineEditTextElement zipCode = new FormSingleLineEditTextElement(8);
        zipCode.setHint("ZIP Code");
        zipCode.setTitle("ZIP Code");

        final FormSingleLineEditTextElement homePhone = new FormSingleLineEditTextElement(25);
        homePhone.setHint("Home Phone");
        homePhone.setTitle("Home Phone");

        final FormSingleLineEditTextElement workPhone = new FormSingleLineEditTextElement(26);
        workPhone.setHint("Work Phone");
        workPhone.setTitle("Work Phone");



        final FormSingleLineEditTextElement weight = new FormSingleLineEditTextElement(9);
        weight.setHint("Weight");
        weight.setTitle("Weight");

        final FormSingleLineEditTextElement height = new FormSingleLineEditTextElement(10);
        height.setHint("Height");
        height.setTitle("Height");

        final FormPickerDropDownElement<ListItem> bloodType = new FormPickerDropDownElement<>(11);
        bloodType.setDialogTitle("Select Blood Type");
        bloodType.setOptions(Arrays.asList(
                new ListItem(1L, "A+"),
                new ListItem(2L, "A-"),
                new ListItem(3L, "B+"),
                new ListItem(4L, "B-"),
                new ListItem(5L, "O+"),
                new ListItem(6L, "O-"),
                new ListItem(7L, "AB+"),
                new ListItem(8L, "AB-")
        ));


        bloodType.setTitle("Blood Type");
        bloodType.setHint("Click to Select");

        final FormPickerDropDownElement<ListItem> ethnicity = new FormPickerDropDownElement<>(12);
        ethnicity.setDialogTitle("Select Ethnicity");
        ethnicity.setOptions(Arrays.asList(
                new ListItem(1L, "American Indian or Alaskan Native"),
                new ListItem(2L, "Asian"),
                new ListItem(3L, "Black or African American"),
                new ListItem(4L, "Hispanic or Latino"),
                new ListItem(5L, "Native Hawaiian or other Pacific Islander"),
                new ListItem(6L, "White"),
                new ListItem(7L, "Two or more races"),
                new ListItem(8L, "Other")
                ));
        ethnicity.setTitle("Ethnicity");
        ethnicity.setHint("Click to Select");

        final FormPickerDropDownElement<ListItem> maritalStatus = new FormPickerDropDownElement<>(13);
        maritalStatus.setDialogTitle("Marital Status");
        maritalStatus.setOptions(Arrays.asList(new ListItem(1L, "Single"), new ListItem(2L, "Married"), new ListItem(3L, "Divorced"), new ListItem(4L, "Widowed")));
        maritalStatus.setTitle("Marital Status");
        maritalStatus.setHint("Click to Select");

        final FormSingleLineEditTextElement primaryInsurance = new FormSingleLineEditTextElement(14);
        primaryInsurance.setHint("Primary Insurance Name");
        primaryInsurance.setTitle("Primary Insurance");

        final FormSingleLineEditTextElement primaryInsuranceNumber = new FormSingleLineEditTextElement(15);
        primaryInsuranceNumber.setHint("Policy Number");
        primaryInsuranceNumber.setTitle("Policy Number");

        final FormSingleLineEditTextElement primaryInsuranceGroupNumberOrMainPH = new FormSingleLineEditTextElement(16);
        primaryInsuranceGroupNumberOrMainPH.setHint("# / M.P.H");
        primaryInsuranceGroupNumberOrMainPH.setTitle("Group # / Main Policy Holder");



        FormButtonElement save = new FormButtonElement(4);
        save.setValue("Save Personal Information");
        save.getValueObservers().add(new Function2<String, BaseFormElement<String>, Unit>() {
            @Override
            public Unit invoke(String newValue, BaseFormElement<String> element) {


                // Save all of the information.

                SharedPreferences prefs = getSharedPreferences("edu.oaklandstudent.medicalid", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();


                editor.putString("name", AESEncryption.encrypt(name.getValue()));
                editor.putString("dob", AESEncryption.encrypt(dob.getValueAsString()));
                editor.putString("street1", AESEncryption.encrypt(street.getValue()));
                editor.putString("street2", AESEncryption.encrypt(street2.getValue()));
                editor.putString("gender", AESEncryption.encrypt(gender.getValueAsString()));
                editor.putString("city", AESEncryption.encrypt(city.getValue()));
                editor.putString("state", AESEncryption.encrypt(state.getValueAsString()));
                editor.putString("zipCode", AESEncryption.encrypt(zipCode.getValue()));
                editor.putString("homePhone", AESEncryption.encrypt(homePhone.getValue()));
                editor.putString("workPhone", AESEncryption.encrypt(workPhone.getValue()));


                editor.putString("weight", AESEncryption.encrypt(weight.getValue()));
                editor.putString("height", AESEncryption.encrypt(height.getValue()));
                editor.putString("bloodType", AESEncryption.encrypt(bloodType.getValueAsString()));
                editor.putString("ethnicity", AESEncryption.encrypt(ethnicity.getValueAsString()));
                editor.putString("maritalStatus", AESEncryption.encrypt(maritalStatus.getValueAsString()));
                editor.putString("primaryInsurance", AESEncryption.encrypt(primaryInsurance.getValueAsString()));
                editor.putString("primaryInsuranceNumber", AESEncryption.encrypt(primaryInsuranceNumber.getValueAsString()));
                editor.putString("primaryInsuranceGroupNumberOrMainPH", AESEncryption.encrypt(primaryInsuranceGroupNumberOrMainPH.getValueAsString()));

                //Log.wtf("OUS45", "Gender ID is "+gender.getId());
                //Log.wtf("OUS45", "Gender Tag is "+gender.getTag());
                //Log.wtf("OUS45", "Gender val is "+gender.getValue());
                //Log.wtf("OUS45", "Gender valas is "+gender.getValueAsString());
                editor.commit();
                editor.apply();
                Log.v("Main", "The button was pressed." +"\n");


                Snackbar.make(findViewById(android.R.id.content), "Information Saved!", Snackbar.LENGTH_SHORT).show();


                return Unit.INSTANCE;
            }
        });


        elements.add(header);
        elements.add(name);
        elements.add(gender);
        elements.add(dob);
        elements.add(street);
        elements.add(street2);
        elements.add(city);
        elements.add(state);
        elements.add(zipCode);
        elements.add(homePhone);
        elements.add(workPhone);


        elements.add(header2);


        elements.add(maritalStatus);
        elements.add(weight);
        elements.add(height);
        elements.add(bloodType);
        elements.add(ethnicity);
        elements.add(primaryInsurance);
        elements.add(primaryInsuranceNumber);
        elements.add(primaryInsuranceGroupNumberOrMainPH);


        elements.add(save);
        formBuilder.addFormElements(elements);
        // formBuilder.


        // Restore all information here.
        SharedPreferences prefs = getSharedPreferences("edu.oaklandstudent.medicalid", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        name.setValue(AESEncryption.decrypt(prefs.getString("name", null)));
        street.setValue(AESEncryption.decrypt(prefs.getString("street1", null)));
        street2.setValue(AESEncryption.decrypt(prefs.getString("street2", null)));
        city.setValue(AESEncryption.decrypt(prefs.getString("city", null)));
        zipCode.setValue(AESEncryption.decrypt(prefs.getString("zipCode", null)));
        homePhone.setValue(AESEncryption.decrypt(prefs.getString("homePhone", null)));
        workPhone.setValue(AESEncryption.decrypt(prefs.getString("workPhone", null)));


        weight.setValue(AESEncryption.decrypt(prefs.getString("weight", null)));
        height.setValue(AESEncryption.decrypt(prefs.getString("height", null)));
        primaryInsurance.setValue(AESEncryption.decrypt(prefs.getString("primaryInsurance", null)));
        primaryInsuranceNumber.setValue(AESEncryption.decrypt(prefs.getString("primaryInsuranceNumber", null)));
        primaryInsuranceGroupNumberOrMainPH.setValue(AESEncryption.decrypt(prefs.getString("primaryInsuranceGroupNumberOrMainPH", null)));

        if (AESEncryption.decrypt(prefs.getString("maritalStatus", "")).equals("Single"))
            maritalStatus.setValue(new ListItem(1L, "Single"));
        if (AESEncryption.decrypt(prefs.getString("maritalStatus", "")).equals("Married"))
            maritalStatus.setValue(new ListItem(2L, "Married"));
        if (AESEncryption.decrypt(prefs.getString("maritalStatus", "")).equals("Divorced"))
            maritalStatus.setValue(new ListItem(3L, "Divorced"));
        if (AESEncryption.decrypt(prefs.getString("maritalStatus", "")).equals("Widowed"))
            maritalStatus.setValue(new ListItem(4L, "Widowed"));





        if (AESEncryption.decrypt(prefs.getString("ethnicity", "")).equals("American Indian or Alaskan Native"))
            ethnicity.setValue(new ListItem(1L, "American Indian or Alaskan Native"));

        if (AESEncryption.decrypt(prefs.getString("ethnicity", "")).equals("Asian"))
            ethnicity.setValue(new ListItem(2L, "Asian"));

        if (AESEncryption.decrypt(prefs.getString("ethnicity", "")).equals("Black or African American"))
            ethnicity.setValue(new ListItem(3L, "Black or African American"));

        if (AESEncryption.decrypt(prefs.getString("ethnicity", "")).equals("Hispanic or Latino"))
            ethnicity.setValue(new ListItem(4L, "Hispanic or Latino"));

        if (AESEncryption.decrypt(prefs.getString("ethnicity", "")).equals("Native Hawaiian or other Pacific Islander"))
            ethnicity.setValue(new ListItem(5L, "Native Hawaiian or other Pacific Islander"));

        if (AESEncryption.decrypt(prefs.getString("ethnicity", "")).equals("White"))
            ethnicity.setValue(new ListItem(6L, "White"));

        if (AESEncryption.decrypt(prefs.getString("ethnicity", "")).equals("Two or more races"))
            ethnicity.setValue(new ListItem(7L, "Two or more races"));

        if (AESEncryption.decrypt(prefs.getString("ethnicity", "")).equals("Other"))
            ethnicity.setValue(new ListItem(8L, "Other"));





        if (AESEncryption.decrypt(prefs.getString("gender", "")).equals("Male"))
            gender.setValue(new ListItem(1L, "Male"));
        if (AESEncryption.decrypt(prefs.getString("gender", "")).equals("Female"))
            gender.setValue(new ListItem(2L, "Female"));
        if (AESEncryption.decrypt(prefs.getString("gender", "")).equals("Other"))
            gender.setValue(new ListItem(3L, "Other"));

        if (AESEncryption.decrypt(prefs.getString("bloodType", "")).equals("A+"))
            bloodType.setValue(new ListItem(1L, "A+"));

        if (AESEncryption.decrypt(prefs.getString("bloodType", "")).equals("A-"))
            bloodType.setValue(new ListItem(2L, "A-"));

        if (AESEncryption.decrypt(prefs.getString("bloodType", "")).equals("B+"))
            bloodType.setValue(new ListItem(3L, "B+"));

        if (AESEncryption.decrypt(prefs.getString("bloodType", "")).equals("B-"))
            bloodType.setValue(new ListItem(4L, "B-"));

        if (AESEncryption.decrypt(prefs.getString("bloodType", "")).equals("O+"))
            bloodType.setValue(new ListItem(5L, "O+"));

        if (AESEncryption.decrypt(prefs.getString("bloodType", "")).equals("O-"))
            bloodType.setValue(new ListItem(6L, "O-"));

        if (AESEncryption.decrypt(prefs.getString("bloodType", "")).equals("AB+"))
            bloodType.setValue(new ListItem(7L, "AB+"));

        if (AESEncryption.decrypt(prefs.getString("bloodType", "")).equals("AB-"))
            bloodType.setValue(new ListItem(8L, "AB-"));


        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("Alabama"))
            state.setValue(new ListItem(1L, "Alabama"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("Alaska"))
            state.setValue(new ListItem(2L, "Alaska"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("Arizona"))
            state.setValue(new ListItem(3L, "Arizona"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("Arkansas"))
            state.setValue(new ListItem(4L, "Arkansas"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("California"))
            state.setValue(new ListItem(5L, "California"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("Colorado"))
            state.setValue(new ListItem(6L, "Colorado"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("Connecticut"))
            state.setValue(new ListItem(7L, "Connecticut"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("Delaware"))
            state.setValue(new ListItem(8L, "Delaware"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("Florida"))
            state.setValue(new ListItem(9L, "Florida"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("Georgia"))
            state.setValue(new ListItem(10L, "Georgia"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("Hawaii"))
            state.setValue(new ListItem(11L, "Hawaii"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("Idaho"))
            state.setValue(new ListItem(12L, "Idaho"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("Illinois"))
            state.setValue(new ListItem(13L, "Illinois"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("Indiana"))
            state.setValue(new ListItem(14L, "Indiana"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("Iowa"))
            state.setValue(new ListItem(15L, "Iowa"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("Kansas"))
            state.setValue(new ListItem(16L, "Kansas"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("Kentucky"))
            state.setValue(new ListItem(17L, "Kentucky"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("Louisiana"))
            state.setValue(new ListItem(18L, "Louisiana"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("Maine"))
            state.setValue(new ListItem(19L, "Maine"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("Maryland"))
            state.setValue(new ListItem(20L, "Maryland"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("Massachusetts"))
            state.setValue(new ListItem(21L, "Massachusetts"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("Michigan"))
            state.setValue(new ListItem(22L, "Michigan"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("Minnesota"))
            state.setValue(new ListItem(23L, "Minnesota"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("Mississippi"))
            state.setValue(new ListItem(24L, "Mississippi"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("Missouri"))
            state.setValue(new ListItem(25L, "Missouri"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("Montana"))
            state.setValue(new ListItem(26L, "Montana"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("Nebraska"))
            state.setValue(new ListItem(27L, "Nebraska"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("Nevada"))
            state.setValue(new ListItem(28L, "Nevada"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("New Hampshire"))
            state.setValue(new ListItem(29L, "New Hampshire"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("New Jersey"))
            state.setValue(new ListItem(30L, "New Jersey"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("New Mexico"))
            state.setValue(new ListItem(31L, "New Mexico"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("New York"))
            state.setValue(new ListItem(32L, "New York"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("North Carolina"))
            state.setValue(new ListItem(33L, "North Carolina"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("North Dakota"))
            state.setValue(new ListItem(34L, "North Dakota"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("Ohio"))
            state.setValue(new ListItem(35L, "Ohio"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("Oklahoma"))
            state.setValue(new ListItem(36L, "Oklahoma"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("Oregon"))
            state.setValue(new ListItem(37L, "Oregon"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("Pennsylvania"))
            state.setValue(new ListItem(38L, "Pennsylvania"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("Rhode Island"))
            state.setValue(new ListItem(39L, "Rhode Island"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("South Carolina"))
            state.setValue(new ListItem(40L, "South Carolina"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("South Dakota"))
            state.setValue(new ListItem(41L, "South Dakota"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("Tennessee"))
            state.setValue(new ListItem(42L, "Tennessee"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("Texas"))
            state.setValue(new ListItem(43L, "Texas"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("Utah"))
            state.setValue(new ListItem(44L, "Utah"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("Vermont"))
            state.setValue(new ListItem(45L, "Vermont"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("Virginia"))
            state.setValue(new ListItem(46L, "Virginia"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("Washington"))
            state.setValue(new ListItem(47L, "Washington"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("West Virginia"))
            state.setValue(new ListItem(48L, "West Virginia"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("Wisconsin"))
            state.setValue(new ListItem(49L, "Wisconsin"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("Wyoming"))
            state.setValue(new ListItem(50L, "Wyoming"));
        if (AESEncryption.decrypt(prefs.getString("state", "")).equals("Other"))
            state.setValue(new ListItem(50L, "Other"));
        // Code to restore dob.


        String dobAsString = AESEncryption.decrypt(prefs.getString("dob", null));
        if (dobAsString != null) {
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


