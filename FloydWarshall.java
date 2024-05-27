/** Algoritmos y Estructuras de datos -  seccion 30
 * Luis Francisco Padilla Juárez - 23663
 * HT10, Floyd
 * 26-05-2024
 * @return FloydWarshall
 */

import java.util.Arrays;

public class FloydWarshall {
    private int[][] dist;
    private int[][] nextHop;
    private static final int INF = Integer.MAX_VALUE;

    public FloydWarshall(int[][] matrizAdyacencia) {
        int n = matrizAdyacencia.length;
        dist = new int[n][n];
        nextHop = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dist[i][j] = matrizAdyacencia[i][j];
                if (dist[i][j] != INF && i != j) {
                    nextHop[i][j] = j;
                } else {
                    nextHop[i][j] = -1;
                }
            }
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (dist[i][k] != INF && dist[k][j] != INF && dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        nextHop[i][j] = nextHop[i][k];
                    }
                }
            }
        }
    }

    public int[][] getDist() {
        return dist;
    }

    public int[][] getNextHop() {
        return nextHop;
    }

    public String rutaMasCorta(int origen, int destino) {
        if (nextHop[origen][destino] == -1) {
            return "No hay ruta.";
        }
        StringBuilder ruta = new StringBuilder();
        while (origen != destino) {
            ruta.append(origen).append(" -> ");
            origen = nextHop[origen][destino];
        }
        ruta.append(destino);
        return ruta.toString();
    }

    public int ciudadCentro() {
        int n = dist.length;
        int[] excéntricas = new int[n];
        Arrays.fill(excéntricas, 0);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (dist[i][j] != INF) {
                    excéntricas[i] = Math.max(excéntricas[i], dist[i][j]);
                }
            }
        }

        int centro = 0;
        for (int i = 1; i < n; i++) {
            if (excéntricas[i] < excéntricas[centro]) {
                centro = i;
            }
        }

        return centro;
    }
}

