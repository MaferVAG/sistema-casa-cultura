package modelo;

import java.io.Serializable;

public class Inscripcion implements Serializable {

    private static final long serialVersionUID = 1L;

    private Taller taller;
    private String horario;
    private boolean incluyeMaterial;

    public Inscripcion(Taller taller, String horario, boolean incluyeMaterial){
        this.taller = taller;
        this.horario = horario;
        this.incluyeMaterial = incluyeMaterial;
    }

    public Taller getTaller() {
        return taller;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public boolean isIncluyeMaterial() {
        return incluyeMaterial;
    }

    public void setIncluyeMaterial(boolean incluyeMaterial) {
        this.incluyeMaterial = incluyeMaterial;
    }

    public double getCostoTotal() {
        double costo = taller.getCosto();
        if (incluyeMaterial){
            costo += taller.getCostoMaterial();
        }
        return costo;
    }

    @Override
    public String toString(){
        return taller.getNombre() + " - " + horario;
    }
}
