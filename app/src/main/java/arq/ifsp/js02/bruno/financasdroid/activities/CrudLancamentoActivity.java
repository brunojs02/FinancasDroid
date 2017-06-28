package arq.ifsp.js02.bruno.financasdroid.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
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
    Lancamento lancamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        banco = new CriaBanco(getBaseContext());
        categoriaDAO = new CategoriaDAO(banco.getWritableDatabase());
        lancamentoDAO = new LancamentoDAO(banco.getWritableDatabase());
        Intent it = getIntent();
        setContentView(R.layout.activity_crud_lancamento);
        spinnerSubCategorias = (Spinner) findViewById(R.id.spinnerSubCategoria);
        List<SubCategoria> subCategorias = new ArrayList<SubCategoria>();
        subCategorias.add(new SubCategoria(null, getBaseContext().getString(R.string.spinner_item_empty), null, null));
        subCategorias.addAll(categoriaDAO.getSubCategorias());
        ArrayAdapter<SubCategoria> adapterSubCategoria = new ArrayAdapter<SubCategoria>(this,
                android.R.layout.simple_spinner_item, subCategorias);
        adapterSubCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSubCategorias.setAdapter(adapterSubCategoria);
        bInsere = (Button) findViewById(R.id.buttonSalvaLancamento);
        bInsere.setOnClickListener(this);
        eValorLancamento = (EditText) findViewById(R.id.editTextCrudValorLancamento);
        Integer idLancamento = null;
        if (it != null) {
            idLancamento = it.getIntExtra("idLancamento", -1);
            if (idLancamento != null && idLancamento != -1) {
                //fazer busca lançamento
                lancamento = lancamentoDAO.getLancamentoById(idLancamento);
                if (lancamento != null && lancamento.getId() != null) {
                    eValorLancamento.setText(String.valueOf(lancamento.getValor()));
                } else {
                    lancamento = new Lancamento();
                }
            } else {
                lancamento = new Lancamento();
            }
        } else {
            lancamento = new Lancamento();
        }
        if (lancamento.getId() != null) {
            for (int i = 0; i < adapterSubCategoria.getCount(); i++) {
                if (adapterSubCategoria.getItem(i).getId() != null) {
                    if (lancamento.getSubCategoria().getId().equals(adapterSubCategoria.getItem(i).getId())) {
                        spinnerSubCategorias.setSelection(i);
                        break;
                    }
                }
            }
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (lancamento != null && lancamento.getId() != null) {
            getMenuInflater().inflate(R.menu.menu_del, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_del:
                apagarLancamento();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSalvaLancamento:
                salvarLancamento();
                break;
        }
    }

    private void salvarLancamento() {
        String mensagem = null;
        try {
            lancamento.setValor(Float.parseFloat(eValorLancamento.getText().toString()));
        }catch (Exception e){
            Toast.makeText(this, getBaseContext().getString(R.string.lancamento_sem_valor), Toast.LENGTH_LONG).show();
            return;
        }
        SubCategoria subCategoria = (SubCategoria) spinnerSubCategorias.getSelectedItem();
        if (subCategoria == null || subCategoria.getId() == null) {
            Toast.makeText(this, getBaseContext().getString(R.string.categoria_empty), Toast.LENGTH_LONG).show();
            return;
        }
        lancamento.setSubCategoria(subCategoria);
        mensagem = null;
        if (lancamento.getId() != null) {
            if (Boolean.TRUE.equals(lancamentoDAO.update(lancamento))) {
                mensagem = getBaseContext().getString(R.string.lancamento_alterado);
            } else {
                mensagem = getBaseContext().getString(R.string.lancamento_nao_alterado);
            }
        } else {
            if (Boolean.TRUE.equals(lancamentoDAO.insere(lancamento))) {
                mensagem = getBaseContext().getString(R.string.lancamento_salvo);
            } else {
                mensagem = getBaseContext().getString(R.string.lancamento_nao_salvo);
            }
        }
        finish();
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
    }

    private void apagarLancamento() {
        if (lancamento != null && lancamento.getId() != null) {
            if (Boolean.TRUE.equals(lancamentoDAO.delete(lancamento))) {
                Toast.makeText(this, getBaseContext().getString(R.string.lancamento_deletado), Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, getBaseContext().getString(R.string.lancamento_nao_deletado), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
