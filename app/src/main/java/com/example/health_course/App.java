package com.example.health_course;

import android.app.Application;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import entity.Parameter;
import entity.Plan;
import entity.Result;

public class App extends Application {
    private DB dataBase;

    public DB getDataBase() {
        return this.dataBase;
    }

    private ArrayList<Plan> scheludes = new ArrayList<>();
    private int planOpenNumber;
    private int parameterOpenNumber;
    public ArrayList<Plan> getScheludes() {
        return this.scheludes;
    }

    public int getPlanOpenNumber() {
        return this.planOpenNumber;
    }

    public void setPlanOpenNumber(int planOpenNumber) {
        this.planOpenNumber = planOpenNumber;
    }

    public int getParameterOpenNumber() {
        return this.parameterOpenNumber;
    }

    public void setParameterOpenNumber(int parameterOpenNumber) {
        this.parameterOpenNumber = parameterOpenNumber;
    }

    public void setScheludes(ArrayList<Plan> scheludes) {
        this.scheludes = scheludes;
    }
    public void updateArrays()
    {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        dataBase = new DB(this);
        dataBase.readDB();
        this.scheludes=dataBase.getScheludes();
    }
    public static Calendar stringToCalendar(String dateToTransform)
    {
        Calendar transformedCalendar;

        int days=0;
        int month = 0;
        int year = 0;

        boolean gotDays=false;
        boolean gotMonth=false;
        for (int i = 0; i<dateToTransform.length();i++)
        {
            if (dateToTransform.charAt(i)=='.')
            {
                if (gotDays==false)
                {
                    days= Integer.parseInt(dateToTransform.substring(0,i));
                    dateToTransform=dateToTransform.substring(i+1);
                    gotDays=true;
                    i=0;
                }
                else if(gotMonth==false)
                {
                    month = Integer.parseInt(dateToTransform.substring(0,i));
                    dateToTransform=dateToTransform.substring(i+1);
                    gotMonth=true;
                    i=0;
                }
            }
            else if (gotDays==true && gotMonth==true)
            {
                year = Integer.parseInt(dateToTransform);
                i=dateToTransform.length();
            }

        }
        transformedCalendar = new GregorianCalendar(year,month-1,days);
        return transformedCalendar;
    }
    public static boolean dateStringCheck(String date)
    {
        if (date.matches("\\d{2}\\.\\d{2}\\.\\d{4}"))
        {
            String day = date.substring(0,2);
            String month = date.substring(3,5);
            int monthInInt = Integer.parseInt(month)-1;
            String year = date.substring(6);
            int[] daysInMonth= {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

            if (monthInInt < 1 || monthInInt > 12)
            {
                return false;
            }

            if (monthInInt == 2 &&
                    (((Integer.parseInt(year) % 4 == 0) && (Integer.parseInt(year) % 100 != 0) && Integer.parseInt(year)%400==0))
            )
            {
                daysInMonth[1] = 29;
            }
            if (Integer.parseInt(day) < 1 || Integer.parseInt(day) > daysInMonth[monthInInt-1])
            {
                return false;
            }

            Calendar now = new GregorianCalendar(
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            );

            Calendar dateToCheck = new GregorianCalendar(Integer.parseInt(year), monthInInt, Integer.parseInt(day));

            if (now.getTimeInMillis() > dateToCheck.getTimeInMillis())
            {
                return false;
            }

            return true;
        }
        else
        {
            return false;
        }

    }


}
