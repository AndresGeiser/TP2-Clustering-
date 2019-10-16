package controlador;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
	private VentanaPrincipal vPrincipal;
	
	private ArrayList<MapMarkerDot> marcasTemporales;
	private ArrayList<CtrlPanelGrafo> ctrlPanelesGrafos;
	
	public CtrlVentanaPrincipal(Modelo modelo, VentanaPrincipal vPrincipal) 
	{
		this.modelo = modelo;
		this.vPrincipal = vPrincipal;
		marcasTemporales = new ArrayList<>();
		
		ctrlPanelesGrafos = new ArrayList<CtrlPanelGrafo>();
	
		this.vPrincipal.btnImportar.addActionListener(this);
		this.vPrincipal.btnExportar.addActionListener(this);
		this.vPrincipal.btnNuevo.addActionListener(this);
		this.vPrincipal.btnGuardar.addActionListener(this);
		this.vPrincipal.btnCancelar.addActionListener(this);
		this.vPrincipal.btnDeshacer.addActionListener(this);
		iniInteraccionConMapa();
	}
	
	private void iniInteraccionConMapa() 
	{
		this.vPrincipal.mapa.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				if(!vPrincipal.btnNuevo.isEnabled()) 
				{
					if (e.getButton() == MouseEvent.BUTTON1)
					{
						Coordinate coord = (Coordinate) vPrincipal.mapa.getPosition(e.getPoint());
						MapMarkerDot marca = new MapMarkerDot(coord);
						
						if(!estaMarcada(marca))
						{	
							vPrincipal.mapa.addMapMarker(marca);
							marcasTemporales.add(marca);
						}
						
						activar(vPrincipal.btnGuardar);
						activar(vPrincipal.btnDeshacer);
	
					}
				}	
			}
			
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				if(!vPrincipal.btnNuevo.isEnabled())
					vPrincipal.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));		
			}
			
			@Override
			public void mouseExited(MouseEvent e) 
			{
				if(!vPrincipal.btnNuevo.isEnabled())
					vPrincipal.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}

	public void iniciar() 
	{
		vPrincipal.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == vPrincipal.btnImportar )
			buscarArchivos();;
		
		if(e.getSource() == vPrincipal.btnExportar)
			exportar();
		
		if(e.getSource() == vPrincipal.btnNuevo) 
		{
			desactivar(vPrincipal.btnNuevo);
			desactivar(vPrincipal.btnImportar);
			desactivar(vPrincipal.btnExportar);
			activar(vPrincipal.btnCancelar);
		}
		
		if(e.getSource() == vPrincipal.btnCancelar) 
		{
			activar(vPrincipal.btnNuevo);
			activar(vPrincipal.btnImportar);
			desactivar(vPrincipal.btnCancelar);
			desactivar(vPrincipal.btnDeshacer);
			desactivar(vPrincipal.btnGuardar);
			
			if(vPrincipal.panelDeControles.getComponents().length > 0)
				activar(vPrincipal.btnExportar);
			
			borrarMarcasTemporales();
		}
		
		if(e.getSource() == vPrincipal.btnGuardar) 
		{
			String nombre = elegirNombre();
				
			if(nombre != null)
			{
				activar(vPrincipal.btnNuevo);
				activar(vPrincipal.btnImportar);
				activar(vPrincipal.btnExportar);
				desactivar(vPrincipal.btnCancelar);
				desactivar(vPrincipal.btnDeshacer);
				desactivar(vPrincipal.btnGuardar);
					
				for(MapMarkerDot marca : marcasTemporales)
					modelo.agregarCoordenada(marca.getCoordinate());
			
				borrarMarcasTemporales();
				
				colocarPanelGrafo(nombre);
			}
		}
		
		if(e.getSource() == vPrincipal.btnDeshacer)
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
			boolean llegoAlEspacio;
			BufferedReader bf;
			String linea;
			
			for(File archivo : archivos)
			{
				 bf = new BufferedReader(new FileReader(archivo));
			
				while(!(linea = bf.readLine()).equals(""))
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
				
				String nombre = archivo.getName().replace(".txt", "");
				
				colocarPanelGrafo(nombre);
			}
		}
		catch (Exception e)
		{
			if(archivos.length > 1)
				JOptionPane.showMessageDialog(null, "Es posible que alguno de los archivos no fue creado por esta aplicacion", "Error al importar", JOptionPane.ERROR_MESSAGE);	
			else
				JOptionPane.showMessageDialog(null, "Es posible que no sea un archivo creado por esta aplicacion", "Error al importar", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void exportar() 
	{
		verificarGrafosBorrados();
		
		VentanaExportar vExportar = new VentanaExportar(vPrincipal , true);
		
		CtrlVentanaExportar ctrlVentanaExportar = new CtrlVentanaExportar(vExportar, ctrlPanelesGrafos);
		ctrlVentanaExportar.iniciar();
	}
	
	private void colocarPanelGrafo(String nombre) 
	{		
		CtrlPanelGrafo ctrlPanelGrafo = new CtrlPanelGrafo(modelo, vPrincipal, new PanelGrafo(nombre));	
		ctrlPanelGrafo.iniciar();
		
		ctrlPanelesGrafos.add(ctrlPanelGrafo);
		
		activar(vPrincipal.btnExportar);
		
		modelo = new Modelo();
		
	}
	
	private void borrarMarcasTemporales() 
	{
		for(MapMarkerDot marca : marcasTemporales)
			vPrincipal.mapa.removeMapMarker(marca);
		marcasTemporales.clear();
	}
	
	private void borrarUltimaMarca() 
	{
		int i = marcasTemporales.size() - 1;
		vPrincipal.mapa.removeMapMarker(marcasTemporales.get(i));
		marcasTemporales.remove(i);			
		
		if(!hayMarcas())
		{
			desactivar(vPrincipal.btnGuardar);
			desactivar(vPrincipal.btnDeshacer);
		}	
	}
	
	private void verificarGrafosBorrados()//borra los controladores de los grafos que ya han sido borrados 
	{
		for(int i=0; i < ctrlPanelesGrafos.size(); i++)
		{
			if(!existeGrafo(ctrlPanelesGrafos.get(i).getNombre())) 
			{
				ctrlPanelesGrafos.remove(ctrlPanelesGrafos.get(i));
				i--;
			}
		}
	}
	
	private void activar(JButton boton) 
	{
		boton.setEnabled(true);
		boton.setForeground(Color.WHITE);
		boton.setBackground(vPrincipal.rojo);
	}
	
	private void desactivar(JButton boton) 
	{
		boton.setEnabled(false);
		boton.setForeground(Color.GRAY);
	}
	
	private String elegirNombre() 
	{
		String nombre = JOptionPane.showInputDialog("Introduzca un nombre para el grafo: ");
		
		if(nombre != null) 
		{
			while(existeGrafo(nombre) || nombre.equals(""))
			{
				if(nombre.equals(""))
					nombre = JOptionPane.showInputDialog("Por favor, introduzca un nombre para el grafo: ");
				else
					nombre = JOptionPane.showInputDialog("El grafo con el nombre '" + nombre + "' ya existe.\nColoque otro: ");
				
				if(nombre == null)
					break;
			}
		}
		
		return nombre;
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
	
	private boolean existeGrafo(String nombre) 
	{
		Component[] componentes = vPrincipal.panelDeControles.getComponents();
		
		PanelGrafo panelGrafo;
		for(Component componente : componentes ) 
		{
			panelGrafo = (PanelGrafo) componente;
			
			if(panelGrafo.lblNombre.getText().equals(nombre))
				return true;
		}
		return false;
	}
}
