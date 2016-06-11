package projects.soundify.Task;

import android.app.Activity;

import java.io.IOException;
import java.net.URLEncoder;

import projects.soundify.Controller.MusicController;
import projects.soundify.Song;

/**
 * Created by joseph on 2016-06-08.
 */
public class PreviousTask extends SoundifyTask {

    private final static String ACTION = "previous";

    public PreviousTask(Activity activity, String ip, String port, boolean isStreaming) {
        super(activity, ip, port, isStreaming);
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try {

            Song song = gson.fromJson(executeGet(ACTION).body().string(), Song.class);

            if (isStreaming) {
                executeStream(song.getPath());
                MusicController.getInstance(activity).play();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
