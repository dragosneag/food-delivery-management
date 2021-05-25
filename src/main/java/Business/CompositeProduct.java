package Business;

import java.util.List;

/**
 * Model class used to represent a compound product.
 * It contains the following variables:
 * - title - the title of each product
 * - productList - list of products
 * */
public class CompositeProduct extends MenuItem {

    private String title;
    private List<MenuItem> productList;

    /**
     * Compound product constructor
     * */
    public CompositeProduct(String title, List<MenuItem> productList) {
        this.title = title;
        this.productList = productList;
    }

    /**
     * price getter
     * @return price - the known price
     * */
    public int computePrice() {
        int price = 0;
        for (MenuItem menuItem : productList) {
            price = price + menuItem.computePrice();
        }
        return price;
    }

    /**
     * rating getter
     * @return rating - the known rating
     * */
    public float computeRating() {
        float rating = 0;
        for (MenuItem menuItem : productList) {
            rating = rating + menuItem.computeRating();
        }
        rating = rating / productList.size();
        return rating;
    }

    /**
     * calories getter
     * @return calories - the known calories
     * */
    public int computeCalories() {
        int calories = 0;
        for (MenuItem menuItem : productList) {
            calories = calories + menuItem.computeCalories();
        }
        return calories;
    }

    /**
     * proteins getter
     * @return proteins - the known proteins
     * */
    public int computeProtein() {
        int proteins = 0;
        for (MenuItem menuItem : productList) {
            proteins = proteins + menuItem.computeProtein();
        }
        return proteins;
    }

    /**
     * fats getter
     * @return fats - the known fats
     * */
    public int computeFat() {
        int fats = 0;
        for (MenuItem menuItem : productList) {
            fats = fats + menuItem.computeFat();
        }
        return fats;
    }

    /**
     * sodium getter
     * @return sodium - the known sodium
     * */
    public int computeSodium() {
        int sodium = 0;
        for (MenuItem menuItem : productList) {
            sodium = sodium + menuItem.computeSodium();
        }
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setProductList(List<MenuItem> productList) {
        this.productList = productList;
    }

    @Override
    public String toString() {
        String menuList = ", Menu{ ";
        String comma = "";
        for (MenuItem menuItem : productList) {
            menuList = menuList + comma + menuItem.getTitle();
            comma = ", ";
        }
        menuList = menuList + "}";
        return super.toString() + menuList;
    }
}
