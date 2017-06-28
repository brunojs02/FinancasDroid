package arq.ifsp.js02.bruno.financasdroid.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import arq.ifsp.js02.bruno.financasdroid.R;
import arq.ifsp.js02.bruno.financasdroid.model.GerenciaBancoDAO;
import arq.ifsp.js02.bruno.financasdroid.model.LancamentoDAO;
import arq.ifsp.js02.bruno.financasdroid.entities.Relatorio;
import arq.ifsp.js02.bruno.financasdroid.entities.RelatorioValorFormatter;

public class MainActivity extends AppCompatActivity {

    GerenciaBancoDAO banco;
    LancamentoDAO lancamentoDAO;
    List<Relatorio> relatorios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        banco = new GerenciaBancoDAO(getBaseContext());
        lancamentoDAO = new LancamentoDAO(banco.getWritableDatabase());
        setContentView(R.layout.main_activity);
        setupPieChart();
    }

    private void setupPieChart() {
        PieChart pieChart = (PieChart) findViewById(R.id.pieChart);
        TextView textView = (TextView) findViewById(R.id.pieChartEmpty);
        relatorios = lancamentoDAO.getRelatorioSubCategoria();
        if (relatorios.size() == 0) {
            pieChart.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.VISIBLE);
            return;
        } else {
            textView.setVisibility(View.INVISIBLE);
            pieChart.setVisibility(View.VISIBLE);
        }
        List<PieEntry> pieEntries = new ArrayList<>();
        for(Relatorio relatorio:relatorios) {
            pieEntries.add(new PieEntry(relatorio.getValor(), relatorio.getTitulo()));
        }
        PieDataSet pieDataSet = new PieDataSet(pieEntries, getBaseContext().getString(R.string.relatorio));
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextSize(18);
        pieDataSet.setValueFormatter(new RelatorioValorFormatter());
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.setEntryLabelTextSize(18);
        pieChart.invalidate();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setupPieChart();
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
                it = new Intent(MainActivity.this, RelatorioActivity.class);
                startActivity(it);
                break;
        }
        return true;
    }
}
