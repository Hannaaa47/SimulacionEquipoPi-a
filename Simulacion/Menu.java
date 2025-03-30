package Simulacion;
import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("\n──────────────────୨ৎ──────────────────────");
            System.out.println("\t1. Prueba Ji-Cuadrado.");
            System.out.println("\t2. Prueba Kolmogorov-Smirnov.");
            System.out.println("\t3. Prueba de las Series.");
            System.out.println("\t4. Prueba de las Distancias.");
            System.out.println("\t0. Salir");
            System.out.print("Elige una opción: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                	//Prueba Ji-Cuadrado.
                    break;
                case 2:
                	//Prueba Kolmogorov-Smirnov.
                    break;
                case 3:
                	//Prueba de las Series.
                    break;
                case 4:
                    //Prueba de las Distancias.
                    Distancias d = new Distancias();
                    break;
                case 0:
                    System.out.println("Hasta pronto");
                    break;
                default:
                    System.out.println("Opción no válida, intenta de nuevo.");
            }
        } while (opcion != 0);
        sc.close();
    }
}
