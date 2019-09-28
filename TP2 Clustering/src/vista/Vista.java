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



public class Vista extends JFrame
{
	public JButton botonNuevo;
	public JButton botonGuardar;
	public JButton botonImportar;
	public JButton botonExportar;
	public JPanel panelMapa;
	public JMapViewer mapa;
	public PanelDeControles panelDeControles;

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
		Color verde1 = new Color(20, 143, 119);
		Color verde2 = new Color(26, 188, 156);
		
		this.getContentPane().setBackground(new Color(21, 67, 96));
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
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(458, 79, 259, 341);
		scrollPane.setBorder(null);
		getContentPane().add(scrollPane);
		
		panelDeControles = new PanelDeControles();
		scrollPane.setViewportView(panelDeControles);
		panelDeControles.setBackground(new Color(26, 82, 118));
		panelDeControles.setLayout(null);
		
		botonImportar = new JButton("Importar");
		botonImportar.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		botonImportar.setIcon(new ImageIcon(Vista.class.getResource("/iconos/iconImportar.png")));
		botonImportar.setToolTipText("Importar coordenadas");
		botonImportar.setBounds(458, 11, 124, 23);
		botonImportar.setBackground(verde2);
		botonImportar.setForeground(Color.WHITE);
		botonImportar.setFocusable(false);
		botonImportar.setBorderPainted(false);
		this.getContentPane().add(botonImportar);
		
		botonExportar = new JButton("Exportar");
		botonExportar.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		botonExportar.setIcon(new ImageIcon(Vista.class.getResource("/iconos/iconExportar.png")));
		botonExportar.setToolTipText("Exportar coordenadas");
		botonExportar.setBounds(592, 11, 124, 23);
		botonExportar.setBackground(verde2);
		botonExportar.setForeground(Color.WHITE);
		botonExportar.setFocusable(false);
		botonExportar.setBorderPainted(false);;
		getContentPane().add(botonExportar);
		
		botonGuardar = new JButton("Guardar");
		botonGuardar.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		botonGuardar.setIcon(new ImageIcon(Vista.class.getResource("/iconos/iconGuardar.png")));
		botonGuardar.setToolTipText("Guardar grafo");
		botonGuardar.setBounds(592, 45, 124, 23);
		botonGuardar.setBackground(verde2);
		botonGuardar.setForeground(Color.WHITE);
		botonGuardar.setFocusable(false);
		botonGuardar.setBorderPainted(false);
		botonGuardar.setEnabled(false);
		getContentPane().add(botonGuardar);
		
		botonNuevo = new JButton("Nuevo");
		botonNuevo.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		botonNuevo.setIcon(new ImageIcon(Vista.class.getResource("/iconos/iconCursor.png")));
		botonNuevo.setToolTipText("Nuevo grafo");
		botonNuevo.setBounds(458, 45, 124, 23);
		botonNuevo.setBackground(verde2);
		botonNuevo.setForeground(Color.WHITE);
		botonNuevo.setFocusable(false);
		botonNuevo.setBorderPainted(false);
		getContentPane().add(botonNuevo);
		
		efectoHoverBoton(botonImportar, verde1, verde2);
		efectoHoverBoton(botonGuardar, verde1, verde2);
		efectoHoverBoton(botonExportar, verde1, verde2);
		efectoHoverBoton(botonNuevo, verde1, verde2);
		
	}
	
	private void efectoHoverBoton(JButton boton, Color punteroEncima, Color punteroFuera) 
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
