package arq.ifsp.js02.bruno.financasdroid.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import arq.ifsp.js02.bruno.financasdroid.entities.Categoria;

/**
 * Created by bruno on 22/06/17.
 */

public class CategoriaDAO {

    private SQLiteDatabase db;

    public CategoriaDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public Boolean insere(Categoria categoria) {
        ContentValues valores = new ContentValues();
        valores.put("nome", categoria.getNome());
        valores.put("id_categoria", categoria.getIdCategoria());
        valores.put("data_cadastro", "datetime('now')");
        long resultado = db.insert("sub_categoria", null, valores);
        if (resultado != -1) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public static String getCreateCategoria() {
        StringBuilder sql = new StringBuilder();
        sql.append("create table categoria (");
        sql.append("_id Integer not null primary key autoincrement, ");
        sql.append("nome varchar(10) not null, ");
        sql.append("data_cadastro datetime not null );");
        sql.append("insert into categoria (nome, data_cadastro) values (");
        sql.append("'Credito', ");
        sql.append("datetime('now') );");
        sql.append("insert into categoria (nome, data_cadastro) values (");
        sql.append("'Debito', ");
        sql.append("datetime('now') );");
        sql.append("create table sub_categoria (");
        sql.append("_id Integer not null primary key autoincrement, ");
        sql.append("nome varchar(30) not null, ");
        sql.append("id_categoria Integer not null, ");
        sql.append("data_cadastro datetime not null, ");
        sql.append("foreign key(id_categoria) references categoria(_id) );");
        return sql.toString();
    }

    public static String getDropCategoria() {
        return "drop table if exists categoria; drop table if exists sub_categoria";
    }
}
