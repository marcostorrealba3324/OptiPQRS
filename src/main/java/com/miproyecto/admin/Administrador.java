package admin;

import java.util.Scanner;
import tickets.GestionTickets;

public class Administrador {

    public static void iniciarAdmin(Scanner sc) {

        System.out.print("Ingrese la clave de administrador: ");
        String clave = sc.nextLine();

        if (clave.equals("admin123")) {
            System.out.println("Acceso concedido.");
            menuAdmin(sc);
        } else {
            System.out.println("Clave incorrecta.");
        }
    }

    public static void menuAdmin(Scanner sc) {

        int opcion = 0;

    do {
        System.out.println("\n===== MENÚ ADMINISTRADOR =====");
        System.out.println("1. Ver todos los tickets");
        System.out.println("2. Eliminar los tickets");
        System.out.println("3. Salir del menú de administrador");
        System.out.print("Seleccione una opción: ");
        
        try {
            opcion = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Por favor, ingrese un número.");
            continue; 
        }

        switch (opcion) {
            case 1:
                GestionTickets.mostrarTodosLosTickets();
                break;
            case 2:
                GestionTickets.eliminarTickets();
                break;
            case 3:
                System.out.println("Saliendo del menú de administrador...");
                break;
            default:
                System.out.println("Opción inválida. Por favor, seleccione 1, 2 o 3.");
        }

    } while (opcion != 3);
    }
}