package br.com.gvt.integrator.ui.component;


import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import br.com.gvt.integrator.action.FindAction;
import br.com.gvt.integrator.listener.ActionListenerCallGetBillingInfo;
import br.com.gvt.integrator.listener.ActionListenerCallKenan;
import br.com.gvt.integrator.listener.ActionListenerCallTransform;
import br.com.gvt.integrator.listener.ActionListenerGenerateBillingRequest;
import br.com.gvt.integrator.listener.ActionListenerGenerateKenanRequest;
import br.com.gvt.integrator.listener.ActionListenerGenerateTransformRequest;
import br.com.gvt.integrator.listener.ActionListenerUnlockAccount;
import br.com.gvt.integrator.listener.ChangeListenerEnableDisableButtonForEditableTab;




/**
 * @author José Júnior
 *         GVT - 2013.07
 */
public class TabPanelBilling extends JPanel {
	private static final long serialVersionUID = 6043181627025708355L;
	
	private final Logger _logger = Logger.getLogger(TabPanelBilling.class);
	
	private JFrame _parent;
	
	private TabPanelBillingToolBar _menuPanelTabBilling;
	
	private TabPanelBillingInfoPanel _panelBilling;
	private TabPanelBillingInfoPanel _panelTransform;
	private TabPanelBillingInfoPanel _panelKenan;
	
	private TabPanelSettingsEnvironmentsPanel _tabPanelSettingsEnvironments;
	private TabPanelSettingsDatabasesPanel _tabPanelSettingsDatabases;
	
	private static final int DIVIDER_SIZE = 4;
	
	public static final String ACTION_BILLING_INFO = "Billing Info";
	public static final String ACTION_TRANSFORM = "Transform";
	public static final String ACTION_CALL_KENAN = "Call Kenan";
	
	
	
	
	public TabPanelBilling(JFrame parent, TabPanelSettingsEnvironmentsPanel tabPanelSettingsEnvironments,
			TabPanelSettingsDatabasesPanel tabPanelSettingsDatabases) {
		_parent = parent;
		_tabPanelSettingsEnvironments = tabPanelSettingsEnvironments;
		_tabPanelSettingsDatabases = tabPanelSettingsDatabases;
		init();
	}
	
	
	
	
	private void init() {
		_logger.info("Initializing....");
		
		// Create the menu panel.
		_menuPanelTabBilling = new TabPanelBillingToolBar(_parent, this);
		
		// Create the billing panel.
		setLayout(new BorderLayout());
		add(_menuPanelTabBilling, BorderLayout.NORTH);
		add(createBillingTabPanelSplitPanes(), BorderLayout.CENTER);
		
		_logger.info("Finished panel initialization.");
	}
	
	
	
	
	private JSplitPane createBillingTabPanelSplitPanes() {
		// Create the left part of the split pane.
		JSplitPane splitPaneMain = new JSplitPane();
		splitPaneMain.setDividerSize(DIVIDER_SIZE);
		splitPaneMain.setResizeWeight(0.3333);
		
		// Create the right part of the split pane.
		JSplitPane splitPaneRight = new JSplitPane();
		splitPaneRight.setDividerSize(DIVIDER_SIZE);
		splitPaneRight.setResizeWeight(0.5);
		
		// The right component of the left split pane is the right split pane.
		splitPaneMain.setRightComponent(splitPaneRight);
		
		// Create the info panels.
		_panelBilling = createBillingInfoPanel();
		_panelTransform = createTransformPanel();
		_panelKenan = createKenanPanel();
		
		// This way we will have the following panels, in order, from left to right:
		splitPaneMain.setLeftComponent(_panelBilling);
		splitPaneRight.setLeftComponent(_panelTransform);
		splitPaneRight.setRightComponent(_panelKenan);
		
		// Capture "Ctrl + F" to run find action.
		final String ACTION_MAP_KEY_FIND = "KEY_FIND";
		KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_F, Event.CTRL_MASK);

		_panelBilling.getTextPaneRequest().getInputMap().put(keyStroke, ACTION_MAP_KEY_FIND);
		_panelBilling.getTextPaneRequest().getActionMap().put(ACTION_MAP_KEY_FIND, new FindAction(_parent, _panelBilling));
		_panelBilling.getTextPaneResponse().getInputMap().put(keyStroke, ACTION_MAP_KEY_FIND);
		_panelBilling.getTextPaneResponse().getActionMap().put(ACTION_MAP_KEY_FIND, new FindAction(_parent, _panelBilling));
		_panelBilling.getTextPaneInfo().getInputMap().put(keyStroke, ACTION_MAP_KEY_FIND);
		_panelBilling.getTextPaneInfo().getActionMap().put(ACTION_MAP_KEY_FIND, new FindAction(_parent, _panelBilling));
		
		_panelTransform.getTextPaneRequest().getInputMap().put(keyStroke, ACTION_MAP_KEY_FIND);
		_panelTransform.getTextPaneRequest().getActionMap().put(ACTION_MAP_KEY_FIND, new FindAction(_parent, _panelTransform));
		_panelTransform.getTextPaneResponse().getInputMap().put(keyStroke, ACTION_MAP_KEY_FIND);
		_panelTransform.getTextPaneResponse().getActionMap().put(ACTION_MAP_KEY_FIND, new FindAction(_parent, _panelTransform));
		_panelTransform.getTextPaneInfo().getInputMap().put(keyStroke, ACTION_MAP_KEY_FIND);
		_panelTransform.getTextPaneInfo().getActionMap().put(ACTION_MAP_KEY_FIND, new FindAction(_parent, _panelTransform));
		
