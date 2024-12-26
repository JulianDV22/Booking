package modelo.entidades;

import util.UtilObjeto;
import util.UtilTexto;

import java.time.LocalDate;

public class Cliente {
    private String nombre;
    private String apellido;
    private String email;
    private String nacionalidad;
    private String telefono;
    private LocalDate fechaNacimiento;

    // Constructor
    private Cliente(String nombre, String apellido, String email, String nacionalidad, String telefono, LocalDate fechaNacimiento) {
        setNombre(nombre);
        setApellido(apellido);
        setEmail(email);
        setNacionalidad(nacionalidad);
        setTelefono(telefono);
        setFechaNacimiento(fechaNacimiento);
    }

    private Cliente(final int numero) {
        setNombre(UtilTexto.EMPTY);
        setApellido(UtilTexto.EMPTY);
        setEmail(UtilTexto.EMPTY);
        setNacionalidad(UtilTexto.EMPTY);
        setTelefono(UtilTexto.EMPTY);
        setFechaNacimiento(LocalDate.now());
    }

    // Builders
    public static Cliente build(final String nombre, final String apellido, final String email, final String nacionalidad, final String telefono, final LocalDate fechaNacimiento){
        return new Cliente(nombre, apellido, email, nacionalidad, telefono, fechaNacimiento);
    }

    public static Cliente build(){
        return new Cliente(0);
    }

    // Getters y Setters (Encapsulamiento)
    public String getNombre() {
        return nombre;
    }

    private Cliente setNombre(String nombre) {
        this.nombre = UtilTexto.applyTrim(nombre);
        return  this;
    }

    public String getApellido() {
        return apellido;
    }

    private Cliente setApellido(String apellido) {
        this.apellido = UtilTexto.applyTrim(apellido);
        return this;
    }

    public String getEmail() {
        return email;
    }

    private Cliente setEmail(String email) {
        this.email = UtilTexto.applyTrim(email);
        return this;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    private Cliente setNacionalidad(String nacionalidad) {
        this.nacionalidad = UtilTexto.applyTrim(nacionalidad);
        return this;
    }

    public String getTelefono() {
        return telefono;
    }

    private Cliente setTelefono(String telefono) {
        this.telefono = UtilTexto.applyTrim(telefono);
        return this;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    private Cliente setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = UtilObjeto.getUtilObjeto().getDefault(fechaNacimiento, LocalDate.now());
        return this;
    }
}

