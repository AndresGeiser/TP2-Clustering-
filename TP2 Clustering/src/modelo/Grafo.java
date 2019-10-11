package modelo;

import java.util.HashSet;
import java.util.Set;

public class Grafo
{
	// Representamos el grafo por su matriz de distancias
	private double[][] D;
	
	// El conjunto de vertices esta fijo
	public Grafo(int vertices)
	{
		D = new double[vertices][vertices];
	}
	
	// Operaciones sobre aristas
	public void agregarArista(int i, int j, double distancia)
	{
		verificarIndices(i, j);
		D[i][j] = D[j][i] = distancia;	
	}
	
	public void borrarArista(int i, int j)
	{
		verificarIndices(i, j);
		D[i][j] = D[j][i] = 0;		
	}
	
	public boolean existeArista(int i, int j)
	{
		verificarIndices(i, j);
		return D[i][j] > 0;
	}

	public double obtenerPeso(int i, int j)
	{
		verificarIndices(i, j);
		return D[i][j];
	}
	
	// Vecinos de un vertice
	public Set<Integer> vecinos(int i)
	{
		verificarVertice(i);
		
		Set<Integer> ret = new HashSet<Integer>();
		for(int j=0; j<tamano(); ++j) 
			if( i!=j && existeArista(i,j) )
			ret.add(j);
		
		return ret;
	}

	// Cantidad de vertices
	public int tamano()
	{
		return D.length;
	}

	// Lanza excepciones si los indices no son validos
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

	public double[][] getMatriz()
	{
		return D;
	}
	
}
