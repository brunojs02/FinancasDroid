package arq.ifsp.js02.bruno.financasdroid.entities;

/**
 * Created by bruno on 25/06/17.
 */

public class Relatorio {

    private Float valor;
    private String titulo;

    public Relatorio() {}

    public Relatorio(Float valor, String titulo) {
        this.valor = valor;
        this.titulo = titulo;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
