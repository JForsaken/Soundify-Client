package projects.soundify.Task;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

        if (song != null) {
            updateView(song);
        }
    }

    private void updateView(Song song) {
        TextView title = (TextView) activity.findViewById(R.id.txtTitle);
        TextView artist = (TextView) activity.findViewById(R.id.txtArtist);
        TextView album = (TextView) activity.findViewById(R.id.txtAlbum);

        title.setText(song.getTitle());
        artist.setText(song.getArtist());
        album.setText(song.getAlbum());

        setPlayButtonVisibility(View.GONE);
        setPauseButtonVisibility(View.VISIBLE);
        setStopButtonVisibility(View.VISIBLE);

        updateDuration(song);
    }


    private void setPlayButtonVisibility(int visibility) {
        Button btnPlay = (Button) activity.findViewById(R.id.btnPlay);
        btnPlay.setVisibility(visibility);
    }

    private void setPauseButtonVisibility(int visibility) {
        Button btnPause = (Button) activity.findViewById(R.id.btnPause);
        btnPause.setVisibility(visibility);
    }

    private void setStopButtonVisibility(int visibility) {
        Button btnStop = (Button) activity.findViewById(R.id.btnStop);
        btnStop.setVisibility(visibility);
    }

    private void updateDuration(Song song) {
        Long duration = Long.parseLong(song.getDuration());

        if (MusicTimer.getInstance().isPaused() == false) {
            MusicTimer.getInstance().setup(activity, duration, isStreaming);
            MusicTimer.getInstance().run();
        }
        else {
            MusicTimer.getInstance().setPause(false);
        }
    }
}
