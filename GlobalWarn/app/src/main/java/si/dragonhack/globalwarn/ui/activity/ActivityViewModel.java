package si.dragonhack.globalwarn.ui.activity;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

import si.dragonhack.globalwarn.ui.profile.ProfileFragment;
import si.dragonhack.globalwarn.ui.profile.ProfileViewModel;

public class ActivityViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    private FirebaseFirestore db;

    public ActivityViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is activity fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }


    public void insertActivityData(String username, String type, Timestamp start, Timestamp end) {
        // Create a new user with a first and last name
        Map<String, Object> activity = new HashMap<>();
        activity.put(ProfileViewModel.USERNAME_FIELD, username);
        activity.put(ProfileViewModel.ACTIVITY_TYPE_FIELD, type);
        activity.put(ProfileViewModel.START_TIMESTAMP_FIELD, start);
        activity.put(ProfileViewModel.END_TIMESTAMP_FIELD, end);

        // Add a new document with a generated ID
        db.collection("activities")
                .add(activity)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("Activity", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Activity", "Error adding document", e);
                    }
                });
    }
}