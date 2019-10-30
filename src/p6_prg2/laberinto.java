package p6_prg2;

import java.util.ArrayList;
import java.util.Scanner;

public class laberinto {
	public static void main(String[] Args) {
		//System.out.println("introduzca el tamaño del laberinto:");
		Scanner leer = new Scanner(System.in);
		String aux = leer.next();
		leer.nextLine();
		int n=Integer.parseInt(aux);
		if(n<=0||n>(Integer.MAX_VALUE/2)){
			//System.out.println("Entrada Inválida. negativos");
			System.exit(0);
		}else{
			int filas = n;
			int columnas = n;
			//System.out.println("introduzca el laberinto de tamaño " +n+ "x" +n);
			String[] vector={""};
			String[] leido= leerLaberinto(leer, n, vector).split(" ");//llamada al metodo leer y guardado en leido
			char[][] laberinto = new char [filas][columnas];
			char[][]laberintolleno=llenarLaberinto(laberinto, 0, 0, filas, columnas, leido, 0);
			boolean asteriscoEnLaberinto=hayAsterisco(laberintolleno);
			//System.out.println("hay asterisco: "+asteriscoEnLaberinto);
			if(asteriscoEnLaberinto==false){
				if(laberintolleno[0][0]=='1' || laberintolleno[n-1][n-1]=='1'){
					System.out.println("NO.");
					System.exit(0);
				}else{
					if(recorridoAnchura(laberintolleno, columnas)[0].length()!=0){
						recorridoAnchura(laberintolleno, n);
						System.out.println("SI, SIN PREMIO.");
						System.out.println(recorridoAnchura(laberintolleno, columnas)[0].trim());
					}else{
						System.out.println("NO.");
						System.exit(0);
					}
				}
			}else{
				if(laberintolleno[0][0]=='1' || laberintolleno[n-1][n-1]=='1'){
					System.out.println("NO.");
					System.exit(0);
				}else{
					int posFila=Asterisco_fila(laberintolleno);
					int posColumna= Asterisco_columna(laberintolleno);
					if(recorridoConPremio(laberintolleno, columnas, posFila, posColumna).length()!=0){
						recorridoConPremio(laberintolleno, columnas, posFila, posColumna);
						System.out.println("SI, CON PREMIO.");
						System.out.println(recorridoConPremio(laberintolleno, columnas, posFila, posColumna).trim());
					}else{
						if(recorridoAnchura(laberintolleno, columnas)[0].length()!=0){
							recorridoAnchura(laberintolleno, n);
							System.out.println("SI, SIN PREMIO.");
							System.out.println(recorridoAnchura(laberintolleno, columnas)[0].trim());
						}else{
							System.out.println("NO.");
							System.exit(0);
						}
					}
				}
			}
			//imprimir el laberinto
			/*System.out.println("\nLaberinto");
			for(int i=0; i< laberintolleno.length; i++){
				for(int j=0; j< laberintolleno.length; j++){
					System.out.print(laberintolleno[i][j] + " ");
				}
				System.out.print("\n");
			}*/
			leer.close();
		}
	}

	//metodo para cargar el laberinto
	public static char[][] llenarLaberinto(char[][] laberinto, int aux1, int aux2, int i, int j, String[] leido,int contador) {
		if(aux1<i){
			if(aux2<j){
				if(leido[contador].charAt(0)!='0' && leido[contador].charAt(0)!='1'&& leido[contador].charAt(0)!='*'){
					//System.out.println("Entrada Inválida. distinto 0,1,*");
					System.exit(0);
				}	
				laberinto[aux1][aux2] = leido[contador].charAt(0);
				llenarLaberinto(laberinto, aux1, aux2+1, i, j, leido, contador+1);
			}	 
			if(aux2==j){
				aux2=0;
				llenarLaberinto(laberinto, aux1+1, aux2, i, j, leido, contador);
			}
		}
		return laberinto;
	}

	//metodo para leer
	public static String leerLaberinto(Scanner teclado, int n, String[] vector){
		for(int i=0; i<n; i++){	
			String [] vectoraux={""};
			vectoraux[0] = teclado.nextLine();//aqui guarda lo que leo(la linea entera)
			char[] nums= vectoraux[0].toString().toCharArray();
			for(char c: nums){
				vector[0]= vector[0]+c+ " ";
			}
		}			
		/*if(vector[0].length()>(i*i*2)+1 || vector[0].length()<(i*i*2)-1){
				System.out.println("Entrada Inválida.tamaño de matriz");
				System.exit(0);
			}*/
		return vector[0];
	}

	//metodo para saber si hay asterisco
	public static boolean hayAsterisco(char[][] laberinto){
		for(int i=0; i< laberinto.length; i++){
			for(int j=0; j< laberinto.length; j++){
				if(laberinto[i][j]=='*'){
					return true;
				}
			}
		}
		return false;
	}

