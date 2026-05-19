package RVAdapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.App;
import com.example.myapplication.R;
import com.example.myapplication.ResultsActivity;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

import entity.Parameter;
import entity.Result;

public class ParamsOfOpenedSchelRVAdapter extends RecyclerView.Adapter<ParamsOfOpenedSchelRVAdapter.ParamsInOpenedSchelViewHolder>{

    private LayoutInflater inflater;
    private ArrayList<Parameter> parameters;

    public ParamsOfOpenedSchelRVAdapter(Context context, ArrayList<Parameter> parameters) {
        this.parameters=parameters;
        this.inflater = LayoutInflater.from(context);

    }
    @NonNull
    @Override
    public ParamsOfOpenedSchelRVAdapter.ParamsInOpenedSchelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.param_of_opened_schelude, parent, false);
        return new ParamsInOpenedSchelViewHolder(view);
    }

    private boolean checkTypeBtnAvailability(Parameter parameter)
    {
        boolean availabilityCheck = false;

        LocalTime timeToCalculate = LocalTime.of(parameter.getBeginHours().getHour(), parameter.getBeginHours().getMinute());
        Calendar dateToCalculate = (Calendar) parameter.getBeginDate().clone();

        Calendar allInOneTime = (Calendar) dateToCalculate.clone();
        allInOneTime.set(
                dateToCalculate.get(Calendar.YEAR),
                dateToCalculate.get(Calendar.MONTH),
                dateToCalculate.get(Calendar.DAY_OF_MONTH),
                timeToCalculate.getHour(),
                timeToCalculate.getMinute(),
                timeToCalculate.getSecond()
        );


        int toPlusMinutes= parameter.getPeriodToActivateInMinutes();
        int toPlusDays=parameter.getPeriodToActivateInDays();

        int diffInDays= parameter.getDiffInDays();

        Calendar todaysDateFiveMinLong = Calendar.getInstance();
        todaysDateFiveMinLong.add(Calendar.MINUTE, 5);

        Calendar todaysDateFiveMinLess = Calendar.getInstance();
        todaysDateFiveMinLess.add(Calendar.MINUTE, -5);

        for (int k = 0; k<diffInDays;k++)
        {

            for (int l = 0; l < parameter.getPeriodicityTime(); l++) {
                if (allInOneTime.getTimeInMillis() < (todaysDateFiveMinLong.getTimeInMillis())
                && allInOneTime.getTimeInMillis() > (todaysDateFiveMinLess.getTimeInMillis())) {
                    availabilityCheck=true;
                }
                timeToCalculate = timeToCalculate.plusMinutes(toPlusMinutes);
                allInOneTime.set(
                        dateToCalculate.get(Calendar.YEAR),
                        dateToCalculate.get(Calendar.MONTH),
                        dateToCalculate.get(Calendar.DAY_OF_MONTH),
                        timeToCalculate.getHour(),
                        timeToCalculate.getMinute(),
                        timeToCalculate.getSecond()
                );
            }

            dateToCalculate.roll(Calendar.DAY_OF_MONTH, toPlusDays);
            timeToCalculate = parameter.getBeginHours();
            allInOneTime.set(
                    dateToCalculate.get(Calendar.YEAR),
                    dateToCalculate.get(Calendar.MONTH),
                    dateToCalculate.get(Calendar.DAY_OF_MONTH),
                    timeToCalculate.getHour(),
                    timeToCalculate.getMinute(),
                    timeToCalculate.getSecond()
            );
        }

        return availabilityCheck;

    }

    @Override
    public void onBindViewHolder(ParamsOfOpenedSchelRVAdapter.ParamsInOpenedSchelViewHolder holder, int position) {


        Parameter parameter = parameters.get(position);


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.y");
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");


        String formatedDate= dateFormat.format(parameter.getBeginDate().getTime()) + " - " + dateFormat.format(parameter.getEndDate().getTime());

        String formatedTime=parameter.getBeginHours().format(timeFormat) + " - " + parameter.getEndHours().format(timeFormat);

        holder.paramNameOpenedSchelViewText.setText(parameter.getName());
        holder.unitOfMeasParamOpenedSchelViewText.setText(parameter.getUnitOfMeasurement());
        holder.datesOfParamOpenedSchelViewText.setText(formatedDate);
        holder.timesOfParamOpenedSchelViewText.setText(formatedTime);


        holder.typeResultsBtnView.setEnabled(checkTypeBtnAvailability(parameters.get(position)));



        holder.typeResultsBtnView.setOnClickListener(view -> {

            AlertDialog.Builder enterResultDialog = new AlertDialog.Builder(inflater.getContext());

            EditText fieldToType = new EditText(inflater.getContext());
            fieldToType.setInputType(InputType.TYPE_CLASS_NUMBER);
            enterResultDialog.setView(fieldToType);
            enterResultDialog.setTitle("Введите значение: ");


            enterResultDialog.setPositiveButton("OK", (dialog, which) -> {

                int typedResult = Integer.parseInt(fieldToType.getText().toString());

                Result resultToAdd = new Result(typedResult, Calendar.getInstance(), LocalTime.now());

                parameters.get(position).addResult(resultToAdd);

                App app = (App) inflater.getContext().getApplicationContext();
                app.getScheludes().get(app.getPlanOpenNumber()).getParameters().get(position).addResult(resultToAdd);
                holder.typeResultsBtnView.setEnabled(false);

            });

            enterResultDialog.setNegativeButton("Отмена", (dialog, which) -> {
                dialog.cancel();
            });
            enterResultDialog.show();

        });


        holder.checkResBtnView.setOnClickListener(view ->{

            App app = (App) inflater.getContext().getApplicationContext();
            app.setParameterOpenNumber(position);


            Intent intent = new Intent(inflater.getContext(), ResultsActivity.class);
            inflater.getContext().startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {
        return parameters.size();
    }


    public static class ParamsInOpenedSchelViewHolder extends RecyclerView.ViewHolder {

        TextView paramNameOpenedSchelViewText, unitOfMeasParamOpenedSchelViewText, datesOfParamOpenedSchelViewText, timesOfParamOpenedSchelViewText;
        Button checkResBtnView, typeResultsBtnView;
        ParamsInOpenedSchelViewHolder(View view){
            super(view);
            paramNameOpenedSchelViewText=view.findViewById(R.id.paramNameOfOpenedSchelTextView);
            unitOfMeasParamOpenedSchelViewText=view.findViewById(R.id.unitOfMeasOfParamInOpenedSchelTextView);
            datesOfParamOpenedSchelViewText=view.findViewById(R.id.datesOfParamInOpenedSchelTextView);
            timesOfParamOpenedSchelViewText=view.findViewById(R.id.timesOfParamInOpenedSchelTextView);

            checkResBtnView=view.findViewById(R.id.checkResBtn);
            typeResultsBtnView=view.findViewById(R.id.typeResultsBtn);


        }
    }
}
