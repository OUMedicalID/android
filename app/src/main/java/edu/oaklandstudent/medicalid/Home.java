package edu.oaklandstudent.medicalid;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Random;


public class Home extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        int[] myImageList = new int[]{R.drawable.ic_undraw_dog_walking_re_l61p, R.drawable.ic_undraw_welcome_3gvl, R.drawable.ic_undraw_woman_mevk};




        View RootView = inflater.inflate(R.layout.home, container, false);
        ImageView iv = (ImageView)RootView.findViewById(R.id.happyPic);
        iv.setImageResource(myImageList[new Random().nextInt(myImageList.length)]);
        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) iv.getLayoutParams();
        params.width = 600;
        params.height = 510;
        // existing height is ok as is, no need to edit it
        iv.setLayoutParams(params);

        return RootView;
    }
}