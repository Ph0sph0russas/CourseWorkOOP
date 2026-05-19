package RVAdapters;

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
import java.util.ArrayList;

import entity.Parameter;
import entity.Plan;

public class ParametersRVAdapter extends RecyclerView.Adapter<ParametersRVAdapter.ViewHolder>{

    private LayoutInflater inflater;
    private ArrayList<Parameter> parameters;
    public ParametersRVAdapter(Context context, ArrayList<Parameter> parameters) {
        this.parameters=parameters;
        this.inflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public ParametersRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item_param, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ParametersRVAdapter.ViewHolder holder, int position) {

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



    public Plan addAllParametersToPlan(Plan plan, RecyclerView recyclerViewParams)
    {
        for (int i =0; i<parameters.size();i++)
        {
            Parameter parameterToAdd = new Parameter();
            RecyclerView.ViewHolder holder = recyclerViewParams.findViewHolderForAdapterPosition(i);
            if (holder instanceof ViewHolder)
            {

                String perDays= ((ViewHolder) holder).paramPeriodicityDaysView.getText().toString();
                String perTime = ((ViewHolder) holder).paramPeriodicityTimeView.getText().toString();
                parameterToAdd=new Parameter(
                        ((ViewHolder) holder).paramNameView.getText().toString(),
                        ((ViewHolder) holder).paramUnitView.getText().toString(),
                        stringToCalendar(((ViewHolder) holder).paramDateStartView.getText().toString()),
                        stringToCalendar(((ViewHolder) holder).paramDateEndView.getText().toString()),
                        Integer.parseInt(perDays),
                        Integer.parseInt(perTime),
                        LocalTime.parse(((ViewHolder) holder).paramTimeStartView.getText().toString()),
                        LocalTime.parse(((ViewHolder) holder).paramTimeEndView.getText().toString())
                );
            }
            plan.addParameter(parameterToAdd);
        }


        return plan;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        EditText paramNameView, paramUnitView, paramDateStartView, paramDateEndView,
                paramTimeStartView, paramTimeEndView, paramPeriodicityDaysView, paramPeriodicityTimeView;
        Button deleteParamButtonView;
        ViewHolder(View view){
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
