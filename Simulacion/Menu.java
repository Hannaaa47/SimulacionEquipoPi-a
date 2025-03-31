package Simulacion;
import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String rutaArchivo = "Simulacion/Datos.csv";
        double [] tablaChi = {3.841, 5.991, 7.815, 9.488, 11.070, 
            12.592,	14.067, 15.507,	16.919,	18.307, 
            19.675, 21.026, 22.362, 23.685, 24.996,
            26.296, 27.587, 28.869, 30.144, 31.410,	
            32.671, 33.924, 35.172, 36.415, 37.652,
            38.885, 40.113, 41.337, 42.557, 43.773,
            44.985, 46.194, 47.400, 48.602, 49.802,
            55.758, 67.500, 79.1, 124.3};
        int opcion;
        do {
            System.out.println("\n────────────────────────────────────────");
        	System.out.println("            NUMEROS ALEATORIOS ");
            System.out.println("────────────────────────────────────────");
            System.out.println("-----# Prueba de bondad de ajuste #-----");
            System.out.println("\t1. Prueba Ji-Cuadrado.");
            System.out.println("\t2. Prueba Kolmogorov-Smirnov.");
            System.out.println("-------# Prueba de aleatoriedad #-------");
            System.out.println("\t3. Prueba de las Series.");
            System.out.println("\t4. Prueba de las Distancias.");
            System.out.println("\t0. Salir");
            System.out.println("────────────────────────────────────────");
            System.out.print("Elige una opción: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                	//Prueba Ji-Cuadrado.
                    System.out.println("\n----------# JI-CUADRADO #----------");
                    JiCuadrada c = new JiCuadrada();
                    c.Ji_Cuadrada(tablaChi, rutaArchivo);
                    break;
                case 2:
                	//Prueba Kolmogorov-Smirnov.
                    System.out.println("\n----------# KOLMOGOROV #----------");
                    Kolmogorov k = new Kolmogorov();
                    k.KolmogorovMain(sc, rutaArchivo);
                    break;
                case 3:
                	//Prueba de las Series.
                    System.out.println("\n----------# SERIES #----------");
                    Series s = new Series();
                    s.SeriesMain(rutaArchivo, tablaChi,sc);
                    break;
                case 4:
                    //Prueba de las Distancias.
                    System.out.println("\n----------# DISTANCIAS #----------");
                    Distancias d = new Distancias();
                    d.prueba_Distancia(tablaChi,sc,rutaArchivo);
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
