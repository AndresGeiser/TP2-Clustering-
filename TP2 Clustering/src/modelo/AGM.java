package modelo;

public class AGM 
{
	private Grafo grafoAGM;
	private boolean marcados[];
	
	public AGM(Grafo grafo) 
	{
		grafoAGM = new Grafo(grafo.tamano());
		marcados = new boolean[grafo.tamano()];
		
		generarAGM(grafo);
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
	

	public static void main(String[] args) 
	{	
		Grafo grafo = new Grafo(5);
		grafo.agregarArista(0, 1, 3);
		grafo.agregarArista(0, 2, 10);
		grafo.agregarArista(1, 2, 20);
		grafo.agregarArista(2, 3, 8);
		grafo.agregarArista(2, 4, 15);
		grafo.agregarArista(3, 4, 4);
		
		System.out.println("GRAFO INICIAL");
		grafo.imprimir();
		
		AGM grafoAGM = new AGM(grafo);
		grafo = grafoAGM.getGrafoAGM();
		
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("GRAFO MINIMO");
		
		grafo.imprimir();
		
	
	}

}
