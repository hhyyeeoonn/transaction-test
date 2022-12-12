<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- View 없음 View가 없다면 jsp파일이 없어도 됨 View와 같은 역할을 해주는 메소드가 있음 -->
<%
	session.invalidate(); // 현재세션을 폐기하고 새로운 세션을 부여받음 => 세션정보가 사라짐 -> 로그아웃
	response.sendRedirect(request.getContextPath() + "/loginForm.jsp");
%>