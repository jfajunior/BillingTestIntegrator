package br.com.gvt.integrator.action;


import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import br.com.gvt.integrator.utils.Resources;




/**
 * @author José Júnior
 *         GVT - 2013.07
 */
public class ExitAction extends AbstractAction {
	private static final long serialVersionUID = 4354719994622664427L;
	private final Logger _logger = Logger.getLogger(ExitAction.class);
	
	private JFrame _frame;
	private boolean _confirm;
	
	
	
	
	public ExitAction(JFrame frame, boolean confirm) {
		_frame = frame;
		_confirm = confirm;
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent event) {
		
		if (_confirm) {
			int option = JOptionPane.showConfirmDialog(
					_frame,
					Resources.INSTANCE.getString("confirm_exit.message"),
					Resources.INSTANCE.getString("confirm_exit.title"),
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE
			);
			
			if (option == JOptionPane.YES_OPTION) {
				_logger.info("END");
				_frame.dispatchEvent(new WindowEvent(_frame, WindowEvent.WINDOW_CLOSING));
				// System.exit(Frame.NORMAL);
			}
		}
		else {
			 _frame.dispose();
		}
	}
	
}
