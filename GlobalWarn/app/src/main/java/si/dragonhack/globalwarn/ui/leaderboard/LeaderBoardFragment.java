package si.dragonhack.globalwarn.ui.leaderboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import si.dragonhack.globalwarn.R;

public class LeaderBoardFragment extends Fragment {

    private LeaderBoardViewModel leaderViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        leaderViewModel =
                ViewModelProviders.of(this).get(LeaderBoardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        final TextView textView = root.findViewById(R.id.text_leaderboard);
        leaderViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}