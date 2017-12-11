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
			MessageBox.post("��ѡ����������İ汾�������", "����", MessageBox.WARNING);
			return;
		}
		if ("".equals(itemID) || itemID == null) {
			MessageBox.post("������Item��", "����", MessageBox.WARNING);
			return;
		}
		if ("".equals(RevID) || RevID == null) {
			MessageBox.post("������RevID", "����", MessageBox.WARNING);
			return;
		}

		TCTextService tcTextService = session.getTextService();
		String[] condition = { "item_revision_id", "item_id" };
		// searchItem:item_revision_id-item_revision_id|item_id-ID
		String[] pres = session.getPreferenceService().getStringValues("searchItem");
		pres[0].split("-");
		String asKey[] = tcTextService.getTextValues(condition);
		String asValues[] = { RevID, itemID };
		// ��ѯ�������ҵ�item�汾
		InterfaceAIFComponent interfaceAifComponent[] = session.search("searchItem", asKey, asValues);

		for (InterfaceAIFComponent interfaceAIFComponent2 : interfaceAifComponent) {
			System.out.println(interfaceAIFComponent2.getType());
		}

		TCComponentItemRevisionType itemType = (TCComponentItemRevisionType) session.getTypeComponent("ItemRevision");
		TCComponentItemRevision itemRev = (TCComponentItemRevision) interfaceAifComponent[0];// itemType.findRevisions(itemID,
																								// RevID);
		TCComponentItemRevision component = (TCComponentItemRevision) this.tccomponent;
		// ��ȡ�汾����
		TCComponentRevisionRuleType ruleType = (TCComponentRevisionRuleType) session.getTypeComponent("RevisionRule");
		TCComponentRevisionRule rule = ruleType.getDefaultRule();
		// ���ݰ汾���򴴽�BOMWindow
		TCComponentBOMWindowType bomWindow = (TCComponentBOMWindowType) session.getTypeComponent("BOMWindow");
		TCComponentBOMWindow window = bomWindow.create(rule);
		// ����BOMLine
		// if (component instanceof TCComponentItemRevision) {
		TCComponentBOMLine bomLine = window.setWindowTopLine(component.getItem(), component, null, null);
		bomLine.add("view", itemRev);
		// }
		MessageBox.post("��" + itemID + "/" + RevID + "������" + component + "��BOM�³ɹ�", "�ɹ�", MessageBox.INFORMATION);

	}

}
