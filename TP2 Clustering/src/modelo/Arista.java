package modelo;

public class Arista 
{
	private int vertice1;
	private int vertice2;
	private double peso;
	
	public Arista(int vertice1, int vertice2, double peso) 
	{
		this.peso = peso;
		this.vertice1 = vertice1;
		this.vertice2 = vertice2;
	}

	public int getVertice1() 
	{
		return vertice1;
	}

	public int getVertice2() 
	{
		return vertice2;
	}

	public double getPeso() 
	{
		return peso;
	}

	@Override
	public String toString()
	{
		StringBuilder s = new StringBuilder();
		
		return s.append(vertice1 + " " + vertice2).toString();
	}
}
