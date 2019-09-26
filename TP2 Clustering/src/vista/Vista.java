package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Vista extends JFrame{

	public JButton botonImportar;
	private JPanel panelMapa;
	public JMapViewer mapa;
	
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
		this.setBounds(100, 100, 608, 408);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(null);
		
		panelMapa = new JPanel();
		panelMapa.setBackground(Color.WHITE);
		panelMapa.setBounds(10, 11, 438, 348);
		this.getContentPane().add(panelMapa);
		
		mapa = new JMapViewer();
		mapa.setBounds(0, 0, 358, 497);
		mapa.setDisplayPosition(new Coordinate(-34.52133782929332,-58.70068073272705), 16);
		panelMapa.add(mapa);
		
		botonImportar = new JButton("Importar");
		botonImportar.setToolTipText("Importar archivo");
		botonImportar.setBounds(458, 11, 124, 23);
		this.getContentPane().add(botonImportar);
		
	}
}
