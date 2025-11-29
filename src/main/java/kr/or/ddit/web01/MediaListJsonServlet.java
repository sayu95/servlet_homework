package kr.or.ddit.web01;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//1. 사용자가 해당 경로에 접근하면
@WebServlet("/media/list")
public class MediaListJsonServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 로컬 dirpath설정
		Path dirPath = Paths.get("E:/00.medias");

		// 로컬 dirPath경로를 담는 리스트
		List<Path> dirPathlist = new ArrayList<>();

		// 로컬 dirPath경로의 dirName을 담는 리스트
		List<String> dirName = new ArrayList<>();

		// 2.서비스 구분
		// Path와 같이 쓰이는 Files의 list를 사용하려니 Stream으로 담아야헀었음.
		try (Stream<Path> dir = Files.list(dirPath)) {
			// Collectors를 import하여 List화하여 List<Path>를 반환하니 이를 List<Path> dirPathlist에 담음
			dirPathlist = dir.collect(Collectors.toList());
		}

		for (Path i : dirPathlist) {
			// Gson은 직렬화 할때 Path를 받지 않기때문에 오류가 발생했음
			// 따라서 toString함수로 String화하여 다시 담음
			dirName.add(i.getFileName().toString());
		}

		// 직렬화하고 Json에 담음
		String json = new Gson().toJson(dirName);

		// 3. 데이터 보내기
		// JSP에 보내기 위해 resp.getWriter() 호출
		// 향상된 try으로 바로 close();
		try (PrintWriter out = resp.getWriter()) {
			// 출력
			out.print(json);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// js에서 역직렬화한 json을 받기위한 gson 객체생성
		Gson gson = new Gson();

		// js에서 역직렬화한 json을 fromJson을 통해 가져온다 매개변수로
		// Reader, 제네릭타입이 필요한데 따로 db에서 가져오는것이 아니라
		// DTO로 받아오지않으며 K(String), V(String)이기 때문에 Map으로 받아온다
		Map<String, String> dir = gson.fromJson(req.getReader(), Map.class);

		// 키를 이용해 해당값을 가져온다.
		String dirName = dir.get("directory");

		Path dirPath = Paths.get("D:/00.medias");

		// 기존의 dirpath에 dirName을 합친 경로를 더한다.
		Path finalPath = dirPath.resolve(dirName);

		// finalPath의 data 자료를 담는 List
		List<Path> datalist = new ArrayList<>();

		// 직렬화한 Data
		List<String> data = new ArrayList<>();

		// 이제 합친 경로의 data를 모두 가져와야 한다.
		try (Stream<Path> finalPathData = Files.list(finalPath)) {
			datalist = finalPathData.collect(Collectors.toList());
		}

		for (Path i : datalist) {
			// Gson은 직렬화 할때 Path를 받지 않기때문에 오류가 발생했음
			// 따라서 toString함수로 String화하여 다시 담음
			data.add(i.getFileName().toString());
		}

		// 직렬화하고 Json에 담음
		// why? 다시 json형태로 jsp에 보내야하니까
		String json = new Gson().toJson(data);

		// 3. 데이터 보내기
		// JSP에 보내기 위해 resp.getWriter() 호출
		// 향상된 try으로 바로 close();
		try (PrintWriter out = resp.getWriter()) {
			// 출력
			out.print(json);
		}
	}
}
