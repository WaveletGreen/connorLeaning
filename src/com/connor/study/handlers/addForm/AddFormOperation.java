package com.connor.study.handlers.addForm;

import com.teamcenter.rac.aif.AbstractAIFOperation;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentFolder;
import com.teamcenter.rac.kernel.TCComponentForm;
import com.teamcenter.rac.kernel.TCComponentFormType;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;

public class AddFormOperation extends AbstractAIFOperation {
	private InterfaceAIFComponent tccomponent = null;
	private String formName;
	private TCSession session;
	private String formDesc;

	public AddFormOperation(InterfaceAIFComponent targetComponent, TCSession session, String formName,
			String formDesc) {
		super();
		this.tccomponent = targetComponent;
		this.session = session;
		this.formName = formName;
		this.formDesc = formDesc;
	}

	@Override
	public void executeOperation() throws Exception {

		if ("".equals(formName) || formName == null) {
			MessageBox.post("请输入Item名", "错误", MessageBox.WARNING);
			return;
		}
		TCComponentFormType formType = (TCComponentFormType) session.getTypeComponent("Form");
		TCComponentForm form = formType.create(formName, formDesc, "Form");
		TCComponent component = (TCComponent) this.tccomponent;
		if (component instanceof TCComponentFolder) {
			component.add("contents", form);
		} else if (component instanceof TCComponentItem) {
			component.add("IMAN_reference", form);
		} else if (component instanceof TCComponentItemRevision) {
			component.add("IMAN_specification", form);
		}
		MessageBox.post("添加表单" + formName + "成功", "成功", MessageBox.INFORMATION);

	}

}
