package edu.oaklandstudent.medicalid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.IOException;
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
import java.util.Random;
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
    public int newID = 0;


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







        FormButtonElement addnew = new FormButtonElement();
        addnew.setValue("Add New Condition");
        addnew.getValueObservers().add(new Function2<String, BaseFormElement<String>, Unit>() {
            @Override
            public Unit invoke(String newValue, BaseFormElement<String> element) {






                try {
                    String latest = formBuilder.getFormElement(latestTagID()).getValueAsString();
                    if (latest == null || latest.equals("")) return null;
                }catch(final NoSuchElementException ex) {}



                int newTagId = getNewTagID();
                Log.wtf("OUG45","Created "+String.valueOf(newTagId));
                final FormSingleLineEditTextElement cond = new FormSingleLineEditTextElement(newTagId);
                cond.setHint("Medical Condition");
                cond.setTitle("Condition");
                List<BaseFormElement<?>> elements2 = new ArrayList<>();
                elements2.add(cond);

                formBuilder.addFormElement(cond);
                formBuilder.setItems();

                /* Wait 200 milliseconds and attempt the restore.
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        saveData(formBuilder); // Save everything for our attempt to bring original values back.
                        attemptFix(formBuilder);
                    }
                }, 200);
               */


                Log.v("Main", "The button was pressed.");
                return Unit.INSTANCE;
            }
        });



        FormButtonElement delete = new FormButtonElement();
        delete.setValue("Delete A Condition");
        delete.getValueObservers().add(new Function2<String, BaseFormElement<String>, Unit>() {
            @Override
            public Unit invoke(String newValue, BaseFormElement<String> element) {

                showDialog(formBuilder);

                Log.v("Main", "Delete button was pressed.");
                return Unit.INSTANCE;
            }
        });


        FormButtonElement save = new FormButtonElement();
        save.setValue("Save Conditions");
        save.getValueObservers().add(new Function2<String, BaseFormElement<String>, Unit>() {
            @Override
            public Unit invoke(String newValue, BaseFormElement<String> element) {

                saveData(formBuilder);

                Log.wtf("OUG45", "Save button was pressed.");
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
        ++newID;
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



        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Conditions Saved");
        builder.setMultiChoiceItems(conditionArray, selected, new DialogInterface.OnMultiChoiceClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                Log.wtf("OUG45"," WE CLICKED "+String.valueOf(which));
                if(isChecked){
                    selected[which] = true;
                } else {
                    selected[which] = false;
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

                Set<String> newConditionsList = new HashSet<String>();

                // Per https://stackoverflow.com/questions/49964074/removing-items-from-string-set-in-shared-preferences-not-persisting-across-app-r?noredirect=1&lq=1
                // When adding or removing from a string set, we for some reason cannot use the list that we get back from SharedPreferences as it seems to resists commits.
                // Instead, we take the string set we're given, load all elements in a new string set, and then save that as the new one.

                int k=0;
                for (boolean b : selected) {
                    if(!b) newConditionsList.add(conditionArray[k]);
                    k++;
                }
                editor.putStringSet("mConditions", newConditionsList);
                editor.commit();


                // After making the deletions, it is much easier to reload the page than to try removing the now deleted elements from the view while keeping everything else.
                Intent intent = getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                startActivity(intent);

                dialogInterface.dismiss();
            }
        });

        builder.show();
    }


    public void saveData(final FormBuildHelper formBuilder){
        SharedPreferences prefs = getSharedPreferences("edu.oaklandstudent.medicalid", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        Set<String> mConditions = prefs.getStringSet("mConditions", null);
        if(mConditions == null) mConditions = new HashSet<String>();
        if(latestTagID() == -1)return;

        Set<String> mConditions2 = new HashSet<String>();
        for (String s : mConditions) {
            if(s.equals(""))continue;
            mConditions2.add(s);
        }

        for(int i=0;i<=latestTagID(); i++){
            try {
                Log.wtf("OUG45","Saving "+formBuilder.getFormElement(i).getValueAsString());
                mConditions2.add(formBuilder.getFormElement(i).getValueAsString());
            }
            //handling the exception
            catch(NoSuchElementException e){
            }
        }
        editor.putStringSet("mConditions", mConditions2);
        editor.apply();
        editor.commit();
    }


    public void attemptFix(FormBuildHelper formBuilder){
        // Attempt to prevent overwriting of previous conditions.
        SharedPreferences prefs = getSharedPreferences("edu.oaklandstudent.medicalid", Context.MODE_PRIVATE);
        Set<String> mConditions = prefs.getStringSet("mConditions", null);
        if(mConditions == null) return;
        if(mConditions.isEmpty()) return;
        int m = 1;
        for (String s : mConditions) {
            try {
                if(s.equals(""))continue;
                Log.wtf("OUG45","Restoring "+String.valueOf(m)+ " with "+s);
                formBuilder.getFormElement(m).setValue(s);
            } catch (NoSuchElementException e) {
                Log.wtf("OUG45","Failed "+String.valueOf(m)+ " with "+s);
            }
            ++m;
        }
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }


}