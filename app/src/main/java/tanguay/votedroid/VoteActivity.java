package tanguay.votedroid;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import tanguay.votedroid.databinding.ActivityGraphiqueBinding;
import tanguay.votedroid.databinding.ActivityVoteBinding;


public class VoteActivity extends AppCompatActivity {
    private ActivityVoteBinding binding;
    Button btnVote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVoteBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);

        btnVote = (Button) findViewById(R.id.btnVote);
        btnVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
