package br.com.gvt.integrator;


import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import br.com.gvt.integrator.ui.component.MainFrame;




// TODO Criar opção para criação de produto, inserindo campos no XML sem que se tenha que editar este.
// TODO Fazer documentação da versão 5 - Após alterações.  
// TODO I18N dinâmico no menu (SwingUtilities.updateComponentTreeUI(_mainFrame);), guardado nas settings.
// TODO Drag and drop.
// TODO Settings: Save default LNF.
// TODO Auditoria: Username = System.getProperty("user.name");
// TODO Distribuir a aplicação num único JAR.
/**
 * @author José Júnior
 *         GVT - 2013.07
 */
public class Integrator {
	final static Logger _logger = Logger.getLogger(Integrator.class);
	
	
	
	
	public static void main(String[] args) {
		
		_logger.info("START");
		
		// TODO Remove: just for testing purposes only!
		// Resources.INSTANCE.setLocale(new Locale("en", "US"));
		// Resources.INSTANCE.setLocale(new Locale("pt", "PT"));
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					// Set System L&F.
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					
					// Give some time for the splash screen.
					Thread.sleep(6000);
					
					// Create and show the main frame.
					MainFrame frame = new MainFrame();
					frame.pack();
					frame.setVisible(true);
				}
				catch (Exception e) {
					_logger.error("Exception on main thread: " + e.getMessage());
					e.printStackTrace();
				}
			}
		});
		
		_logger.debug("Main frame executing on its own thread.");
		_logger.info("END");
	}
	
	
}
