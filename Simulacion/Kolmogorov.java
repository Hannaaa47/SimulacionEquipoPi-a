package Simulacion;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Kolmogorov {

    public void KolmogorovMain(Scanner input, String rutaArchivo) {
        //creo mis variables
        ManipularCSV archivo = new ManipularCSV(); // Crear objeto
        archivo.leerArchivo(rutaArchivo); 
        ArrayList<Double> serie = archivo.ListaValores(); // Usar ArrayList directamente
        int n = archivo.getContador();

        double error;
        double[] fi = new double[n];
        double[] Ui = new double[n];
        
        //leo el error
        System.out.print("Ingrese el error: ");
        error = input.nextDouble();
        
        // Ordenar el ArrayList
        Collections.sort(serie); 

        //llamo al metodo kolmogorv
        kolmogorv(serie, fi, Ui, 0, n, 1.0 / n, (error / 100.00), Double.NEGATIVE_INFINITY); 
    }

    static void kolmogorv( ArrayList<Double> serie, double[] fi, double[] Ui, int index, int n, double fn, double a, double d1) {
        if (index < n) {
            fi[index] = (index + 1) * fn;
            Ui[index] = Math.abs(serie.get( index) - fi[index]);
            d1 = Math.max(d1, Ui[index]); // Actualizar d1 si Ui[index] es mayor
            kolmogorv(serie, fi, Ui, index + 1, n, fn, a, d1);
        } else {
            double d2 = calcularH1(n, a);
            imprimirResultados(serie, fi, Ui, n, a, fn, d1, d2);
        }
    }

    static double calcularH1(double n, double a) {
        if (a == 0.01) return 1.63 / Math.sqrt(n);
        if (a == 0.05) return 1.36 / Math.sqrt(n);
        if (a == 0.1) return 1.22 / Math.sqrt(n);
        if (a == 0.15) return 1.14 / Math.sqrt(n);
        if (a == 0.2) return 1.07 / Math.sqrt(n);
        return 0; // Valor por defecto en caso de error
    }

    static void imprimirResultados(ArrayList<Double> serie, double[] fi, double[] Ui, int n, double a, double fn, double d1, double d2) {
        System.out.println("--------------------------------------------------");
        System.out.println("| i     | Valor      | Fi(Ui)     | Ui - Fi(Ui)  |");
        System.out.println("--------------------------------------------------");
        for (int i = 0; i < serie.size(); i++) {
            System.out.printf("| %-5d | %-10.5f | %-10.5f | %-12.5f |%n", (i + 1), serie.get(i), fi[i], Ui[i]);
        }
        System.out.println("--------------------------------------------------");
        System.out.println("n=" + n);
        System.out.println("Error= " + (a * 100));
        System.out.printf("Fn= %.5f%n", fn);
        System.out.printf("D1= %.5f%n", d1);
        System.out.printf("D2= %.5f%n", d2);
        if (d1 < d2) {
            System.out.println("Conclusion: \nNo hay evidencia suficiente para decir que estos datos no estan distribuidos uniformemente");
        } else {
            System.out.println("Conclusion: \nHay evidencia suficiente para decir que estos datos no estan distribuidos uniformemente");
        }
    }
}