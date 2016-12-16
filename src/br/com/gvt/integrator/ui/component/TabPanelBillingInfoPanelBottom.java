package br.com.gvt.integrator.ui.component;


import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;

import br.com.gvt.integrator.enums.Environment;
import br.com.gvt.integrator.enums.KenanFlow;
import br.com.gvt.integrator.listener.ActionListenerCleanTextPane;
import br.com.gvt.integrator.listener.ActionListenerCopyToClipboard;
import br.com.gvt.integrator.listener.ActionListenerFormatXML;
import br.com.gvt.integrator.listener.ActionListenerPasteFromClipboard;
import br.com.gvt.integrator.listener.ItemListenerLockUnlock;
import br.com.gvt.integrator.utils.Constants;
import br.com.gvt.integrator.utils.Resources;




/**
 * Bottom of info panel with action buttons. Among other things, it contains all the layout for the panel buttons.
 * 
 * @author José Júnior
 *         GVT - 2013.07
 */
public class TabPanelBillingInfoPanelBottom extends JPanel {
	private static final long serialVersionUID = -241774369985142737L;
	
	private static int GAP_HORIZONTAL_COMPONENTS = 4;
	private static int GAP_HORIZONTAL_CONTAINER = 4;
	private static int GAP_VERTICAL_CONTAINER = 2;
	
	private JComboBox<Enum<Environment>> _comboBoxEnvironment;
	private JComboBox<Enum<KenanFlow>> _comboBoxKenanFlow;
	private JButton _buttonAction;
	private JButton _buttonGenerate;
	private JButton _buttonCopy;
	private JButton _buttonPaste;
	private JButton _buttonClear;
	private JButton _buttonFormat;
	private JButton _buttonUnlockAccount;
	
	private JToggleButton _toggleButton;
	
	private KenanFlow _kenanFlowAdvice;
	
