import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Message.Builder;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/**
 * Servlet implementation class DiaryServlet
 */
public class ptpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String REMOTE_REQUEST = "remote_request";
	public static final String REMOTE_ACCEPT = "remote_accept";
	public static final String REMOTE_REJECT = "remote_reject";
	public static final String SHARE_REQUEST = "share_request";
	public static final String SHARE_ACCEPT = "share_accept";
	public static final String SHARE_REJECT = "share_reject";
	public static final String API_KEY = "AIzaSyDhel_hP_qc1NNe8tbF8AYGG0IrgFhFzhg";
	String action;
	String email_id;
	String password;
	String username;
	String phonenumber;
	String reg_id;
	String ip;
	String sender_name;
	String reciever_name;

	String comments;
	String my_id;
	String your_id;
	String usermale;
	String userfemale;
	String title;
	String imgpath;
	
	int couple_id;
	boolean paramresult = false;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ptpServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("euc-kr");

		
		 String action = request.getParameter("action"); // 넘어 오는 액션에 따라 처리
		 System.out.println("action="+action);
		 if(action.equals("join")) { 
			 //회원가입 
			 email_id = request.getParameter("email_id");
			 username = request.getParameter("username");
			 password = request.getParameter("password");
			 phonenumber = request.getParameter("phonenumber");
			 reg_id = request.getParameter("reg_id");
			 join(response); 
		} else if(action.equals("login")) 
		{ //로그인 
			email_id = request.getParameter("email_id");
			password = request.getParameter("password");
			login(response); 
		} else if(action.equals("sendmsg")){
		//	sender_num = request.getParameter("sender_num");
		//	reciever_num = request.getParameter("reciever_num");
		//	sendMessage();
		}else if(action.equals("contacts")){
			contacts(response);
		}else if(action.equals(REMOTE_REQUEST)){
			//when server get request message
			//to send device
			sender_name = getUserNameByNum(request.getParameter("mynum"));
			reciever_name = request.getParameter("friendname");
			ip = request.getParameter("ip");
			System.out.println(sender_name);
			String receiverId = getUserRegIdByName(reciever_name);
			if(receiverId!=null){
				sendMessage(receiverId, ip);
			}
		}else if(action.equals(REMOTE_ACCEPT)){
			
		}else if(action.equals(REMOTE_REJECT)){
			
		}else if(action.equals(SHARE_REQUEST)){
			
		}else if(action.equals(SHARE_ACCEPT)){
			
		}else if(action.equals(SHARE_REJECT)){
			
		}
		
	}
	
	public String getUserNameByNum(String num){
		String name=null;
		Connection conn = null;
		Statement statement = null;
		ResultSet resultSet = null;
		DBCPUtils mysql = DBCPUtils.getInstance();
		String sql = "SELECT username FROM ptp_user where phonenumber='" + num
				+"'";

		try {
			conn = mysql.getConnection();
			statement = conn.createStatement();
			resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				name = resultSet.getString("username");
				//System.out.println(resultSet.getString("reg_id"));
			} else {
				System.out.println("실패");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return name;
	}
	//유저 이름으로 유저의 등록아이디를 받아온다. 
	public String getUserRegIdByName(String name){
		String regId=null;
		Connection conn = null;
		Statement statement = null;
		ResultSet resultSet = null;
		DBCPUtils mysql = DBCPUtils.getInstance();
		String sql = "SELECT reg_id FROM ptp_user where username='" + name
				+"'";

		try {
			conn = mysql.getConnection();
			statement = conn.createStatement();
			resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				regId = resultSet.getString("reg_id");
				System.out.println(resultSet.getString("reg_id"));
			} else {
				System.out.println("실패");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return regId;
	}

	private void sendMessage(String regId, String ip) {
		Sender sender = new Sender(API_KEY);
		
		//GCM onMessage 에서는 intent getString extra..
		Message.Builder builder = new Builder();
		builder.addData("action", REMOTE_REQUEST);
		builder.addData("sender", sender_name);
		builder.addData("ip", ip);
		Message message = builder.build();

		List<String> list = new ArrayList<String>();
		list.add(regId);

		MulticastResult multiResult;
		try {
			multiResult = sender.send(message, list, 5);
			if (multiResult != null) {
				List<Result> resultList = multiResult.getResults();

				for (Result result : resultList) {
					System.out.println(result.getMessageId());
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	private String makeXml(boolean paramresult) {
		String message = null;
		String title = "join";
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		stringBuffer.append("<items>");
		stringBuffer.append("<item>");
		stringBuffer.append("<title>" + title + "</title>");
		if (paramresult) {
			message = "true";
		} else {
			message = "false";
		}

		stringBuffer.append("\t<result>" + message + "</result>");
		// stringBuffer.append("\t<filemessage>" + filemessage +
		// "</filemessage>");

		stringBuffer.append("</item>");
		stringBuffer.append("</items>");

		return stringBuffer.toString();
	}

	private String makeXmlLogin(boolean paramresult) {
		String message = null;
		String title = "login";
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		stringBuffer.append("<items>");
		stringBuffer.append("<item>");
		stringBuffer.append("<title>" + title + "</title>");
		if (paramresult) {
			message = "true";
		} else {
			message = "false";
		}

		stringBuffer.append("\t<result>" + message + "</result>");
		// stringBuffer.append("\t<filemessage>" + filemessage +
		// "</filemessage>");

		stringBuffer.append("</item>");
		stringBuffer.append("</items>");

		return stringBuffer.toString();
	}

	private void join(HttpServletResponse response) {

		/*
		 * Enumeration params = multi.getParameterNames(); String mEmail =
		 * request.getParameter("email_id"); String mPassword =
		 * request.getParameter("password"); String mUsername =
		 * request.getParameter("username"); String mGender =
		 * request.getParameter("gender"); System.out.println(mEmail);
		 * System.out.println(mPassword); System.out.println(mUsername);
		 * System.out.println(mGender);
		 */
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {

			// Class.forName("com.mysql.jdbc.Driver");

			String sql = "insert into ptp_user(email_id,password,username,phonenumber,reg_id) values(?,?,?,?,?)";

			DBCPUtils mysql = DBCPUtils.getInstance();

			conn = mysql.getConnection();// 드라이브매니저객체를
											// 이용하여
											// 커넥션객체 // 얻어온다.
			pstmt = conn.prepareStatement(sql);// 미완성된 문장 상태로 얻어올수 있다.
			pstmt.setString(1, email_id);
			pstmt.setString(2, password);
			pstmt.setString(3, username);
			pstmt.setString(4, phonenumber); 
			pstmt.setString(5, reg_id);
			int result = pstmt.executeUpdate();

			if (result > 0) {
				paramresult = true;
				// response.sendRedirect("viewtest.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {// 문제가 있든 없든 연결을 끊음
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		response.setContentType("text/xml");
		response.setCharacterEncoding("euc-kr");
		try {
			response.getWriter().write(makeXml(paramresult));
			response.getWriter().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void login(HttpServletResponse response) {

		Connection conn = null;
		Statement statement = null;
		ResultSet resultSet = null;
		DBCPUtils mysql = DBCPUtils.getInstance();
		try {

			// Class.forName("com.mysql.jdbc.Driver");

			String sql = "SELECT * FROM ptp_user where email_id='" + email_id
					+ "' and password='" + password + "'";

			conn = mysql.getConnection();// 드라이브매니저객체를
											// 이용하여
											// 커넥션객체를
											// 얻어온다.
			statement = conn.createStatement();
			resultSet = statement.executeQuery(sql);

			if (resultSet.next()) {
				System.out.println("성공");
				paramresult = true;
			} else {
				System.out.println("실패");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {// 문제가 있든 없든 연결을 끊음
			try {
				resultSet.close();
				statement.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		response.setContentType("text/xml");
		response.setCharacterEncoding("euc-kr");
		try {
			response.getWriter().write(makeXmlLogin(paramresult));
			response.getWriter().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void contacts(HttpServletResponse response) {
		StringBuffer stringBuffer = new StringBuffer();
		Connection conn = null;
		Statement statement = null;
		ResultSet resultSet = null;
		DBCPUtils mysql = DBCPUtils.getInstance();
		try {

			// Class.forName("com.mysql.jdbc.Driver");

			String sql = "SELECT * FROM ptp_user";

			conn = mysql.getConnection();// 드라이브매니저객체를
											// 이용하여
											// 커넥션객체를
											// 얻어온다.
			statement = conn.createStatement();
			resultSet = statement.executeQuery(sql);
			
			
			stringBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			stringBuffer.append("<items>\n");
			int count = 0;
			while(resultSet.next()){
				stringBuffer.append("\t<item itemnum='"+count+"'>\n");
				
				stringBuffer.append("\t\t<name>" + resultSet.getString("username") + "</name>\n");
				stringBuffer.append("\t\t<phone>" + resultSet.getString("phonenumber") + "</phone>\n");
				stringBuffer.append("\t\t<register>true</register>\n");
				stringBuffer.append("\t</item>\n");
				count++;
			}
			stringBuffer.append("</items>");
			System.out.println(stringBuffer.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {// 문제가 있든 없든 연결을 끊음
			try {
				resultSet.close();
				statement.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		response.setContentType("text/xml");
		response.setCharacterEncoding("euc-kr");
		try {
			response.getWriter().write(stringBuffer.toString());
			response.getWriter().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
