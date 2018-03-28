package marrit.trivia2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HighScoreActivity extends AppCompatActivity implements HighScoreHelper.Callback {

    // variables
    int mScore;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        // get extra info
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        mScore = extras.getInt("SCORE");

        // initiate helper
        //HighScoreHelper highScoreHelper = new HighScoreHelper(this);

        // initiate variables
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // put new highScore in database
        HighScore newHighScore = new HighScore(mScore);
        postHighScore(newHighScore);

        // read from database
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                // get highscores
                ArrayList<HighScore> highScoreArrayList = new ArrayList<>();
                //for (DataSnapshot ds:dataSnapshot.child("highScores").getChildren()) {
                for (DataSnapshot ds:dataSnapshot.getChildren()) {
                    //HighScore highScore = ds.getValue(HighScore.class);
                    HighScore highScore = ds.child("highScores").getValue(HighScore.class);
                    highScoreArrayList.add(highScore);
                    Log.d("HIGHSCOREACTIVITY", "List is: " + highScoreArrayList);
                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("HIGHSCOREACTIVITY", "Failed to read value.", error.toException());
            }
        });

    }

    @Override
    public void gotHighScores(ArrayList<Integer> highScoresArrayList) {

    }

    @Override
    public void gotError(String message) {
        Toast.makeText(HighScoreActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    void postHighScore(HighScore highScore) {

        mDatabase.child("highScores").setValue(highScore);

    }
}
