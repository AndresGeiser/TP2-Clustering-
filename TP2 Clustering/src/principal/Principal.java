package principal;

import javax.swing.UIManager;

import controlador.Controlador;
import modelo.Modelo;
import vista.Vista;

public class Principal 
{

	public static void main(String[] args) 
	{	
		try{ 
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		Modelo modelo = new Modelo();
		Vista vista = new Vista();

		Controlador controlador = new Controlador(modelo, vista);		
		controlador.iniciar();		
	}

}
