package modelo;

import java.util.ArrayList;
import org.openstreetmap.gui.jmapviewer.Coordinate;

public class Modelo 
{
	private Grafo grafo;
	private ArrayList<Arista> aristasGrafoOriginal = new ArrayList<Arista>();
	private ArrayList<Coordinate> coordenadas;
	private CalculosAuxiliares calcAux;
	
	private double pesoTotal;
	private double desviacionEstandar;
	
	public Modelo()
	{
		coordenadas = new ArrayList<Coordinate>();
		calcAux = new CalculosAuxiliares();
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
		
		pesoTotalAristas(aristasGrafoOriginal);
		
		calcAux.getDesviacion(aristasGrafoOriginal);
		
	}
	
	private void armarGrafoCompleto()
	{
		for (int i=0; i < coordenadas.size() - 1; i++)
		{
			for(int j= i+1; j < coordenadas.size(); j++)
			{
				double pesoArista = calcAux.getDistanciaEuclidea(coordenadas.get(i), coordenadas.get(j));
				
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

	//Elimina las n aristas con mayor peso
	public void clustering(int n) 
	{
		armarGrafoOriginal();
	
		eliminarAristasMayores(n - 1);
	}
	
	private void armarGrafoOriginal() 
	{
		for(Arista arista : aristasGrafoOriginal)
			grafo.agregarArista(arista.getVertice1(), arista.getVertice2(), arista.getPeso());
		
	}
	
	private void eliminarAristasMayores(int n) //'n' cantidad de aristas a borrar
	{
		ArrayList<Arista> aristasAuxiliar = (ArrayList<Arista>) aristasGrafoOriginal.clone();//copiamos las aristas en una auxiliar para no perder sus valores
		Arista aristaMaxima;
		
		for(int i=0; i < n; i++) 
		{
			aristaMaxima = aristasAuxiliar.get(0);
			
			for(Arista arista : aristasAuxiliar)
				if(arista.getPeso() > aristaMaxima.getPeso())
					aristaMaxima = arista;
	
			grafo.borrarArista(aristaMaxima.getVertice1(), aristaMaxima.getVertice2());
			aristasAuxiliar.remove(aristaMaxima);
			
		}
		
		pesoTotalAristas(aristasAuxiliar);
		calcAux.getDesviacion(aristasAuxiliar);
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
	
	private void pesoTotalAristas(ArrayList<Arista> aristas)
	{
		pesoTotal = 0;
		for(Arista arista: aristas)
			pesoTotal += arista.getPeso();
	}
		
	
	public double getPesoTotal()
	{
		return pesoTotal;
	}
	
	public double getDesviacionEstandar()
	{
		return desviacionEstandar;
	}
	

}
