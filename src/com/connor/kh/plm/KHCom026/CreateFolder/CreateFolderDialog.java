package com.connor.kh.plm.KHCom026.CreateFolder;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.teamcenter.rac.aif.AbstractAIFDialog;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCSession;

/**
 * �½�ά���ļ���
 * 
 * @author Administrator
 *
 */
public class CreateFolderDialog extends AbstractAIFDialog {
	private static final long serialVersionUID = 6314246018015989404L;
	private final boolean DEBUG = true;
	private JButton btn_ok;
	private JButton btn_cancle;
	private JDialog dialog;
	private JTextField field;

	public CreateFolderDialog() {
	}

	// @Override
	public void run() {
		JFrame frame = new JFrame();

		dialog = new JDialog(frame, "�½���Ŀά���ļ���");

		dialog.setSize(350, 130);

		JPanel panel_show = new JPanel();
		panel_show.setSize(300, 80);

		JLabel label = new JLabel("�����ļ�������");
		field = new JTextField(16);

		panel_show.add(label, "1.1.left.top");
		panel_show.add(field, "1.2.left.top");

		btn_ok = new JButton("ȷ��");
		btn_cancle = new JButton("ȡ��");

		JPanel btn_panel = new JPanel(new FlowLayout());
		btn_panel.add(btn_ok);
		btn_panel.add(btn_cancle);

		dialog.add(panel_show, BorderLayout.NORTH);
		dialog.add(btn_panel, BorderLayout.SOUTH);

		dialog.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - dialog.getWidth()) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - dialog.getHeight()) / 2);

		dialog.setResizable(false);
		dialog.setVisible(true);
		btn_addListener();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void btn_addListener() {
		btn_ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					CreateFolderOperation operation = new CreateFolderOperation();
					operation.setFolderName(field.getText());
					TCSession session = (TCSession) AIFUtility.getCurrentApplication().getSession();
					session.queueOperation(operation);
					dialog.dispose();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btn_cancle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
				// ���Խ׶���Ҫϵͳ�˳��������ڴ�ռ�ù��࣬��ʽ������ҪDEBUG=false����������java���򶼽��˳�
				if (DEBUG) {
					// System.gc();
					System.out.println("ϵͳ�˳�");
					System.exit(0);
				}

			}
		});
	}

	public static void main(String[] args) {
		CreateFolderDialog d = new CreateFolderDialog();
		d.run();

	}
}
