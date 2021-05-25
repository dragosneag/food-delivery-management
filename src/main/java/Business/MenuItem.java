package Business;

import java.io.Serializable;

/**
 * Abstract class used to represent a product.
 * */
public abstract class MenuItem implements Serializable {

    public abstract int computePrice();

    public abstract float computeRating();

    public abstract int computeCalories();

    public abstract int computeProtein();

    public abstract int computeFat();

    public abstract int computeSodium();

    public abstract String getTitle();

    @Override
    public int hashCode() {
        return getTitle().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MenuItem) {
            return getTitle().equals(((MenuItem)obj).getTitle());
        }
        return false;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "title='" + getTitle() + '\'' +
                ", rating=" + computeRating() +
                ", calories=" + computeCalories() +
                ", proteins=" + computeProtein() +
                ", fats=" + computeFat() +
                ", sodium=" + computeSodium() +
                ", price=" + computePrice() +
                '}';
    }
}