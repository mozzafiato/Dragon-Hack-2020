package si.dragonhack.globalwarn.ui.leaderboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import si.dragonhack.globalwarn.DAOs.User;
import si.dragonhack.globalwarn.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CardViewHolder> {
    List<User> users;
    private Context context;


    public RecyclerViewAdapter(List<User> users){
        this.users=users;
    }

    class CardViewHolder extends RecyclerView.ViewHolder {
        public TextView itemTime;
        public TextView itemTitle;

        public CardViewHolder(View itemView) {
            super(itemView);
            itemTime =itemView.findViewById(R.id.leaderboard_time);
            itemTitle=itemView.findViewById(R.id.leaderboard_title);
        }
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_leaderboard,viewGroup, false);
        CardViewHolder cardViewHolder=new CardViewHolder(view);
        context = viewGroup.getContext();
        return cardViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull CardViewHolder viewHolder, int position) {
        viewHolder.itemTitle.setText(users.get(position).getUsername());
        viewHolder.itemTime.setText(Double.toString(users.get(position).getActivityTime()));

//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, DetailsActivity.class);
//                intent.putExtra("sentFrom",sentFrom);
//                intent.putExtra("id",recipes.get(position).getIdMeal());
//                context.startActivity(intent);
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return users.size();
    }
}