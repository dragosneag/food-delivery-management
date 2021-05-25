package Data;

import Business.BaseProduct;
import Business.MenuItem;

import java.util.regex.Pattern;

/**
 * Validator class, used to check the format of a product and a client object.
 */
public class Validator {

    private static final String namePattern = "(([A-Z])+\\w+(\\-)?\\s?\\d*\\s?)+";
    private static final String intPattern = "(\\d+)";
    private static final String floatPattern = "(([0-9]*[.])?[0-9]+)";
    private static final String addressPattern = "(([A-Za-z]\\w+[-]?\\s?)+\\.?([0-9]+)?\\,?\\s?\\d*)+";
    private static final String phoneNumberPattern = "^(7[0-8]{1}[0-9]{1}|02[0-9]{2}|03[0-9]{2}){1}?(\\s|\\.|\\-)?([0-9]{3}(\\s|\\.|\\-|)){2}$";

    /***
     * Method that checks if a product is valid
     * @param product - product to check
     */
    public void validateProduct(BaseProduct product) {

        Pattern pattern = Pattern.compile(floatPattern);
        if (!pattern.matcher(String.valueOf(product.computeRating())).matches()) {
            throw new IllegalArgumentException("Rating is not a valid rating!");
        }
        pattern = Pattern.compile(intPattern);
        if (!pattern.matcher(String.valueOf(product.computeCalories())).matches()) {
            throw new IllegalArgumentException("Calories is not a valid calories!");
        }
        if (!pattern.matcher(String.valueOf(product.computeProtein())).matches()) {
            throw new IllegalArgumentException("Proteins is not a valid protein!");
        }
        if (!pattern.matcher(String.valueOf(product.computeFat())).matches()) {
            throw new IllegalArgumentException("Fats is not a valid fats!");
        }
        if (!pattern.matcher(String.valueOf(product.computeSodium())).matches()) {
            throw new IllegalArgumentException("Sodium is not a valid sodium!");
        }
        if (!pattern.matcher(String.valueOf(product.computePrice())).matches()) {
            throw new IllegalArgumentException("Price is not a valid price!");
        }
    }

    /***
     * Method that checks if a client is valid
     * @param client - client to check
     */
    public void validateClient(Client client) {

        Pattern pattern = Pattern.compile(namePattern);
        if (!pattern.matcher(client.getName()).matches()) {
            throw new IllegalArgumentException("Client name is not a valid name!");
        }
        pattern = Pattern.compile(phoneNumberPattern);
        if (!pattern.matcher(String.valueOf(client.getPhone())).matches()) {
            throw new IllegalArgumentException("Phone is not a valid phone!");
        }
        pattern = Pattern.compile(addressPattern);
        if (!pattern.matcher(String.valueOf(client.getAddress())).matches()) {
            throw new IllegalArgumentException("Address is not a valid address!");
        }
    }
}