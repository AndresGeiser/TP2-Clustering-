package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import modelo.Modelo;
import vista.Vista;

public class Controlador implements ActionListener
{
	private Modelo modelo;
	private Vista vista;
	
	public Controlador(Modelo modelo, Vista vista) 
	{
		this.modelo = modelo;
		this.vista = vista;
		
		vista.botonImportar.addActionListener(this);
		vista.botonExportar.addActionListener(this);
		vista.botonNuevo.addActionListener(this);
		vista.botonGuardar.addActionListener(this);
	}

	public void iniciar() 
	{
		vista.setVisible(true);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == vista.botonImportar )
		{
			importar();
			
			modelo.armarGrafo();
			
			colocarPanelsito();
		}
		
		if(e.getSource() == vista.botonNuevo) 
		{
			
		}
	}
	
	private void importar() 
	{
		try 
	    {
			buscarArchivo();
		} 
		catch (IOException e1)
	   	{
			e1.printStackTrace();
		}
	}
	
	private void buscarArchivo() throws IOException 
	{
		JFileChooser jf = new JFileChooser();
		jf.showOpenDialog(vista);
		
		leerArchivo(jf.getSelectedFile());
	}
	
	public void leerArchivo(File file) throws IOException
	{	
			String x ;
			String y ;
			
			BufferedReader bf = new BufferedReader(new FileReader(file));
			
			String linea;
			
			boolean llegoAlEspacio;
		
			while(!(linea=bf.readLine()).equals(""))
			{
				llegoAlEspacio = false;
				
				x = "";
				y = "";
				
				for(int i=0; i < linea.length(); i++) 
				{
					if(i == 0 && linea.charAt(i) == ' ') //Condicion Agregada para el txt 4
						i++;
					
					if(llegoAlEspacio == false) 
					{
						if(linea.charAt(i) != ' ')
							x += linea.charAt(i);
						else
							llegoAlEspacio = true;
					}
					else
						y += linea.charAt(i);
					
					
				}
				
				modelo.agregarCoordenada(new Coordinate(Double.parseDouble(x), Double.parseDouble(y)));
	
			}

			bf.close();
			
	}
	
	private void colocarPanelsito() 
	{
		String nombre = JOptionPane.showInputDialog("Nombre: ");
		
		vista.panelDeControles.agregar(new PanelControl(nombre, modelo, vista));
		vista.panelDeControles.updateUI();
		
		modelo = new Modelo();
	}
}
