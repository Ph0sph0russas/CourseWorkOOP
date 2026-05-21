package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        App app = (App) getApplicationContext();
        scheludes=app.getScheludes();


        RecyclerView notificationsListRV = findViewById(R.id.NotificationsRV);
        notificationsListRV.setLayoutManager(new LinearLayoutManager(this));


        NotificationsAdapter notificationsAdapterForRV = new NotificationsAdapter(this,scheludes);
        notificationsListRV.setAdapter(notificationsAdapterForRV);



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
}