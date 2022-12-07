package tanguay.votedroid;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import tanguay.votedroid.bd.BD;
import tanguay.votedroid.databinding.ActivityCreationBinding;
import tanguay.votedroid.databinding.ActivityGraphiqueBinding;
import tanguay.votedroid.modele.VDQuestion;
import tanguay.votedroid.service.Service;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class GraphiqueActivity extends AppCompatActivity {
    private ActivityGraphiqueBinding binding;
    BarChart chart;
    private Service service;
    private BD maBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGraphiqueBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);

        long id = getIntent().getLongExtra("id",-1L);

        maBD =  Room.databaseBuilder(getApplicationContext(), BD.class, "BDQuestions")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        service = new Service(maBD);

        chart = findViewById(R.id.chart);
        TextView moyenne = findViewById(R.id.tVMoyenne);
        TextView ecartType = findViewById(R.id.tVÉcart);
        TextView titreQuestion = findViewById(R.id.tvQuestionSelectionner);
        VDQuestion question = service.questionParId(id);
        moyenne.setText("Moyenne: " + String.format("%.2f", service.moyenneVotes(question)));
        ecartType.setText("Écart type: " + String.format("%.2f", service.distributionVotes(question)));
        titreQuestion.setText(question.texteQuestion);

        int[] votes = service.voteParQuestion(question.idQuestion);


        /* Settings for the graph - Change me if you want*/
        chart.setMaxVisibleValueCount(6);
        chart.setPinchZoom(false);
        chart.setDrawGridBackground(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(new DefaultAxisValueFormatter(0));

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setGranularity(1);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setValueFormatter(new DefaultAxisValueFormatter(0));
        chart.getDescription().setEnabled(false);
        chart.getAxisRight().setEnabled(false);



        /* Data and function call to bind the data to the graph */
        Map<Integer, Integer> dataGraph = new HashMap<Integer, Integer>() {{
            put(0, votes[0]);
            put(1, votes[1]);
            put(2, votes[2]);
            put(3, votes[3]);
            put(4, votes[4]);
            put(5, votes[5]);
        }};
        setData(dataGraph);
    }

    /**
     * methode fournie par le prof pour séparer
     * - la configuration dans le onCreate
     * - l'ajout des données dans le setDate
     * @param datas
     */
    private void setData(Map<Integer, Integer> datas) {
        List<BarEntry> values = new ArrayList<>();
        /* Every bar entry is a bar in the graphic */
        for (Map.Entry<Integer, Integer> key : datas.entrySet()) {
            values.add(new BarEntry(key.getKey(), key.getValue()));
        }

        BarDataSet set1;
        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "Notes");

            set1.setDrawIcons(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(0f);
            data.setBarWidth(.9f);
            chart.setData(data);
        }
    }
}
