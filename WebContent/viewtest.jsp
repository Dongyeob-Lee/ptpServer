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
			<TH>�̸��Ͼ��̵�</TH>
			<TH>��й�ȣ</TH>
			<TH>�̸�</TH>
			<TH>����</TH>
		</TR>
		<%
			//JDBC ����̹� �ε�
			Class.forName("com.mysql.jdbc.Driver");

			String jdbcDriver = "jdbc:mysql://localhost:3306/ptp?useUnicode=true&characterEncoding=euckr";
			String dbUser = "root";
			String dbPass = "ehdduq88";

			Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;

			String sql = "SELECT * FROM ptp_user";//sql ���ǹ�

			try {
				conn = DriverManager.getConnection(jdbcDriver, dbUser, dbPass);//����̺�Ŵ�����ü�� �̿��Ͽ� Ŀ�ؼǰ�ü�� ���´�.
				stmt = conn.createStatement();//Ŀ�ؼ� ��ü�� ����Ͽ� ������Ʈ��Ʈ ��ü�� �����.
				rs = stmt.executeQuery(sql);//���� Ÿ���� ResultSet ���ǹ��� ���� ����Ÿ���� �����Ѵ�. ex)Insert/delete/update�� excute();  
											//select�� excuteQuerty���

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
			} finally {//������ �ֵ� ���� ������ ����
				rs.close();
				stmt.close();
				conn.close();
			}
		%>

	</TABLE>
</body>
</html>