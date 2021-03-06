import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class ClerkWindow extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ClerkWindow dialog = new ClerkWindow();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ClerkWindow() {
		setBounds(100, 100, 622, 470);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JButton btnProcessPurchase = new JButton("PROCESS PURCHASE");
			btnProcessPurchase.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ProcessPurchaseWindow frame = new ProcessPurchaseWindow();
					frame.setVisible(true);
					setVisible(false);
				}
			});
			btnProcessPurchase.setBounds(82, 138, 170, 63);
			contentPanel.add(btnProcessPurchase);
		}
		{
			JButton btnProcessRefund = new JButton("PROCESS REFUND");
			btnProcessRefund.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ProcessRefundWindow frame = new ProcessRefundWindow();
					frame.setVisible(true);
					setVisible(false);
				}
			});
			btnProcessRefund.setBounds(311, 138, 170, 63);
			contentPanel.add(btnProcessRefund);
		}
		{
			JButton btnHOME = new JButton("HOME");
			btnHOME.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					swing frame = new swing();
					frame.setVisible(true);
					setVisible(false);
				}
			});
			btnHOME.setBounds(10, 11, 89, 23);
			contentPanel.add(btnHOME);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
		}
	}

}
