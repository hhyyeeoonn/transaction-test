<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "vo.*" %>
<%@ page import = "dao.*" %>
<% 
	// 유효성 검사 : 이 페이지 코드를 계속해서 실행해야하는가?
	// 로그인 되어 있다면, 밑에 나오는 페이지는 실행 X
	// 로그인 되어있는 사용자는 들어올 수 없다
	if(session.getAttribute("loginMember") != null) {
		// 이미 로그인 되어 있으니 웹브라우저에서 다시 main.jsp 재요청하시오
		response.sendRedirect(request.getContextPath() + "/main.jsp");
		return;
	}

%>

<!-- View 있음 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>로그인</h1>
	<form action = "loginAction.jsp" method = "post">
		<table border="1">
			<tr>
				<td>ID</td>
				<td>
					<input type = "text" name= "memberId">
				</td>
			</tr>
			<tr>
				<td>PW</td>
				<td>
					<input type = "password" name = "memberPw">
				</td>
			</tr>
		</table>
		<button type = "submit">로그인</button>
		<a href = "<%=request.getContextPath()%>/insertLoginForm.jsp">회원가입</a>
	</form>
</body>
</html>