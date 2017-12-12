package com.connor.kh.plm.KHCom026.CreateFolder;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.connor.common.handlerUtil.CommonHandler;

/**
 * 新建维护文件夹
 * 
 * @author Administrator
 *
 */
public class CreateFolderHandler extends AbstractHandler {
	@Override
	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		CommonHandler action = new CommonHandler("com.connor.kh.plm.KHCom026.CreateFolder.CreateFolderDialog");
		action.CallCommonAction();
		return null;
	}

}
