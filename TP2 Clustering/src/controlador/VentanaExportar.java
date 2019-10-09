package controlador;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import vista.Vista;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;


public class VentanaExportar extends JDialog implements ActionListener
{
	private Vista vista;

	private JLabel lblMensaje;
	private JButton btnCancelar;
	private JButton btnExportar;
	private JScrollPane scrollPane;
	private JPanel panelDeOpciones;
	
	private ArrayList<JCheckBox> opciones;
	
	public VentanaExportar(Vista vista, boolean b) 
	{
		super(vista, b);
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaExportar.class.getResource("/iconos/iconVentanaExportar.png")));	
		this.setResizable(false);
		this.setTitle("Exportar");
		this.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 11));
		this.setBounds(100, 100, 500, 210);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(null);
	
		this.vista = vista;
		
		opciones = new ArrayList<JCheckBox>();
		
		iniComponentes();
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
		panelDeOpciones.setBackground(vista.gris1);
		scrollPane.setViewportView(panelDeOpciones);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBorderPainted(false);
		btnCancelar.setBorder(null);
		btnCancelar.setFont(new Font("Arial", Font.PLAIN, 13));
		btnCancelar.setBackground(vista.rojo);
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setFocusable(false);
		btnCancelar.setBounds(10, 127, 126, 31);
		getContentPane().add(btnCancelar);
		
		btnExportar = new JButton("Exportar");
		btnExportar.setBorderPainted(false);
		btnExportar.setBorder(null);
		btnExportar.setFont(new Font("Arial", Font.PLAIN, 13));
		btnExportar.setBackground(vista.rojo);
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
				
				boton.setBackground(vista.rojo.brighter());
				boton.setFont(new Font("Arial", Font.PLAIN, 11));	
			}
		
			@Override
			public void mouseExited(MouseEvent e) 
			{
				JButton boton = (JButton) e.getSource();
				
				boton.setBackground(vista.rojo);
				boton.setFont(new Font("Arial", Font.PLAIN, 13));
			}
		};
		btnExportar.addMouseListener(efectoHover);
		btnCancelar.addMouseListener(efectoHover);
		
		btnExportar.addActionListener(this);
		btnCancelar.addActionListener(this);
		
	}
	
	//Metodo que obtiene el nombre de cada conjunto de coordenadas y las pone como opciones a exportar
	private void colocarOpciones() 
	{
		Component[] componentes = vista.panelDeControles.getComponents(); 
		
		JCheckBox checkBox;
		PanelControl panelControl;
		for(Component componente : componentes) 
		{
			panelControl = (PanelControl) componente;
			
			checkBox = new JCheckBox(panelControl.getNombre());
			checkBox.setToolTipText(checkBox.getText());
			checkBox.setPreferredSize(new Dimension(100,50));
			
			panelDeOpciones.add(checkBox);
			opciones.add(checkBox);
		}	 	
	}

	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		if(ae.getSource() == btnExportar) 
		{	
			if(selecionoAlguno())
			{
				JFileChooser guardar = new JFileChooser();
				guardar.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				
				if(guardar.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
				{	
					File directorio = guardar.getSelectedFile();
					
					if(existe(directorio)) 
					{
						crearArchivos(directorio);
						JOptionPane.showMessageDialog(null, "Las coordenadas se han exportado exitosamente.", "Completado", JOptionPane.INFORMATION_MESSAGE);			
						this.dispose();
					}
					else
						JOptionPane.showMessageDialog(null, "El directorio " + directorio + " no existe.", "Error", JOptionPane.ERROR_MESSAGE);
				}
		
			}
			else	
				JOptionPane.showMessageDialog(null, "Selecione alguno !!", "Atencion", JOptionPane.WARNING_MESSAGE);
			
		}
		
		if(ae.getSource() == btnCancelar)
			this.dispose();
		
	}
	
	private void crearArchivos(File directorio) 
	{
		try 
		{	
			File archivo;
			FileWriter fw;
			BufferedWriter bw;
			
			for(PanelControl panel : elegidos()) 
			{
				archivo = new File(directorio + "\\" + panel.getNombre() + ".txt");
				
				int i=0;
				while(archivo.exists())
				{
					i++;
					archivo.renameTo(new File(directorio + "\\" + panel.getNombre() + " (" + i + ")"+ ".txt"));
				}
			
				archivo.createNewFile();
				
				fw = new FileWriter(archivo);
				bw = new BufferedWriter(fw);
				
				for(Coordinate coordenada : panel.getCoordenadas()) 
				{
					bw.write(coordenada.getLat() + " " + coordenada.getLon());
					bw.newLine();
				}
				
				bw.newLine();
				bw.close();
			
			}
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	private ArrayList<PanelControl> elegidos() 
	{
		Component[] componentes = vista.panelDeControles.getComponents();
		
		ArrayList<PanelControl> paneles = new ArrayList<PanelControl>();
		
		
		for(int i=0; i < opciones.size(); i++) 
			if(opciones.get(i).isSelected())
				paneles.add((PanelControl) componentes[i]);
		
		return paneles;
	}
	
	
	private boolean selecionoAlguno() 
	{
		for(JCheckBox opcion : opciones) 
			if(opcion.isSelected())
				return true;
		return false;
	}
	
	private boolean existe(File directorio) 
	{
		File archivo = new File(String.valueOf(directorio));
		
		if(archivo.exists())
			return true;
		return false;	
	}
}
