import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.CORBA.portable.ApplicationException;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class FileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	String action;

	String realFolder = "";
	String saveFolder = "dataroom/storage";
	String encType = "UTF-8";


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		ServletContext context = getServletContext();
		int postMaxSize = 10 * 1024 * 1024;

		MultipartRequest mRequest = new MultipartRequest( request,realFolder,postMaxSize,encType,new DefaultFileRenamePolicy());


//		MultipartRequest mRequest = new MultipartRequest(request, folderPath,
//				postMaxSize, encoding, new DefaultFileRenamePolicy());

		action = mRequest.getParameter("action");

		if (action.equals("file_transport")) {
			// 받은 데이터 출력
			String key, value;
			Enumeration<String> enumer = mRequest.getParameterNames();
			while (enumer.hasMoreElements()) {
				key = enumer.nextElement();
				value = mRequest.getParameter(key);

				System.out.println(key + " : " + value);
			}

			// 파일 이름 출력
			File file;
			File rename = new File("picture1.jpg");
			enumer = mRequest.getFileNames();
			while (enumer.hasMoreElements()) {
				key = enumer.nextElement();
				file = mRequest.getFile(key);
				System.out.println(key + " : " + file.getName());

				// 파일명 수정
				// renameTo() 메소드는 플랫폼 의존적 메소드이므로 제대로 동작이 되는지ß
				// 반드시 확인해야 한다. 만약 false가 반환된다면 복사 방식으로
				// 구현해야 한다. (퍼미션 관련)
				System.out.println("rename : " + file.renameTo(rename));

				System.out.println(file.getPath());

			}
		}

	}
}
