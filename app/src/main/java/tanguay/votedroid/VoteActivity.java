package tanguay.votedroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import tanguay.votedroid.bd.BD;
import tanguay.votedroid.databinding.ActivityVoteBinding;
import tanguay.votedroid.exceptions.MauvaisVote;
import tanguay.votedroid.modele.VDQuestion;
import tanguay.votedroid.modele.VDVote;
import tanguay.votedroid.service.Service;


public class VoteActivity extends AppCompatActivity {
    private ActivityVoteBinding binding;
    private Service service;
    private BD maBD;
    private VDVote vote;

    @Override
    protected void onCreate(@Nullable Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        binding = ActivityVoteBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);

        long id = getIntent().getLongExtra("id",-1L);

        maBD =  Room.databaseBuilder(getApplicationContext(), BD.class, "BDQuestions")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        service = new Service(maBD);
        VDQuestion question = service.questionParId(id);
        binding.voteQuestion.setText(question.texteQuestion);

        binding.btnVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent i = new Intent(VoteActivity.this, MainActivity.class);
                    vote = new VDVote();
                    vote.idQuestion = id;
                    vote.votant = binding.votant.getText().toString();
                    service.creerVote(vote);
                    startActivity(i);

                }catch (MauvaisVote m){
                    Log.e("CREERVOTE", "Impossible de créer le vote : " + m.getMessage());

                    Toast.makeText(getApplicationContext(), m.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
