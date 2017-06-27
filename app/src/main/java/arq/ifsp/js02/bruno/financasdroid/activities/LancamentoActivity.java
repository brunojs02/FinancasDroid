package arq.ifsp.js02.bruno.financasdroid.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import arq.ifsp.js02.bruno.financasdroid.R;
import arq.ifsp.js02.bruno.financasdroid.adapters.LancamentoAdapter;
import arq.ifsp.js02.bruno.financasdroid.dao.CategoriaDAO;
import arq.ifsp.js02.bruno.financasdroid.dao.CriaBanco;
import arq.ifsp.js02.bruno.financasdroid.dao.LancamentoDAO;
import arq.ifsp.js02.bruno.financasdroid.entities.Categoria;
import arq.ifsp.js02.bruno.financasdroid.entities.Lancamento;

public class LancamentoActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener{

    Spinner spinnerCat;
    ListView viewLancamentos;
    Button bFiltrar;
    CategoriaDAO categoriaDAO;
    LancamentoDAO lancamentoDAO;
    CriaBanco banco;
    List<Lancamento> lancamentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        banco = new CriaBanco(getBaseContext());
        categoriaDAO = new CategoriaDAO(banco.getWritableDatabase());
        lancamentoDAO = new LancamentoDAO(banco.getWritableDatabase());
        lancamentos = new ArrayList<Lancamento>();
        setContentView(R.layout.activity_lancamento);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        bFiltrar = (Button) findViewById(R.id.buttonFiltrar);
        bFiltrar.setOnClickListener(this);
        spinnerCat = (Spinner) findViewById(R.id.spinnerSubCategoria);
        List<Categoria> categorias = new ArrayList<Categoria>();
        Categoria categoria = new Categoria();
        categoria.setNome("Selecione...");
        categorias.add(categoria);
        categorias.addAll(categoriaDAO.getCategorias());
        ArrayAdapter<Categoria> adapterSpinner = new ArrayAdapter<Categoria>(this,
                android.R.layout.simple_spinner_item, categorias);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCat.setAdapter(adapterSpinner);
        viewLancamentos = (ListView) findViewById(R.id.listViewLancamentos);
        viewLancamentos.setOnItemClickListener(this);
        atualizaLancamentos(null);
    }

    private void atualizaLancamentos(Integer idCategoria) {
        lancamentos = lancamentoDAO.getLancamentos(idCategoria);
        LancamentoAdapter adapterLancamento = new LancamentoAdapter(this, R.layout.lancamentos_list, lancamentos);
        viewLancamentos.setAdapter(adapterLancamento);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        atualizaLancamentos(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent it;
        switch (item.getItemId()) {
            case R.id.menu_add:
                it = new Intent(LancamentoActivity.this, CrudLancamentoActivity.class);
                startActivity(it);
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonFiltrar:
                Categoria categoria = (Categoria) spinnerCat.getSelectedItem();
                Integer idCategoria = null;
                if (categoria != null && categoria.getId() != null) {
                    idCategoria = categoria.getId();
                }
                atualizaLancamentos(idCategoria);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Lancamento lancamento = (Lancamento) adapterView.getItemAtPosition(i);
        if (lancamento != null && lancamento.getId() != null) {
            Intent it = new Intent(LancamentoActivity.this, CrudLancamentoActivity.class);
            it.putExtra("idLancamento", lancamento.getId());
            startActivity(it);
        }
    }
}
