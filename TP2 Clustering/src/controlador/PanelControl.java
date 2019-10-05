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
import javax.swing.border.MatteBorder;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import modelo.Modelo;
import vista.PanelDeControles;
import vista.Vista;

public class PanelControl extends JPanel implements ActionListener
{
	private Modelo modelo;
	private Vista vista;
	
	private JLabel lblNombre;
	private JTextField txtCantClusters;
	private JCheckBox boxVisibilidad;
	private JButton btnCentrar, btnEliminar, btnClustering;
	
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
		this.setBackground(this.vista.gris3);
		this.setLayout(null);
		
		iniComponentes(nombre);
		dibujarPuntos();
		dibujarAristas();
	}

	private void iniComponentes(String nombre) 
	{	
		this.lblNombre = new JLabel(nombre);
		this.lblNombre.setToolTipText(nombre);
		this.lblNombre.setBounds(28, 7, 100, 23);
		this.lblNombre.setForeground(Color.white);
		this.lblNombre.setFont(new Font("Lucida Sans", Font.BOLD, 13));
		this.lblNombre.setBorder(null);
		this.add(this.lblNombre);
		
		boxVisibilidad = new JCheckBox();
		boxVisibilidad.setBackground(null);
		boxVisibilidad.setForeground(Color.WHITE);
		boxVisibilidad.setBounds(6, 7, 21, 23);
		boxVisibilidad.setToolTipText("Ocultar");
		boxVisibilidad.setSelected(true);
		boxVisibilidad.setFocusable(false);
		this.add(boxVisibilidad);
	
		btnCentrar = new JButton();
		btnCentrar.setIcon(new ImageIcon(PanelControl.class.getResource("/iconos/iconCentrar.png")));
		btnCentrar.setToolTipText("Centrar");
		btnCentrar.setBackground(null);
		btnCentrar.setBounds(160, 7, 29, 23);
		btnCentrar.setFocusable(false);
		btnCentrar.setBorderPainted(false);
		this.add(btnCentrar);
		
		btnEliminar = new JButton();
		btnEliminar.setIcon(new ImageIcon(PanelControl.class.getResource("/iconos/iconCesto.png")));
		btnEliminar.setToolTipText("Eliminar");
		btnEliminar.setBackground(null);
		btnEliminar.setBounds(191, 7, 29, 23);
		btnEliminar.setFocusable(false);
		btnEliminar.setBorderPainted(false);
		this.add(btnEliminar);
		
		txtCantClusters = new JTextField();
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
		txtCantClusters.setBounds(222, 7, 30, 23);
		txtCantClusters.setToolTipText("Max: " + (modelo.getCoordenadas().size()));
		this.add(txtCantClusters);
		
		btnClustering = new JButton();
		btnClustering.setIcon(new ImageIcon(PanelControl.class.getResource("/iconos/iconClustering.png")));
		btnClustering.setToolTipText("Clustering");
		btnClustering.setBackground(null);
		btnClustering.setBounds(253, 7, 29, 23);
		btnClustering.setFocusable(false);
		btnClustering.setBorderPainted(false);
		this.add(btnClustering);
			
		MouseAdapter efectoHover = new MouseAdapter()
		{
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				JButton boton = (JButton) e.getSource();
			
				boton.setBackground(new Color(102, 94, 94));
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
	
		boxVisibilidad.addActionListener(this);
		btnCentrar.addActionListener(this);
		btnEliminar.addActionListener(this);
		btnClustering.addActionListener(this);	
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
			for(int i=0; i < puntos.size(); i++)
				puntos.get(i).setVisible(true);
			
			for(int i=0; i < aristas.size(); i++)
				aristas.get(i).setVisible(true);
			
			boxVisibilidad.setToolTipText("Ocultar");
		}
		else 
		{
			for(int i=0; i < puntos.size(); i++)
				puntos.get(i).setVisible(false);
			
			for(int i=0; i < aristas.size(); i++)
				aristas.get(i).setVisible(false);
			
			boxVisibilidad.setToolTipText("Aparecer");
		}
		
		vista.mapa.updateUI();
	}
	
	//Metodo que elimina el grafo 
	private void eliminar() 
	{
		for(int i=0; i < puntos.size(); i++) 
			vista.mapa.removeMapMarker(puntos.get(i));

		for(int i=0; i < aristas.size(); i++)
			vista.mapa.removeMapPolygon(aristas.get(i));

		vista.panelDeControles.eliminar(this);
		vista.mapa.updateUI();
		vista.panelDeControles.updateUI();
		
		if(vista.panelDeControles.getComponents().length == 0)
		{
			vista.btnExportar.setBackground(vista.bordo);
			vista.btnExportar.setEnabled(false);
		}
	}
	
	//Metodo que posiciona la ubicacion del mapa en un punto del grafo
	private void centrar() 
	{
		vista.mapa.setDisplayPosition(modelo.getCoordenadas().get(0), 13);
	}
	
	private void dibujarPuntos() 
	{
		MapMarkerDot punto;
		for(Coordinate coordenada : modelo.getCoordenadas()) 
		{
			punto = new MapMarkerDot(coordenada);
			punto.setBackColor(colorGrafo);
			puntos.add(punto);
			vista.mapa.addMapMarker(punto);
		}
	}
	
	private void dibujarAristas() 
	{
		Coordinate origen;
		Coordinate destino;
		ArrayList<Coordinate> route;
		MapPolygonImpl poligon;

		for(int i=0; i < modelo.getCoordenadas().size(); i++) 
		{
			origen = modelo.getCoordenadas().get(i);
				
			for(Integer j : modelo.getGrafo().vecinos(i)) /*Recorremos sus vecinos*/
			{						
				destino = modelo.getCoordenadas().get(j);
				route = new ArrayList<Coordinate>(Arrays.asList(origen, destino, destino)); //El poligono requiere  tres coordenadas
				poligon = new MapPolygonImpl(route);
				poligon.setColor(colorGrafo.darker());
			    vista.mapa.addMapPolygon(poligon);                     
			    aristas.add(poligon);		
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
