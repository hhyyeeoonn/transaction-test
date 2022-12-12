<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "vo.*" %>
<%@ page import = "dao.*" %>
<%@ page import="java.net.*" %>

<%
	//이미 로그인 되어있을 경우 웹브라우저에서 다시 main.jsp 재요청
	if(session.getAttribute("loginMember") != null) {
		response.sendRedirect(request.getContextPath()+"/main.jsp");
		return;
	}

	request.setCharacterEncoding("utf-8");
	String memberId = request.getParameter("id");
	String memberName = request.getParameter("name");
	String memberPw = request.getParameter("pw");
	
	if(memberId.equals("") || memberName.equals("") || memberPw.equals("")) { // 아이디나 이름이나 비밀번호를 입력하지 않았을 때
		String msg1=URLEncoder.encode("입력한 내용을 확인하세요", "utf-8");
		System.out.println("insertLoginAction:빈칸확인");
		response.sendRedirect(request.getContextPath() + "/insertLoginForm.jsp?msg1="+msg1);
		return;
	}
	
	Member member = new Member();
	member.setMemberId(memberId);
	member.setMemberName(memberName);
	member.setMemberPw(memberPw);
	
	
	// 모델호출
	MemberDao memberDao = new MemberDao();
	System.out.println("insertLoginAction:"+member.getMemberId());
	
	// 중복확인
	boolean id = memberDao.checkMemberId(member.getMemberId());
	if(id == false) {
		String msg2=URLEncoder.encode("이미 사용하고 있는 아이디입니다", "utf-8");
		System.out.println("insertLoginAction:중복확인");
		response.sendRedirect(request.getContextPath() + "/insertLoginForm.jsp?msg2="+msg2);
		return;
	}
	
	int row = memberDao.insertMember(member);
	System.out.println("insertLoginAction:가입완료");
	response.sendRedirect(request.getContextPath() + "/loginForm.jsp");
 %>