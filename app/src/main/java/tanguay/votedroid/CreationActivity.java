package tanguay.votedroid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import tanguay.votedroid.bd.BD;
import tanguay.votedroid.databinding.ActivityCreationBinding;
import tanguay.votedroid.databinding.ActivityMainBinding;
import tanguay.votedroid.exceptions.MauvaiseQuestion;
import tanguay.votedroid.modele.VDQuestion;
import tanguay.votedroid.service.Service;

public class CreationActivity extends AppCompatActivity {
    private ActivityCreationBinding binding;
    private Service service;
    private BD maBD;
    private VDQuestion question;
    Button buttonQuestion;
    EditText Question;

    @Override
    protected void onCreate(@Nullable Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        binding = ActivityCreationBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);

        maBD =  Room.databaseBuilder(getApplicationContext(), BD.class, "BDQuestions")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        service = new Service(maBD);


        binding.btnQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent i = new Intent(CreationActivity.this, MainActivity.class);
                    question = new VDQuestion();
                    question.texteQuestion = binding.texteQuestion.getText().toString();
                    service.creerQuestion(question);
                    startActivity(i);

                }catch (MauvaiseQuestion m){
                    Log.e("CREERQUESTION", "Impossible de créer la question : " + m.getMessage());

                    Toast.makeText(getApplicationContext(), m.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}