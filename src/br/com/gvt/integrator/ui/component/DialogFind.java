package br.com.gvt.integrator.ui.component;


import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import br.com.gvt.integrator.action.ExitAction;
import br.com.gvt.integrator.listener.ActionListenerFind;
import br.com.gvt.integrator.listener.ActionListenerReplace;
import br.com.gvt.integrator.listener.DocumentListenerEnableButton;
import br.com.gvt.integrator.listener.KeyListenerEnterKeyToButtonClick;
import br.com.gvt.integrator.listener.WindowFocusListenerDialogFocus;
import br.com.gvt.integrator.utils.Constants;
import br.com.gvt.integrator.utils.Resources;




public class DialogFind extends JFrame {
	private static final long serialVersionUID = -3770161967222486491L;
	
	private static final Logger _logger = Logger.getLogger(DialogFind.class);
	
	private static DialogFind _singletonInstance;
	
	private static JFrame _mainFrame;
	private static TabPanelBillingInfoPanel _tabPanelBillingInfoPanel;
	
	// Search panel text fields.
	private static JTextField _textFieldFind;
	private static JTextField _textFieldReplace;
	
	// Search panel checkboxes.
	private static JCheckBox _checkBoxCaseSensitive;
	private static JCheckBox _checkBoxWrapAround;
	private static JCheckBox _checkBoxWholeWord;
	private static JCheckBox _checkBoxSearchBackwards;
	
	// Search panel buttons.
	private static JButton _buttonFind;
	private static JButton _buttonReplace;
	private static JButton _buttonCancel;
	