		_panelKenan.getTextPaneRequest().getInputMap().put(keyStroke, ACTION_MAP_KEY_FIND);
		_panelKenan.getTextPaneRequest().getActionMap().put(ACTION_MAP_KEY_FIND, new FindAction(_parent, _panelKenan));
		_panelKenan.getTextPaneResponse().getInputMap().put(keyStroke, ACTION_MAP_KEY_FIND);
		_panelKenan.getTextPaneResponse().getActionMap().put(ACTION_MAP_KEY_FIND, new FindAction(_parent, _panelKenan));
		_panelKenan.getTextPaneInfo().getInputMap().put(keyStroke, ACTION_MAP_KEY_FIND);
		_panelKenan.getTextPaneInfo().getActionMap().put(ACTION_MAP_KEY_FIND, new FindAction(_parent, _panelKenan));
		
		return splitPaneMain;
	}
	
	
	
	
	private TabPanelBillingInfoPanel createBillingInfoPanel() {
		TabPanelBillingInfoPanel infoPanel = new TabPanelBillingInfoPanel(ACTION_BILLING_INFO,
				_menuPanelTabBilling.getTextPaneOrderId());
		
		// Listener to get billing info.
		ActionListener actionListener =
				new ActionListenerCallGetBillingInfo(_parent, infoPanel, _tabPanelSettingsEnvironments);
		
		// Listener to generate billing request.
		ActionListener actionListenerGenerateRequest = new ActionListenerGenerateBillingRequest(_parent,
				infoPanel, _menuPanelTabBilling.getTextPaneOrderId());
		
		// Add listeners.
		addGenericListeners(infoPanel, actionListener, actionListenerGenerateRequest);
		
		return infoPanel;
	}
	
	
	
	
	private TabPanelBillingInfoPanel createTransformPanel() {
		TabPanelBillingInfoPanel infoPanel = new TabPanelBillingInfoPanel(ACTION_TRANSFORM,
				_panelBilling.getTextPaneResponse());
		
		// Listener to call transform.
		ActionListener actionListener =
				new ActionListenerCallTransform(_parent, infoPanel, _tabPanelSettingsEnvironments);
		
		// Listener to generate transform request.
		ActionListener actionListenerGenerateRequest = new ActionListenerGenerateTransformRequest(_parent,
				infoPanel, _panelBilling.getTextPaneResponse());
		
		// Add listeners.
		addGenericListeners(infoPanel, actionListener, actionListenerGenerateRequest);
		
		// Add specific Transform listener to unlock account.
		infoPanel.getInfoPanelBottom().getButtonUnlockAccount().addActionListener(
				new ActionListenerUnlockAccount(_parent, infoPanel, _tabPanelSettingsDatabases));
		
		return infoPanel;
	}
	
	
	
	
	private TabPanelBillingInfoPanel createKenanPanel() {
		TabPanelBillingInfoPanel infoPanel = new TabPanelBillingInfoPanel(ACTION_CALL_KENAN,
				_panelTransform.getTextPaneResponse());
		
		// Listener to call kenan.
		ActionListener actionListener = new ActionListenerCallKenan(_parent, infoPanel, _tabPanelSettingsEnvironments);
		
		// Listener to generate kenan request.
		ActionListener actionListenerGenerateRequest = new ActionListenerGenerateKenanRequest(_parent,
				infoPanel, _panelTransform);
		
		// Add listeners.
		addGenericListeners(infoPanel, actionListener, actionListenerGenerateRequest);
		
		return infoPanel;
	}
	
	
	
	
	private void addGenericListeners(TabPanelBillingInfoPanel infoPanel, ActionListener actionListener,
			ActionListener actionListenerGenerateRequest) {
		
		// Listener to enable/disable paste button.
		ChangeListener changeListenerEnableDisablePaste =
				new ChangeListenerEnableDisableButtonForEditableTab(infoPanel,
						infoPanel.getInfoPanelBottom().getButtonPaste());
		
		
		// Listener to enable/disable format button.
		ChangeListener changeListenerEnableDisableFormat =
				new ChangeListenerEnableDisableButtonForEditableTab(infoPanel,
						infoPanel.getInfoPanelBottom().getButtonFormat());
		
		// Add listeners.
		infoPanel.getInfoPanelBottom().getButtonAction().addActionListener(actionListener);
		infoPanel.getInfoPanelBottom().getButtonGenerate().addActionListener(actionListenerGenerateRequest);
		infoPanel.getTabbedPane().addChangeListener(changeListenerEnableDisablePaste);
		infoPanel.getTabbedPane().addChangeListener(changeListenerEnableDisableFormat);
	}
	
	
	
	
	public TabPanelBillingInfoPanel getFocusedTabPanelBillingInfoPanel() {
		if (_panelKenan.isFocusOwner()) {
			return _panelKenan;
		}
		else if (_panelTransform.isFocusOwner()) {
			return _panelTransform;
		}
		else {
			return _panelBilling;
		}
	}
	
	
	
	
	public TabPanelBillingInfoPanel getPanelBilling() {
		return _panelBilling;
	}
	
	
	
	
	public TabPanelBillingInfoPanel getPanelTransform() {
		return _panelTransform;
	}
	
	
	
	
	public TabPanelBillingInfoPanel getPanelKenan() {
		return _panelKenan;
	}
	
	
}
