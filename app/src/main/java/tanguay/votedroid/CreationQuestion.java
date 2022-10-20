package tanguay.votedroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;

public class CreationQuestion extends AppCompatActivity {

    Button buttonQuestion;

    @Override
    protected void onCreate(@Nullable Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.creation_page);

        buttonQuestion = (Button) findViewById(R.id.btnQuestion);
        buttonQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
