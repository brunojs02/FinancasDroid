package arq.ifsp.js02.bruno.financasdroid.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import arq.ifsp.js02.bruno.financasdroid.R;
import arq.ifsp.js02.bruno.financasdroid.dao.CategoriaDAO;
import arq.ifsp.js02.bruno.financasdroid.dao.CriaBanco;
import arq.ifsp.js02.bruno.financasdroid.dao.LancamentoDAO;
import arq.ifsp.js02.bruno.financasdroid.entities.Categoria;
import arq.ifsp.js02.bruno.financasdroid.entities.Relatorio;
import arq.ifsp.js02.bruno.financasdroid.entities.RelatorioValorFormatter;
import arq.ifsp.js02.bruno.financasdroid.entities.SpinnerCalendario;

public class RelatorioMensalActivity extends AppCompatActivity implements View.OnClickListener{

    CriaBanco banco;
    LancamentoDAO lancamentoDAO;
    CategoriaDAO categoriaDAO;
    List<Relatorio> relatorios;
    Spinner spinnerCategoria;
    Button bFiltrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        banco = new CriaBanco(getBaseContext());
        lancamentoDAO = new LancamentoDAO(banco.getWritableDatabase());
        categoriaDAO = new CategoriaDAO(banco.getWritableDatabase());
        setContentView(R.layout.activity_relatorio_mensal);
        bFiltrar = (Button) findViewById(R.id.buttonFiltrar);
        bFiltrar.setOnClickListener(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        spinnerCategoria = (Spinner) findViewById(R.id.spinnerMes);
        List<SpinnerCalendario> spinnerCalendarios = new ArrayList<>();
        spinnerCalendarios.add(new SpinnerCalendario(null, "Selecione..."));
        for(int count = 0; count < 12; count ++) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MONTH, count);
            SpinnerCalendario spinnerCalendario = new SpinnerCalendario();
            spinnerCalendario.setCal(calendar);
            String nome = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, new Locale( "pt", "BR" ));
            nome = Character.toString(nome.charAt(0)).toUpperCase()+nome.substring(1);
            spinnerCalendario.setTitulo(nome);

            spinnerCalendarios.add(spinnerCalendario);
        }
        ArrayAdapter<SpinnerCalendario> adapterSpinner = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, spinnerCalendarios);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapterSpinner);
        setupPieChart(null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent it;
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    private void setupPieChart(SpinnerCalendario spinnerCalendario) {
        List<PieEntry> pieEntries = new ArrayList<>();
        relatorios = lancamentoDAO.getRelatorioCategoria(spinnerCalendario);
        for(Relatorio relatorio:relatorios) {
            pieEntries.add(new PieEntry(relatorio.getValor(), relatorio.getTitulo()));
        }
        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Relatório por categoria");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextSize(18);
        pieDataSet.setValueFormatter(new RelatorioValorFormatter());
        PieData pieData = new PieData(pieDataSet);
        PieChart pieChart = (PieChart) findViewById(R.id.chartRelatorioMensal);
        pieChart.setData(pieData);
        pieChart.setEntryLabelTextSize(18);
        pieChart.invalidate();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonFiltrar:
                setupPieChart((SpinnerCalendario) spinnerCategoria.getSelectedItem());
                break;
        }
    }
}
