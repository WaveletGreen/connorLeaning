package com.connor.study.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.connor.common.handlerUtil.CommonAction;
import com.connor.common.handlerUtil.CommonHandler;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aifrcp.AIFUtility;

public class AddDatasetHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		CommonHandler action = new CommonHandler("com.connor.study.handlers.addDataset.AddDatasetDialog");
		action.CallCommonAction();
		return null;
	}

}
