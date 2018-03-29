package marrit.trivia2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class GameActivity extends AppCompatActivity implements TriviaHelper.Callback {

    // variables
    private Question question;
    private String mCorrectAnswer;
    private int mScore;
    private String mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // get intent extra
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Integer categoryNumber = Integer.valueOf(extras.getString("NUMBER"));
        mScore = extras.getInt("SCORE");
        mName = extras.getString("USERNAME");

        // Load question
        TriviaHelper triviaHelper = new TriviaHelper(this);
        triviaHelper.getQuestion(this, categoryNumber );

        // Initiate variables
        TextView userScore = findViewById(R.id.TV_userscore);
        userScore.setText(String.valueOf(mScore));
        TextView userName = findViewById(R.id.TV_username);
        userName.setText(mName);
    }

    @Override
    //public void gotQuestion(Question question) {
    public void gotQuestion(ArrayList<Question> questionArrayList, ArrayList<String> answersArrayList) {

        // Initiate variables
        TextView TVQuestion = findViewById(R.id.TV_question);
        TextView TVValue = findViewById(R.id.TV_value);
        Button buttonAnswer1 = findViewById(R.id.button_answer1);
        Button buttonAnswer2 = findViewById(R.id.button_answer2);
        Button buttonAnswer3 = findViewById(R.id.button_answer3);
        Button buttonAnswer4 = findViewById(R.id.button_answer4);

        // check if there are at least 4 answers in list
        if (questionArrayList.size() < 4) {

            // apologies
            Toast.makeText(GameActivity.this,"Sorry, something went wrong",
                    Toast.LENGTH_SHORT).show();

            // let the user choose another category
            Intent intent = new Intent(GameActivity.this, CategoriesActivity.class);
            GameActivity.this.startActivity(intent);
        }

        // Display question and it's value
        question = questionArrayList.get(0);
        TVQuestion.setText(question.getQuestion());
        TVValue.setText(String.valueOf(question.getValue()));
        mCorrectAnswer = question.getCorrectAnswer();

        // create list with random answers (include the right answer)
        answersArrayList.remove(0);     // remove correct answer from ArrayList
        Collections.shuffle(answersArrayList);      // shuffle answers

        // put shuffled answers and correct answer in new ArrayList
        ArrayList<String> displayAnswersArrayList = new ArrayList<>();
        displayAnswersArrayList.add(answersArrayList.get(0));
        displayAnswersArrayList.add(answersArrayList.get(1));
        displayAnswersArrayList.add(answersArrayList.get(2));
        displayAnswersArrayList.add(mCorrectAnswer);

        Collections.shuffle(displayAnswersArrayList);   // shuffle again

        // Display answers
        buttonAnswer1.setText(displayAnswersArrayList.get(0));
        buttonAnswer1.setTag(displayAnswersArrayList.get(0));
        buttonAnswer2.setText(displayAnswersArrayList.get(1));
        buttonAnswer2.setTag(displayAnswersArrayList.get(1));
        buttonAnswer3.setText(displayAnswersArrayList.get(2));
        buttonAnswer3.setTag(displayAnswersArrayList.get(2));
        buttonAnswer4.setText(displayAnswersArrayList.get(3));
        buttonAnswer4.setTag(displayAnswersArrayList.get(3));

        // attach listeners on buttons
        buttonAnswer1.setOnClickListener(new checkAnswer());
        buttonAnswer2.setOnClickListener(new checkAnswer());
        buttonAnswer3.setOnClickListener(new checkAnswer());
        buttonAnswer4.setOnClickListener(new checkAnswer());

    }

    // handle error
    @Override
    public void gotError(String message) {
        Toast.makeText(GameActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    // when button clicked check if answer is right
    class checkAnswer implements View.OnClickListener{

        @Override
        public void onClick(View view) {

            // check answer
            String clicked = view.getTag().toString();
            if (clicked.equals(mCorrectAnswer)) {

                Toast.makeText(GameActivity.this,"correct", Toast.LENGTH_SHORT).show();

                // update score
                System.out.println(mScore);
                mScore += question.getValue();
                System.out.println(mScore);

                // choose category for next question
                Intent intent = new Intent(GameActivity.this, CategoriesActivity.class);
                Bundle extras = new Bundle();
                extras.putInt("SCORE", mScore);
                extras.putString("USERNAME", mName);
                intent.putExtras(extras);
                GameActivity.this.startActivity(intent);
            }
            else {
                // do something else
                Toast.makeText(GameActivity.this,"wrong", Toast.LENGTH_SHORT).show();

                // Go to highscore activity
                Intent intent = new Intent(GameActivity.this, HighScoreActivity.class);
                Bundle extras = new Bundle();
                extras.putInt("SCORE", mScore);
                extras.putString("USERNAME", mName);
                intent.putExtras(extras);
                GameActivity.this.startActivity(intent);
            }
        }
    }





}

