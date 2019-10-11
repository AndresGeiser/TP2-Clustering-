package modelo;

import java.util.ArrayList;

import org.openstreetmap.gui.jmapviewer.Coordinate;

public class CalculosAuxiliares 
{
	public static double distanciaEuclidea(Coordinate i, Coordinate j) 
	{
		double x1 = i.getLon();
		double y1 = i.getLat();
		
		double x2 = j.getLon();
		double y2 = j.getLat();
		
		return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2-y1), 2));
	}
	
	public static double desviacionEstandar(ArrayList<Arista> aristas)
	{
		double sumaDif = 0;
		double suma = 0;
		double varianza;
		ArrayList<Double> numeros = new ArrayList<Double>();
		
		
		for(Arista arista: aristas) //Se realiza la sumatoria de todos los numeros
		{
			numeros.add(arista.getPeso());
			suma += arista.getPeso();
		}
		
		int conteo = numeros.size();   //se obtiene la cantidad de numeros
		double promedio = suma/conteo; //Se toma la media
		
		for(Double numero: numeros) //Se resta a cada numero la media y se eleva al cuadrado 
		{
			numero = numero - promedio;
			numero = Math.pow(numero, 2);
			
			sumaDif += numero; //Se suman las diferencias al cuadrado
		}
		
		varianza = sumaDif/conteo;
		
		return Math.sqrt(varianza);
		
	}
	
}
