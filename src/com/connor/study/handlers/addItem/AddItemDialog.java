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
	 * ���캯��,Ӧ�ñ����޲εĹ��캯�����㷴�����
	 */
	public AddItemDialog() {
		super();
		this.app = AIFUtility.getCurrentApplication();
		this.session = (TCSession) app.getSession();
	}

	public void initUI() {

		// ��ǰ�û�������·��
		this.setPreferredSize(new Dimension(300, 250));
		this.setTitle("�½�Item");
		/*
		 * this.okButton = new JButton("ȷ��"); this.celButton = new
		 * JButton("ȡ��"); this.okButton.addActionListener(this);
		 * this.celButton.addActionListener(this); JPanel rootJPanel = new
		 * JPanel(new FlowLayout()); rootJPanel.add(okButton);
		 * rootJPanel.add(celButton);
		 */
		UI = new CommonUI();
		JPanel centerPanel = null;
		try {
			centerPanel = UI.generateCommonLab_Tf_UI(new String[] { "�������", "���������", "ID" }, new int[] { 16, 16, 16 });
		} catch (Exception e) {
			e.printStackTrace();
		}
		JPanel rootJPanel = new JPanel(new FlowLayout());
		btns = UI.generateBtnListPanel(this, rootJPanel, "ȷ��", "ȡ��");
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
