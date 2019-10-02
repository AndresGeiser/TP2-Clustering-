package controlador;


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
	
		this.vista.btnImportar.addActionListener(this);
		this.vista.btnExportar.addActionListener(this);
		this.vista.btnNuevo.addActionListener(this);
		this.vista.btnGuardar.addActionListener(this);
		this.vista.btnCancelar.addActionListener(this);
		this.vista.btnDeshacer.addActionListener(this);
		iniInteraccionConMapa();
	}
	
	private void iniInteraccionConMapa() 
	{
		this.vista.mapa.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				if(!vista.btnNuevo.isEnabled()) 
				{
					if (e.getButton() == MouseEvent.BUTTON1)
					{
						Coordinate coord = (Coordinate) vista.mapa.getPosition(e.getPoint());
						MapMarkerDot marca = new MapMarkerDot(coord);
						
						if(!esta(marca))
						{	
							vista.mapa.addMapMarker(marca);
							marcasTemporales.add(marca);
						}
						
						if(marcasTemporales.size() > 0)
						{
							vista.btnGuardar.setEnabled(true);
							vista.btnGuardar.setBackground(vista.rojo1);
							vista.btnDeshacer.setEnabled(true);
							vista.btnDeshacer.setBackground(vista.rojo1);
						}
					}
				}	
			}
			
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				if(!vista.btnNuevo.isEnabled())
					vista.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));		
			}
			
			@Override
			public void mouseExited(MouseEvent e) 
			{
				if(!vista.btnNuevo.isEnabled())
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
		if(e.getSource() == vista.btnImportar )
			importar();
			
		if(e.getSource() == vista.btnNuevo) 
		{
			vista.btnNuevo.setEnabled(false);
			vista.btnNuevo.setBackground(vista.gris3);
			vista.btnImportar.setEnabled(false);
			vista.btnImportar.setBackground(vista.bordo);
			vista.btnExportar.setEnabled(false);
			vista.btnExportar.setBackground(vista.bordo);
			vista.btnCancelar.setEnabled(true);
			vista.btnCancelar.setBackground(vista.rojo1);
		}
		
		if(e.getSource() == vista.btnCancelar) 
		{
			vista.btnNuevo.setEnabled(true);
			vista.btnNuevo.setBackground(vista.rojo1);
			vista.btnImportar.setEnabled(true);
			vista.btnImportar.setBackground(vista.rojo1);
			vista.btnExportar.setEnabled(true);
			vista.btnExportar.setBackground(vista.rojo1);
			vista.btnCancelar.setEnabled(false);
			vista.btnCancelar.setBackground(vista.bordo);
			vista.btnDeshacer.setEnabled(false);
			vista.btnDeshacer.setBackground(vista.bordo);
			vista.btnGuardar.setEnabled(false);
			vista.btnGuardar.setBackground(vista.bordo);
			
			while(marcasTemporales.size() > 0) 
			{
				vista.mapa.removeMapMarker(marcasTemporales.get(0));
				marcasTemporales.remove(0);
			}
		}
		
		if(e.getSource() == vista.btnGuardar) 
		{
			for(int i=0; i < marcasTemporales.size(); i++) 
			{
				vista.mapa.removeMapMarker(marcasTemporales.get(i));
				modelo.agregarCoordenada(marcasTemporales.get(i).getCoordinate());
			}
			marcasTemporales.clear();
				
			vista.btnNuevo.setEnabled(true);
			vista.btnNuevo.setBackground(vista.rojo1);
			vista.btnImportar.setEnabled(true);
			vista.btnImportar.setBackground(vista.rojo1);
			vista.btnExportar.setEnabled(true);
			vista.btnExportar.setBackground(vista.rojo1);
			vista.btnCancelar.setEnabled(false);
			vista.btnCancelar.setBackground(vista.bordo);
			vista.btnGuardar.setEnabled(false);
			vista.btnGuardar.setBackground(vista.bordo);
			vista.btnDeshacer.setEnabled(false);
			vista.btnDeshacer.setBackground(vista.bordo);
			
			colocarPanelControl();
		}
		
		if(e.getSource() == vista.btnDeshacer) 
		{
			if(marcasTemporales.size() > 0) 
			{
				int i = marcasTemporales.size() - 1;
				vista.mapa.removeMapMarker(marcasTemporales.get(i));
				marcasTemporales.remove(i);
				
				if(marcasTemporales.size() == 0) 
				{
					vista.btnGuardar.setEnabled(false);
					vista.btnGuardar.setBackground(vista.bordo);
					vista.btnDeshacer.setEnabled(false);
					vista.btnDeshacer.setBackground(vista.bordo);
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
	
	private void leerArchivo(File file) throws IOException
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
	
	private boolean esta(MapMarkerDot coordenada) 
	{
		for(MapMarkerDot marca : marcasTemporales)
			if(coordenada.getCoordinate().equals(marca.getCoordinate()))
				return true;
		return false;
	}
}
