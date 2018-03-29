package marrit.trivia2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

public class HighScoreHolder extends RecyclerView.ViewHolder {

    TextView mTVName;
    TextView mTVScore;
    HighScore mHighScore;

    // constructor
    public HighScoreHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.list_item_highscore, parent, false));

        mTVName = itemView.findViewById(R.id.TV_name);
        mTVScore = itemView.findViewById(R.id.TV_score);
    }

    // bind the data to the viewHolder
    public void bind(HighScore highScore) {

        mHighScore = highScore;

        // make score positive again (was needed for ordering the query)
        int score = mHighScore.getHighScore();
        score *= -1;

        // display
        mTVScore.setText(String.valueOf(score));
        mTVName.setText(mHighScore.getName());

    }

}