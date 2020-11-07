package si.dragonhack.globalwarn.ui.leaderboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LeaderBoardViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LeaderBoardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is leaderboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}