package projects.soundify.Task;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import projects.soundify.Controller.MusicController;
import projects.soundify.R;
import projects.soundify.Song;
import projects.soundify.Timer.MusicTimer;

/**
 * Created by joseph on 2016-06-08.
 */
public class PauseTask extends SoundifyTask {

    private final static String ACTION = "pause";

    public PauseTask(Activity activity, String ip, String port, boolean isStreaming) {
        super(activity, ip, port, isStreaming);
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        try {
            if (isStreaming) {
                MusicController.getInstance(activity).pause();
            }
            else {
                executeGet(ACTION);
            }
        }
        catch (IOException e) {
            this.cancel(true);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        updateView();
    }

    private void updateView() {
        setPauseButtonVisibility(View.GONE);
        setPlayButtonVisibility(View.VISIBLE);
        MusicTimer.getInstance().setPause(true);
    }


    private void setPlayButtonVisibility(int visibility) {
        Button btnPlay = (Button) activity.findViewById(R.id.btnPlay);
        btnPlay.setVisibility(visibility);
    }

    private void setPauseButtonVisibility(int visibility) {
        Button btnPause = (Button) activity.findViewById(R.id.btnPause);
        btnPause.setVisibility(visibility);
    }
}
