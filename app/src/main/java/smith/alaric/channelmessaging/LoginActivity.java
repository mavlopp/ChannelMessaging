package smith.alaric.channelmessaging;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashMap;

public class LoginActivity extends Activity implements View.OnClickListener, OnDownloadListener {

    private Button valider;
    private EditText login;
    private EditText password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        valider = (Button) findViewById(R.id.bValider);
        valider.setOnClickListener(this) ;
        login = (EditText) findViewById(R.id.editLogin);
        password = (EditText) findViewById(R.id.editPwd);
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
        Toast t = Toast.makeText(c, downloadedContent, duration);
        t.show();
    }

    @Override
    public void onDownloadError(String error) {
        Context c = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast t = Toast.makeText(c, error, duration);
        t.show();
    }
}
