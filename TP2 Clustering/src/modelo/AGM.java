package modelo;

import javax.swing.plaf.synth.SynthSpinnerUI;

public class AGM 
{
	private Grafo grafoAGM;
	private boolean marcados[];
	
	public AGM(Grafo grafo) 
	{
		grafoAGM = new Grafo(grafo.tamano());
		marcados = new boolean[grafo.tamano()];
		marcados[0] = true; //marco el inicial
		
		generarAGM(grafo);
	}
	
	private void generarAGM(Grafo grafo) 
	{
		double aristaMinima = 0;
		
		int vertice1 = 0;
		int vertice2 = 0;
		
		while(todosMarcados() == false) 
		{
			for(int i=0; i < marcados.length; i++) 
			{
				if(marcados[i]) 
				{
					for(Integer j : grafo.vecinos(i))
					{
						if(marcados[j] == false) 
						{
							if(aristaMinima == 0)
							{
								aristaMinima = grafo.obtenerPeso(i, j);
								vertice1 = i;
								vertice2 = j;
							}
							
							if( grafo.obtenerPeso(i, j) < aristaMinima) 
							{
								aristaMinima = grafo.obtenerPeso(i, j);
								vertice1 = i;
								vertice2 = j;
							}
						}
					}
				}
			}
			
			grafoAGM.agregarArista(vertice1, vertice2, grafo.obtenerPeso(vertice1, vertice2));
			aristaMinima = 0;
			marcados[vertice2] = true;
		}
	}
	
	public boolean todosMarcados() 
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
