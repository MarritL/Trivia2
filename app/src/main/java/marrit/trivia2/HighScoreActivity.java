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
import android.widget.Button;
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
    private FirebaseRecyclerAdapter adapter;
    private Button mRestart;


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

        // initiate variables
        mDatabase = FirebaseDatabase.getInstance().getReference().child("highScores");
        mRestart = findViewById(R.id.button_restart);

        // attach listener
        mRestart.setOnClickListener(new restartListener());

        // put new highScore in database
        mScore *= -1;       // to get descending order back from query save as negative in database
        HighScore newHighScore = new HighScore(mScore);
        postHighScore(newHighScore);

        // read from database
        readFromDatabase();

        /*Query myHighScoresQuery = mDatabase.orderByChild("highScore").limitToFirst(20);

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
            public void onDataChanged() {
                // Called each time there is a new data snapshot. You may want to use this method
                // to hide a loading spinner or check for the "no documents" state and update your UI.
                // ...



            }

            @Override
            public void onError(DatabaseError e) {
                // Called when there is an error getting data. You may want to update
                // your UI to display an error message to the user.
                // ...

                // Failed to read value
                Log.w("HIGHSCOREACTIVITY", "Failed to read value.", e.toException());

            }






        };

        *//*myHighScoresQuery.addValueEventListener(new ValueEventListener() {
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

                    *//**//*ArrayList<Integer> scoresArrayList = new ArrayList<>();
                    ArrayList<String> namesArrayList = new ArrayList<>();
                    ListIterator iterator = highScoreArrayList.listIterator();
                    while (iterator.hasNext()){
                        scoresArrayList.add(highScore.getHighScore());
                        namesArrayList.add(highScore.getName());*//**//*
                    //}



                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("HIGHSCOREACTIVITY", "Failed to read value.", error.toException());
            }
        });*//*

        mHighScoreRecyclerView.setAdapter(adapter);*/

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

    // used FireBase opensource software from:
    // https://firebaseopensource.com/projects/firebase/firebaseui-android/
    void readFromDatabase() {
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
            public void onDataChanged() {
                // Called each time there is a new data snapshot. You may want to use this method
                // to hide a loading spinner or check for the "no documents" state and update your UI.
                // ...

            }

            @Override
            public void onError(DatabaseError e) {
                // Called when there is an error getting data. You may want to update
                // your UI to display an error message to the user.
                // ...

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
            HighScoreActivity.this.startActivity(intent);

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


}
