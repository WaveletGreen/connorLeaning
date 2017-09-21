package com.connor.study.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.connor.common.handlerUtil.CommonHandler;

public class SetItemPropertyHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		CommonHandler handler = new CommonHandler("com.connor.study.handlers.setItenProperty.SetItemPropertyDialog");
		handler.CallCommonAction();
		return null;
	}

}
