package com.example.myapplication;

import static com.example.myapplication.App.stringToCalendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalTime;
import java.util.ArrayList;

import RVAdapters.ParametersRVAdapter;
import entity.Parameter;
import entity.Plan;

public class CreateNewScheludeActivity extends AppCompatActivity {
    ArrayList<Parameter> parametersList=new ArrayList<>();
    RecyclerView parametersToAddRecycler;
    ParametersRVAdapter adapterToRecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_new_schelude);

        parametersToAddRecycler = findViewById(R.id.parameterListToAdd);
        parametersToAddRecycler.setLayoutManager(new LinearLayoutManager(this));

        adapterToRecycler = new ParametersRVAdapter(this, parametersList);
        parametersToAddRecycler.setAdapter(adapterToRecycler);
        adapterToRecycler.addParameter();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutForCreateSchelude), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void backFromCreateScheludeBtnClick(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void addParamBtnClick(View v)
    {
        adapterToRecycler.addParameter();
    }
    public void createScheludeBtnClick(View v)
    {

        EditText editName = findViewById(R.id.editNameSchel);
        EditText editStartDate = findViewById(R.id.editTextStartDateSchel);
        EditText editEndDate=findViewById(R.id.editTextEndDateSchel);
        EditText editStartTime = findViewById(R.id.editTextTimeStartSchel);
        EditText editEndTime=findViewById(R.id.editTextTimeEndSchel);


        Plan createdSchelude = new Plan(
                editName.getText().toString(),
                stringToCalendar(editStartDate.getText().toString()),
                stringToCalendar(editEndDate.getText().toString()),
                LocalTime.parse(editStartTime.getText().toString()),
                LocalTime.parse(editEndTime.getText().toString())
        );

        createdSchelude = adapterToRecycler.addAllParametersToPlan(createdSchelude, parametersToAddRecycler);

        App app = (App) getApplicationContext();
        app.getScheludes().add(createdSchelude);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Создание расписания")
                .setMessage("Расписание создано!")
                .setPositiveButton("ОК", (dialog, id) -> {
                    builder.create().dismiss();

                });
        builder.create().show();
    }
    public void makeScheludeInvisibleBtnClick(View v)
    {
        ArrayList<Object> objectsToHide = new ArrayList<>();

        Button createSchelude = findViewById(R.id.createScheludeBtn);
        Button createParam = findViewById(R.id.addParamButton);

        EditText editName = findViewById(R.id.editNameSchel);
        EditText editStartDate = findViewById(R.id.editTextStartDateSchel);
        EditText editEndDate=findViewById(R.id.editTextEndDateSchel);
        EditText editStartTime = findViewById(R.id.editTextTimeStartSchel);
        EditText editEndTime=findViewById(R.id.editTextTimeEndSchel);

        TextView scheludeTitle = findViewById(R.id.schelude);

        View dividerToHide = findViewById(R.id.divider4);



        objectsToHide.add(createSchelude);
        objectsToHide.add(createParam);
        objectsToHide.add(editName);
        objectsToHide.add(editStartDate);
        objectsToHide.add(editEndDate);
        objectsToHide.add(editStartTime);
        objectsToHide.add(editEndTime);
        objectsToHide.add(scheludeTitle);
        objectsToHide.add(dividerToHide);


        for (int i = 0; i<objectsToHide.size(); i++)
        {
            if (objectsToHide.get(i) instanceof Button)
            {
                if (((Button) objectsToHide.get(i)).getVisibility()==View.VISIBLE)
                {
                    ((Button) objectsToHide.get(i)).setVisibility(View.GONE);
                }
                else
                {
                    ((Button) objectsToHide.get(i)).setVisibility(View.VISIBLE);
                }
            }
            else if (objectsToHide.get(i) instanceof EditText)
            {
                if (((EditText) objectsToHide.get(i)).getVisibility()==View.VISIBLE)
                {
                    ((EditText) objectsToHide.get(i)).setVisibility(View.GONE);
                }
                else
                {
                    ((EditText) objectsToHide.get(i)).setVisibility(View.VISIBLE);
                }
            }
            else if(objectsToHide.get(i) instanceof TextView)
            {
                if (((TextView) objectsToHide.get(i)).getVisibility()==View.VISIBLE)
                {
                    ((TextView) objectsToHide.get(i)).setVisibility(View.GONE);
                }
                else
                {
                    ((TextView) objectsToHide.get(i)).setVisibility(View.VISIBLE);
                }
            }
            else if(objectsToHide.get(i) instanceof View)
            {
                View divider2 = findViewById(R.id.divider2);
                ConstraintLayout.LayoutParams coordinatesParams = (ConstraintLayout.LayoutParams) parametersToAddRecycler.getLayoutParams();

                if (((View) objectsToHide.get(i)).getVisibility()==View.VISIBLE)
                {
                    ((View) objectsToHide.get(i)).setVisibility(View.GONE);
                    coordinatesParams.topToBottom=divider2.getId();
                    parametersToAddRecycler.setLayoutParams(coordinatesParams);
                    parametersToAddRecycler.requestLayout();
                }
                else
                {
                    ((View) objectsToHide.get(i)).setVisibility(View.VISIBLE);
                    coordinatesParams.topToBottom=((View)objectsToHide.get(i)).getId();
                    parametersToAddRecycler.setLayoutParams(coordinatesParams);
                    parametersToAddRecycler.requestLayout();
                }
            }
        }

    }

}