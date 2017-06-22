package arq.ifsp.js02.bruno.financasdroid.entities;

import java.util.Calendar;

/**
 * Created by bruno on 22/06/17.
 */

public class Categoria {

    private Integer _id;
    private Integer idCategoria;
    private String nome;
    private Calendar dataCadastro;

    public Integer get_id() { return _id; }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public Integer getIdCategoria() { return idCategoria; }

    public void setIdCategoria(Integer idCategoria) { this.idCategoria = idCategoria; }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Calendar getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Calendar dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
