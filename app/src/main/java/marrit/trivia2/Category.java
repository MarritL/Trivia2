package marrit.trivia2;

public class Category {

    private String mCategory;
    private Integer mNumber;


    // constructor
    public Category(String category, int number) {

        mCategory = category;
        mNumber = number;

    }

    // getters and setters
    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public Integer getNumber() {
        return mNumber;
    }

    public void setNumber(Integer number) {
        mNumber = number;
    }
}
