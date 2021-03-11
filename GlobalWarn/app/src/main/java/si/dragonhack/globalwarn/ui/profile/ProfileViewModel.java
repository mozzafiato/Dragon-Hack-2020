package si.dragonhack.globalwarn.ui.profile;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    private FirebaseFirestore db;

    public static final String USERNAME_FIELD = "username";
    public static final String ACTIVITY_TYPE_FIELD = "activity";
    public static final String START_TIMESTAMP_FIELD = "start_timestamp";
    public static final String END_TIMESTAMP_FIELD = "end_timestamp";

    public ProfileViewModel() {
        mText = new MutableLiveData<>();

        db = FirebaseFirestore.getInstance();
        getFirebaseActivityData();
    }

    public LiveData<String> getText() {
        return mText;
    }


    private void getFirebaseActivityData() {

        db.collection("activities")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        StringBuilder stringBuilder = new StringBuilder();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Profile", document.getId() + " => " + document.getData());
                                stringBuilder.append(buildString(document) + "\n");
                            }
                            mText.setValue(stringBuilder.toString());
                        } else {
                            Log.w("Profile", "Error getting documents.", task.getException());
                        }
                    }
                });
    }


    /**
        Funkcija služi samo izpisovanju podatkov v Fragment in je zato zelo spaghetti style
     **/
    private String buildString(QueryDocumentSnapshot document) {
        StringBuilder line = new StringBuilder();
        line.append(document.get(USERNAME_FIELD));
        line.append(" ");
        line.append(document.get(ACTIVITY_TYPE_FIELD));
        line.append(" ");
        Timestamp start_timestamp = (Timestamp) document.get(START_TIMESTAMP_FIELD);
        Timestamp end_timestamp = (Timestamp) document.get(END_TIMESTAMP_FIELD);
        assert end_timestamp != null;
        assert start_timestamp != null;
        line.append((end_timestamp.getSeconds() - start_timestamp.getSeconds()) / 60);
        line.append("min");

        return line.toString();
    }
}