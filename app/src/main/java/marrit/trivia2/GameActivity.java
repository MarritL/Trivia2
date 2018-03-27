package marrit.trivia2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity implements TriviaHelper.Callback {

    // variables
    TextView mTVQuestion;
    //String mQuestion;
   // ArrayList<Question> mQuestionArrayList;
    Question question;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // get intent extra
        Intent intent = getIntent();
        //String categoryNumber = intent.getStringExtra("NUMBER");
        Integer categoryNumber = Integer.valueOf(intent.getStringExtra("NUMBER"));

        // Load question
        TriviaHelper triviaHelper = new TriviaHelper(this);
        triviaHelper.getQuestion(this, categoryNumber );

    }

    @Override
    //public void gotQuestion(Question question) {
    public void gotQuestion(ArrayList<Question> questionArrayList) {

        // TODO: do somehting with the question

        // Initiate variables
        mTVQuestion = findViewById(R.id.TV_question);
        //mQuestion = question.getQuestion();
        question = questionArrayList.get(0);


        // Display question
        mTVQuestion.setText(question.getQuestion());


        System.out.println("gameactivity questionArray is: " + questionArrayList);

    }

    @Override
    public void gotError(String message) {

        Toast.makeText(GameActivity.this, message, Toast.LENGTH_SHORT).show();

    }
}

