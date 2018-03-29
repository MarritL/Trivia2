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


class TriviaHelper {
    public interface Callback {
        void gotQuestion(ArrayList<Question> questionArrayList, ArrayList<String> answerArrayList);
        void gotError(String message);
    }

    // declare variables
    private final Context mContext;
    private Callback mCallback;

    // constructor
    TriviaHelper(Context context) {
        mContext = context;
    }

    // request questions from API
    void getQuestion(Callback activity, int category) {

        mCallback = activity;

        RequestQueue queue = Volley.newRequestQueue(mContext);

        String mUrl = "http://jservice.io/api/clues?category="+ category;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, mUrl, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        ArrayList<Question> mQuestionsArray = new ArrayList<>();
                        ArrayList<String> mAnswersArray = new ArrayList<>();

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject question = response.getJSONObject(i);

                                String mQuestion = question.getString("question");
                                String mAnswer = question.getString("answer");

                                // set default value on 100 if value not returned by API
                                Integer mValue;
                                if (question.isNull("value")) {
                                    mValue = 100;
                                }
                                else {
                                    mValue = question.getInt("value");
                                }

                                Question theQuestion = new Question(mQuestion, mAnswer, mValue);

                                mQuestionsArray.add(theQuestion);

                                // create an answers array to make questions multiple choice
                                mAnswersArray.add(mAnswer);
                            }

                        } catch (JSONException e) {
                            System.out.println("JSONException: " + e.getMessage());
                        }

                        mCallback.gotQuestion(mQuestionsArray, mAnswersArray);

                    }
                }, new Response.ErrorListener() {

                    // Handle error
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mCallback.gotError(error.getMessage());
                    }
                });

        queue.add(jsonArrayRequest);
    }
}

