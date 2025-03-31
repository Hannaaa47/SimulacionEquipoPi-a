package Simulacion;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Series
{
    public void SeriesMain(String rutaArchivo, HashMap<Integer, Double> tablaChi, Scanner sc) {
        // Leer archivo usando ManipularCSV
        ManipularCSV csv = new ManipularCSV();
        csv.leerArchivo(rutaArchivo);
        
        ArrayList<Double> valores = csv.ListaValores();
        
        // Realizar prueba de series
        System.out.print("Ingrese n: ");
        int n= sc.nextInt();
        System.out.println();
        PruebaSerie prueba = new PruebaSerie(valores,n);
        prueba.formarPares();
        prueba.mostrarResultados(tablaChi);
    }

    public static class PruebaSerie {
        private ArrayList<Double> valores; 
        private int N;
        private int n = 5; // Número de intervalos
        private int[][] oij; 
        private double Eij;
        private double[][] tabEij;

        public PruebaSerie(ArrayList<Double> valores, int n) {
            this.valores = valores;
            this.N = valores.size();
            this.n= n;
            this.oij = new int[n][n];
            this.tabEij = new double[n][n];
            this.Eij = (double)N / (n * n); // Calcula la frecuencia esperada
        }

        public void formarPares() //METODO QUE RECORRE LA LISTA DE VALORES
        {
            double ui;
            double ui1;
            for (int i = 0; i < N; i++) { 
                //forma pares
                ui = valores.get(i);
                if (i == 0){
                    ui1 = valores.get(N-1);
                } else {
                    ui1 = valores.get(i - 1);
                }

                //los acomoda en los obvservados
                int fila=-1;
                int columna=-1;
       

                double x;
                double y;
                for (int j = 0; j < n && columna==-1; j++) {
                    x = (j + 1) / (double) n;
                    if(ui1 <= x) columna=j;
                }

                for (int k = 0;  k< n && fila==-1; k++) {
                    y = (k + 1) / (double) n;
                    if(ui <= y) fila=k;
                }
                if (fila==-1) fila=n-1;
                if (columna==-1) columna=n-1;

                oij[fila][columna]++;
            }
        }

        public double calcularChiCuadrado()  {
            double chi2 = 0.0,temp, diferencia;
            System.out.println("Tabla de frecuencias observadas (Oij - Eij)² / Eij:");
            // Encabezado de columnas
            System.out.println("-------------------------------------------------------------------------------");
            System.out.print("|            | ");
            for (int j = 0; j < n; j++) {
                System.out.printf("%-10.2f | ", (j + 1) / (double) n);
            }
            System.out.println();
            System.out.println("-------------------------------------------------------------------------------");
            for (int i = n - 1; i >= 0; i--) { // Cambio aquí: iterar en orden inverso
                System.out.printf("| %-10.2f | ", (i + 1) / (double) n);
                for (int j = 0; j < n; j++) { 
                    diferencia = tabEij[i][j];
                    temp= (diferencia * diferencia) / Eij;
                    chi2 += temp;    
                    System.out.printf("%-10.4f | ", temp );
                }
                System.out.println();
            }
            System.out.println("-------------------------------------------------------------------------------");
            return chi2;
        }

        public void mostrarResultados(HashMap<Integer, Double> tablaChi) {
            // Mostrar tabla Oij
            System.out.println("Tabla de frecuencias observadas (Oij):");
            
            // tabla oij
            // Encabezado de columnas
            System.out.println("-------------------------------------------------------------------------------");
            System.out.print("|            | ");
            for (int j = 0; j < n; j++) {
                System.out.printf("%-10.2f | ", (j + 1) / (double) n);
            }
            System.out.println();
            System.out.println("-------------------------------------------------------------------------------");

            for (int i = n - 1; i >= 0; i--) { // Cambio aquí: iterar en orden inverso
                System.out.printf("| %-10.2f | ", (i + 1) / (double) n);
                for (int j = 0; j < n; j++) {
                    
                    System.out.printf("%-10d | ", oij[i][j]);
                }
                System.out.println();
            }
            System.out.println("-------------------------------------------------------------------------------");
            System.out.println();

            // tabla eij
            System.out.println("Tabla de frecuencias observadas (Eij):");
            // Encabezado de columnas
            System.out.println("-------------------------------------------------------------------------------");
            System.out.print("|            | ");
            for (int j = 0; j < n; j++) {
                System.out.printf("%-10.2f | ", (j + 1) / (double) n);
            }
            System.out.println();
            System.out.println("-------------------------------------------------------------------------------");

            for (int i = n - 1; i >= 0; i--) { // Cambio aquí: iterar en orden inverso
                System.out.printf("| %-10.2f | ", (i + 1) / (double) n);
                for (int j = 0; j < n; j++) {
                    
                    System.out.printf("%-10.4f | ", Eij);
                }
                System.out.println();
            }
            System.out.println("-------------------------------------------------------------------------------");
            System.out.println();

            // tabla oij - eij
            System.out.println("Tabla de frecuencias observadas (Oij - Eij):");
            // Encabezado de columnas
            System.out.println("-------------------------------------------------------------------------------");
            System.out.print("|            | ");
            for (int j = 0; j < n; j++) {
                System.out.printf("%-10.2f | ", (j + 1) / (double) n);
            }
            System.out.println();
            System.out.println("-------------------------------------------------------------------------------");
 
            for (int i = n - 1; i >= 0; i--) { // Cambio aquí: iterar en orden inverso
                System.out.printf("| %-10.2f | ", (i + 1) / (double) n);
                for (int j = 0; j < n; j++) {    
                    tabEij[i][j] = (double)oij[i][j] - Eij;  
                    System.out.printf("%-10.4f | ", tabEij[i][j] );
                }
                System.out.println();
            }
            System.out.println("-------------------------------------------------------------------------------");
            System.out.println();

            // Calcular y mostrar X²
            double chi2 = calcularChiCuadrado(); //x calculado
            int in =(int)Math.pow(n, 2) - 1;
            System.out.println("\nResultados de la prueba:");
            System.out.println("N = " + N);
            System.out.println("n = " + n);
            System.out.printf("X² calculado = %.4f\n", chi2);
            System.out.println("X² crítico (" + in  + " 5%) = " +  tablaChi.get(in)); //x tabla 
            
            // Conclusión
            if (chi2 < tablaChi.get(in)) {
                System.out.println("Conclusión: X² < X²(" + in  + ", 5%) → Se acepta H0");
                System.out.println("Conclusion: \nNo hay evidencia suficiente para decir que estos datos no estan distribuidos uniformemente");
            
            } else {
                System.out.println("Conclusión: X² ≥ X²(" + in  + ", 5%) → Se rechaza H0");
                System.out.println("Conclusion: \nHay evidencia suficiente para decir que estos datos no estan distribuidos uniformemente");
            
            }
        }
    }
}