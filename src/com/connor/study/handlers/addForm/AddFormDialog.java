package com.connor.study.handlers.addForm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.teamcenter.rac.aif.AbstractAIFApplication;
import com.teamcenter.rac.aif.AbstractAIFDialog;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;
import com.teamcenter.rac.util.PropertyLayout;

public class AddFormDialog extends AbstractAIFDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	private TCSession session = null;
	private AbstractAIFApplication app;

	JLabel lable_formName = new JLabel("表单名称");
	JTextField formName = new JTextField(16);
	JLabel lable_formDesc = new JLabel("表单描述");
	JTextArea formDesc = new JTextArea(6, 16);

	private JButton okButton;
	private JButton celButton;

	public AddFormDialog() {
		super();
		this.app = AIFUtility.getCurrentApplication();
		this.session = (TCSession) app.getSession();
	}

	public void initUI() {

		// 当前用户的桌面路径
		this.setSize(new Dimension(300, 200));
		this.setTitle("新建表单");

		this.okButton = new JButton("确定");
		this.celButton = new JButton("取消");

		this.okButton.addActionListener(this);
		this.celButton.addActionListener(this);

		JPanel centerPanel = new JPanel(new PropertyLayout());
		centerPanel.add("1.1.left.top", lable_formName);
		centerPanel.add("1.2.left.top", formName);
		centerPanel.add("2.1.left.top", lable_formDesc);
		centerPanel.add("2.2.left.top", formDesc);

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
			if (formName.getText() != null && !"".equals(formName.getText())) {
				AddFormOperation newFolderOperation;
				newFolderOperation = new AddFormOperation(app.getTargetComponent(), session, formName.getText(),
						formDesc.getText());
				session.queueOperation(newFolderOperation, true);
				this.disposeDialog();
				this.dispose();

			} else {
				MessageBox.post("文件名不能为空", "提示 ", MessageBox.ERROR);
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
