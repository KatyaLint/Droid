package hellogbye.com.hellogbyeandroid.onboarding;

/**
 * Created by nyawka on 11/10/16.
 */

public class OnboardingPagerTextVO {
    private int imageResource;
    private String textTitle;
    private String textExplanation;


    public OnboardingPagerTextVO(int imageResource, String textTitle,String textExplanation){
        this.imageResource = imageResource;
        this.textTitle = textTitle;
        this.textExplanation = textExplanation;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getTextTitle() {
        return textTitle;
    }

    public void setTextTitle(String textTitle) {
        this.textTitle = textTitle;
    }

    public String getTextExplanation() {
        return textExplanation;
    }

    public void setTextExplanation(String textExplanation) {
        this.textExplanation = textExplanation;
    }
}
