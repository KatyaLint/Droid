package hellogbye.com.hellogbyeandroid.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CountryItemVO {


    @SerializedName("code")
    private String code;

    @SerializedName("name")
    private String name;

    @SerializedName("provinces")
    private ArrayList<ProvincesItem> provinces;


    public CountryItemVO(String code, String name, ArrayList<ProvincesItem> provinces) {
        this.code = code;
        this.name = name;
        this.provinces = provinces;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ProvincesItem> getProvinces() {
        return provinces;
    }

    public void setProvinces(ArrayList<ProvincesItem> provinces) {
        this.provinces = provinces;
    }

    @Override
    public String toString() {
        return "CountryItemVO{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", provinces=" + provinces +
                '}';
    }
}
