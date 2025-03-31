package Simulacion;
import java.util.HashMap;
import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String rutaArchivo = "Simulacion/Pruebas2.csv";
        HashMap<Integer, Double> tablaChi = new HashMap<>();

        int[] grados = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
                        21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
                        45, 50, 55, 60, 70, 80, 90, 100, 120, 140, 160, 180, 200, 250, 300, 400, 500, 600};
        
        double[] valoresChi = {3.8415, 5.9915, 7.8147, 9.4877, 11.0705, 12.5916, 14.0671, 15.5073, 16.9190, 18.3070,
                               19.6752, 21.0261, 22.3620, 23.6848, 24.9958, 26.2962, 27.5871, 28.8693, 30.1435, 31.4104,
                               32.6706, 33.9245, 35.1725, 36.4150, 37.6525, 38.8851, 40.1133, 41.3372, 42.5569, 43.7730,
                               44.9853, 46.1942, 47.3999, 48.6024, 49.8018, 50.9985, 52.1923, 53.3835, 54.5722, 55.7585,
                               61.6562, 67.5048, 73.3115, 79.0820, 90.5313, 101.8795, 113.1452, 124.3421, 146.5673, 168.6130,
                               190.5164, 212.3039, 233.9942, 287.8815, 341.3951, 453.1269, 553.1269, 658.0936};
        
        for (int i = 0; i < grados.length; i++){
            tablaChi.put(grados[i], valoresChi[i]);
        }

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
