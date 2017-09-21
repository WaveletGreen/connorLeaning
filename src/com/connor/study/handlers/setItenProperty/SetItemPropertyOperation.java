package com.connor.study.handlers.setItenProperty;

import com.teamcenter.rac.aif.AbstractAIFOperation;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentBOMLine;
import com.teamcenter.rac.kernel.TCComponentBOMLineType;
import com.teamcenter.rac.kernel.TCComponentBOMWindow;
import com.teamcenter.rac.kernel.TCComponentBOMWindowType;
import com.teamcenter.rac.kernel.TCComponentFolder;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCComponentItemRevisionType;
import com.teamcenter.rac.kernel.TCComponentItemType;
import com.teamcenter.rac.kernel.TCComponentRevisionRule;
import com.teamcenter.rac.kernel.TCComponentRevisionRuleType;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;

public class SetItemPropertyOperation extends AbstractAIFOperation {
	private InterfaceAIFComponent tccomponent = null;
	private String itemProperty;
	private TCSession session;

	public SetItemPropertyOperation(InterfaceAIFComponent targetComponent, TCSession session, String itemProperty) {
		super();
		this.tccomponent = targetComponent;
		this.session = session;
		this.itemProperty = itemProperty;
	}

	@Override
	public void executeOperation() throws Exception {
		if (!(tccomponent instanceof TCComponentItem)) {
			MessageBox.post("请选择Item", "错误", MessageBox.WARNING);
			return;
		}
		if ("".equals(itemProperty) || itemProperty == null) {
			MessageBox.post("请输入属性值", "错误", MessageBox.WARNING);
			return;
		}
		TCComponentItem item=(TCComponentItem) tccomponent;
		item.refresh();
		item.lock();
		((TCComponentItem) tccomponent).setProperty("object_name",itemProperty);
		item.save();
		item.unlock();
		item.refresh();
		MessageBox.post("设置" + tccomponent.toString() + "属性" + itemProperty, "成功", MessageBox.INFORMATION);

	}

}
