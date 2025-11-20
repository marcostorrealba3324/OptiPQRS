package usuarios;

import java.io.*;
import java.util.*;

public class GuardarUsuarios {

    // --- Guardar usuarios (todos los datos) ---
    public static void guardarUsuarios(Map<String, String> usuarios) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("usuarios/usuarios.txt"))) {
            for (Map.Entry<String, String> entry : usuarios.entrySet()) {
                // cédula + todos los datos separados por comas
                bw.write(entry.getKey() + "," + entry.getValue());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar usuarios.");
        }
    }

    // --- Cargar usuarios (lee todas las columnas) ---
    public static void cargarUsuarios(Map<String, String> usuarios) {
        try (BufferedReader br = new BufferedReader(new FileReader("usuarios/usuarios.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",", 7); // hasta 7 campos
                if (partes.length >= 2) {
                    // La clave es la cédula, el valor son los demás datos concatenados
                    String cedula = partes[0];
                    String datos = String.join(",", Arrays.copyOfRange(partes, 1, partes.length));
                    usuarios.put(cedula, datos);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo de usuarios. Se creará uno nuevo.");
        } catch (IOException e) {
            System.out.println("Error al cargar usuarios.");
        }
    }
}