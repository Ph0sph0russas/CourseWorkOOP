package RVAdapters;

import static com.example.myapplication.App.dateStringCheck;
import static com.example.myapplication.App.stringToCalendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.R;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import entity.Parameter;
import entity.Plan;

public class ParametersRVAdapter extends RecyclerView.Adapter<ParametersRVAdapter.СreateSchelViewHolder>{

    private LayoutInflater inflater;
    private ArrayList<Parameter> parameters;
    public ParametersRVAdapter(Context context, ArrayList<Parameter> parameters) {
        this.parameters=parameters;
        this.inflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public СreateSchelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item_param, parent, false);
        return new СreateSchelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(СreateSchelViewHolder holder, int position) {

        holder.paramNameView.setText("");
        holder.paramUnitView.setText("");
        holder.paramDateStartView.setText("");
        holder.paramDateEndView.setText("");
        holder.paramTimeStartView.setText("");
        holder.paramTimeEndView.setText("");
        holder.paramPeriodicityDaysView.setText("");
        holder.paramPeriodicityTimeView.setText("");

        holder.deleteParamButtonView.setOnClickListener(v -> {

            parameters.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, parameters.size());
        });


    }

    @Override
    public int getItemCount() {
        return parameters.size();
    }

    public void addParameter()
    {
        parameters.add(new Parameter());
        notifyItemInserted(parameters.size()-1);
    }



    public Plan addAllParametersToPlan(Plan plan, RecyclerView recyclerViewParams) throws Exception
    {
        for (int i =0; i<parameters.size();i++)
        {
            Parameter parameterToAdd = new Parameter();
            RecyclerView.ViewHolder holder = recyclerViewParams.findViewHolderForAdapterPosition(i);
            if (holder instanceof СreateSchelViewHolder)
            {
                String paramName = ((СreateSchelViewHolder) holder).paramNameView.getText().toString();

                String unit = ((СreateSchelViewHolder) holder).paramUnitView.getText().toString();

                if (paramName.isEmpty())
                {
                    throw new Exception("Название одного из параметров не может быть пустым.");
                }
                if(unit.isEmpty())
                {
                    throw new Exception("Единицы измерения не могут быть пустыми");
                }


                if (dateStringCheck(((СreateSchelViewHolder) holder).paramDateStartView.getText().toString()) == false)
                {
                    throw new Exception("Дата начала одного из параметров написана неправильно.");
                }
                if (dateStringCheck(((СreateSchelViewHolder) holder).paramDateEndView.getText().toString())==false)
                {
                    throw new Exception("Дата окончания одного из параметров написана неправильно");
                }

                Calendar paramDateStart= stringToCalendar(((СreateSchelViewHolder) holder).paramDateStartView.getText().toString());;


                Calendar paramDateEnd= stringToCalendar(((СreateSchelViewHolder) holder).paramDateEndView.getText().toString());


                LocalTime paramTimeStart;
                try
                {
                    paramTimeStart = LocalTime.parse(((СreateSchelViewHolder) holder).paramTimeStartView.getText().toString());
                }
                catch (DateTimeParseException e)
                {
                    throw new Exception("Время начала одного из параметров записано в неправильной форме!");
                }

                LocalTime paramTimeEnd;
                try
                {
                    paramTimeEnd = LocalTime.parse(((СreateSchelViewHolder) holder).paramTimeEndView.getText().toString());
                }
                catch(DateTimeParseException e)
                {
                    throw new Exception("Время конца одного из параметров записано в неправильной форме!");
                }





                String perDays= ((СreateSchelViewHolder) holder).paramPeriodicityDaysView.getText().toString();

                String perTime = ((СreateSchelViewHolder) holder).paramPeriodicityTimeView.getText().toString();

                int perDaysInteger;
                try
                {
                    perDaysInteger= Integer.parseInt(perDays);
                }
                catch(NumberFormatException e)
                {
                    throw new Exception("Повтор в днях должен быть написан только в виде числа");
                }

                int perTimeInteger;
                try
                {
                    perTimeInteger= Integer.parseInt(perTime);
                }
                catch(NumberFormatException e)
                {
                    throw new Exception("Повтор в часах должен быть написан только в виде числа");
                }


                Calendar allInOneTimeStartSchel = new GregorianCalendar();
                allInOneTimeStartSchel.set(
                        plan.getBeginDate().get(Calendar.YEAR),
                        plan.getBeginDate().get(Calendar.MONTH),
                        plan.getBeginDate().get(Calendar.DAY_OF_MONTH),
                        plan.getBeginHours().getHour(),
                        plan.getBeginHours().getMinute(),
                        plan.getBeginHours().getSecond()
                );

                Calendar allInOneTimeEndSchel = new GregorianCalendar();
                allInOneTimeEndSchel.set(
                        plan.getEndDate().get(Calendar.YEAR),
                        plan.getEndDate().get(Calendar.MONTH),
                        plan.getEndDate().get(Calendar.DAY_OF_MONTH),
                        plan.getEndHours().getHour(),
                        plan.getEndHours().getMinute(),
                        plan.getEndHours().getSecond()
                );


                Calendar allInOneTimeStartParam = new GregorianCalendar();
                allInOneTimeStartParam.set(
                        paramDateStart.get((Calendar.YEAR)),
                        paramDateStart.get((Calendar.MONTH)),
                        paramDateStart.get((Calendar.DAY_OF_MONTH)),
                        paramTimeStart.getHour(),
                        paramTimeStart.getMinute(),
                        paramTimeStart.getSecond()
                );

                Calendar allInOneTimeEndParam = new GregorianCalendar();
                allInOneTimeEndParam.set(
                        paramDateEnd.get(Calendar.YEAR),
                        paramDateEnd.get(Calendar.MONTH),
                        paramDateEnd.get(Calendar.DAY_OF_MONTH),
                        paramTimeEnd.getHour(),
                        paramTimeEnd.getMinute(),
                        paramTimeEnd.getSecond()
                );


                int afterNight =0;
                int endHoursInMinutes = (paramTimeEnd.getHour()*60)+ paramTimeEnd.getMinute();
                int startHoursInMinutes = (paramTimeStart.getHour()*60) + paramTimeStart.getMinute();
                if (endHoursInMinutes< startHoursInMinutes)
                {
                    afterNight=1440;
                }
                endHoursInMinutes = endHoursInMinutes+afterNight;
                if (perTimeInteger > (endHoursInMinutes-startHoursInMinutes))
                {
                    throw new Exception("Повторов в день у одного из параметров не может быть больше чем количество минут между часами");
                }

                long diffInMillis = paramDateEnd.getTimeInMillis()
                        - paramDateStart.getTimeInMillis();
                int diffInDays= (int)(diffInMillis / (24 * 60 * 60 * 1000))+1;

                if (allInOneTimeStartParam.getTimeInMillis()> allInOneTimeEndParam.getTimeInMillis())
                {
                    throw new Exception("Полное время начала параметра не может больше полного времени конца параметра");
                }
                else if ((allInOneTimeStartSchel.getTimeInMillis() >allInOneTimeStartParam.getTimeInMillis()) ||
                         (allInOneTimeEndSchel.getTimeInMillis() < allInOneTimeEndParam.getTimeInMillis()))
                {
                    throw new Exception("Время параметра выходит за пределы расписания");
                }
                else if (perDaysInteger > diffInDays)
                {
                    throw new Exception("Количество повторов в днях не может быть больше, чем интервал между начальной датой и конечной в параметре");
                }
                else if (perDaysInteger==0)
                {
                    throw new Exception("Повторы между днями должны иметь количество большее 0");
                }
                else if (perTimeInteger==0)
                {
                    throw new Exception("Повторы в день должны иметь количество большее 0");
                }
                parameterToAdd=new Parameter(
                        paramName,
                        ((СreateSchelViewHolder) holder).paramUnitView.getText().toString(),
                        paramDateStart,
                        paramDateEnd,
                        perDaysInteger,
                        perTimeInteger,
                        paramTimeStart,
                        paramTimeEnd
                );
            }
            plan.getParameters().add(parameterToAdd);
        }


        return plan;
    }
    public static class СreateSchelViewHolder extends RecyclerView.ViewHolder {

        EditText paramNameView, paramUnitView, paramDateStartView, paramDateEndView,
                paramTimeStartView, paramTimeEndView, paramPeriodicityDaysView, paramPeriodicityTimeView;
        Button deleteParamButtonView;
        СreateSchelViewHolder(View view){
            super(view);

            paramNameView=view.findViewById(R.id.editTextNameParam);
            paramUnitView=view.findViewById(R.id.editTextUnitParam);
            paramDateStartView=view.findViewById(R.id.editTextDateStartParam);
            paramDateEndView=view.findViewById(R.id.editTextDateEndParam);
            paramTimeStartView=view.findViewById(R.id.editTextTimeStartParam);
            paramTimeEndView=view.findViewById(R.id.editTextTimeEndParam);
            paramDateEndView=view.findViewById(R.id.editTextDateEndParam);
            paramPeriodicityDaysView=view.findViewById(R.id.editTextPeriodicityDaysParam);
            paramPeriodicityTimeView=view.findViewById(R.id.editTextPeriodicityTimeParam);
            deleteParamButtonView=view.findViewById(R.id.deleteParamButton);
        }
    }
}
