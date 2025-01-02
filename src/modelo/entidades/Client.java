package modelo.entidades;

import util.UtilObject;
import util.UtilText;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private String name;
    private String lastName;
    private String email;
    private String nationality;
    private String phoneNumber;
    private LocalDate birthday;
    private static final UtilObject utilObject = UtilObject.getInstance();
    private static final UtilText utilText = UtilText.getInstance();

    // Constructor
    private Client(String name, String lastName, String email, String nationality, String phoneNumber, LocalDate birthday) {
        setName(name);
        setLastName(lastName);
        setEmail(email);
        setNationality(nationality);
        setPhoneNumber(phoneNumber);
        setBirthday(birthday);
    }

    private Client() {
        setName(utilText.EMPTY);
        setLastName(utilText.EMPTY);
        setEmail(utilText.EMPTY);
        setNationality(utilText.EMPTY);
        setPhoneNumber(utilText.EMPTY);
        setBirthday(LocalDate.now());
    }

    // Builders
    public static Client build(final String name, final String lastName, final String email, final String nationality, final String phoneNumber, final LocalDate birthday){
        return new Client(name, lastName, email, nationality, phoneNumber, birthday);
    }

    public static Client build(){
        return new Client();
    }

    // Getters y Setters
    public String getName() {
        return name;
    }

    private Client setName(String name) {
        this.name = utilText.applyTrim(name);
        return  this;
    }

    public String getLastName() {
        return lastName;
    }

    private Client setLastName(String lastName) {
        this.lastName = utilText.applyTrim(lastName);
        return this;
    }

    public String getEmail() {
        return email;
    }

    private Client setEmail(String email) {
        this.email = utilText.applyTrim(email);
        return this;
    }

    public String getNationality() {
        return nationality;
    }

    private Client setNationality(String nationality) {
        this.nationality = utilText.applyTrim(nationality);
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    private Client setPhoneNumber(String phoneNumber) {
        this.phoneNumber = utilText.applyTrim(phoneNumber);
        return this;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    private Client setBirthday(LocalDate birthday) {
        this.birthday = utilObject.getUtilObjeto().getDefault(birthday, LocalDate.now());
        return this;
    }

    // Convierte un objeto Cliente a una fila de Excel (toRow)
    public List<String> toRow() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<String> row = new ArrayList<>();
        row.add(name);
        row.add(lastName);
        row.add(email);
        row.add(nationality);
        row.add(phoneNumber);
        row.add(birthday.format(formatter));
        return row;
    }

    // Crea un objeto Cliente desde una fila de Excel (fromRow)
    public static Client fromRow(List<String> row) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return new Client(
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
                "nombre='" + name + '\'' +
                ", apellido='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", nacionalidad='" + nationality + '\'' +
                ", telefono='" + phoneNumber + '\'' +
                ", fechaNacimiento=" + birthday +
                '}';
    }
}