	public static final String ACTION_MAP_KEY_ESCAPE = "Escape";
	
	
	
	
	/**
	 * Singleton frame. Don't worry about thread-safe.
	 * No threads will be created for dialogs.
	 * 
	 * @param mainFrame
	 * @param panelTabBilling
	 * @return
	 */
	public static synchronized DialogFind getInstance(JFrame mainFrame,
			TabPanelBillingInfoPanel tabPanelBillingInfoPanel) {
		if (_singletonInstance == null) {
			_singletonInstance = new DialogFind(mainFrame, tabPanelBillingInfoPanel);
			_logger.info("Dialog singleton created!");
		}
		else {
			_singletonInstance.setVisible(true);
			setTabPanelBillingInfoPanel(tabPanelBillingInfoPanel);
			_logger.info("Got singleton instance.");
		}
		_textFieldFind.requestFocus();
		return _singletonInstance;
	}
	
	
	
	
	private DialogFind(JFrame mainFrame, TabPanelBillingInfoPanel tabPanelBillingInfoPanel) {
		_mainFrame = mainFrame;
		_tabPanelBillingInfoPanel = tabPanelBillingInfoPanel;
		
		// Focus listener to correct dialog visibility.
		 _mainFrame.addWindowFocusListener(new WindowFocusListenerDialogFocus(this));
		
		init();
		
		// Hide frame on ESC key.
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), ACTION_MAP_KEY_ESCAPE);
		
		getRootPane().getActionMap().put(ACTION_MAP_KEY_ESCAPE, new AbstractAction() {
			private static final long serialVersionUID = 1L;
			
			
			
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
	}
	
	
	
	
	private void init() {
		setResizable(false);
		setAutoRequestFocus(false);
		setTitle(Resources.INSTANCE.getString("find"));
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setIconImage(new ImageIcon(getClass().getResource(Constants.PATH_IMAGE_FIND)).getImage());
		
		// Remove icon from Windows task bar.
		setType(Type.UTILITY);
		
		// Create search and replace labels.
		JLabel labelFind = new JLabel(Resources.INSTANCE.getString("find") + ":");
		JLabel labelReplace = new JLabel(Resources.INSTANCE.getString("replace") + ":");
		
		// Create panel's text fields.
		_textFieldFind = new JTextField();
		_textFieldReplace = new JTextField();
		
		// Create option check boxes.
		_checkBoxCaseSensitive = new JCheckBox(Resources.INSTANCE.getString("case_sensitive"));
		_checkBoxWrapAround = new JCheckBox(Resources.INSTANCE.getString("wrap_around"));
		_checkBoxWholeWord = new JCheckBox(Resources.INSTANCE.getString("whole_word"));
		_checkBoxSearchBackwards = new JCheckBox(Resources.INSTANCE.getString("search_backwards"));
		
		// Set defaults.
		_checkBoxWrapAround.setSelected(true);
		
		// Create dialog action buttons.
		_buttonFind = new JButton(Resources.INSTANCE.getString("find"));
		_buttonReplace = new JButton(Resources.INSTANCE.getString("replace"));
		_buttonCancel = new JButton(Resources.INSTANCE.getString("Button.cancel"));
		
		// Set buttons action listeners.
		_buttonFind.addActionListener(new ActionListenerFind(this));
		_buttonReplace.addActionListener(new ActionListenerReplace(this, _buttonFind));
		_buttonCancel.addActionListener(new ExitAction(this, false));
		
		// Find text field to enable/disable find button.
		_buttonFind.setEnabled(false);
		_textFieldFind.getDocument().addDocumentListener(new DocumentListenerEnableButton(_buttonFind));
		
		// Key listener to listen to ENTER key and click the find button.
		_textFieldFind.addKeyListener(new KeyListenerEnterKeyToButtonClick(_buttonFind));
		
		// Key listener to listen to ENTER key and click the replace button.
		_textFieldReplace.addKeyListener(new KeyListenerEnterKeyToButtonClick(_buttonReplace));
		
		// Remove redundant default border of check boxes.
		_checkBoxCaseSensitive.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		_checkBoxWrapAround.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		_checkBoxWholeWord.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		_checkBoxSearchBackwards.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		
		// Create panel layout.
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		getContentPane().setLayout(groupLayout);
		groupLayout.setAutoCreateGaps(true);
		groupLayout.setAutoCreateContainerGaps(true);
		
		// Set horizontal group.
		groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(labelFind)
						.addComponent(labelReplace))
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(_textFieldFind)
						.addComponent(_textFieldReplace)
						.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(_checkBoxCaseSensitive)
										.addComponent(_checkBoxWholeWord))
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(_checkBoxWrapAround)
										.addComponent(_checkBoxSearchBackwards))))
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(_buttonFind)
						.addComponent(_buttonReplace)
						.addComponent(_buttonCancel))
				);
		
		groupLayout.linkSize(SwingConstants.HORIZONTAL, _buttonFind, _buttonReplace, _buttonCancel);
		
		// Set vertical group.
		groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(labelFind)
						.addComponent(_textFieldFind)
						.addComponent(_buttonFind))
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(labelReplace)
										.addComponent(_textFieldReplace)
										.addComponent(_buttonReplace))
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(_checkBoxCaseSensitive)
										.addComponent(_checkBoxWrapAround)
										.addComponent(_buttonCancel))
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(_checkBoxWholeWord)
										.addComponent(_checkBoxSearchBackwards))
						))
				);
		
		
		pack();
		setVisible(true);
		
		// This will center the JFrame in the middle of the screen.
		setLocationRelativeTo(_mainFrame);
	}
	
	
	
	
	public TabPanelBillingInfoPanel getPanelTabBillingInfoPanel() {
		return _tabPanelBillingInfoPanel;
	}
	
	
	
	
	public static void setTabPanelBillingInfoPanel(TabPanelBillingInfoPanel tabPanelBillingInfoPanel) {
		_tabPanelBillingInfoPanel = tabPanelBillingInfoPanel;
	}
	
	
	
	
	public static JTextField getTextFieldFind() {
		return _textFieldFind;
	}
	
	
	
	
	public static JTextField getTextFieldReplace() {
		return _textFieldReplace;
	}
	
	
	
	
	public static JCheckBox getCheckBoxCaseSensitive() {
		return _checkBoxCaseSensitive;
	}
	
	
	
	
	public static JCheckBox getCheckBoxWrapAround() {
		return _checkBoxWrapAround;
	}
	
	
	
	
	public static JCheckBox getCheckBoxWholeWord() {
		return _checkBoxWholeWord;
	}
	
	
	
	
	public static JCheckBox getCheckBoxSearchBackwards() {
		return _checkBoxSearchBackwards;
	}
	
	
	
	
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Set System L&F.
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
				new DialogFind(null, null).setVisible(true);
			}
		});
	}
}