	//metodo para saber la fila del *
	public static int Asterisco_fila(char[][] laberinto){
		int fila = 0;
		for(int i=0; i< laberinto.length; i++){
			for(int j=0; j< laberinto.length; j++){
				if(laberinto[i][j]=='*'){
					fila=i+1;
					
				}
			}
		}
		//System.out.println("fila asterisco: "+ fila);
		return fila;
	}
	//metodo para saber la columna del *
	public static int Asterisco_columna(char[][] laberinto){
		int columna = 0;
		for(int i=0; i< laberinto.length; i++){
			for(int j=0; j< laberinto.length; j++){
				if(laberinto[i][j]=='*'){
					columna=j+1;
				}
			}
		}
		//System.out.println("columna asterisco: "+ columna);
		return columna;
	}

	//metodo recorrido si no hay premio
	public static String[] recorridoAnchura(char[][] laberinto, int n){ 
		// lista que guarda los posibles caminos  
		ArrayList<String> camino = new ArrayList<String>();
		//matriz que marca los caminos ya visitados
		boolean[][] visited=new boolean[n][n];  
		recorridoAnchuraAux(1, 1, laberinto, n, camino, visited); 
		String res = String.join(" ", camino);
		String[] res2= res.split("final");
		//System.out.println("potaaa"+res2[0]);
		return res2;
	} 	

	//metodo auxiliar recorrido
	public static void recorridoAnchuraAux(int fila, int columna, char laberinto[][], int n, ArrayList<String> camino, boolean visited[][]) { 
		//aqui verifico el punto inicial, el [0][0]
		if (fila == 0 || fila == n+1 || columna == 0 || columna == n+1 || visited[fila-1][columna-1] || laberinto[fila-1][columna-1] =='1'){ 
			//System.out.println("casilla 00 no hay un 0");
			return; 
		}

		// si estoy en la celta(n,n) devuelvo la lista
		if (fila == n && columna == n) { 
			if(laberinto[fila-1][columna-1]=='*'){
				camino.add("("+(fila)+","+(columna)+")"+"*"/*+"salida"*/);
			}else{
				camino.add("("+(fila)+","+(columna)+")"/*+"salida"*/);
			}
			camino.add("final");
			return; 
		}

		// Marco las celdas visitadas 
		visited[fila-1][columna-1] = true; 

		// movimiento diagonal abajo derecha 
		if (movimientoseguro(fila + 1, columna+1, laberinto, n, visited)){ 
			if(laberinto[fila-1][columna-1]=='*'){
				camino.add("("+(fila)+","+(columna)+")"+"*"/*+" Dabd "*/);
			}else{
				camino.add("("+(fila)+","+(columna)+")"/*+" Dabd "*/);
			}
			recorridoAnchuraAux(fila+1, columna+1, laberinto, n, camino, visited);
		}

		// movimiento abajo 
		if (movimientoseguro(fila + 1, columna, laberinto, n, visited)){ 
			if(laberinto[fila-1][columna-1]=='*'){
				camino.add("("+(fila)+","+(columna)+")"+"*"/*+" aba "*/);
			}else{
				camino.add("("+(fila)+","+(columna)+")"/*+" aba "*/);
			} 
			recorridoAnchuraAux(fila + 1, columna, laberinto, n, camino, visited);
		}

		// movimiento derecha 
		if (movimientoseguro(fila, columna +1, laberinto, n, visited)){ 
			if(laberinto[fila-1][columna-1]=='*'){
				camino.add("("+(fila)+","+(columna)+")"+"*"/*+" de "*/);
			}else{
				camino.add("("+(fila)+","+(columna)+")"/*+" de "*/);
			}
			recorridoAnchuraAux(fila, columna + 1, laberinto, n, camino, visited); 
		}

		// movimiento izquierda 
		if (movimientoseguro(fila, columna - 1, laberinto, n, visited)){ 
			if(laberinto[fila-1][columna-1]=='*'){
				camino.add("("+(fila)+","+(columna)+")"+"*"/*+" iz "*/);
			}else{
				camino.add("("+(fila)+","+(columna)+")"/*+" iz "*/);
			}
			recorridoAnchuraAux(fila, columna - 1, laberinto, n, camino, visited);  
		} 

		// movimiento diagonal abajo izquierda 
		if (movimientoseguro(fila+1, columna-1, laberinto, n, visited)){ 
			if(laberinto[fila-1][columna-1]=='*'){
				camino.add("("+(fila)+","+(columna)+")"+"*"/*+" Dabi "*/);
			}else{
				camino.add("("+(fila)+","+(columna)+")"/*+" Dabi "*/);
			}
			recorridoAnchuraAux(fila+1, columna-1, laberinto, n, camino, visited);
		} 

		// movimiento diagonal arriba izquierda 
		if (movimientoseguro(fila - 1, columna-1, laberinto, n, visited)){ 
			if(laberinto[fila-1][columna-1]=='*'){
				camino.add("("+(fila)+","+(columna)+")"+"*"/*+" Dari "*/);
			}else{
				camino.add("("+(fila)+","+(columna)+")"/*+" Dari "*/); 
			}
			recorridoAnchuraAux(fila - 1, columna-1, laberinto, n, camino, visited);
		}

		// movimiento diagonal arriba derecha 
		if (movimientoseguro(fila - 1, columna+1, laberinto, n, visited)){ 
			if(laberinto[fila-1][columna-1]=='*'){
				camino.add("("+(fila)+","+(columna)+")"+"*"/*+" Dard "*/);
			}else{
				camino.add("("+(fila)+","+(columna)+")"/*+" Dard "*/);
			}
			recorridoAnchuraAux(fila - 1, columna+1, laberinto, n, camino, visited);
		}

		// movimiento arriba 
		if (movimientoseguro(fila - 1, columna, laberinto, n, visited)){
			if(laberinto[fila-1][columna-1]=='*'){
				camino.add("("+(fila)+","+(columna)+")"+"*"/*+" arr "*/);
			}else{
				camino.add("("+(fila)+","+(columna)+")"/*+" arr "*/);
			}
			recorridoAnchuraAux(fila-1, columna, laberinto, n, camino, visited); 
		} 

		// marca la celda como no visitada para otros caminos posibles
		visited[fila-1][columna-1] = false;
	} 

