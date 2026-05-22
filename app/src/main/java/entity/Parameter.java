package entity;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;


public class Parameter {
    private String name="name";
    private String unitOfMeasurement="name";
    private Calendar beginDate;
    private Calendar endDate;
    private LocalTime beginHours;
    private LocalTime endHours;
    private int periodicityDate=1;
    private int periodicityTime=1;
    private ArrayList<Result> results = new ArrayList<Result>();
    private long id;
    public Parameter(){}
    public Parameter(String name, String unitOfMeasurement, Calendar beginDate, Calendar endDate, int periodicityDate, int periodicityTime, LocalTime beginHours, LocalTime endHours)
    {
        this.name=name;
        this.unitOfMeasurement=unitOfMeasurement;
        this.beginDate=beginDate;
        this.endDate=endDate;
        this.periodicityDate=periodicityDate;
        this.periodicityTime=periodicityTime;
        this.beginHours = beginHours;
        this.endHours = endHours;

    }
    public int getPeriodToActivateInDays()
    {

        long diffInDays= getDiffInDays();
        double periodToActivateInDays= Math.ceil(((double)diffInDays / (double)periodicityDate));
        return (int)periodToActivateInDays;
    }

    public int getDiffInDays() {

        long diffInMillis = this.endDate.getTimeInMillis() - this.beginDate.getTimeInMillis();
        int diffInDays= (int)(diffInMillis / (24 * 60 * 60 * 1000))+1;
        return diffInDays;
    }

    public int getPeriodToActivateInMinutes()
    {
        int afterNight = 1440;
        int endHoursInMinutes = endHours.getHour()*60+ endHours.getMinute();
        int startHoursInMinutes = beginHours.getHour()*60 + beginHours.getMinute();
        if (endHoursInMinutes > startHoursInMinutes)
            afterNight=0;
        return ((endHoursInMinutes + afterNight)- startHoursInMinutes)/this.periodicityTime;
    }

    public ArrayList<Result> getResults() {
        return this.results;
    }

    public String getName() {
        return this.name;
    }

    public String getUnitOfMeasurement() {
        return this.unitOfMeasurement;
    }

    public Calendar getBeginDate() {
        return this.beginDate;
    }

    public Calendar getEndDate() {
        return this.endDate;
    }

    public LocalTime getBeginHours() {
        return this.beginHours;
    }

    public LocalTime getEndHours() {
        return this.endHours;
    }

    public int getPeriodicityDate() {
        return this.periodicityDate;
    }

    public int getPeriodicityTime() {
        return this.periodicityTime;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnitOfMeasurement(String unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public void setBeginDate(Calendar beginDate) {
        this.beginDate = beginDate;
    }

    public void setBeginHours(LocalTime beginHours) {
        this.beginHours = beginHours;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public void setEndHours(LocalTime endHours) {
        this.endHours = endHours;
    }

    public void setPeriodicityDate(int periodicityDate) {
        this.periodicityDate = periodicityDate;
    }

    public void setPeriodicityTime(int periodicityTime) {
        this.periodicityTime = periodicityTime;
    }
}
