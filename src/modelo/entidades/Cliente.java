package modelo.entidades;

import util.UtilObjeto;
import util.UtilTexto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

    // Convierte un objeto Cliente a una fila de Excel (toRow)
    public List<String> toRow() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<String> row = new ArrayList<>();
        row.add(nombre);
        row.add(apellido);
        row.add(email);
        row.add(nacionalidad);
        row.add(telefono);
        row.add(fechaNacimiento.format(formatter));
        return row;
    }

    // Crea un objeto Cliente desde una fila de Excel (fromRow)
    public static Cliente fromRow(List<String> row) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return new Cliente(
                row.get(0), // Nombre
                row.get(1), // Apellido
                row.get(2), // Email
                row.get(3), // Nacionalidad
                row.get(4), // Teléfono
                LocalDate.parse(row.get(5), formatter) // Fecha de Nacimiento
        );
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", nacionalidad='" + nacionalidad + '\'' +
                ", telefono='" + telefono + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                '}';
    }
}

