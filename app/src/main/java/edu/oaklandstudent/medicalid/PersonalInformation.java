package edu.oaklandstudent.medicalid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.oaklandstudent.medicalid.R;
import com.thejuki.kformmaster.listener.OnFormElementValueChangedListener;
import com.thejuki.kformmaster.model.BaseFormElement;
import com.thejuki.kformmaster.model.FormHeader;
import com.thejuki.kformmaster.model.FormPickerDateElement;
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


        FormSingleLineEditTextElement name = new FormSingleLineEditTextElement(1);
        name.setHint("Full Name");
        name.setTitle("Name");

        FormPickerDateElement dob = new FormPickerDateElement(2);
        dob.setDateValue(new Date());
        dob.setDateFormat(new SimpleDateFormat("MM/dd/yyyy", Locale.US));
        dob.setTitle("Date of Birth");

        FormSingleLineEditTextElement street = new FormSingleLineEditTextElement(3);
        street.setHint("Street");
        street.setTitle("Street");

        elements.add(header);
        elements.add(name);
        elements.add(dob);
        elements.add(street);
        formBuilder.addFormElements(elements);
        // formBuilder.
    }


}