package controlador;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import modelo.Modelo;
import vista.Vista;

public class PanelControl extends JPanel implements ActionListener
{
	private Modelo modelo;
	private Vista vista;
	
	private JLabel lblNombre;
	private JTextField txtCantClusters;
	private JCheckBox boxVisibilidad;
	private JButton btnCentrar, btnEliminar, btnClustering, btnEstadisticas;
	
	private ArrayList<MapMarkerDot> puntos;
	private ArrayList<MapPolygonImpl> aristas;
	
	private Color colorGrafo;
	
	public PanelControl(String nombre, Modelo modelo, Vista vista) 
	{
		this.modelo = modelo;
		this.vista = vista;
		
		puntos = new ArrayList<MapMarkerDot>();
		aristas = new ArrayList<MapPolygonImpl>();
		
		int r = (int)(Math.random()*256);
		int g = (int)(Math.random()*256);
		int b = (int)(Math.random()*256);
		colorGrafo = new Color(r, g, b);
	
		this.setBorder(new MatteBorder(0, 0, 1, 0, Color.WHITE));
		this.setBackground(this.vista.gris2);
		this.setLayout(null);
		
		iniComponentes(nombre);
		dibujarPuntos();
		dibujarAristas();	
	}

	private void iniComponentes(String nombre) 
	{	
		lblNombre = new JLabel(nombre);
		lblNombre.setToolTipText(nombre);
		lblNombre.setBounds(28, 7, 100, 23);
		lblNombre.setForeground(Color.white);
		lblNombre.setFont(new Font("Lucida Sans", Font.BOLD, 13));
		lblNombre.setBorder(null);
		this.add(this.lblNombre);
		
		boxVisibilidad = new JCheckBox();
		boxVisibilidad.setBackground(null);
		boxVisibilidad.setBounds(6, 7, 21, 23);
		boxVisibilidad.setSelected(true);
		boxVisibilidad.setToolTipText("Ocultar");
		this.add(boxVisibilidad);
	
		btnCentrar = new JButton();
		btnCentrar.setIcon(new ImageIcon(PanelControl.class.getResource("/iconos/iconCentrar.png")));
		btnCentrar.setToolTipText("Centrar");
		btnCentrar.setBackground(null);
		btnCentrar.setBounds(156, 7, 29, 23);
		this.add(btnCentrar);
		
		btnEliminar = new JButton();
		btnEliminar.setIcon(new ImageIcon(PanelControl.class.getResource("/iconos/iconCesto.png")));
		btnEliminar.setToolTipText("Eliminar");
		btnEliminar.setBackground(null);
		btnEliminar.setBounds(187, 7, 29, 23);
		this.add(btnEliminar);
		
		btnEstadisticas = new JButton();
		btnEstadisticas.setIcon(new ImageIcon(PanelControl.class.getResource("/iconos/iconEstadisticas.png")));
		btnEstadisticas.setToolTipText("Ver estadisticas");
		btnEstadisticas.setBackground(null);
		btnEstadisticas.setBounds(125, 7, 29, 23);
		this.add(btnEstadisticas);
		
		txtCantClusters = new JTextField();
		txtCantClusters.setBackground(vista.gris2.brighter());
		txtCantClusters.setForeground(Color.WHITE);
		txtCantClusters.setText("1");
		txtCantClusters.addKeyListener(new KeyAdapter() 
		{
			@Override
			public void keyTyped(KeyEvent arg0) 
			{			
				if(txtCantClusters.getText().length() == 3)
				{
					Toolkit.getDefaultToolkit().beep();
					arg0.consume();
				}
				
				if(!Character.isDigit(arg0.getKeyChar()))
					arg0.consume();
			}	
		});
		txtCantClusters.setBounds(218, 7, 33, 23);
		txtCantClusters.setToolTipText("Max: " + (modelo.getCoordenadas().size()));
		this.add(txtCantClusters);
		
		btnClustering = new JButton();
		btnClustering.setIcon(new ImageIcon(PanelControl.class.getResource("/iconos/iconClustering.png")));
		btnClustering.setToolTipText("Clustering");
		btnClustering.setBackground(null);
		btnClustering.setBounds(253, 7, 29, 23);
		this.add(btnClustering);
			
		MouseAdapter efectoHover = new MouseAdapter()
		{
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				JButton boton = (JButton) e.getSource();
			
				boton.setBackground(vista.gris2.brighter());
			}
		
			@Override
			public void mouseExited(MouseEvent e) 
			{
				JButton boton = (JButton) e.getSource();
				
				boton.setBackground(null);
			}
		};
		btnCentrar.addMouseListener(efectoHover);
		btnEliminar.addMouseListener(efectoHover);
		btnClustering.addMouseListener(efectoHover);
		btnEstadisticas.addMouseListener(efectoHover);
		
	
		boxVisibilidad.addActionListener(this);
		btnCentrar.addActionListener(this);
		btnEliminar.addActionListener(this);
		btnClustering.addActionListener(this);	
		btnEstadisticas.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == boxVisibilidad)
			activarODesactivar();
		
		if(e.getSource() == btnCentrar)
			centrar();
		
		if(e.getSource() == btnEliminar)
			eliminar();
		
		if(e.getSource() == btnClustering)
			clustering();
		
		if(e.getSource() == btnEstadisticas)
			verEstadisticas();
		
	}

	private void clustering() 
	{
		if (!txtCantClusters.getText().equals(""))
		{
			if(Integer.parseInt(txtCantClusters.getText()) > modelo.getCoordenadas().size())
				JOptionPane.showMessageDialog(null, "Excediste el maximo de clusters.", "Error", JOptionPane.WARNING_MESSAGE);
			
			else if(Integer.parseInt(txtCantClusters.getText()) == 0)
				JOptionPane.showMessageDialog(null, "No puede haber 0 clusters.", "Error", JOptionPane.WARNING_MESSAGE);
			
			else
			{
				modelo.clustering(Integer.parseInt(txtCantClusters.getText()));
				
				for(MapPolygonImpl poligono : aristas) 
					vista.mapa.removeMapPolygon(poligono);
				aristas.clear();
				
				dibujarAristas();
			}
		}
	}
	
	//Metodo que desactiva o activa la visibilidad del grafo en el mapa
	private void activarODesactivar() 
	{
		if(boxVisibilidad.isSelected() == true)
		{
			for(MapMarkerDot punto : puntos)
				punto.setVisible(true);
			
			for(MapPolygonImpl arista : aristas)
				arista.setVisible(true);
			
			boxVisibilidad.setToolTipText("Ocultar");
		}
		else 
		{
			for(MapMarkerDot punto : puntos)
				punto.setVisible(false);
			
			for(MapPolygonImpl arista : aristas)
				arista.setVisible(false);
			
			boxVisibilidad.setToolTipText("Aparecer");
		}
		
		vista.mapa.updateUI();
	}
	
	//Metodo que elimina el grafo 
	private void eliminar() 
	{
		for(MapMarkerDot punto : puntos)
			vista.mapa.removeMapMarker(punto);
		
		for(MapPolygonImpl arista : aristas) 
			vista.mapa.removeMapPolygon(arista);

		vista.panelDeControles.eliminar(this);
		vista.mapa.updateUI();
		vista.panelDeControles.updateUI();
		
		//Desactivamos el boton exportar en caso de eliminar todos los grafos
		if(vista.panelDeControles.getComponents().length == 0)
		{
			vista.btnExportar.setEnabled(false);
			vista.btnExportar.setForeground(Color.GRAY);
		}
	}
	
	//Metodo que posiciona la ubicacion del mapa en un punto del grafo
	private void centrar() 
	{
		vista.mapa.setDisplayPosition(modelo.getCoordenadas().get(0), 13);
	}
	
	private void verEstadisticas()
	{
		StringBuilder stats = new StringBuilder("");
		stats.append("Cantidad de vertices: " + modelo.cantVertices() + "\n");
		stats.append("Cantidad de Clusters: " + txtCantClusters.getText() + "\n");
		stats.append("Peso total de aristas: " + modelo.getPesoTotal() + "\n");
		stats.append("Desviacion Estandar ~ " + modelo.getDesviacionEstandar());
		
		
		JOptionPane.showMessageDialog(null, stats.toString(), "Estadisticas", JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void dibujarPuntos() 
	{
		MapMarkerDot punto;
		for(Coordinate coordenada : modelo.getCoordenadas()) 
		{
			punto = new MapMarkerDot(coordenada);
			punto.setBackColor(colorGrafo);
			vista.mapa.addMapMarker(punto);
			puntos.add(punto);
		}
	}
	
	private void dibujarAristas() 
	{
		Coordinate origen;
		Coordinate destino;
		MapPolygonImpl poligono;

		for(int i=0; i < modelo.getCoordenadas().size(); i++) 
		{
			origen = modelo.getCoordenadas().get(i);
				
			for(Integer j : modelo.getGrafo().vecinos(i)) /*Recorremos sus vecinos*/
			{						
				destino = modelo.getCoordenadas().get(j);
				poligono = new MapPolygonImpl(Arrays.asList(origen, destino, destino));//El poligono necesita 3 puntos para dibujarse
				poligono.setColor(colorGrafo.darker());
			    vista.mapa.addMapPolygon(poligono);                     
			    aristas.add(poligono);		
			}
		}
		
	}
	
	public String getNombre() 
	{
		return lblNombre.getText();
	}
	
	public ArrayList<Coordinate> getCoordenadas()
	{
		return modelo.getCoordenadas();
	}
	
}
