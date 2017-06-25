package arq.ifsp.js02.bruno.financasdroid.entities;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by bruno on 25/06/17.
 */

public class RelatorioValorFormatter implements IValueFormatter {

    private DecimalFormat decimalFormat;

    public RelatorioValorFormatter() {
        decimalFormat = new DecimalFormat("###,###,##0.0");
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return NumberFormat.getCurrencyInstance().format(value);
    }
}