	//metodo para saber si el movimineto es valido o no
	public static boolean movimientoseguro(int fila, int columna, char laberinto[][], int n, boolean visited[][]){
		if (fila == 0 || fila == n+1 || columna == 0 ||  columna == n+1 || visited[fila-1][columna-1] || laberinto[fila-1][columna-1] == '1'){ 
			return false; 
		}
		return true; 	
	}

	//metodo para recorrer cuando hay premio
	public static String recorridoConPremio(char[][] laberinto, int n, int i_asterisco, int j_asterisco) {
		// lista que guarda los posibles caminos  
		ArrayList<String> camino1 = new ArrayList<String>();
		ArrayList<String> camino2 = new ArrayList<String>();
		//matriz que marca los caminos ya visitados
		boolean[][] visited=new boolean[n][n];  
		recorridoAnchuraAux2(1, 1, laberinto, n, camino1, visited, i_asterisco, j_asterisco); 
		String resA = String.join(" ", camino1);
		String[] resA2= resA.split("final");
		//System.out.println(resA2[0]+"primera parte con asterisco");
		recorridoAnchuraAux2(i_asterisco, j_asterisco, laberinto, n, camino2, visited, n, n);
		String resB = String.join(" ", camino2);
		String[] resvacio = {""};
		if(resB.length()<=7){
			return resvacio[0];
		}
		String resB2= resB.substring(7);
		String[] resB3= resB2.split("final");
		//System.out.println(resB3[0]+"segunda parte quitao asterisco");
		
		if(resA2[0].length()!=0 && resB3[0].length()!=0 ){
			return (resA2[0] + resB3[0]);
		}
		return resvacio[0];
	}

