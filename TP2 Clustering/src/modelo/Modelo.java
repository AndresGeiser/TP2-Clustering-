package modelo;

import java.util.ArrayList;

import org.openstreetmap.gui.jmapviewer.Coordinate;

public class Modelo 
{
	private Grafo grafo;
	private ArrayList<Coordinate> coordenadas;
	
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
		
	}
	private void armarGrafoCompleto()
	{
		for (int i=0; i < coordenadas.size() - 1; i++)
		{
			for(int j= i+1; j < coordenadas.size(); j++)
			{
				double pesoArista = distanciaEuclidiana(coordenadas.get(i) , coordenadas.get(j));
				
				grafo.agregarArista(i, j, pesoArista);
			}
		}
	}
	private double distanciaEuclidiana(Coordinate i, Coordinate j) 
	{
		double x1 = i.getLon();
		double y1 = i.getLat();
		
		double x2 = j.getLon();
		double y2 = j.getLat();
		
		return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2-y1), 2));
	}
	
	private void grafoArbolMinimo() 
	{
		AGM grafoAGM = new AGM(grafo);
		grafo = grafoAGM.getGrafoAGM();
	}

	//Elimina las n aristas con mayor peso
	public void clustering(int n) 
	{
		for (int j = 0; j < n; j++) 
		{
			Arista mayor = grafo.getAristas().get(0);
			
			for (int i = 0; i < grafo.getAristas().size(); i++) 
			{
				if (grafo.getAristas().get(i).getPeso() > mayor.getPeso())
					mayor = grafo.getAristas().get(i);
			}
		
			grafo.borrarArista(mayor.getVertice1(), mayor.getVertice2(), mayor);
		}
	}
	
	public ArrayList<Coordinate> getCoordenadas() 
	{
		return coordenadas;
	}
	
	public Grafo getGrafo() 
	{
		return grafo;
	}
		
}
