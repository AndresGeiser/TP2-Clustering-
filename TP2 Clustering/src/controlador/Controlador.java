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

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
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
							activarBoton(vista.btnGuardar, true);
							activarBoton(vista.btnDeshacer, true);
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
			activarBoton(vista.btnNuevo, false);
			activarBoton(vista.btnImportar, false);
			activarBoton(vista.btnExportar, false);
			activarBoton(vista.btnCancelar, true);
		}
		
		if(e.getSource() == vista.btnCancelar) 
		{
			activarBoton(vista.btnNuevo, true);
			activarBoton(vista.btnImportar, true);
			activarBoton(vista.btnCancelar, false);
			activarBoton(vista.btnDeshacer, false);
			activarBoton(vista.btnGuardar, false);
			
			if(vista.panelDeControles.getComponents().length > 0)
				activarBoton(vista.btnExportar, true);
			
			borrarMarcasTemporales();
		}
		
		if(e.getSource() == vista.btnGuardar) 
		{
			String nombre = JOptionPane.showInputDialog("Nombre: ");
				
			if(nombre != null) //Chequeamos si acepto
			{
				activarBoton(vista.btnNuevo, true);
				activarBoton(vista.btnImportar, true);
				activarBoton(vista.btnCancelar, false);
				activarBoton(vista.btnGuardar, false);
				activarBoton(vista.btnDeshacer, false);		
				
				for(MapMarkerDot marca : marcasTemporales)
					modelo.agregarCoordenada(marca.getCoordinate());
			
				borrarMarcasTemporales();
				
				if(nombre.equals(""))
					nombre = "Mi grafo " + vista.panelDeControles.getComponents().length;
				
				colocarPanelControl(nombre);
			}
		}
		
		if(e.getSource() == vista.btnDeshacer)
			borrarUltimaMarca();
				
			
	}
	
	private void buscarArchivo() 
	{
		
		JFileChooser jf = new JFileChooser();
		jf.setMultiSelectionEnabled(true);
		FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.TXT", "txt");
		jf.setFileFilter(filtro);
		
		if(jf.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			leerArchivo(jf.getSelectedFiles());		
		
	}
	
	private void leerArchivo(File[] archivos)
	{	
		try 
		{
			String latitud;
			String longitud;	
			String linea;
			boolean llegoAlEspacio;
			BufferedReader bf;
			
			for(File archivo : archivos)
			{
				 bf = new BufferedReader(new FileReader(archivo));
			
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
				
				String nombreArchivo = archivo.getName();
				String nombre = nombreArchivo.substring(0, nombreArchivo.length() - 4); 
		
				colocarPanelControl(nombre);
			}
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
		
		activarBoton(vista.btnExportar, true);
		
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
		
			if(!hayMarcas())
			{
				activarBoton(vista.btnGuardar, false);
				activarBoton(vista.btnDeshacer, false);
			}
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
	
	private void activarBoton(JButton boton , boolean b) 
	{
		if(b == true) 
		{
			boton.setEnabled(true);
			boton.setForeground(Color.WHITE);
			boton.setBackground(vista.rojo);
		}
		else 
		{
			boton.setEnabled(false);
			boton.setForeground(Color.GRAY);
		}
	}
}
