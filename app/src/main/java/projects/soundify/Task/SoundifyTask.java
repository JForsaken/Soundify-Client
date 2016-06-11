package projects.soundify.Task;

import android.app.Activity;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by joseph on 2016-06-08.
 */
public abstract class SoundifyTask extends AsyncTask {

    protected String ip;
    protected String port;
    protected boolean isStreaming;
    protected OkHttpClient client = null;
    protected Request request = null;
    protected Response response = null;
    protected Gson gson = null;
    protected Activity activity = null;

    public SoundifyTask(Activity activity, String ip, String port, boolean isStreaming) {
        super();

        this.activity = activity;
        this.ip = ip;
        this.port = port;
        this.isStreaming = isStreaming;

        gson = new GsonBuilder().create();
        client = new OkHttpClient();
    }

    protected String getServerUrl(String action) {
        String actionType = null;

        if (isStreaming == true) {
            actionType = "stream";
        }
        else {
            actionType = "action";
        }

        String serverUrl = String.format("http://%1$s:%2$s/%3$s/%4$s", ip, port, actionType, action);

        return serverUrl;
    }

    protected String getSongUrl(String songPath) {
        return String.format("Http://%1$s:%2$s%3$s", ip, port, songPath);
    }

    protected Response executeGet(String action) throws IOException {
        String url = getServerUrl(action);
        request = new Request.Builder().url(url).build();
        response = client.newCall(request).execute();
        return response;
    }

    protected String executeStreaming(String songPath) {
        return String.format("Http://%1$s:%2$s%3$s", ip, port, songPath);
    }
}
