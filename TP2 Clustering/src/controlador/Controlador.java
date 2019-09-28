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
	
	private int	yPanelGrafos = 0;
	
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
		
		File file = jf.getSelectedFile();
		
		leerCoordenadas(file);
		
		
	}
	
	public void leerCoordenadas(File file) throws IOException
	{	
			StringBuilder x = new StringBuilder("");
			StringBuilder y = new StringBuilder("");
			
			BufferedReader bf = new BufferedReader(new FileReader(file));
			int caracter  = bf.read();
			boolean hayEspacio = false;
			int i = 0;
			
			while(caracter != -1 )
			{	
				
				Coordinate coor;
				while ( !hayEspacio)
				{
					x.append((char) caracter);
					caracter = bf.read();
					hayEspacio = true && ( (char)caracter == ' ' );	
				}
				
				caracter = bf.read();

				
				if ( (char)caracter != '\n')
				{
					y.append((char) caracter);
					hayEspacio = true;
				}
			
				else
				{
					 coor = new Coordinate(Double.parseDouble(x.toString()), Double.parseDouble(y.toString()));
					 modelo.agregarCoordenada(coor);
					 x.setLength(0);
					 y.setLength(0);
					 hayEspacio = false;
					 i++;
					 					
				}	
					
				if ( i >= 68)
					break;

			}
			
			bf.close();
			
	}
	
	private void colocarPanelsito() 
	{
		String nombre = JOptionPane.showInputDialog("Nombre: ");
		
		vista.panelGrafos.add(new Panelsito(nombre, modelo, vista, yPanelGrafos));
		vista.panelGrafos.updateUI();
		
		yPanelGrafos += 37;
		
		modelo = new Modelo();
	}
}
