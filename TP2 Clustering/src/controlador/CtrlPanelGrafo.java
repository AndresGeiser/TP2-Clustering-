package controlador;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

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
		this.panelGrafo.txtCantClusters.setToolTipText("Max: " + modelo.coordenadas().size());
		
		Random n = new Random();
		int r = n.nextInt(256);
		int g = n.nextInt(256);
		int b = n.nextInt(256);
		colorGrafo = new Color(r, g, b);
		
		puntos = new ArrayList<MapMarkerDot>();
		aristas = new ArrayList<MapPolygonImpl>();
		
		this.panelGrafo.boxVisibilidad.addActionListener(this);
		this.panelGrafo.btnCentrar.addActionListener(this);
		this.panelGrafo.btnEliminar.addActionListener(this);
		this.panelGrafo.btnClustering.addActionListener(this);
		this.panelGrafo.btnEstadisticas.addActionListener(this);	
	}
	
	public void iniciar() 
	{
		modelo.armarGrafo();
		
		dibujarPuntos();
		dibujarAristas();
		
		vista.panelDeControles.agregar(panelGrafo);
		vista.panelDeControles.updateUI();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == panelGrafo.boxVisibilidad)
			activarODesactivar();
		
		if(e.getSource() == panelGrafo.btnCentrar)
			centrarEnElMapa();
		
		if(e.getSource() == panelGrafo.btnEliminar)
			eliminar();
		
		if(e.getSource() == panelGrafo.btnClustering)
			clustering();
		
		if(e.getSource() == panelGrafo.btnEstadisticas)
			verEstadisticas();
		
	}
	
	private void activarODesactivar() 
	{
		if(panelGrafo.boxVisibilidad.isSelected())
			visibilidad(true);	
		else 
			visibilidad(false);
	}
	
	private void centrarEnElMapa() 
	{
		vista.mapa.setDisplayPosition(modelo.coordenadas().get(0), 13);
	}
	
	//Metodo que elimina el grafo 
	private void eliminar() 
	{
		borrarPuntosDelMapa();
		borrarAristasDelMapa();

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
	
	private void clustering() 
	{
		int n = Integer.parseInt(panelGrafo.txtCantClusters.getText());
		
		if(esValido(n))
		{		
			modelo.clustering(n);
			
			borrarAristasDelMapa();	//Las borramos para
			
			dibujarAristas();		//dibujar las aristas que quedaron despues del clustering
		}
	}
	
	private void verEstadisticas()
	{
		StringBuilder stats = new StringBuilder("");
		
		stats.append("Cantidad de vertices: " + modelo.cantVertices() + "\n");
		stats.append("Cantidad de Clusters: " + modelo.cantClusters() + "\n");
		stats.append("Peso total de aristas: " + modelo.pesoTotal() + "\n");
		stats.append("Desviacion Estandar ~ " + modelo.desviacionEstandar());
		
		JOptionPane.showMessageDialog(null, stats.toString(), "Estadisticas", JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void visibilidad(boolean b) 
	{
		for(MapMarkerDot punto : puntos)
			punto.setVisible(b);
		
		for(MapPolygonImpl arista : aristas)
			arista.setVisible(b);
		
		if(b == true)
			panelGrafo.boxVisibilidad.setToolTipText("Ocultar");
		else
			panelGrafo.boxVisibilidad.setToolTipText("Aparecer");
		
		vista.mapa.updateUI();
	}
	
	private void borrarPuntosDelMapa() 
	{
		for(MapMarkerDot punto : puntos)
			vista.mapa.removeMapMarker(punto);
		puntos.clear();
	}
	private void borrarAristasDelMapa() 
	{
		for(MapPolygonImpl arista : aristas) 
			vista.mapa.removeMapPolygon(arista);
		aristas.clear();
	}
	
	private void dibujarPuntos() 
	{
		MapMarkerDot punto;
		for(Coordinate coordenada : modelo.coordenadas()) 
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

		for(int i=0; i < modelo.coordenadas().size(); i++) 
		{
			origen = modelo.coordenadas().get(i);
				
			for(Integer j : modelo.getGrafo().vecinos(i))
			{						
				destino = modelo.coordenadas().get(j);
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
	
	public ArrayList<Coordinate> coordenadas()
	{
		return modelo.coordenadas();
	}

	
	//METODOS AUXILIARES
	
	private boolean esValido(int n) 
	{	
		if(n > modelo.cantVertices())
		{
			JOptionPane.showMessageDialog(null, "Excediste el maximo de clusters.", "Error", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(n == 0)
		{
			JOptionPane.showMessageDialog(null, "No puede haber 0 clusters.", "Error", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		return true;		
	}
}
