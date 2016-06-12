package projects.soundify.Controller;

import android.app.Activity;
import android.media.MediaPlayer;

import java.io.IOException;

import projects.soundify.Timer.MusicTimer;

/**
 * Created by joseph on 2016-06-08.
 */
public class MusicController {

    private static Activity _activity;
    private static MusicController _instance = null;
    private static MediaPlayer _mediaPlayer = null;
    private boolean _isPaused = false;
    private static boolean _isServerLooping = false;

    public static MusicController getInstance(final Activity activity) {

        _activity = activity;

        if (_instance == null) {
            _instance = new MusicController();
            _mediaPlayer = new MediaPlayer();
            _mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {

                    if (_isServerLooping) {
                        HttpController.getInstance(activity).next(true);
                    }
                }
            });
        }

        return _instance;
    }

    private MusicController() {
    }

    public void init(String url) throws IOException {
        _mediaPlayer.reset();
        _mediaPlayer.setDataSource(url);
        _mediaPlayer.prepare();
    }

    public void play() {
        initMusicPlayerState();
        _mediaPlayer.start();
    }

    public void pause() {
        initMusicPlayerState();
        _mediaPlayer.pause();
        _isPaused = true;
    }

    public void stop() {
        initMusicPlayerState();
        _mediaPlayer.stop();
    }

    public void repeat() {
        _mediaPlayer.setLooping(!_mediaPlayer.isLooping());
    }

    public boolean isPaused() {
        return _isPaused;
    }

    public void setServerLooping() {
        _isServerLooping = !_isServerLooping;
    }

    public boolean isServerLooping() {
        return _isServerLooping;
    }

    private void initMusicPlayerState() {
        _isPaused = false;
    }

}
