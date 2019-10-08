package test;

import static org.junit.Assert.*;

import org.junit.Test;

import modelo.Grafo;

public class AgregarAristaTest
{
	@Test(expected = IllegalArgumentException.class)
	public void primerVerticeNegativoTest()
	{
		Grafo grafo = new Grafo(5);
		grafo.agregarArista(-1, 4,0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void primerVerticeExcedidoTest()
	{
		Grafo grafo = new Grafo(5);
		grafo.agregarArista(5, 4,0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void segundoVerticeNegativoTest()
	{
		Grafo grafo = new Grafo(5);
		grafo.agregarArista(3, -1,0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void segundoVerticeExcedidoTest()
	{
		Grafo grafo = new Grafo(5);
		grafo.agregarArista(2, 5,0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void verticesIgualesTest()
	{
		Grafo grafo = new Grafo(5);
		grafo.agregarArista(2, 2,0);
	}

	@Test
	public void aristaExistenteTest()
	{
		Grafo grafo = new Grafo(5);
		grafo.agregarArista(2, 4, 1); //se le agrega distancia de lo contrario i,j = 0
		assertTrue( grafo.existeArista(2, 4) );
	}

	@Test
	public void aristaRepetidaTest()
	{
		Grafo grafo = new Grafo(5);
		grafo.agregarArista(2, 4,1);
		
		grafo.agregarArista(2, 4,1);
		assertTrue( grafo.existeArista(2, 4) );
	}
	
	@Test
	public void aristaInvertidaTest()
	{
		Grafo grafo = new Grafo(5);
		grafo.agregarArista(2, 3, 1); //se le agrega distancia de lo contrario j,i = 0
		assertTrue( grafo.existeArista(3, 2) );
	}
	
	@Test
	public void aristaInexistenteTest()
	{
		Grafo grafo = new Grafo(5);
		assertFalse( grafo.existeArista(2, 0) );
	}
}





