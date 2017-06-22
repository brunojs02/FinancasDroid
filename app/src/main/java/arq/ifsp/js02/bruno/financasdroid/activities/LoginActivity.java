package arq.ifsp.js02.bruno.financasdroid.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import arq.ifsp.js02.bruno.financasdroid.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText eTEmail, eTSenha;
    Button bLogin, bCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        eTEmail = (EditText) findViewById(R.id.editTextEmail);
        eTSenha = (EditText) findViewById(R.id.editTextPassword);
        bLogin = (Button) findViewById(R.id.buttonLogar);
        bLogin.setOnClickListener(this);
        bCadastro = (Button) findViewById(R.id.buttonRegistrar);
    }

    @Override
    public void onClick(View view) {
        Intent it;
        switch (view.getId()) {
            case R.id.buttonLogar:
                it = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(it);
                break;
            case R.id.buttonRegistrar:

                break;
        }
    }
}
