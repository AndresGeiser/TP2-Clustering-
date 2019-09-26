package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;

import modelo.Modelo;
import vista.Vista;

public class Controlador implements ActionListener
{
	private Modelo modelo;
	private Vista vista;
	private ArrayList<Coordinate> coordenadas = new ArrayList<Coordinate>();


	public Controlador(Modelo modelo, Vista vista) 
	{
		this.modelo = modelo;
		this.vista = vista;
		vista.botonImportar.addActionListener(this);
	}

	public void iniciar() 
	{
		vista.setVisible(true);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == vista.botonImportar )
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
		colocarCoord();
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
					 coordenadas.add(coor);
					 x.setLength(0);
					 y.setLength(0);
					 hayEspacio = false;
					 i++;
					 
					 //System.out.println(coordenadas.get(i -1 ));
					
				}	
					
				if ( i >= 68)
					break;

			}
			
			bf.close();
			
	}
	
	private void colocarCoord()
	{
		for(Coordinate coor : coordenadas)
			vista.mapa.addMapMarker(new MapMarkerDot(coor));
	}
		
}
