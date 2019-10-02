package controlador;

import java.awt.Color;
import java.awt.Font;
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
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;

import modelo.Grafo;
import modelo.Modelo;
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
	private ArrayList<MapPolygonImpl> poligonos;
	
	private Color colorGrafo;
	
	public PanelControl(String nombre, Modelo modelo, Vista vista) 
	{
		this.modelo = modelo;
		this.vista = vista;
		
		puntos = new ArrayList<MapMarkerDot>();
		for(Coordinate coord : modelo.getCoordenadas())
			puntos.add(new MapMarkerDot(coord));
		
		poligonos = new ArrayList<MapPolygonImpl>();
		
		int r = (int)(Math.random()*256);
		int g = (int)(Math.random()*256);
		int b = (int)(Math.random()*256);
		colorGrafo = new Color(r, g, b);
	
		this.setBorder(new MatteBorder(0, 0, 1, 0, Color.WHITE));
		this.setBackground(this.vista.gris3);
		this.setLayout(null);
		
		iniComponentes(nombre);
		dibujarPuntos();
		dibujarGrafo();
	}

	private void iniComponentes(String nombre) 
	{	
		this.lblNombre = new JLabel(nombre);
		this.lblNombre.setToolTipText(nombre);
		this.lblNombre.setBounds(28, 7, 100, 23);
		this.lblNombre.setForeground(Color.white);
		this.lblNombre.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		this.lblNombre.setBorder(null);
		this.add(this.lblNombre);
		
		boxVisibilidad = new JCheckBox();
		boxVisibilidad.setBackground(null);
		boxVisibilidad.setForeground(Color.WHITE);
		boxVisibilidad.setBounds(6, 7, 21, 23);
		boxVisibilidad.setToolTipText(nombre);
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
		txtCantClusters.addKeyListener(new KeyAdapter() 
		{
			@Override
			public void keyTyped(KeyEvent arg0) 
			{
				if(txtCantClusters.getText().length() == 3)
					arg0.consume();
				
				if(!Character.isDigit(arg0.getKeyChar()))
					arg0.consume();
			}
		});
		txtCantClusters.setBounds(222, 7, 30, 23);
		txtCantClusters.setToolTipText("Max: " + modelo.getCoordenadas().size());
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
		{	
			modelo.clustering(0);
			for(MapPolygonImpl poligono : poligonos)
				vista.mapa.removeMapPolygon(poligono);
			poligonos.clear();
			dibujarGrafo();
		}
	}
	
	private void activarODesactivar() 
	{
		if(boxVisibilidad.isSelected() == true)
		{
			for(int i=0; i < puntos.size(); i++)
				puntos.get(i).setVisible(true);
			
			for(int i=0; i < poligonos.size(); i++)
				poligonos.get(i).setVisible(true);
		}
		else 
		{
			for(int i=0; i < puntos.size(); i++)
				puntos.get(i).setVisible(false);
			
			for(int i=0; i < poligonos.size(); i++)
				poligonos.get(i).setVisible(false);
		}
		
		vista.mapa.updateUI();
	}
	
	private void eliminar() 
	{
		for(int i=0; i < puntos.size(); i++) 
			vista.mapa.removeMapMarker(puntos.get(i));

		for(int i=0; i < poligonos.size(); i++)
			vista.mapa.removeMapPolygon(poligonos.get(i));

		vista.mapa.updateUI();
		vista.panelDeControles.eliminar(this);
		vista.panelDeControles.updateUI();
	}
	
	private void centrar() 
	{
		vista.mapa.setDisplayPosition(modelo.getCoordenadas().get(0), 13);
	}
	
	private void dibujarPuntos() 
	{
		for(MapMarkerDot punto : puntos) 
		{
			punto.setBackColor(colorGrafo);
			vista.mapa.addMapMarker(punto);
		}
	}
	
	
	public void dibujarGrafo() 
	{
		Coordinate origen;
		Coordinate destino;
		ArrayList<Coordinate> route;
		MapPolygonImpl poligon;
		
		boolean marcados[] = new boolean[modelo.getCoordenadas().size()];
		marcados[0] = true;
		
		while(todosMarcados(marcados) == false ) 
		{
			for(int i=0; i < marcados.length; i++) 
			{
				origen = modelo.getCoordenadas().get(i);
				
				if(marcados[i]) /*Si esta marcado*/
				{
					for(Integer j : modelo.getGrafo().vecinos(i)) /*Recorremos sus vecinos*/
					{
						destino = modelo.getCoordenadas().get(j);
						
						if(marcados[j] == false) /*Si su vecino no esta marcado*/
						{
							destino = modelo.getCoordenadas().get(j);
							route = new ArrayList<Coordinate>(Arrays.asList(origen, destino, destino)); //El poligono requiere  tres coordenadas
							poligon = new MapPolygonImpl(route);
							poligon.setColor(colorGrafo);
							vista.mapa.addMapPolygon(poligon);                     
							poligonos.add(poligon);
							marcados[j] = true;
						}
					}
				}
			}
		}
	}
	private boolean todosMarcados(boolean[] marcados) 
	{
		for(boolean b : marcados) 
			if(b == false)
				return false;
		return true;
	}
}
