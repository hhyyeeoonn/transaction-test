<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "vo.*"%>
<%@ page import = "dao.* %>
<!-- VIEW가 있음 -->
<%
	// 유효성검사	
	//로그인이 되어 있다면 세션정보를 가져온다
	if(session.getAttribute("longinMember") != null) {// return은 메소드를 끝내는 역할 반환할 값은 리턴 뒤에 적어주기
		response.sendRedirect(request.getContextPath() +"/loginForm.jsp");
		// 이미 로그인 되어있으니 웹브라이즈에서 다시 main.jsp 재요청하시오
		return;
	}

	//로그인 되어있다면 세션정보를 가져온다
	// Object obj = session.getAttribute("loginMember");
	// Member loginMember = (Member)obj;
	Member loginMember = (Member)(session.getAttribute("loginMember"));

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div><%=loginMember.getMemberName()%>님 반갑습니다.</div>
	<div><a href="<%=request.getContextPath()%>/logout.jsp">로그아웃</a></div>
	<div><a href="<%=request.getContextPath()%>/deleteMember.jsp">회원탈퇴</a></div>
</body>
</html>