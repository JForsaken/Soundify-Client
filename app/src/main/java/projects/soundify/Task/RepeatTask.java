package projects.soundify.Task;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import projects.soundify.Controller.MusicController;
import projects.soundify.R;

/**
 * Created by joseph on 2016-06-08.
 */
public class RepeatTask extends SoundifyTask {

    private final static String ACTION = "repeat";

    public RepeatTask(Activity activity, String ip, String port, boolean isStreaming) {
        super(activity, ip, port, isStreaming);
    }

    @Override
    protected Object inBackground(Object[] objects) {
        try {
            if (isStreaming) {
                MusicController.getInstance(activity).repeat();
            }
            else {
                executeGet(ACTION);
                MusicController.getInstance(activity).setServerLooping();
            }
        }
        catch (IOException e) {
            this.cancel(true);
        }

        return null;
    }

    @Override
    protected void postExecute(Object o) {
        updateView();
    }

    private void updateView() {
        Button btnRepeat = (Button) activity.findViewById(R.id.btnRepeat);

        if (btnRepeat.getCurrentTextColor() == Color.RED) {
            btnRepeat.setTextColor(Color.BLACK);
        }
        else {
            btnRepeat.setTextColor(Color.RED);
        }
    }
}
