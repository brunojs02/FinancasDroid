package arq.ifsp.js02.bruno.financasdroid.entities;

import java.util.Calendar;

/**
 * Created by bruno on 22/06/17.
 */

public class SubCategoria {

    private Integer id;
    private Categoria categoria;
    private String nome;
    private Calendar dataCadastro;

    public SubCategoria() {
    }

    public SubCategoria(Integer id, String nome, Calendar dataCadastro, Categoria categoria) {
        this.id = id;
        this.categoria = categoria;
        this.nome = nome;
        this.dataCadastro = dataCadastro;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setId(String idString) {
        Integer id = null;
        try{
            id = Integer.parseInt(idString);
        }catch (Exception e){}
        this.id = id;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

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
