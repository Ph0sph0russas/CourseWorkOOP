package RVAdapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.health_course.R;

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


        holder.resultDeleteButtonView.setOnClickListener(view ->{

            AlertDialog.Builder deleteDialog = new AlertDialog.Builder(inflater.getContext());

            deleteDialog.setTitle("Удалить результат " + result.getResult() + " ?");

            deleteDialog.setPositiveButton("OK", (dialog, which) -> {

                results.remove(position);

                notifyItemRemoved(position);
                notifyItemRangeChanged(position, results.size());

            });

            deleteDialog.setNegativeButton("Отмена", (dialog, which) -> {
                dialog.cancel();
            });
            deleteDialog.show();


        });

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public static class ResultsViewHolder extends RecyclerView.ViewHolder {

        TextView resultViewText, resultDateViewText, resultTimeViewText;

        Button resultDeleteButtonView;

        ResultsViewHolder(View view){
            super(view);

            resultViewText=view.findViewById(R.id.resultTextView);
            resultDateViewText=view.findViewById(R.id.resultDateTextView);
            resultTimeViewText=view.findViewById(R.id.resultTimeTextView);

            resultDeleteButtonView=view.findViewById(R.id.resultDeleteButton);

        }
    }
}
