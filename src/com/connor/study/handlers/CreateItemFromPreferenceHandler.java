package com.connor.study.handlers;

import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCPreferenceService;
import com.teamcenter.rac.kernel.TCSession;

public class CreateItemFromPreferenceHandler {
	public void getPrefrence() {
		TCSession session = (TCSession) AIFUtility.getCurrentApplication().getSession();
		// ��ȡ��ѡ��
		TCPreferenceService service = session.getPreferenceService();
		// ��ȡ�������ֵ
		String preferenctValue = service.getStringValue("Custom_MyPreference");
		// ��ȡ�ж�ֵ����ѡ��
		service.getStringValues("Custom_MyPreference");
		// service.getPreferenceUsage(TCPreferenceService., arg1)
	}
}
