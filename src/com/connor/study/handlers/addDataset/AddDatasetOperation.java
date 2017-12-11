package com.connor.study.handlers.addDataset;

import com.teamcenter.rac.aif.AbstractAIFOperation;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentDataset;
import com.teamcenter.rac.kernel.TCComponentDatasetType;
import com.teamcenter.rac.kernel.TCComponentFolder;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;

public class AddDatasetOperation extends AbstractAIFOperation {
	private InterfaceAIFComponent tccomponent = null;
	private String folderName = null;
	private TCSession session = null;
	private String describe;
	private String path;

	public AddDatasetOperation(InterfaceAIFComponent tccomponent, String folderName, TCSession session, String describe,
			String path) {
		super();
		this.tccomponent = tccomponent;
		this.folderName = folderName;
		this.session = session;
		this.describe = describe;
		this.path = path;
	}

	@Override
	public void executeOperation() throws Exception {

		if ("".equals(path) || path == null) {
			MessageBox.post("请选择文件", "错误", MessageBox.WARNING);
			return;
		}
		if (this.folderName == null || "".equals(this.folderName)) {
			MessageBox.post("请输入文件名", "错误", MessageBox.WARNING);
			return;
		}
		if (!this.path.endsWith("txt")) {
			MessageBox.post("选中的不是txt文件，请选择txt文件", "警告", MessageBox.WARNING);
			return;
		}

		String as1[] = { this.path };// 文件的物理路径
		String as2[] = { "Text" };
		
		TCComponentDatasetType t = (TCComponentDatasetType) session.getTypeComponent("Dataset");
		TCComponentDataset f = t.create(folderName, describe, "Text");
		f.setFiles(as1, as2);
		
		TCComponent component = (TCComponent) this.tccomponent;
		
		if (component instanceof TCComponentFolder) {
			component.add("contents", f);
		} else if (component instanceof TCComponentItem) {
			component.add("IMAN_reference", f);
		} else if (component instanceof TCComponentItemRevision) {
			component.add("IMAN_specification", f);
			component.getRelatedComponents("IMAN_specification");
		}
		MessageBox.post("数据集" + folderName + "成功", "成功", MessageBox.INFORMATION);

	}

}
