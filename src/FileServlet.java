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
			// ���� ������ ���
			String key, value;
			Enumeration<String> enumer = mRequest.getParameterNames();
			while (enumer.hasMoreElements()) {
				key = enumer.nextElement();
				value = mRequest.getParameter(key);

				System.out.println(key + " : " + value);
			}

			// ���� �̸� ���
			File file;
			File rename = new File("picture1.jpg");
			enumer = mRequest.getFileNames();
			while (enumer.hasMoreElements()) {
				key = enumer.nextElement();
				file = mRequest.getFile(key);
				System.out.println(key + " : " + file.getName());

				// ���ϸ� ����
				// renameTo() �޼ҵ�� �÷��� ������ �޼ҵ��̹Ƿ� ����� ������ �Ǵ�����
				// �ݵ�� Ȯ���ؾ� �Ѵ�. ���� false�� ��ȯ�ȴٸ� ���� �������
				// �����ؾ� �Ѵ�. (�۹̼� ����)
				System.out.println("rename : " + file.renameTo(rename));

				System.out.println(file.getPath());

			}
		}

	}
}
