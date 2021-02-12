package edu.oaklandstudent.medicalid;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Map;

public class ExportData {

    public static void getSavedPrefsData(Context context){
        SharedPreferences prefs = context.getSharedPreferences("edu.oaklandstudent.medicalid", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        Map<String, ?> allEntries = prefs.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.wtf("MAINDEBUG", entry.getKey() + ": " + entry.getValue().toString());
        }
    }
}
