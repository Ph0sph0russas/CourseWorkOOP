package entity;

import java.time.LocalTime;
import java.util.Calendar;

public class Result {
    private int result;
    private Calendar date;
    private LocalTime hours;
    public Result(int result, Calendar date, LocalTime hours)
    {
        this.result=result;
        this.date=date;
        this.hours=hours;
    }

    public int getResult() {
        return this.result;
    }

    public Calendar getDate() {
        return this.date;
    }

    public LocalTime getHours() {
        return this.hours;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public void setHours(LocalTime hours) {
        this.hours = hours;
    }
}
