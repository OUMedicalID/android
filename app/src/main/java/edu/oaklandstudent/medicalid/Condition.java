package edu.oaklandstudent.medicalid;


import java.io.Serializable;

public class Condition implements Serializable {

    public String conditionName;

    public Condition() {

    }

    public Condition(String conditionName) {
        this.conditionName = conditionName;
    }

    public String getConditionName() {
        return conditionName;
    }

    public void setConditionName(String conditionName) {
        this.conditionName = conditionName;
    }
}
