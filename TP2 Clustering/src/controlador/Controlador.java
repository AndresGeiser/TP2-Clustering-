package controlador;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;

import modelo.Modelo;
import vista.Vista;

public class Controlador implements ActionListener
{
	private Modelo modelo;
	private Vista vista;
	
	private ArrayList<MapMarkerDot> marcasTemporales;
	
	public Controlador(Modelo modelo, Vista vista) 
	{
		this.modelo = modelo;
		this.vista = vista;
		
		marcasTemporales = new ArrayList<>();
	
		this.vista.botonImportar.addActionListener(this);
		this.vista.botonExportar.addActionListener(this);
		this.vista.botonNuevo.addActionListener(this);
		this.vista.botonGuardar.addActionListener(this);
		this.vista.botonCancelar.addActionListener(this);
		this.vista.botonBorrarUltimo.addActionListener(this);
		iniInteraccionConMapa();
	}
	
	private void iniInteraccionConMapa() 
	{
		this.vista.mapa.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				if(!vista.botonNuevo.isEnabled()) 
				{
					if (e.getButton() == MouseEvent.BUTTON1)
					{
						Coordinate coord = (Coordinate) vista.mapa.getPosition(e.getPoint());
						modelo.agregarCoordenada(coord);
						MapMarkerDot marca = new MapMarkerDot(coord);
						vista.mapa.addMapMarker(marca);
						marcasTemporales.add(marca);
						
						if(marcasTemporales.size() > 0)
						{
							vista.botonGuardar.setEnabled(true);
							vista.botonGuardar.setBackground(vista.rojo1);
							vista.botonBorrarUltimo.setEnabled(true);
							vista.botonBorrarUltimo.setBackground(vista.rojo1);
						}
					}
				}
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				if(!vista.botonNuevo.isEnabled())
					vista.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));		
			}
			
			@Override
			public void mouseExited(MouseEvent e) 
			{
				if(!vista.botonNuevo.isEnabled())
					vista.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			
			
		});
	}

	public void iniciar() 
	{
		vista.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == vista.botonImportar )
			importar();
			
		if(e.getSource() == vista.botonNuevo) 
		{
			vista.botonNuevo.setEnabled(false);
			vista.botonNuevo.setBackground(new Color(62, 54, 54));
			vista.botonImportar.setEnabled(false);
			vista.botonImportar.setBackground(vista.bordo);
			vista.botonExportar.setEnabled(false);
			vista.botonExportar.setBackground(vista.bordo);
			vista.botonCancelar.setEnabled(true);
			vista.botonCancelar.setBackground(vista.rojo1);
		}
		
		if(e.getSource() == vista.botonCancelar) 
		{
			vista.botonNuevo.setEnabled(true);
			vista.botonNuevo.setBackground(vista.rojo1);
			vista.botonImportar.setEnabled(true);
			vista.botonImportar.setBackground(vista.rojo1);
			vista.botonExportar.setEnabled(true);
			vista.botonExportar.setBackground(vista.rojo1);
			vista.botonCancelar.setEnabled(false);
			vista.botonCancelar.setBackground(vista.bordo);
			vista.botonBorrarUltimo.setEnabled(false);
			vista.botonBorrarUltimo.setBackground(vista.bordo);
			vista.botonGuardar.setEnabled(false);
			vista.botonGuardar.setBackground(vista.bordo);
			
			while(marcasTemporales.size() > 0) 
			{
				vista.mapa.removeMapMarker(marcasTemporales.get(0));
				marcasTemporales.remove(0);
				modelo.coordenadas().remove(0);
			}
		}
		
		if(e.getSource() == vista.botonGuardar) 
		{
			for(int i=0; i < marcasTemporales.size(); i++) 
				vista.mapa.removeMapMarker(marcasTemporales.get(i));
			
			vista.botonNuevo.setEnabled(true);
			vista.botonNuevo.setBackground(vista.rojo1);
			vista.botonImportar.setEnabled(true);
			vista.botonImportar.setBackground(vista.rojo1);
			vista.botonExportar.setEnabled(true);
			vista.botonExportar.setBackground(vista.rojo1);
			vista.botonCancelar.setEnabled(false);
			vista.botonCancelar.setBackground(vista.bordo);
			vista.botonGuardar.setEnabled(false);
			vista.botonGuardar.setBackground(vista.bordo);
			vista.botonBorrarUltimo.setEnabled(false);
			vista.botonBorrarUltimo.setBackground(vista.bordo);
			
			colocarPanelControl();
		}
		
		if(e.getSource() == vista.botonBorrarUltimo) 
		{
			if(marcasTemporales.size() > 0) 
			{
				int i = marcasTemporales.size() - 1;
				vista.mapa.removeMapMarker(marcasTemporales.get(i));
				marcasTemporales.remove(i);
				modelo.coordenadas().remove(i);
				
				if(marcasTemporales.size() == 0) 
				{
					vista.botonGuardar.setEnabled(false);
					vista.botonGuardar.setBackground(vista.bordo);
					vista.botonBorrarUltimo.setEnabled(false);
					vista.botonBorrarUltimo.setBackground(vista.bordo);
				}
			}
		}
	}
	
	private void importar() 
	{
		try{
			buscarArchivo();
		}catch (IOException e1){
			e1.printStackTrace();
		}
	}
	
	private void buscarArchivo() throws IOException 
	{
		JFileChooser jf = new JFileChooser();
		jf.showOpenDialog(vista);
		
		if(jf.getSelectedFile() != null) 
		{
			leerArchivo(jf.getSelectedFile());
			colocarPanelControl();
		}
	}
	
	public void leerArchivo(File file) throws IOException
	{	
		String latitud;
		String longitud;
		
		BufferedReader bf = new BufferedReader(new FileReader(file));
		
		String linea;
		
		boolean llegoAlEspacio;
	
		while(!(linea=bf.readLine()).equals(""))
		{
			llegoAlEspacio = false;
			latitud = "";
			longitud = "";
			
			for(int i=0; i < linea.length(); i++) 
			{
				if(i == 0 && linea.charAt(i) == ' ') //Condicion Agregada para el txt 4
					i++;
				
				if(llegoAlEspacio == false) 
				{
					if(linea.charAt(i) != ' ')
						latitud += linea.charAt(i);
					else
						llegoAlEspacio = true;
				}
				else
					longitud += linea.charAt(i);
			}
			
			modelo.agregarCoordenada(new Coordinate(Double.parseDouble(latitud), Double.parseDouble(longitud)));
		}
		
		bf.close();
	}
	
	private void colocarPanelControl() 
	{
		modelo.armarGrafo();
		
		String nombre = JOptionPane.showInputDialog("Nombre: ");
		
		vista.panelDeControles.agregar(new PanelControl(nombre, modelo, vista));
		vista.panelDeControles.updateUI();
		
		modelo = new Modelo();
	}
}
