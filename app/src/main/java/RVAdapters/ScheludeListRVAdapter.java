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



        Plan schelude = scheludes.get(position);
        holder.nameOfScheludeViewText.setText(schelude.getName());



        holder.openScheludeButtonView.setOnClickListener(v -> {

            App app = (App) inflater.getContext().getApplicationContext();
            app.setPlanOpenNumber(position);

            Intent intent = new Intent(inflater.getContext(), OpenedScheludeActivity.class);
            inflater.getContext().startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {
        return scheludes.size();
    }

    public static class ViewHolderScheludeListShow extends RecyclerView.ViewHolder {

        TextView nameOfScheludeViewText;
        Button openScheludeButtonView;

        ViewHolderScheludeListShow(View view){
            super(view);

            nameOfScheludeViewText=view.findViewById(R.id.nameOfScheludeTextView);
            openScheludeButtonView=view.findViewById(R.id.openScheludeButton);

        }
    }
}
