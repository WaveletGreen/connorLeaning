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
	// 标签，显示需要输入ItemID
	private JLabel lab_itemID;
	// 标签，显示需要输入ItemRevision
	private JLabel lab_revID;
	// 输入框
	private JTextField tf_itemID;
	// 输入框，获取ItemRevision
	private JTextField tf_revID;
	// 确定按钮
	private JButton btn_OK;
	// 取消按钮
	private JButton btn_cancle;
	// 添加父line按钮
	private JButton addParent;
	// 添加子line按钮
	private JButton addChild;
	// 显示Tree结构
	private JPanel treePanel;
	// 顶层的空间容器
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
		this.setTitle("搭建BOM草图");
		// 初始化一些控件
		this.lab_itemID = new JLabel("ItemID:");
		this.lab_revID = new JLabel("版本ID:");
		this.tf_itemID = new JTextField(20);
		this.tf_revID = new JTextField(20);
		this.btn_OK = new JButton("确定");
		this.btn_cancle = new JButton("取消");
		this.addChild = new JButton("添加子line");
		this.addParent = new JButton("添加父line");
		// 先构建顶层容器
		this.topPanel = new PropertyPanel();
		topPanel.add("1.1.left.top", lab_itemID);
		topPanel.add("1.2.left.top", tf_itemID);
		topPanel.add("1.3.left.top", addParent);

		topPanel.add("2.1.left.top", lab_revID);
		topPanel.add("2.2.left.top", tf_revID);
		topPanel.add("2.3.left.top", addChild);

		this.add(topPanel);

		// 搭建Tree结构容器
		treePanel = new JPanel(new FlowLayout());
		topNode = new DefaultMutableTreeNode("搭建BOM草图结构");
		tree = new JTree(topNode);
		treePanel.add(tree);

		// 给Button添加监听器
		addParent.addActionListener(this);
		addChild.addActionListener(this);
		btn_OK.addActionListener(this);
		btn_cancle.addActionListener(this);
		// 添加Tree监听器
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
		// 如果点击了添加父，访问查询构建器，得到结果并绘制查询结果到tree上
		if (btn.equals(addParent)) {
			QueryOperation operation;
			operation = new QueryOperation(session, tree);
			session.queueOperation(operation);
			this.disposeDialog();
			this.dispose();

		} else if (btn.equals(addChild)) {
			// 插入添加子逻辑，添加到tree下
			// 先判断输入框是否为空,为空的话则不进行查询
			if (tf_itemID.getText() == null || "".equals(tf_itemID.getText())) {
				MessageBox.post("请输入正确的ItemID", "错误", MessageBox.ERROR);
			}
			if (tf_revID.getText() == null || "".equals(tf_revID.getText())) {
				MessageBox.post("请输入正确的ItemRevisionID", "错误", MessageBox.ERROR);
			}
			Dialog dialog = new Dialog(this);
			dialog.setTitle("查询结果");
			this.disposeDialog();
			this.dispose();
		} else if (btn.equals(btn_OK)) {
			// 确认BOM结构，并提交到结构管理器上显示
			this.disposeDialog();
			this.dispose();
		} else {
			this.disposeDialog();
			this.dispose();
		}
	}

}
