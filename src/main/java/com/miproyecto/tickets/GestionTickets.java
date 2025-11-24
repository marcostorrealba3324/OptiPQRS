package tickets;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import usuarios.GuardarUsuarios;

public class GestionTickets {


    public static void generarTicket(Map<String, String> usuarios, String cedula, int tipo, String descripcion) {


        String tipoTexto = "";
        String prefijo = "";
        String archivoContador = "";


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


        int contador = cargarContador(archivoContador);


        String ticketID = prefijo + String.format("%04d", contador);


        contador++;
        guardarContador(archivoContador, contador);


        String datos = usuarios.get(cedula);
        String[] partes = datos.split(",");
        String nombre = partes[0];
        String apellido = partes.length > 1 ? partes[1] : "";


        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));


        System.out.println("\n===== TICKET GENERADO =====");
        System.out.println("Número de ticket: " + ticketID);
        System.out.println("Fecha: " + fecha);
        System.out.println("Usuario: " + nombre + " " + apellido + " (" + cedula + ")");
        System.out.println("Tipo: " + tipoTexto);
        System.out.println("Descripción: " + descripcion);
        System.out.println("============================\n");

        System.out.println("En 15 días hábiles se le estará respondiendo a su solicitud.\n");


        guardarTicket(ticketID, cedula, nombre + " " + apellido, tipoTexto, descripcion, fecha, estado);
    }


    public static void guardarTicket(String id, String cedula, String nombre, String tipo, String descripcion, String fecha) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("tickets/tickets.txt", true))) {
            bw.write(id + "," + cedula + "," + nombre + "," + tipo + "," + descripcion + "," + fecha + ",NO RESPONDIDO");
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar el ticket.");
        }
    }


    public static void guardarContador(String archivo, int contador) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("tickets/" +archivo))) {
            bw.write(String.valueOf(contador));
        } catch (IOException e) {
            System.out.println("Error al guardar contador en " + archivo);
        }
    }


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
                System.out.println("Estado: " + partes[6]);
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

    public static void eliminarTickets() {
        File archivo = new File("tickets/tickets.txt");
        if (archivo.delete()) {
            System.out.println("Todos los tickets han sido eliminados.");
        } else {
            System.out.println("No se pudieron eliminar los tickets o no existen.");
        }
    }
    public static void responderTicket(String ticketID, String respuesta) {
    File inputFile = new File("tickets/tickets.txt");
    File tempFile = new File("tickets/temp.txt");

    try (
        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))
    ) {
        String linea;

        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split(",");

            if (partes[0].equals(ticketID)) {
                // Ticket encontrado → actualizar
                partes[6] = "RESPONDIDO: " + respuesta;
                linea = String.join(",", partes);
            }

            bw.write(linea);
            bw.newLine();
        }

        // reemplazar archivo original
        inputFile.delete();
        tempFile.renameTo(inputFile);

        System.out.println("El ticket " + ticketID + " ha sido marcado como respondido.");

    } catch (IOException e) {
        System.out.println("Error al responder el ticket.");
    }
}

}
