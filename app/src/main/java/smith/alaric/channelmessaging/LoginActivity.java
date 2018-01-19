package smith.alaric.channelmessaging;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.HashMap;

public class LoginActivity extends Activity implements View.OnClickListener {

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
        PostRequest p = new PostRequest("http://raphaelbischof.fr/messaging/", myMap);
        HttpPostHandler handler = new HttpPostHandler();
        handler.execute(p);
        
    }
}
