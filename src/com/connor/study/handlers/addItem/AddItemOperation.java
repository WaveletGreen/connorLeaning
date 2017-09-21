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
				builder.append(labels[0].getText() + "\t����Ϊ��\n");
				errorCtrl = true;
			}
			if (tf[2] == null || "".equals(tf[2].getText())) {
				builder.append(labels[2].getText() + "\t����Ϊ��\n");
				errorCtrl = true;
			}
			if (errorCtrl) {
				MessageBox.post(builder.toString(), "����", MessageBox.ERROR);
				return;
			}

			TCComponentItemType itemType = (TCComponentItemType) session.getTypeComponent("Item");

			// Item�İ汾��ͨ��ItenRevisionType�İ汾�����ȡrevisionID
			TCComponentItemRevisionType revisionType = (TCComponentItemRevisionType) this.session
					.getTypeComponent("ItemRevision");
			// ���ݰ汾�����ȡһ���µİ汾����׼����������Ҳ����ʹ��itemType.getNewRev(null)
			String revID = null;
			// �汾������ô��ȡ��
			RevNameRuleImpl revRule = (RevNameRuleImpl) revisionType.getRevisionNamingRule();
			// �汾�����ȡ���ˣ�������һ�л����
			// revID=revRule.getStartingRevision();
			System.out.println("---------------->" + revID);

			// ��ȡ��ѡ��
			TCPreferenceService service = session.getPreferenceService();
			// ��ȡ�������ֵ
			String preferenctValue = service.getStringValue("Custom_MyPreference");
			// ��ȡ�ж�ֵ����ѡ��
			// ������ѡ���ȡItem����
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
			MessageBox.post("��������" + tf[0].getText() + "�ɹ�", "�ɹ�", MessageBox.INFORMATION);

		}

	}

}
