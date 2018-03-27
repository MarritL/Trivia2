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


public class TriviaHelper {
    public interface Callback {
        void gotQuestion(ArrayList<Question> questionArrayList);
        void gotError(String message);
        /*void gotCategories(ArrayList<String> categories);*/

    }

    // declare variables
    private final Context mContext;
    private Callback mCallback;

    // constructor
    public TriviaHelper(Context context) {
        mContext = context;
    }

    // request questions from API
    void getQuestion(Callback activity, int category) {

        mCallback = activity;

        RequestQueue queue = Volley.newRequestQueue(mContext);

        String mUrl = "http://jservice.io/api/random?count=5";


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, mUrl, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        System.out.println(response);
                        ArrayList<Question> mQuestionsArray = new ArrayList<>();

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                System.out.println(i);
                                JSONObject question = response.getJSONObject(i);

                                Question theQuestion = new Question();
                                String mQuestion = question.getString("question");
                                String mAnswer = question.getString("answer");
                                Integer mValue = question.getInt("value");

                                theQuestion.setQuestion(mQuestion);
                                theQuestion.setCorrectAnswer(mAnswer);
                                theQuestion.setValue(mValue);

                                mQuestionsArray.add(theQuestion);

                                //activity.gotQuestion(theQuestion);
                                mCallback.gotQuestion(mQuestionsArray);
                            }
                        } catch (JSONException e) {
                            System.out.println("JSONException: " + e.getMessage());
                        }


                        //activity.gotQuestion(question);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        System.out.println("Error: " + error.getMessage());
                        //activity.gotError(error.getMessage());
                        mCallback.gotError(error.getMessage());


                    }
                });

        // Access the RequestQueue through your singleton class.
        queue.add(jsonArrayRequest);


    }

    /*void getCategories(Callback activity) {

        mCallback = activity;

        RequestQueue categoriesQueue = Volley.newRequestQueue(mContext);

        String mUrl = "http://jservice.io/api/categorie



    }*/

    /*void getQuestion(Callback activity){

        RequestQueue queue = Volley.newRequestQueue(mContext);

        String url = "http://jservice.io/api/random";

        // Request a JSON response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        *//*try{
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject questionlist = jsonArray.getJSONObject(i);
                                Question question = new Question();
                                question.setQuestion(questionlist.getString("question"));
                                question.setCorrectAnswer(questionlist.getString("answer"));

                            }*//*

                            //JSONObject jsonObject = new JSONObject(response);
                            //JSONObject resultsObj = jsonObject.getJSONObject("0");






                        }
                        *//*catch (JSONException e){
                            e.printStackTrace();
                        }*//*
                    }

                    }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);


    }
*/




}