	//metodo auxiliar recorrido premio
	public static void recorridoAnchuraAux2(int fila, int columna, char laberinto[][], int n, ArrayList<String> camino1, boolean visited[][], int i_asterisco, int j_asterisco) { 
		//System.out.println("ahora voy por: "+ "("+fila +","+ columna+")");
		//aqui verifico el punto inicial, el [0][0]
		if (fila == 0 || fila == i_asterisco+1 || columna == 0 || columna == j_asterisco+1 || visited[fila-1][columna-1] || laberinto[fila-1][columna-1] =='1'){ 
			//System.out.println("casilla 00 no hay un 0");
			return; 
		}

		// si estoy en la celta(i_asterisco,j_asterisco) devuelvo la lista
		if (fila == i_asterisco && columna == j_asterisco) { 
			if(laberinto[fila-1][columna-1]=='*'){
				camino1.add("("+(fila)+","+(columna)+")"+"*"/*+"salida"*/);
			}else{
				camino1.add("("+(fila)+","+(columna)+")"/*+"salida"*/);
			}
			//camino1.add("solo hasta asterisco");
			camino1.add("final");
			return; 
		}

		// Marco las celdas visitadas 
		visited[fila-1][columna-1] = true; 

		// movimiento diagonal abajo derecha 
		if (movimientoseguro2(fila+1, columna+1, laberinto, n, visited, i_asterisco, j_asterisco)){ 
			if(laberinto[fila-1][columna-1]=='*'){
				camino1.add("("+(fila)+","+(columna)+")"+"*"/*+" Dabd "*/);
			}else{
				camino1.add("("+(fila)+","+(columna)+")"/*+" Dabd "*/);
			}
			recorridoAnchuraAux2(fila+1, columna+1, laberinto, n, camino1, visited, i_asterisco, j_asterisco);
		}

		// movimiento abajo 
		if (movimientoseguro2(fila+1, columna, laberinto, n, visited, i_asterisco, j_asterisco)){ 
			if(laberinto[fila-1][columna-1]=='*'){
				camino1.add("("+(fila)+","+(columna)+")"+"*"/*+" aba "*/);
			}else{
				camino1.add("("+(fila)+","+(columna)+")"/*+" aba "*/);
			} 
			recorridoAnchuraAux2(fila+1, columna, laberinto, n, camino1, visited, i_asterisco, j_asterisco);
		}

		// movimiento derecha 
		if (movimientoseguro2(fila, columna+1, laberinto, n, visited, i_asterisco, j_asterisco)){ 
			if(laberinto[fila-1][columna-1]=='*'){
				camino1.add("("+(fila)+","+(columna)+")"+"*"/*+" de "*/);
			}else{
				camino1.add("("+(fila)+","+(columna)+")"/*+" de "*/);
			}
			recorridoAnchuraAux2(fila, columna+1, laberinto, n, camino1, visited, i_asterisco, j_asterisco); 
		}

		// movimiento izquierda 
		if (movimientoseguro2(fila, columna-1, laberinto, n, visited, i_asterisco, j_asterisco)){ 
			if(laberinto[fila-1][columna-1]=='*'){
				camino1.add("("+(fila)+","+(columna)+")"+"*"/*+" iz "*/);
			}else{
				camino1.add("("+(fila)+","+(columna)+")"/*+" iz "*/);
			}
			recorridoAnchuraAux2(fila, columna-1, laberinto, n, camino1, visited, i_asterisco, j_asterisco);  
		} 

		// movimiento diagonal abajo izquierda 
		if (movimientoseguro2(fila+1, columna-1, laberinto, n, visited, i_asterisco, j_asterisco)){ 
			if(laberinto[fila-1][columna-1]=='*'){
				camino1.add("("+(fila)+","+(columna)+")"+"*"/*+" Dabi "*/);
			}else{
				camino1.add("("+(fila)+","+(columna)+")"/*+" Dabi "*/);
			}
			recorridoAnchuraAux2(fila+1, columna-1, laberinto, n, camino1, visited, i_asterisco, j_asterisco);
		} 

		// movimiento diagonal arriba izquierda 
		if (movimientoseguro2(fila-1, columna-1, laberinto, n, visited, i_asterisco, j_asterisco)){ 
			if(laberinto[fila-1][columna-1]=='*'){
				camino1.add("("+(fila)+","+(columna)+")"+"*"/*+" Dari "*/);
			}else{
				camino1.add("("+(fila)+","+(columna)+")"/*+" Dari "*/); 
			}
			recorridoAnchuraAux2(fila-1, columna-1, laberinto, n, camino1, visited, i_asterisco, j_asterisco);
		}

		// movimiento diagonal arriba derecha 
		if (movimientoseguro2(fila-1, columna+1, laberinto, n, visited, i_asterisco, j_asterisco)){ 
			if(laberinto[fila-1][columna-1]=='*'){
				camino1.add("("+(fila)+","+(columna)+")"+"*"/*+" Dard "*/);
			}else{
				camino1.add("("+(fila)+","+(columna)+")"/*+" Dard "*/);
			}
			recorridoAnchuraAux2(fila-1, columna+1, laberinto, n, camino1, visited, i_asterisco, j_asterisco);
		}

		// movimiento arriba 
		if (movimientoseguro2(fila-1, columna, laberinto, n, visited, i_asterisco, j_asterisco)){
			if(laberinto[fila-1][columna-1]=='*'){
				camino1.add("("+(fila)+","+(columna)+")"+"*"/*+" arr "*/);
			}else{
				camino1.add("("+(fila)+","+(columna)+")"/*+" arr "*/);
			}
			recorridoAnchuraAux2(fila-1, columna, laberinto, n, camino1, visited, i_asterisco, j_asterisco); 
		} 

		// marca la celda como no visitada para otros caminos posibles
		visited[fila-1][columna-1] = false;
	} 

	//metodo para saber si el movimineto es valido o no
	public static boolean movimientoseguro2(int fila, int columna, char laberinto[][], int n, boolean visited[][], int i_asterisco, int j_asterisco){
		if (fila == 0 || fila == i_asterisco+1 || columna == 0 ||  columna == j_asterisco+1 || visited[fila-1][columna-1] || laberinto[fila-1][columna-1] == '1'){ 
			return false; 
		}
		return true; 	
	}
	
}