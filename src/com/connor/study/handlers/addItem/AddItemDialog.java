package com.connor.study.handlers.addItem;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.eclipse.jface.layout.GridLayoutFactory;

import com.connor.common.UIUtil.CommonUI;
import com.teamcenter.rac.aif.AbstractAIFApplication;
import com.teamcenter.rac.aif.AbstractAIFDialog;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;

public class AddItemDialog extends AbstractAIFDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	private TCSession session = null;
	private AbstractAIFApplication app;
	private CommonUI UI;
	private JButton[] btns;

	/**
	 * 构造函数,应该保留无参的构造函数方便反射调用
	 */
	public AddItemDialog() {
		super();
		this.app = AIFUtility.getCurrentApplication();
		this.session = (TCSession) app.getSession();
	}

	public void initUI() {

		// 当前用户的桌面路径
		this.setPreferredSize(new Dimension(300, 250));
		this.setTitle("新建Item");
		/*
		 * this.okButton = new JButton("确定"); this.celButton = new
		 * JButton("取消"); this.okButton.addActionListener(this);
		 * this.celButton.addActionListener(this); JPanel rootJPanel = new
		 * JPanel(new FlowLayout()); rootJPanel.add(okButton);
		 * rootJPanel.add(celButton);
		 */
		UI = new CommonUI();
		JPanel centerPanel = null;
		try {
			centerPanel = UI.generateCommonLab_Tf_UI(new String[] { "零组件名", "零组件描述", "ID" }, new int[] { 16, 16, 16 });
		} catch (Exception e) {
			e.printStackTrace();
		}
		JPanel rootJPanel = new JPanel(new FlowLayout());
		btns = UI.generateBtnListPanel(this, rootJPanel, "确定", "取消");
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
		if (btn.equals(btns[0])) {
				AddItemOperation newFolderOperation;
				newFolderOperation = new AddItemOperation(app.getTargetComponent(), session,
						UI);
				session.queueOperation(newFolderOperation, true);
				this.disposeDialog();
				this.dispose();
			
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
