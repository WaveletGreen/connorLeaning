package com.connor.study.handlers.queryItem;

import com.teamcenter.rac.aif.AbstractAIFOperation;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
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
import com.teamcenter.rac.kernel.TCTextService;
import com.teamcenter.rac.util.MessageBox;

public class AddRevisionToBOMWithQueryOperation extends AbstractAIFOperation {
	private InterfaceAIFComponent tccomponent = null;
	private String itemID;
	private TCSession session;
	private String RevID;

	public AddRevisionToBOMWithQueryOperation(InterfaceAIFComponent targetComponent, TCSession session, String itemName,
			String ItemDesc) {
		super();
		this.tccomponent = targetComponent;
		this.session = session;
		this.itemID = itemName;
		this.RevID = ItemDesc;
	}

	@Override
	public void executeOperation() throws Exception {
		if (!(tccomponent instanceof TCComponentItemRevision)) {
			MessageBox.post("请选择在零组件的版本下面挂载", "错误", MessageBox.WARNING);
			return;
		}
		if ("".equals(itemID) || itemID == null) {
			MessageBox.post("请输入Item名", "错误", MessageBox.WARNING);
			return;
		}
		if ("".equals(RevID) || RevID == null) {
			MessageBox.post("请输入RevID", "错误", MessageBox.WARNING);
			return;
		}

		TCTextService tcTextService = session.getTextService();
		String[] condition = { "item_revision_id", "item_id" };
		// searchItem:item_revision_id-item_revision_id|item_id-ID
		String[] pres = session.getPreferenceService().getStringValues("searchItem");
		pres[0].split("-");
		String asKey[] = tcTextService.getTextValues(condition);
		String asValues[] = { RevID, itemID };
		// 查询构建器找到item版本
		InterfaceAIFComponent interfaceAifComponent[] = session.search("searchItem", asKey, asValues);

		for (InterfaceAIFComponent interfaceAIFComponent2 : interfaceAifComponent) {
			System.out.println(interfaceAIFComponent2.getType());
		}

		TCComponentItemRevisionType itemType = (TCComponentItemRevisionType) session.getTypeComponent("ItemRevision");
		TCComponentItemRevision itemRev = (TCComponentItemRevision) interfaceAifComponent[0];// itemType.findRevisions(itemID,
																								// RevID);
		TCComponentItemRevision component = (TCComponentItemRevision) this.tccomponent;
		// 获取版本规则
		TCComponentRevisionRuleType ruleType = (TCComponentRevisionRuleType) session.getTypeComponent("RevisionRule");
		TCComponentRevisionRule rule = ruleType.getDefaultRule();
		// 根据版本规则创建BOMWindow
		TCComponentBOMWindowType bomWindow = (TCComponentBOMWindowType) session.getTypeComponent("BOMWindow");
		TCComponentBOMWindow window = bomWindow.create(rule);
		// 创建BOMLine
		// if (component instanceof TCComponentItemRevision) {
		TCComponentBOMLine bomLine = window.setWindowTopLine(component.getItem(), component, null, null);
		bomLine.add("view", itemRev);
		// }
		MessageBox.post("将" + itemID + "/" + RevID + "挂载在" + component + "的BOM下成功", "成功", MessageBox.INFORMATION);

	}

}
