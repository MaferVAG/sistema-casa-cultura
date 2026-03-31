package modelo;
import vistas.PanelInscripcion;

import java.io.Serializable;
import java.util.ArrayList;

public class Asistente implements Serializable {

    private static final long seralVersionUID = 1L;

    private String id;
    private String nombre;
    private String apellidoP;
    private String apellidoM;
    private int edad;
    private String genero;
    private String direccion;
    private String telContacto;
    private String telEmergencia;
    private ArrayList<Inscripcion> inscripciones;

    public Asistente(String id, String nombre, String apellidoP, String apellidoM,
                     int edad, String genero, String direccion, String telContacto,
                     String telEmergencia) {
        this.id = id;
        this.nombre = nombre;
        this.apellidoP = apellidoP;
        this.apellidoM = apellidoM;
        this.edad = edad;
        this.genero = genero;
        this.direccion = direccion;
        this.telContacto = telContacto;
        this.telEmergencia = telEmergencia;
        this.inscripciones = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoP() {
        return apellidoP;
    }

    public void setApellidoP(String apellidoP) {
        this.apellidoP = apellidoP;
    }

    public String getApellidoM() {
        return apellidoM;
    }

    public void setApellidoM(String apellidoM) {
        this.apellidoM = apellidoM;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelContacto() {
        return telContacto;
    }

    public void setTelContacto(String telContacto) {
        this.telContacto = telContacto;
    }

    public String getTelEmergencia() {
        return telEmergencia;
    }

    public void setTelEmergencia(String telEmergencia) {
        this.telEmergencia = telEmergencia;
    }

    public ArrayList<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    public void agregarInscripcion(Inscripcion i){
        inscripciones.add(i);
    }

    public void eliminarInscripcion(Inscripcion i){
        inscripciones.remove(i);
    }


    public String getNombreCompleto() {
        return nombre + " " + apellidoP + " " + apellidoM;
    }

    public double calcularMensualidad(){
        double total = 0;
        for (Inscripcion i : inscripciones) {
            total += i.getCostoTotal();
        }
        return total;
    }
}
