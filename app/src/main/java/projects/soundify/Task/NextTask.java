package projects.soundify.Task;

import android.app.Activity;
import android.widget.TextView;

import java.io.IOException;
import java.net.URLEncoder;

import projects.soundify.Controller.MusicController;
import projects.soundify.R;
import projects.soundify.Song;
import projects.soundify.Timer.MusicTimer;

/**
 * Created by joseph on 2016-06-08.
 */
public class NextTask extends SoundifyTask {

    private final static String ACTION = "next";

    public NextTask(Activity activity, String ip, String port, boolean isStreaming) {
        super(activity, ip, port, isStreaming);
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        Song song = null;

        try {

            song = gson.fromJson(executeGet(ACTION).body().string(), Song.class);

            if (isStreaming) {
                executeStream(song.getPath());
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

        updateDuration(song);
    }

    private void updateDuration(Song song) {
        Long duration = Long.parseLong(song.getDuration());
        MusicTimer.getInstance().stop();
        MusicTimer.getInstance().setup(activity, duration, isStreaming);
        MusicTimer.getInstance().setPause(false);
        MusicTimer.getInstance().run();
    }
}
