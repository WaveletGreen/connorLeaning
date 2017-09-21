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

	JLabel lable_itemProperty = new JLabel("属性值");
	JTextField itemProperty = new JTextField(32);

	private JButton okButton;
	private JButton celButton;

	public SetItemPropertyDialog() {
		super();
		this.app = AIFUtility.getCurrentApplication();
		this.session = (TCSession) app.getSession();
	}

	public void initUI() {

		// 当前用户的桌面路径
		this.setSize(new Dimension(300, 200));
		this.setTitle("设置属性");

		this.okButton = new JButton("确定");
		this.celButton = new JButton("取消");

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
				// session.queueOperation()启动Operation但不等待operation的结果
				// session.queueOperationAndWait(arg0) 等待operation的结果
				// 大剂量（长时）的运算必须放在operation中运行，否则TC会假死甚至崩溃，operation还会调用TC的进度条显示操作进度
				if (operation != null) {
					session.queueOperation(operation, true);
				}
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
