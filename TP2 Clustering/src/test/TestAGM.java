package test;

import static org.junit.Assert.*;
import modelo.AGM;
import modelo.Grafo;
import org.junit.Test;

public class TestAGM 
{
	@Test
	public void testAGM()
	{
		Grafo grafo = new Grafo(3);
		
		grafo.agregarArista(0, 1, 10);
		grafo.agregarArista(1, 2, 5);
		grafo.agregarArista(0, 2, 16);
		
		AGM grafoAGM = new AGM(grafo);
		grafo = grafoAGM.getGrafoAGM();

		assertFalse(grafo.existeArista(0, 2));
	}

}
