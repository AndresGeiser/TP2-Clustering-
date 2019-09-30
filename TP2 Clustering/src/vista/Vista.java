package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import java.awt.event.MouseAdapter;
import javax.swing.border.LineBorder;


public class Vista extends JFrame  
{
	public JButton botonNuevo, botonGuardar, botonBorrarUltimo, botonCancelar, botonImportar, botonExportar;
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
		botonImportar.setFont(new Font("Arial", Font.PLAIN, 13));
		botonImportar.setIcon(new ImageIcon(Vista.class.getResource("/iconos/importar24.png")));
		botonImportar.setBounds(458, 11, 124, 28);
		botonImportar.setBackground(rojo1);
		botonImportar.setForeground(Color.WHITE);
		botonImportar.setFocusable(false);
		this.getContentPane().add(botonImportar);
		
		botonExportar = new JButton("Exportar");
		botonExportar.setBorderPainted(false);
		botonExportar.setBorder(null);
		botonExportar.setFont(new Font("Arial", Font.PLAIN, 13));
		botonExportar.setIcon(new ImageIcon(Vista.class.getResource("/iconos/exportar24.png")));
		botonExportar.setBounds(592, 11, 124, 28);
		botonExportar.setBackground(rojo1);
		botonExportar.setForeground(Color.WHITE);
		botonExportar.setFocusable(false);;
		getContentPane().add(botonExportar);		
		
		botonNuevo = new JButton("Nuevo");
		botonNuevo.setBorderPainted(false);
		botonNuevo.setBorder(null);
		botonNuevo.setFont(new Font("Arial", Font.PLAIN, 13));
		botonNuevo.setIcon(new ImageIcon(Vista.class.getResource("/iconos/puntero.png")));
		botonNuevo.setBounds(458, 45, 124, 28);
		botonNuevo.setBackground(rojo1);
		botonNuevo.setForeground(Color.WHITE);
		botonNuevo.setFocusable(false);
		getContentPane().add(botonNuevo);
		
		botonGuardar = new JButton("");
		botonGuardar.setBorderPainted(false);
		botonGuardar.setBorder(null);
		botonGuardar.setFont(new Font("Arial", Font.PLAIN, 13));
		botonGuardar.setIcon(new ImageIcon(Vista.class.getResource("/iconos/guardar24.png")));
		botonGuardar.setBounds(592, 45, 40, 28);
		botonGuardar.setBackground(bordo);
		botonGuardar.setForeground(Color.WHITE);
		botonGuardar.setFocusable(false);
		botonGuardar.setEnabled(false);
		getContentPane().add(botonGuardar);
		
		botonBorrarUltimo = new JButton("");
		botonBorrarUltimo.setIcon(new ImageIcon(Vista.class.getResource("/iconos/deshacer24.png")));
		botonBorrarUltimo.setForeground(Color.WHITE);
		botonBorrarUltimo.setFont(new Font("Arial", Font.PLAIN, 13));
		botonBorrarUltimo.setFocusable(false);
		botonBorrarUltimo.setEnabled(false);
		botonBorrarUltimo.setBorderPainted(false);
		botonBorrarUltimo.setBorder(null);
		botonBorrarUltimo.setBackground(bordo);
		botonBorrarUltimo.setBounds(634, 45, 40, 28);
		getContentPane().add(botonBorrarUltimo);
		
		botonCancelar = new JButton("");
		botonCancelar.setIcon(new ImageIcon(Vista.class.getResource("/iconos/cruz24.png")));
		botonCancelar.setForeground(Color.WHITE);
		botonCancelar.setFont(new Font("Arial", Font.PLAIN, 13));
		botonCancelar.setFocusable(false);
		botonCancelar.setEnabled(false);
		botonCancelar.setBorderPainted(false);
		botonCancelar.setBorder(null);
		botonCancelar.setBackground(bordo);
		botonCancelar.setBounds(676, 45, 40, 28);
		getContentPane().add(botonCancelar);
		
		MouseAdapter efectoHover = new MouseAdapter()
		{
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				JButton boton = (JButton) e.getSource();
				
				if(boton.isEnabled()) 
					boton.setBackground(rojo2);
				
			}
		
			@Override
			public void mouseExited(MouseEvent e) 
			{
				JButton boton = (JButton) e.getSource();
				if(boton.isEnabled()) 
					boton.setBackground(rojo1);
			
			}
		};
		
		botonImportar.addMouseListener(efectoHover);
		botonExportar.addMouseListener(efectoHover);
		botonNuevo.addMouseListener(efectoHover);
		botonGuardar.addMouseListener(efectoHover);
		botonCancelar.addMouseListener(efectoHover);
		botonBorrarUltimo.addMouseListener(efectoHover);
		
		
	}
	

}
