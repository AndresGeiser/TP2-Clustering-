package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;

import modelo.Modelo;

public class PanelGrafo extends JPanel 
{
	public JLabel lblNombre;
	public JTextField txtCantClusters;
	public JCheckBox boxVisibilidad;
	public JButton btnCentrar, btnEliminar, btnClustering, btnEstadisticas;
	
	public Color gris;
	
	public PanelGrafo(String nombre) 
	{
		gris = new Color(62, 54, 54);
		
		this.setBorder(new MatteBorder(0, 0, 1, 0, Color.WHITE));
		this.setBackground(gris);
		this.setLayout(null);
		
		iniComponentes(nombre);
	}

	private void iniComponentes(String nombre) 
	{	
		lblNombre = new JLabel(nombre);
		lblNombre.setToolTipText(nombre);
		lblNombre.setBounds(28, 7, 100, 23);
		lblNombre.setForeground(Color.white);
		lblNombre.setFont(new Font("Lucida Sans", Font.BOLD, 13));
		lblNombre.setBorder(null);
		this.add(this.lblNombre);
		
		boxVisibilidad = new JCheckBox();
		boxVisibilidad.setBackground(null);
		boxVisibilidad.setBounds(6, 7, 21, 23);
		boxVisibilidad.setSelected(true);
		boxVisibilidad.setToolTipText("Ocultar");
		this.add(boxVisibilidad);
	
		btnCentrar = new JButton();
		btnCentrar.setIcon(new ImageIcon(PanelGrafo.class.getResource("/iconos/iconCentrar.png")));
		btnCentrar.setToolTipText("Centrar");
		btnCentrar.setBackground(null);
		btnCentrar.setBounds(156, 7, 29, 23);
		this.add(btnCentrar);
		
		btnEliminar = new JButton();
		btnEliminar.setIcon(new ImageIcon(PanelGrafo.class.getResource("/iconos/iconCesto.png")));
		btnEliminar.setToolTipText("Eliminar");
		btnEliminar.setBackground(null);
		btnEliminar.setBounds(187, 7, 29, 23);
		this.add(btnEliminar);
		
		btnEstadisticas = new JButton();
		btnEstadisticas.setIcon(new ImageIcon(PanelGrafo.class.getResource("/iconos/iconEstadisticas.png")));
		btnEstadisticas.setToolTipText("Ver estadisticas");
		btnEstadisticas.setBackground(null);
		btnEstadisticas.setBounds(125, 7, 29, 23);
		this.add(btnEstadisticas);
		
		txtCantClusters = new JTextField();
		txtCantClusters.setBackground(gris.brighter());
		txtCantClusters.setForeground(Color.WHITE);
		txtCantClusters.setText("1");
		txtCantClusters.addKeyListener(new KeyAdapter() 
		{
			@Override
			public void keyTyped(KeyEvent arg0) 
			{			
				if(txtCantClusters.getText().length() == 3)
				{
					Toolkit.getDefaultToolkit().beep();
					arg0.consume();
				}
				
				if(!Character.isDigit(arg0.getKeyChar()))
					arg0.consume();
			}	
		});
		txtCantClusters.setBounds(218, 7, 33, 23);
		this.add(txtCantClusters);
		
		btnClustering = new JButton();
		btnClustering.setIcon(new ImageIcon(PanelGrafo.class.getResource("/iconos/iconClustering.png")));
		btnClustering.setToolTipText("Clustering");
		btnClustering.setBackground(null);
		btnClustering.setBounds(253, 7, 29, 23);
		this.add(btnClustering);
			
		MouseAdapter efectoHover = new MouseAdapter()
		{
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				JButton boton = (JButton) e.getSource();
			
				boton.setBackground(gris.brighter());
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
		btnEstadisticas.addMouseListener(efectoHover);
		
	}
	
}
