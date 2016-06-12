package projects.soundify.Controller;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

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

    public void play(boolean isStreaming) {
        AsyncTask task = new PlayTask(_activity, getIP(), getPort(), isStreaming);
        task.execute();
    }

    public void pause(boolean isStreaming) {
        AsyncTask task = new PauseTask(_activity, getIP(), getPort(), isStreaming);
        task.execute();
    }

    public void stop(boolean isStreaming) {
        AsyncTask task = new StopTask(_activity, getIP(), getPort(), isStreaming);
        task.execute();
    }

    public void shuffle(boolean isStreaming) {
        AsyncTask task = new ShuffleTask(_activity, getIP(), getPort(), isStreaming);
        task.execute();
    }

    public void repeat(boolean isStreaming) {
        AsyncTask task = new RepeatTask(_activity, getIP(), getPort(), isStreaming);
        task.execute();
    }

    public void next(boolean isStreaming) {
        AsyncTask task = new NextTask(_activity, getIP(), getPort(), isStreaming);
        task.execute();
    }

    public void previous(boolean isStreaming) {
        AsyncTask task = new PreviousTask(_activity, getIP(), getPort(), isStreaming);
        task.execute();
    }

    private String getIP() {
        return _sharedPreferences.getString("soundify_IP", "255.255.255.255");
    }

    private String getPort() {
        return _sharedPreferences.getString("soundify_Port", "8080");
    }
}
