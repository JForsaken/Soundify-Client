package projects.soundify;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import projects.soundify.Controller.HttpController;


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
        HttpController.getInstance(this).play(isStreaming);

        view.setVisibility(View.GONE);

    }

    public void pause(View view) {
        HttpController.getInstance(this).pause(isStreaming);
    }

    public void stop(View view) {
        HttpController.getInstance(this).stop(isStreaming);
    }

    public void next(View view) {
        HttpController.getInstance(this).next(isStreaming);
    }

    public void previous(View view) {
        HttpController.getInstance(this).previous(isStreaming);
    }

    public void shuffle(View view) {
        HttpController.getInstance(this).shuffle(isStreaming);
    }

    public void repeat(View view) {
        HttpController.getInstance(this).repeat(isStreaming);
    }

    public void setting(View view) {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }
}
