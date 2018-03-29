package marrit.trivia2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;




public class HighScoreActivity extends AppCompatActivity {

    private String mName;
    private DatabaseReference mDatabase;
    private RecyclerView mHighScoreRecyclerView;
    private FirebaseRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        // get extra info
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        int score = extras.getInt("SCORE");
        mName = extras.getString("USERNAME");

        // set up recyclerView
        mHighScoreRecyclerView = findViewById(R.id.RV_highscore);
        mHighScoreRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // initiate variables
        mDatabase = FirebaseDatabase.getInstance().getReference().child("highScores");
        Button restart = findViewById(R.id.button_restart);

        // attach listener
        restart.setOnClickListener(new restartListener());

        // put new highScore in database
        score *= -1;       // to get descending order back from query save as negative in database
        HighScore newHighScore = new HighScore(mName, score);
        postHighScore(newHighScore);

        // read from database
        readFromDatabase();
    }


    private void postHighScore(HighScore highScore) {
        mDatabase.push().setValue(highScore);
    }

    // used FireBase opensource software from:
    // https://firebaseopensource.com/projects/firebase/firebaseui-android/
    private void readFromDatabase() {
        Query myHighScoresQuery = mDatabase.orderByChild("highScore").limitToFirst(100);

        FirebaseRecyclerOptions<HighScore> options =
                new FirebaseRecyclerOptions.Builder<HighScore>()
                        .setQuery(myHighScoresQuery, HighScore.class).build();

        adapter = new FirebaseRecyclerAdapter<HighScore, HighScoreHolder>(options) {

            // called when new viewHolder is needed
            @Override
            public HighScoreHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

                return new HighScoreHolder(layoutInflater, parent);
            }

            // called when a viewHolder needs to display a HighScore
            @Override
            protected void onBindViewHolder(HighScoreHolder holder, int position, HighScore model) {
                holder.bind(model);

            }

            @Override
            public void onError(DatabaseError e) {
                // Failed to read value
                Log.w("HIGHSCOREACTIVITY", "Failed to read value.", e.toException());
            }
        };
        mHighScoreRecyclerView.setAdapter(adapter);
    }

    // restart the game
    private class restartListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(HighScoreActivity.this, CategoriesActivity.class);
            Bundle extras = new Bundle();
            extras.putInt("SCORE", 0);
            extras.putString("USERNAME", mName);
            intent.putExtras(extras);
            HighScoreActivity.this.startActivity(intent);
        }
    }

    // start FireBase adapter on start
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    // stop FireBase adapter on stop
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    // on back pressed restart the game
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(HighScoreActivity.this, CategoriesActivity.class);
        Bundle extras = new Bundle();
        extras.putInt("SCORE", 0);
        extras.putString("USERNAME", mName);
        intent.putExtras(extras);
        HighScoreActivity.this.startActivity(intent);
    }
}
