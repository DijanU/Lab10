# Algoritmos y Estructuras de datos -  seccion 30
# Luis Francisco Padilla Juárez - 23663
# HT10, Floyd
# 26-05-2024
# Alternative

import networkx as nx

INF = float('inf')

def leer_archivo(nombre_archivo):
    ciudades = set()
    conexiones = []
    with open(nombre_archivo, 'r') as archivo:
        for linea in archivo:
            ciudad1, ciudad2, distancia = linea.strip().split()
            ciudades.add(ciudad1)
            ciudades.add(ciudad2)
            conexiones.append((ciudad1, ciudad2, int(distancia)))
    return list(ciudades), conexiones

def construir_grafo(ciudades, conexiones):
    grafo = nx.Graph()
    grafo.add_nodes_from(ciudades)
    for ciudad1, ciudad2, distancia in conexiones:
        grafo.add_edge(ciudad1, ciudad2, weight=distancia)
    return grafo

def floyd_warshall(grafo):
    dist = nx.floyd_warshall_numpy(grafo)
    return dist

def ruta_mas_corta(grafo, origen, destino):
    try:
        ruta = nx.shortest_path(grafo, origen, destino, weight='weight')
        distancia = nx.shortest_path_length(grafo, origen, destino, weight='weight')
        return ruta, distancia
    except nx.NetworkXNoPath:
        return None, None

def centro_del_grafo(grafo):
    ecc = nx.eccentricity(grafo)
    centro = min(ecc, key=ecc.get)
    distancia_centro = max(ecc.values())
    return centro, distancia_centro

def modificar_grafo(grafo, ciudad1, ciudad2, nueva_distancia):
    if grafo.has_edge(ciudad1, ciudad2):
        grafo[ciudad1][ciudad2]['weight'] = nueva_distancia
    else:
        grafo.add_edge(ciudad1, ciudad2, weight=nueva_distancia)

def main():
    nombre_archivo = 'guategrafo.txt'
    ciudades, conexiones = leer_archivo(nombre_archivo)
    grafo = construir_grafo(ciudades, conexiones)
    
    while True:
        print("\nOpciones del menú:")
        print("1. Ruta más corta entre dos ciudades")
        print("2. Ciudad centro del grafo")
        print("3. Modificar el grafo")
        print("4. Finalizar")
        opcion = input("Seleccione una opción: ")
        
        if opcion == '1':
            origen = input("Ingrese la ciudad de origen: ")
            destino = input("Ingrese la ciudad de destino: ")
            if origen in grafo.nodes and destino in grafo.nodes:
                ruta, distancia = ruta_mas_corta(grafo, origen, destino)
                if ruta:
                    print(f"La ruta más corta de {origen} a {destino} es {ruta} con una distancia de {distancia} KM.")
                else:
                    print(f"No hay ruta de {origen} a {destino}.")
            else:
                print("Una o ambas ciudades no existen en el grafo.")
                
        elif opcion == '2':
            centro, distancia_centro = centro_del_grafo(grafo)
            print(f"La ciudad centro del grafo es {centro} con una distancia máxima de {distancia_centro} KM a cualquier otra ciudad.")
        
        elif opcion == '3':
            print("1. Interrupción de tráfico entre dos ciudades")
            print("2. Establecer una nueva conexión entre dos ciudades")
            subopcion = input("Seleccione una subopción: ")
            
            ciudad1 = input("Ingrese la primera ciudad: ")
            ciudad2 = input("Ingrese la segunda ciudad: ")
            
            if subopcion == '1':
                grafo.remove_edge(ciudad1, ciudad2)
                print(f"Se ha eliminado la conexión entre {ciudad1} y {ciudad2}.")
                
            elif subopcion == '2':
                nueva_distancia = int(input("Ingrese la nueva distancia en KM: "))
                modificar_grafo(grafo, ciudad1, ciudad2, nueva_distancia)
                print(f"Se ha establecido una nueva conexión entre {ciudad1} y {ciudad2} con una distancia de {nueva_distancia} KM.")
            
        elif opcion == '4':
            print("Finalizando el programa.")
            break
        
        else:
            print("Opción no válida. Intente de nuevo.")

if __name__ == '__main__':
    main()
