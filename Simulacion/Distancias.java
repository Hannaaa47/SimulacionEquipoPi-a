package Simulacion;
import java.util.Scanner;
import java.util.ArrayList;

public class Distancias {
	 
	public static void main(String[] args) {
		double [] tablaChi = {3.841, 5.991, 7.815, 9.488, 11.070, 
        		12.592,	14.067, 15.507,	16.919,	18.307, 
        		19.675, 21.026, 22.362, 23.685, 24.996,
        		26.296, 27.587, 28.869, 30.144, 31.410,	
        		32.671, 33.924, 35.172, 36.415, 37.652,
        		38.885, 40.113, 41.337, 42.557, 43.773,
        		44.985, 46.194, 47.400, 48.602, 49.802,
        		55.758, 67.500, 79.1, 124.3};
		
		prueba_Distancia(tablaChi);
		
    }
	
	//metodo Prueba de Aleatoriedad (independencia).
	public static void prueba_Distancia(double[] tablaChi) {
		ManipularCSV archivo = new ManipularCSV(); // Crear objeto
        archivo.leerArchivo("Simulacion/Datos.csv"); 
        ArrayList<Double> datos = archivo.ListaValores(); 
		Scanner sc = new Scanner(System.in);
	    
	    if (datos.isEmpty()) {
	        System.out.println("No se encontraron datos o el archivo no existe.");
	        return;
	    }

	    System.out.println("Ingresa valor de alpha(α): ");
	    double alpha = sc.nextDouble();
	    System.out.println("Ingresa valor de teta(θ): ");
	    double teta = sc.nextDouble();

	    double beta = alpha + teta;
	    System.out.println("β = " + beta);
	    double pe = teta;
	    double pf = 1 - teta;

	    ArrayList<Integer> huecos = new ArrayList<>();
	    int contadorHuecos = 0;
	    ArrayList<Integer> indicesHuecos = new ArrayList<>();
	    int huecosDeCero = 0;
	    int prevEpsilon = -1;

	    System.out.println("──────────────────────୨ৎ────────────────────────");
	    System.out.printf("| %-4s | %-8s | %-2s | %-2s |%n", "n", "Ui", "Є", "i");
	    System.out.println("──────────────────────୨ৎ────────────────────────");

	    for (int n = 0; n < datos.size(); n++) {
	        double ui = datos.get(n);
	        int epsilon = (ui >= alpha && ui <= beta) ? 1 : 0;

	        if (epsilon == 0) {
	            contadorHuecos++;
	            indicesHuecos.add(n);
	        } else {
	            if (contadorHuecos > 0) {
	                huecos.add(contadorHuecos);
	                int inicioHueco = indicesHuecos.get(0);
	                System.out.printf("| %-4d | %-8.4f | %-2d | %-2d |%n", inicioHueco + 1, datos.get(inicioHueco), 0, contadorHuecos);
	                for (int j = 1; j < indicesHuecos.size(); j++) {
	                    int idx = indicesHuecos.get(j);
	                    System.out.printf("| %-4d | %-8.4f | %-2d | %-2s |%n", idx + 1, datos.get(idx), 0, "");
	                }
	                indicesHuecos.clear();
	                contadorHuecos = 0;
	            }

	            if (prevEpsilon != 1) {
	                huecos.add(0);
	                huecosDeCero++;
	                System.out.printf("| %-4d | %-8.4f | %-2d | %-2d |%n", n + 1, ui, epsilon, 0);
	            } else {
	                System.out.printf("| %-4d | %-8.4f | %-2d | %-2s |%n", n + 1, ui, epsilon, "");
	            }
	        }

	        prevEpsilon = epsilon;
	    }

	    if (contadorHuecos > 0) {
	        huecos.add(contadorHuecos);
	        int inicioHueco = indicesHuecos.get(0);
	        System.out.printf("| %-4d | %-8.4f | %-2d | %-2d |%n", inicioHueco + 1, datos.get(inicioHueco), 0, contadorHuecos);
	        for (int j = 1; j < indicesHuecos.size(); j++) {
	            int idx = indicesHuecos.get(j);
	            System.out.printf("| %-4d | %-8.4f | %-2d | %-2s |%n", idx + 1, datos.get(idx), 0, "");
	        }
	        indicesHuecos.clear();
	    }

	    System.out.println("\nCantidad de huecos con i = 0: " + huecosDeCero);
	    
	    System.out.println("\nTabla de frecuencias y cálculo de χ²:");
	    System.out.printf("%-6s %-10s %-6s %-8s %-8s %-10s%n", "i", "Pi", "Oi", "Ei", "Ei-Oi", "(Ei-Oi)^2/Ei");
	    System.out.println("------------------------------------------------------------");

	    int N = huecos.size();

	    // Determinar el valor máximo de i observado (Oi > 0)
	    int maxI = 0;
	    for (int h : huecos) if (h > maxI) maxI = h;

	    // Contar frecuencias observadas Oi
	    int[] frecuencias = new int[maxI + 1];
	    for (int h : huecos) frecuencias[h]++;

	    // Determinar desde qué i no hay más huecos observados
	    int agrupamientoDesde = 0;
	    for (int i = 1; i < frecuencias.length - 1; i++) {
	        if (frecuencias[i] > 0 && frecuencias[i + 1] == 0) {
	            agrupamientoDesde = i + 1;
	            break;
	        } else if (frecuencias[i] == 0 && frecuencias[i + 1] > 0) {
	            // si hay un hueco no consecutivo (ej. hueco 10 después de huecos 1-6)
	            agrupamientoDesde = i;
	            break;
	        }
	    }

	    double sumaProbabilidades = 0;
	    int totalObservado = 0;
	    double chiCuadrado = 0;

	    // Cálculo de i individuales hasta antes del agrupamiento
	    for (int i = 0; i < agrupamientoDesde; i++) {
	        double pi = Math.pow(pf, i) * pe;
	        int oi = (i < frecuencias.length) ? frecuencias[i] : 0;
	        double ei = N * pi;
	        double diferencia = ei - oi;
	        double chiLocal = (ei == 0) ? 0 : Math.pow(diferencia, 2) / ei;

	        System.out.printf("%-6d %-10.4f %-6d %-8.2f %-8.2f %-10.3f%n", i, pi, oi, ei, diferencia, chiLocal);
	        sumaProbabilidades += pi;
	        chiCuadrado += chiLocal;
	        totalObservado += oi;
	    }

	    // Agrupación i ≥ agrupamientoDesde con fórmula Pi = (1 - θ)^i
	    double piGrupo = Math.pow(pf, agrupamientoDesde);
	    int oiGrupo = 0;
	    for (int j = agrupamientoDesde; j < frecuencias.length; j++) {
	        oiGrupo += frecuencias[j];
	    }
	    double eiGrupo = N * piGrupo;
	    double diferencia = eiGrupo - oiGrupo;
	    double chiGrupo = (eiGrupo == 0) ? 0 : Math.pow(diferencia, 2) / eiGrupo;

	    // Imprimir fila agrupada
	    System.out.printf("i≥%-3d %-10.4f %-6d %-8.2f %-8.2f %-10.3f%n",
	            agrupamientoDesde, piGrupo, oiGrupo, eiGrupo, diferencia, chiGrupo);

	    sumaProbabilidades += piGrupo;
	    chiCuadrado += chiGrupo;
	    totalObservado += oiGrupo;

	    System.out.println("------------------------------------------------------------");
	    System.out.printf("∑Pi = %-8.4f ∑Oi = %-4d ∑Ei = %-6.2f ∑χ² = %.4f%n", sumaProbabilidades, totalObservado, (double) N, chiCuadrado);

	    int gradosLibertad = agrupamientoDesde;
	    if (gradosLibertad >= tablaChi.length) {
	        System.out.println("No se puede evaluar H₀: grados de libertad fuera de tabla.");
	    } else {
	        double chiCritico = tablaChi[gradosLibertad - 1];
	        System.out.printf("χ² crítico (gl = %d, α = 0.05) = %.3f%n", gradosLibertad, chiCritico);
	        if (chiCuadrado < chiCritico) {
	            System.out.println("Se acepta H₀ (distribución compatible)");
	        } else {
	            System.out.println("Se rechaza H₀ (no es aleatoria)");
	        }
	    }
		sc.close();
	}
}