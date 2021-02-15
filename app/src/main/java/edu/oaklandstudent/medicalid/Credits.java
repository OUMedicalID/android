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

        FormHeader header = new FormHeader("Credits");
        FormLabelElement label1 = new FormLabelElement(1);
        FormLabelElement label2 = new FormLabelElement(1);
        FormLabelElement label3 = new FormLabelElement(1);
        FormLabelElement label4 = new FormLabelElement(1);
        FormLabelElement label5 = new FormLabelElement(1);
        FormLabelElement label6 = new FormLabelElement(1);
        label1.setTitle("App Developers:                                                                     ");
        label2.setTitle("Yousif Hanani (Android)                                                      ");
        label3.setTitle("Tin Liu (Android)                                                                   ");
        label4.setTitle("Mathew Yaldo (Android, IOS)                                             ");
        label5.setTitle("Aryan Abdolhosseini (IOS)                                                 ");
        label6.setTitle("Adrian Berishaj (IOS)                                                           ");



        elements.add(header);
        elements.add(label1);
        elements.add(label2);
        elements.add(label3);
        elements.add(label4);
        elements.add(label5);
        elements.add(label6);


        formBuilder.addFormElements(elements);


    }
}
