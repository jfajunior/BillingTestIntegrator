package br.com.gvt.integrator.ui.component;


import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.apache.log4j.Logger;

import br.com.gvt.integrator.action.AboutAction;
import br.com.gvt.integrator.action.ExitAction;
import br.com.gvt.integrator.action.FindAction;
import br.com.gvt.integrator.action.LookAndFeelAction;
import br.com.gvt.integrator.action.SimulateTransformRequestRandomAction;
import br.com.gvt.integrator.action.SimulateTransformRequestInjectiveFunctionAction;
import br.com.gvt.integrator.listener.ActionListenerCleanTextPane;
import br.com.gvt.integrator.listener.ActionListenerCopyToClipboard;
import br.com.gvt.integrator.listener.ActionListenerFormatXML;
import br.com.gvt.integrator.listener.ActionListenerPasteFromClipboard;
import br.com.gvt.integrator.listener.ActionListenerSaveDatabasesSettings;
import br.com.gvt.integrator.listener.ActionListenerSaveEnvironmentsSettings;
import br.com.gvt.integrator.utils.Resources;




/**
 * The main frame's menu bar.
 * 
 * @author José Júnior
 *         GVT - 2013.07
 */
public class MainFrameMenuBar extends JMenuBar {
	private static final long serialVersionUID = 8248875630482040634L;
	private final Logger _logger = Logger.getLogger(MainFrameMenuBar.class);
	
	
	
	
	/**
	 * Creates the frame menu bar.
	 * @param parent
	 *            The frame's menu parent.
	 */
	public MainFrameMenuBar(JFrame parent, TabPanelBilling panelTabBilling,
			TabPanelSettingsEnvironmentsPanel tabPanelSettingsEnvironmentsPanel,
			TabPanelSettingsDatabasesPanel tabPanelSettingsDatabasesPanel) {
		init(parent, panelTabBilling, tabPanelSettingsEnvironmentsPanel, tabPanelSettingsDatabasesPanel);
	}
	
	
	
	
	/**
	 * Initializes the frame's menu.
	 */
	private void init(JFrame parent, TabPanelBilling panelTabBilling,
			TabPanelSettingsEnvironmentsPanel tabPanelSettingsEnvironmentsPanel,
			TabPanelSettingsDatabasesPanel tabPanelSettingsDatabasesPanel) {
		_logger.info("Creating Frame Menu Bar....");
		
		// Get a reference to resource instance.
		Resources resources = Resources.INSTANCE;
		
		// *** File Menu ***//
		JMenu menuFile = createFileMenu(parent, resources, tabPanelSettingsEnvironmentsPanel, 
				tabPanelSettingsDatabasesPanel);
		
		// *** Edit Menu ***//
		JMenu menuEdit = createEditMenu(parent, resources, panelTabBilling);
		
		// *** View Menu ***//
		JMenu menuView = createViewMenu(parent, resources);
		
		// *** Tools Menu ***//
		JMenu menuTools = createToolsMenu(parent, resources, panelTabBilling);
		
		// *** Help Menu ***//
		JMenu menuHelp = createHelpMenu(parent, resources);
		
		// Add the menus to the menu bar.
		add(menuFile);
		add(menuEdit);
		add(menuView);
		add(menuTools);
		add(menuHelp);
		
		_logger.info("Frame Menu Bar successfully created");
	}
	
	
	
	
	/**
	 * Creates a generic menu item.
	 * @param label
	 *            The menu item label.
	 * @param mnemonic
	 *            A mnemonic for the shortcut.
	 * @param action
	 *            The menu action that will be executed by clicking the menu.
	 * @return A menu item.
	 */
	private JMenuItem createMenuItem(String label, char mnemonic, Action action) {
		
		JMenuItem mi = new JMenuItem(label, mnemonic);
		mi.addActionListener(action);
		if (action == null || !action.isEnabled()) {
			mi.setEnabled(false);
		}
		return mi;
	}
	
	
	
	
	private JMenu createFileMenu(JFrame parent, Resources resources,
			TabPanelSettingsEnvironmentsPanel tabPanelSettingsEnvironmentsPanel,
			TabPanelSettingsDatabasesPanel tabPanelSettingsDatabasesPanel) {
		
		// File Menu.
		JMenu menuFile = new JMenu(resources.getString("FileMenu.file_label"));
		menuFile.setMnemonic(resources.getMnemonic("FileMenu.file_mnemonic"));
		
		// Save environments.
		JMenuItem menuItemSaveConfigurations = new JMenuItem(resources.getString("FileMenu.save_configurations_label"));
		menuItemSaveConfigurations.setMnemonic(resources.getMnemonic("FileMenu.save_configurations_mnemonic"));
		menuItemSaveConfigurations.addActionListener(new ActionListenerSaveEnvironmentsSettings(
				tabPanelSettingsEnvironmentsPanel));
		menuItemSaveConfigurations.addActionListener(new ActionListenerSaveDatabasesSettings(
				tabPanelSettingsDatabasesPanel));
		menuFile.add(menuItemSaveConfigurations);
		
		// Exit.
		JMenuItem menuItemExit = new JMenuItem(resources.getString("FileMenu.exit_label"));
		menuItemExit.setMnemonic(resources.getMnemonic("FileMenu.exit_mnemonic"));
		menuItemExit.addActionListener(new ExitAction(parent, true));
		menuFile.add(menuItemExit);
		
		_logger.debug("File menu created.");
		
		return menuFile;
	}
	
	
	
	
	private JMenu createMenuActions(JFrame parent, Resources resources, String menuLabel, char menuMnemonic,
			TabPanelBillingInfoPanel tabPanelBillingInfoPanel) {
		
		// Copy.
		JMenuItem menuItemCopy = new JMenuItem(resources.getString("EditMenu.copy_label"));
		menuItemCopy.setMnemonic(resources.getMnemonic("EditMenu.copy_mnemonic"));
		menuItemCopy.addActionListener(new ActionListenerCopyToClipboard(tabPanelBillingInfoPanel.getTabbedPane()));
		
		// Paste.
		JMenuItem menuItemPaste = new JMenuItem(resources.getString("EditMenu.paste_label"));
		menuItemPaste.setMnemonic(resources.getMnemonic("EditMenu.paste_mnemonic"));
		menuItemPaste.addActionListener(new ActionListenerPasteFromClipboard(tabPanelBillingInfoPanel.getTabbedPane()));
		
		// Clean.
		JMenuItem menuItemClean = new JMenuItem(resources.getString("EditMenu.clean_label"));
		menuItemClean.setMnemonic(resources.getMnemonic("EditMenu.clean_mnemonic"));
		menuItemClean.addActionListener(new ActionListenerCleanTextPane(tabPanelBillingInfoPanel.getTabbedPane()));
		
		// Format.
		JMenuItem menuItemFormat = new JMenuItem(resources.getString("EditMenu.format_label"));
		menuItemFormat.setMnemonic(resources.getMnemonic("EditMenu.format_mnemonic"));
		menuItemFormat.addActionListener(new ActionListenerFormatXML(tabPanelBillingInfoPanel.getTabbedPane()));
		
		// Find.
		JMenuItem menuItemFind = new JMenuItem(resources.getString("EditMenu.find_label"));
		menuItemFind.setMnemonic(resources.getMnemonic("EditMenu.find_mnemonic"));
		menuItemFind.addActionListener(new FindAction(parent, tabPanelBillingInfoPanel));
		
		// Menu.
		JMenu jMenu = new JMenu(menuLabel);
		jMenu.setMnemonic(menuMnemonic);
		
		// Add action to menu.
		jMenu.add(menuItemCopy);
		jMenu.add(menuItemPaste);
		jMenu.add(menuItemClean);
		jMenu.add(menuItemFormat);
		jMenu.add(menuItemFind);
		
		return jMenu;
	}
	
	
	
	
	private JMenu createEditMenu(JFrame parent, Resources resources, TabPanelBilling panelTabBilling) {
		// Edit Menu.
		JMenu menuEdit = new JMenu(resources.getString("EditMenu.edit_label"));
		menuEdit.setMnemonic(resources.getMnemonic("EditMenu.edit_mnemonic"));
		
		// Billing actions.
		menuEdit.add(createMenuActions(parent, resources, resources.getString("EditMenu.billing_label"),
				resources.getMnemonic("EditMenu.billing_mnemonic"), panelTabBilling.getPanelBilling()));
		
		// Transform actions.
		menuEdit.add(createMenuActions(parent, resources, resources.getString("EditMenu.transform_label"),
				resources.getMnemonic("EditMenu.transform_mnemonic"), panelTabBilling.getPanelTransform()));
		
		// Kenan actions.
		menuEdit.add(createMenuActions(parent, resources, resources.getString("EditMenu.kenan_label"),
				resources.getMnemonic("EditMenu.kenan_mnemonic"), panelTabBilling.getPanelKenan()));
		
		_logger.debug("Edit menu created.");
		
		return menuEdit;
	}
	
	
	
	
	private JMenu createViewMenu(JFrame parent, Resources resources) {
		// View Menu.
		JMenu menuView = new JMenu(resources.getString("ViewMenu.view_label"));
		menuView.setMnemonic(resources.getMnemonic("ViewMenu.view_mnemonic"));
		
		// Look and Feel submenu.
		JMenu lookAndFeelMenu = new JMenu(resources.getString("ViewMenu.laf_label"));
		lookAndFeelMenu.setMnemonic(resources.getMnemonic("ViewMenu.laf_mnemonic"));
		
		// Get all available look and feel.
		LookAndFeelInfo[] availableLAF = UIManager.getInstalledLookAndFeels();
		
		// Create a new menu item for each available look and feel.
		String lookAndFeelName;
		for (LookAndFeelInfo lafInfo : availableLAF) {
			lookAndFeelName = lafInfo.getName();
			lookAndFeelMenu.add(createMenuItem(lookAndFeelName, lookAndFeelName.charAt(0), new LookAndFeelAction(
					parent, lafInfo.getClassName())));
		}
		menuView.add(lookAndFeelMenu);
		
		_logger.debug("View menu created.");
		
		return menuView;
	}
	
	
	
	
	private JMenu createToolsMenu(JFrame parent, Resources resources, TabPanelBilling panelTabBilling) {
		// Tools Menu.
		JMenu menuTools = new JMenu(resources.getString("ToolsMenu.tools_label"));
		menuTools.setMnemonic(resources.getMnemonic("ToolsMenu.tools_mnemonic"));
		
		// Simulate new client for transformer.
		JMenuItem menuItemSimulate = new JMenuItem(resources.getString("ToolsMenu.simulate_label"));
		menuItemSimulate.setMnemonic(resources.getMnemonic("ToolsMenu.simulate_mnemonic"));
		menuItemSimulate.addActionListener(new SimulateTransformRequestRandomAction(parent, panelTabBilling));
		menuTools.add(menuItemSimulate);
		
		// Simulate new client for transformer with injective function.
		JMenuItem menuItemSimulateWithInjectiveFunction =
				new JMenuItem(resources.getString("ToolsMenu.simulate_injective_label"));
		
		menuItemSimulateWithInjectiveFunction.setMnemonic(
				resources.getMnemonic("ToolsMenu.simulate_mnemonic_injective"));
		
		menuItemSimulateWithInjectiveFunction.addActionListener(
				new SimulateTransformRequestInjectiveFunctionAction(parent, panelTabBilling));
		
		menuTools.add(menuItemSimulateWithInjectiveFunction);
		
		_logger.debug("Tools menu created.");
		
		return menuTools;
	}
	
	
	
	
	private JMenu createHelpMenu(JFrame parent, Resources resources) {
		// Help Menu.
		JMenu menuHelp = new JMenu(resources.getString("HelpMenu.help_label"));
		menuHelp.setMnemonic(resources.getMnemonic("HelpMenu.help_mnemonic"));
		
		// About.
		JMenuItem menuItem_about = new JMenuItem(resources.getString("HelpMenu.about_label"));
		menuItem_about.setMnemonic(resources.getMnemonic("HelpMenu.about_mnemonic"));
		menuItem_about.addActionListener(new AboutAction(parent));
		menuHelp.add(menuItem_about);
		
		_logger.debug("Help menu created.");
		
		return menuHelp;
	}
	
}
