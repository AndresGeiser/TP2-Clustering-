package modelo;

import java.util.ArrayList;

import javax.management.RuntimeErrorException;

public class AGM 
{
	private Grafo grafoAGM;
	private boolean marcados[];
	private ArrayList<Arista> aristas;
	
	public AGM(Grafo grafo) 
	{
		grafoAGM = new Grafo(grafo.tamano());
		marcados = new boolean[grafo.tamano()];
		aristas = new ArrayList<Arista>();
		
		verificarTamanio(grafo);
		generarAGM(grafo);
	}
	
	private void verificarTamanio(Grafo grafo) 
	{
		if ( grafo.tamano() == 0)
			throw new IllegalArgumentException("El grafo no tiene vertices");
	}
	
	private void generarAGM(Grafo grafo) 
	{
		
		marcados[0] = true; //marco el inicial
		
		double aristaMinima = 0;
		int verticeMarcado = 0; //
		int verticeNoMarcado = 0; //Guardaremos el vecino no marcado
	
		while(todosMarcados() == false) /*Recorremos hasta que todos los vertices del grafo esten marcados*/
		{
			for(int i=0; i < marcados.length; i++) 
			{
				if(marcados[i]) /*Si esta marcado*/
				{
					for(Integer j : grafo.vecinos(i)) /*Recorremos sus vecinos*/
					{
						if(marcados[j] == false) /*Si su vecino no esta marcado*/
						{
							if(aristaMinima == 0) /*Condicion que solo entrara la primera vez para asignarle un valor distinto de 0 a aristaMinima*/
							{
								
								aristaMinima = grafo.obtenerPeso(i, j);
								verticeMarcado = i;
								verticeNoMarcado = j;
							}
							
							if( grafo.obtenerPeso(i, j) < aristaMinima) //Vemos si el peso de la arista entre el vertice marcado y  
							{												//su vecino no marcado es menor a la ultima arista minima que se encontro
								aristaMinima = grafo.obtenerPeso(i, j);
								verticeMarcado = i;
								verticeNoMarcado = j;
							}
						}
					}
				}
			}
		
			grafoAGM.agregarArista(verticeMarcado, verticeNoMarcado, grafo.obtenerPeso(verticeMarcado, verticeNoMarcado));
			aristas.add(new Arista(verticeMarcado, verticeNoMarcado, grafo.obtenerPeso(verticeMarcado, verticeNoMarcado)));
			marcados[verticeNoMarcado] = true;
			aristaMinima = 0;
		}
	}
	
	private boolean todosMarcados() 
	{
		for(boolean b : marcados) 
			if(b == false)
				return false;
		return true;
	}
	
	public Grafo getGrafoAGM() 
	{
		return grafoAGM;
	}
	
	public ArrayList<Arista> getAristas()
	{
		return aristas;
	}
	
}
