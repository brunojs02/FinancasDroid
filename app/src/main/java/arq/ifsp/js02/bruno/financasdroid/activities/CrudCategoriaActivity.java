package arq.ifsp.js02.bruno.financasdroid.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import arq.ifsp.js02.bruno.financasdroid.R;
import arq.ifsp.js02.bruno.financasdroid.model.CategoriaDAO;
import arq.ifsp.js02.bruno.financasdroid.model.GerenciaBancoDAO;
import arq.ifsp.js02.bruno.financasdroid.entities.Categoria;
import arq.ifsp.js02.bruno.financasdroid.entities.SubCategoria;

public class CrudCategoriaActivity extends AppCompatActivity implements View.OnClickListener{

    Button bInsere;
    GerenciaBancoDAO banco;
    EditText eTCat;
    Spinner spinnerCat;
    CategoriaDAO categoriaDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        banco = new GerenciaBancoDAO(getBaseContext());
        categoriaDAO = new CategoriaDAO(banco.getWritableDatabase());
        setContentView(R.layout.activity_crud_categoria);
        bInsere = (Button) findViewById(R.id.buttonInsereCategoria);
        bInsere.setOnClickListener(this);
        eTCat = (EditText) findViewById(R.id.editTextCrudValorLancamento);
        spinnerCat = (Spinner) findViewById(R.id.spinnerSubCategoria);
        ArrayAdapter<Categoria> adapterSpinner = new ArrayAdapter<Categoria>(this,
                android.R.layout.simple_spinner_item, categoriaDAO.getCategorias());
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCat.setAdapter(adapterSpinner);
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
            case R.id.buttonInsereCategoria:
                SubCategoria subCategoria = new SubCategoria();
                subCategoria.setNome(eTCat.getText().toString().trim());
                subCategoria.setCategoria(new Categoria());
                subCategoria.getCategoria().setId(((Categoria) spinnerCat.getSelectedItem()).getId());
                String mensagem = null;
                if (Boolean.TRUE.equals(categoriaDAO.insere(subCategoria))) {
                    mensagem = getBaseContext().getString(R.string.categoria_salva);
                } else {
                    mensagem = getBaseContext().getString(R.string.categoria_erro_salvar);
                }
                finish();
                Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
