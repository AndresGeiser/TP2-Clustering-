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
	
	
}
