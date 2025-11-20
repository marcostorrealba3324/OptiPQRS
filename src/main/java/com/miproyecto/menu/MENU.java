package menu;

import java.util.*;
import usuarios.GuardarUsuarios;
import usuarios.RegistrarUsuario;
import tickets.GestionTickets;
import admin.Administrador;

public class MENU {
    static Scanner sc = new Scanner(System.in);
    static Map<String, String> usuarios = new HashMap<>();

    public static void main(String[] args) {
        GuardarUsuarios.cargarUsuarios(usuarios); 

        int opcion;
        do {
            System.out.println("\n===== BIENVENIDO A OPTIPQRS =====");
            System.out.println("1. Ingresar al sistema");
            System.out.println("2. Registrar nuevo usuario");
            System.out.println("3. Administrador");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            
            
            try {
                opcion = sc.nextInt();
                sc.nextLine(); 
            } catch (InputMismatchException e) {
                System.out.println("Opción inválida. Por favor, ingrese solo números.");
                sc.nextLine(); 
                opcion = 0; 
                continue;
            }

            switch (opcion) {
                case 1: 
                    ingresarSistema();
                    break; 
                case 2: 
                    RegistrarUsuario.registrarUsuario(sc, usuarios);
                    break; 
                case 3: 
                    Administrador.iniciarAdmin(sc);
                    break; 
                case 4: {
                    System.out.println("Guardando datos y saliendo...");
                    GuardarUsuarios.guardarUsuarios(usuarios);
                    break; 
                }
                default: 
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 4); 
    }

    // --- Ingresar al sistema ---
    public static void ingresarSistema() {
        System.out.print("¿Está registrado? (si/no): ");
        String respuesta = sc.nextLine().toLowerCase();

        if (respuesta.equals("si")) {
            System.out.print("Ingrese su número de cédula: ");
            String cedula = sc.nextLine();

            if (usuarios.containsKey(cedula)) {
                String[] datos = usuarios.get(cedula).split(",");
                System.out.println("¡Bienvenido, " + datos[0] + " " + datos[1] + "!");
                mostrarMenuPQRS(cedula);
            } else {
                System.out.println("Cédula no registrada. Regístrese primero.");
            }
        } else if (respuesta.equals("no")) {
            RegistrarUsuario.registrarUsuario(sc, usuarios);
        } else {
            System.out.println("Respuesta inválida.");
        }
    }

    // --- Menú PQRS ---
    public static void mostrarMenuPQRS(String cedula) {
        int opcion;
        do {
            System.out.println("\n===== MENÚ PQRS =====");
            System.out.println("1. Petición");
            System.out.println("2. Queja");
            System.out.println("3. Reclamo");
            System.out.println("4. Sugerencia");
            System.out.println("5. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            
            try {
                opcion = sc.nextInt();
                sc.nextLine(); // Consumir el salto de línea
            } catch (InputMismatchException e) {
                System.out.println("Opción inválida. Por favor, ingrese solo números.");
                sc.nextLine();
                opcion = 0;
                continue;
            }

            if (opcion >= 1 && opcion <= 4) {
                System.out.print("Describa brevemente su solicitud: ");
                String descripcion = sc.nextLine();
                GestionTickets.generarTicket(usuarios, cedula, opcion, descripcion);
            }

        } while (opcion != 5);
    }
}