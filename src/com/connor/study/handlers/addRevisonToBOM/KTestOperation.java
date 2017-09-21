package com.connor.study.handlers.addRevisonToBOM;


import com.teamcenter.rac.aif.AbstractAIFOperation;
import com.teamcenter.rac.aif.kernel.InterfaceAIFComponent;
import com.teamcenter.rac.kernel.TCComponentBOMLine;
import com.teamcenter.rac.kernel.TCComponentBOMWindow;
import com.teamcenter.rac.kernel.TCComponentBOMWindowType;
import com.teamcenter.rac.kernel.TCComponentFolder;
import com.teamcenter.rac.kernel.TCComponentFormType;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCComponentItemRevisionType;
import com.teamcenter.rac.kernel.TCComponentItemType;
import com.teamcenter.rac.kernel.TCComponentRevisionRule;
import com.teamcenter.rac.kernel.TCComponentRevisionRuleType;
import com.teamcenter.rac.kernel.TCComponentUser;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.rac.util.MessageBox;

public class KTestOperation extends AbstractAIFOperation {
	
	private TCSession session;
	private String filename;
	private InterfaceAIFComponent target;
	private String datasetname;
	private String desc;

	public KTestOperation(String datasetname, String desc, String filename, TCSession session,
			InterfaceAIFComponent target) {
		this.datasetname=datasetname;
		this.desc=desc;
		this.filename=filename;
		this.session=session;
		this.target=target;
	}

	@Override
	public void executeOperation() throws Exception {
		/*if(datasetname==null||"".equals(datasetname)) {
			MessageBox.post("请输入数据集的id！", "警告", MessageBox.WARNING);
			return;
		}
		if(this.filename.endsWith("Desktop")){  
            MessageBox.post("请选择文件！", "警告", MessageBox.WARNING);
			return;
        }*/
		/*TCComponentDatasetType datasetType=(TCComponentDatasetType) session.getTypeComponent("Text");
		TCComponentDataset dataset=datasetType.create(datasetname, desc, "Text");
		if(dataset==null) {
			MessageBox.post("创建失败", "错误", MessageBox.ERROR);
			return;
		}
		String as1[] = {filename};
		String as2[] = {"Text"};
		dataset.setFiles(as1, as2);*/
		
		
		TCComponentItemType itemTypeTest=(TCComponentItemType) session.getTypeComponent("Item");
		TCComponentItem[] count=itemTypeTest.findItems(datasetname);
		TCComponentItemRevisionType type2=(TCComponentItemRevisionType) session.getTypeComponent("ItemRevision");
		TCComponentItemRevision[] re=type2.findRevisions(datasetname, desc);
		TCComponentFormType type3=(TCComponentFormType) session.getTypeComponent("Form");
		
		
//		((TCComponentItem) target).getLatestItemRevision().add("IMAN_specification", dataset);
		if (target instanceof TCComponentFolder) {
//			((TCComponentFolder) target).add("contents", dataset);
//			MessageBox.post("Folder！", "成功", MessageBox.INFORMATION);
		} else if(target instanceof TCComponentItem){
			// 获取当前登陆的用户
			TCComponentUser user = this.session.getUser();
			// 通过用户获取newstuff文件夹
			TCComponentFolder newStuff = user.getNewStuffFolder();
			//获取bomline
			TCComponentRevisionRuleType imanRuleType=(TCComponentRevisionRuleType) session.getTypeComponent("RevisionRule");
			TCComponentRevisionRule imanRule=imanRuleType.getDefaultRule();
			TCComponentBOMWindowType bomType=(TCComponentBOMWindowType) session.getTypeComponent("BOMWindow");
			TCComponentBOMWindow bom=bomType.create(imanRule);
			TCComponentItem item=(TCComponentItem) target;
			TCComponentBOMLine bomline=bom.setWindowTopLine(item,item.getLatestItemRevision(),null,null);
			//MessageBox.post("test:"+bomline.toString(),"kk",MessageBox.INFORMATION);
			TCComponentItemType itemType=(TCComponentItemType) session.getTypeComponent("Item");
			TCComponentItem item1=itemType.create(itemType.getNewID(),itemType.getNewRev(null), "Item",
					datasetname, desc, null);
			bomline.add("view", item1.getLatestItemRevision());
			item=(TCComponentItem) itemType.getRelatedComponents()[0];
//			((TCComponentItem) target).getLatestItemRevision().add("IMAN_specification", dataset);
			MessageBox.post("添加bom视图成功"+item.toString(), "成功", MessageBox.INFORMATION);
		} else if(target instanceof TCComponentItemRevision){
//			((TCComponentItemRevision) target).add("IMAN_specification", dataset);
//			MessageBox.post("ItemRevision！", "成功", MessageBox.INFORMATION);
		}else {
			
//			newStuff.add("contents", dataset);
//			MessageBox.post("NewStuff！", "成功",MessageBox.INFORMATION);
		}
	}

}