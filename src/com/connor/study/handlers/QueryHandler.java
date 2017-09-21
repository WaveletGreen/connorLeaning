package com.connor.study.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.connor.common.handlerUtil.CommonAction;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aifrcp.AIFUtility;

/**
 * ²éÑ¯¹¹½¨Æ÷
 * */
public class QueryHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		AbstractAIFUIApplication app = AIFUtility.getCurrentApplication();
		CommonAction action = new CommonAction(app, 
				"com.connor.study.handlers.query.QueryDialog");
		new Thread(action).start();
		return null;
	}

}
