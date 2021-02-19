package edu.oaklandstudent.medicalid;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.thejuki.kformmaster.helper.FormBuildHelper;
import com.thejuki.kformmaster.listener.OnFormElementValueChangedListener;
import com.thejuki.kformmaster.model.BaseFormElement;

public class About extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);


        ImageView iv = (ImageView)findViewById(R.id.picture);
        iv.setImageResource(R.drawable.ic_undraw_profile_6l1l);
        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) iv.getLayoutParams();
        params.width = 800;
        params.height = 710;
        iv.setLayoutParams(params);

        
    }
}
