package projects.soundify.Timer;

import android.app.Activity;
import android.os.Handler;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import projects.soundify.Controller.HttpController;
import projects.soundify.Controller.MusicController;
import projects.soundify.R;

/**
 * Created by joseph on 2016-06-10.
 */
public class MusicTimer {

    private static final int DELAY = 1000; // 1 Second
    private static Handler handler = null;
    private static Runnable runnable = null;
    private static MusicTimer instance = null;
    private static Activity _activity  = null;
    private static Long _currentDuration = 0L;
    private static Long _duration = 0L;
    private static boolean _isStreaming = false;
    private static boolean _isPaused = false;

    private MusicTimer() {

    }

    public static MusicTimer getInstance() {

        if (instance == null) {
            instance = new MusicTimer();
            handler = new Handler();
        }

        return  instance;
    }

    private static void updateTimer() {
        _currentDuration += DELAY;
        updateDurationView(String.format("%1$s/%2$s", formatDuration(_currentDuration),
                formatDuration(_duration)));
    }

    public void setup(Activity activity, Long duration, boolean isStreaming) {
        _activity = activity;
        _duration = duration;
        _isStreaming = isStreaming;
        _currentDuration = 0L;

        setupRunnable();
    }

    public void run() {
        handler.postAtTime(runnable, DELAY);
    }

    public void stop() {
        handler.removeCallbacks(runnable);
    }

    private static String formatDuration(Long duration) {
        Long hours = TimeUnit.MILLISECONDS.toHours(duration);
        Long minutes = TimeUnit.MILLISECONDS.toMinutes(duration) - TimeUnit.HOURS.toMinutes(hours);
        Long seconds = TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(minutes);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private static void updateDurationView(String duration) {
        TextView txtDuration = (TextView) _activity.findViewById(R.id.txtDuration);
        txtDuration.setText(duration);
    }

    public void setPause(boolean isPaused) {
        _isPaused = isPaused;
    }

    public boolean isPaused() {
        return _isPaused;
    }

    public void restart() {
        _currentDuration = 0L;
        handler.postAtTime(runnable, DELAY);
    }

    private void setupRunnable() {
        runnable = new Runnable() {
            @Override
            public void run() {

                if (_currentDuration < _duration) {

                    if (!_isPaused) {
                        updateTimer();
                    }

                    if (_currentDuration > _duration) {
                        if (!MusicController.getInstance(_activity).isServerLooping()) {
                            HttpController.getInstance(_activity).next(_isStreaming);
                        }
                        else {
                            restart();
                        }
                    }
                    else {
                        handler.postDelayed(this, DELAY);
                    }
                }
            }
        };
    }
}
