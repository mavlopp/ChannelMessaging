package smith.alaric.channelmessaging;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import smith.alaric.channelmessaging.model.Connect;

public class LoginActivity extends Activity implements View.OnClickListener, OnDownloadListener {
    private ImageView ChanLogo;
    private Button valider;
    private EditText login;
    private EditText password;

    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        valider = (Button) findViewById(R.id.btValider);
        valider.setOnClickListener(this) ;
        login = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);
        ChanLogo = (ImageView) findViewById(R.id.logo);
    }

    @Override
    public void onClick(View v) {
        HashMap<String, String> myMap = new HashMap<String, String>();
        myMap.put("username", login.getText().toString());
        myMap.put("password", password.getText().toString());
        PostRequest p = new PostRequest("http://www.raphaelbischof.fr/messaging/?function=connect", myMap);
        HttpPostHandler handler = new HttpPostHandler();
        handler.addOnDownloadListener(this);
        handler.execute(p);
    }

    @Override
    public void onDownloadComplete(String downloadedContent) {
        Context c = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        //Toast t = Toast.makeText(c, downloadedContent, duration);

        Gson gson = new Gson();
        Connect obj = gson.fromJson(downloadedContent, Connect.class);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("accesstoken", obj.getAccesstoken());
        // Commit the edits!
        editor.commit();

        if(obj.getCode() == 200){
            Intent myIntent = new Intent(getApplicationContext(),MainActivity.class);
            //startActivity(myIntent, ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, ChanLogo, "logo").toBundle());
            startActivity(myIntent);

        } else {
            Toast t = Toast.makeText(c, "Erreur code "+obj.getCode()+": "+obj.getResponse(), duration);
            t.show();
        }
    }

    @Override
    public void onDownloadError(String error) {
        Context c = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast t = Toast.makeText(c, error, duration);
        t.show();
    }

}
