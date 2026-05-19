package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import RVAdapters.ResultsAdapter;
import entity.Parameter;
import entity.Result;

public class ResultsActivity extends AppCompatActivity {
    Parameter openedParameter;
    RecyclerView resultsRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_results);


        TextView paramNameView, paramDateView, paramTimeView;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.y");
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

        App app= (App) getApplicationContext();

        openedParameter=app.getScheludes().get(app.getPlanOpenNumber()).getParameters().get(app.getParameterOpenNumber());

        paramNameView=findViewById(R.id.paramItemNameTextView);
        paramDateView=findViewById(R.id.datesOfParamTextView);
        paramTimeView=findViewById(R.id.paramTimeTextView);

        String formatedDate= dateFormat.format(openedParameter.getBeginDate().getTime()) + " - " + dateFormat.format(openedParameter.getEndDate().getTime());

        String formatedTime=openedParameter.getBeginHours().format(timeFormat) + " - " + openedParameter.getEndHours().format(timeFormat);


        paramNameView.setText(openedParameter.getName());
        paramDateView.setText(formatedDate);
        paramTimeView.setText(formatedTime);

        resultsRV = findViewById(R.id.resultsRecyclerView);
        resultsRV.setLayoutManager(new LinearLayoutManager(this));

        ResultsAdapter resultsAdapter = new ResultsAdapter(this, openedParameter.getResults());
        resultsRV.setAdapter(resultsAdapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void backToOpenedSchelButtonClick(View v)
    {
        Intent intent = new Intent(this, OpenedScheludeActivity.class);
        startActivity(intent);
    }

    public void graphSwitchClick(View v)
    {

        Switch graphSwitchView = findViewById(R.id.graphSwitch);
        LineChart lineChart = findViewById(R.id.resultsGraph);
        if (graphSwitchView.isChecked())
        {
            resultsRV.setVisibility(View.GONE);
            lineChart.setVisibility(View.VISIBLE);

            ArrayList<Entry> entries = new ArrayList<>();
            ArrayList<Result> resultsFromParam= openedParameter.getResults();

            Calendar allInOneTime = new GregorianCalendar();



            for (int i = 0; i<resultsFromParam.size(); i++)
            {
                allInOneTime.set(
                        resultsFromParam.get(i).getDate().get(Calendar.YEAR),
                        resultsFromParam.get(i).getDate().get(Calendar.MONTH),
                        resultsFromParam.get(i).getDate().get(Calendar.DAY_OF_MONTH),
                        resultsFromParam.get(i).getHours().getHour(),
                        resultsFromParam.get(i).getHours().getMinute(),
                        resultsFromParam.get(i).getHours(). getSecond()
                );
                entries.add(new Entry( (float) allInOneTime.getTimeInMillis(), resultsFromParam.get(i).getResult()));
            }


            LineDataSet lineDataSet = new LineDataSet(entries, "Результаты: " + openedParameter.getName());
            lineDataSet.setColor(Color.BLUE);
            lineDataSet.setValueTextSize(10);

            LineData lineData = new LineData(lineDataSet);

            lineChart.getXAxis().setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    long timeInMillis = (long) value;
                    Date date = new Date(timeInMillis);
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(date);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    return dateFormat.format(calendar.getTime());
                }
            });

            lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);


            lineChart.setData(lineData);
            lineChart.invalidate();


        }
        else
        {
            resultsRV.setVisibility(View.VISIBLE);
            lineChart.setVisibility(View.GONE);
        }
    }
}