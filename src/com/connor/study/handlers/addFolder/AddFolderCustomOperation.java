package com.connor.study.handlers.addFolder;

import com.teamcenter.rac.aif.AbstractAIFOperation;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentFolder;
import com.teamcenter.rac.kernel.TCComponentFolderType;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemType;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;

public class AddFolderCustomOperation extends AbstractAIFOperation {
	private TCComponent tccomponent = null;
	private String folderName = null;
	private TCSession session = null;
	private String describe;

	public AddFolderCustomOperation(TCSession session, TCComponent tccomponent, String folderName, String des) {
		this.tccomponent = tccomponent;
		this.folderName = folderName;
		this.session = session;
		this.describe = des;
	}

	@Override
	public void executeOperation() throws Exception {
		// TODO Auto-generated method stub
		TCComponentFolderType t = (TCComponentFolderType) session.getTypeComponent("Folder");
		TCComponentFolder f = t.create(folderName, describe, "Folder");
		tccomponent.add("contents", f);
		MessageBox.post("添加文件夹" + folderName + "成功", "成功", MessageBox.INFORMATION);
	}

}
