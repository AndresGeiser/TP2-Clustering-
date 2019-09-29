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

public class PanelControl extends JPanel implements ActionListener
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
	
	public PanelControl(String nombre, Modelo modelo, Vista vista) 
	{
		this.modelo = modelo;
		this.vista = vista;
		iniComponentes(nombre);
		dibujarse();
	}

	private void iniComponentes(String nombre) 
	{
		this.setBackground(vista.rojo2);
		this.setLayout(null);
		
		grafoCheckBox = new JCheckBox(nombre);
		grafoCheckBox.setBackground(vista.rojo2);
		grafoCheckBox.setForeground(Color.WHITE);
		grafoCheckBox.setBounds(6, 7, 97, 23);
		grafoCheckBox.setToolTipText(nombre);
		grafoCheckBox.setSelected(true);
		grafoCheckBox.setFocusable(false);
		this.add(grafoCheckBox);
		
		centrarGrafo = new JButton();
		centrarGrafo.setIcon(new ImageIcon(PanelControl.class.getResource("/iconos/iconCentrar.png")));
		centrarGrafo.setToolTipText("Centrar");
		centrarGrafo.setBackground(vista.bordo);
		centrarGrafo.setBounds(109, 7, 39, 23);
		centrarGrafo.setFocusable(false);
		centrarGrafo.setBorderPainted(false);
		vista.efectoHoverBoton(centrarGrafo, vista.rojo3, vista.bordo);
		this.add(centrarGrafo);
		
	
		eliminarGrafo = new JButton();
		eliminarGrafo.setIcon(new ImageIcon(PanelControl.class.getResource("/iconos/iconCesto.png")));
		eliminarGrafo.setToolTipText("Eliminar");
		eliminarGrafo.setBackground(vista.bordo);
		eliminarGrafo.setBounds(149, 7, 29, 23);
		eliminarGrafo.setFocusable(false);
		eliminarGrafo.setBorderPainted(false);
		vista.efectoHoverBoton(eliminarGrafo, vista.rojo3, vista.bordo);
		this.add(eliminarGrafo);
		
		clusters = new JTextField();
		clusters.setBounds(179, 7, 38, 23);
		this.add(clusters);
		
		clustering = new JButton();
		clustering.setIcon(new ImageIcon(PanelControl.class.getResource("/iconos/iconClustering.png")));
		clustering.setToolTipText("Clustering");
		clustering.setBackground(vista.bordo);
		clustering.setBounds(218, 7, 29, 23);
		clustering.setFocusable(false);
		clustering.setBorderPainted(false);
		vista.efectoHoverBoton(clustering, vista.rojo3, vista.bordo);
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
		vista.panelDeControles.eliminar(this);
		vista.panelDeControles.updateUI();
	}
	
	private void centrarGrafo() 
	{
		vista.mapa.setDisplayPosition(modelo.coordenadas().get(0), 13);
	}
	
	private void dibujarse() 
	{
		int R = (int)(Math.random()*256);
		int G = (int)(Math.random()*256);
		int B= (int)(Math.random()*256);
		Color color = new Color(R, G, B);
		
		coordenadas = new ArrayList<MapMarkerDot>();
		
		MapMarkerDot coordenada;
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
		
		vista.mapa.setDisplayPosition(modelo.coordenadas().get(0), 13);
		
	}
	
	public String nombre() 
	{
		return grafoCheckBox.getName();
	}
}
