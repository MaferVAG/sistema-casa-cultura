package modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Taller implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombre;
    private String instructor;
    private double costo;
    private double costoMaterial;
    private ArrayList<String> horarios;

    public Taller(String nombre, String instructor, double costo, double costoMaterial){
        this.nombre = nombre;
        this.instructor = instructor;
        this.costo = costo;
        this.costoMaterial = costoMaterial;
        this.horarios = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getInstructor() {
        return instructor;
    }

    public double getCosto() {
        return costo;
    }

    public double getCostoMaterial() {
        return costoMaterial;
    }

    public ArrayList<String> getHorarios() {
        return horarios;
    }

    public void agregarHorario(String horario){
        horarios.add(horario);
    }

    @Override
    public String toString(){
        return nombre;
    }
}
