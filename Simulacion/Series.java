package Simulacion;
import java.util.ArrayList;

public class Series
{
    public static void main(String[] args) {
        // Leer archivo usando ManipularCSV
        ManipularCSV csv = new ManipularCSV();
        csv.leerArchivo("Simulacion/Datos.csv");
        
        ArrayList<Double> valores = csv.ListaValores();
        
        // Realizar prueba de series
        PruebaSerie prueba = new PruebaSerie(valores);
        prueba.formarPares();
        prueba.mostrarResultados();
    }

    public static class PruebaSerie {
        private ArrayList<Double> valores; 
        private int N;
        private int n = 5; // Número de intervalos
        private int[][] oij; //MATRIZ 5 X 5
        private double Eij;
        private final double VALOR_CRITICO = 36.41; // Valor crítico para 24 grados de libertad y α=0.05

        public PruebaSerie(ArrayList<Double> valores) {
            this.valores = valores;
            this.N = valores.size() - 1;
            this.oij = new int[n][n];
            this.Eij = (double)N / (n * n); // Calcula la frecuencia esperada
        }

        public void formarPares() //METODO QUE RECORRE LA LISTA DE VALORES
        {
            for (int i = 0; i < N; i++) {
                double ui = valores.get(i);
                double ui1 = valores.get(i + 1);

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

        public void mostrarResultados() {
            // Mostrar tabla Oij
            System.out.println("Tabla de frecuencias observadas (Oij):");
            System.out.println("-----------------------------------");
            
            // Encabezado de columnas
            System.out.print("     ");
            for (int j = 0; j < n; j++) {
                System.out.printf("%.1f  ", (j + 1) / (double) n);
            }
            System.out.println();
            
            // Filas de la tabla
            for (int i = 0; i < n; i++) {
                System.out.printf("%.1f | ", (i + 1) / (double) n);
                for (int j = 0; j < n; j++) {
                    System.out.printf("%3d ", oij[i][j]);
                }
                System.out.println();
            }
            
            // Calcular y mostrar X²
            double chi2 = calcularChiCuadrado();
            System.out.println("\nResultados de la prueba:");
            System.out.println("N = " + N);
            System.out.println("n = " + n);
            System.out.printf("X2 calculado = %.4f\n", chi2);
            System.out.println("X2 crítico (24, 5%) = " + VALOR_CRITICO);
            
            // Conclusión
            if (chi2 < VALOR_CRITICO) {
                System.out.println("Conclusión: X2 < X²(24, 5%) → Se acepta H0");
            } else {
                System.out.println("Conclusión: X2 ≥ X²(24, 5%) → Se rechaza H0");
            }
        }
    }
}