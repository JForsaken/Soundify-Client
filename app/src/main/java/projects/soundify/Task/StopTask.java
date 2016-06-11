package projects.soundify.Task;

import android.app.Activity;

import java.io.IOException;

import projects.soundify.Controller.MusicController;

/**
 * Created by joseph on 2016-06-08.
 */
public class StopTask extends SoundifyTask {

    private final static String ACTION = "stop";

    public StopTask(Activity activity, String ip, String port, boolean isStreaming) {
        super(activity, ip, port, isStreaming);
    }


    @Override
    protected Object doInBackground(Object[] objects) {

        try {
            if (isStreaming) {
                MusicController.getInstance(activity).stop();
            }
            else {
                executeGet(ACTION).body().string();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
