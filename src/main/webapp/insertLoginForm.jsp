<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>회원가입</h1>
	<form action = "<%=request.getContextPath()%>/insertLoginAction.jsp">
		<table border = "1">
			<tr>
				<td>ID</td>
				<td>
					<input type = "text" name = "id">
				</td>
			</tr>
			<tr>
				<td>Name</td>
				<td>
					<input type = "text" name = "name"> 
				</td>
			</tr>
			<tr>
				<td>Password</td>
				<td>
					<input type = "password" name = "pw">
				</td>
			</tr>
		</table>
		<button type = "submit">가입</button>
		<%
			if(request.getParameter("msg1") != null) {
		%>
				<%=request.getParameter("msg1")%>
		<%
			} else if(request.getParameter("msg2") != null) {
		%>
				<%=request.getParameter("msg2")%>
		<%
			}		
		%>
	</form>
</body>
</html>