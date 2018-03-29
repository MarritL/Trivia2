package marrit.trivia2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class WelcomeActivity extends AppCompatActivity {

    //variables
    Button mButtonStart;
    EditText mETUsername;
    String mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Initiate variables
        mButtonStart = findViewById(R.id.button_start);
        mETUsername = findViewById(R.id.ET_username);

        // Attach listener
        mButtonStart.setOnClickListener(new startListener());

    }

    // start game on click
    private class startListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            // check if user gave username
            System.out.println("WELCOMACTIVITY: " + mETUsername.getText().toString());
            if (!mETUsername.getText().toString().equals("")) {
                mUsername = mETUsername.getText().toString();
            }
            else{
                mUsername = "Anonymous";
            }

            // start game with username and startscore of 0 points
            int mScore = 0;
            Intent intent = new Intent(WelcomeActivity.this, CategoriesActivity.class);
            Bundle extras = new Bundle();
            extras.putInt("SCORE", mScore);
            extras.putString("USERNAME", mUsername);
            intent.putExtras(extras);
            WelcomeActivity.this.startActivity(intent);


        }
    }
}
