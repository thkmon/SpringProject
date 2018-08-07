package com.thkmon.ddoc.service.member;

import com.bb.mapper.BBMapper;
import com.thkmon.database.BBMapperPool;
import com.thkmon.ddoc.entity.member.DdocMember;
import com.thkmon.exception.MsgException;
import com.thkmon.util.date.DateUtil;

public class MemberService {
	
	public boolean addMember(String userId, String userName, String sha256password) throws Exception {
		
		boolean bResult = false;
		
		DdocMember member = new DdocMember();
		member.setUserId(userId);
		
		BBMapper mapper = BBMapperPool.getInstance();
		DdocMember oldMember = (DdocMember) mapper.selectSingleRow(member);
		if (oldMember != null) {
			throw new MsgException("이미 존재하는 사용자입니다.");
		}
		
		String regTime = DateUtil.getTodayDateTimeMilSec();
		String endTime = DateUtil.getEndDateTimeMilSec();
		
		member.setUserName(userName);
		member.setPassword(sha256password);
		member.setRegTime(regTime);
		member.setDelTime("");
		member.setBeginTime(regTime);
		member.setEndTime(endTime);
		member.setIsValid("1");
		
		int iResult = mapper.insert(member);
		if (iResult > 0) {
			bResult = true;
		}

		return bResult;
	}
}
