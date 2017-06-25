package arq.ifsp.js02.bruno.financasdroid.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import arq.ifsp.js02.bruno.financasdroid.R;
import arq.ifsp.js02.bruno.financasdroid.dao.CategoriaDAO;
import arq.ifsp.js02.bruno.financasdroid.dao.CriaBanco;
import arq.ifsp.js02.bruno.financasdroid.dao.LancamentoDAO;
import arq.ifsp.js02.bruno.financasdroid.entities.Lancamento;
import arq.ifsp.js02.bruno.financasdroid.entities.SubCategoria;

public class CrudLancamentoActivity extends AppCompatActivity implements View.OnClickListener{

    CriaBanco banco;
    CategoriaDAO categoriaDAO;
    LancamentoDAO lancamentoDAO;
    Spinner spinnerSubCategorias;
    Button bInsere;
    EditText eValorLancamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        banco = new CriaBanco(getBaseContext());
        categoriaDAO = new CategoriaDAO(banco.getWritableDatabase());
        lancamentoDAO = new LancamentoDAO(banco.getWritableDatabase());
        setContentView(R.layout.activity_crud_lancamento);
        spinnerSubCategorias = (Spinner) findViewById(R.id.spinnerSubCategoria);
        List<SubCategoria> subCategorias = new ArrayList<SubCategoria>();
        subCategorias.add(new SubCategoria(null, "Selecione...", null, null));
        subCategorias.addAll(categoriaDAO.getSubCategorias());
        ArrayAdapter<SubCategoria> adapterSubCategoria = new ArrayAdapter<SubCategoria>(this,
                android.R.layout.simple_spinner_item, subCategorias);
        adapterSubCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSubCategorias.setAdapter(adapterSubCategoria);
        bInsere = (Button) findViewById(R.id.buttonInsereLancamento);
        bInsere.setOnClickListener(this);
        eValorLancamento = (EditText) findViewById(R.id.editTextCrudValorLancamento);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonInsereLancamento:
                Lancamento lancamento = new Lancamento();
                try {
                    lancamento.setValor(Float.parseFloat(eValorLancamento.getText().toString()));
                }catch (Exception e){
                    Toast.makeText(this, "Informe uma valor para o lançamento", Toast.LENGTH_LONG).show();
                    return;
                }
                SubCategoria subCategoria = (SubCategoria) spinnerSubCategorias.getSelectedItem();
                if (subCategoria == null || subCategoria.getId() == null) {
                    Toast.makeText(this, "Selecione uma categoria", Toast.LENGTH_LONG).show();
                    return;
                }
                lancamento.setSubCategoria(subCategoria);
                String mensagem = null;
                if (Boolean.TRUE.equals(lancamentoDAO.insere(lancamento))) {
                    mensagem = "Lançamento salvo com sucesso";
                } else {
                    mensagem = "Problema ao salvar lançamento";
                }
                finish();
                Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
