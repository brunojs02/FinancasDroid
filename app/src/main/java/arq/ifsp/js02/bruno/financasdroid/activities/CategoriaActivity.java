package arq.ifsp.js02.bruno.financasdroid.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.List;

import arq.ifsp.js02.bruno.financasdroid.R;
import arq.ifsp.js02.bruno.financasdroid.adapters.CategoriaAdapter;
import arq.ifsp.js02.bruno.financasdroid.model.CategoriaDAO;
import arq.ifsp.js02.bruno.financasdroid.model.GerenciaBancoDAO;
import arq.ifsp.js02.bruno.financasdroid.entities.SubCategoria;

public class CategoriaActivity extends AppCompatActivity{

    ListView viewSubCategorias;
    GerenciaBancoDAO banco;
    CategoriaDAO categoriaDAO;
    List<SubCategoria> subCategorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        banco = new GerenciaBancoDAO(getBaseContext());
        categoriaDAO = new CategoriaDAO(banco.getWritableDatabase());
        setContentView(R.layout.activity_categoria);
        viewSubCategorias = (ListView) findViewById(R.id.listViewCategorias);
        atualizaSubCategoriasView();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void atualizaSubCategoriasView() {
        subCategorias = categoriaDAO.getSubCategorias();
        CategoriaAdapter adapterCategoria = new CategoriaAdapter(this, R.layout.sub_categoria_list,
                subCategorias);
        viewSubCategorias.setAdapter(adapterCategoria);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        atualizaSubCategoriasView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent it;
        switch (item.getItemId()) {
            case R.id.menu_add:
                it = new Intent(CategoriaActivity.this, CrudCategoriaActivity.class);
                startActivity(it);
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
