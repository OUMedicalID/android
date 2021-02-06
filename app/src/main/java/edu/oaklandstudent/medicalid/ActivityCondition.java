package edu.oaklandstudent.medicalid;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ActivityCondition extends AppCompatActivity {

    RecyclerView recyclerCondition;
    ArrayList<Condition> conditionList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condition);

        recyclerCondition = findViewById(R.id.recycler_conditions);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerCondition.setLayoutManager(layoutManager);

        conditionList = (ArrayList<Condition>) getIntent().getExtras().getSerializable("list");

        recyclerCondition.setAdapter(new ConditionAdapter(conditionList));

    }
}
