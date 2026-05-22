package RVAdapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.health_course.App;
import com.example.health_course.OpenedScheludeActivity;
import com.example.health_course.R;

import java.util.ArrayList;

import entity.Plan;

public class ScheludeListRVAdapter extends  RecyclerView.Adapter<ScheludeListRVAdapter.ViewHolderScheludeListShow>{

    private LayoutInflater inflater;
    private ArrayList<Plan> scheludes;
    public ScheludeListRVAdapter(Context context, ArrayList<Plan> scheludes){
        this.scheludes=scheludes;
        this.inflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public ScheludeListRVAdapter.ViewHolderScheludeListShow onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_schelude, parent, false);
        return new ScheludeListRVAdapter.ViewHolderScheludeListShow(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderScheludeListShow holder, int position) {

        App app = (App) inflater.getContext().getApplicationContext();

        Plan schelude = scheludes.get(position);
        holder.nameOfScheludeViewText.setText(schelude.getName());


        holder.openScheludeButtonView.setOnClickListener(v -> {


            app.setPlanOpenNumber(position);

            Intent intent = new Intent(inflater.getContext(), OpenedScheludeActivity.class);
            inflater.getContext().startActivity(intent);

        });

        holder.deleteScheludeButtonView.setOnClickListener(v -> {

            AlertDialog.Builder deleteDialog = new AlertDialog.Builder(inflater.getContext());

            deleteDialog.setTitle("Удалить расписание " + schelude.getName() + " ?");

            deleteDialog.setPositiveButton("OK", (dialog, which) -> {

                app.getDataBase().deleteRowPlanDB(scheludes.get(position).getId());
                scheludes.remove(position);

                notifyItemRemoved(position);
                notifyItemRangeChanged(position, scheludes.size());

            });

            deleteDialog.setNegativeButton("Отмена", (dialog, which) -> {
                dialog.cancel();
            });
            deleteDialog.show();


        });

    }

    @Override
    public int getItemCount() {
        return scheludes.size();
    }

            public static class ViewHolderScheludeListShow extends RecyclerView.ViewHolder {

        TextView nameOfScheludeViewText;
        Button openScheludeButtonView, deleteScheludeButtonView;

        ViewHolderScheludeListShow(View view){
            super(view);

            nameOfScheludeViewText=view.findViewById(R.id.nameOfScheludeTextView);
            openScheludeButtonView=view.findViewById(R.id.openScheludeButton);
            deleteScheludeButtonView=view.findViewById(R.id.deleteSchelButton);

        }
    }
}
