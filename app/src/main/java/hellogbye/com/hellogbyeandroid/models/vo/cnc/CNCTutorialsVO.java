package hellogbye.com.hellogbyeandroid.models.vo.cnc;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by nyawka on 5/10/17.
 */

public class CNCTutorialsVO {

    @SerializedName("examples")
    private ArrayList<CNCExamplesVO> examples = new ArrayList<CNCExamplesVO>();

    public ArrayList<CNCExamplesVO> getExamples() {
        return examples;
    }

    public void setExamples(ArrayList<CNCExamplesVO> examples) {
        this.examples = examples;
    }
}
