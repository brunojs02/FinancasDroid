package arq.ifsp.js02.bruno.financasdroid.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import arq.ifsp.js02.bruno.financasdroid.R;
import arq.ifsp.js02.bruno.financasdroid.entities.Lancamento;

/**
 * Created by bruno on 24/06/17.
 */

public class LancamentoAdapter extends ArrayAdapter<Lancamento> {

    Context context;
    Integer layoutResourceId;
    List<Lancamento> lancamentos;

    public LancamentoAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Lancamento> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutResourceId = Integer.valueOf(resource);
        this.lancamentos = objects;
    }

    @Override
    public int getCount() {
        return lancamentos.size();
    }

    @Nullable
    @Override
    public Lancamento getItem(int position) {
        return lancamentos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View row, ViewGroup parent) {
        LancamentoHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new LancamentoHolder();
            holder.textViewNomeCategoria = (TextView) row.findViewById(R.id.textViewNomeCategoria);
            holder.textViewNomeSubCategoria = (TextView) row.findViewById(R.id.textViewNomeSubCategoria);
            holder.textViewValorLancamento = (TextView) row.findViewById(R.id.textViewValorLancamento);
            holder.textViewDataLancamento = (TextView) row.findViewById(R.id.textViewDataLancamento);

            Lancamento lancamento = lancamentos.get(position);

            holder.textViewNomeCategoria.setText(lancamento.getSubCategoria().getCategoria().getNome());
            holder.textViewNomeSubCategoria.setText(lancamento.getSubCategoria().getNome());
            String valor = NumberFormat.getCurrencyInstance().format(lancamento.getValor());
            holder.textViewValorLancamento.setText(valor);

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = null;
            if (Integer.valueOf(cal.get(Calendar.DAY_OF_MONTH)).equals(Integer.valueOf(lancamento.getData()
                    .get(Calendar.DAY_OF_MONTH)))) {
                sdf = new SimpleDateFormat("HH:mm");
            }else{
                sdf = new SimpleDateFormat("dd/MM/yyyy");
            }
            holder.textViewDataLancamento.setText(sdf.format(lancamento.getData().getTime()));
        }
        return row;
    }

    static class LancamentoHolder{
        TextView textViewNomeSubCategoria;
        TextView textViewNomeCategoria;
        TextView textViewDataLancamento;
        TextView textViewValorLancamento;
    }

}
