package modelo;

import java.util.ArrayList;
import org.openstreetmap.gui.jmapviewer.Coordinate;

public class Modelo 
{
	private Grafo grafo;
	private ArrayList<Arista> aristasGrafoOriginal = new ArrayList<Arista>();
	private ArrayList<Coordinate> coordenadas;
	
	private double pesoTotal;
	private double desviacionEstandar;
	private int cantClusters;
	
	public Modelo()
	{
		coordenadas = new ArrayList<Coordinate>();
	}
	
	public void agregarCoordenada(Coordinate coordenada)
	{
		coordenadas.add(coordenada);
	}
	
	public void armarGrafo() 
	{
		grafo = new Grafo(coordenadas.size());
		
		armarGrafoCompleto();
		
		grafoArbolMinimo();
		
		actualizarDatos(aristasGrafoOriginal, 1);
		
	}
	
	private void armarGrafoCompleto()
	{
		for (int i=0; i < coordenadas.size() - 1; i++)
		{
			for(int j= i+1; j < coordenadas.size(); j++)
			{
				double pesoArista = CalculosAuxiliares.distanciaEuclidea(coordenadas.get(i), coordenadas.get(j));
				
				grafo.agregarArista(i, j, pesoArista);
			}
		}
	}
	
	
	private void grafoArbolMinimo() 
	{
		AGM grafoAGM = new AGM(grafo);
		grafo = grafoAGM.getGrafoAGM();
		aristasGrafoOriginal = grafoAGM.getAristas();
	}

	public void clustering(int n) 
	{
		armarGrafoOriginal();
	
		ArrayList<Arista> aristasAuxiliar = (ArrayList<Arista>) aristasGrafoOriginal.clone();//copiamos las aristas en una auxiliar para no perder sus valores
		
		eliminarAristasMayores(aristasAuxiliar, n-1);
		
		actualizarDatos(aristasAuxiliar, n);
	}
	
	private void armarGrafoOriginal() 
	{
		for(Arista arista : aristasGrafoOriginal)
			grafo.agregarArista(arista.getVertice1(), arista.getVertice2(), arista.getPeso());
		
	}
	
	private void eliminarAristasMayores(ArrayList<Arista> aristas, int n)
	{
		Arista aristaMaxima;
		for(int i=0; i < n ; i++) 
		{
			aristaMaxima = aristas.get(0);
			
			for(Arista arista : aristas)
				if(arista.getPeso() > aristaMaxima.getPeso())
					aristaMaxima = arista;
	
			grafo.borrarArista(aristaMaxima.getVertice1(), aristaMaxima.getVertice2());
			aristas.remove(aristaMaxima);
		}
	}

	private void actualizarDatos(ArrayList<Arista> aristas, int cantClusters) 
	{
		pesoTotalAristas(aristas);
		desviacionEstandar =  CalculosAuxiliares.desviacionEstandar(aristas);
		this.cantClusters = cantClusters;
	}
	
	private void pesoTotalAristas(ArrayList<Arista> aristas)
	{
		pesoTotal = 0;
		for(Arista arista: aristas)
			pesoTotal += arista.getPeso();
	}
	
	public ArrayList<Coordinate> getCoordenadas() 
	{
		return coordenadas;
	}
	
	public Grafo getGrafo() 
	{
		return grafo;
	}
	
	public int cantVertices()
	{
		return grafo.tamano();
	}
	
	public int cantClusters() 
	{
		return cantClusters;
	}
	
	public double pesoTotal()
	{
		return pesoTotal;
	}
	
	public double desviacionEstandar()
	{
		return desviacionEstandar;
	}
	

}
