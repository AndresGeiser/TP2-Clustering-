package modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Grafo
{
	// Representamos el grafo por su matriz de adyacencia
	private double[][] A;
	private ArrayList<Double> listaVertices = new ArrayList<Double>();
	
	// El conjunto de vértices está fijo
	public Grafo(int vertices)
	{
		A = new double[vertices][vertices];
	}
	
	// Operaciones sobre aristas
	public void agregarArista(int i, int j, double distancia)
	{
		verificarIndices(i, j);
		A[i][j] = A[j][i] = distancia;
		
		listaVertices.add(distancia);
	}
	public void borrarArista(int i, int j)
	{
		verificarIndices(i, j);
		A[i][j] = A[j][i] = 0;
	}
	public boolean existeArista(int i, int j)
	{
		verificarIndices(i, j);
		return A[i][j] == A[j][i];
	}

	public double obtenerPeso(int i, int j)
	{
		verificarIndices(i, j);
		return A[i][j];
	}
	
	// Vecinos de un vertice
	public Set<Integer> vecinos(int i)
	{
		verificarVertice(i);
		
		Set<Integer> ret = new HashSet<Integer>();
		for(int j=0; j<tamano(); ++j) if( i!=j && existeArista(i,j) )
			ret.add(j);
		
		return ret;
	}

	// Cantidad de vertices
	public int tamano()
	{
		return A.length;
	}

	// Lanza excepciones si los índices no son válidos
	private void verificarIndices(int i, int j)
	{
		verificarVertice(i);
		verificarVertice(j);
		
		if( i == j )
			throw new IllegalArgumentException("No existen aristas entre un vertice y si mismo! vertice = " + i);
	}
	private void verificarVertice(int i)
	{
		if( i < 0 || i >= tamano() )
			throw new IllegalArgumentException("El vertice " + i + " no existe!");
	}

	public void imprimir() {
		for (int x=0; x < A.length; x++) {
			  System.out.print("|");
			  for (int y=0; y < A[x].length; y++) {
			    System.out.print (A[x][y]);
			    if (y!=A[x].length-1) System.out.print("\t");
			  }
			  System.out.println("|");
			}
	}
	
	public double[][] getMatriz()
	{
		return A;
	}
	
	public ArrayList<Double> getVertices()
	{
		return listaVertices;
	}
	
}
