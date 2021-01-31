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
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

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

    public List<BaseFormElement<?>> elements = new ArrayList<>();
    public final FormHeader header1 = new FormHeader("Medical Conditions");
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







        FormButtonElement addnew = new FormButtonElement(1);
        addnew.setValue("Add New Condition");
        addnew.getValueObservers().add(new Function2<String, BaseFormElement<String>, Unit>() {
            @Override
            public Unit invoke(String newValue, BaseFormElement<String> element) {
                int newTagId = getNewTagID();
                Log.wtf("OUG45","Created "+String.valueOf(newTagId));
                final FormSingleLineEditTextElement cond = new FormSingleLineEditTextElement(newTagId);
                cond.setHint("Medical Condition");
                cond.setTitle("Condition");
                List<BaseFormElement<?>> elements2 = new ArrayList<>();
                elements2.add(cond);
                formBuilder.addFormElements(elements2);
                //Tried to find what "cond" is stored as so I could use the editor.remove method on it
                for(int i = 0;i < elements2.size();i++){
                    Log.v("Main", "The value of " + i + " is: " );
                }
                Log.v("Main", "The button was pressed.");
                return Unit.INSTANCE;
            }
        });



        FormButtonElement delete = new FormButtonElement(2);
        delete.setValue("Delete A Condition");
        delete.getValueObservers().add(new Function2<String, BaseFormElement<String>, Unit>() {
            @Override
            public Unit invoke(String newValue, BaseFormElement<String> element) {

                showDialog(formBuilder);
/*
                final CharSequence[] items = {"cat1","cat2","cat3" };
                AlertDialog.Builder builder = new AlertDialog.Builder(MedicalConditions.this.getApplicationContext());
                builder.setTitle("Categories");
                builder.setSingleChoiceItems(items , -1,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position
                                // of the selected item
                            }
                        });
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                //handle item1
                                break;
                            case 1:
                                //item2
                                break;
                            case 2:
                                //item3
                                break;
                            default:
                                break;
                        }  }
                });
                AlertDialog alert = builder.create();
                alert.show();
*/

                Log.v("Main", "Delete button was pressed.");
                return Unit.INSTANCE;
            }
        });


        FormButtonElement save = new FormButtonElement(3);
        save.setValue("Save Conditions");
        save.getValueObservers().add(new Function2<String, BaseFormElement<String>, Unit>() {
            @Override
            public Unit invoke(String newValue, BaseFormElement<String> element) {

                SharedPreferences prefs = getSharedPreferences("edu.oaklandstudent.medicalid", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();

                Set<String> mConditions = prefs.getStringSet("mConditions", null);
                if(mConditions == null) mConditions = new HashSet<String>();


                if(latestTagID() == -1)return Unit.INSTANCE;

                for(int i=4;i<=latestTagID(); i++){
                    try {
                        Log.wtf("OUG45",formBuilder.getFormElement(i).getValueAsString());
                        mConditions.add(formBuilder.getFormElement(i).getValueAsString());
                    }
                    //handling the exception
                    catch(NoSuchElementException e){
                    }
                }
                editor.putStringSet("mConditions", mConditions);
                editor.apply();

                Log.v("Main", "Delete button was pressed.");
                return Unit.INSTANCE;
            }
        });


        elements.add(addnew);
        elements.add(delete);
        elements.add(save);
        elements.add(header1);
        formBuilder.addFormElements(elements);

        restore(formBuilder);
    }

    public int getNewTagID(){
        newID++;
        return newID;

    }

    public int latestTagID(){
        return newID;
    }

    public void restore(FormBuildHelper formBuilder){
        SharedPreferences prefs = getSharedPreferences("edu.oaklandstudent.medicalid", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Set<String> mConditions = prefs.getStringSet("mConditions", null);

        if(mConditions == null) return;
        if(mConditions.isEmpty()) return;


        for (String s : mConditions) {

            int newTagId = getNewTagID();
            final FormSingleLineEditTextElement cond = new FormSingleLineEditTextElement(newTagId);
            cond.setHint("Medical Condition");
            cond.setTitle("Condition");
            cond.setValue(s);
            List<BaseFormElement<?>> elements2 = new ArrayList<>();
            elements2.add(cond);
           formBuilder.addFormElements(elements2);
        }
    }


    //String[] items = new String[]{"Back Pain","Shoulder Pain","1","Back Pain","Shoulder Pain","1","Back Pain","Shoulder Pain","1","Back Pain","Shoulder Pain","1", "bbb","bbb","bbb"};
    //boolean selected[] = new boolean[]{false, false, false, false, false, false,false, false, false,false, false, false,false, false, false};

    private void showDialog(final FormBuildHelper formbuilder) {
        SharedPreferences prefs = getSharedPreferences("edu.oaklandstudent.medicalid", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();
        final Set<String> mConditions = prefs.getStringSet("mConditions", null);
        final String[] conditionArray = new String[mConditions.size()];
        final boolean selected[] = new boolean[mConditions.size()];
        int count = 0;
        if(mConditions == null) return;
        if(mConditions.isEmpty()) return;

        for (String s : mConditions) {
            conditionArray[count] = s;
            count++;
        }
        for(int j = 0; j < selected.length;j++){
            selected[j] = false;
        }
        /*
        This can be used to check the keys in mConditions

        Map<String,?> keys = prefs.getAll();
        for(Map.Entry<String,?> entry : keys.entrySet()){
            Log.d("map values",entry.getKey() + ": " + entry.getValue().toString());
        }
        */
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Conditions Saved");
        builder.setMultiChoiceItems(conditionArray, selected, new DialogInterface.OnMultiChoiceClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                Log.wtf("OUG45"," WE CLICKED "+String.valueOf(which));

                for (int i = 0; i < selected.length; i++) {
                    if (i == which) {
                        selected[i]=true;
                        //((AlertDialog) dialog).getListView().setItemChecked(i, true);
                    }
                    else {
                        selected[i]=false;
                        //((AlertDialog) dialog).getListView().setItemChecked(i, false);
                    }
                }
            }

        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Removes everything in mConditions
                editor.remove("mConditions");
                //Removes the element but only works when backspacing out of the page and going back in
                mConditions.remove(formbuilder.getFormElement(5).getValueAsString());
                editor.commit();
                editor.apply();
                // Remove what was checked.


                dialogInterface.dismiss();
            }
        });

        builder.show();
    }


}