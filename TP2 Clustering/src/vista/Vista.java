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

import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import javax.swing.border.LineBorder;
import javax.swing.BoxLayout;


public class Vista extends JFrame  
{
	public JButton btnNuevo, btnGuardar, btnDeshacer, btnCancelar, btnImportar, btnExportar;
	public JPanel panelMapa;
	public JMapViewer mapa;
	public PanelDeControles panelDeControles;
	
	public Color gris1, gris2, gris3, gris4, rojo1, rojo2, rojo3, bordo, naranja1, naranja2;
	
	public Vista() 
	{
		this.getContentPane().setBackground(gris2);
		this.setBounds(100, 100, 776, 501);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(null);
		
		gris1 = new Color(179, 182, 183);
		gris2 = new Color(208, 211, 212);
		gris3 = new Color(62, 54, 54);
		rojo1 = new Color(201, 47, 47);
		rojo2 = new Color(225, 121, 121);
		rojo3 = new Color(180, 51, 51);
		bordo = new Color(116, 16, 16);
		naranja1 = new Color(220, 118, 51);
		naranja2 = new Color(245, 176, 65);
		gris4 = new Color(102, 94, 94);
		iniComponentes();	
	}

	private void iniComponentes() 
	{
		panelMapa = new JPanel();
		panelMapa.setBackground(Color.white);
		panelMapa.setBounds(10, 11, 438, 446);
		this.getContentPane().add(panelMapa);
		
		mapa = new JMapViewer();
		mapa.setBorder(new LineBorder(gris1, 4));
		mapa.setBounds(0, 0, 438, 446);
		mapa.setZoomContolsVisible(false);	
		mapa.setDisplayPosition(new Coordinate(-34.52133782929332,-58.70068073272705), 13);
		panelMapa.setLayout(null);
		panelMapa.add(mapa);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(458, 87, 308, 370);
		scrollPane.setBorder(null);
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);
		getContentPane().add(scrollPane);
		
		panelDeControles = new PanelDeControles();
		panelDeControles.setBackground(gris1);
		panelDeControles.setPreferredSize(new Dimension(260, 371));
		scrollPane.setViewportView(panelDeControles);
		panelDeControles.setLayout(null);
		
		btnImportar = new JButton("Importar");
		btnImportar.setBorder(null);
		btnImportar.setFont(new Font("Arial", Font.PLAIN, 13));
		btnImportar.setIcon(new ImageIcon(Vista.class.getResource("/iconos/importar24.png")));
		btnImportar.setBounds(458, 11, 135, 28);
		btnImportar.setBackground(rojo1);
		btnImportar.setForeground(Color.WHITE);
		btnImportar.setFocusable(false);
		this.getContentPane().add(btnImportar);
		
		btnExportar = new JButton("Exportar");
		btnExportar.setBorderPainted(false);
		btnExportar.setBorder(null);
		btnExportar.setFont(new Font("Arial", Font.PLAIN, 13));
		btnExportar.setIcon(new ImageIcon(Vista.class.getResource("/iconos/exportar24.png")));
		btnExportar.setBounds(600, 11, 135, 28);
		btnExportar.setBackground(rojo1);
		btnExportar.setForeground(Color.WHITE);
		btnExportar.setFocusable(false);
		getContentPane().add(btnExportar);		
		
		btnNuevo = new JButton("Nuevo");
		btnNuevo.setBorderPainted(false);
		btnNuevo.setBorder(null);
		btnNuevo.setFont(new Font("Arial", Font.PLAIN, 13));
		btnNuevo.setIcon(new ImageIcon(Vista.class.getResource("/iconos/puntero.png")));
		btnNuevo.setBounds(458, 45, 135, 28);
		btnNuevo.setBackground(rojo1);
		btnNuevo.setForeground(Color.WHITE);
		btnNuevo.setFocusable(false);
		getContentPane().add(btnNuevo);
		
		btnGuardar = new JButton("");
		btnGuardar.setBorderPainted(false);
		btnGuardar.setBorder(null);
		btnGuardar.setFont(new Font("Arial", Font.PLAIN, 13));
		btnGuardar.setIcon(new ImageIcon(Vista.class.getResource("/iconos/guardar24.png")));
		btnGuardar.setBounds(600, 45, 45, 28);
		btnGuardar.setBackground(bordo);
		btnGuardar.setForeground(Color.WHITE);
		btnGuardar.setFocusable(false);
		btnGuardar.setEnabled(false);
		getContentPane().add(btnGuardar);
		
		btnDeshacer = new JButton("");
		btnDeshacer.setIcon(new ImageIcon(Vista.class.getResource("/iconos/deshacer24.png")));
		btnDeshacer.setForeground(Color.WHITE);
		btnDeshacer.setFont(new Font("Arial", Font.PLAIN, 13));
		btnDeshacer.setFocusable(false);
		btnDeshacer.setEnabled(false);
		btnDeshacer.setBorderPainted(false);
		btnDeshacer.setBorder(null);
		btnDeshacer.setBackground(bordo);
		btnDeshacer.setBounds(645, 45, 45, 28);
		getContentPane().add(btnDeshacer);
		
		btnCancelar = new JButton("");
		btnCancelar.setIcon(new ImageIcon(Vista.class.getResource("/iconos/cruz24.png")));
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setFont(new Font("Arial", Font.PLAIN, 13));
		btnCancelar.setFocusable(false);
		btnCancelar.setEnabled(false);
		btnCancelar.setBorderPainted(false);
		btnCancelar.setBorder(null);
		btnCancelar.setBackground(bordo);
		btnCancelar.setBounds(690, 45, 45, 28);
		getContentPane().add(btnCancelar);
		
		
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
		
		btnImportar.addMouseListener(efectoHover);
		btnExportar.addMouseListener(efectoHover);
		btnNuevo.addMouseListener(efectoHover);
		btnGuardar.addMouseListener(efectoHover);
		btnCancelar.addMouseListener(efectoHover);
		btnDeshacer.addMouseListener(efectoHover);
		
	}
}
