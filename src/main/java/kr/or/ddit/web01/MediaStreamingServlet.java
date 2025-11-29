package kr.or.ddit.web01;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/media/stream")
public class MediaStreamingServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//오류를 보낼 상태코드, 기본값은 200
		int statusCode = HttpServletResponse.SC_OK;

		Gson gson = new Gson();

		// js에서 역직렬화한 json을 fromJson을 통해 가져온다 매개변수로
		// Reader, 제네릭타입이 필요한데 따로 db에서 가져오는것이 아니라
		// DTO로 받아오지않으며 K(String), V(String)이기 때문에 Map으로 받아온다
		Map<String, String> data = gson.fromJson(req.getReader(), Map.class);

		String dirName = data.get("media");
		String fileName = data.get("data");

		//비동기 통신으로 가져온 dirName, fileName 값이 비어 있을 경우 무조건 오류
		if (dirName.isEmpty() || fileName.isEmpty()) {
            //필수 데이터가 누락되면 400 에러를 보냅니다.
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400

            // 클라이언트에게 오류 메시지를 JSON 형태로 보냅니다.
            try (PrintWriter out = resp.getWriter()) {
                out.print("{\"error\": \"400 Bad Request: media 또는 data 필드가 누락되었습니다.\"}");
            }
            return; // 이후 로직 실행 중단
        }

		// 확장자
		int dot = fileName.lastIndexOf('.');
		String extension = fileName.substring(dot + 1);

		Path dirPath = Paths.get("E:/00.medias/");

		// finalPath 설정
		Path finalPath = dirPath.resolve(dirName).resolve(fileName);

		// finalPath 설정이 존재한다면
		// 해당 dirName에 따라 inputSteam을 해야한다.
		// 문자 기반의 자원의 출력스트림 : resp.getWriter()
		// 어떤 기반의 자원의 출력스트림인지 모를 때 : resp.getOutputStream()

		if (Files.exists(finalPath)) {
			// dirName이 texts 라면
			if (dirName.equals("texts")) {
				resp.setContentType("text/plain; charset=UTF-8");

				// finalPath의 text파일을 Stream<String>으로 라인줄 별로 받는다 . 한글이 있을 수 있으니 UTF_8
				// 이 방식이 효율이 좋다하여 채용
				Stream<String> lineStream = Files.lines(finalPath, StandardCharsets.UTF_8);

				// PrinterWriter out 객체를 생성하니 반드시 닫아줘야한다. 향상된 try로 close까지 해결
				try (PrintWriter out = resp.getWriter()) {
					// forEach함수는 매개변수로 함수형 인터페이스 Consumer를 받는다.
					// 함수형 인터페이스란 단 하나의 추상 메서드만을 가지는 인터페이스를 말합니다.
					// Stream이 String 요소를 처리하는 메서드(forEach)를
					// 제공하고, 그 메서드가 **함수형 인터페이스(Consumer)**를 사용하도록 설계되었기 때문입니다.
					lineStream.forEach(line -> {
						System.out.println(line);
						out.println(line);
					});
					System.out.println("✅ 파일 스트림 전송 완료: " + finalPath.toString());
				}
			}
			// dirName이 images
			else if (dirName.equals("images")) {
				// 확장자 대/소문자 구분
				if (extension.equals("jpg") || extension.equals("JPG")) {
					// 컨탠츠타입 지정
					resp.setContentType("image/jpeg");
					// 텍스트가 아니니 getOutputStream() 사용
					// getOutputStream의 반환값은 ServletOutputStream이므로 이걸로 채용해봄
					try (ServletOutputStream os = resp.getOutputStream()) {
						//파일 copy
						Files.copy(finalPath, os);
					}
				}
				// 확장자 대/소문자 구분
				else if (extension.equals("png") || extension.equals("PNG")) {
					// 컨탠츠타입 지정
					resp.setContentType("image/png");
					// 텍스트가 아니니 getOutputStream() 사용
					// getOutputStream의 반환값은 ServletOutputStream이므로 이걸로 채용해봄
					try (ServletOutputStream os = resp.getOutputStream()) {
						//파일 copy
						Files.copy(finalPath, os);
					}
				}
			}

			// dirName이 movies
			else if (dirName.equals("movies")) {
				// 컨탠츠타입 지정
				resp.setContentType("video/mp4");
				// 텍스트가 아니니 getOutputStream() 사용
				// getOutputStream의 반환값은 ServletOutputStream이므로 이걸로 채용해봄
				try (ServletOutputStream os = resp.getOutputStream()) {
					//파일 copy
					Files.copy(finalPath, os);
				}

			// dirName이 icon
			} else {
				// 컨탠츠타입 지정
				resp.setContentType("image/x-icon");
				// 텍스트가 아니니 getOutputStream() 사용
				// getOutputStream의 반환값은 ServletOutputStream이므로 이걸로 채용해봄
				try (ServletOutputStream os = resp.getOutputStream()) {
					//파일 copy
					Files.copy(finalPath, os);
				}
			}
		}
	}
}
