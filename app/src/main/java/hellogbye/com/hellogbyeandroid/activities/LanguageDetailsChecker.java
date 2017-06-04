/*
package hellogbye.com.hellogbyeandroid.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

import java.util.List;

import hellogbye.com.hellogbyeandroid.utilities.SpeechRecognitionUtil;

*/
/**
 * Created by nyawka on 5/25/17.
 *//*


public class LanguageDetailsChecker extends BroadcastReceiver {
    private List<String> supportedLanguages;

    private String languagePreference;

    @Override
    public void onReceive(Context context, Intent i) {
        Bundle results = getResultExtras(true);
        if (results.containsKey(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE)) {
            languagePreference =
                    results.getString(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE);
        }
        if (results.containsKey(RecognizerIntent.EXTRA_SUPPORTED_LANGUAGES)) {
            supportedLanguages =
                    results.getStringArrayList(
                            RecognizerIntent.EXTRA_SUPPORTED_LANGUAGES);
        }
        try {

            boolean recognizerIntent =
                    SpeechRecognitionUtil.isSpeechAvailable(MainActivityBottomTabs.this);
            if (recognizerIntent) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                //intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, languagePreference);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak Now...");
                startActivityForResult(intent, 0);
            } else {
                Crashlytics.logException(new Exception("Speech not avaibale"));
                Log.e("MainActvity", "Speeach not avaiable");
            }

        } catch (Exception e) {
            Log.v("Speech", "Could not find any Speech Recognition Actions");
        }
    }
}*/
