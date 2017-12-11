package com.connor.study.handlers.queryItem;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.connor.common.handlerUtil.CommonAction;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aifrcp.AIFUtility;

public class AddRevisionToBOMWithQueryHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		AbstractAIFUIApplication app = AIFUtility.getCurrentApplication();
		CommonAction newFolderAction = new CommonAction(app,
				"com.connor.study.handlers.queryItem.AddRevisionToBOMWithQueryDialog");
		new Thread(newFolderAction).start();
		return null;
	}

}
