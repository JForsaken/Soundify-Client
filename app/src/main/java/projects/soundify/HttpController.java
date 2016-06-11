package projects.soundify;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import projects.soundify.Task.NextTask;
import projects.soundify.Task.PauseTask;
import projects.soundify.Task.PlayTask;
import projects.soundify.Task.PreviousTask;
import projects.soundify.Task.RepeatTask;
import projects.soundify.Task.ShuffleTask;
import projects.soundify.Task.StopTask;

/**
 * Created by jderrico on 16-05-17.
 */
public class HttpController {
    private static HttpController _instance = null;
    private static SharedPreferences _sharedPreferences = null;
    private static MediaPlayer mediaPlayer;
    private static Activity _activity;

    private HttpController() {}

    public static HttpController getInstance(Activity activity) {

        _activity = activity;

        if (_instance == null) {
            _instance = new HttpController();
            _sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }

        return _instance;
    }

    public void play(boolean isStreaming, boolean isResponseRequired) {
        //sendRequest(isStreaming, isResponseRequired, "play");
        AsyncTask task = new PlayTask(_activity, getIP(), getPort(), isStreaming);
        task.execute();
    }

    public void pause(boolean isStreaming, boolean isResponseRequired) {
        //sendRequest(isStreaming, isResponseRequired, "pause");
        AsyncTask task = new PauseTask(_activity, getIP(), getPort(), isStreaming);
        task.execute();
    }

    public void stop(boolean isStreaming, boolean isResponseRequired) {
        //sendRequest(isStreaming, isResponseRequired, "stop");
        AsyncTask task = new StopTask(_activity, getIP(), getPort(), isStreaming);
        task.execute();
    }

    public void shuffle(boolean isStreaming, boolean isResponseRequired) {
        //sendRequest(isStreaming, isResponseRequired, "shuffle");
        AsyncTask task = new ShuffleTask(_activity, getIP(), getPort(), isStreaming);
        task.execute();
    }

    public void repeat(boolean isStreaming, boolean isResponseRequired) {
        //sendRequest(isStreaming, isResponseRequired, "repeat");
        AsyncTask task = new RepeatTask(_activity, getIP(), getPort(), isStreaming);
        task.execute();
    }

    public void next(boolean isStreaming, boolean isResponseRequired) {
        //sendRequest(isStreaming, isResponseRequired, "next");
        AsyncTask task = new NextTask(_activity, getIP(), getPort(), isStreaming);
        task.execute();
    }

    public void previous(boolean isStreaming, boolean isResponseRequired) {
        //sendRequest(isStreaming, isResponseRequired, "previous");
        AsyncTask task = new PreviousTask(_activity, getIP(), getPort(), isStreaming);
        task.execute();
    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public void sendRequest(boolean isStreaming, final boolean isResponseRequired, String action) {
        AsyncTask requestTask = new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] objects) {

                boolean isStreaming = (boolean) objects[0];
                String action = (String) objects[1];
                String IP =  _sharedPreferences.getString("soundify_IP", "255.255.255.255");
                String port = _sharedPreferences.getString("soundify_Port", "8080");
                String url = String.format("Http://%1$s:%2$s/%3$s/%4$s", IP, port
                        , isStreaming ? "stream" : "action", action);

                OkHttpClient client = null;
                Request request = null;
                Response response = null;

                try {

                    client = new OkHttpClient();

                    request = new Request.Builder()
                            .url(url)
                            .build();

                    response = client.newCall(request).execute();

                    Gson gson = new GsonBuilder().create();
                    Song song = null;

                    if (isResponseRequired) {
                         song = gson.fromJson(response.body().string(), Song.class);
                        updateSongInformation(song);
                    }


                    if (isStreaming == true) {

                        if (isResponseRequired) {
                            String mediaPlayerUrl = String.format("Http://%1$s:%2$s%3$s", IP, port
                                    , song.getPath());

                            mediaPlayer.reset();
                            mediaPlayer.setDataSource("http://192.168.43.193:8080/storage/emulated/0/Music/1.mp3");
                            mediaPlayer.prepare();
                        }

                        switch (action) {
                            case "play":
                                mediaPlayer.start();
                                break;
                            case "pause":
                                mediaPlayer.pause();
                                break;
                            case "stop":
                                mediaPlayer.stop();
                                break;
                            case "next":
                            case "previous":
                                mediaPlayer.pause();
                                mediaPlayer.reset();
                                mediaPlayer.start();
                                break;
                        }
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }
        };

        requestTask.execute(isStreaming, action);
    }

    private void updateSongInformation(Song song) {
        //TODO : Ajouter l'update de l'interface
    }

    private String getIP() {
        return _sharedPreferences.getString("soundify_IP", "255.255.255.255");
    }

    private String getPort() {
        return _sharedPreferences.getString("soundify_Port", "8080");
    }
}
