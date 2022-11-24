package tanguay.votedroid;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tanguay.votedroid.modele.VDQuestion;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    public List<VDQuestion> list;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView btnQuestion;
        public ImageButton btnGraph;

        public ViewHolder(LinearLayout view) {
            super(view);
            btnQuestion = view.findViewById(R.id.btnQuestion);
            btnGraph = view.findViewById(R.id.btnGraphique);
        }

        public TextView getTextView() {
            return btnQuestion;
        }
    }

    public QuestionAdapter() {
        list = new ArrayList<>();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public QuestionAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        LinearLayout view = (LinearLayout) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.question_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        VDQuestion questionCourante = list.get(position);
        viewHolder.btnQuestion.setText(questionCourante.texteQuestion);
        viewHolder.btnGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentGraph = new Intent(view.getContext(),Graphique.class);
                view.getContext().startActivity(intentGraph);
            }
        });

        viewHolder.btnQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentVote = new Intent(view.getContext(),Vote.class);
                view.getContext().startActivity(intentVote);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }
}
