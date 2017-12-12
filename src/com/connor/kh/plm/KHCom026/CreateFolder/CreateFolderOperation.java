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
 * 新建维护文件夹
 * 
 * @author Administrator
 *
 */
public class CreateFolderOperation extends AbstractAIFOperation {
	/** 需要创建文件夹的名字 */
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
	 * 创建文件夹主逻辑
	 */
	@Override
	public void executeOperation() throws Exception {
		target = (TCComponent) app.getTargetComponent();
		if (null == target) {
			MessageBox.post("请选择一个文件夹", "消息", MessageBox.INFORMATION);
			return;
		}
		// 如果目标下面有子文件夹
		if (target.getChildrenCount() > 0) {
			System.out.println(target.getProperty("object_type"));
			// 检查是否有同名文件夹
			for (AIFComponentContext child : target.getChildren()) {
				if (child.getComponent().getProperty("object_name").equals(folderName)) {
					MessageBox.post("不允许同名文件夹", "错误", MessageBox.ERROR);
					return;
				}
			}
		}
		// 没有同名文件夹，好，创建文件夹
		execute();

	}

	// soa创建文件夹
	private boolean execute() throws TCException {
		BOCreateDefinitionFactory bocdf = BOCreateDefinitionFactory.getInstance();
		IBOCreateDefinition iboCD=bocdf.getCreateDefinition(session, "Item");
		//.getCreateDefinition(session, type);
		CreateInstanceInput cii = new CreateInstanceInput(iboCD);
		ArrayList<ICreateInstanceInput> list = new ArrayList<>();
		list.add(cii);
		List<TCComponent> folderList = SOAGenericCreateHelper.create(session, iboCD, list);
		if (folderList.size() == 0) {
			MessageBox.post("文件夹创建失败！", "错误", MessageBox.ERROR);
			return false;
		}
		// 获得文件夹对象-真实对象类型是TCComponentFolder
		TCComponent folder = folderList.get(0);
		folder.setStringProperty("object_name", folderName);
		// 将文件夹挂到目标下面
		if (!(target instanceof TCComponent)) {
			session.getUser().getNewStuffFolder().add("contents", folder);
			MessageBox.post("文件夹创建成功！\n当前对象:" + target + " 不是TCComponent，项目文件夹" + folderName + "保存到NewStuff文件夹下", "提示",
					MessageBox.WARNING);
			return false;
		}
		TCComponent targetComp = (TCComponent) target;
		targetComp.add(targetComp.getDefaultPasteRelation(), folder);
		MessageBox.post("文件夹创建成功！\n项目文件夹" + folderName + "保存到" + targetComp + "下", "提示", MessageBox.INFORMATION);
		return true;
	}

}
