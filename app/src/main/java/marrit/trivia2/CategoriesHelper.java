package marrit.trivia2;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class CategoriesHelper {

    public interface Callback {
        void gotCategories(ArrayList<Category> categoryArrayList);
        void gotError(String message);
    }

    // declare variables
    private final Context mContext;
    private Callback mCallback;

    // constructor
    public CategoriesHelper(Context context) {
        mContext = context;
    }

    // request questions from API
    void getCategories(Callback activity) {

        mCallback = activity;

        RequestQueue categoriesQueue = Volley.newRequestQueue(mContext);

        int offset = randInt(1, 18000);

        String mUrl = "http://jservice.io/api/categories?offset="+ offset + "&count=2";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, mUrl, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        System.out.println(response);
                        ArrayList<Category> mCategoriesArray = new ArrayList<>();

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                System.out.println(i);
                                JSONObject category = response.getJSONObject(i);

                                String mCategory = category.getString("title");
                                int mId = category.getInt("clues_count");

                                Category theCategory = new Category(mCategory, mId);

                                mCategoriesArray.add(theCategory);
                            }



                        } catch (JSONException e) {
                            System.out.println("JSONException: " + e.getMessage());
                        }

                        mCallback.gotCategories(mCategoriesArray);
                        //activity.gotQuestion(question);
                    }
                }, new Response.ErrorListener() {

                    // Handle errors
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mCallback.gotError(error.getMessage());
                    }
                });

        categoriesQueue.add(jsonArrayRequest);
    }

    // random integer generator from https://stackoverflow.com/questions/20389890/generating-a-random-number-between-1-and-10-java
    private static int randInt(int min, int max) {

        // Usually this can be a field rather than a method variable
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}
