package com.connor.study.handlers.addFolder;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.teamcenter.rac.aif.AbstractAIFApplication;
import com.teamcenter.rac.aif.AbstractAIFDialog;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;
import com.teamcenter.rac.util.PropertyLayout;

public class AddFolderCustomDialog extends AbstractAIFDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TCSession session = null;
	private AbstractAIFApplication app;
	JButton btnOK = new JButton("ȷ��");
	JButton btnCal = new JButton("ȡ��");
	JLabel folder = new JLabel("�ļ�����");
	JTextField folderName = new JTextField(16);
	JLabel folderDesc = new JLabel("�ļ�����");
	JTextField folderDescCxt = new JTextField(16);

	public AddFolderCustomDialog() {
		super();
		this.app = AIFUtility.getCurrentApplication();
		this.session = (TCSession) app.getSession();
	}

	public void initUI() {
		this.setTitle("�����ļ���");

		Dimension dimension = new Dimension(300, 200);
		setPreferredSize(dimension);

		JPanel centerJPanel = new JPanel(new PropertyLayout());
		centerJPanel.add("1.1.left.top", folder);
		centerJPanel.add("1.2.left.top", folderName);
		centerJPanel.add("2.1.left.top", folderDesc);
		centerJPanel.add("2.2.left.top", folderDescCxt);

		JPanel butPanal = new JPanel(new FlowLayout());

		butPanal.add(btnOK);
		butPanal.add(btnCal);

		btnOK.addActionListener(this);
		btnCal.addActionListener(this);

		this.setLayout(new BorderLayout());

		this.add(centerJPanel, BorderLayout.CENTER);
		this.add(butPanal, BorderLayout.SOUTH);

		this.pack();
		centerToScreen(1.0D, 0.75D);
		this.showDialog();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object btn = e.getSource();
		if (btn.equals(btnOK)) {
			if (folderName.getText() != null && !"".equals(folderName.getText())) {
				AddFolderCustomOperation newFolderOperation;
				try {
					newFolderOperation = new AddFolderCustomOperation(session, session.getUser().getHomeFolder(),
							folderName.getText(), folderDescCxt.getText());
					session.queueOperation(newFolderOperation, true);
					this.disposeDialog();
					this.dispose();
				} catch (TCException e1) {
					e1.printStackTrace();
				}

			} else {
				MessageBox.post("�ļ������Ʋ���Ϊ��", "��ʾ ", MessageBox.ERROR);
			}
		} else if (btn.equals(btnCal)) {
			this.disposeDialog();
			this.dispose();
		}
	}

	@Override
	public void run() {
		super.run();
		initUI();
	}

}
