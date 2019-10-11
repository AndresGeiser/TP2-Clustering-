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
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import modelo.Modelo;
import vista.PanelGrafo;
import vista.VentanaExportar;
import vista.VentanaPrincipal;

public class CtrlVentanaPrincipal implements ActionListener
{
	private Modelo modelo;
	private VentanaPrincipal vista;
	
	private ArrayList<MapMarkerDot> marcasTemporales;
	private ArrayList<CtrlPanelGrafo> ctrlPanelesGrafos;
	
	public CtrlVentanaPrincipal(Modelo modelo, VentanaPrincipal vista) 
	{
		this.modelo = modelo;
		this.vista = vista;
		marcasTemporales = new ArrayList<>();
		
		ctrlPanelesGrafos = new ArrayList<CtrlPanelGrafo>();
	
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
						
						setBoton(vista.btnGuardar, true);
						setBoton(vista.btnDeshacer, true);
	
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
			buscarArchivos();;
		
		if(e.getSource() == vista.btnExportar)
			exportar();
		
		if(e.getSource() == vista.btnNuevo) 
		{
			setBoton(vista.btnNuevo, false);
			setBoton(vista.btnImportar, false);
			setBoton(vista.btnExportar, false);
			setBoton(vista.btnCancelar, true);
		}
		
		if(e.getSource() == vista.btnCancelar) 
		{
			setBoton(vista.btnNuevo, true);
			setBoton(vista.btnImportar, true);
			setBoton(vista.btnCancelar, false);
			setBoton(vista.btnDeshacer, false);
			setBoton(vista.btnGuardar, false);
			
			if(vista.panelDeControles.getComponents().length > 0)
				setBoton(vista.btnExportar, true);
			
			borrarMarcasTemporales();
		}
		
		if(e.getSource() == vista.btnGuardar) 
		{
			String nombre = JOptionPane.showInputDialog("Nombre: ");
				
			if(nombre != null) //Chequeamos si acepto
			{
				setBoton(vista.btnNuevo, true);
				setBoton(vista.btnImportar, true);
				setBoton(vista.btnCancelar, false);
				setBoton(vista.btnGuardar, false);
				setBoton(vista.btnDeshacer, false);		
				
				for(MapMarkerDot marca : marcasTemporales)
					modelo.agregarCoordenada(marca.getCoordinate());
			
				borrarMarcasTemporales();
				
				if(nombre.equals(""))
					nombre = "Mi grafo " + vista.panelDeControles.getComponents().length;
				
				colocarPanelGrafo(nombre);
			}
		}
		
		if(e.getSource() == vista.btnDeshacer)
			borrarUltimaMarca();
				
			
	}
	
	private void buscarArchivos() 
	{
		JFileChooser jf = new JFileChooser();
		jf.setMultiSelectionEnabled(true);
		jf.setFileFilter(new FileNameExtensionFilter("*.TXT", "txt"));
		
		if(jf.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			leerArchivos(jf.getSelectedFiles());		
		
	}
	
	private void leerArchivos(File[] archivos)
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
				
				String nombre = archivo.getName().replace(".txt", "");
				
				colocarPanelGrafo(nombre);
			}
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
	
	private void exportar() 
	{
		VentanaExportar vExportar = new VentanaExportar(vista , true);
		
		CtrlVentanaExportar ctrlVentanaExportar = new CtrlVentanaExportar(vExportar, ctrlPanelesGrafos);
		ctrlVentanaExportar.iniciar();
	}
	
	private void colocarPanelGrafo(String nombre) 
	{		
		PanelGrafo panelGrafo = new PanelGrafo(nombre);
		
		CtrlPanelGrafo ctrlPanelGrafo = new CtrlPanelGrafo(modelo, vista, panelGrafo);	
		ctrlPanelGrafo.iniciar();
		
		ctrlPanelesGrafos.add(ctrlPanelGrafo);
		
		setBoton(vista.btnExportar, true);
		
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
				setBoton(vista.btnGuardar, false);
				setBoton(vista.btnDeshacer, false);
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
	
	
	private void setBoton(JButton boton , boolean b)//Activa o desactiva el boton 
	{
		boton.setEnabled(b);
		
		if(b == true) 
		{
			boton.setForeground(Color.WHITE);
			boton.setBackground(vista.rojo);
		}
		
		else
			boton.setForeground(Color.GRAY);
		
	}
}
