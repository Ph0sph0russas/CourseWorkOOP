package RVAdapters;

import android.content.Context;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.health_course.R;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import entity.Plan;

public class NotificationsAdapter extends  RecyclerView.Adapter<NotificationsAdapter.ViewHolderNotifications>{

    private LayoutInflater inflater;
    private ArrayList<Plan> scheludes;
    private ArrayList<String> notifications=new ArrayList<String>();
    private ArrayList<Boolean> lateNotif = new ArrayList<Boolean>();
    public NotificationsAdapter(Context context, ArrayList<Plan> scheludes){
        this.scheludes=scheludes;
        this.inflater = LayoutInflater.from(context);
        makeNotifications();
    }
    @NonNull
    @Override
    public NotificationsAdapter.ViewHolderNotifications onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.notification_item, parent, false);
        return new NotificationsAdapter.ViewHolderNotifications(view);
    }
    public void updateNotifications()
    {
        makeNotifications();
        notifyDataSetChanged();
    }
    private void makeNotifications()
    {

        notifications.clear();
        lateNotif.clear();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.y");
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");



        Plan schelude;
        for (int i = 0; i<scheludes.size();i++)
        {
            schelude=scheludes.get(i);
            for (int j=0; j<schelude.getParameters().size();j++)
            {

                LocalTime timeToCalculate = LocalTime.of(schelude.getParameters().get(j).getBeginHours().getHour(), schelude.getParameters().get(j).getBeginHours().getMinute());
                Calendar dateToCalculate = (Calendar) schelude.getParameters().get(j).getBeginDate().clone();

                Calendar allInOneTimeParam = (Calendar) dateToCalculate.clone();
                allInOneTimeParam.set(
                        dateToCalculate.get(Calendar.YEAR),
                        dateToCalculate.get(Calendar.MONTH),
                        dateToCalculate.get(Calendar.DAY_OF_MONTH),
                        timeToCalculate.getHour(),
                        timeToCalculate.getMinute(),
                        timeToCalculate.getSecond()
                );



                int toPlusMinutes= schelude.getParameters().get(j).getPeriodToActivateInMinutes();
                int toPlusDays=schelude.getParameters().get(j).getPeriodToActivateInDays();

                Calendar todaysDateFiveMinLong = Calendar.getInstance();
                todaysDateFiveMinLong.add(Calendar.MINUTE, 5);

                Calendar todaysDateFiveMinLess = Calendar.getInstance();
                todaysDateFiveMinLess.add(Calendar.MINUTE, -5);

                Calendar now = Calendar.getInstance();
                for (int k = 0; k<schelude.getParameters().get(j).getPeriodicityDate();k++)
                {
                    for (int l = 0; l < schelude.getParameters().get(j).getPeriodicityTime(); l++) {
                        if (allInOneTimeParam.getTimeInMillis()>=todaysDateFiveMinLess.getTimeInMillis()) {

                            boolean alreadyResulted = false;
                            for (int m = 0; m<schelude.getParameters().get(j).getResults().size(); m++)
                            {
                                Calendar allInOneTimeResult = new GregorianCalendar();
                                allInOneTimeResult.set(
                                        schelude.getParameters().get(j).getResults().get(m).getDate().get(Calendar.YEAR),
                                        schelude.getParameters().get(j).getResults().get(m).getDate().get(Calendar.MONTH),
                                        schelude.getParameters().get(j).getResults().get(m).getDate().get(Calendar.DAY_OF_MONTH),
                                        schelude.getParameters().get(j).getResults().get(m).getHours().getHour(),
                                        schelude.getParameters().get(j).getResults().get(m).getHours().getMinute(),
                                        schelude.getParameters().get(j).getResults().get(m).getHours().getSecond()
                                );

                                if (allInOneTimeResult.getTimeInMillis() <= (allInOneTimeParam.getTimeInMillis()+360000)
                                        && allInOneTimeResult.getTimeInMillis() >= (allInOneTimeParam.getTimeInMillis()-360000)) {
                                    alreadyResulted=true;
                                }

                            }


                            if (allInOneTimeParam.getTimeInMillis()<= now.getTimeInMillis() &&
                             allInOneTimeParam.getTimeInMillis()> todaysDateFiveMinLess.getTimeInMillis() && alreadyResulted==false)
                            {
                                lateNotif.add(true);
                            }
                            else
                            {
                                lateNotif.add(false);
                            }

                            if (alreadyResulted==false)
                            {
                                String formatedDate = dateFormat.format(allInOneTimeParam.getTime());
                                String formatedTime = timeToCalculate.format(timeFormat);
                                notifications.add(schelude.getParameters().get(j).getName() + " \n" +
                                        formatedDate + " " + formatedTime);
                            }



                        }
                        timeToCalculate = timeToCalculate.plusMinutes(toPlusMinutes);
                        allInOneTimeParam.set(
                                dateToCalculate.get(Calendar.YEAR),
                                dateToCalculate.get(Calendar.MONTH),
                                dateToCalculate.get(Calendar.DAY_OF_MONTH),
                                timeToCalculate.getHour(),
                                timeToCalculate.getMinute(),
                                timeToCalculate.getSecond()
                        );
                    }

                    dateToCalculate.add(Calendar.DAY_OF_MONTH, toPlusDays);
                    timeToCalculate = schelude.getParameters().get(j).getBeginHours();
                    allInOneTimeParam.set(
                            dateToCalculate.get(Calendar.YEAR),
                            dateToCalculate.get(Calendar.MONTH),
                            dateToCalculate.get(Calendar.DAY_OF_MONTH),
                            timeToCalculate.getHour(),
                            timeToCalculate.getMinute(),
                            timeToCalculate.getSecond()
                    );

                }


            }

        }

    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolderNotifications holder, int position) {


        String notification = notifications.get(position);
        if (lateNotif.get(position) == true)
        {
            holder.notificationViewText.setTextColor(Color.RED);
        }
        else
        {
            holder.notificationViewText.setTextColor(Color.BLACK);
        }
        holder.notificationViewText.setText(notification);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public static class ViewHolderNotifications extends RecyclerView.ViewHolder {

        TextView notificationViewText;
        ViewHolderNotifications(View view){
            super(view);

            notificationViewText=view.findViewById(R.id.notificationTextView);

        }
    }
}