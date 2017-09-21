package com.connor.study.handlers.ExportExcel;

import com.teamcenter.rac.aif.AbstractAIFOperation;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentFolder;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCComponentItemType;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;

public class ExportExcelOperation extends AbstractAIFOperation {
	private InterfaceAIFComponent tccomponent = null;
	private String itemName;
	private TCSession session;
	private String ItemDesc;

	private ExportExcelDialog dialog;
	public ExportExcelOperation(InterfaceAIFComponent targetComponent, TCSession session, String itemName,
			String ItemDesc) {
		super();
		this.tccomponent = targetComponent;
		this.session = session;
		this.itemName = itemName;
		this.ItemDesc = ItemDesc;
	}

	public ExportExcelOperation(ExportExcelDialog dialog) {
		super();
		this.dialog = dialog;
	}

	@Override
	public void executeOperation() throws Exception {
		dialog.okEvent();
	}

}
