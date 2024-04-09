package pogodynka.dao;


public class ChartData {

    private String title;

    private String unit;

    private String[] xData;
    private Double[] yData;


    public ChartData(String title, String unit, String[] xData, Double[] yData) {
        this.title = title;
        this.unit = unit;
        this.xData = xData;
        this.yData = yData;
    }

    public String getUnit() {
        return unit;
    }

    public String getTitle() {
        return title;
    }

    public String[] getxData() {
        return xData;
    }

    public Double[] getyData() {
        return yData;
    }
}
