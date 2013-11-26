<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.sql.DriverManager"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.Statement"%>
<%@ page import="java.sql.ResultSet"%>
<%
	request.setCharacterEncoding("euc-kr");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<TABLE border="1" width="500">
		<TR>
			<TH>이메일아이디</TH>
			<TH>비밀번호</TH>
			<TH>이름</TH>
			<TH>성별</TH>
		</TR>
		<%
			//JDBC 드라이버 로딩
			Class.forName("com.mysql.jdbc.Driver");

			String jdbcDriver = "jdbc:mysql://localhost:3306/ptp?useUnicode=true&characterEncoding=euckr";
			String dbUser = "root";
			String dbPass = "ehdduq88";

			Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;

			String sql = "SELECT * FROM ptp_user";//sql 질의문

			try {
				conn = DriverManager.getConnection(jdbcDriver, dbUser, dbPass);//드라이브매니저객체를 이용하여 커넥션객체를 얻어온다.
				stmt = conn.createStatement();//커넥션 객체를 사용하여 스테이트먼트 객체를 만든다.
				rs = stmt.executeQuery(sql);//리턴 타입이 ResultSet 질의문에 따라 리턴타입을 선별한다. ex)Insert/delete/update는 excute();  
											//select는 excuteQuerty사용

				while (rs.next()) {
		%>
		<TR>
			<TD><%=rs.getString("email_id")%></TD>
			<TD><%=rs.getString("password")%></TD>
			<TD><%=rs.getString("username")%></TD>
			<TD><%=rs.getString("phonenumber")%></TD>
		</TR>
		<%
			}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {//문제가 있든 없든 연결을 끊음
				rs.close();
				stmt.close();
				conn.close();
			}
		%>

	</TABLE>
</body>
</html>