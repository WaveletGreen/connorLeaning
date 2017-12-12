package com.connor.form;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.teamcenter.rac.aif.InterfaceAIFOperationListener;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.stylesheet.AbstractRendering;
import com.teamcenter.rac.util.PropertyLayout;

public class CustomItemRevisionForm extends AbstractRendering {
	private static final long serialVersionUID = -8147363520407626594L;
	private String[] properties = { "current_name", "user_data_1", "user_data_2", "user_data_3" };
	private String[] properties_ZN = { "当前名称", "用户数据1", "用户数据2", "用户数据3" };
	private TCComponent revision;

	public CustomItemRevisionForm(TCComponent arg0) throws Exception {
		super(arg0);
		revision =arg0;
		// if (arg0 instanceof TCComponentItemRevision) {
		//
		// } else {
		// return;
		// }
		loadRendering();
	}

	@Override
	public void loadRendering() throws TCException {
		init();
	}

	private void init() throws TCException {
		JPanel panel = new JPanel();
		panel.setLayout(new PropertyLayout());
		for (int i = 0; i < properties.length; i++) {
			JLabel label = new JLabel(properties_ZN[i]);
			JTextField field = new JTextField(16);
			// 把itemrevision的属性传到输入框中
			field.setText(revision.getProperty(properties[i]));
			if (properties[i].equals("current_name")) {
				field.setEditable(false);
			}
			panel.add(label, i + 1 + ".1.left.top");
			panel.add(field, i + 1 + ".2.left.top");
		}
		panel.setBackground(new Color(255, 255, 255));
		this.add(panel);
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setVisible(true);
	}

	@Override
	public void saveRendering() {

	}

	@Override
	public void saveRendering(InterfaceAIFOperationListener arg0) {
		super.saveRendering(arg0);
	}
	

}
