package hellogbye.com.hellogbyeandroid.models.vo.flights;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by arisprung on 10/20/15.
 */
public class HotelVO {

    @SerializedName("paymentprocessingstate")
    private String paymentprocessingstate;
    @SerializedName("hotelcode")
    private String hotelcode;
    @SerializedName("cityname")
    private String cityname;
    @SerializedName("hotelname")
    private String hotelname;
    @SerializedName("hotelchain")
    private String hotelchain;
    @SerializedName("shortdescription")
    private String shortdescription;
    @SerializedName("locationdescription")
    private String locationdescription;

    @SerializedName("postalcode")
    private String postalcode;
    @SerializedName("address1")
    private String address1;
    @SerializedName("address2")
    private String address2;
    @SerializedName("address3")
    private String address3;
    @SerializedName("phone")
    private String phone;
    @SerializedName("image")
    private String image;
    @SerializedName("allimages")
    private ArrayList<AllImagesVO> allImagesVOs= new ArrayList<AllImagesVO>();
    @SerializedName("amenities")
    private String amenities;
    @SerializedName("checkin")
    private String checkin;
    @SerializedName("checkout")
    private String checkout;
    @SerializedName("country")
    private String country;
    @SerializedName("stateprovince")
    private String stateprovince;
    @SerializedName("paxguid")
    private String paxguid;
    @SerializedName("defaultroomguid")
    private String defaultroomguid;






}
