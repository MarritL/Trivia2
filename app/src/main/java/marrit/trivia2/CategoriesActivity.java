package marrit.trivia2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;


public class CategoriesActivity extends AppCompatActivity implements CategoriesHelper.Callback{

    private int mScore;
    private String mUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        Intent intent = getIntent();
        if (intent == null) {
            mScore = 0;
            mUsername = "Anonymous";
        }
        else {
            Bundle extras = intent.getExtras();
            mScore = extras.getInt("SCORE");
            mUsername = extras.getString("USERNAME");
        }

        // get categories from API
        CategoriesHelper categoriesHelper = new CategoriesHelper(this);
        categoriesHelper.getCategories(this);
    }


    @Override
    public void gotCategories(ArrayList<Category> categoryArrayList) {

        // Initiate variables
        Button buttonFirstCategory = findViewById(R.id.button_category1);
        Button buttonSecondCategory = findViewById(R.id.button_category2);

        // Get categories
        Category firstCategory = categoryArrayList.get(0);
        Category secondCategory = categoryArrayList.get(1);

        // Set values
        buttonFirstCategory.setText(firstCategory.getCategory());
        buttonFirstCategory.setTag(firstCategory.getNumber());
        buttonSecondCategory.setText(secondCategory.getCategory());
        buttonSecondCategory.setTag(secondCategory.getNumber());

        // set on click listeners
        buttonFirstCategory.setOnClickListener(new onButtonClickListener());
        buttonSecondCategory.setOnClickListener(new onButtonClickListener());
    }

    @Override
    public void gotError(String message) {
        Toast.makeText(CategoriesActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    class onButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            // go to GameActivity
            Intent intent = new Intent(CategoriesActivity.this, GameActivity.class);
            Bundle extras = new Bundle();
            extras.putInt("SCORE", mScore);
            extras.putString("NUMBER", view.getTag().toString());
            extras.putString("USERNAME", mUsername);
            intent.putExtras(extras);
            CategoriesActivity.this.startActivity(intent);

        }
    }
}
