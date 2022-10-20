package tanguay.votedroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;

import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button buttonAjouter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonAjouter = (Button) findViewById(R.id.btnAjouter);
        buttonAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAjouter = new Intent(MainActivity.this,CreationQuestion.class);
                startActivity(intentAjouter);
            }
        });
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
}