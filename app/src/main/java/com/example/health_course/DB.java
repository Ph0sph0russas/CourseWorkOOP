package com.example.health_course;


import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.*;
import android.content.Context;

import java.util.ArrayList;

import entity.Plan;

public class DB extends SQLiteOpenHelper {
    DB(Context context)
    {
        super(context, "health_course.db",null, 1);
    }
    private static ArrayList<Plan> scheludes = new ArrayList<>();
    private static SQLiteDatabase db;
    public static void newTables() throws SQLException{
        db.execSQL("CREATE TABLE if not exists 'Plan' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' text, 'begin_date' text, 'end_date' text, 'begin_time' text, 'end_time' text);");
        db.execSQL("CREATE TABLE if not exists 'Parameter' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' text, 'unit_of_measurement' text, 'begin_date' text, 'end_date' text, 'periodicity_time' INTEGER, 'begin_time' text, 'end_time' text, 'periodicity_date' INTEGER, FOREIGN KEY ('plan_id') REFERENCES 'plan'('id'))");
        db.execSQL("CREATE TABLE if not exists 'Result'('id' INTEGER PRIMARY KEY AUTOINCREMENT,'result' INTEGER,'date' text, 'time' text, FOREIGN KEY('parameter_id') REFERENCES 'Parameter'('id')) ");
    }
    public static long writeScheludeDB(String enteredName, String enteredBeginDate, String enteredEndDate, String enteredBeginTime, String enteredEndTime) throws SQLException
    {
        ContentValues valuesToEnter = new ContentValues();
        valuesToEnter.put("name", enteredName);
        valuesToEnter.put("begin_date", enteredBeginDate);
        valuesToEnter.put("end_date", enteredEndDate);
        valuesToEnter.put("begin_time", enteredBeginTime);
        valuesToEnter.put("end_time", enteredEndTime);
        return db.insert("Plan", null, valuesToEnter);

    }
    public static long writeParameterDB(String enteredName, String enteredUnitOfMeas, String enteredBeginDate, String enteredEndDate, String enteredBeginTime, String enteredEndTime, String enteredPeriodTime, String enteredPeriodDate, long planId) throws SQLException
    {
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
        return db.insert("Parameter", null, valuesToEnter);
    }
    public static long writeResultDB(String enteredName, String enteredDate, String enteredTime, long parameterId) throws SQLException
    {
        ContentValues valuesToEnter = new ContentValues();
        valuesToEnter.put("name", enteredName);
        valuesToEnter.put("date", enteredDate);
        valuesToEnter.put("time", enteredTime);
        valuesToEnter.put("parameter_id", parameterId);
        return db.insert("Result", null, valuesToEnter);

    }
//    public static void readDB() throws SQLException
//    {
//
//        government.clear();
//        result = stab.executeQuery("SELECT * FROM city"); //выборки данных с помощью команды SELECT
//        while(result.next())
//        {
//            City city=new City(result.getString("name"),result.getString("status"),result.getInt("population"), result.getString("profile"));
//            government.add(city);
//            idList.add(result.getInt("no"));
//        }
//        result = stab.executeQuery("SELECT * FROM capital"); //выборки данных с помощью команды SELECT
//        while(result.next())
//        {
//            Capital capital=new Capital(result.getString("name"),result.getString("status"),result.getInt("population"), result.getString("profile"), result.getString("mainGovernment"));
//            government.add(capital);
//            idList.add(result.getInt("no"));
//        }
//        result = stab.executeQuery("SELECT * FROM region"); //выборки данных с помощью команды SELECT
//        while(result.next())
//        {
//            Region region=new Region(result.getString("name"),result.getString("status"),result.getInt("population"),result.getString("specification"));
//            government.add(region);
//            idList.add(result.getInt("no"));
//        }
//    }
//    public static ArrayList<Place> getGovernment()
//    {
//        return government;
//    }
//    public static void deleteRowCityDB(int rowIndex) throws SQLException
//    {
//        int indexInBD=idList.get(rowIndex);
//        editWriteDeleteStat= stab.getConnection().prepareStatement("DELETE FROM City WHERE no=?");
//        editWriteDeleteStat.setInt(1,indexInBD);
//        editWriteDeleteStat.execute();
//        idList.remove(rowIndex);
//    }
//    public static void deleteRowCapitalDB(int rowIndex) throws SQLException
//    {
//        int indexInBD=idList.get(rowIndex);
//        editWriteDeleteStat= stab.getConnection().prepareStatement("DELETE FROM Capital WHERE no=?");
//        editWriteDeleteStat.setInt(1,indexInBD);
//        editWriteDeleteStat.execute();
//        idList.remove(rowIndex);
//    }
//    public static void deleteRowRegionDB(int rowIndex) throws SQLException
//    {
//        int indexInBD=idList.get(rowIndex);
//        editWriteDeleteStat= stab.getConnection().prepareStatement("DELETE FROM Region WHERE no=?");
//        editWriteDeleteStat.setInt(1,indexInBD);
//        editWriteDeleteStat.execute();
//        idList.remove(rowIndex);
//    }
//    public static void editCity(int rowIndex, String enteredName, String enteredStatus, int enteredPopulation, String enteredProfile) throws SQLException
//    {
//        int indexInBD=idList.get(rowIndex);
//        editWriteDeleteStat=stab.getConnection().prepareStatement("UPDATE City SET name = ?, status = ?, population=?,profile=? WHERE no=?");
//        editWriteDeleteStat.setString(1, enteredName);
//        editWriteDeleteStat.setString(2, enteredStatus);
//        editWriteDeleteStat.setInt(3, enteredPopulation);
//        editWriteDeleteStat.setString(4,enteredProfile);
//        editWriteDeleteStat.setInt(5,indexInBD);
//        editWriteDeleteStat.execute();
//    }
//    public static void editCapital(int rowIndex, String enteredName, String enteredStatus, int enteredPopulation, String enteredProfile, String enteredMainGovernment) throws SQLException
//    {
//        int indexInBD=idList.get(rowIndex);
//        editWriteDeleteStat=stab.getConnection().prepareStatement("UPDATE Capital SET name = ?, status = ?, population=?,profile=?, mainGovernment=? WHERE no=?");
//        editWriteDeleteStat.setString(1, enteredName);
//        editWriteDeleteStat.setString(2, enteredStatus);
//        editWriteDeleteStat.setInt(3, enteredPopulation);
//        editWriteDeleteStat.setString(4, enteredProfile);
//        editWriteDeleteStat.setString(5,enteredMainGovernment);
//        editWriteDeleteStat.setInt(6,indexInBD);
//        editWriteDeleteStat.execute();
//    }
//    public static void editRegion(int rowIndex,String enteredName, String enteredStatus, int enteredPopulation, String enteredSpecification) throws SQLException
//    {
//        int indexInBD=idList.get(rowIndex);
//
//        editWriteDeleteStat=stab.getConnection().prepareStatement("UPDATE Region SET name = ?, status = ?, population=?,specification=? WHERE no=?");
//        editWriteDeleteStat.setString(1, enteredName);
//        editWriteDeleteStat.setString(2, enteredStatus);
//        editWriteDeleteStat.setInt(3, enteredPopulation);
//        editWriteDeleteStat.setString(4,enteredSpecification);
//        editWriteDeleteStat.setInt(5,indexInBD);
//        editWriteDeleteStat.execute();
//    }
    public static void closeDB() throws SQLException
    {
        db.close();
    }


    @Override
    public void onCreate(SQLiteDatabase dataBase) {
        dataBase.execSQL("CREATE TABLE if not exists 'Plan' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' text, 'begin_date' text, 'end_date' text, 'begin_time' text, 'end_time' text);");
        dataBase.execSQL("CREATE TABLE if not exists 'Parameter' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' text, 'unit_of_measurement' text, 'begin_date' text, 'end_date' text, 'periodicity_time' INTEGER, 'begin_time' text, 'end_time' text, 'periodicity_date' INTEGER, FOREIGN KEY ('plan_id') REFERENCES 'plan'('id'))");
        dataBase.execSQL("CREATE TABLE if not exists 'Result'('id' INTEGER PRIMARY KEY AUTOINCREMENT,'result' INTEGER,'date' text, 'time' text, FOREIGN KEY('parameter_id') REFERENCES 'Parameter'('id')) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
