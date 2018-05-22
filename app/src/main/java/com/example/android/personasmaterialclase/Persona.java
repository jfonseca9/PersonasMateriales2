package com.example.android.personasmaterialclase;

/**
 * Created by android on 17/10/2017.
 */

public class Persona {
    private String id;
    private String foto;
    private String cedula;
    private String nombre;
    private String apellido;
    private int sexo;

    public Persona(){
    }

    public Persona(String foto, String cedula, String nombre, String apellido, int sexo) {
        this.foto = foto;
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.sexo = sexo;
    }
    public Persona(String foto, String cedula, String nombre, String apellido) {
        this.foto = foto;
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;

    }

    public Persona(String id,String foto, String cedula, String nombre, String apellido) {
        this.id=id;
        this.foto = foto;
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;

    }
    public Persona(String id){
        this.id=id;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getSexo() {
        return sexo;
    }

    public void setSexo(int sexo) {
        this.sexo = sexo;
    }

    public void guardar(){
        Datos.guardarPersona(this);
    }

    public void modificar(){Datos.actualizar(this);}

    public void eliminar(){Datos.eliminar(this);}


}
