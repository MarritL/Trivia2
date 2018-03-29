package marrit.trivia2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.ListIterator;

public class HighScoreActivity extends AppCompatActivity implements HighScoreHelper.Callback {

    // variables
    int mScore;
    private DatabaseReference mDatabase;
    private RecyclerView mHighScoreRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        // get extra info
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        mScore = extras.getInt("SCORE");

        // set up recyclerView
        mHighScoreRecyclerView = (RecyclerView) findViewById(R.id.RV_highscore);
        mHighScoreRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // TODO: display recyclerView


        // initiate variables
        mDatabase = FirebaseDatabase.getInstance().getReference().child("highScores");

        // put new highScore in database
        HighScore newHighScore = new HighScore(mScore);
        postHighScore(newHighScore);

        // read from database
        Query myHighScoresQuery = mDatabase.orderByChild("highScore").limitToFirst(20);

        FirebaseRecyclerOptions<HighScore> options =
                new FirebaseRecyclerOptions.Builder<HighScore>()
                        .setQuery(myHighScoresQuery, HighScore.class).build();

        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<HighScore, HighScoreHolder>(options) {

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


        };

        myHighScoresQuery.addValueEventListener(new ValueEventListener() {
        //mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                // get highscores


                ArrayList<HighScore> highScoreArrayList = new ArrayList<>();
                for (DataSnapshot ds:dataSnapshot.getChildren()) {
                    HighScore highScore = ds.getValue(HighScore.class);
                    highScoreArrayList.add(highScore);
                    Log.d("HIGHSCOREACTIVITY", "List is: " + highScoreArrayList);
                    System.out.println(highScore.getHighScore());

                    /*ArrayList<Integer> scoresArrayList = new ArrayList<>();
                    ArrayList<String> namesArrayList = new ArrayList<>();
                    ListIterator iterator = highScoreArrayList.listIterator();
                    while (iterator.hasNext()){
                        scoresArrayList.add(highScore.getHighScore());
                        namesArrayList.add(highScore.getName());*/
                    //}



                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("HIGHSCOREACTIVITY", "Failed to read value.", error.toException());
            }
        });

        mHighScoreRecyclerView.setAdapter(adapter);

    }

    public class HighScoreViewHolder extends RecyclerView.ViewHolder {

        public HighScoreViewHolder(View itemV) {
            super(itemV);
        }
    }

    @Override
    public void gotHighScores(ArrayList<Integer> highScoresArrayList) {

    }

    @Override
    public void gotError(String message) {
        Toast.makeText(HighScoreActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    void postHighScore(HighScore highScore) {

        mDatabase.push().setValue(highScore);


    }


}
