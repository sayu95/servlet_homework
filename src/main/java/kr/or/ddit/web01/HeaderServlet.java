package kr.or.ddit.web01;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/proxy/http-headers")
public class HeaderServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		URI mediaURI = URI
				.create("https://developer.mozilla.org/ko/docs/Web/HTTP/Reference/Headers");
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest mediaReq = HttpRequest.newBuilder().uri(mediaURI).GET().build();
		
		try {
			HttpResponse<String> mediaResp = client.send(mediaReq, BodyHandlers.ofString());
			String html = mediaResp.body();
			Document document = Jsoup.parse(html);

			String iconAbbrSelector = 
                "abbr[title*=\"Experimental\"], abbr[title*=\"지원이 중단되었습니다\"], abbr[title*=\"비표준\"]";
			
			String unwantedItemSelector = 
			    "li:has(" + iconAbbrSelector + "), " +
			    "dt:has(" + iconAbbrSelector + "), " +
			    "dt:has(" + iconAbbrSelector + ") + dd";
			
			document.select(unwantedItemSelector).remove();

			String bodySelector = ".reference-layout__body";
			Elements elementBody = document.select(bodySelector);
			
			resp.setContentType("text/html");
            resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            resp.setHeader("Pragma", "no-cache");
            
			resp.getWriter().write(elementBody.outerHtml());
			
		} catch (IOException | InterruptedException e) {
		    throw new IOException(e);
		}
	}
}