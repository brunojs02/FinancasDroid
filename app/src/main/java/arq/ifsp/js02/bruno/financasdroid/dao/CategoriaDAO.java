package arq.ifsp.js02.bruno.financasdroid.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import arq.ifsp.js02.bruno.financasdroid.entities.Categoria;
import arq.ifsp.js02.bruno.financasdroid.entities.SubCategoria;

/**
 * Created by bruno on 22/06/17.
 */

public class CategoriaDAO {

    private SQLiteDatabase db;

    public CategoriaDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public List<Categoria> getCategorias() {
        List<Categoria> labels = new ArrayList<Categoria>();
        Cursor cursor = db.rawQuery("select * from categoria", null);
        if (cursor.moveToFirst()) {
            do {
                labels.add(new Categoria(cursor.getInt(0), cursor.getString(1)));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return labels;
    }

    public List<SubCategoria> getSubCategorias() {
        List<SubCategoria> subCategorias = new ArrayList<SubCategoria>();
        StringBuilder sql = new StringBuilder();
        sql.append("select sub_categoria._id, sub_categoria.nome, sub_categoria.data_cadastro, " +
                "categoria.nome from sub_categoria ");
        sql.append("inner join categoria on categoria._id = sub_categoria.id_categoria ");
        sql.append("order by sub_categoria.nome asc");
        Cursor cursor = db.rawQuery(sql.toString(), null);
        if(cursor.moveToFirst()) {
            do {
                Categoria categoria = new Categoria();
                categoria.setNome(cursor.getString(3));
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    calendar.setTime(sdf.parse(cursor.getString(2)));
                }catch (Exception e) {
                    Log.e("ERRO_PARSE_DATA", e.getMessage());
                }
                subCategorias.add(new SubCategoria(cursor.getInt(0), cursor.getString(1), calendar, categoria));
            }while (cursor.moveToNext());
        }

        return subCategorias;
    }

    public Boolean insere(SubCategoria subCategoria) {
        ContentValues valores = new ContentValues();
        valores.put("nome", subCategoria.getNome());
        valores.put("id_categoria", subCategoria.getCategoria().getId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        valores.put("data_cadastro", sdf.format(Calendar.getInstance().getTime()));
        long resultado = db.insert("sub_categoria", null, valores);
        if (resultado != -1) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public static List<String> getCreateCategoria() {
        StringBuilder sql = new StringBuilder();
        List<String> sqlQuerys = new ArrayList<String>();
        sql.append("create table categoria (");
        sql.append("_id Integer not null primary key autoincrement, ");
        sql.append("nome varchar(10) not null, ");
        sql.append("data_cadastro datetime not null );");
        sqlQuerys.add(sql.toString());
        sql = new StringBuilder();
        sql.append("insert into categoria (nome, data_cadastro) values (");
        sql.append("'Credito', ");
        sql.append("datetime('now') );");
        sqlQuerys.add(sql.toString());
        sql = new StringBuilder();
        sql.append("insert into categoria (nome, data_cadastro) values (");
        sql.append("'Debito', ");
        sql.append("datetime('now') );");
        sqlQuerys.add(sql.toString());
        sql = new StringBuilder();
        sql.append("create table sub_categoria (");
        sql.append("_id Integer not null primary key autoincrement, ");
        sql.append("nome varchar(30) not null, ");
        sql.append("id_categoria Integer not null, ");
        sql.append("data_cadastro datetime default current_timestamp not null, ");
        sql.append("foreign key(id_categoria) references categoria(_id) );");
        sqlQuerys.add(sql.toString());
        return sqlQuerys;
    }

    public static List<String> getDropCategoria() {
        List<String> sqlQuerys = new ArrayList<String>();
        sqlQuerys.add("drop table if exists categoria;");
        sqlQuerys.add("drop table if exists sub_categoria;");
        return sqlQuerys;
    }
}
