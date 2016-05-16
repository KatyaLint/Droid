package hellogbye.com.hellogbyeandroid.utilities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.File;
import java.io.IOException;

import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

/**
 * Created by arisprung on 5/16/16.
 */
public class ViewPDFManager {

    private Context mContext;

    public ViewPDFManager(Context context){
        mContext = context;
    }

    public String  starDownloadPDF(String pdfUrl ){
        OkHttpClient okHttpClient = new OkHttpClient();
        HGBPreferencesManager sharedPreferences = HGBPreferencesManager.getInstance(mContext);
        String token = sharedPreferences.getStringSharedPreferences(HGBPreferencesManager.TOKEN, "");

        Request request = new Request.Builder()
                .url(pdfUrl)
                .addHeader("Content-Type", "application/pdf")
                .addHeader("Authorization", "Session " + token)
                .build();

        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response.isSuccessful()) {
            try {
                String folderPath = HGBUtility.createFolder("HGB");
                File destFile = new File(folderPath + "/" + "hgbPDF");
                ResponseBody body = response.body();
                long contentLength = body.contentLength();
                BufferedSource source = body.source();
                BufferedSink sink = Okio.buffer(Okio.sink(destFile));
                Buffer sinkBuffer = sink.buffer();
                long totalBytesRead = 0;
                int bufferSize = 8 * 1024;
                for (long bytesRead; (bytesRead = source.read(sinkBuffer, bufferSize)) != -1; ) {
                    sink.emit();
                    totalBytesRead += bytesRead;
                }
                sink.flush();
                sink.close();
                source.close();
                return destFile.getPath();
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    private void viewPDF(String path) {

        File file = new File(path);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");

        mContext.startActivity(intent);
    }
}
