package projects.soundify.Controller;

import android.content.Context;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by joseph on 2016-06-08.
 */
public class MusicController {

    private static Context _context;
    private static MusicController _instance = null;
    private static MediaPlayer _mediaPlayer = null;
    private boolean _isPlaylistLooping = false;
    private boolean _isPaused = false;
    private boolean _repeat = false;

    public static MusicController getInstance(Context context) {

        _context = context;

        if (_instance == null) {
            _instance = new MusicController();
            _mediaPlayer = new MediaPlayer();
            _mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {

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
        _mediaPlayer.setLooping(!_repeat);
    }

    public boolean isPlaying() {
        return _mediaPlayer.isPlaying();
    }

    public boolean isPaused() {
        return _isPaused;
    }

    private void initMusicPlayerState() {
        _isPaused = false;
    }

    public int getDuration() {
        return _mediaPlayer.getDuration();
    }
}
