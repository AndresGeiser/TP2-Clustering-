package modelo;

import java.util.ArrayList;


public class Prim 
{
	private ArrayList<Double> ListaVertices;
	
	public Prim(ArrayList<Double> lista)
	{
		this.ListaVertices = lista;
	}

	public double[][] AlgPrim(double[][] Matriz) 		//Llega la matriz a la que le vamos a aplicar el algoritmo
	{
        boolean[] marcados = new boolean[ListaVertices.size()]; 			//Creamos un vector booleano, para saber cuales están marcados
        double vertice = ListaVertices.get(0); 						//Le introducimos un nodo aleatorio, o el primero
        
        return AlgPrim(Matriz, marcados, vertice, new double[Matriz.length][Matriz.length]); 		//Llamamos al método recursivo mandándole 
    }                                                                                     			//un matriz nueva para que en ella nos 
                                                                                          			//devuelva el árbol final
	private double[][] AlgPrim(double[][] Matriz, boolean[] marcados, double vertice, double[][] Final) 
    {
        marcados[ListaVertices.indexOf(vertice)] = true;//marcamos el primer nodo
        double aux = -1;
        if (!TodosMarcados(marcados)) { 				//Mientras que no todos estén marcados
            for (int i = 0; i < marcados.length; i++) { //Recorremos sólo las filas de los nodos marcados
                if (marcados[i]) 
                {
                    for (int j = 0; j < Matriz.length; j++) 
                    {
                        if (Matriz[i][j] != 0) {        //Si la arista existe
                            if (!marcados[j]) {         //Si el nodo no ha sido marcado antes
                                if (aux == -1) {        //Esto sólo se hace una vez
                                    aux = Matriz[i][j];
                                } else {
                                    aux = Math.min(aux, Matriz[i][j]); //Encontramos la arista mínima
                                }
                            }
                        }
                    }
                }
            }
            //Aquí buscamos el nodo correspondiente a esa arista mínima (aux)
            for (int i = 0; i < marcados.length; i++)
            {
                if (marcados[i]) {
                    for (int j = 0; j < Matriz.length; j++) 
                    {
                        if (Matriz[i][j] == aux) {
                            if (!marcados[j]) { //Si no ha sido marcado antes
                                Final[i][j] = aux; //Se llena la matriz final con el valor
                                Final[j][i] = aux;//Se llena la matriz final con el valor
                                return AlgPrim(Matriz, marcados, ListaVertices.get(j), Final); //se llama de nuevo al método con
                                                                                               //el nodo a marcar
                            }
                        }
                    }
                }
            }
        }
        return Final;
    }
    public boolean TodosMarcados(boolean[] vertice) { //Método para saber si todos están marcados
        for (boolean b : vertice)
        {
            if (!b) 
            {
                return b;
            }
        }
        return true;
    }
	
}
