package vista;

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
		y += 37;
	}
	
}
