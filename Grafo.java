/** Algoritmos y Estructuras de datos -  seccion 30
 * Luis Francisco Padilla Juárez - 23663
 * HT10, Floyd
 * 26-05-2024
 * @return Grafo
 */

import java.util.*;

public class Grafo {
    private static final int INF = Integer.MAX_VALUE;
    private List<String> ciudades;
    private int[][] matrizAdyacencia;
    private Map<String, Integer> ciudadIndices;

    public int INF(){
        return INF;
    }

    public Grafo(List<String> ciudades, List<Conexion> conexiones) {
        this.ciudades = new ArrayList<>(ciudades);
        int n = ciudades.size();
        matrizAdyacencia = new int[n][n];
        ciudadIndices = new HashMap<>();
        
        for (int i = 0; i < n; i++) {
            ciudadIndices.put(ciudades.get(i), i);
            Arrays.fill(matrizAdyacencia[i], INF);
            matrizAdyacencia[i][i] = 0;
        }

        for (Conexion conexion : conexiones) {
            int i = ciudadIndices.get(conexion.ciudad1);
            int j = ciudadIndices.get(conexion.ciudad2);
            matrizAdyacencia[i][j] = conexion.distancia;
        }
    }

    public int[][] getMatrizAdyacencia() {
        return matrizAdyacencia;
    }

    public Map<String, Integer> getCiudadIndices() {
        return ciudadIndices;
    }

    public List<String> getCiudades() {
        return ciudades;
    }

    public void modificarConexion(String ciudad1, String ciudad2, int distancia) {
        int i = ciudadIndices.get(ciudad1);
        int j = ciudadIndices.get(ciudad2);
        matrizAdyacencia[i][j] = distancia;
    }

    public static class Conexion {
        String ciudad1;
        String ciudad2;
        int distancia;

        public Conexion(String ciudad1, String ciudad2, int distancia) {
            this.ciudad1 = ciudad1;
            this.ciudad2 = ciudad2;
            this.distancia = distancia;
        }
    }
}
