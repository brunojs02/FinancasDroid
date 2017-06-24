package arq.ifsp.js02.bruno.financasdroid.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import arq.ifsp.js02.bruno.financasdroid.R;
import arq.ifsp.js02.bruno.financasdroid.entities.Categoria;
import arq.ifsp.js02.bruno.financasdroid.entities.SubCategoria;

/**
 * Created by bruno on 23/06/17.
 */

public class CategoriaAdapter extends ArrayAdapter<SubCategoria> {

    Context context;
    Integer layoutResourceId;
    List<SubCategoria> categorias;

    public CategoriaAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<SubCategoria> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutResourceId = Integer.valueOf(resource);
        this.categorias = objects;
    }

    @Override
    public View getView(int position, View row, ViewGroup parent) {
        CategoriaHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new CategoriaHolder();
            holder.textViewNomeSubCategoria = (TextView) row.findViewById(R.id.textViewNomeSubCategoria);
            holder.textViewNomeCategoria = (TextView) row.findViewById(R.id.textViewNomeCategoria);
            holder.textViewDataInsercaoSubCategoria = (TextView) row.findViewById(R.id.textViewDataInsercaoSubCategoria);
            holder.textViewIdSubCategoria = (TextView) row.findViewById(R.id.textViewIdSubCategoria);
        }
        else{
            holder = (CategoriaHolder)row.getTag();
        }
        SubCategoria subCategoria = categorias.get(position);
        holder.textViewNomeSubCategoria.setText(subCategoria.getNome());
        holder.textViewNomeCategoria.setText(subCategoria.getCategoria().getNome());
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = null;
        if (Integer.valueOf(cal.get(Calendar.DAY_OF_MONTH)).equals(Integer.valueOf(subCategoria.getDataCadastro()
                .get(Calendar.DAY_OF_MONTH)))) {
            sdf = new SimpleDateFormat("HH:mm");
        }else{
            sdf = new SimpleDateFormat("dd/MM/yyyy");
        }
        holder.textViewDataInsercaoSubCategoria.setText(sdf.format(subCategoria.getDataCadastro().getTime()));
        holder.textViewIdSubCategoria.setText(subCategoria.getId().toString());
        return row;
    }

    static class CategoriaHolder{
        TextView textViewNomeSubCategoria;
        TextView textViewNomeCategoria;
        TextView textViewDataInsercaoSubCategoria;
        TextView textViewIdSubCategoria;
    }
}
