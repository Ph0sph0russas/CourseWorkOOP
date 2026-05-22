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
//        DB.connectionDBHealthCourse();
        Plan plan = new Plan("Давление",
        new GregorianCalendar(2026,5,22),
        new GregorianCalendar(2026,5,29),
        LocalTime.of(12,0),
        LocalTime.of(19,0)
        );
        Parameter highPressure = new Parameter(
                "Высокое давление",
                "мм. рт. ст.",
                new GregorianCalendar(2026,5,22),
                new GregorianCalendar(2026,5,29),
                2,
                2,
                LocalTime.of(12,0),
                LocalTime.of(19,0)
        );
//        Result r1 = new Result(
//                120,
//                new GregorianCalendar(2026,5,22),
//                LocalTime.of(12,0)
//        );
//
//        Result r2 = new Result(
//                100,
//                new GregorianCalendar(2026,5,22),
//                LocalTime.of(15,30)
//        );
//
//        Result r3 = new Result(
//                130,
//                new GregorianCalendar(2026,5,26),
//                LocalTime.of(12,0)
//        );
//
//        Result r4 = new Result(
//                150,
//                new GregorianCalendar(2026,5,26),
//                LocalTime.of(15,30)
//        );

        plan.getParameters().add(highPressure);
//        plan.getParameters().get(0).getResults().add(r1);
//        plan.getParameters().get(0).getResults().add(r2);
//        plan.getParameters().get(0).getResults().add(r3);
//        plan.getParameters().get(0).getResults().add(r4);
        scheludes.add(plan);
    }
    public void initializeData()
    {
//        Plan plan2 = new Plan("Температура",
//                new GregorianCalendar(2026, 4, 17),
//                new GregorianCalendar(2026, 5, 3),
//                LocalTime.of(0, 0),
//                LocalTime.of(23, 0));
//
//        Parameter bodyTemp = new Parameter("Температура тела", "°C",
//                new GregorianCalendar(2026, 4, 21), new GregorianCalendar(2026, 4, 25),
//                3, 3,
//                LocalTime.of(10, 0), LocalTime.of(14, 0));
//
//        Parameter roomTemp = new Parameter("Комнатная температура", "°C",
//                new GregorianCalendar(2026, 5, 1), new GregorianCalendar(2026, 5, 3),
//                2, 4,
//                LocalTime.of(10, 0), LocalTime.of(19, 0));
//
//        plan2.getParameters().add(bodyTemp);
//        plan2.getParameters().add(roomTemp);
//
//
//        Result r1 = new Result(365, new GregorianCalendar(2026, 4, 17), LocalTime.of(7, 30));
//        Result r2 = new Result(368, new GregorianCalendar(2026, 4, 17), LocalTime.of(12, 15));
//        Result r3 = new Result(370, new GregorianCalendar(2026, 4, 17), LocalTime.of(17, 45));
//
//// 18 мая 2026
//        Result r4 = new Result(366, new GregorianCalendar(2026, 4, 18), LocalTime.of(6, 45));
//        Result r5 = new Result(369, new GregorianCalendar(2026, 4, 18), LocalTime.of(14, 0));
//        Result r6 = new Result(371, new GregorianCalendar(2026, 4, 18), LocalTime.of(18, 0));
//
//// 19 мая 2026
//        Result r7 = new Result(364, new GregorianCalendar(2026, 4, 19), LocalTime.of(8, 20));
//        Result r8 = new Result(367, new GregorianCalendar(2026, 4, 19), LocalTime.of(13, 10));
//        Result r9 = new Result(372, new GregorianCalendar(2026, 4, 19), LocalTime.of(16, 50));
//        plan2.getParameters().get(0).getResults().add(r1);
//        plan2.getParameters().get(0).getResults().add(r2);
//        plan2.getParameters().get(0).getResults().add(r3);
//        plan2.getParameters().get(0).getResults().add(r4);
//        plan2.getParameters().get(0).getResults().add(r5);
//        plan2.getParameters().get(0).getResults().add(r6);
//        plan2.getParameters().get(0).getResults().add(r7);
//        plan2.getParameters().get(0).getResults().add(r8);
//        plan2.getParameters().get(0).getResults().add(r9);
//
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
//        plan3.getParameters().add(steps);
//        plan3.getParameters().add(calories);
//
//
//        getScheludes().add(plan2);
//        getScheludes().add(plan3);

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

            Calendar now = Calendar.getInstance();

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

    @Override
    public void onTerminate() {
        super.onTerminate();
        DB.closeDB();
    }
}
