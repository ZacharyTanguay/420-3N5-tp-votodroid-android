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

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    public List<Question> list;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvQuestion;
        public ImageButton btnGraph;

        public ViewHolder(LinearLayout view) {
            super(view);
            tvQuestion = view.findViewById(R.id.tvQuestion);
            btnGraph = view.findViewById(R.id.btnGraphique);
        }

        public TextView getTextView() {
            return tvQuestion;
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
        Question questionCourante = list.get(position);
        viewHolder.tvQuestion.setText(questionCourante.question);
        viewHolder.btnGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAjouter = new Intent(view.getContext(),Graphique.class);
                view.getContext().startActivity(intentAjouter);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }
}
