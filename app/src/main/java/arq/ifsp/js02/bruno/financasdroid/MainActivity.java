package arq.ifsp.js02.bruno.financasdroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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
        switch (item.getItemId()){
            case R.id.cad_categoria:
                //cadastrar categorias
                break;

            case R.id.cad_credito:
                //cadastrar creditos
                break;

            case R.id.cad_debito:
                //cadastrar debitos
                break;

            case R.id.show_credito:
                //mostrar creditos
                break;

            case R.id.show_debito:
                //mostrar debitos
                break;

            case R.id.show_relatorio_final:
                //mostrar relatório final
                break;
        }
        return true;
    }
}
