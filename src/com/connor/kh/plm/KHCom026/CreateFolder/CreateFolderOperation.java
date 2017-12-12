package com.connor.kh.plm.KHCom026.CreateFolder;

import java.util.ArrayList;
import java.util.List;

import com.teamcenter.rac.aif.AbstractAIFApplication;
import com.teamcenter.rac.aif.AbstractAIFOperation;
import com.teamcenter.rac.aif.kernel.AIFComponentContext;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.common.create.BOCreateDefinitionFactory;
import com.teamcenter.rac.common.create.CreateInstanceInput;
import com.teamcenter.rac.common.create.IBOCreateDefinition;
import com.teamcenter.rac.common.create.ICreateInstanceInput;
import com.teamcenter.rac.common.create.SOAGenericCreateHelper;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;

/**
 * �½�ά���ļ���
 * 
 * @author Administrator
 *
 */
public class CreateFolderOperation extends AbstractAIFOperation {
	/** ��Ҫ�����ļ��е����� */
	private String folderName;
	private TCSession session;
	private TCComponent target;
	private AbstractAIFApplication app;
	private final String type = "KH3_XM";

	public CreateFolderOperation() {
		app = AIFUtility.getCurrentApplication();
		session=(TCSession) app.getSession();
	}

	public void setFolderName(String FolderName) {
		this.folderName = FolderName;
	}

	/**
	 * �����ļ������߼�
	 */
	@Override
	public void executeOperation() throws Exception {
		target = (TCComponent) app.getTargetComponent();
		if (null == target) {
			MessageBox.post("��ѡ��һ���ļ���", "��Ϣ", MessageBox.INFORMATION);
			return;
		}
		// ���Ŀ�����������ļ���
		if (target.getChildrenCount() > 0) {
			System.out.println(target.getProperty("object_type"));
			// ����Ƿ���ͬ���ļ���
			for (AIFComponentContext child : target.getChildren()) {
				if (child.getComponent().getProperty("object_name").equals(folderName)) {
					MessageBox.post("������ͬ���ļ���", "����", MessageBox.ERROR);
					return;
				}
			}
		}
		// û��ͬ���ļ��У��ã������ļ���
		execute();

	}

	// soa�����ļ���
	private boolean execute() throws TCException {
		BOCreateDefinitionFactory bocdf = BOCreateDefinitionFactory.getInstance();
		IBOCreateDefinition iboCD=bocdf.getCreateDefinition(session, "Item");
		//.getCreateDefinition(session, type);
		CreateInstanceInput cii = new CreateInstanceInput(iboCD);
		ArrayList<ICreateInstanceInput> list = new ArrayList<>();
		list.add(cii);
		List<TCComponent> folderList = SOAGenericCreateHelper.create(session, iboCD, list);
		if (folderList.size() == 0) {
			MessageBox.post("�ļ��д���ʧ�ܣ�", "����", MessageBox.ERROR);
			return false;
		}
		// ����ļ��ж���-��ʵ����������TCComponentFolder
		TCComponent folder = folderList.get(0);
		folder.setStringProperty("object_name", folderName);
		// ���ļ��йҵ�Ŀ������
		if (!(target instanceof TCComponent)) {
			session.getUser().getNewStuffFolder().add("contents", folder);
			MessageBox.post("�ļ��д����ɹ���\n��ǰ����:" + target + " ����TCComponent����Ŀ�ļ���" + folderName + "���浽NewStuff�ļ�����", "��ʾ",
					MessageBox.WARNING);
			return false;
		}
		TCComponent targetComp = (TCComponent) target;
		targetComp.add(targetComp.getDefaultPasteRelation(), folder);
		MessageBox.post("�ļ��д����ɹ���\n��Ŀ�ļ���" + folderName + "���浽" + targetComp + "��", "��ʾ", MessageBox.INFORMATION);
		return true;
	}

}
