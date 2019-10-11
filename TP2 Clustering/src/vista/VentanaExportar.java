package vista;

import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

public class VentanaExportar extends JDialog
{
	private VentanaPrincipal vPrincipal;
	
	public JLabel lblMensaje;
	public JButton btnCancelar;
	public JButton btnExportar;
	public JScrollPane scrollPane;
	public JPanel panelDeOpciones;
	
	public ArrayList<JCheckBox> opciones;
	
	private Color rojo, gris;
	
	public VentanaExportar(VentanaPrincipal vPrincipal, boolean b) 
	{
		super(vPrincipal, b);
		
		this.vPrincipal = vPrincipal;
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaExportar.class.getResource("/iconos/iconVentanaExportar.png")));	
		this.setResizable(false);
		this.setTitle("Exportar");
		this.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 11));
		this.setBounds(100, 100, 500, 210);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(null);
		
		rojo = new Color(201, 47, 47);
		gris = new Color(179, 182, 183);
		
		iniComponentes();
		
		opciones = new ArrayList<JCheckBox>();
		colocarOpciones();
		
	}
	
	private void iniComponentes() 
	{
		lblMensaje = new JLabel("Seleccione las coordenadas que quieres exportar:");
		lblMensaje.setFont(new Font("Arial", Font.PLAIN, 13));
		lblMensaje.setForeground(Color.BLACK);
		lblMensaje.setBounds(10, 11, 464, 14);
		getContentPane().add(lblMensaje);
	
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 36, 464, 80);
		scrollPane.setBorder(null);
		getContentPane().add(scrollPane);
		
		panelDeOpciones = new JPanel();
		panelDeOpciones.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panelDeOpciones.setBackground(gris);
		scrollPane.setViewportView(panelDeOpciones);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBorderPainted(false);
		btnCancelar.setBorder(null);
		btnCancelar.setFont(new Font("Arial", Font.PLAIN, 13));
		btnCancelar.setBackground(rojo);
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setFocusable(false);
		btnCancelar.setBounds(10, 127, 126, 31);
		getContentPane().add(btnCancelar);
		
		btnExportar = new JButton("Exportar");
		btnExportar.setBorderPainted(false);
		btnExportar.setBorder(null);
		btnExportar.setFont(new Font("Arial", Font.PLAIN, 13));
		btnExportar.setBackground(rojo);
		btnExportar.setForeground(Color.WHITE);
		btnExportar.setFocusable(false);
		btnExportar.setBounds(348, 127, 126, 31);
		getContentPane().add(btnExportar);	
		
		MouseAdapter efectoHover = new MouseAdapter()
		{
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				JButton boton = (JButton) e.getSource();
				
				boton.setBackground(rojo.darker());
				boton.setFont(new Font("Arial", Font.PLAIN, 11));	
			}
		
			@Override
			public void mouseExited(MouseEvent e) 
			{
				JButton boton = (JButton) e.getSource();
				
				boton.setBackground(rojo);
				boton.setFont(new Font("Arial", Font.PLAIN, 13));
			}
		};
		btnExportar.addMouseListener(efectoHover);
		btnCancelar.addMouseListener(efectoHover);
		
	}

	
	private void colocarOpciones() 
	{
		Component[] componentes = vPrincipal.panelDeControles.getComponents();
		
		PanelGrafo panel;
		JCheckBox opcion;	
		for(Component componente : componentes)
		{	
			panel = (PanelGrafo) componente;
			
			opcion = new JCheckBox(panel.lblNombre.getText());
			opcion.setToolTipText(opcion.getText());
			opcion.setPreferredSize(new Dimension(100,50));
			
			panelDeOpciones.add(opcion);
			opciones.add(opcion);
		}
	}
	
	
}