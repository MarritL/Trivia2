package marrit.trivia2;

class HighScore {

    // variables
    private String mName;
    private int mHighScore;

    public HighScore() {
        // Default constructor required for calls to FireBase DataSnapshot.getValue(HighScore.class)
    }

    // constructor
    HighScore(String name, int highScore) {
        mName = name;
        mHighScore = highScore;
    }


    // getters and setters
    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getHighScore() {
        return mHighScore;
    }

    public void setHighScore(int highScore) {
        mHighScore = highScore;
    }
}
