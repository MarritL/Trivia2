package marrit.trivia2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class WelcomeActivity extends AppCompatActivity {

    private EditText mETUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Initiate variables
        Button buttonStart = findViewById(R.id.button_start);
        mETUsername = findViewById(R.id.ET_username);

        // Attach listener
        buttonStart.setOnClickListener(new startListener());
    }

    // start game on click
    private class startListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            // check if user gave username
            System.out.println("WELCOMACTIVITY: " + mETUsername.getText().toString());
            String username;
            if (!mETUsername.getText().toString().equals("")) {
                username = mETUsername.getText().toString();
            }
            else{
                username = "Anonymous";
            }

            // start game with username and startscore of 0 points
            int mScore = 0;
            Intent intent = new Intent(WelcomeActivity.this, CategoriesActivity.class);
            Bundle extras = new Bundle();
            extras.putInt("SCORE", mScore);
            extras.putString("USERNAME", username);
            intent.putExtras(extras);
            WelcomeActivity.this.startActivity(intent);
        }
    }
}
