package arq.ifsp.js02.bruno.financasdroid.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import arq.ifsp.js02.bruno.financasdroid.R;

public class CategoriaActivity extends AppCompatActivity implements View.OnClickListener{

    Button bAddCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);
        bAddCategoria = (Button) findViewById(R.id.buttonAddCategoria);
        bAddCategoria.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent it;
        switch (view.getId()) {
            case R.id.buttonAddCategoria:
                it = new Intent(CategoriaActivity.this, CrudCategoriaActivity.class);
                startActivity(it);
                break;
        }
    }
}
