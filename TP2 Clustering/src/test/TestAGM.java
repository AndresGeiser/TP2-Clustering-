package test;

import static org.junit.Assert.*;
import modelo.AGM;
import modelo.Grafo;
import org.junit.Test;

public class TestAGM 
{
	@Test //Prueba que se elimine la arista con mayor distancia
	public void testEliminarMayor()
	{
		Grafo grafo = new Grafo(3);
		
		grafo.agregarArista(0, 1, 10);
		grafo.agregarArista(1, 2, 5);
		grafo.agregarArista(0, 2, 16);
		
		AGM grafoAGM = new AGM(grafo);
		grafo = grafoAGM.getGrafoAGM();

		assertFalse(grafo.existeArista(0, 2));
	}

	@Test
	public void testEliminarMayorInversa()
	{
		Grafo grafo = new Grafo(3);
		
		grafo.agregarArista(0, 1, 10);
		grafo.agregarArista(1, 2, 5);
		grafo.agregarArista(0, 2, 16);
		
		AGM grafoAGM = new AGM(grafo);
		grafo = grafoAGM.getGrafoAGM();

		assertFalse(grafo.existeArista(2, 0));
	}
	
	@Test //Prueba que se elimine alguna de las aristas
	public void testAristasIguales()
	{
		Grafo grafo = new Grafo(3);
		
		grafo.agregarArista(0, 1, 10);
		grafo.agregarArista(1, 2, 5);
		grafo.agregarArista(0, 2, 10);
		
		AGM grafoAGM = new AGM(grafo);
		grafo = grafoAGM.getGrafoAGM();

		assertFalse(grafo.existeArista(0, 2));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAGMVacio()
	{
		Grafo grafo = new Grafo(0);
		
		AGM grafoAGM =  new AGM(grafo);
		grafo = grafoAGM.getGrafoAGM();
	}
	
}
