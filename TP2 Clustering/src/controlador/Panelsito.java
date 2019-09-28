package controlador;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
	
	public Panelsito(String nombre, Modelo modelo, Vista vista) 
	{
		iniComponentes(nombre);
		
		this.modelo = modelo;
		this.vista = vista;
		
		dibujarse();
		
	}

	private void iniComponentes(String nombre) 
	{
		this.setBounds(0, 0, 257, 37);
		this.setLayout(null);

		grafoCheckBox = new JCheckBox(nombre);
		grafoCheckBox.setBounds(6, 7, 97, 23);
		grafoCheckBox.setToolTipText(nombre);
		grafoCheckBox.setSelected(true);
		this.add(grafoCheckBox);
		
		centrarGrafo = new JButton();
		centrarGrafo.setToolTipText("Centrar");
		centrarGrafo.setFont(new Font("Tahoma", Font.PLAIN, 7));
		centrarGrafo.setBounds(109, 7, 39, 23);
		this.add(centrarGrafo);
		
		eliminarGrafo = new JButton();
		eliminarGrafo.setToolTipText("Eliminar");
		eliminarGrafo.setBounds(149, 7, 29, 23);
		this.add(eliminarGrafo);
		
		clusters = new JTextField();
		clusters.setBounds(179, 8, 38, 20);
		this.add(clusters);
		
		clustering = new JButton("Clustering");
		clustering.setBounds(218, 7, 29, 23);
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
		MapMarkerDot coordenada;
		coordenadas = new ArrayList<MapMarkerDot>();
		
		for(Coordinate coor : modelo.coordenadas()) 
		{
			coordenada = new MapMarkerDot(coor);
			
			coordenadas.add(coordenada);
			vista.mapa.addMapMarker(coordenada);
		}
		
		poligono = new MapPolygonImpl(modelo.coordenadas());
		vista.mapa.addMapPolygon(poligono);
		
	}
	

}
