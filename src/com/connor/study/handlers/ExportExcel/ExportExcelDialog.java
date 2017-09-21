package com.connor.study.handlers.ExportExcel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileSystemView;

import com.connor.common.handler.bean.ReportBean;
import com.connor.common.handlerUtil.ExcelUtil07;
import com.teamcenter.rac.aif.AbstractAIFApplication;
import com.teamcenter.rac.aif.AbstractAIFDialog;
import com.teamcenter.rac.aif.kernel.AIFComponentContext;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentBOMLine;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCComponentType;
import com.teamcenter.rac.kernel.TCComponentUser;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCProperty;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.DateButton;
import com.teamcenter.rac.util.MessageBox;
import com.teamcenter.rac.util.PropertyLayout;

public class ExportExcelDialog extends AbstractAIFDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	private TCSession session = null;
	private AbstractAIFApplication app;
	private JLabel nameLabel;
	private JLabel startLabel;
	private DateButton startButton;
	private JLabel endLabel;
	private DateButton endButton;
	private JLabel fileLabel;
	private JTextField textField;
	private JFileChooser jFileChooser;
	private JButton pathButton;
	private JButton okButton;
	private JButton celButton;
	private List<ReportBean> beanList;
	private int number = 0;

	/**
	 * 构造函数,应该保留无参的构造函数方便反射调用
	 */
	public ExportExcelDialog() {
		super();
		this.app = AIFUtility.getCurrentApplication();
		this.session = (TCSession) app.getSession();
	}

	public void initUI() {

		// 得到系统的fileview
		FileSystemView fsv = FileSystemView.getFileSystemView();
		// 当前用户的桌面路径
		String deskPath = fsv.getHomeDirectory().getPath();

		this.setSize(new Dimension(600, 400));
		this.setTitle("图纸下发统计");

		this.nameLabel = new JLabel("发起时间：");
		this.startLabel = new JLabel("起");
		this.startButton = new DateButton();
		this.endLabel = new JLabel("止");
		this.endButton = new DateButton();
		this.fileLabel = new JLabel("导出位置：");
		this.textField = new JTextField(deskPath);

		jFileChooser = new JFileChooser();
		this.jFileChooser.setCurrentDirectory(new File(deskPath));// 文件选择器的初始目录定为当前用户桌面
		this.jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		this.pathButton = new JButton("...");
		this.okButton = new JButton("确定");
		this.celButton = new JButton("取消");

		addActionEvent();

		JPanel midJPanel = new JPanel(new PropertyLayout());
		// -------------线框
		// TitleBorder--------------------------------------------
		midJPanel.setBorder(new TitledBorder(BorderFactory.createLineBorder(Color.WHITE), "请选择日期"));
		midJPanel.add("1.1.left.top", nameLabel);
		midJPanel.add("1.2.left.top", startLabel);
		midJPanel.add("1.3.left.top", startButton);
		midJPanel.add("1.4.left.top", endLabel);
		midJPanel.add("1.5.left.top", endButton);
		midJPanel.add("2.1.left.top", fileLabel);
		midJPanel.add("2.2.left.top", textField);
		midJPanel.add("2.3.left.top", pathButton);

		JPanel rootJPanel = new JPanel(new FlowLayout());
		rootJPanel.add(okButton);
		rootJPanel.add(celButton);

		JPanel centerJPanel = new JPanel(new BorderLayout());
		centerJPanel.add(midJPanel, BorderLayout.CENTER);
		centerJPanel.add(rootJPanel, BorderLayout.SOUTH);

		this.setLayout(new BorderLayout());
		this.add(centerJPanel, BorderLayout.CENTER);
		this.setVisible(true);
		this.centerToScreen();
		this.pack();
		this.showDialog();

	}

	/**
	 * 对控件添加 ActionListener 事件监听
	 */
	public void addActionEvent() {
		this.pathButton.addActionListener(this);
		this.okButton.addActionListener(this);
		this.celButton.addActionListener(this);
	}

	/**
	 * 文件夹选择按钮事件
	 * 
	 * @param e
	 */
	public void selectFileButtonEvent() {
		jFileChooser.setFileSelectionMode(1);// 设定只能选择到文件夹
		int state = jFileChooser.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
		if (state == 1) {
			return;
		} else {
			File f = jFileChooser.getSelectedFile();// f为选择到的目录
			textField.setText(f.getAbsolutePath());
		}
	}

	/**
	 * 确定按钮事件
	 */
	public void okEvent() {

		SimpleDateFormat adf = new SimpleDateFormat("yyyy-M-dd HH:mm");
		Date startdatevalue = this.startButton.getDate();
		Date enddatevalue = this.endButton.getDate();

		InterfaceAIFComponent[] resultCompS = null;

		List<String> valueList = new ArrayList<String>();
		valueList.add("N");
		List<String> nameList = new ArrayList<String>();
		nameList.add("是否外发图纸");
		if (enddatevalue != null) {

			// valueList.add("N");
			valueList.add(adf.format(enddatevalue));
			nameList.add("发布日期早于");
			System.out.println("endvalue is" + adf.format(enddatevalue));
			// 调用查询
			// resultCompS = JFomMethodUtil.searchComponentsCollection(session,
			// "下发图纸统计",
			// new String[] { "是否外发图纸","发布日期早于" }, valueList.toArray(new
			// String[valueList.size()]));
		}
		if (startdatevalue != null) {
			// List<String> valueList = new ArrayList<String>();
			// valueList.add("N");
			valueList.add(adf.format(startdatevalue));
			nameList.add("发布日期晚于");
			// 调用查询
			// resultCompS = JFomMethodUtil.searchComponentsCollection(session,
			// "下发图纸统计",
			// new String[] { "是否外发图纸","发布日期晚于" }, valueList.toArray(new
			// String[valueList.size()]));
		}

		// System.out.println(app.getTargetComponent().toString());
		// 调用查询
		resultCompS = app.getTargetComponents();
		/*
		 * = JFomMethodUtil.searchComponentsCollection(session, "下发图纸统计",
		 * nameList.toArray(new String[nameList.size()]), valueList.toArray(new
		 * String[valueList.size()])); // 判断查询结果是否为空 if (resultCompS == null ||
		 * resultCompS.length == 0) { MessageBox.post("查询结果为空", "警告",
		 * MessageBox.WARNING); return; }
		 */
		/*
		 * if(!(resultCompS instanceof TCComponentItemRevision[])){
		 * MessageBox.post("请选择版本导出","警告",MessageBox.WARNING); return ; }
		 */

		// 如果选择的是Item，则全部导出版本
		List<TCComponent> revList = new ArrayList<TCComponent>();
		/*
		 * if (resultCompS.length == 1 && resultCompS[0] != null &&
		 * resultCompS[0] instanceof TCComponentItem) {
		 * 
		 * 
		 * 
		 * 
		 * 导出所有的版本，需要用到查询所有的版本
		 * 
		 * 
		 * 
		 * 
		 * 
		 * }
		 */
		// else
		{
			for (InterfaceAIFComponent comp : resultCompS) {
				revList.add((TCComponent) comp);
			}
			try {
				getRevMessage(revList);
			} catch (TCException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			InputStream is = ExportExcelDialog.class.getResourceAsStream("导出报表.xlsx");
			try {
				ExcelUtil07.writeExcel(textField.getText() + "\\" + "导出报表.xlsx", is, beanList);
				MessageBox.post("导出报表成功", "INFO", MessageBox.INFORMATION);

			} catch (IOException e1) {
				e1.printStackTrace();
				MessageBox.post("导出报表失败", "ERROR", MessageBox.ERROR);

			}
			// }

		}
		this.disposeDialog();
		this.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		Object sourceObj = arg0.getSource();
		if (sourceObj.equals(this.pathButton)) {
			selectFileButtonEvent();
		} else if (sourceObj.equals(this.okButton)) {
			// this.disposeDialog();
			// TransfProgressBarThread thread = new
			// TransfProgressBarThread("Export Report", "Process .....");
			// thread.start();
			// okEvent();
			ExportExcelOperation operation = new ExportExcelOperation(this);
			this.session.queueOperation(operation);
			// thread.stopBar();
			// this.showDialog();
		} else if (sourceObj.equals(this.celButton)) {
			this.disposeDialog();
			this.dispose();
		}
	}

	@Override
	public void run() {
		super.run();
		initUI();
	}

	/**
	 * 获取属性
	 * 
	 * @param revList
	 * @throws TCException
	 */
	public void getRevMessage(List<TCComponent> revList) throws TCException {
		if (revList == null) {
			System.out.println("revlist为空");
			return;
		}
		List<TCComponentItem> itemList = new ArrayList<TCComponentItem>();
		List<TCComponentItemRevision> relationList = new ArrayList<TCComponentItemRevision>();
		this.beanList = new ArrayList<ReportBean>();
		TCProperty[][] propssRev;
		boolean isBOMLine = false;
		if (revList.size() > 0 && revList.get(0) instanceof TCComponentBOMLine) {
			//获取一组对象的多个属性，参数1是多个对象的集合，参数2是需要获得的属性集（这些属性在对象中必须有，否则会返回null）
			//获取数组
			propssRev = TCComponentType.getTCPropertiesSet(revList,
					new String[] { "bl_rev_owning_user", "object_name", "bl_rev_date_released" });
			isBOMLine = true;
		} else {
			propssRev = TCComponentType.getTCPropertiesSet(revList,
					new String[] { "owning_user", "object_name", "date_released" });
			isBOMLine = false;
		}
		// 获取Item下的所有版本
		// TCComponentItem item=new TCComponentItem();
		// item.getChildren();
		SimpleDateFormat adf = new SimpleDateFormat("yyyy-M-dd HH:mm");
		// 如果是版本或者Item走这里
		if (!isBOMLine) {
			for (int i = 0; i < propssRev.length; i++) {

				TCComponentUser user = (TCComponentUser) propssRev[i][0].getReferenceValue();
				String ownuser = user.getStringProperty("user_name");
				// // 获取日期属性的值，用getDateValue()
				Date date = propssRev[i][2].getDateValue();
				String data = null;
				if (date != null) {
					data = adf.format(date);
				}
				// TCProperty iman = propssRev[i][2];
				// TCComponent[] values = iman.getReferenceValueArray();
				// if (values != null && values.length != 0) {
				// for (int num = 0; num < values.length; num++) {
				ReportBean bean = new ReportBean();
				TCComponentItemRevision ffsj = null;
				TCComponent component = (TCComponent) revList.get(i);
				if (component instanceof TCComponentItemRevision) {
					ffsj = (TCComponentItemRevision) component;
				} else if (component instanceof TCComponentItem) {
					ffsj = ((TCComponentItem) component).getLatestItemRevision();
				}
				/*
				 * else if (component instanceof TCComponentBOMLine) {
				 * ffsj=component; }
				 */
				String Objtype = ffsj.getStringProperty("object_type");
				if (Objtype.equals("ItemRevision") || Objtype.equals("Item")) {
					bean.setIndex("" + (number + 1));
					number++;
					String ffsjRev = ffsj.getTCProperty("item_revision_id").getStringValue();
					String ffsjItemId = ffsj.getItem().getProperty("item_id");
					String ffsjName = ffsj.getItem().getProperty("object_name");
					bean.setItem_revision_id(ffsjRev);
					bean.setItem_id(ffsjItemId);
					bean.setObject_name(ffsjName);
					if (ownuser != null) {
						bean.setOwning_user(ownuser);
					} else {
						bean.setOwning_user("");
					}
				}
				if (data != null) {
					bean.setDate_released(data);
				}
				beanList.add(bean);
			}
		}
		// 如果是结构管理器的BOMLine，获取的参数名字符串不一样
		else {
			for (int i = 0; i < propssRev.length; i++) {
				System.out.println(propssRev[i][0]);
				// TCComponentUser user = (TCComponentUser)
				// propssRev[i][0].getReferenceValue();
				String ownuser = propssRev[i][0].toString();
				// // 获取日期属性的值
				Date date = propssRev[i][2].getDateValue();
				String data = adf.format(date);

				// TCProperty iman = propssRev[i][2];
				// TCComponent[] values = iman.getReferenceValueArray();
				// if (values != null && values.length != 0) {
				// for (int num = 0; num < values.length; num++) {
				ReportBean bean = new ReportBean();
				TCComponentBOMLine ffsj = null;
				TCComponent component = (TCComponent) revList.get(i);
				if (component instanceof TCComponentBOMLine) {
					ffsj = (TCComponentBOMLine) component;
				} else
					continue;

				/*
				 * else if (component instanceof TCComponentBOMLine) {
				 * ffsj=component; }
				 */
				String Objtype = ffsj.getStringProperty("bl_object_type");
				System.out.println(Objtype);
				// if (Objtype.equals("BOMLine"))
				{
					bean.setIndex("" + (number + 1));
					number++;
					String ffsjRev = ffsj.getTCProperty("bl_rev_item_revision_id").getStringValue();
					String ffsjItemId = ffsj.getProperty("bl_item_item_id");
					String ffsjName = ffsj.getItem().getProperty("object_name");
					bean.setItem_revision_id(ffsjRev);
					bean.setItem_id(ffsjItemId);
					bean.setObject_name(ffsjName);
					if (ownuser != null) {
						bean.setOwning_user(ownuser);
					} else {
						bean.setOwning_user("");
					}
				}
				if (data != null) {
					bean.setDate_released(data);
				}
				beanList.add(bean);

				boolean hasChildren = ffsj.getTCProperty("bl_has_children").getBoolValue();
				if (hasChildren) {

					List<TCComponent> bl_children_list = new ArrayList<TCComponent>();
					AIFComponentContext[] bl_children_List = ffsj.getChildren();
					for (AIFComponentContext child : bl_children_List) {
						bl_children_list.add((TCComponent) child.getComponent());
					}
					getRevMessage(bl_children_list);
				}
			}

		}
	}
}
