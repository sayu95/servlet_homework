package controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet(value = "/mbti", loadOnStartup = 1)
@MultipartConfig
public class MbtiServlet extends HttpServlet {

	private String mbtiURI = "/WEB-INF/views/mbti/";
	private ServletContext application;
	private Map<String, String> mbtiMap;
	private Path dirPath;

	// 이후 배포환경에서의 경로를 맞춰주기 위해
	// 톰캣의 realPath을 이용한다.
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ServletContext application = getServletContext();
		String realPath = application.getRealPath(mbtiURI);
		System.out.println(realPath);
		mbtiMap = application.getResourcePaths(mbtiURI).stream().map(FilenameUtils::getName)
				// intj, intj.html
				.collect(Collectors.toMap(FilenameUtils::getBaseName, Function.identity()));
		dirPath = Paths.get(realPath);
		application.setAttribute("mbtiMap", mbtiMap);
		application.setAttribute("mbtiURI", mbtiURI);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String view = "/WEB-INF/views/mbtiView.jsp";
		req.getRequestDispatcher(view).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * 문자기반 파트(mtType-entj) -> mbtiMap 새로운 엔트리의 key가 됨. 파일기반 파트(mtFile-entj.html
		 * binary) -> mbtiMap stream copy로 파일 업로드 (path(directory, file), Files) mbtiMap
		 * 새로운 엔트리의 value로 filename이 사용됨.
		 */
		// 1.<input type="text" name="mtType" placeholder="entj" /> 데이터 가져오기
		String mtType = req.getParameter("mtType");

		// 2.<input type="file" name="mtFile" placeholder="entj.html"/> 데이터 가져오기
		Part mtFile = req.getPart("mtFile");

		boolean valid = validate(mtType, mtFile, req);

		if (!valid) {
			resp.sendError(400);
			return;
		}

//		1) 업로드 파일 저장 디렉토리 결정. 
//		2) 업로드 파일명 결정 : 절대로 원본 파일명으로 저장하지 말것. 
//		3) stream copy로 업로드 처리.
		String filename = UUID.randomUUID().toString();
		Path filePath = dirPath.resolve(filename);

		try (InputStream is = mtFile.getInputStream()) {
			Files.copy(is, filePath);
		}
		mbtiMap.put(mtType, filename);
		
		//Post - Redirect - Get >> PRG pattern (멱등성 유지 방법)
		String location = req.getContextPath() + "/mbti";
		resp.sendRedirect(location);
	}

	private boolean validate(String mtType, Part mtFile, HttpServletRequest req) {
		boolean valid = true;
		if (StringUtils.isBlank(mtType)) {
			valid = false;
		}

		if (mtFile == null || StringUtils.isBlank(mtFile.getSubmittedFileName())) {
			valid = false;
		}
		return valid;
	}
}
