package tanguay.votedroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import tanguay.votedroid.bd.BD;
import tanguay.votedroid.databinding.ActivityMainBinding;
import tanguay.votedroid.exceptions.MauvaiseQuestion;
import tanguay.votedroid.modele.VDQuestion;
import tanguay.votedroid.service.Service;

public class MainActivity extends AppCompatActivity {
    private Service service;
    private BD maBD;
    private ActivityMainBinding binding;

    Button buttonAjouter;
    QuestionAdapter adapter;
    ImageButton buttonGraphique;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);

        maBD =  Room.databaseBuilder(getApplicationContext(), BD.class, "BDQuestions")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        service = new Service(maBD);
        binding.btnAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, CreationActivity.class);
                startActivity(i);
            }
        });
        this.initRecycler();
        this.remplirRecycler();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_supprimerQuestions) {
            Toast.makeText(getApplicationContext(),"Supprimer toutes les questions",Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.action_supprimerVotes) {
            Toast.makeText(getApplicationContext(),"Supprimer tous les votes",Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initRecycler() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new QuestionAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void remplirRecycler() {
        adapter.list.clear();
        adapter.list.addAll(service.toutesLesQuestions());
        adapter.notifyDataSetChanged();
    }
}