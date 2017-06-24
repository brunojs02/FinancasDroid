package arq.ifsp.js02.bruno.financasdroid.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import arq.ifsp.js02.bruno.financasdroid.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent it;
        switch (item.getItemId()){
            case R.id.categoria_menu:
                //gerenciar categorias_list
                it = new Intent(MainActivity.this, CategoriaActivity.class);
                startActivity(it);
                break;

            case R.id.lancamento_menu:
                //cadastrar lancamentos
                it = new Intent(MainActivity.this, LancamentoActivity.class);
                startActivity(it);
                break;

            case R.id.relatorio_menu:
                //mostrar relatório final
                break;
        }
        return true;
    }
}
