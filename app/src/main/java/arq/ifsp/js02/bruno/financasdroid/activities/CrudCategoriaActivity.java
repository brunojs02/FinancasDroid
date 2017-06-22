package arq.ifsp.js02.bruno.financasdroid.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import arq.ifsp.js02.bruno.financasdroid.R;
import arq.ifsp.js02.bruno.financasdroid.dao.CriaBanco;

public class CrudCategoriaActivity extends AppCompatActivity implements View.OnClickListener{

    Button bInsere;
    CriaBanco banco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (banco == null) {
            banco = new CriaBanco(getBaseContext());
        }
        setContentView(R.layout.activity_crud_categoria);
        bInsere = (Button) findViewById(R.id.buttonInsereCategoria);
        bInsere.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonInsereCategoria:

                break;
        }
    }
}
