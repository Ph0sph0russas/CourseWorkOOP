package RVAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.App;
import com.example.myapplication.OpenedScheludeActivity;
import com.example.myapplication.R;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import entity.Result;

public class ResultsAdapter extends  RecyclerView.Adapter<ResultsAdapter.ResultsViewHolder>{

    private LayoutInflater inflater;
    private ArrayList<Result> results;

    public ResultsAdapter(Context context, ArrayList<Result> results){
        this.inflater = LayoutInflater.from(context);
        this.results= results;

    }
    @NonNull
    @Override
    public ResultsAdapter.ResultsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_result, parent, false);
        return new ResultsAdapter.ResultsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultsViewHolder holder, int position) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.y");
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");


        Result result = results.get(position);

        holder.resultViewText.setText(Integer.toString(result.getResult()));
        holder.resultDateViewText.setText(dateFormat.format(result.getDate().getTime()));
        holder.resultTimeViewText.setText(result.getHours().format(timeFormat));



    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public static class ResultsViewHolder extends RecyclerView.ViewHolder {

        TextView resultViewText, resultDateViewText, resultTimeViewText;

        ResultsViewHolder(View view){
            super(view);

            resultViewText=view.findViewById(R.id.resultTextView);
            resultDateViewText=view.findViewById(R.id.resultDateTextView);
            resultTimeViewText=view.findViewById(R.id.resultTimeTextView);

        }
    }
}
