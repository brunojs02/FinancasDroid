package arq.ifsp.js02.bruno.financasdroid.entities;

import java.util.Calendar;

/**
 * Created by bruno on 24/06/17.
 */

public class Lancamento {

    private Integer id;
    private Calendar data;
    private SubCategoria subCategoria;
    private Double valor;

    public Lancamento() {}

    public Lancamento(Integer id, Calendar data, SubCategoria subCategoria, Double valor) {
        this.id = id;
        this.data = data;
        this.subCategoria = subCategoria;
        this.valor = valor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }

    public SubCategoria getSubCategoria() {
        return subCategoria;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void setSubCategoria(SubCategoria subCategoria) {
        this.subCategoria = subCategoria;
    }
}
