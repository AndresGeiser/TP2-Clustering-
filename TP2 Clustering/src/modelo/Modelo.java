package modelo;

import java.util.ArrayList;

import org.openstreetmap.gui.jmapviewer.Coordinate;


public class Modelo 
{
	private ArrayList<Coordinate> coordenadas;
	Grafo grafo;
	
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
			for(int j=i+1; j < coordenadas.size(); j++)
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
		
	}
	
	
	public void eliminarAristas(int n) 
	{
		
	}
	
	public ArrayList<Coordinate> coordenadas() 
	{
		return coordenadas;
	}
	
}
