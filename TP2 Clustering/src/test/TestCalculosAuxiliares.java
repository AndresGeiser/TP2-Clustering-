package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import modelo.Arista;
import modelo.CalculosAuxiliares;

public class TestCalculosAuxiliares {

	@Test
	public void testDosAristas() 
	{
		ArrayList<Arista> aristas = new ArrayList<Arista>();

		aristas.add(new Arista(0,1,1));
		aristas.add(new Arista(0,2,10));
		
		assertTrue(CalculosAuxiliares.desviacionEstandar(aristas) == 4.5);
	}

	@Test
	public void testTresAristas()
	{
		ArrayList<Arista> aristas = new ArrayList<Arista>();

		aristas.add(new Arista(0,0,1));
		aristas.add(new Arista(0,1,2));
		aristas.add(new Arista(0,0,10));
		
		assertTrue(CalculosAuxiliares.desviacionEstandar(aristas) == 4.0276819911981905);
	}
	
	
}
