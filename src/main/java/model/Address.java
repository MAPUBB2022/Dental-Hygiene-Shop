package model;

public class Address {
    private static int idCounter = 0;
    private int id;
    private String country;
    private String region;
    private String city;
    private String street;
    private String number;
    private String postalCode;

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

    @Override
    public String toString() {
        return "\n"  + city + ", " + street + ", no. " + number + " (" + postalCode + ')'
                + "\n" + region + ", " + country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

}
