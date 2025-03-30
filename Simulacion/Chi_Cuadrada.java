package Simulacion;
public class Chi_Cuadrada {
	public static void main(String[] args) {
		double [] tablaChi = {3.841, 5.991, 7.815, 9.488, 11.070, 
        		12.592,	14.067, 15.507,	16.919,	18.307, 
        		19.675, 21.026, 22.362, 23.685, 24.996,
        		26.296, 27.587, 28.869, 30.144, 31.410,	
        		32.671, 33.924, 35.172, 36.415, 37.652,
        		38.885, 40.113, 41.337, 42.557, 43.773,
        		44.985, 46.194, 47.400, 48.602, 49.802,
        		55.758, 67.500, 79.1, 124.3};
        
        ChiCuadrada(tablaChi);
    }
	
	public static void ChiCuadrada (double [] tablaChi) {
		ManipularCSV archivo = new ManipularCSV(); // Crear objeto
        archivo.leerArchivo("Simulacion/Datos.csv"); // Llamar al método
        
		int n = archivo.getContador(),observados=0;
		double valorInferior=0, valorSuperior=0, intervalo=0, x2=0, k, e;
        System.out.println("Total de numeros (N): " + n);
        
        //Calcula numero de intervalos
        //k = Math.round(Math.sqrt(n)); //me saca la raiz y redondea a un numero entero de intervalos
        k=10; //por si quiero poner los intervalos fijos
        System.out.println("Numero de intervalos: " + k);
        
        //creacion de vectores para facilitar el guardado e impresion de los datos
		int [] o = new int [(int) k];
		double [] iv = new double [(int) k];
		double [] o_e = new double [(int) k];
		double [] o_e_cuadrada = new double [(int) k];
        
		//elementos esperados
        e=n/k;
        //calcula valor de aumento de los intervalos
        intervalo = 1.0/k;
        valorSuperior=intervalo;
        System.out.println("\n------------------------------------------------");
        System.out.printf("| %-5s | %-5s | %-5s | %-7s | %-10s |\n", "I", "O", "E", "O-E", "(O-E)^2/E");
        System.out.println("------------------------------------------------");
        for(int i=0; i<k; i++) {
        	observados=0;
        	for(int j=0; j<archivo.ListaValores().size(); j++) {
        		if(archivo.ListaValores().get(j)>=valorInferior && archivo.ListaValores().get(j)<=valorSuperior) {
        			observados++;
        		}
        	}
        	o[i]=observados;
        	iv[i]=valorSuperior;
        	o_e[i]=o[i]-e;
        	o_e_cuadrada[i]=(Math.pow(o_e[i], 2))/e;
        	x2= x2 + o_e_cuadrada[i];
    		valorInferior=valorSuperior;
    		valorSuperior=valorInferior+intervalo;
    		System.out.printf("| %-5.3f | %-5d | %-5.1f | %-7.1f | %-10.4f |\n", iv[i], o[i], e, o_e[i], o_e_cuadrada[i]);
    		System.out.println("------------------------------------------------");
        }
        System.out.println("Valor de X2: " + x2);
        int gradosLibertad = (int)k-1;
        System.out.println("Grados de libertad: " + gradosLibertad);
        for(int i=0; i<=tablaChi.length; i++)
        {
        	if(i+1==gradosLibertad) {
        		System.out.println("X2: (" + gradosLibertad + ", 5%): " + tablaChi[i]);
        		if(tablaChi[i]>x2) {
        			System.out.println("\nNO existe evidencia suficiente para sustentar que los datos NO estan distribuidos uniformemente");
        		}else {
        			System.out.println("\nExiste evidencia suficiente para sustentar que los datos NO estan distribuidos uniformemente");
        		}
        	}
        }
	}
}