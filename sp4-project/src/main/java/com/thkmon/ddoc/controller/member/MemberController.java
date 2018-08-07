package com.thkmon.ddoc.controller.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.h2.security.SHA256;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bb.logger.BBLogger;
import com.bb.logger.BBLoggerExample;
import com.thkmon.ddoc.service.member.MemberService;
import com.thkmon.exception.MsgException;
import com.thkmon.util.conv.AesUtil;
import com.thkmon.util.conv.ShaUtil;
import com.thkmon.util.data.ReqInfo;
import com.thkmon.util.date.DateUtil;
import com.thkmon.util.log.LogUtil;
import com.thkmon.util.result.ResultUtil;

@Controller
public class MemberController {

	@RequestMapping("/join")
	public String join(Model model, HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		String hint = DateUtil.getTodayDateTimeMilSec();
		String salt = AesUtil.getSalt();
		String iv = AesUtil.getIv(hint);
		
		model.addAttribute("hint", hint);
		model.addAttribute("salt", salt);
		model.addAttribute("iv", iv);
		
		return "ddoc/join/join_view";
	}
	
	
	@ResponseBody
	@RequestMapping("/reqjoin")
	public String reqjoin(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		try {
			LogUtil.debug("[begin] reqjoin");
			
			ReqInfo reqInfo = new ReqInfo(req);
			String emailInput = reqInfo.getParamNotEmpty("email_input");
			String nickInput = reqInfo.getParamNotEmpty("nick_input");
			String encpw = reqInfo.getParamNotEmpty("encpw");
			String hint = reqInfo.getParamNotEmpty("hint");
			
			reqInfo.checkAllMarked();
			emailInput = emailInput.toLowerCase();
			
			AesUtil aesUtil = new AesUtil();
			String passwd = aesUtil.decrypt(hint, encpw);
			
			String sha256password = new ShaUtil().convertToSHA256(passwd);
			
			LogUtil.debug("encpw : " + encpw);
			LogUtil.debug("sha256password : " + sha256password);
			
			boolean bResult = new MemberService().addMember(emailInput, nickInput, sha256password);
			if (!bResult) {
				return ResultUtil.makeErrorJSON("알 수 없는 오류가 발생하였습니다.");
			}
			
			LogUtil.debug("[end] reqjoin");
			
		} catch (MsgException e) {
			return ResultUtil.makeErrorJSON(e);
			
		} catch (Exception e) {
			throw e;
		}
		
		return ResultUtil.makeSuccessJSON();
	}
}
