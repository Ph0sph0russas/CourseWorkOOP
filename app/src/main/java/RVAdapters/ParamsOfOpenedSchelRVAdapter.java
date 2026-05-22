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


import com.example.health_course.App;
import com.example.health_course.R;
import com.example.health_course.ResultsActivity;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

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

    public boolean checkTypeBtnAvailability(Parameter parameter)
    {
        boolean availabilityCheck = false;

        LocalTime timeToCalculate = LocalTime.of(parameter.getBeginHours().getHour(), parameter.getBeginHours().getMinute());
        Calendar dateToCalculate = (Calendar) parameter.getBeginDate().clone();

        Calendar allInOneTimeParam = (Calendar) dateToCalculate.clone();
        allInOneTimeParam.set(
                dateToCalculate.get(Calendar.YEAR),
                dateToCalculate.get(Calendar.MONTH),
                dateToCalculate.get(Calendar.DAY_OF_MONTH),
                timeToCalculate.getHour(),
                timeToCalculate.getMinute(),
                timeToCalculate.getSecond()
        );

        Calendar allInOneTimeResult = new GregorianCalendar();



        int toPlusMinutes= parameter.getPeriodToActivateInMinutes();
        int toPlusDays=parameter.getPeriodToActivateInDays();



        Calendar todaysDateFiveMinLong = Calendar.getInstance();
        todaysDateFiveMinLong.add(Calendar.MINUTE, 5);

        Calendar todaysDateFiveMinLess = Calendar.getInstance();
        todaysDateFiveMinLess.add(Calendar.MINUTE, -5);

        for (int i = 0; i<parameter.getPeriodicityDate();i++)
        {

            for (int j = 0; j < parameter.getPeriodicityTime(); j++) {
                if (allInOneTimeParam.getTimeInMillis() < (todaysDateFiveMinLong.getTimeInMillis())
                && allInOneTimeParam.getTimeInMillis() > (todaysDateFiveMinLess.getTimeInMillis())) {
                    availabilityCheck=true;
                }

                for (int k = 0; k<parameter.getResults().size(); k++)
                {
                    allInOneTimeResult.set(
                            parameter.getResults().get(k).getDate().get(Calendar.YEAR),
                            parameter.getResults().get(k).getDate().get(Calendar.MONTH),
                            parameter.getResults().get(k).getDate().get(Calendar.DAY_OF_MONTH),
                            parameter.getResults().get(k).getHours().getHour(),
                            parameter.getResults().get(k).getHours().getMinute(),
                            parameter.getResults().get(k).getHours().getSecond()
                    );

                    if (allInOneTimeResult.getTimeInMillis() < (todaysDateFiveMinLong.getTimeInMillis())
                            && allInOneTimeResult.getTimeInMillis() > (todaysDateFiveMinLess.getTimeInMillis())) {
                        availabilityCheck=false;
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

            dateToCalculate.roll(Calendar.DAY_OF_MONTH, toPlusDays);
            timeToCalculate = parameter.getBeginHours();
            allInOneTimeParam.set(
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
                int typedResult;
                try
                {
                    typedResult = Integer.parseInt(fieldToType.getText().toString());
                    Result resultToAdd = new Result(typedResult, Calendar.getInstance(), LocalTime.now());

                    parameters.get(position).getResults().add(resultToAdd);

                    holder.typeResultsBtnView.setEnabled(false);
                }
                catch(NumberFormatException e)
                {
                    dialog.cancel();
                }




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


        holder.deleteParamBtnView.setOnClickListener(view ->{


            AlertDialog.Builder deleteDialog = new AlertDialog.Builder(inflater.getContext());

            deleteDialog.setTitle("Удалить параметр " + parameter.getName() + " ?");

            deleteDialog.setPositiveButton("OK", (dialog, which) -> {

                parameters.remove(position);

                notifyItemRemoved(position);
                notifyItemRangeChanged(position, parameters.size());

            });

            deleteDialog.setNegativeButton("Отмена", (dialog, which) -> {
                dialog.cancel();
            });
            deleteDialog.show();
        });


    }

    @Override
    public int getItemCount() {
        return parameters.size();
    }


    public static class ParamsInOpenedSchelViewHolder extends RecyclerView.ViewHolder {

            TextView paramNameOpenedSchelViewText, unitOfMeasParamOpenedSchelViewText, datesOfParamOpenedSchelViewText, timesOfParamOpenedSchelViewText;
        Button checkResBtnView, typeResultsBtnView, deleteParamBtnView;
        ParamsInOpenedSchelViewHolder(View view){
            super(view);
            paramNameOpenedSchelViewText=view.findViewById(R.id.paramNameOfOpenedSchelTextView);
            unitOfMeasParamOpenedSchelViewText=view.findViewById(R.id.unitOfMeasOfParamInOpenedSchelTextView);
            datesOfParamOpenedSchelViewText=view.findViewById(R.id.datesOfParamInOpenedSchelTextView);
            timesOfParamOpenedSchelViewText=view.findViewById(R.id.timesOfParamInOpenedSchelTextView);

            checkResBtnView=view.findViewById(R.id.checkResBtn);
            typeResultsBtnView=view.findViewById(R.id.typeResultsBtn);
            deleteParamBtnView= view.findViewById(R.id.deleteParamOpenSchelButton);


        }
    }
}
