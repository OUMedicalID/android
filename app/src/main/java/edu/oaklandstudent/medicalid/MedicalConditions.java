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
import com.thejuki.kformmaster.model.FormPhoneEditTextElement;
import com.thejuki.kformmaster.model.FormPickerDateElement;
import com.thejuki.kformmaster.model.FormPickerDropDownElement;
import com.thejuki.kformmaster.model.FormSegmentedElement;
import com.thejuki.kformmaster.model.FormSingleLineEditTextElement;
import com.thejuki.kformmaster.helper.FormBuildHelper;


public class MedicalConditions extends AppCompatActivity{


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
        final List<BaseFormElement<?>> elements = new ArrayList<>();

        // First contact
        final FormHeader header1 = new FormHeader("Medical Conditions");








        FormButtonElement save = new FormButtonElement(4);
        save.setValue("Add New Condition");
        save.getValueObservers().add(new Function2<String, BaseFormElement<String>, Unit>() {
            @Override
            public Unit invoke(String newValue, BaseFormElement<String> element) {

                final FormSingleLineEditTextElement name2 = new FormSingleLineEditTextElement(1);
                name2.setHint("Condition Description");
                name2.setTitle("Condition");

                elements.add(name2);
                formBuilder.addFormElements(elements);




                Log.v("Main", "The button was pressed.");
                return Unit.INSTANCE;
            }
        });




        elements.add(header1);

        elements2.add(save);

        formBuilder.addFormElements(elements);


    }


    public void restore(){

        String[] conditions = {"Back Pain", "Shoulder Pain"};



    }


}