	final static Logger _logger = Logger.getLogger(TabPanelBillingInfoPanelBottom.class);
	
	
	
	
	public TabPanelBillingInfoPanelBottom(String buttonName, JTabbedPane tabbedPane, JTextPane textPaneEditable) {
		init(buttonName, tabbedPane, textPaneEditable);
	}
	
	
	
	
	private void init(String buttonName, final JTabbedPane tabbedPane, final JTextPane textPaneEditable) {
		
		// Action button.
		_buttonAction = createButton(buttonName);
		
		// Generate.
		_buttonGenerate = createButton(Resources.INSTANCE.getString("Button.generate"));
		
		// Copy.
		_buttonCopy = createButton(Resources.INSTANCE.getString("Button.copy"));
		_buttonCopy.addActionListener(new ActionListenerCopyToClipboard(tabbedPane));
		
		// Paste.
		_buttonPaste = createButton(Resources.INSTANCE.getString("Button.paste"));
		_buttonPaste.addActionListener(new ActionListenerPasteFromClipboard(tabbedPane));
		
		// Clear.
		_buttonClear = createButton(Resources.INSTANCE.getString("Button.clear"));
		_buttonClear.addActionListener(new ActionListenerCleanTextPane(tabbedPane));
		
		// Format.
		_buttonFormat = createButton(Resources.INSTANCE.getString("Button.format"));
		_buttonFormat.addActionListener(new ActionListenerFormatXML(tabbedPane));
		
		// Environment combo box.
		_comboBoxEnvironment = createComboBoxEnvironment();
		
		// Kenan execution flow.
		if (buttonName.equalsIgnoreCase(TabPanelBilling.ACTION_CALL_KENAN)) {
			_comboBoxKenanFlow = createComboBoxKenanExecutionFlow();
		}
		
		// Unlock account.
		if (buttonName.equalsIgnoreCase(TabPanelBilling.ACTION_TRANSFORM)) {
			_buttonUnlockAccount = createButton(Resources.INSTANCE.getString("Button.unlock"));
		}
		
		// Create the panel lock/unlock icons for the toggle button.
		final Icon iconLock = new ImageIcon(getClass().getResource(Constants.PATH_IMAGE_LOCK));
		final Icon iconUnlock = new ImageIcon(getClass().getResource(Constants.PATH_IMAGE_UNLOCK));
		
		// Create the toggle button.
		_toggleButton = new JToggleButton(iconUnlock);
		_toggleButton.setMargin(new Insets(2, 2, 2, 2));
		_toggleButton.setPreferredSize(new Dimension(22, 22));
		_toggleButton.setMaximumSize(new Dimension(22, 22));
		_toggleButton.setMinimumSize(new Dimension(22, 22));
		_toggleButton.setSelectedIcon(iconLock);
		_toggleButton.setSelected(true);
		
		// Enable editing on allowed panels.
		int totalTabs = tabbedPane.getTabCount() - 1;
		for (int i = 0; i < totalTabs; i++) {
			JTextPane textPane = (JTextPane) ((JScrollPane) tabbedPane.getComponentAt(i)).getViewport().getView();
			_toggleButton.addItemListener(new ItemListenerLockUnlock(textPane));
		}
		
		// Create the panel group layout.
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(createHorizontalSequentialGroup(groupLayout));
		groupLayout.setVerticalGroup(createVerticalSequentialGroup(groupLayout));
		
		setLayout(groupLayout);
	}
	
	
	
	
	private ParallelGroup createHorizontalSequentialGroup(GroupLayout groupLayout) {
		
		// Create a parallel horizontal group.
		ParallelGroup horizontalParallelGroup = groupLayout.createParallelGroup(Alignment.LEADING);
		SequentialGroup horizontalSequentialGroupLineTop = groupLayout.createSequentialGroup();
		SequentialGroup horizontalSequentialGroupLineBottom = groupLayout.createSequentialGroup();
		
		horizontalParallelGroup.addGroup(horizontalSequentialGroupLineTop);
		horizontalParallelGroup.addGroup(horizontalSequentialGroupLineBottom);
		
		// Add top group components.
		horizontalSequentialGroupLineTop.addGap(GAP_HORIZONTAL_COMPONENTS);
		horizontalSequentialGroupLineTop.addComponent(_buttonAction, GroupLayout.PREFERRED_SIZE,
				GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
		horizontalSequentialGroupLineTop.addGap(GAP_HORIZONTAL_COMPONENTS);
		
		horizontalSequentialGroupLineTop.addComponent(_buttonGenerate, GroupLayout.PREFERRED_SIZE,
				GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
		horizontalSequentialGroupLineTop.addGap(GAP_HORIZONTAL_COMPONENTS);
		
		horizontalSequentialGroupLineTop.addComponent(_buttonCopy, GroupLayout.PREFERRED_SIZE,
				GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
		horizontalSequentialGroupLineTop.addGap(GAP_HORIZONTAL_COMPONENTS);
		
		horizontalSequentialGroupLineTop.addComponent(_buttonPaste, GroupLayout.PREFERRED_SIZE,
				GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
		horizontalSequentialGroupLineTop.addGap(GAP_HORIZONTAL_COMPONENTS);
		
		horizontalSequentialGroupLineTop.addComponent(_buttonClear, GroupLayout.PREFERRED_SIZE,
				GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
		horizontalSequentialGroupLineTop.addPreferredGap(ComponentPlacement.RELATED, 63, Short.MAX_VALUE);
		
		horizontalSequentialGroupLineTop.addComponent(_toggleButton, GroupLayout.PREFERRED_SIZE,
				GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
		horizontalSequentialGroupLineTop.addGap(GAP_HORIZONTAL_CONTAINER);
		
		// Add bottom group components.
		horizontalSequentialGroupLineBottom.addGap(GAP_HORIZONTAL_COMPONENTS);
		horizontalSequentialGroupLineBottom.addComponent(_comboBoxEnvironment, GroupLayout.PREFERRED_SIZE,
				GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);;
		horizontalSequentialGroupLineBottom.addPreferredGap(ComponentPlacement.RELATED);
		
		// Kenan flow control just goes on Kenan call panel.
		if (_comboBoxKenanFlow != null) {
			horizontalSequentialGroupLineBottom.addComponent(_comboBoxKenanFlow, GroupLayout.PREFERRED_SIZE,
					GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
			horizontalSequentialGroupLineBottom.addPreferredGap(ComponentPlacement.RELATED);
		}
		
		// Unlock order just goes on Transform panel.
		if (_buttonUnlockAccount != null) {
			horizontalSequentialGroupLineBottom.addComponent(_buttonUnlockAccount, GroupLayout.PREFERRED_SIZE,
					GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
			horizontalSequentialGroupLineBottom.addPreferredGap(ComponentPlacement.RELATED);
		}
		
		horizontalSequentialGroupLineBottom.addComponent(_buttonFormat);
		horizontalSequentialGroupLineBottom.addContainerGap(80, Short.MAX_VALUE);
		
		return horizontalParallelGroup;
	}
	
	
	
	
	private SequentialGroup createVerticalSequentialGroup(GroupLayout groupLayout) {
		
		// Create a sequential vertical group.
		SequentialGroup verticalSequentialGroup = groupLayout.createSequentialGroup();
		ParallelGroup verticalParallelGroupLineTop = groupLayout.createParallelGroup(Alignment.LEADING);
		ParallelGroup verticalParallelGroupLineBottom = groupLayout.createParallelGroup(Alignment.BASELINE);
		
		verticalSequentialGroup.addGap(GAP_VERTICAL_CONTAINER);
		verticalSequentialGroup.addGroup(verticalParallelGroupLineTop);
		verticalSequentialGroup.addGap(GAP_VERTICAL_CONTAINER);
		verticalSequentialGroup.addGroup(verticalParallelGroupLineBottom);
		verticalSequentialGroup.addGap(GAP_VERTICAL_CONTAINER);
		
		// Add top group components.
		verticalParallelGroupLineTop.addComponent(_buttonAction, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
				GroupLayout.PREFERRED_SIZE);
		verticalParallelGroupLineTop.addComponent(_buttonGenerate, GroupLayout.PREFERRED_SIZE,
				GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
		verticalParallelGroupLineTop.addComponent(_buttonCopy, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
				GroupLayout.PREFERRED_SIZE);
		verticalParallelGroupLineTop.addComponent(_buttonPaste, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
				GroupLayout.PREFERRED_SIZE);
		verticalParallelGroupLineTop.addComponent(_buttonClear, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
				GroupLayout.PREFERRED_SIZE);
		verticalParallelGroupLineTop.addComponent(_toggleButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
				GroupLayout.PREFERRED_SIZE);
		
		// Add bottom group components.
		verticalParallelGroupLineBottom.addComponent(_comboBoxEnvironment, GroupLayout.PREFERRED_SIZE,
				GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
		
		// Kenan flow control just goes on Kenan call panel.
		if (_comboBoxKenanFlow != null) {
			verticalParallelGroupLineBottom.addComponent(_comboBoxKenanFlow, GroupLayout.PREFERRED_SIZE,
					GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
		}
		
		// Unlock order just goes on Transform panel.
		if (_buttonUnlockAccount != null) {
			verticalParallelGroupLineBottom.addComponent(_buttonUnlockAccount, GroupLayout.PREFERRED_SIZE,
					GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
		}
		
		verticalParallelGroupLineBottom.addComponent(_buttonFormat);
		
		return verticalSequentialGroup;
	}
	
	
	
	
	/**
	 * Creates a JButton with application default settings.
	 * @param buttonName
	 *            The action button name.
	 * @return A JButton with default settings.
	 */
	private JButton createButton(String buttonName) {
		JButton jButton = new JButton(buttonName);
		jButton.setMargin(new Insets(2, 4, 2, 4));
		jButton.setHorizontalTextPosition(SwingConstants.CENTER);
		return jButton;
	}
	
	
	
	
	/**
	 * Creates a combo box with environment settings.
	 * @return A JComboBox with all possible environments.
	 */
	private JComboBox<Enum<Environment>> createComboBoxEnvironment() {
		_comboBoxEnvironment = new JComboBox<>();
		_comboBoxEnvironment.setAlignmentY(Component.CENTER_ALIGNMENT);
		_comboBoxEnvironment.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		_comboBoxEnvironment.setModel(new DefaultComboBoxModel<Enum<Environment>>(Environment.values()));
		
		// Set the development environment as the default environment.
		_comboBoxEnvironment.setSelectedItem(Environment.DEVELOPMENT);
		_comboBoxEnvironment.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unchecked")
				JComboBox<Enum<Environment>> comboBox = (JComboBox<Enum<Environment>>) e.getSource();
				Environment environment = (Environment) comboBox.getSelectedItem();
				
				if (environment == Environment.PRODUCTION) {
					_logger.info("Production environment selected!");
				}
				else {
					_logger.info("Non production environment.");
				}
			}
		});
		
		return _comboBoxEnvironment;
	}
	
	
	
	
	/**
	 * Creates a combo box with Kenan execution flows.
	 * @return A JComboBox with all possible Kenan execution flows.
	 */
	private JComboBox<Enum<KenanFlow>> createComboBoxKenanExecutionFlow() {
		_comboBoxKenanFlow = new JComboBox<>();
		_comboBoxKenanFlow.setAlignmentY(Component.CENTER_ALIGNMENT);
		_comboBoxKenanFlow.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		_comboBoxKenanFlow.setModel(new DefaultComboBoxModel<Enum<KenanFlow>>(KenanFlow.values()));
		
		// Set the Execute Order flow as the default flow.
		_comboBoxKenanFlow.setSelectedItem(KenanFlow.EXECUTE_ORDER);
		
		return _comboBoxKenanFlow;
	}
	
	
	
	
	/*******************************************************************************************************************
	 * Getters and Setters.
	 ******************************************************************************************************************/
	
	public JButton getButtonAction() {
		return _buttonAction;
	}
	
	
	
	
	public JButton getButtonGenerate() {
		return _buttonGenerate;
	}
	
	
	
	
	public JButton getButtonCopy() {
		return _buttonCopy;
	}
	
	
	
	
	public JButton getButtonPaste() {
		return _buttonPaste;
	}
	
	
	
	
	public JButton getButtonClear() {
		return _buttonClear;
	}
	
	
	
	
	public JButton getButtonFormat() {
		return _buttonFormat;
	}
	
	
	
	
	public JButton getButtonUnlockAccount() {
		return _buttonUnlockAccount;
	}
	
	
	
	
	public JComboBox<Enum<Environment>> getComboBoxEnvironment() {
		return _comboBoxEnvironment;
	}
	
	
	
	
	public JComboBox<Enum<KenanFlow>> getComboBoxKenanFlow() {
		return _comboBoxKenanFlow;
	}
	
	
	
	
	public KenanFlow getKenanFlowAdvice() {
		return _kenanFlowAdvice;
	}
	
	
	
	
	public void setKenanFlowAdvice(KenanFlow KenanFlowAdvice) {
		_kenanFlowAdvice = KenanFlowAdvice;
	}
	
}
