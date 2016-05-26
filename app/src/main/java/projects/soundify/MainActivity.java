package projects.soundify;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void play(View view) {
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String[] files  = file.list();

        int c = 0;
    }

    public void pause(View view) {

    }

    public void next(View view) {

    }

    public void previous(View view) {

    }

    public void setting(View view) {
        Intent settingActivityIntent = new Intent(this, SettingActivity.class);
        startActivity(settingActivityIntent);
    }
}
