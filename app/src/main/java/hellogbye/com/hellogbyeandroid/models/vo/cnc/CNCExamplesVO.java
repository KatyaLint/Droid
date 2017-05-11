package hellogbye.com.hellogbyeandroid.models.vo.cnc;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by nyawka on 5/10/17.
 */

public class CNCExamplesVO {
    @SerializedName("name")
    private
    String name;

    @SerializedName("queries")
    private ArrayList<String> examples;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getExamples() {
        return examples;
    }

    public void setExamples(ArrayList<String> examples) {
        this.examples = examples;
    }
}
