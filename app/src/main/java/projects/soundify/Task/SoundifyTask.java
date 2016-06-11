package projects.soundify.Task;

import android.app.Activity;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import fi.iki.elonen.NanoHTTPD;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import projects.soundify.Controller.MusicController;

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

    protected String getActionUrl(String action) {
        String actionType = null;

        if (isStreaming == true) {
            actionType = "stream";
        }
        else {
            actionType = "action";
        }

        String actionUrl = String.format("%1$s/%2$s/%3$s", getServerUrl(), actionType, action);

        return actionUrl;
    }

    protected String getServerUrl() {
        return String.format("http://%1$s:%2$s", ip, port);
    }

    protected Response executeGet(String action) throws IOException {
        String url = getActionUrl(action);
        request = new Request.Builder().url(url).build();
        response = client.newCall(request).execute();
        return response;
    }

    protected void executeStream(String path) throws IOException {
        String songFolder = path.substring(0, path.lastIndexOf("/") + 1);
        String songName = path.substring(path.lastIndexOf("/") + 1, path.length());
        String encodedSongName = URLEncoder.encode(songName, "utf-8");
        MusicController.getInstance(activity).init(getServerUrl() + songFolder + encodedSongName);
    }
}
