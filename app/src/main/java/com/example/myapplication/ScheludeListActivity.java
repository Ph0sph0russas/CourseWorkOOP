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

import RVAdapters.ScheludeListRVAdapter;
import entity.Plan;

public class ScheludeListActivity extends AppCompatActivity {
    ArrayList<Plan> scheludes = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_schelude_list);

        App app = (App) getApplicationContext();
        scheludes=app.getScheludes();

        RecyclerView scheludesListRecycler = findViewById(R.id.scheludeListRecycler);
        scheludesListRecycler.setLayoutManager(new LinearLayoutManager(this));

        ScheludeListRVAdapter adapterToSchelListRV = new ScheludeListRVAdapter(this, scheludes);
        scheludesListRecycler.setAdapter(adapterToSchelListRV);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }




    public void backFromOpenScheludesListBtnClick(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}