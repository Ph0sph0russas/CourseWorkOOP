package com.example.myapplication;

import android.app.Application;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import entity.Parameter;
import entity.Plan;
import entity.Result;

public class App extends Application {

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

    @Override
    public void onCreate() {
        super.onCreate();
        initializeData();
    }
    public void initializeData()
    {
        Plan plan2 = new Plan("Температура",
                new GregorianCalendar(2026, 4, 17),
                new GregorianCalendar(2026, 5, 3),
                LocalTime.of(6, 0),
                LocalTime.of(20, 0));

        Parameter bodyTemp = new Parameter("Температура тела", "°C",
                new GregorianCalendar(2026, 4, 17), new GregorianCalendar(2026, 4, 25),
                9, 12,
                LocalTime.of(6, 0), LocalTime.of(18, 0));

        Parameter roomTemp = new Parameter("Комнатная температура", "°C",
                new GregorianCalendar(2026, 5, 1), new GregorianCalendar(2026, 5, 3),
                2, 4,
                LocalTime.of(10, 0), LocalTime.of(19, 0));

        plan2.addParameter(bodyTemp);
        plan2.addParameter(roomTemp);


        Result r1 = new Result(365, new GregorianCalendar(2026, 4, 17), LocalTime.of(7, 30));
        Result r2 = new Result(368, new GregorianCalendar(2026, 4, 17), LocalTime.of(12, 15));
        Result r3 = new Result(370, new GregorianCalendar(2026, 4, 17), LocalTime.of(17, 45));

// 18 мая 2026
        Result r4 = new Result(366, new GregorianCalendar(2026, 4, 18), LocalTime.of(6, 45));
        Result r5 = new Result(369, new GregorianCalendar(2026, 4, 18), LocalTime.of(14, 0));
        Result r6 = new Result(371, new GregorianCalendar(2026, 4, 18), LocalTime.of(18, 0));

// 19 мая 2026
        Result r7 = new Result(364, new GregorianCalendar(2026, 4, 19), LocalTime.of(8, 20));
        Result r8 = new Result(367, new GregorianCalendar(2026, 4, 19), LocalTime.of(13, 10));
        Result r9 = new Result(372, new GregorianCalendar(2026, 4, 19), LocalTime.of(16, 50));
        plan2.getParameters().get(0).addResult(r1);
        plan2.getParameters().get(0).addResult(r2);
        plan2.getParameters().get(0).addResult(r3);
        plan2.getParameters().get(0).addResult(r4);
        plan2.getParameters().get(0).addResult(r5);
        plan2.getParameters().get(0).addResult(r6);
        plan2.getParameters().get(0).addResult(r7);
        plan2.getParameters().get(0).addResult(r8);
        plan2.getParameters().get(0).addResult(r9);

//        Plan plan3 = new Plan("Физическая активность",
//                new GregorianCalendar(2026, 4, 25),  // 25 мая 2026
//                new GregorianCalendar(2026, 4, 27),  // 27 мая 2026
//                LocalTime.of(7, 0),
//                LocalTime.of(22, 0));
//
//        Parameter steps = new Parameter("Количество шагов", "шагов",
//                new GregorianCalendar(2026, 4, 25), new GregorianCalendar(2026, 4, 27),
//                1, 1,                               // 1 запись в день
//                LocalTime.of(20, 0), LocalTime.of(22, 0)); // вечером
//
//        Parameter calories = new Parameter("Сожжённые калории", "ккал",
//                new GregorianCalendar(2026, 4, 25), new GregorianCalendar(2026, 4, 27),
//                2, 3,
//                LocalTime.of(12, 0), LocalTime.of(21, 0));
//
//        plan3.addParameter(steps);
//        plan3.addParameter(calories);


        getScheludes().add(plan2);
//        app.getScheludes().add(plan3);

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
        transformedCalendar = new GregorianCalendar(year,month,days);
        return transformedCalendar;
    }
}
