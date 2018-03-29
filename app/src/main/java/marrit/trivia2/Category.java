package marrit.trivia2;

class Category {

    private final String mCategory;
    private Integer mNumber;

    // constructor
    Category(String category, int number) {
        mCategory = category;
        mNumber = number;
    }

    // getters and setters
    public String getCategory() {
        return mCategory;
    }

    public Integer getNumber() {
        return mNumber;
    }

    public void setNumber(Integer number) {
        mNumber = number;
    }
}
