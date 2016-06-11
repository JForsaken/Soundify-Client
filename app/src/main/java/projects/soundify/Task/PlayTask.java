package projects.soundify.Task;

import android.app.Activity;
import android.net.Uri;
import android.widget.TextView;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import projects.soundify.Controller.MusicController;
import projects.soundify.R;
import projects.soundify.Song;
import projects.soundify.Timer.MusicTimer;

/**
 * Created by joseph on 2016-06-08.
 */
public class PlayTask extends SoundifyTask {

    private final static String ACTION = "play";

    public PlayTask(Activity activity, String ip, String port, boolean isStreaming) {
        super(activity, ip, port, isStreaming);
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        Song song = null;

        try {
            song = gson.fromJson(executeGet(ACTION).body().string(), Song.class);

            if (isStreaming) {

                if (MusicController.getInstance(activity).isPaused() == false) {
                    executeStream(song.getPath());
                }

                MusicController.getInstance(activity).play();
            }
        }
        catch (IOException e) {
            this.cancel(true);
        }

        return song;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        Song song = (Song) o;

        TextView txtDuration = (TextView) activity.findViewById(R.id.txtDuration);
        Long minutes = TimeUnit.MICROSECONDS.toMinutes(Integer.parseInt(song.getDuration()));

        if (song != null) {
            if (isStreaming == false) {
                //txtDuration.setText(minutes.toString());
            }
            else {
                if (MusicController.getInstance(activity).isPlaying()) {
                    //txtDuration.setText(MusicController.getInstance(activity).getDuration());
                }
            }

            //MusicTimer.getInstance(txtDuration).setup(Long.parseLong(song.getDuration()));
            //MusicTimer.getInstance(txtDuration).run();
        }
    }
}
