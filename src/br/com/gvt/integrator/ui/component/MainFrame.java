package br.com.gvt.integrator.ui.component;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.apache.log4j.Logger;

import br.com.gvt.integrator.utils.Constants;
import br.com.gvt.integrator.utils.Resources;




/**
 * The application main frame, where everything is draw into.
 * 
 * @author José Júnior
 *         GVT - 2013.07
 */
public class MainFrame extends JFrame {
	private static final long serialVersionUID = -9112614828440806504L;
	private final Logger _logger = Logger.getLogger(MainFrame.class);
	
	// Tab panels and menus.
	private TabPanelBilling _panelTabBilling;
	private TabPanelSettingsEnvironmentsPanel _tabPanelSettingsEnvironmentsPanel;
	private TabPanelSettingsDatabasesPanel _tabPanelSettingsDatabasesPanel;
	
	
	
	
	/**
	 * Creates the main window frame.
	 */
	public MainFrame() {
		setPreferredSize(new Dimension(900, 520));
		setTitle(Resources.INSTANCE.getString("Title"));
		setIconImage(new ImageIcon(getClass().getResource(Constants.PATH_IMAGE_BTI)).getImage());
		
		init();
	}
	
	
	
	
	private void init() {
		_logger.info("Initializing....");
		
		// Set its general properties.
		setBounds(100, 100, 900, 520);
		setMinimumSize(new Dimension(900, 520));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// This will center the JFrame in the middle of the screen.
		setLocationRelativeTo(null);
		
		// Create the main panel.
		JPanel panelMain = createMainPanel();
		_logger.debug("Main panel created.");
		
		// Create the environments settings panel.
		_tabPanelSettingsEnvironmentsPanel = new TabPanelSettingsEnvironmentsPanel();
		_logger.debug("Environment settings panel created.");
		
		// Create the jdbcs settings panel.
		_tabPanelSettingsDatabasesPanel = new TabPanelSettingsDatabasesPanel();
		_logger.debug("JDBC settings panel created.");
		
		// Create the billing panel.
		_panelTabBilling = new TabPanelBilling(this, _tabPanelSettingsEnvironmentsPanel, 
				_tabPanelSettingsDatabasesPanel);
		_logger.debug("Billing panel created.");
		
		// Create the tabbed pane with 2 tabs: Billing and Settings.
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab(Resources.INSTANCE.getString("billing"), null, _panelTabBilling);
		tabbedPane.addTab(Resources.INSTANCE.getString("environments"), null, _tabPanelSettingsEnvironmentsPanel);
		tabbedPane.addTab(Resources.INSTANCE.getString("databases"), null, _tabPanelSettingsDatabasesPanel);
		
		// Create and set the frame menu bar.
		setJMenuBar(new MainFrameMenuBar(this, _panelTabBilling, _tabPanelSettingsEnvironmentsPanel, 
				_tabPanelSettingsDatabasesPanel));
		
		_logger.debug("Menu bar created.");
		
		// Finally, add the tabbed pane to the main window.
		panelMain.add(tabbedPane, BorderLayout.CENTER);
		_logger.info("Finished main frame initialization.");
	}
	
	
	
	
	private JPanel createMainPanel() {
		JPanel panelMain = new JPanel();
		panelMain.setAlignmentY(Component.TOP_ALIGNMENT);
		panelMain.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelMain.setLayout(new BorderLayout(0, 0));
		
		getContentPane().add(panelMain, BorderLayout.CENTER);
		
		return panelMain;
	}
	
	
}
