package projects.soundify;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;


public class MainActivity extends AppCompatActivity {
    private boolean isStreaming = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Switch switchStreaming = (Switch) findViewById(R.id.switchStreaming);
        switchStreaming.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isStreaming = isChecked;
            }
        });


    }

    public void play(View view) {
        HttpController.getInstance(this).play(isStreaming, true);
    }

    public void pause(View view) {
        HttpController.getInstance(this).pause(isStreaming, false);
    }

    public void stop(View view) {
        HttpController.getInstance(this).stop(isStreaming, true);
    }

    public void next(View view) {
        HttpController.getInstance(this).next(isStreaming, true);
    }

    public void previous(View view) {
        HttpController.getInstance(this).previous(isStreaming, true);
    }

    public void shuffle(View view) {
        HttpController.getInstance(this).shuffle(isStreaming, false);
    }

    public void repeat(View view) {
        HttpController.getInstance(this).repeat(isStreaming, false);
    }



    public void setting(View view) {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }
}
