package Simulacion;
import java.util.ArrayList;

public class Series
{
    public void SeriesMain(String rutaArchivo, double tablaChi[]) {
        // Leer archivo usando ManipularCSV
        ManipularCSV csv = new ManipularCSV();
        csv.leerArchivo(rutaArchivo);
        
        ArrayList<Double> valores = csv.ListaValores();
        
        // Realizar prueba de series
        PruebaSerie prueba = new PruebaSerie(valores);
        prueba.formarPares();
        prueba.mostrarResultados(tablaChi);
    }

    public static class PruebaSerie {
        private ArrayList<Double> valores; 
        private int N;
        private int n = 5; // Número de intervalos
        private int[][] oij; 
        private double Eij;

        public PruebaSerie(ArrayList<Double> valores) {
            this.valores = valores;
            this.N = valores.size();
            this.oij = new int[n][n];
            this.Eij = (double)N / (n * n); // Calcula la frecuencia esperada
        }

        public void formarPares() //METODO QUE RECORRE LA LISTA DE VALORES
        {
            double ui;
            double ui1;
            for (int i = 0; i < N; i++) { 
                //forma pares
                ui = valores.get(i);
                if (i == N-1){
                    ui1 = valores.get(0);
                } else {
                    ui1 = valores.get(i + 1);
                }

                //los acomoda en los obvservados
                int fila = (int) (ui * n);
                int columna = (int) (ui1 * n);

                // Ajustar para valores iguales a 1.0
                if (fila == n) fila--;
                if (columna == n) columna--;

                oij[fila][columna]++;
            }
        }

        public double calcularChiCuadrado() //METODO QUE USA LA FORMULA
        {
            double chi2 = 0.0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    double diferencia = oij[i][j] - Eij;
                    chi2 += (diferencia * diferencia) / Eij;
                }
            }
            return chi2;
        }

        public void mostrarResultados(double []tablaChi) {
            // Mostrar tabla Oij
            System.out.println("Tabla de frecuencias observadas (Oij):");
            System.out.println("-----------------------------------");
            
            // Encabezado de columnas
            System.out.print("     ");
            for (int j = 0; j < n; j++) {
                System.out.printf("%.1f  ", (j + 1) / (double) n);
            }
            System.out.println();
            
            // Filas de la tabla (orden inverso)
            for (int i = n - 1; i >= 0; i--) { // Cambio aquí: iterar en orden inverso
                System.out.printf("%.1f | ", (i + 1) / (double) n);
                for (int j = 0; j < n; j++) {
                    System.out.printf("%3d ", oij[i][j]);
                }
                System.out.println();
            }
            // Calcular y mostrar X²
            double chi2 = calcularChiCuadrado(); //x calculado
            double in =Math.pow(n, 2) - 1;
            System.out.println("\nResultados de la prueba:");
            System.out.println("N = " + N);
            System.out.println("n = " + n);
            System.out.printf("X2 calculado = %.4f\n", chi2);
            System.out.println("X2 crítico (" + in  + " 5%) = " +  tablaChi[(int)in-1]); //x tabla 
            
            // Conclusión
            if (chi2 < tablaChi[(int)n-1]) {
                System.out.println("Conclusión: X2 < X²(" + in  + ", 5%) → Se acepta H0");
            } else {
                System.out.println("Conclusión: X2 ≥ X²(" + in  + ", 5%) → Se rechaza H0");
            }
        }
    }
}