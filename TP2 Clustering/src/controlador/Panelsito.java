package controlador;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;

import modelo.Modelo;
import vista.Vista;

public class Panelsito extends JPanel implements ActionListener
{
	private Modelo modelo;
	private Vista vista;
	
	private JCheckBox grafoCheckBox;
	private JButton centrarGrafo;
	private JButton eliminarGrafo;
	private JTextField clusters;
	private JButton clustering;
	
	private MapPolygonImpl poligono;
	private ArrayList<MapMarkerDot> coordenadas;
	
	public Panelsito(String nombre, Modelo modelo, Vista vista, int y) 
	{
		iniComponentes(nombre, y);
		
		this.modelo = modelo;
		this.vista = vista;
		
		dibujarse();
	}

	private void iniComponentes(String nombre, int y) 
	{
		this.setBounds(0, y, 257, 37);
		this.setBackground( new Color(26, 82, 118));
		this.setLayout(null);
		
		grafoCheckBox = new JCheckBox(nombre);
		grafoCheckBox.setBackground(new Color(26, 82, 118));
		grafoCheckBox.setForeground(Color.WHITE);
		grafoCheckBox.setBounds(6, 7, 97, 23);
		grafoCheckBox.setToolTipText(nombre);
		grafoCheckBox.setSelected(true);
		grafoCheckBox.setFocusable(false);
		this.add(grafoCheckBox);
		
		centrarGrafo = new JButton();
		centrarGrafo.setIcon(new ImageIcon(Panelsito.class.getResource("/iconos/iconCentrar.png")));
		centrarGrafo.setToolTipText("Centrar");
		centrarGrafo.setBackground(new Color(20, 143, 119));
		centrarGrafo.setBounds(109, 7, 39, 23);
		centrarGrafo.setFocusable(false);
		this.add(centrarGrafo);
		
	
		eliminarGrafo = new JButton();
		eliminarGrafo.setIcon(new ImageIcon(Panelsito.class.getResource("/iconos/iconCesto.png")));
		eliminarGrafo.setToolTipText("Eliminar");
		eliminarGrafo.setBackground(new Color(20, 143, 119));
		eliminarGrafo.setBounds(149, 7, 29, 23);
		eliminarGrafo.setFocusable(false);
		this.add(eliminarGrafo);
		
		clusters = new JTextField();
		clusters.setBounds(179, 7, 38, 23);
		this.add(clusters);
		
		clustering = new JButton();
		clustering.setIcon(new ImageIcon(Panelsito.class.getResource("/iconos/iconClustering.png")));
		clustering.setToolTipText("clustering");
		clustering.setBackground(new Color(20, 143, 119));
		clustering.setBounds(218, 7, 29, 23);
		clustering.setFocusable(false);
		this.add(clustering);
		
		grafoCheckBox.addActionListener(this);
		centrarGrafo.addActionListener(this);
		eliminarGrafo.addActionListener(this);
		clustering.addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == grafoCheckBox)
			activarODesactivar();
		
		if(e.getSource() == centrarGrafo)
			centrarGrafo();
		
		if(e.getSource() == eliminarGrafo)
			eliminarGrafo();
			
		
//		if(e.getSource() == clustering) POR AHORA ESTE NO HACE NADA
		
	}
	
	private void activarODesactivar() 
	{
		if(grafoCheckBox.isSelected() == true)
		{
			for(int i=0; i < coordenadas.size(); i++)
				coordenadas.get(i).setVisible(true);
			
			poligono.setVisible(true);
		}
		else 
		{
			for(int i=0; i < coordenadas.size(); i++)
				coordenadas.get(i).setVisible(false);
			
			poligono.setVisible(false);

	
		}
		
		vista.mapa.updateUI();
	}
	
	private void eliminarGrafo() 
	{
		for(int i=0; i < coordenadas.size(); i++) 
			vista.mapa.removeMapMarker(coordenadas.get(i));
		
		vista.mapa.removeMapPolygon(poligono);
		vista.mapa.updateUI();
		vista.panelGrafos.remove(this);
		vista.panelGrafos.updateUI();
	}
	
	private void centrarGrafo() 
	{
		
	}
	
	private void dibujarse() 
	{
		int R = (int)(Math.random()*256);
		int G = (int)(Math.random()*256);
		int B= (int)(Math.random()*256);
		Color color = new Color(R, G, B);
		
		MapMarkerDot coordenada;
		coordenadas = new ArrayList<MapMarkerDot>();
		
		for(Coordinate coor : modelo.coordenadas()) 
		{
			coordenada = new MapMarkerDot(coor);
			coordenada.setBackColor(color);
			coordenadas.add(coordenada);
			vista.mapa.addMapMarker(coordenada);
		}
		
		poligono = new MapPolygonImpl(modelo.coordenadas());
		poligono.setColor(color);
		vista.mapa.addMapPolygon(poligono);
		
	}
	

}
