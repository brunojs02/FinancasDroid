package arq.ifsp.js02.bruno.financasdroid.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bruno on 22/06/17.
 */

public class CriaBanco extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "financasdroid.db";
    private static final Integer VERSAO_BANCO = 11;

    public CriaBanco(Context context) {
        super(context, CriaBanco.NOME_BANCO, null, CriaBanco.VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (String sql:CategoriaDAO.getCreateCategoria()) {
            db.execSQL(sql);
        }
        db.execSQL(LancamentoDAO.getCreateLancamento());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        for (String sql:CategoriaDAO.getDropCategoria()) {
            db.execSQL(sql);
        }
        db.execSQL(LancamentoDAO.getDropLancamento());
        onCreate(db);
    }
}
