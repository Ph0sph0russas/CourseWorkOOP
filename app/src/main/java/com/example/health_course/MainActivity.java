package com.example.health_course;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import RVAdapters.NotificationsAdapter;
import entity.Plan;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Plan> scheludes = new ArrayList<>();

    private RecyclerView notificationsListRV;
    private Runnable checkTimeRunnable;
    private Handler handler = new Handler(Looper.getMainLooper());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        App app = (App) getApplicationContext();
        scheludes=app.getScheludes();


        notificationsListRV = findViewById(R.id.NotificationsRV);
        notificationsListRV.setLayoutManager(new LinearLayoutManager(this));


        NotificationsAdapter notificationsAdapterForRV = new NotificationsAdapter(this,scheludes);
        notificationsListRV.setAdapter(notificationsAdapterForRV);


        checkTimeRunnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i<scheludes.size(); i++)
                    for (int j = 0; i<scheludes.get(i).getParameters().size(); j++)
                    {
                        notificationsAdapterForRV.updateNotifications();
                        handler.postDelayed(this,60000);
                    }
            }
        };

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void createNewScheludeBtnClick(View v)
    {
        Intent intent = new Intent(this, CreateNewScheludeActivity.class);
        startActivity(intent);
    }
    public void openScheludesListBtnClick(View v)
    {
        Intent intent = new Intent(this, ScheludeListActivity.class);
        startActivity(intent);
    }
    public void makeNotifsInvincibleBtnClick(View v)
    {

        Button openCreateSchel = findViewById(R.id.createNewScheludesBtn);
        Button openSchelList = findViewById(R.id.openScheludeListButton);


        if (openCreateSchel.getVisibility()==View.VISIBLE)
        {
            openCreateSchel.setVisibility(View.GONE);
        }
        else
        {
            openCreateSchel.setVisibility(View.VISIBLE);
        }
        View divider = findViewById(R.id.divider);
        ConstraintLayout.LayoutParams coordinatesParams = (ConstraintLayout.LayoutParams) notificationsListRV.getLayoutParams();
        if (openSchelList.getVisibility()==View.VISIBLE)
        {
            openSchelList.setVisibility(View.GONE);
            coordinatesParams.topToBottom=divider.getId();
            coordinatesParams.topMargin=0;
            notificationsListRV.setLayoutParams(coordinatesParams);
            notificationsListRV.requestLayout();
        }
        else
        {
            openSchelList.setVisibility(View.VISIBLE);
            coordinatesParams.topToBottom=openCreateSchel.getId();
            coordinatesParams.topMargin=55;
            notificationsListRV.setLayoutParams(coordinatesParams);
            notificationsListRV.requestLayout();
        }

    }

}