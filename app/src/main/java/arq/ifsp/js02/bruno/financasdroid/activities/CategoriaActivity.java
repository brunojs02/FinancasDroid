package arq.ifsp.js02.bruno.financasdroid.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.List;

import arq.ifsp.js02.bruno.financasdroid.R;
import arq.ifsp.js02.bruno.financasdroid.adapters.CategoriaAdapter;
import arq.ifsp.js02.bruno.financasdroid.dao.CategoriaDAO;
import arq.ifsp.js02.bruno.financasdroid.dao.CriaBanco;
import arq.ifsp.js02.bruno.financasdroid.entities.SubCategoria;

public class CategoriaActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener{

    Button bAddCategoria;
    ListView viewSubCategorias;
    CriaBanco banco;
    CategoriaDAO categoriaDAO;
    List<SubCategoria> subCategorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        banco = new CriaBanco(getBaseContext());
        categoriaDAO = new CategoriaDAO(banco.getWritableDatabase());
        setContentView(R.layout.activity_categoria);
        bAddCategoria = (Button) findViewById(R.id.buttonAddCategoria);
        bAddCategoria.setOnClickListener(this);
        viewSubCategorias = (ListView) findViewById(R.id.listViewCategorias);
        viewSubCategorias.setOnItemClickListener(this);
        atualizaSubCategoriasView();
    }

    private void atualizaSubCategoriasView() {
        subCategorias = categoriaDAO.getSubCategorias();
        CategoriaAdapter adapterCategoria = new CategoriaAdapter(this, R.layout.sub_categoria_list,
                subCategorias);
        viewSubCategorias.setAdapter(adapterCategoria);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        atualizaSubCategoriasView();
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        SubCategoria subCategoria = subCategorias.get(i);
        Toast.makeText(this, subCategoria.getId().toString(), Toast.LENGTH_SHORT).show();
    }
}
