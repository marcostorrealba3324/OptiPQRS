package tickets;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import usuarios.GuardarUsuarios;

// Clase encargada de manejar los tickets y los contadores individuales
public class GestionTickets {

    // --- Generar un nuevo ticket ---
    public static void generarTicket(Map<String, String> usuarios, String cedula, int tipo, String descripcion) {

        // Variables base
        String tipoTexto = "";
        String prefijo = "";
        String archivoContador = "";

        // Asignar valores según el tipo de PQRS
        if (tipo == 1) {
            tipoTexto = "Petición";
            prefijo = "P-";
            archivoContador = "contador_P.txt";
        } else if (tipo == 2) {
            tipoTexto = "Queja";
            prefijo = "Q-";
            archivoContador = "contador_Q.txt";
        } else if (tipo == 3) {
            tipoTexto = "Reclamo";
            prefijo = "R-";
            archivoContador = "contador_R.txt";
        } else if (tipo == 4) {
            tipoTexto = "Sugerencia";
            prefijo = "S-";
            archivoContador = "contador_S.txt";
        }

        // Cargar contador correspondiente
        int contador = cargarContador(archivoContador);

        // Crear código del ticket (ej: P-0001)
        String ticketID = prefijo + String.format("%04d", contador);

        // Incrementar y guardar nuevo valor
        contador++;
        guardarContador(archivoContador, contador);

        // Obtener nombre y apellido del usuario
        String datos = usuarios.get(cedula);
        String[] partes = datos.split(",");
        String nombre = partes[0];
        String apellido = partes.length > 1 ? partes[1] : "";

        // Obtener la fecha actual
        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

        // Mostrar ticket generado
        System.out.println("\n===== TICKET GENERADO =====");
        System.out.println("Número de ticket: " + ticketID);
        System.out.println("Fecha: " + fecha);
        System.out.println("Usuario: " + nombre + " " + apellido + " (" + cedula + ")");
        System.out.println("Tipo: " + tipoTexto);
        System.out.println("Descripción: " + descripcion);
        System.out.println("============================\n");

        // Guardar ticket en archivo
        guardarTicket(ticketID, cedula, nombre + " " + apellido, tipoTexto, descripcion, fecha);
    }

    // --- Guardar ticket ---
    public static void guardarTicket(String id, String cedula, String nombre, String tipo, String descripcion, String fecha) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("tickets/tickets.txt", true))) {
            bw.write(id + "," + cedula + "," + nombre + "," + tipo + "," + descripcion + "," + fecha);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar el ticket.");
        }
    }

    // --- Guardar contador ---
    public static void guardarContador(String archivo, int contador) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("tickets/" +archivo))) {
            bw.write(String.valueOf(contador));
        } catch (IOException e) {
            System.out.println("Error al guardar contador en " + archivo);
        }
    }

    // --- Cargar contador ---
    public static int cargarContador(String archivo) {
        int contador = 1;
        try (BufferedReader br = new BufferedReader(new FileReader("tickets/" + archivo))) {
            String linea = br.readLine();
            if (linea != null) contador = Integer.parseInt(linea);
        } catch (FileNotFoundException e) {
            guardarContador(archivo, 1); // crea el archivo si no existe
        } catch (IOException e) {
            System.out.println("Error al cargar contador en " + archivo);
        }
        return contador;
    }
public static void mostrarTodosLosTickets() {
    try (BufferedReader br = new BufferedReader(new FileReader("tickets/tickets.txt"))) {
        String linea;
        System.out.println("\n===== LISTA DE TICKETS =====");
        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split(",");
            if (partes.length >= 6) {
                System.out.println("ID: " + partes[0]);
                System.out.println("Cédula: " + partes[1]);
                System.out.println("Usuario: " + partes[2]);
                System.out.println("Tipo: " + partes[3]);
                System.out.println("Descripción: " + partes[4]);
                System.out.println("Fecha: " + partes[5]);
                System.out.println("-----------------------------");
            }
        }
    } catch (FileNotFoundException e) {
        System.out.println("No hay tickets registrados."); 
    }
    catch (IOException e) {
        System.out.println("Error al leer los tickets.");
    }
}
    // --- Eliminar todos los tickets ---
    public static void eliminarTickets() {
        File archivo = new File("tickets/tickets.txt");
        if (archivo.delete()) {
            System.out.println("Todos los tickets han sido eliminados.");
        } else {
            System.out.println("No se pudieron eliminar los tickets o no existen.");
        }
    }
}
