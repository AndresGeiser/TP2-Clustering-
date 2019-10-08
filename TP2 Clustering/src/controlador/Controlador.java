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
import javax.swing.filechooser.FileNameExtensionFilter;

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
						
						if(!estaMarcada(marca))
						{	
							vista.mapa.addMapMarker(marca);
							marcasTemporales.add(marca);
						}
						
						if(marcasTemporales.size() > 0)
						{
							//Activamos boton guardar y deshacer
							vista.btnGuardar.setEnabled(true);
							vista.btnGuardar.setBackground(vista.rojo);
							vista.btnDeshacer.setEnabled(true);
							vista.btnDeshacer.setBackground(vista.rojo);
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
			buscarArchivo();;
		
		if(e.getSource() == vista.btnExportar)
			exportar();
		
		if(e.getSource() == vista.btnNuevo) 
		{
			vista.btnNuevo.setEnabled(false);
			vista.btnNuevo.setBackground(vista.bordo);
			vista.btnImportar.setEnabled(false);
			vista.btnImportar.setBackground(vista.bordo);
			vista.btnExportar.setEnabled(false);
			vista.btnExportar.setBackground(vista.bordo);
			vista.btnCancelar.setEnabled(true);
			vista.btnCancelar.setBackground(vista.rojo);
		}
		
		if(e.getSource() == vista.btnCancelar) 
		{
			vista.btnNuevo.setEnabled(true);
			vista.btnNuevo.setBackground(vista.rojo);
			vista.btnImportar.setEnabled(true);
			vista.btnImportar.setBackground(vista.rojo);
			vista.btnCancelar.setEnabled(false);
			vista.btnCancelar.setBackground(vista.bordo);
			vista.btnDeshacer.setEnabled(false);
			vista.btnDeshacer.setBackground(vista.bordo);
			vista.btnGuardar.setEnabled(false);
			vista.btnGuardar.setBackground(vista.bordo);
			
			if(vista.panelDeControles.getComponents().length > 0)
			{
				vista.btnExportar.setEnabled(true);
				vista.btnExportar.setBackground(vista.rojo);
			}
			
			borrarMarcasTemporales();
		}
		
		if(e.getSource() == vista.btnGuardar) 
		{
			String nombre = JOptionPane.showInputDialog("Nombre: ");
				
			if(nombre != null) //Chequeamos si acepto
			{
				vista.btnNuevo.setEnabled(true);
				vista.btnNuevo.setBackground(vista.rojo);
				vista.btnImportar.setEnabled(true);
				vista.btnImportar.setBackground(vista.rojo);
				vista.btnCancelar.setEnabled(false);
				vista.btnCancelar.setBackground(vista.bordo);
				vista.btnGuardar.setEnabled(false);
				vista.btnGuardar.setBackground(vista.bordo);
				vista.btnDeshacer.setEnabled(false);
				vista.btnDeshacer.setBackground(vista.bordo);
				
				for(MapMarkerDot marca : marcasTemporales)
					modelo.agregarCoordenada(marca.getCoordinate());
			
				borrarMarcasTemporales();
				
				if(nombre.equals(""))
					nombre = "Mi grafo " + vista.panelDeControles.getComponents().length;
				
				colocarPanelControl(nombre);
			}
		}
		
		if(e.getSource() == vista.btnDeshacer) 
		{
			borrarUltimaMarca();
				
			if(!hayMarcas())
			{
				vista.btnGuardar.setEnabled(false);
				vista.btnGuardar.setBackground(vista.bordo);
				vista.btnDeshacer.setEnabled(false);
				vista.btnDeshacer.setBackground(vista.bordo);
			}	
		}	
	}
	
	private void buscarArchivo() 
	{
		JFileChooser jf = new JFileChooser();
		FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.TXT", "txt");

		jf.setFileFilter(filtro);
		
		if(jf.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) 
		{	
			leerArchivo(jf.getSelectedFile());	
			
			String nombreArchivo = jf.getSelectedFile().getName();
			String nombre = nombreArchivo.substring(0, nombreArchivo.length() - 4); 
	
			colocarPanelControl(nombre);	
		}
	}
	
	private void leerArchivo(File file)
	{	
		try 
		{
			String latitud;
			String longitud;	
			String linea;
			boolean llegoAlEspacio;
			
			BufferedReader bf = new BufferedReader(new FileReader(file));
		
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
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
	}
	
	private void exportar() 
	{
		VentanaExportar ventana = new VentanaExportar(vista, true);
		ventana.setVisible(true);
	}
	
	private void colocarPanelControl(String nombre) 
	{
		modelo.armarGrafo();
		
		vista.panelDeControles.agregar(new PanelControl(nombre, modelo, vista));
		vista.panelDeControles.updateUI();
		
		vista.btnExportar.setBackground(vista.rojo);
		vista.btnExportar.setEnabled(true);
		
		modelo = new Modelo();
	}
	
	private void borrarMarcasTemporales() 
	{
		for(MapMarkerDot marca : marcasTemporales)
			vista.mapa.removeMapMarker(marca);
		marcasTemporales.clear();
	}
	
	private void borrarUltimaMarca() 
	{
		if(hayMarcas()) 
		{
			int i = marcasTemporales.size() - 1;
			vista.mapa.removeMapMarker(marcasTemporales.get(i));
			marcasTemporales.remove(i);			
		}
	}
	
	//METODOS AUXILIARES
	private boolean estaMarcada(MapMarkerDot coordenada) 
	{
		for(MapMarkerDot marca : marcasTemporales)
			if(coordenada.getCoordinate().equals(marca.getCoordinate()))
				return true;
		return false;
	}
	
	private boolean hayMarcas() 
	{
		if(marcasTemporales.size() > 0) 
			return true;
		return false;
	}
}
