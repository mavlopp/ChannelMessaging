package smith.alaric.channelmessaging;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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
        login = (EditText) findViewById(R.id.editPwd);
    }

    @Override
    public void onClick(View v) {

    }
}
