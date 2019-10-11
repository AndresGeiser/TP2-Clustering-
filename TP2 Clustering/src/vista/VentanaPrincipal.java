package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import java.awt.event.MouseAdapter;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.MouseMotionAdapter;

public class VentanaPrincipal extends JFrame 
{
	public JButton btnNuevo, btnGuardar, btnDeshacer, btnCancelar, btnImportar, btnExportar;
	public JPanel panelMapa;
	public JMapViewer mapa;
	public JTextField txtLatitud, txtLongitud;
	public JLabel dibujoPlaneta;
	
	public JScrollPane scrollPaneControles;
	public PanelDeControles panelDeControles;
	
	public Color gris1, rojo;
	
	public VentanaPrincipal() 
	{
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaPrincipal.class.getResource("/iconos/iconPrincipal.png")));
		this.setTitle("Clustering");
		this.setResizable(false);
		this.setBounds(100, 100, 967, 680);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(null);
		
		gris1 = new Color(179, 182, 183);
		rojo = new Color(201, 47, 47);

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
		mapa.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent m) {
				
				Coordinate coord = (Coordinate) mapa.getPosition(m.getPoint());
				txtLatitud.setText("Lat: " + coord.getLat());
				txtLongitud.setText("Lon: " + coord.getLon());
			}
		});

		mapa.setBorder(new LineBorder(gris1, 4));
		mapa.setBounds(0, 0, 620, 620);
		mapa.setZoomContolsVisible(false);	
		mapa.setDisplayPosition(new Coordinate(-34.52133782929332,-58.70068073272705), 13);
		panelMapa.add(mapa);
		
		txtLatitud = new JTextField("Lat :");
		txtLatitud.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		txtLatitud.setBounds(10, 585, 165, 24);
		txtLatitud.setEditable(false);
		txtLatitud.setFocusable(false);
		txtLatitud.setBackground(gris1);
		mapa.add(txtLatitud);
		
		txtLongitud = new JTextField("Lon :");
		txtLongitud.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		txtLongitud.setBounds(445, 585, 165, 24);
		txtLongitud.setEditable(false);
		txtLongitud.setFocusable(false);
		txtLongitud.setBackground(gris1);
		mapa.add(txtLongitud);
		
		scrollPaneControles = new JScrollPane();
		scrollPaneControles.setBounds(640, 87, 308, 407);
		scrollPaneControles.setBorder(null);
		scrollPaneControles.getVerticalScrollBar().setUnitIncrement(30);
		this.getContentPane().add(scrollPaneControles);
		
		panelDeControles = new PanelDeControles();
		panelDeControles.setBackground(gris1);
		panelDeControles.setPreferredSize(new Dimension(260, 408));
		panelDeControles.setLayout(null);
		scrollPaneControles.setViewportView(panelDeControles);
		
		btnImportar = new JButton("Importar");
		btnImportar.setBorder(null);
		btnImportar.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/iconos/importar24.png")));
		btnImportar.setFont(new Font("Arial", Font.PLAIN, 13));
		btnImportar.setBounds(640, 11, 150, 35);
		btnImportar.setBackground(rojo);
		btnImportar.setForeground(Color.WHITE);
		this.getContentPane().add(btnImportar);
		
		btnExportar = new JButton("Exportar");
		btnExportar.setBorder(null);
		btnExportar.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/iconos/exportar24.png")));
		btnExportar.setFont(new Font("Arial", Font.PLAIN, 13));
		btnExportar.setBounds(798, 11, 150, 35);
		btnExportar.setBackground(rojo);
		btnExportar.setForeground(Color.gray);
		btnExportar.setEnabled(false);
		this.getContentPane().add(btnExportar);		
		
		btnNuevo = new JButton("Nuevo");
		btnNuevo.setBorder(null);
		btnNuevo.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/iconos/puntero.png")));
		btnNuevo.setFont(new Font("Arial", Font.PLAIN, 13));
		btnNuevo.setBounds(640, 45, 150, 35);
		btnNuevo.setBackground(rojo);
		btnNuevo.setForeground(Color.WHITE);
		this.getContentPane().add(btnNuevo);
		
		btnGuardar = new JButton();
		btnGuardar.setBorder(null);
		btnGuardar.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/iconos/guardar24.png")));
		btnGuardar.setFont(new Font("Arial", Font.PLAIN, 13));
		btnGuardar.setBounds(798, 45, 50, 35);
		btnGuardar.setForeground(Color.WHITE);
		btnGuardar.setEnabled(false);
		this.getContentPane().add(btnGuardar);
		
		btnDeshacer = new JButton();
		btnDeshacer.setBorder(null);
		btnDeshacer.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/iconos/deshacer24.png")));
		btnDeshacer.setForeground(Color.WHITE);
		btnDeshacer.setFont(new Font("Arial", Font.PLAIN, 13));
		btnDeshacer.setEnabled(false);
		btnDeshacer.setBounds(848, 45, 50, 35);
		this.getContentPane().add(btnDeshacer);
		
		btnCancelar = new JButton();
		btnCancelar.setBorder(null);
		btnCancelar.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/iconos/cruz24.png")));
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setFont(new Font("Arial", Font.PLAIN, 13));
		btnCancelar.setEnabled(false);
		btnCancelar.setBounds(898, 45, 50, 35);
		this.getContentPane().add(btnCancelar);
		
		dibujoPlaneta = new JLabel("");
		dibujoPlaneta.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/iconos/tierra (1).png")));
		dibujoPlaneta.setBounds(720, 503, 128, 128);
		this.getContentPane().add(dibujoPlaneta);
		
		
		MouseAdapter efectoHover = new MouseAdapter()
		{
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				JButton boton = (JButton) e.getSource();
				
				if(boton.isEnabled()) 
					boton.setBackground(rojo.darker());
				
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
