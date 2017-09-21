package com.connor.study.handlers.addItem;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.connor.common.UIUtil.CommonUI;
import com.teamcenter.rac.aif.AbstractAIFOperation;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentFolder;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCComponentItemRevisionType;
import com.teamcenter.rac.kernel.TCComponentItemType;
import com.teamcenter.rac.kernel.TCPreferenceService;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;
import com.teamcenter.soa.internal.client.model.RevNameRuleImpl;

public class AddItemOperation extends AbstractAIFOperation {
	private InterfaceAIFComponent tccomponent = null;
	private String itemName;
	private TCSession session;
	private String ItemDesc;
	private CommonUI UI;

	public AddItemOperation(InterfaceAIFComponent targetComponent, TCSession session, String itemName,
			String ItemDesc) {
		super();
		this.tccomponent = targetComponent;
		this.session = session;
		this.itemName = itemName;
		this.ItemDesc = ItemDesc;
	}

	public AddItemOperation(InterfaceAIFComponent targetComponent, TCSession session, CommonUI UI) {
		this.tccomponent = targetComponent;
		this.session = session;
		this.UI = UI;
	}

	@Override
	public void executeOperation() throws Exception {
		StringBuilder builder = new StringBuilder();
		boolean errorCtrl = false;
		if (UI != null) {
			JTextField[] tf = UI.getTf_fields();
			JLabel[] labels = UI.getLab_labels();
			if (tf[0] == null || "".equals(tf[0].getText())) {
				builder.append(labels[0].getText() + "\t不能为空\n");
				errorCtrl = true;
			}
			if (tf[2] == null || "".equals(tf[2].getText())) {
				builder.append(labels[2].getText() + "\t不能为空\n");
				errorCtrl = true;
			}
			if (errorCtrl) {
				MessageBox.post(builder.toString(), "错误", MessageBox.ERROR);
				return;
			}

			TCComponentItemType itemType = (TCComponentItemType) session.getTypeComponent("Item");

			// Item的版本可通过ItenRevisionType的版本规则获取revisionID
			TCComponentItemRevisionType revisionType = (TCComponentItemRevisionType) this.session
					.getTypeComponent("ItemRevision");
			// 根据版本规则获取一个新的版本（标准化操作），也可以使用itemType.getNewRev(null)
			String revID = null;
			// 版本规则怎么获取？
			RevNameRuleImpl revRule = (RevNameRuleImpl) revisionType.getRevisionNamingRule();
			// 版本规则获取不了，下面这一行会出错
			// revID=revRule.getStartingRevision();
			System.out.println("---------------->" + revID);

			// 获取首选项
			TCPreferenceService service = session.getPreferenceService();
			// 获取首先项的值
			String preferenctValue = service.getStringValue("Custom_MyPreference");
			// 获取有多值的首选项
			// 根据首选项获取Item类型
			TCComponentItem item = itemType.create(tf[2].getText(), itemType.getNewRev(null),
					service.getStringValues("Custom_MyPreference")[0], tf[0].getText(), tf[1].getText(), null);
			TCComponent component = (TCComponent) this.tccomponent;
			if (component instanceof TCComponentFolder) {
				component.add("contents", item);
			} else if (component instanceof TCComponentItem) {
				component.add("IMAN_reference", item);
			} else if (component instanceof TCComponentItemRevision) {
				component.add("IMAN_specification", item);
			}
			MessageBox.post("添加零组件" + tf[0].getText() + "成功", "成功", MessageBox.INFORMATION);

		}

	}

}
