package edu.oaklandstudent.medicalid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ConditionAdapter extends RecyclerView.Adapter<ConditionAdapter.ConditionView> {

    ArrayList<Condition> conditionsList = new ArrayList<>();

    public ConditionAdapter(ArrayList<Condition> conditionsList) {
        this.conditionsList = conditionsList;
    }

    @NonNull
    @Override
    public ConditionView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_condition,parent,false);

        return new ConditionView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConditionView holder, int position) {

        Condition condition = conditionsList.get(position);
        holder.textConditionName.setText(condition.getConditionName());


    }

    @Override
    public int getItemCount() {
        return conditionsList.size();
    }

    public class ConditionView extends RecyclerView.ViewHolder{

        TextView textConditionName;
        public ConditionView(@NonNull View itemView) {
            super(itemView);

            textConditionName = (TextView)itemView.findViewById(R.id.text_condition_name);

        }
    }

}
