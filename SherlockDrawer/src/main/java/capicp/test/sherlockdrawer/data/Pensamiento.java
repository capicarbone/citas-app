package capicp.test.sherlockdrawer.data;

import java.io.Serializable;

/**
 * Created by capi on 28/06/13.
 */
public class Pensamiento implements Serializable{

    private String cita;
    private String autor_nombre;
    private String autor_descripcion;
    private String autor_foto;

    public String getCita() {
        return cita;
    }

    public void setCita(String cita) {
        this.cita = cita;
    }

    public String getAutor_nombre() {
        return autor_nombre;
    }

    public void setAutor_nombre(String autor_nombre) {
        this.autor_nombre = autor_nombre;
    }

    public String getAutor_descripcion() {
        return autor_descripcion;
    }

    public void setAutor_descripcion(String autor_descripcion) {
        this.autor_descripcion = autor_descripcion;
    }

    public String getAutor_foto() {
        return autor_foto;
    }

    public void setAutor_foto(String autor_foto) {
        this.autor_foto = autor_foto;
    }
}
