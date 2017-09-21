package com.connor.study.handlers.setItenProperty;

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
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;
import com.teamcenter.rac.util.PropertyLayout;

public class SetItemPropertyDialog extends AbstractAIFDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	private TCSession session = null;
	private AbstractAIFApplication app;

	JLabel lable_itemProperty = new JLabel("����ֵ");
	JTextField itemProperty = new JTextField(32);

	private JButton okButton;
	private JButton celButton;

	public SetItemPropertyDialog() {
		super();
		this.app = AIFUtility.getCurrentApplication();
		this.session = (TCSession) app.getSession();
	}

	public void initUI() {

		// ��ǰ�û�������·��
		this.setSize(new Dimension(300, 200));
		this.setTitle("��������");

		this.okButton = new JButton("ȷ��");
		this.celButton = new JButton("ȡ��");

		this.okButton.addActionListener(this);
		this.celButton.addActionListener(this);

		JPanel centerPanel = new JPanel(new PropertyLayout());
		centerPanel.add("1.1.left.top", lable_itemProperty);
		centerPanel.add("1.2.left.top", itemProperty);

		JPanel rootJPanel = new JPanel(new FlowLayout());
		rootJPanel.add(okButton);
		rootJPanel.add(celButton);

		this.setLayout(new BorderLayout());
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(rootJPanel, BorderLayout.SOUTH);
		this.setVisible(true);
		this.centerToScreen();
		this.pack();
		this.showDialog();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object btn = e.getSource();
		if (btn.equals(okButton)) {
			if (itemProperty.getText() != null && !"".equals(itemProperty.getText())) {

				SetItemPropertyOperation operation;
				operation = new SetItemPropertyOperation(app.getTargetComponent(), session, itemProperty.getText());
				// session.queueOperation()����Operation�����ȴ�operation�Ľ��
				// session.queueOperationAndWait(arg0) �ȴ�operation�Ľ��
				// ���������ʱ��������������operation�����У�����TC���������������operation�������TC�Ľ�������ʾ��������
				if (operation != null) {
					session.queueOperation(operation, true);
				}
				this.disposeDialog();
				this.dispose();

			} else {
				MessageBox.post("�ļ�������Ϊ��", "��ʾ ", MessageBox.ERROR);
			}
		} else {
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
