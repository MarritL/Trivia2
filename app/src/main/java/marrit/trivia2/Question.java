package marrit.trivia2;

class Question {
    private String mQuestion;
    private String mCorrectAnswer;
    private Integer mValue;

    // constructor
    Question(String question, String correctAnswer, int value) {
        mQuestion = question;
        mCorrectAnswer = correctAnswer;
        mValue = value;
    }

    // getters and setters
    public String getQuestion() {
        return mQuestion;
    }

    public String getCorrectAnswer() {
        return mCorrectAnswer;
    }

    public Integer getValue() {
        return mValue;
    }

    public void setValue(Integer value) {
        mValue = value;
    }
}

