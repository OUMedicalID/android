package edu.oaklandstudent.medicalid;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.thejuki.kformmaster.helper.FormBuildHelper;
import com.thejuki.kformmaster.listener.OnFormElementValueChangedListener;
import com.thejuki.kformmaster.model.BaseFormElement;
import com.thejuki.kformmaster.model.FormHeader;
import com.thejuki.kformmaster.model.FormLabelElement;
import com.thejuki.kformmaster.model.FormSingleLineEditTextElement;

import java.util.ArrayList;
import java.util.List;

public class Credits extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.informationcontainer);

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

        FormHeader header = new FormHeader("Developers");
        FormSingleLineEditTextElement label2 = new FormSingleLineEditTextElement(1);
        FormSingleLineEditTextElement label3 = new FormSingleLineEditTextElement(2);
        FormSingleLineEditTextElement label4 = new FormSingleLineEditTextElement(3);
        FormSingleLineEditTextElement label5 = new FormSingleLineEditTextElement(4);
        FormSingleLineEditTextElement label6 = new FormSingleLineEditTextElement(5);

        label2.setValue("Developer");
        label3.setValue("Developer");
        label4.setValue("Developer");
        label5.setValue("Developer");
        label6.setValue("Developer");

        label2.setTitle("Yousif Hanani");
        label3.setTitle("Tin Liu");
        label4.setTitle("Mathew Yaldo");
        label5.setTitle("Aryan Abdolhosseini");
        label6.setTitle("Adrian Berishaj");

        label2.setFocusable(false);
        label3.setFocusable(false);
        label4.setFocusable(false);
        label5.setFocusable(false);
        label6.setFocusable(false);


        elements.add(header);
        elements.add(label2);
        elements.add(label3);
        elements.add(label4);
        elements.add(label5);
        elements.add(label6);


        formBuilder.addFormElements(elements);


    }
}
