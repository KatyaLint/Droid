package hellogbye.com.hellogbyeandroid.models.vo.airports;

import java.util.ArrayList;

/**
 * Created by nyawka on 2/7/16.
 */
public class AirportSendValuesVO {

    private String query;
    private String type;
    private String id;
    private double start;
    private double end;
    private String value;
    private String latitude;
    private String longitude;
    private String travelpreferenceprofileid;
    private int centeredItem;
    private String[] titleArray;
    private ArrayList<AirportResultsVO> results;

    public AirportSendValuesVO() {
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }



    public String getTravelpreferenceprofileid() {
        return travelpreferenceprofileid;
    }

    public void setTravelpreferenceprofileid(String travelpreferenceprofileid) {
        this.travelpreferenceprofileid = travelpreferenceprofileid;
    }




    public double getEnd() {
        return end;
    }

    public void setEnd(double end) {
        this.end = end;
    }

    public double getStart() {
        return start;
    }

    public void setStart(double start) {
        this.start = start;
    }


    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getCenteredItem() {
        return centeredItem;
    }

    public void setCenteredItem(int centeredItem) {
        this.centeredItem = centeredItem;
    }

    public String[] getTitleArray() {
        return titleArray;
    }

    public void setTitleArray(String[] titleArray) {
        this.titleArray = titleArray;
    }

    public ArrayList<AirportResultsVO> getResults() {
        return results;
    }

    public void setResults(ArrayList<AirportResultsVO> results) {
        this.results = results;
    }
}
