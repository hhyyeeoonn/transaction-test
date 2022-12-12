<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "vo.*" %>
<%@ page import = "service.*" %>
<%
	// 로그인 되어있다면 세션정보를 가져온다
	// Object obj = session.getAttribute("loginMember");
	// Member loginMember = (Member)obj;
	Member loginMember = (Member)(session.getAttribute("loginMember"));
	/*
	OutIdDao outidDao = new OutIdDao();
	MemberDao memberDao = new MemberDao();
	*/
	MemberService memberService = new MemberService();
	
	// 2 탈퇴성공
	int row = 0;
	/*
	if(outidDao.insertMemberId(loginMember.getMemberId()) == 1) { // 입력성공시 
	   row = memberDao.deleteMember(loginMember.getMemberId());
	} // --> 트랜잭션 처리 X
	*/
	
	row = memberService.deleteMember(loginMember.getMemberId());
	
	if(row == 1) { // 탈퇴성공
	   System.out.println("탈퇴성공!");
	   session.invalidate();
	   response.sendRedirect(request.getContextPath()+"/loginForm.jsp");
	} else { // 탈퇴실패
	   System.out.println("탈퇴실패!");
	   response.sendRedirect(request.getContextPath()+"/main.jsp");
	}
%>