package marrit.trivia2;

import java.util.UUID;

public class Question {
    private UUID mId;
    private String mQuestion;
    //private String[] mAnswers;
    private String mCorrectAnswer;
    private Integer mValue;

    /*{
        //instance initializer; runs before any constructor
        mId = null;
        mQuestion = "";
        //mAnswers = "";
        mCorrectAnswer = "";
        mValue = 0;
    }*/

    // constructor
    public Question(String question, String correctAnswer, int value) {
        mQuestion = question;
        //mAnswers = answers;
        mCorrectAnswer = correctAnswer;
        mValue = value;
    }

    // empty constructor
    public Question(){
        mId = UUID.randomUUID();
    }

    // getters and setters
    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String question) {
        mQuestion = question;
    }

    /*public String[] getAnswers() {
        return mAnswers;
    }*/

    /*public void setAnswers(String[] answers) {
        mAnswers = answers;
    }*/

    public String getCorrectAnswer() {
        return mCorrectAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        mCorrectAnswer = correctAnswer;
    }

    public Integer getValue() {
        return mValue;
    }

    public void setValue(Integer value) {
        mValue = value;
    }
}

