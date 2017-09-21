package com.connor.study.handlers.query;

import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import com.connor.study.handlers.addItem.AddItemOperation;
import com.teamcenter.rac.aif.AbstractAIFDialog;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.stylesheet.PropertyPanel;
import com.teamcenter.rac.util.MessageBox;

public class QueryDialog extends AbstractAIFDialog implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// ��ǩ����ʾ��Ҫ����ItemID
	private JLabel lab_itemID;
	// ��ǩ����ʾ��Ҫ����ItemRevision
	private JLabel lab_revID;
	// �����
	private JTextField tf_itemID;
	// ����򣬻�ȡItemRevision
	private JTextField tf_revID;
	// ȷ����ť
	private JButton btn_OK;
	// ȡ����ť
	private JButton btn_cancle;
	// ��Ӹ�line��ť
	private JButton addParent;
	// �����line��ť
	private JButton addChild;
	// ��ʾTree�ṹ
	private JPanel treePanel;
	// ����Ŀռ�����
	private JPanel topPanel;

	private JTree tree;
	private DefaultMutableTreeNode topNode;

	private TCSession session;

	public QueryDialog() {
		super();
		session = (TCSession) AIFUtility.getCurrentApplication().getSession();
	}

	@Override
	public void run() {
		super.run();
		initUI();
	}

	private void initUI() {
		this.setTitle("�BOM��ͼ");
		// ��ʼ��һЩ�ؼ�
		this.lab_itemID = new JLabel("ItemID:");
		this.lab_revID = new JLabel("�汾ID:");
		this.tf_itemID = new JTextField(20);
		this.tf_revID = new JTextField(20);
		this.btn_OK = new JButton("ȷ��");
		this.btn_cancle = new JButton("ȡ��");
		this.addChild = new JButton("�����line");
		this.addParent = new JButton("��Ӹ�line");
		// �ȹ�����������
		this.topPanel = new PropertyPanel();
		topPanel.add("1.1.left.top", lab_itemID);
		topPanel.add("1.2.left.top", tf_itemID);
		topPanel.add("1.3.left.top", addParent);

		topPanel.add("2.1.left.top", lab_revID);
		topPanel.add("2.2.left.top", tf_revID);
		topPanel.add("2.3.left.top", addChild);

		this.add(topPanel);

		// �Tree�ṹ����
		treePanel = new JPanel(new FlowLayout());
		topNode = new DefaultMutableTreeNode("�BOM��ͼ�ṹ");
		tree = new JTree(topNode);
		treePanel.add(tree);

		// ��Button��Ӽ�����
		addParent.addActionListener(this);
		addChild.addActionListener(this);
		btn_OK.addActionListener(this);
		btn_cancle.addActionListener(this);
		// ���Tree������
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

				if (node == null)
					return;

				Object object = node.getUserObject();
				if (node.isLeaf()) {
				}
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object btn = e.getSource();
		// ����������Ӹ������ʲ�ѯ���������õ���������Ʋ�ѯ�����tree��
		if (btn.equals(addParent)) {
			QueryOperation operation;
			operation = new QueryOperation(session, tree);
			session.queueOperation(operation);
			this.disposeDialog();
			this.dispose();

		} else if (btn.equals(addChild)) {
			// ����������߼�����ӵ�tree��
			// ���ж�������Ƿ�Ϊ��,Ϊ�յĻ��򲻽��в�ѯ
			if (tf_itemID.getText() == null || "".equals(tf_itemID.getText())) {
				MessageBox.post("��������ȷ��ItemID", "����", MessageBox.ERROR);
			}
			if (tf_revID.getText() == null || "".equals(tf_revID.getText())) {
				MessageBox.post("��������ȷ��ItemRevisionID", "����", MessageBox.ERROR);
			}
			Dialog dialog = new Dialog(this);
			dialog.setTitle("��ѯ���");
			this.disposeDialog();
			this.dispose();
		} else if (btn.equals(btn_OK)) {
			// ȷ��BOM�ṹ�����ύ���ṹ����������ʾ
			this.disposeDialog();
			this.dispose();
		} else {
			this.disposeDialog();
			this.dispose();
		}
	}

}
