package com.connor.study.handlers.addDataset;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

import com.teamcenter.rac.aif.AbstractAIFApplication;
import com.teamcenter.rac.aif.AbstractAIFDialog;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;
import com.teamcenter.rac.util.PropertyLayout;

public class AddDatasetDialog extends AbstractAIFDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TCSession session = null;
	private AbstractAIFApplication app;

	JButton pathButton = new JButton("...");

	JLabel folder = new JLabel("�ļ���");
	JTextField folderName = new JTextField(16);
	JLabel folderDesc = new JLabel("�ļ�����");
	JTextArea folderDescCxt = new JTextArea(6, 16);
	private String path;

	private JLabel label;
	private JTextField pathfield;
	private JButton okButton;
	private JButton celButton;

	// ����fileChooser��ͬʱҲ������·��
	private JFileChooser fileChooser = new JFileChooser();

	public AddDatasetDialog() {
		super();
		this.app = AIFUtility.getCurrentApplication();
		this.session = (TCSession) app.getSession();
	}

	public void initUI() {

		FileSystemView fsv = FileSystemView.getFileSystemView();
		// ��ǰ�û�������·��
		String deskPath = fsv.getHomeDirectory().getPath();

		this.setSize(new Dimension(600, 400));
		this.setTitle("�ϴ��ļ�");
		this.label = new JLabel("ѡ���ļ�:");
		this.pathfield = new JTextField(deskPath);

		this.pathButton = new JButton("...");
		this.fileChooser = new JFileChooser();

		this.fileChooser.setCurrentDirectory(new File(deskPath));// �ļ�ѡ�����ĳ�ʼĿ¼��Ϊ��ǰ�û�����
		this.fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		this.okButton = new JButton("ȷ��");
		this.celButton = new JButton("ȡ��");

		this.okButton.addActionListener(this);
		this.celButton.addActionListener(this);
		this.pathButton.addActionListener(this);

		JPanel centerPanel = new JPanel(new PropertyLayout());
		centerPanel.add("1.1.left.top", folder);
		centerPanel.add("1.2.left.top", folderName);
		centerPanel.add("2.1.left.top", folderDesc);
		centerPanel.add("2.2.left.top", folderDescCxt);

		centerPanel.add("3.1.left.top", label);

		centerPanel.add("3.2.left.top", pathfield);

		centerPanel.add("3.3.left.top", pathButton);

		JPanel rootJPanel = new JPanel(new FlowLayout());
		rootJPanel.add(okButton);
		rootJPanel.add(celButton);

		this.setLayout(new BorderLayout());
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(rootJPanel, BorderLayout.SOUTH);
		this.setVisible(true);
		this.centerToScreen();
		// this.pack();
		this.showDialog();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object btn = e.getSource();
		if (btn.equals(okButton)) {
			if (folderName.getText() != null && !"".equals(folderName.getText())) {
				AddDatasetOperation newFolderOperation;
				newFolderOperation = new AddDatasetOperation(app.getTargetComponent(), folderName.getText(), session,
						folderDescCxt.getText(), path);
				session.queueOperation(newFolderOperation, true);
				this.disposeDialog();
				this.dispose();

			} else {
				MessageBox.post("�ļ�������Ϊ��", "��ʾ ", MessageBox.ERROR);
			}
		} else if (btn.equals(pathButton)) {
			selectFileButtonEvent();
		} else {
			this.disposeDialog();
			this.dispose();
		}
	}

	public void selectFileButtonEvent() {
		int state = fileChooser.showOpenDialog(null);// �˾��Ǵ��ļ�ѡ��������Ĵ������
		if (state == 1) {
			return;
		} else {
			File f = fileChooser.getSelectedFile();// fΪѡ�񵽵�Ŀ¼
			path = f.getAbsolutePath();
		}
	}

	@Override
	public void run() {
		super.run();
		initUI();
	}

}
