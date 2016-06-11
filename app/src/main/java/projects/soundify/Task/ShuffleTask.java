package projects.soundify.Task;

import android.app.Activity;

import java.io.IOException;

/**
 * Created by joseph on 2016-06-08.
 */
public class ShuffleTask extends SoundifyTask {

    private final static String ACTION = "shuffle";

    public ShuffleTask(Activity activity, String ip, String port, boolean isStreaming) {
        super(activity, ip, port, isStreaming);
    }


    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            executeGet(ACTION);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
