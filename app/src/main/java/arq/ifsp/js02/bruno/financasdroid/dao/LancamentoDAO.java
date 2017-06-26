package arq.ifsp.js02.bruno.financasdroid.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import arq.ifsp.js02.bruno.financasdroid.entities.Categoria;
import arq.ifsp.js02.bruno.financasdroid.entities.Lancamento;
import arq.ifsp.js02.bruno.financasdroid.entities.Relatorio;
import arq.ifsp.js02.bruno.financasdroid.entities.SpinnerCalendario;
import arq.ifsp.js02.bruno.financasdroid.entities.SubCategoria;

/**
 * Created by bruno on 24/06/17.
 */

public class LancamentoDAO {

    SQLiteDatabase db;

    public LancamentoDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public Boolean insere(Lancamento lancamento) {
        ContentValues valores = new ContentValues();
        valores.put("valor", lancamento.getValor());
        valores.put("id_sub_categoria", lancamento.getSubCategoria().getId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        valores.put("data_lancamento", sdf.format(Calendar.getInstance().getTime()));
        long resultado = db.insert("lancamento", null, valores);
        if (resultado != -1) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public List<Lancamento> getLancamentos(Integer idCategoria) {
        List<Lancamento> lancamentos = new ArrayList<Lancamento>();
        StringBuilder sql = new StringBuilder();
        sql.append("select lancamento._id, lancamento.valor, lancamento.data_lancamento, sub_categoria.nome, categoria.nome from lancamento ");
        sql.append("inner join sub_categoria on sub_categoria._id = lancamento.id_sub_categoria ");
        sql.append("inner join categoria on categoria._id = sub_categoria.id_categoria ");
        if (idCategoria != null) {
            sql.append("where categoria._id = " + idCategoria + " ");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sql.append("and strftime('%Y', data_lancamento) = strftime('%Y', '" +  sdf.format(Calendar.getInstance().getTime()) + "') ");
        sql.append("order by lancamento.data_lancamento desc;");
        Cursor cursor = db.rawQuery(sql.toString(), null);
        if (cursor.moveToFirst()) {
            do {
                Lancamento lancamento = new Lancamento();
                Categoria categoria = new Categoria();
                SubCategoria subCategoria = new SubCategoria();
                lancamento.setId(cursor.getInt(0));
                lancamento.setValor(cursor.getFloat(1));
                Calendar calendar = Calendar.getInstance();
                try {
                    calendar.setTime(sdf.parse(cursor.getString(2)));
                }catch (Exception e) {
                    Log.e("ERRO_PARSE_DATA", e.getMessage());
                }
                lancamento.setData(calendar);
                subCategoria.setNome(cursor.getString(3));
                categoria.setNome(cursor.getString(4));
                subCategoria.setCategoria(categoria);
                lancamento.setSubCategoria(subCategoria);
                lancamentos.add(lancamento);
            }while (cursor.moveToNext());
        }

        return lancamentos;
    }

    public List<Relatorio> getRelatorioSubCategoria() {
        StringBuilder sql = new StringBuilder();
        List<Relatorio> relatorios = new ArrayList<>();
        sql.append("select sub_categoria.nome, sum(lancamento.valor) from lancamento ");
        sql.append("inner join sub_categoria on sub_categoria._id = lancamento.id_sub_categoria ");
        sql.append("inner join categoria on categoria._id = sub_categoria.id_categoria ");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sql.append("where strftime('%Y', data_lancamento) = strftime('%Y', '" +  sdf.format(Calendar.getInstance().getTime()) + "') ");
        sql.append("group by sub_categoria._id;");
        Cursor cursor = db.rawQuery(sql.toString(), null);
        if (cursor.moveToFirst()) {
            do {
                relatorios.add(new Relatorio(cursor.getFloat(1), cursor.getString(0)));
            }while (cursor.moveToNext());
        }
        return relatorios;
    }

    public List<Relatorio> getRelatorioCategoria(SpinnerCalendario spinnerCalendario) {
        StringBuilder sql = new StringBuilder();
        List<Relatorio> relatorios = new ArrayList<>();
        sql.append("select categoria.nome, sum(lancamento.valor) from lancamento ");
        sql.append("inner join sub_categoria on sub_categoria._id = lancamento.id_sub_categoria ");
        sql.append("inner join categoria on categoria._id = sub_categoria.id_categoria ");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (spinnerCalendario != null && spinnerCalendario.getCal() != null) {
            sql.append("where strftime('%m', data_lancamento) = strftime('%m', '" +  sdf.format(spinnerCalendario.getCal().getTime()) + "') ");
        }else{
            sql.append("where strftime('%Y', data_lancamento) = strftime('%Y', '" +  sdf.format(Calendar.getInstance().getTime()) + "') ");
        }
        sql.append("group by categoria._id;");
        Cursor cursor = db.rawQuery(sql.toString(), null);
        if (cursor.moveToFirst()) {
            do {
                relatorios.add(new Relatorio(cursor.getFloat(1), cursor.getString(0)));
            }while (cursor.moveToNext());
        }
        return relatorios;
    }

    public static String getCreateLancamento() {
        StringBuilder sql = new StringBuilder();
        sql.append("create table lancamento (");
        sql.append("_id Integer not null primary key autoincrement, ");
        sql.append("valor float not null, ");
        sql.append("id_sub_categoria Integer not null, ");
        sql.append("data_lancamento datetime not null, ");
        sql.append("foreign key(id_sub_categoria) references sub_categoria(_id) );");
        return sql.toString();
    }

    public static String getDropLancamento() {
        return "drop table if exists lancamento";
    }
}
