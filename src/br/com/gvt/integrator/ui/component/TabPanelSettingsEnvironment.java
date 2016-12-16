package br.com.gvt.integrator.ui.component;


import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import br.com.gvt.integrator.dto.EnvironmentDTO;




/**
 * A custom label to be used on the environment settings panel.
 * 
 * @author José Júnior
 *         GVT - 2013.07
 */
class SettingsLabel extends JLabel {
	private static final long serialVersionUID = 1387651984563632184L;
	
	
	
	
	SettingsLabel(Font font, String text) {
		super(text);
		setFont(font);
		setHorizontalAlignment(SwingConstants.RIGHT);
		setMinimumSize(new Dimension(24, 14));
	}
}




/**
 * A single environment settings.
 * 
 * @author junior
 * 
 */
public class TabPanelSettingsEnvironment extends JPanel {
	private static final long serialVersionUID = -6671544256702298176L;
	
	private static int JTEXTFIELD_COLUMN_SIZE = 70;
	
	private JTextField _textFieldUrlSiebelCustomerOrder;
	private JTextField _textFieldUrlSiebelAccountProfile;
	private JTextField _textFieldUrlTransform;
	private JTextField _textFieldUrlKenan;
	
	
	
	
	public TabPanelSettingsEnvironment(String environment) {
		Font fontSettings = new Font("Tahoma", Font.PLAIN, 12);
		
		// Create the title border for the given environment.
		Border compound = new BevelBorder(BevelBorder.LOWERED, null, null, null, null);
		setBorder(BorderFactory.createTitledBorder(compound, environment, TitledBorder.LEADING, TitledBorder.TOP,
				fontSettings.deriveFont(Font.BOLD)));
		
		_textFieldUrlSiebelCustomerOrder = new JTextField(JTEXTFIELD_COLUMN_SIZE);
		_textFieldUrlSiebelAccountProfile = new JTextField(JTEXTFIELD_COLUMN_SIZE);
		_textFieldUrlTransform = new JTextField(JTEXTFIELD_COLUMN_SIZE);
		_textFieldUrlKenan = new JTextField(JTEXTFIELD_COLUMN_SIZE);
		
		_textFieldUrlSiebelCustomerOrder.setToolTipText("GetCustomerOrderBillInfo WSDL URL");
		_textFieldUrlSiebelAccountProfile.setToolTipText("GetAccounProfile WSDL URL");
		_textFieldUrlTransform.setToolTipText("TransformerService WSDL URL");
		_textFieldUrlKenan.setToolTipText("BillingAdapterService WSDL URL");
		
		JLabel labelSiebelCustomerOrder = new SettingsLabel(fontSettings, "Siebel CO");
		JLabel labelSiebelAccountProfile = new SettingsLabel(fontSettings, "Siebel AP");
		JLabel labelTransform = new SettingsLabel(fontSettings, "Transform");
		JLabel labelKenan = new SettingsLabel(fontSettings, "Kenan");
		
		// Setting the panel layout.
		GroupLayout gl_panel1 = new GroupLayout(this);
		gl_panel1.setHorizontalGroup(
			gl_panel1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel1.createSequentialGroup()
							.addComponent(labelSiebelCustomerOrder, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(_textFieldUrlSiebelCustomerOrder, GroupLayout.PREFERRED_SIZE, 580, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel1.createSequentialGroup()
							.addComponent(labelSiebelAccountProfile, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(_textFieldUrlSiebelAccountProfile, GroupLayout.PREFERRED_SIZE, 580, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel1.createSequentialGroup()
							.addComponent(labelKenan, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(_textFieldUrlKenan, GroupLayout.PREFERRED_SIZE, 580, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel1.createSequentialGroup()
							.addComponent(labelTransform, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(_textFieldUrlTransform, GroupLayout.PREFERRED_SIZE, 580, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(46, Short.MAX_VALUE))
		);
		gl_panel1.setVerticalGroup(
			gl_panel1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel1.createParallelGroup(Alignment.BASELINE)
						.addComponent(labelSiebelCustomerOrder, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(_textFieldUrlSiebelCustomerOrder, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel1.createParallelGroup(Alignment.BASELINE)
						.addComponent(labelSiebelAccountProfile, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(_textFieldUrlSiebelAccountProfile, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel1.createParallelGroup(Alignment.BASELINE)
						.addComponent(labelTransform, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(_textFieldUrlTransform, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel1.createParallelGroup(Alignment.BASELINE)
						.addComponent(_textFieldUrlKenan, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(labelKenan, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(115, Short.MAX_VALUE))
		);
		setLayout(gl_panel1);
	}
	
	
	
	
	public void loadEnvironment(EnvironmentDTO environment) {
		setUrlSiebelCustomerOrder(environment.getUrlSiebelCustomerOrder());
		setUrlSiebelAccountProfile(environment.getUrlSiebelAccountProfile());
		setUrlTransform(environment.get_urlTransform());
		setUrlKenan(environment.getUrlKenan());
	}
	
	
	
	
	public String getUrlSiebelCustomerOrder() {
		return _textFieldUrlSiebelCustomerOrder.getText();
	}
	
	
	

	public String getUrlSiebelAccountProfile() {
		return _textFieldUrlSiebelAccountProfile.getText();
	}
	
	
	
	
	public String getUrlTransform() {
		return _textFieldUrlTransform.getText();
	}
	
	
	
	
	public String getUrlKenan() {
		return _textFieldUrlKenan.getText();
	}
	
	
	
	
	public void setUrlSiebelCustomerOrder(String url) {
		_textFieldUrlSiebelCustomerOrder.setText(url);
	}
	
	
	
	
	public void setUrlSiebelAccountProfile(String url) {
		_textFieldUrlSiebelAccountProfile.setText(url);
	}
	
	
	
	
	public void setUrlTransform(String url) {
		_textFieldUrlTransform.setText(url);
	}
	
	
	
	
	public void setUrlKenan(String url) {
		_textFieldUrlKenan.setText(url);
	}
}
