package controlador;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;

import modelo.Modelo;
import vista.PanelGrafo;
import vista.VentanaPrincipal;

public class CtrlPanelGrafo implements ActionListener 
{
	private Modelo modelo;
	private VentanaPrincipal vista;
	private PanelGrafo panelGrafo;
	
	private ArrayList<MapMarkerDot> puntos;
	private ArrayList<MapPolygonImpl> aristas;
	
	private Color colorGrafo;
	
	public CtrlPanelGrafo(Modelo modelo, VentanaPrincipal vista, PanelGrafo panelGrafo)
	{
		this.modelo = modelo;
		this.vista = vista;
		this.panelGrafo = panelGrafo;
		
		puntos = new ArrayList<MapMarkerDot>();
		aristas = new ArrayList<MapPolygonImpl>();
		
		int r = (int)(Math.random()*256);
		int g = (int)(Math.random()*256);
		int b = (int)(Math.random()*256);
		colorGrafo = new Color(r, g, b);
		
		dibujarPuntos();
		dibujarAristas();
		
		panelGrafo.boxVisibilidad.addActionListener(this);
		panelGrafo.btnCentrar.addActionListener(this);
		panelGrafo.btnEliminar.addActionListener(this);
		panelGrafo.btnClustering.addActionListener(this);
		panelGrafo.btnEstadisticas.addActionListener(this);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == panelGrafo.boxVisibilidad)
			activarODesactivar();
		
		if(e.getSource() == panelGrafo.btnCentrar)
			centrar();
		
		if(e.getSource() == panelGrafo.btnEliminar)
			eliminar();
		
		if(e.getSource() == panelGrafo.btnClustering)
			clustering();
		
		if(e.getSource() == panelGrafo.btnEstadisticas)
			verEstadisticas();
		
	}
	
	private void clustering() 
	{
		if (!panelGrafo.txtCantClusters.getText().equals(""))
		{
			if(Integer.parseInt(panelGrafo.txtCantClusters.getText()) > modelo.getCoordenadas().size())
				JOptionPane.showMessageDialog(null, "Excediste el maximo de clusters.", "Error", JOptionPane.WARNING_MESSAGE);
			
			else if(Integer.parseInt(panelGrafo.txtCantClusters.getText()) == 0)
				JOptionPane.showMessageDialog(null, "No puede haber 0 clusters.", "Error", JOptionPane.WARNING_MESSAGE);
			
			else
			{
				modelo.clustering(Integer.parseInt(panelGrafo.txtCantClusters.getText()));
				
				for(MapPolygonImpl poligono : aristas) 
					vista.mapa.removeMapPolygon(poligono);
				aristas.clear();
				
				dibujarAristas();
			}
		}
	}
	
	//Metodo que desactiva o activa la visibilidad del grafo en el mapa
	private void activarODesactivar() 
	{
		if(panelGrafo.boxVisibilidad.isSelected() == true)
		{
			for(MapMarkerDot punto : puntos)
				punto.setVisible(true);
			
			for(MapPolygonImpl arista : aristas)
				arista.setVisible(true);
			
			panelGrafo.boxVisibilidad.setToolTipText("Ocultar");
		}
		else 
		{
			for(MapMarkerDot punto : puntos)
				punto.setVisible(false);
			
			for(MapPolygonImpl arista : aristas)
				arista.setVisible(false);
			
			panelGrafo.boxVisibilidad.setToolTipText("Aparecer");
		}
		
		vista.mapa.updateUI();
	}
	
	//Metodo que elimina el grafo 
	private void eliminar() 
	{
		for(MapMarkerDot punto : puntos)
			vista.mapa.removeMapMarker(punto);
		
		for(MapPolygonImpl arista : aristas) 
			vista.mapa.removeMapPolygon(arista);

		vista.panelDeControles.eliminar(panelGrafo);
		vista.mapa.updateUI();
		vista.panelDeControles.updateUI();
		
		//Desactivamos el boton exportar en caso de eliminar todos los grafos
		if(vista.panelDeControles.getComponents().length == 0)
		{
			vista.btnExportar.setEnabled(false);
			vista.btnExportar.setForeground(Color.GRAY);
		}
	}
	
	//Metodo que posiciona la ubicacion del mapa en un punto del grafo
	private void centrar() 
	{
		vista.mapa.setDisplayPosition(modelo.getCoordenadas().get(0), 13);
	}
	
	private void verEstadisticas()
	{
		StringBuilder stats = new StringBuilder("");
		stats.append("Cantidad de vertices: " + modelo.cantVertices() + "\n");
		stats.append("Cantidad de Clusters: " + panelGrafo.txtCantClusters.getText() + "\n");
		stats.append("Peso total de aristas: " + modelo.getPesoTotal() + "\n");
		stats.append("Desviacion Estandar ~ " + modelo.getDesviacionEstandar());
		
		
		JOptionPane.showMessageDialog(null, stats.toString(), "Estadisticas", JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void dibujarPuntos() 
	{
		MapMarkerDot punto;
		for(Coordinate coordenada : modelo.getCoordenadas()) 
		{
			punto = new MapMarkerDot(coordenada);
			punto.setBackColor(colorGrafo);
			vista.mapa.addMapMarker(punto);
			puntos.add(punto);
		}
	}
	
	private void dibujarAristas() 
	{
		Coordinate origen;
		Coordinate destino;
		MapPolygonImpl poligono;

		for(int i=0; i < modelo.getCoordenadas().size(); i++) 
		{
			origen = modelo.getCoordenadas().get(i);
				
			for(Integer j : modelo.getGrafo().vecinos(i)) /*Recorremos sus vecinos*/
			{						
				destino = modelo.getCoordenadas().get(j);
				poligono = new MapPolygonImpl(Arrays.asList(origen, destino, destino));//El poligono necesita 3 puntos para dibujarse
				poligono.setColor(colorGrafo.darker());
			    vista.mapa.addMapPolygon(poligono);                     
			    aristas.add(poligono);		
			}
		}
		
	}
	
	public String getNombre() 
	{
		return panelGrafo.lblNombre.getText();
	}
	
	public ArrayList<Coordinate> getCoordenadas()
	{
		return modelo.getCoordenadas();
	}

}
