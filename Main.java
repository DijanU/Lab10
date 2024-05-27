/** Algoritmos y Estructuras de datos -  seccion 30
 * Luis Francisco Padilla Juárez - 23663
 * HT10, Floyd
 * 26-05-2024
 * @return main
 */

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        final int Infinit = Integer.MAX_VALUE;
        List<String> ciudades = new ArrayList<>();
        List<Grafo.Conexion> conexiones = new ArrayList<>();
        leerArchivo("guategrafo.txt", ciudades, conexiones);

        Grafo grafo = new Grafo(ciudades, conexiones);
        FloydWarshall floydWarshall = new FloydWarshall(grafo.getMatrizAdyacencia());

        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\nOpciones del menú:");
            System.out.println("1. Ruta más corta entre dos ciudades");
            System.out.println("2. Ciudad centro del grafo");
            System.out.println("3. Modificar el grafo");
            System.out.println("4. Finalizar");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();  // Consumir la nueva línea

            if (opcion == 1) {
                System.out.print("Ingrese la ciudad de origen: ");
                String origen = scanner.nextLine();
                System.out.print("Ingrese la ciudad de destino: ");
                String destino = scanner.nextLine();
                
                if (grafo.getCiudadIndices().containsKey(origen) && grafo.getCiudadIndices().containsKey(destino)) {
                    int origenIdx = grafo.getCiudadIndices().get(origen);
                    int destinoIdx = grafo.getCiudadIndices().get(destino);
                    String ruta = floydWarshall.rutaMasCorta(origenIdx, destinoIdx);
                    int distancia = floydWarshall.getDist()[origenIdx][destinoIdx];
                    System.out.println("La ruta más corta de " + origen + " a " + destino + " es: " + ruta + " con una distancia de " + distancia + " KM.");
                } else {
                    System.out.println("Una o ambas ciudades no existen en el grafo.");
                }

            } else if (opcion == 2) {
                int centroIdx = floydWarshall.ciudadCentro();
                String ciudadCentro = grafo.getCiudades().get(centroIdx);
                int distanciaCentro = floydWarshall.getDist()[centroIdx][floydWarshall.ciudadCentro()];
                System.out.println("La ciudad centro del grafo es " + ciudadCentro + " con una distancia máxima de " + distanciaCentro + " KM a cualquier otra ciudad.");
            
            } else if (opcion == 3) {
                System.out.println("1. Interrupción de tráfico entre dos ciudades");
                System.out.println("2. Establecer una nueva conexión entre dos ciudades");
                System.out.print("Seleccione una subopción: ");
                int subopcion = scanner.nextInt();
                scanner.nextLine();  // Consumir la nueva línea
                
                System.out.print("Ingrese la primera ciudad: ");
                String ciudad1 = scanner.nextLine();
                System.out.print("Ingrese la segunda ciudad: ");
                String ciudad2 = scanner.nextLine();
                
                if (subopcion == 1) {
                    grafo.modificarConexion(ciudad1, ciudad2, Infinit);
                    floydWarshall = new FloydWarshall(grafo.getMatrizAdyacencia());
                    System.out.println("Se ha eliminado la conexión entre " + ciudad1 + " y " + ciudad2 + ".");
                
                } else if (subopcion == 2) {
                    System.out.print("Ingrese la nueva distancia en KM: ");
                    int nuevaDistancia = scanner.nextInt();
                    grafo.modificarConexion(ciudad1, ciudad2, nuevaDistancia);
                    floydWarshall = new FloydWarshall(grafo.getMatrizAdyacencia());
                    System.out.println("Se ha establecido una nueva conexión entre " + ciudad1 + " y " + ciudad2 + " con una distancia de " + nuevaDistancia + " KM.");
                }

            } else if (opcion == 4) {
                System.out.println("Finalizando el programa.");
                break;

            } else {
                System.out.println("Opción no válida. Intente de nuevo.");
            }
        }

        scanner.close();
    }

    private static void leerArchivo(String nombreArchivo, List<String> ciudades, List<Grafo.Conexion> conexiones) {
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("\\s+");
                String ciudad1 = partes[0];
                String ciudad2 = partes[1];
                int distancia = Integer.parseInt(partes[2]);
                conexiones.add(new Grafo.Conexion(ciudad1, ciudad2, distancia));
                if (!ciudades.contains(ciudad1)) {
                    ciudades.add(ciudad1);
                }
                if (!ciudades.contains(ciudad2)) {
                    ciudades.add(ciudad2);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}          
