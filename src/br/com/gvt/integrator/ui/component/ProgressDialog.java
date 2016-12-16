package br.com.gvt.integrator.ui.component;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import org.apache.log4j.Logger;

import br.com.gvt.integrator.utils.Resources;




/**
 * ProgressDialog is a custom dialog that allows you to customize dialog messages and progress bar.
 * 
 * @author José Júnior
 *         GVT - 2013.07
 */
public class ProgressDialog extends JDialog {
	private static final long serialVersionUID = 4420083904752541885L;
	
	private final Logger _logger = Logger.getLogger(ProgressDialog.class);
	
	private JPanel _contentPanel;
	
	private JProgressBar _progressBar;
	
	private JLabel _labelDot;
	private JLabel _labelDialogInfo;
	private JButton _buttonCancel;
	
	private int _progressMinimumValue;
	private int _progressMaximumValue;
	
	private boolean _running = true;
	
	
	
	
	/**
	 * The progress dialog constructor.
	 * @param parent
	 *            The dialog's parent frame.
	 * @param title
	 *            The dialog title.
	 */
	public ProgressDialog(JFrame parent, String title) {
		super(parent, title, false);
		
		init(parent);
		
		_logger.debug("Dialog running on it's own thread. Exit WorkingDialog.");
	}
	
	
	
	
	/**
	 * Initializes component.
	 * 
	 * @param parent
	 *            The dialog's parent frame.
	 */
	private void init(JFrame parent) {
		setResizable(false);
		setPreferredSize(new Dimension(400, 200));
		setMinimumSize(new Dimension(400, 200));
		setMaximumSize(new Dimension(400, 200));
		
		setLocationRelativeTo(parent);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		
		createContentPanel();
		
		new UpdateProgressBarTask().execute();
		updateDotLabel();
		
		pack();
		setVisible(true);
	}
	
	
	
	
	/**
	 * Create dialog content panel.
	 */
	private void createContentPanel() {
		_contentPanel = new JPanel();
		_contentPanel.setLayout(null);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(_contentPanel, BorderLayout.CENTER);
		
		_progressBar = new JProgressBar(0, 100);
		_progressBar.setPreferredSize(new Dimension(300, 26));
		_progressBar.setBounds(46, 70, 300, 26);
		
		JLabel label = new JLabel(Resources.INSTANCE.getString("processing"));
		label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label.setBounds(46, 40, 68, 14);
		label.setHorizontalTextPosition(JLabel.LEADING);
		
		_contentPanel.add(label);
		_contentPanel.add(_progressBar);
		
		_buttonCancel = new JButton("Cancel");
		_buttonCancel.setBounds(256, 137, 90, 24);
		_buttonCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				stop();
			}
		});
		_contentPanel.add(_buttonCancel);
		
		_labelDot = new JLabel("....");
		_labelDot.setFont(new Font("Tahoma", Font.PLAIN, 12));
		_labelDot.setBounds(114, 40, 28, 14);
		_contentPanel.add(_labelDot);
		
		_labelDialogInfo = new JLabel("");
		_labelDialogInfo.setBounds(46, 107, 300, 14);
		_contentPanel.add(_labelDialogInfo);
	}
	
	
	
	
	/**
	 * This method invokes a new thread to update the dots on a dialog message. It's useful to give the user another
	 * feedback so one can understand that the application still working.
	 */
	private void updateDotLabel() {
		
		new Thread() {
			public void run() {
				while (_running) {
					try {
						Thread.sleep(300);
						
						// Here, we can safely update the GUI because it'll be called from the event dispatch thread.
						if (_labelDot.getText().length() == 8) {
							_labelDot.setText(".");
						}
						else {
							_labelDot.setText(_labelDot.getText() + ".");
						}
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
		
	}
	
	
	
	
	/**
	 * Updates the dialog's message.
	 * @param text
	 *            The text to show on the dialog.
	 */
	public void updateLabel(String text) {
		_logger.trace("Label updated to: " + text);
		_labelDialogInfo.setText(text);
	}
	
	
	
	
	/**
	 * Schedule a compute-intensive task in a background thread.
	 * @author José Júnior
	 */
	private class UpdateProgressBarTask extends SwingWorker<Void, Integer> {
		@Override
		protected Void doInBackground() {
			// Reset progress values.
			_progressMinimumValue = 0;
			_progressMaximumValue = 100;
			
			// 0% to 100%.
			for (int i = 0; i < 100;) {
				// System.out.println("update progress " + i);
				
				// Check if progress should stop.
				if (!_running) {
					_logger.trace("UpdateProgressBarTask stoped doInBackground!");
					return null;
				}
				
				// Jump to progress value.
				if (i < _progressMinimumValue) {
					// System.out.println("i < _progressMinimumValue");
					i = _progressMinimumValue;
				}
				
				// Update progress bar.
				if (i >= _progressMaximumValue) {
					// System.out.println("i >= _progressMaximumValue");
				}
				else {
					publish(++i);
				}
				
				// Sleep a bit.
				try {
					Thread.sleep(50);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			return null;
		}
		
		
		
		
		/**
		 * Run in event-dispatching thread to process intermediate results sent from publish().
		 */
		@Override
		protected void process(List<Integer> values) {
			_progressBar.setValue(values.get(values.size() - 1));
		}
		
		
		
		
		/**
		 * Run in event-dispatching thread after doInBackground() completes.
		 */
		@Override
		protected void done() {
			stop();
		}
	}
	
	
	
	
	public void stop() {
		_running = false;
		_logger.trace("Dialog stoped!");
		dispose();
	}
	
	
	
	
	public static void stop(ProgressDialog workingDialog) {
		if (workingDialog != null) {
			workingDialog.stop();
		}
	}
	
	
	
	
	public void disableCancel() {
		_buttonCancel.setEnabled(false);
	}
	
	
	
	
	public boolean isRunning() {
		return _running;
	}
	
	
	
	
	public void updateProgressMinimumValue(int progressValue) {
		_progressMinimumValue = progressValue;
		// System.out.println("Minimum value: " + progressValue);
	}
	
	
	
	
	public void updateProgressMaximumValue(int progressValue) {
		_progressMaximumValue = progressValue;
		// System.out.println("Maximum value: " + progressValue);
	}
	
	
	
	
	public void updateProgressValues(int progressMinimumValue, int progressMaximumValue) {
		updateProgressMinimumValue(progressMinimumValue);
		updateProgressMaximumValue(progressMaximumValue);
	}
	
	
	
	
	/**
	 * Entry point for testing the WorkingDialog.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					ProgressDialog dialog = new ProgressDialog(null, "Progress");
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}
}
