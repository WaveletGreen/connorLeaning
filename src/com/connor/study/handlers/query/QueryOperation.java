package com.connor.study.handlers.query;

import javax.swing.JTree;

import com.teamcenter.rac.aif.AbstractAIFOperation;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;

public class QueryOperation extends AbstractAIFOperation {

	private JTree tree;
	private TCSession session;

	public QueryOperation(TCSession session, JTree tree) {
		this.session = session;
		this.tree = tree;
	}

	@Override
	public void executeOperation() throws Exception {
		if (tree.getRowCount() > 1) {
			System.out.println("tree������-------"+tree.getRowCount());
		} else {
			MessageBox.post("��ѡ��һ��Item","����",MessageBox.ERROR);
		}
	}

}
