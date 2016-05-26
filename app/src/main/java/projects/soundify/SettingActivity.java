package projects.soundify;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        EditText txtIP = (EditText) findViewById(R.id.txtIP);
        EditText txtPort = (EditText) findViewById(R.id.txtPort);

        txtIP.setText(sharedPreferences.getString("soundify_IP", "255.255.255.255"));
        txtPort.setText(sharedPreferences.getString("soundify_Port", "80"));
    }

    public void back(View view) {
        finish();
    }

    public void save(View view) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        EditText txtIP = (EditText) findViewById(R.id.txtIP);
        EditText txtPort = (EditText) findViewById(R.id.txtPort);

        editor.putString("soundify_IP", txtIP.getText().toString());
        editor.putString("soundify_Port", txtPort.getText().toString());

        editor.commit();

        finish();
    }
}
