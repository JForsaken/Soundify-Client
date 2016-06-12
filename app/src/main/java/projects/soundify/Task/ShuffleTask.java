package projects.soundify.Task;

import android.app.Activity;
import android.graphics.Color;
import android.widget.Button;

import java.io.IOException;

import projects.soundify.R;

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
        Button btnShuffle = (Button) activity.findViewById(R.id.btnShuffle);

        if (btnShuffle.getCurrentTextColor() == Color.RED) {
            btnShuffle.setTextColor(Color.BLACK);
        }
        else {
            btnShuffle.setTextColor(Color.RED);
        }
    }
}
