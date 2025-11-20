package usuarios;

import java.util.*;
import java.io.*;


public class RegistrarUsuario {

    public static void registrarUsuario(Scanner sc, Map<String, String> usuarios) {
        System.out.print("Ingrese su número de cédula: ");
        String cedula = sc.nextLine();

        if (usuarios.containsKey(cedula)) {
            System.out.println("Este usuario ya está registrado.");
            return;
        }

        System.out.print("Ingrese su nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Ingrese su apellido: ");
        String apellido = sc.nextLine();
        System.out.print("Ingrese su departamento: ");
        String departamento = sc.nextLine();
        System.out.print("Ingrese su municipio: ");
        String municipio = sc.nextLine();
        System.out.print("Ingrese su dirección: ");
        String direccion = sc.nextLine();
        System.out.print("Ingrese su correo electrónico: ");
        String correo = sc.nextLine();

        String datos = nombre + "," + apellido + "," + departamento + "," + municipio + "," + direccion + "," + correo;
        usuarios.put(cedula, datos);

        GuardarUsuarios.guardarUsuarios(usuarios);
        System.out.println("\nRegistro exitoso. ¡Bienvenido, " + nombre + " " + apellido + "!");
    }
}