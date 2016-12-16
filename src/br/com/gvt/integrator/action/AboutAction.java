package br.com.gvt.integrator.action;


import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import br.com.gvt.integrator.utils.Constants;
import br.com.gvt.integrator.utils.Resources;




/**
 * @author José Júnior GVT - 2013.07
 */
public class AboutAction extends AbstractAction {
	private static final long serialVersionUID = 4354719994622664427L;
	
	private JFrame _mainFrame;
	
	
	
	
	public AboutAction(JFrame mainFrame) {
		_mainFrame = mainFrame;
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent event) {
		ImageIcon iconBti = new ImageIcon(getClass().getResource(Constants.PATH_IMAGE_BTI_LOGO));
		JOptionPane.showMessageDialog(_mainFrame, Resources.INSTANCE.getString("AboutDialog.message"),
				Resources.INSTANCE.getString("AboutDialog.title"), JOptionPane.INFORMATION_MESSAGE, iconBti);
	}
	
}
