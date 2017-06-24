package arq.ifsp.js02.bruno.financasdroid.entities;

/**
 * Created by bruno on 23/06/17.
 */

public class Categoria {

    private Integer id;
    private String nome;

    public Categoria() {}

    public Categoria(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}
