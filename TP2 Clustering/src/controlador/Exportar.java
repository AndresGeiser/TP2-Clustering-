package controlador;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
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
import org.openstreetmap.gui.jmapviewer.Coordinate;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;


public class Exportar extends JDialog implements ActionListener
{
	private Vista vista;
	
	private JLabel lblNewLabel;
	private JButton btnCancelar;
	private JButton btnExportar;
	private JScrollPane scrollPane;
	private JPanel panel;
	
	private ArrayList<JCheckBox> opciones;
	
	public Exportar(Vista vista, boolean b) 
	{
		super(vista, b);
		
		this.setResizable(false);
		this.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 11));
		this.setBounds(100, 100, 500, 210);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(null);
	
		this.vista = vista;
		
		opciones = new ArrayList<JCheckBox>();
		
		iniComponentes();
		llenarPanel();
	}

	private void iniComponentes() 
	{
		lblNewLabel = new JLabel("Seleccione las coordenadas que quieres exportar:");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 13));
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setBounds(10, 11, 464, 14);
		getContentPane().add(lblNewLabel);
	
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 36, 464, 80);
		scrollPane.setBorder(null);
		getContentPane().add(scrollPane);
		
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		scrollPane.setViewportView(panel);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBorderPainted(false);
		btnCancelar.setBorder(null);
		btnCancelar.setFont(new Font("Arial", Font.PLAIN, 13));
		btnCancelar.setBackground(vista.rojo1);
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setFocusable(false);
		btnCancelar.setBounds(10, 127, 126, 31);
		getContentPane().add(btnCancelar);
		
		btnExportar = new JButton("Exportar");
		btnExportar.setBorderPainted(false);
		btnExportar.setBorder(null);
		btnExportar.setFont(new Font("Arial", Font.PLAIN, 13));
		btnExportar.setBackground(vista.rojo1);
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
				
				if(boton.isEnabled()) 
					boton.setBackground(vista.rojo2);
				
			}
		
			@Override
			public void mouseExited(MouseEvent e) 
			{
				JButton boton = (JButton) e.getSource();
				if(boton.isEnabled()) 
					boton.setBackground(vista.rojo1);
			
			}
		};
		btnExportar.addMouseListener(efectoHover);
		btnCancelar.addMouseListener(efectoHover);
		
		btnExportar.addActionListener(this);
		btnCancelar.addActionListener(this);
		
	}
	
	private void llenarPanel() 
	{
		Component[] componentes = vista.panelDeControles.getComponents(); //Obtenemos los paneles para obtener sus nombres y llenar el panel
		
		if(componentes.length > 0) 
		{
			JCheckBox checkBox;
			PanelControl panelControl;
			for(Component componente : componentes) 
			{
				panelControl = (PanelControl) componente;
				
				checkBox = new JCheckBox(panelControl.getNombre());
				checkBox.setToolTipText(checkBox.getText());
				checkBox.setPreferredSize(new Dimension(100,50));
				panel.add(checkBox);
				opciones.add(checkBox);
			}	
		} 	
	}

	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		if(ae.getSource() == btnExportar) 
		{	
			if(selecionoAlguno())
			{
				exportar();				
				this.dispose();				
			}
			else
			{	
				JOptionPane.showMessageDialog(null, "Selecione alguno !!", "Error", JOptionPane.WARNING_MESSAGE);
			}
		}
		
		if(ae.getSource() == btnCancelar)
			this.dispose();
		
	}
	
	private void exportar()
	{
		JFileChooser guardar = new JFileChooser();
		guardar.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		if(guardar.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
		{
			File ruta = guardar.getSelectedFile();
			
			try{
				crearArchivos(elegidos(), ruta);
			}catch(IOException ioe){ }
		}
	}
	
	private void crearArchivos(ArrayList<PanelControl> paneles, File ruta) throws IOException 
	{
		File archivo;
		FileWriter fw;
		BufferedWriter bw;
		
		for(PanelControl panel : paneles) 
		{
			archivo = new File(ruta + "\\" + panel.getNombre() + ".txt");
			
			if(!archivo.exists()) 
			{
				archivo.createNewFile();
			}
			
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
	
	private ArrayList<PanelControl> elegidos() 
	{
		ArrayList<PanelControl> paneles = new ArrayList<PanelControl>();
		
		PanelControl panelControl;
		for(JCheckBox seleccionado : seleccionados()) 
		{
			for(Component componente : vista.panelDeControles.getComponents())
			{
				panelControl = (PanelControl) componente;
				
				if(seleccionado.getText().equals(panelControl.getNombre()))
				{
					paneles.add(panelControl);
				}
			}
		}

		return paneles;
	}
	
	private ArrayList<JCheckBox> seleccionados()
	{
		ArrayList<JCheckBox> selecionados = new ArrayList<JCheckBox>();
	
		for(JCheckBox opcion : opciones) 
			if(opcion.isSelected())
				selecionados.add(opcion);
		
		return selecionados;
	}
	
	private boolean selecionoAlguno() 
	{
		for(JCheckBox opcion : opciones) 
			if(opcion.isSelected())
				return true;
		return false;
	}
}
