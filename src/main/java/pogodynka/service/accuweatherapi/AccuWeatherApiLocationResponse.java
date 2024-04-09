package pogodynka.service.accuweatherapi;

public class AccuWeatherApiLocationResponse {

    private String Key;
    private Integer Rank;

    private String LocalizedName;

    private Region Region;



    private Country Country;

    private GeoPosition GeoPosition;

    public static class Region{
        private String ID;
        private String LocalizedName;
        private String EnglishName;

        public String getID() {
            return ID;
        }

        public String getLocalizedName() {
            return LocalizedName;
        }

        public String getEnglishName() {
            return EnglishName;
        }
    }

    public static class Country {
        private String ID;
        private String LocalizedName;
        private String EnglishName;

        public String getID() {
            return ID;
        }

        public String getLocalizedName() {
            return LocalizedName;
        }

        public String getEnglishName() {
            return EnglishName;
        }
    }

    public static class GeoPosition{
        private Double Latitude;
        private Double Longitude;

        public Double getLatitude() {
            return Latitude;
        }

        public Double getLongitude() {
            return Longitude;
        }
    }


    public String getKey() {
        return Key;
    }

    public GeoPosition getGeoPosition() {
        return GeoPosition;
    }

    public Integer getRank() {
        return Rank;
    }

    public Region getRegion() {
        return Region;
    }

    public Country getCountry() {
        return Country;
    }

    public String getLocalizedName() {
        return LocalizedName;
    }
}
