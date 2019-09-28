package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.Color;

import javax.swing.JButton;


import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import javax.swing.BoxLayout;



public class Vista extends JFrame
{

	public JButton botonNuevo;
	public JButton botonGuardar;
	public JButton botonImportar;
	public JButton botonExportar;
	public JPanel panelMapa;
	public JMapViewer mapa;
	public JPanel panelGrafos;
	public MapPolygonImpl poligono;



	/**
	 * Create the application.
	 */
	public Vista() 
	{
		initialize();	
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		this.getContentPane().setBackground(Color.DARK_GRAY);
		this.setBounds(100, 100, 743, 469);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(null);
		
		panelMapa = new JPanel();
		panelMapa.setBackground(Color.WHITE);
		panelMapa.setBounds(10, 11, 438, 409);
		this.getContentPane().add(panelMapa);
		
		mapa = new JMapViewer();
		mapa.setBounds(0, 0, 358, 497);
		mapa.setDisplayPosition(new Coordinate(-34.52133782929332,-58.70068073272705), 16);
		panelMapa.add(mapa);
		
		botonImportar = new JButton("Importar");
		botonImportar.setToolTipText("Importar coordenadas");
		botonImportar.setBounds(458, 11, 124, 23);
		this.getContentPane().add(botonImportar);
		
		botonExportar = new JButton("Exportar");
		botonExportar.setToolTipText("Exportar coordenadas");
		botonExportar.setBounds(592, 11, 124, 23);
		getContentPane().add(botonExportar);
		
		botonGuardar = new JButton("Guardar");
		botonGuardar.setToolTipText("Guardar grafo");
		botonGuardar.setBounds(592, 45, 124, 23);
		getContentPane().add(botonGuardar);
		
		botonNuevo = new JButton("Nuevo");
		botonNuevo.setToolTipText("Nuevo grafo");
		botonNuevo.setBounds(458, 45, 124, 23);
		getContentPane().add(botonNuevo);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(458, 79, 259, 341);
		getContentPane().add(scrollPane);
		
		panelGrafos = new JPanel();
		scrollPane.setViewportView(panelGrafos);
		panelGrafos.setLayout(new BoxLayout(panelGrafos, BoxLayout.Y_AXIS));
	
		
	}
}
