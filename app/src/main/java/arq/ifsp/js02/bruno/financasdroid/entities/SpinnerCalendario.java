package arq.ifsp.js02.bruno.financasdroid.entities;

import java.util.Calendar;

/**
 * Created by bruno on 25/06/17.
 */

public class SpinnerCalendario {

    private Calendar cal;
    private String titulo;

    public SpinnerCalendario() {}

    public SpinnerCalendario(Calendar cal, String titulo) {
        this.cal = cal;
        this.titulo = titulo;
    }

    public Calendar getCal() {
        return cal;
    }

    public void setCal(Calendar cal) {
        this.cal = cal;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public String toString() {
        return titulo;
    }
}
