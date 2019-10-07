package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Dimension;
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
	public JButton btnNuevo, btnGuardar, btnDeshacer, btnCancelar, btnImportar, btnExportar;
	private JPanel panelMapa;
	public JMapViewer mapa;
	private JScrollPane scrollPane;
	public PanelDeControles panelDeControles;
	
	public Color gris1, gris2, rojo, bordo;
	
	public Vista() 
	{
		this.setTitle("Clustering");
		this.setResizable(false);
		this.setBounds(100, 100, 977, 680);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(null);
		
		gris1 = new Color(179, 182, 183);
		gris2 = new Color(62, 54, 54);
		rojo = new Color(201, 47, 47);
		bordo = new Color(116, 16, 16);
		
		iniComponentes();	
	}

	private void iniComponentes() 
	{
		panelMapa = new JPanel();
		panelMapa.setBackground(Color.white);
		panelMapa.setBounds(10, 11, 620, 620);
		panelMapa.setLayout(null);
		this.getContentPane().add(panelMapa);
		
		mapa = new JMapViewer();
		mapa.setBorder(new LineBorder(gris1, 4));
		mapa.setBounds(0, 0, 620, 620);
		mapa.setZoomContolsVisible(false);	
		mapa.setDisplayPosition(new Coordinate(-34.52133782929332,-58.70068073272705), 13);
		panelMapa.add(mapa);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(640, 87, 308, 544);
		scrollPane.setBorder(null);
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);
		this.getContentPane().add(scrollPane);
		
		panelDeControles = new PanelDeControles();
		panelDeControles.setBackground(gris1);
		panelDeControles.setPreferredSize(new Dimension(260, 545));
		panelDeControles.setLayout(null);
		scrollPane.setViewportView(panelDeControles);
		
		btnImportar = new JButton("Importar");
		btnImportar.setBorder(null);
		btnImportar.setFont(new Font("Arial", Font.PLAIN, 13));
		btnImportar.setIcon(new ImageIcon(Vista.class.getResource("/iconos/importar24.png")));
		btnImportar.setBounds(640, 11, 150, 28);
		btnImportar.setBackground(rojo);
		btnImportar.setForeground(Color.WHITE);
		btnImportar.setFocusable(false);
		this.getContentPane().add(btnImportar);
		
		btnExportar = new JButton("Exportar");
		btnExportar.setBorderPainted(false);
		btnExportar.setBorder(null);
		btnExportar.setFont(new Font("Arial", Font.PLAIN, 13));
		btnExportar.setIcon(new ImageIcon(Vista.class.getResource("/iconos/exportar24.png")));
		btnExportar.setBounds(798, 11, 150, 28);
		btnExportar.setBackground(bordo);
		btnExportar.setForeground(Color.WHITE);
		btnExportar.setFocusable(false);
		btnExportar.setEnabled(false);
		this.getContentPane().add(btnExportar);		
		
		btnNuevo = new JButton("Nuevo");
		btnNuevo.setBorderPainted(false);
		btnNuevo.setBorder(null);
		btnNuevo.setFont(new Font("Arial", Font.PLAIN, 13));
		btnNuevo.setIcon(new ImageIcon(Vista.class.getResource("/iconos/puntero.png")));
		btnNuevo.setBounds(640, 45, 150, 28);
		btnNuevo.setBackground(rojo);
		btnNuevo.setForeground(Color.WHITE);
		btnNuevo.setFocusable(false);
		this.getContentPane().add(btnNuevo);
		
		btnGuardar = new JButton("");
		btnGuardar.setBorderPainted(false);
		btnGuardar.setBorder(null);
		btnGuardar.setFont(new Font("Arial", Font.PLAIN, 13));
		btnGuardar.setIcon(new ImageIcon(Vista.class.getResource("/iconos/guardar24.png")));
		btnGuardar.setBounds(798, 45, 50, 28);
		btnGuardar.setBackground(bordo);
		btnGuardar.setForeground(Color.WHITE);
		btnGuardar.setFocusable(false);
		btnGuardar.setEnabled(false);
		this.getContentPane().add(btnGuardar);
		
		btnDeshacer = new JButton("");
		btnDeshacer.setIcon(new ImageIcon(Vista.class.getResource("/iconos/deshacer24.png")));
		btnDeshacer.setForeground(Color.WHITE);
		btnDeshacer.setFont(new Font("Arial", Font.PLAIN, 13));
		btnDeshacer.setFocusable(false);
		btnDeshacer.setEnabled(false);
		btnDeshacer.setBorderPainted(false);
		btnDeshacer.setBorder(null);
		btnDeshacer.setBackground(bordo);
		btnDeshacer.setBounds(848, 45, 50, 28);
		this.getContentPane().add(btnDeshacer);
		
		btnCancelar = new JButton("");
		btnCancelar.setIcon(new ImageIcon(Vista.class.getResource("/iconos/cruz24.png")));
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setFont(new Font("Arial", Font.PLAIN, 13));
		btnCancelar.setFocusable(false);
		btnCancelar.setEnabled(false);
		btnCancelar.setBorderPainted(false);
		btnCancelar.setBorder(null);
		btnCancelar.setBackground(bordo);
		btnCancelar.setBounds(898, 45, 50, 28);
		this.getContentPane().add(btnCancelar);
		
		
		MouseAdapter efectoHover = new MouseAdapter()
		{
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				JButton boton = (JButton) e.getSource();
				
				if(boton.isEnabled()) 
					boton.setBackground(rojo.brighter());
				
			}
		
			@Override
			public void mouseExited(MouseEvent e) 
			{
				JButton boton = (JButton) e.getSource();
				if(boton.isEnabled()) 
					boton.setBackground(rojo);
			}
		};
		
		btnImportar.addMouseListener(efectoHover);
		btnExportar.addMouseListener(efectoHover);
		btnNuevo.addMouseListener(efectoHover);
		btnGuardar.addMouseListener(efectoHover);
		btnCancelar.addMouseListener(efectoHover);
		btnDeshacer.addMouseListener(efectoHover);
		
	}
}
