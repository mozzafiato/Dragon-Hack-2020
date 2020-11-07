package si.dragonhack.globalwarn.ui.leaderboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import si.dragonhack.globalwarn.DAOs.User;
import si.dragonhack.globalwarn.R;

public class LeaderBoardFragment extends Fragment {

    private RecyclerView recyclerView;
    private LeaderBoardViewModel leaderViewModel;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter recAdapter;
    private FirebaseFirestore db;
    private List<User> userList=new ArrayList<>();

    private static final String USERNAME_FIELD = "username";
    private static final String START_TIMESTAMP_FIELD = "start_timestamp";
    private static final String END_TIMESTAMP_FIELD = "end_timestamp";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        leaderViewModel = ViewModelProviders.of(this).get(LeaderBoardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        db = FirebaseFirestore.getInstance();
        getFirebaseUsers(root);
        return root;
    }

    private void getFirebaseUsers(final View root) {

        db.collection("activities")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        StringBuilder stringBuilder = new StringBuilder();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                fillUserList(document);
                            }

                            Collections.sort(userList);
                            RecyclerView recyclerView = root.findViewById(R.id.leaderboard_recycler);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(root.getContext());
                            recyclerView.setLayoutManager(layoutManager);
                            RecyclerView.Adapter adapter = new RecyclerViewAdapter(userList);
                            recyclerView.setAdapter(adapter);

                        } else {
                            Log.w("LeaderFragment", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private void fillUserList(QueryDocumentSnapshot document) {
        String username= (String) document.get(USERNAME_FIELD);
        Timestamp start_timestamp = (Timestamp) document.get(START_TIMESTAMP_FIELD);
        Timestamp end_timestamp = (Timestamp) document.get(END_TIMESTAMP_FIELD);
        assert end_timestamp != null;
        assert start_timestamp != null;
        double time=(end_timestamp.getSeconds() - start_timestamp.getSeconds()) / 60;
        if(!checkList(username)){
            User user=new User(username,time);

            userList.add(user);
        }
        else{
            User user=getUser(username);

            double newTime=user.getActivityTime() + time;
            user.setActivityTime(newTime);
        }
    }

    private boolean checkList(String username){
        for (User user : userList) {
            if(user.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    private User getUser(String username){
        for (User user : userList) {
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }
}