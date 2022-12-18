package model;

/**
 * Each user has an address.
 */
public class Address {
    private static int idCounter = 0;
    private int id;
    private String country;
    private String region;
    private String city;
    private String street;
    private String number;
    private String postalCode;

    /**
     * Address constructor with parameters.
     */
    public Address(String country, String region, String city, String street, String number, String postalCode) {
        idCounter++;
        this.id = idCounter;
        this.country = country;
        this.region = region;
        this.city = city;
        this.street = street;
        this.number = number;
        this.postalCode = postalCode;
    }

    /**
     * Address default constructor.
     */
    public Address() {
    }

    /**
     * Address to string method.
     */
    @Override
    public String toString() {
        return "\n" + city + ", " + street + ", no. " + number + " (" + postalCode + ')'
                + "\n" + region + ", " + country;
    }

    /**
     * ID getter.
     */
    public int getId() {
        return id;
    }

    /**
     * ID setter.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Country getter.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Country setter.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * City getter.
     */
    public String getCity() {
        return city;
    }

    /**
     * City setter.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Region getter.
     */
    public String getRegion() {
        return region;
    }

    /**
     * Region setter.
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Street getter.
     */
    public String getStreet() {
        return street;
    }

    /**
     * Street setter.
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Number getter.
     */
    public String getNumber() {
        return number;
    }

    /**
     * Number setter.
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * Postal code getter.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Postal code setter.
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

}
