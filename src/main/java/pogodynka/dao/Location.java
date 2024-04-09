package pogodynka.dao;



import pogodynka.repository.CreationException;

import java.util.UUID;

public class Location {

    private UUID id;
    private double longtitude; // długość geograficzna
    private double latitude; // szerokość geograficzna;
    private String city;
    private String region;
    private String country;


    public Location(UUID id,
                    double longtitude,
                    double latitude,
                    String city,
                    String region,
                    String country) throws CreationException {
        super();

        if (id == null) id = UUID.randomUUID();
        checkValues(id, longtitude, latitude, city, region, country);

        this.id = id;
        this.longtitude = longtitude;
        this.latitude = latitude;
        this.city = city;
        this.region = region;
        this.country = country;
    }

    public void setId(UUID id) {
        this.id = id;
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


    public UUID getId() {
        return id;
    }


    public double getLongtitude() {
        return longtitude;
    }


    public double getLatitude() {
        return latitude;
    }


    public String getCountry() {
        return country;
    }


    private void checkValues(UUID id,
                             double longtitude,
                             double latitude,
                             String city,
                             String region,
                             String country) throws CreationException {

        if (longtitude < -180 || longtitude > 180) cee("Nieprawdiłowa długość geograficzna");
        if (latitude < -90 || latitude > 90) cee("Nieprawdiłowa szerokość geograficzna");
        if (city == null || city.length() == 0) cee("Nazwa miasta nie może być pusta");
        if (country == null || country.length() == 0) cee("Nazwa kraju nie może być pusta");
    }


    private void cee(String msg) throws CreationException {
        throw new CreationException(msg);
    }


    @Override
    public String toString() {
        return "Location [id=" + id
                + ", longtitude=" + longtitude
                + ", latitude=" + latitude
                + ", city=" + city
                + ", region=" + region
                + ", country=" + country + "]";
    }
}