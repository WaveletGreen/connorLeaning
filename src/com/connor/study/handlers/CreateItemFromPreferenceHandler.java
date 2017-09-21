package com.connor.study.handlers;

import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCPreferenceService;
import com.teamcenter.rac.kernel.TCSession;

public class CreateItemFromPreferenceHandler {
	public void getPrefrence() {
		TCSession session = (TCSession) AIFUtility.getCurrentApplication().getSession();
		// 获取首选项
		TCPreferenceService service = session.getPreferenceService();
		// 获取首先项的值
		String preferenctValue = service.getStringValue("Custom_MyPreference");
		// 获取有多值的首选项
		service.getStringValues("Custom_MyPreference");
		// service.getPreferenceUsage(TCPreferenceService., arg1)
	}
}
