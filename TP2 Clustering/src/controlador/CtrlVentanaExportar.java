package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import vista.VentanaExportar;

public class CtrlVentanaExportar implements ActionListener
{
	private VentanaExportar vExportar;
	
	private ArrayList<CtrlPanelGrafo> controladoresGrafos;
	
	public CtrlVentanaExportar(VentanaExportar vExportar, ArrayList<CtrlPanelGrafo> controladoresGrafos)
	{
		this.vExportar = vExportar;
		this.controladoresGrafos = controladoresGrafos;
		
		this.vExportar.btnExportar.addActionListener(this);
		this.vExportar.btnCancelar.addActionListener(this);
	}

	public void iniciar() 
	{
		vExportar.setVisible(true);
	}
		
	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		if(ae.getSource() == this.vExportar.btnExportar) 
		{	
			if(selecionoAlguno())
				elegirDirectorio();
			
			else	
				JOptionPane.showMessageDialog(null, "Seleccione alguno !!", "Atencion", JOptionPane.WARNING_MESSAGE);
			
		}
		
		if(ae.getSource() == vExportar.btnCancelar)
			vExportar.dispose();
		
	}

	private void elegirDirectorio() 
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
				vExportar.dispose();
			}
			else
				JOptionPane.showMessageDialog(null, "El directorio " + directorio + " no existe.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void crearArchivos(File directorio) 
	{
		try 
		{	
			File archivo;
			FileWriter fw;
			BufferedWriter bw;
			
			for(CtrlPanelGrafo panel : panelesElegidos()) 
			{
				archivo = new File(directorio + "\\" + panel.getNombre() + ".txt");
				
				int i=1;
				while(archivo.exists())
				{
					archivo = new File(directorio + "\\" + panel.getNombre() + " ("+ i +")" + ".txt");
					i++;
				}
			
				archivo.createNewFile();
				
				fw = new FileWriter(archivo);
				bw = new BufferedWriter(fw);
				
				for(Coordinate coordenada : panel.coordenadas()) 
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
	
	private ArrayList<CtrlPanelGrafo> panelesElegidos() 
	{
		ArrayList<CtrlPanelGrafo> paneles = new ArrayList<CtrlPanelGrafo>();

		for(int i=0; i < vExportar.opciones.size(); i++) 
			if(vExportar.opciones.get(i).isSelected())
				paneles.add(controladoresGrafos.get(i));
		
		return paneles;
	}
	
	
	private boolean selecionoAlguno() 
	{
		for(JCheckBox opcion : vExportar.opciones) 
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

