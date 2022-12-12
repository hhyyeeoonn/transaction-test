<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "vo.*" %>
<%@ page import = "service.*" %>

<!-- View 없음 -->
<% 
	// 로그인 되어있는 사용자는 들어올 수 없다
	// 유효성 검사 : 이 페이지 코드 계속해서 실행할건지
	// 로그인 되어있다면 이 페이지는 실행X
	if(session.getAttribute("loginMember") != null) {
		// 이미 로그인 되어있으니 웹브라우저에서 다시 main.jsp 재요청하시오
		response.sendRedirect(request.getContextPath()+"/main.jsp");
		return;
	}


	String memberId = request.getParameter("memberId");
	String memberPw = request.getParameter("memberPw");
	Member paramMember = new Member();
	paramMember.setMemberId(memberId);
	paramMember.setMemberPw(memberPw);
	
	//MemberDao memberDao = new MemberDao();
	// Member member = new MemberDao().login(paramMember);
//	Member member = memberDao.login(paramMember);
	
	MemberService memberService = new MemberService();
	Member member = memberService.login(paramMember);
	

	if(member == null) {
		// 로그인 실패
	} else {
		session.setAttribute("loginMember", member);
		response.sendRedirect(request.getContextPath() + "/main.jsp");
	}
%>
