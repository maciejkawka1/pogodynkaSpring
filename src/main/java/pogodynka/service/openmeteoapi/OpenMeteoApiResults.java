package pogodynka.service.openmeteoapi;

public class OpenMeteoApiResults {

    private double latitude;
    private double longitude;
    private double generationtime_ms;
    private int utc_offset_seconds;
    private String timezone;
    private String timezone_abbreviation;
    private int elevation;
    private HourlyUnits hourly_units;
    private HourlyData hourly;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }


    public double getGenerationtime_ms() {
        return generationtime_ms;
    }


    public int getUtc_offset_seconds() {
        return utc_offset_seconds;
    }


    public String getTimezone() {
        return timezone;
    }

    public String getTimezone_abbreviation() {
        return timezone_abbreviation;
    }

    public int getElevation() {
        return elevation;
    }

    public HourlyUnits getHourly_units() {
        return hourly_units;
    }


    public HourlyData getHourly() {
        return hourly;
    }



    public static class HourlyUnits {
        private String time;
        private String temperature_2m;
        private String relative_humidity_2m;
        private String pressure_msl;
        private String wind_speed_10m;
        private String wind_direction_10m;

        public String getTime() {
            return time;
        }

        public String getTemperature_2m() {
            return temperature_2m;
        }

        public String getRelative_humidity_2m() {
            return relative_humidity_2m;
        }

        public String getPressure_msl() {
            return pressure_msl;
        }

        public String getWind_speed_10m() {
            return wind_speed_10m;
        }

        public String getWind_direction_10m() {
            return wind_direction_10m;
        }
    }


    public static class HourlyData {
        private String[] time;
        private Double[] temperature_2m;
        private Double[] relative_humidity_2m;
        private Double[] pressure_msl;
        private Double[] wind_speed_10m;
        private Double[] wind_direction_10m;

        public String[] getTime() {
            return time;
        }

        public Double[] getTemperature_2m() {
            return temperature_2m;
        }

        public Double[] getRelative_humidity_2m() {
            return relative_humidity_2m;
        }

        public Double[] getPressure_msl() {
            return pressure_msl;
        }

        public Double[] getWind_speed_10m() {
            return wind_speed_10m;
        }

        public Double[] getWind_direction_10m() {
            return wind_direction_10m;
        }
    }
}
