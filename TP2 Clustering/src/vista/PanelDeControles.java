package vista;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JPanel;
import controlador.PanelControl;

public class PanelDeControles extends JPanel
{
	private int y;
	
	public PanelDeControles() 
	{
		y = 0;
	}
	
	public void agregar(PanelControl panelControl)
	{
		panelControl.setBounds(0, y, this.getWidth(), 37);
		this.add(panelControl);
		
		y += panelControl.getHeight();
		
		if(this.getComponents().length > 11)
			this.setPreferredSize(new Dimension(this.getWidth(), this.getHeight() + panelControl.getHeight()));
	}
	
	public void eliminar(PanelControl panelControl) 
	{
		Component[] componentes = this.getComponents();
		
		if(panelControl == componentes[componentes.length-1]) 
		{
			this.remove(panelControl);
			y -= panelControl.getHeight();
		}	
		else 
		{
			this.remove(panelControl);
			componentes = this.getComponents();
			y = 0;
			for(int i=0; i < componentes.length; i++) 
			{
				if(i == 0 && componentes[i].getY() != 0)
				{
					componentes[i].setBounds(componentes[i].getX(), y, componentes[i].getWidth(), componentes[i].getHeight());
					y += componentes[i].getHeight();
				}
				else if(componentes[i].getY() != y)
				{
					componentes[i].setBounds(componentes[i].getX(), y, componentes[i].getWidth(), componentes[i].getHeight());
					y += componentes[i].getHeight();
				}
				else
					y += componentes[i].getHeight();
			}
		}
		
		if(this.getComponents().length >= 11)
			this.setPreferredSize(new Dimension(this.getWidth(), this.getHeight() - panelControl.getHeight()));
	}
	
}
