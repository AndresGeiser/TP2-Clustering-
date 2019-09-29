package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;

import java.awt.event.MouseAdapter;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Vista extends JFrame
{
	public JButton botonNuevo;
	public JButton botonGuardar;
	public JButton botonImportar;
	public JButton botonExportar;
	public JPanel panelMapa;
	public JMapViewer mapa;
	public PanelDeControles panelDeControles;
	
	public Color gris1, gris2, rojo1, rojo2, rojo3, bordo;

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
		gris1 = new Color(179, 182, 183);
		gris2 = new Color(208, 211, 212);
		rojo1 = new Color(201, 47, 47);
		rojo2 = new Color(225, 121, 121);
		rojo3 = new Color(180, 51, 51);
		bordo = new Color(116, 16, 16);
		
		this.getContentPane().setBackground(gris2);
		this.setBounds(100, 100, 743, 469);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(null);
		
		panelMapa = new JPanel();
		panelMapa.setBackground(Color.white);
		panelMapa.setBounds(10, 11, 438, 409);
		this.getContentPane().add(panelMapa);
		
		mapa = new JMapViewer();
		mapa.setBorder(new LineBorder(new Color(179, 182, 183), 4));
		mapa.setBounds(0, 0, 438, 409);
		mapa.setZoomContolsVisible(false);
		mapa.setDisplayPosition(new Coordinate(-34.52133782929332,-58.70068073272705), 13);
		panelMapa.setLayout(null);
		panelMapa.add(mapa);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(458, 79, 259, 341);
		scrollPane.setBorder(null);
		getContentPane().add(scrollPane);
		
		panelDeControles = new PanelDeControles();
		scrollPane.setViewportView(panelDeControles);
		panelDeControles.setBackground(new Color(179, 182, 183));
		panelDeControles.setLayout(null);
		
		botonImportar = new JButton("Importar");
		botonImportar.setBorder(null);
		botonImportar.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		botonImportar.setIcon(new ImageIcon(Vista.class.getResource("/iconos/iconImportar.png")));
		botonImportar.setToolTipText("Importar coordenadas");
		botonImportar.setBounds(458, 11, 124, 23);
		botonImportar.setBackground(rojo1);
		botonImportar.setForeground(Color.WHITE);
		botonImportar.setFocusable(false);
		this.getContentPane().add(botonImportar);
		
		botonExportar = new JButton("Exportar");
		botonExportar.setBorderPainted(false);
		botonExportar.setBorder(null);
		botonExportar.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		botonExportar.setIcon(new ImageIcon(Vista.class.getResource("/iconos/iconExportar.png")));
		botonExportar.setToolTipText("Exportar coordenadas");
		botonExportar.setBounds(592, 11, 124, 23);
		botonExportar.setBackground(rojo1);
		botonExportar.setForeground(Color.WHITE);
		botonExportar.setFocusable(false);;
		getContentPane().add(botonExportar);
		
		botonGuardar = new JButton("Guardar");
		botonGuardar.setBorderPainted(false);
		botonGuardar.setBorder(null);
		botonGuardar.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		botonGuardar.setIcon(new ImageIcon(Vista.class.getResource("/iconos/iconGuardar.png")));
		botonGuardar.setToolTipText("Guardar grafo");
		botonGuardar.setBounds(592, 45, 124, 23);
		botonGuardar.setBackground(rojo1);
		botonGuardar.setForeground(Color.WHITE);
		botonGuardar.setFocusable(false);
		botonGuardar.setEnabled(false);
		getContentPane().add(botonGuardar);
		
		botonNuevo = new JButton("Nuevo");
		botonNuevo.setBorderPainted(false);
		botonNuevo.setBorder(null);
		botonNuevo.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		botonNuevo.setIcon(new ImageIcon(Vista.class.getResource("/iconos/iconCursor.png")));
		botonNuevo.setToolTipText("Nuevo grafo");
		botonNuevo.setBounds(458, 45, 124, 23);
		botonNuevo.setBackground(rojo1);
		botonNuevo.setForeground(Color.WHITE);
		botonNuevo.setFocusable(false);
		getContentPane().add(botonNuevo);
		
		efectoHoverBoton(botonImportar, rojo2, rojo1);
		efectoHoverBoton(botonGuardar, rojo2, rojo1);
		efectoHoverBoton(botonExportar, rojo2, rojo1);
		efectoHoverBoton(botonNuevo, rojo2, rojo1);
		
	}
	
	public void efectoHoverBoton(JButton boton, Color punteroEncima, Color punteroFuera) 
	{
		boton.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseEntered(MouseEvent arg0) 
			{
				boton.setBackground(punteroEncima);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				boton.setBackground(punteroFuera);
			}
		});
	}
}
