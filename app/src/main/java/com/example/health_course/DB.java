package com.example.health_course;


import static com.example.health_course.App.stringToCalendar;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.*;
import android.content.Context;

import java.time.LocalTime;
import java.util.ArrayList;

import entity.Parameter;
import entity.Plan;
import entity.Result;

public class DB extends SQLiteOpenHelper {
    SQLiteDatabase db;

    DB(Context context) {
        super(context, "health_course.db", null, 1);
    }

    private ArrayList<Plan> scheludes = new ArrayList<>();

    public long writeScheludeDB(String enteredName, String enteredBeginDate, String enteredEndDate, String enteredBeginTime, String enteredEndTime)
    {
        db = this.getWritableDatabase();
        ContentValues valuesToEnter = new ContentValues();
        valuesToEnter.put("name", enteredName);
        valuesToEnter.put("begin_date", enteredBeginDate);
        valuesToEnter.put("end_date", enteredEndDate);
        valuesToEnter.put("begin_time", enteredBeginTime);
        valuesToEnter.put("end_time", enteredEndTime);
        long newId= db.insert("Plan", null, valuesToEnter);
        db.close();
        return newId;

    }
    public long writeParameterDB(String enteredName, String enteredUnitOfMeas, String enteredBeginDate, String enteredEndDate, String enteredBeginTime, String enteredEndTime, String enteredPeriodTime, String enteredPeriodDate, long planId)
    {
        db = this.getWritableDatabase();
        ContentValues valuesToEnter = new ContentValues();
        valuesToEnter.put("name", enteredName);
        valuesToEnter.put("plan_id", planId);
        valuesToEnter.put("unit_of_measurement", enteredUnitOfMeas);
        valuesToEnter.put("begin_date", enteredBeginDate);
        valuesToEnter.put("end_date", enteredEndDate);
        valuesToEnter.put("begin_time", enteredBeginTime);
        valuesToEnter.put("end_time", enteredEndTime);
        valuesToEnter.put("periodicity_date", enteredPeriodDate);
        valuesToEnter.put("periodicity_time", enteredPeriodTime);
        long newId = db.insert("Parameter", null, valuesToEnter);
        db.close();
        return newId;
    }
    public long writeResultDB(int enteredResult, String enteredDate, String enteredTime, long parameterId)
    {
        db = this.getWritableDatabase();
        ContentValues valuesToEnter = new ContentValues();
        valuesToEnter.put("result", enteredResult);
        valuesToEnter.put("date", enteredDate);
        valuesToEnter.put("time", enteredTime);
        valuesToEnter.put("parameter_id", parameterId);
        long newId =  db.insert("Result", null, valuesToEnter);
        db.close();
        return newId;

    }
    public void readDB()
    {
        db = this.getReadableDatabase();
        scheludes.clear();
        Cursor selectQuerys = db.rawQuery("SELECT * FROM 'Plan'", null);
        while (selectQuerys.moveToNext())
        {
            long planId = selectQuerys.getLong(selectQuerys.getColumnIndexOrThrow("id"));
            String planName = selectQuerys.getString(selectQuerys.getColumnIndexOrThrow("name"));
            String planBeginDate = selectQuerys.getString(selectQuerys.getColumnIndexOrThrow("begin_date"));
            String planEndDate = selectQuerys.getString(selectQuerys.getColumnIndexOrThrow("end_date"));
            String planBeginTime = selectQuerys.getString(selectQuerys.getColumnIndexOrThrow("begin_time"));
            String planEndTime = selectQuerys.getString(selectQuerys.getColumnIndexOrThrow("end_time"));



            Plan plan = new Plan(planName, stringToCalendar(planBeginDate), stringToCalendar(planEndDate), LocalTime.parse(planBeginTime), LocalTime.parse(planEndTime));
            plan.setId(planId);
            scheludes.add(plan);
        }
        selectQuerys.close();
        selectQuerys= db.rawQuery("SELECT * FROM 'Parameter'", null);
        while (selectQuerys.moveToNext())
        {
            long paramId = selectQuerys.getLong(selectQuerys.getColumnIndexOrThrow("id"));
            String paramName = selectQuerys.getString(selectQuerys.getColumnIndexOrThrow("name"));
            String paramUnitOfMeas = selectQuerys.getString(selectQuerys.getColumnIndexOrThrow("unit_of_measurement"));
            String paramBeginDate = selectQuerys.getString(selectQuerys.getColumnIndexOrThrow("begin_date"));
            String paramEndDate = selectQuerys.getString(selectQuerys.getColumnIndexOrThrow("end_date"));
            String paramBeginTime = selectQuerys.getString(selectQuerys.getColumnIndexOrThrow("begin_time"));
            String paramEndTime = selectQuerys.getString(selectQuerys.getColumnIndexOrThrow("end_time"));
            int paramPeriodicityTime = selectQuerys.getInt(selectQuerys.getColumnIndexOrThrow("periodicity_time"));
            int paramPeriodicityDate = selectQuerys.getInt(selectQuerys.getColumnIndexOrThrow("periodicity_date"));
            long foreignPlanId = selectQuerys.getLong(selectQuerys.getColumnIndexOrThrow("plan_id"));

            Parameter parameter= new Parameter(paramName, paramUnitOfMeas, stringToCalendar(paramBeginDate),stringToCalendar(paramEndDate),
                    paramPeriodicityDate, paramPeriodicityTime, LocalTime.parse(paramBeginTime), LocalTime.parse(paramEndTime));
            parameter.setId(paramId);
            for (int i = 0; i< scheludes.size(); i++)
            {
                if (scheludes.get(i).getId()==foreignPlanId)
                {
                    scheludes.get(i).getParameters().add(parameter);
                    break;
                }
            }

        }
        selectQuerys.close();
        selectQuerys=db.rawQuery("SELECT * FROM 'Result'", null);
        while (selectQuerys.moveToNext())
        {
            long resultId = selectQuerys.getLong(selectQuerys.getColumnIndexOrThrow("id"));
            int resultEntry = selectQuerys.getInt(selectQuerys.getColumnIndexOrThrow("result"));
            String resultDate = selectQuerys.getString(selectQuerys.getColumnIndexOrThrow("date"));
            String resultTime = selectQuerys.getString(selectQuerys.getColumnIndexOrThrow("time"));
            long foreignParamId = selectQuerys.getLong(selectQuerys.getColumnIndexOrThrow("parameter_id"));
            Result result = new Result(resultEntry,stringToCalendar(resultDate), LocalTime.parse(resultTime));
            result.setId(resultId);
            boolean resultAdded = false;
            for (int i = 0; i< scheludes.size(); i++)
            {
                for (int j = 0; j<scheludes.get(i).getParameters().size(); j++)
                {
                    if (scheludes.get(i).getParameters().get(j).getId() == foreignParamId)
                    {
                        scheludes.get(i).getParameters().get(j).getResults().add(result);
                        resultAdded=true;
                        break;

                    }
                }
                if (resultAdded==true)
                {
                    break;
                }
            }
        }
        db.close();
    }
    public void deleteRowPlanDB(long rowIndex)
    {
        db = this.getWritableDatabase();
        db.execSQL("DELETE FROM 'Plan' WHERE id = ?", new Object[]{rowIndex});
        db.close();

    }
    public void deleteRowParameterDB(long rowIndex)
    {
        db = this.getWritableDatabase();
        db.execSQL("DELETE FROM 'Parameter' WHERE id = ?", new Object[]{rowIndex});
        db.close();
    }
    public void deleteRowResultDB(long rowIndex)
    {
        db = this.getWritableDatabase();
        db.execSQL("DELETE FROM 'Result' WHERE id = ?", new Object[]{rowIndex});
        db.close();
    }



    public ArrayList<Plan> getScheludes() {
        return this.scheludes;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE if not exists 'Plan' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' text, 'begin_date' text, 'end_date' text, 'begin_time' text, 'end_time' text);");
        db.execSQL("CREATE TABLE if not exists 'Parameter' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' text, 'unit_of_measurement' text, 'begin_date' text, 'end_date' text, 'periodicity_time' INTEGER, 'begin_time' text, 'end_time' text, 'periodicity_date' INTEGER, 'plan_id' INTEGER, FOREIGN KEY ('plan_id') REFERENCES 'Plan'('id') ON DELETE CASCADE)");
        db.execSQL("CREATE TABLE if not exists 'Result'('id' INTEGER PRIMARY KEY AUTOINCREMENT,'result' INTEGER,'date' text, 'time' text,'parameter_id' INTEGER, FOREIGN KEY('parameter_id') REFERENCES 'Parameter'('id') ON DELETE CASCADE)");

    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.execSQL("PRAGMA foreign_keys = ON;");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
