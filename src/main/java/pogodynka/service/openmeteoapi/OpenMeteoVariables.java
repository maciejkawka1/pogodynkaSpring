package pogodynka.service.openmeteoapi;

public enum OpenMeteoVariables {
    TEMPERATURE("temperature_2m","Temperature (2 m)","°C"),
    RELATIVE_HUMIDITY("relative_humidity_2m","Relative Humidity (2 m)","%"),
    SEA_LEVEL_PRESSURE("pressure_msl","Sealevel Pressure","hPa"),
    WIND_SPEED("wind_speed_10m","Wind Speed (10 m)","km/h"),
    WIND_DIRECTION("wind_direction_10m","Wind Direction (10 m)","°");


    private String urlCode;
    private String userInfo;

    private String unit;

    OpenMeteoVariables(String urlCode, String userInfo, String unit){
        this.urlCode = urlCode;
        this.userInfo = userInfo;
        this.unit = unit;
    }

    public String getUrlCode(){
        return this.urlCode;
    }

    public String getUserInfo(){
        return this.userInfo;
    }

    public String getUnit() {
        return unit;
    }
}
