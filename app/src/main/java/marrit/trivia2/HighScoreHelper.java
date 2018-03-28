package marrit.trivia2;


import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HighScoreHelper {



    public interface Callback {
        void gotHighScores(ArrayList<Integer> highScoresArrayList);
        void gotError(String message);
    }

    // declare variables
    private final Context mContext;
    private Callback mCallback;
//    private DatabaseReference mDatabase;

    // constructor
    public HighScoreHelper(Context context) {
        mContext = context;
//        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    void getHighScores(Callback activity, HighScore highScore) {

        mCallback = activity;

        /*postHighScore(highScore);*/





    }

    /*void postHighScore(HighScore highScore) {

        mDatabase.child("highscores").setValue(highScore);

    }*/
}
