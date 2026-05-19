package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

import RVAdapters.ParamsOfOpenedSchelRVAdapter;
import entity.Plan;

public class OpenedScheludeActivity extends AppCompatActivity {
    Plan openedSchelude;
    ParamsOfOpenedSchelRVAdapter adapterForRV;
    private Runnable checkTimeRunnable;
    private Handler handler = new Handler(Looper.getMainLooper());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView schelNameView, schelDatesView, schelTimesView;


        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_opened_schelude);

        App app= (App) getApplicationContext();

        openedSchelude=app.getScheludes().get(app.getPlanOpenNumber());

        schelNameView=findViewById(R.id.scheludeItemNameTextView);
        schelDatesView=findViewById(R.id.datesOfSchelTextView);
        schelTimesView=findViewById(R.id.schelTimeTextView);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.y");
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");


        String formatedDate= dateFormat.format(openedSchelude.getBeginDate().getTime()) + " - " + dateFormat.format(openedSchelude.getEndDate().getTime());

        String formatedTime=openedSchelude.getBeginHours().format(timeFormat) + " - " + openedSchelude.getEndHours().format(timeFormat);



        schelNameView.setText(openedSchelude.getName());
        schelDatesView.setText(formatedDate);
        schelTimesView.setText(formatedTime);

        RecyclerView openedSchelParamsRV= findViewById(R.id.paramsOfOpenedSchelRV);
        openedSchelParamsRV.setLayoutManager(new LinearLayoutManager(this));



        adapterForRV = new ParamsOfOpenedSchelRVAdapter(this, openedSchelude.getParameters());
        openedSchelParamsRV.setAdapter(adapterForRV);


        checkTimeRunnable = new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i<openedSchelude.getParameters().size(); i++)
                {
                    if (adapterForRV.checkTypeBtnAvailability(openedSchelude.getParameters().get(i))==true)
                    {

                        adapterForRV.notifyItemChanged(i);

                    }
                    else
                    {
                        handler.postDelayed(this,60000);

                    }
                }
            }
        };

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    // когда сидим в окне, кнопка могла загореться, когда необходимо записать результаты
    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(checkTimeRunnable, 0);

    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(checkTimeRunnable);
    }

    public void returnToSchelListBtnClick(View v)
    {
        Intent intent = new Intent(this, ScheludeListActivity.class);
        startActivity(intent);
    }
}