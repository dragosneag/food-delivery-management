package Business;

/**
 * Model class used to represent a basic product.
 * It contains the following variables:
 * - title - the title of each product
 * - rating - rating of the product
 * - calories - calories of the product
 * - proteins - proteins of the product
 * - fats - fats of the product
 * - sodium - sodium of the product
 * - price - price of the product
 * */
public class BaseProduct extends MenuItem {

    private String title;
    private float rating;
    private int calories;
    private int proteins;
    private int fats;
    private int sodium;
    private int price;

    /**
     * Basic product constructor
     * */
    public BaseProduct(String title, float rating, int calories, int proteins, int fats, int sodium, int price) {
        this.title = title;
        this.rating = rating;
        this.calories = calories;
        this.proteins = proteins;
        this.fats = fats;
        this.sodium = sodium;
        this.price = price;
    }

    /**
     * price getter
     * @return price - the known price
     * */
    public int computePrice() {
        return price;
    }

    /**
     * rating getter
     * @return rating - the known rating
     * */
    public float computeRating() {
        return rating;
    }

    /**
     * calories getter
     * @return calories - the known calories
     * */
    public int computeCalories() {
        return calories;
    }

    /**
     * proteins getter
     * @return proteins - the known proteins
     * */
    public int computeProtein() {
        return proteins;
    }

    /**
     * fats getter
     * @return fats - the known fats
     * */
    public int computeFat() {
        return fats;
    }

    /**
     * sodium getter
     * @return sodium - the known sodium
     * */
    public int computeSodium() {
        return sodium;
    }

    /**
     * title getter
     * @return title - the known title
     * */
    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}