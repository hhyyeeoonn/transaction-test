<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "vo.*" %>
<%@ page import = "dao.*" %>
<%
	//로그인 되어있는 사용자는 들어올 수 없다
	// 유효성 검사 : 이 페이지 코드 계속해서 실행할건지
	// 로그인 되어있다면 이 페이지는 실행X
	if(session.getAttribute("loginMember") != null) {
		// 이미 로그인 되어있으니 웹브라우저에서 다시 main.jsp 재요청하시오
		response.sendRedirect(request.getContextPath()+"/main.jsp");
		return;
	}

	//로그인 되어있다면 세션정보를 가져온다
	// Object obj = session.getAttribute("loginMember");
	// Member loginMember = (Member)obj;
	Member loginMember = (Member)(session.getAttribute("loginMember"));
	
	MemberDao memberDao = new MemberDao();
	// int row 가 2이면 탈퇴성공
	int row = memberDao.deleteMember(loginMember.getMemberId());
	if(row == 2) {
		System.out.println("deleteMember:탈퇴성공");
	} else {
		System.out.println("deleteMember:탈퇴실패");
		response.sendRedirect(request.getContextPath() + "/main.jsp");
	}



%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>