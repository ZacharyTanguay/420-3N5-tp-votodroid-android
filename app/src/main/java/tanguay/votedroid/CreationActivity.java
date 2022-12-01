package tanguay.votedroid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import tanguay.votedroid.databinding.ActivityCreationBinding;

public class CreationActivity extends AppCompatActivity {
    private ActivityCreationBinding binding;
    Button buttonQuestion;
    EditText Question;

    @Override
    protected void onCreate(@Nullable Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        binding = ActivityCreationBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);

        Question = (EditText) findViewById(R.id.Question);
        buttonQuestion = (Button) findViewById(R.id.btnQuestion);
        buttonQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}