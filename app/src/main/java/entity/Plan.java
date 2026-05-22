package entity;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;

public class Plan {
    private String name;
    private Calendar beginDate;
    private LocalTime beginHours;
    private LocalTime endHours;
    private Calendar endDate;
    private ArrayList<Parameter> parameters = new ArrayList<Parameter>();
    private long id;
    public Plan(String name, Calendar beginDate, Calendar endDate, LocalTime beginHours, LocalTime endHours)
    {
        this.name=name;
        this.beginDate=beginDate;
        this.endDate=endDate;
        this.beginHours = beginHours;
        this.endHours = endHours;
    }



    public ArrayList<Parameter> getParameters() {
        return parameters;
    }

    public String getName() {
        return this.name;
    }

    public Calendar getBeginDate() {
        return this.beginDate;
    }

    public LocalTime getBeginHours() {
        return this.beginHours;
    }

    public Calendar getEndDate() {
        return this.endDate;
    }

    public LocalTime getEndHours() {
        return this.endHours;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setBeginHours(LocalTime beginHours) {
        this.beginHours = beginHours;
    }

    public void setBeginDate(Calendar beginDate) {
        this.beginDate = beginDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public void setEndHours(LocalTime endHours) {
        this.endHours = endHours;
    }

    public void setName(String name) {
        this.name = name;
    }


}
