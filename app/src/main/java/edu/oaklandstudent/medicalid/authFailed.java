package edu.oaklandstudent.medicalid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import edu.oaklandstudent.medicalid.R;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

import com.google.android.material.snackbar.Snackbar;
import com.thejuki.kformmaster.item.ListItem;
import com.thejuki.kformmaster.listener.OnFormElementValueChangedListener;
import com.thejuki.kformmaster.model.BaseFormElement;
import com.thejuki.kformmaster.model.FormButtonElement;
import com.thejuki.kformmaster.model.FormHeader;
import com.thejuki.kformmaster.model.FormLabelElement;
import com.thejuki.kformmaster.model.FormPasswordEditTextElement;
import com.thejuki.kformmaster.model.FormPhoneEditTextElement;
import com.thejuki.kformmaster.model.FormPickerDateElement;
import com.thejuki.kformmaster.model.FormPickerDropDownElement;
import com.thejuki.kformmaster.model.FormSegmentedElement;
import com.thejuki.kformmaster.model.FormSingleLineEditTextElement;
import com.thejuki.kformmaster.helper.FormBuildHelper;


public class authFailed extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authfailed);

        ImageView iv = (ImageView)findViewById(R.id.picture);
        iv.setImageResource(R.drawable.ic_undraw_authentication_fsn5);
        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) iv.getLayoutParams();
        params.width = 800;
        params.height = 710;
        iv.setLayoutParams(params);


        Button resetApplication = (Button) findViewById(R.id.resetApplication);
        resetApplication.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                SharedPreferences prefs = getSharedPreferences("edu.oaklandstudent.medicalid", Context.MODE_PRIVATE);
                final SharedPreferences.Editor editor = prefs.edit();


                android.app.AlertDialog.Builder builder = new AlertDialog.Builder(authFailed.this);
                builder.setTitle("Application Data");
                builder.setMessage("Are you sure you want to clear app data?");

                builder.setPositiveButton(R.string.dialog_yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        editor.clear().apply();
                        Snackbar.make(findViewById(android.R.id.content), "All data cleared. Please restart the application.", Snackbar.LENGTH_INDEFINITE).show();
                    }
                });

                builder.setNegativeButton(R.string.dialog_no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                builder.show();


            }
        });




    }

    // Disable the software back button, effectively keeping users trapped in this page.
    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }

}

