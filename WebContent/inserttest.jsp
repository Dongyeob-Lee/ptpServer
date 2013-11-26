<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%request.setCharacterEncoding("euc-kr"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>test</title>
</head>
<body>
	<FORM METHOD="POST" ACTION="diary.servlet">
	email :<INPUT TYPE="text" NAME="email_id" size="20"><BR>
	password :<INPUT TYPE="text" NAME="password" size="80"><BR>
	이름 :<INPUT TYPE="text" NAME="username" size="50"><BR>
	성별:<INPUT TYPE="text" NAME="gender" size="50"><BR>
	<INPUT TYPE="submit" value="로그인">
	<INPUT TYPE="reset" value="취소">
	</FORM>
</body>
</html>