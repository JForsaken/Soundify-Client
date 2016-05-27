package projects.soundify;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View;
import java.io.File;

public class MainActivity extends AppCompatActivity {

    HttpController _httpController = HttpController.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _httpController.init();
    }

    public void play(View view) {
        _httpController.play();
    }

    public void pause(View view) {
        _httpController.stop();
    }

    public void next(View view) { _httpController.next(); }

    public void previous(View view) { _httpController.previous(); }
}
