package datos;

import modelo.Asistente;
import modelo.Taller;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class GestorArchivos {

    private static final String ARCHIVO_ASISTENTES = "asistente.dat";
    private static final String ARCHIVO_ADMIN = "administra.dat";
    private static final String ARCHIVO_TALLERES = "talleres.dat";

    //ADMINISTRADOR
    private static void guardarAdmin(String usuario, String contrasena){
        try(ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(ARCHIVO_ADMIN))){
            oos.writeObject(usuario);
            oos.writeObject(contrasena);
        }
        catch(IOException e){
            System.out.println("Error al guardar admin: " + e.getMessage());
        }
    }

    public static String[] leerAdmin() {
        File archivo = new File(ARCHIVO_ADMIN);
        if (!archivo.exists()){
            guardarAdmin("admin", "1234");
            return new String[]{"admin", "1234"};
        }
        try(ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(ARCHIVO_ADMIN))) {
            String usuario = (String) ois.readObject();
            String contrasena = (String) ois.readObject();
            return new String[]{usuario, contrasena};
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al leer admin: " + e.getMessage());
            return  new String[]{"admin", "1234"};
        }
    }

    //ASISTENTES
    public static void  guardarAsistentes(ArrayList<Asistente> lista) {
        try(ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(ARCHIVO_ASISTENTES))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            System.out.println("Error al guardar asistente: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Asistente> leerAsistentes() {
        File archivo = new File(ARCHIVO_ASISTENTES);
        if (!archivo.exists())
            return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(ARCHIVO_ASISTENTES))) {
            return (ArrayList<Asistente>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al leer asistentes: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    //TALLERES
    public static void guardarTalleres(ArrayList<Taller> lista) {
        try(ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(ARCHIVO_TALLERES))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            System.out.println("Error al guardar talleres: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Taller> leerTalleres() {
        File archivo = new File(ARCHIVO_TALLERES);
        if (!archivo.exists()){
            return crearTalleresPorDefecto();
        }
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(ARCHIVO_TALLERES))) {
            return (ArrayList<Taller>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al leer talleres: " + e.getMessage());
            return crearTalleresPorDefecto();
        }
    }

    private static ArrayList<Taller> crearTalleresPorDefecto() {
        ArrayList<Taller> talleres = new ArrayList<>();

        Taller teatro = new Taller("Teatro", "Pérez, R.", 350, 180);
        teatro.agregarHorario("Lunes y Miércoles 10:00-12:00");
        teatro.agregarHorario("Martes y Jueves 17:00-19:00");

        Taller danza = new Taller("Danza", "González, L.", 320, 200);
        danza.agregarHorario("Martes y Jueves 16:00-18:00");
        danza.agregarHorario("Sábado 10:00-12:00");

        Taller lectura = new Taller("Lectura", "Ruiz, M.", 280, 120);
        lectura.agregarHorario("Viernes 09:00-11:00");
        lectura.agregarHorario("Miércoles 15:00-17:00");

        Taller dibujo = new Taller("Artes", "Torres, A.", 380, 250);
        dibujo.agregarHorario("Sábado 10:00-13:00");
        dibujo.agregarHorario("Viernes 12:00-13:00");

        talleres.add(teatro);
        talleres.add(danza);
        talleres.add(lectura);
        talleres.add(dibujo);

        guardarTalleres(talleres);
        return talleres;
    }
}
