package kr.or.ddit.web01;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/system-info") // URL 매핑 설정
public class ServerInfoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Properties props = System.getProperties();

        out.println("<!DOCTYPE html><html><head>");
        out.println("<meta charset='UTF-8'><title>System Information (forEach)</title>");
        out.println("<style>");
        out.println("table { width: 80%; border-collapse: collapse; margin: 20px auto; }");
        out.println("th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }");
        out.println("th { background-color: #f2f2f2; }");
        out.println("</style>");
        out.println("</head><body>");
        out.println("<table>");
        out.println("<tr><th>Property Name</th><th>Property Value</th></tr>");

        props.forEach((key, value) -> {
            String name = (String) key;
            String propValue = (String) value;

            out.println(String.format("<tr><td>%s</td><td>%s</td></tr>", name, propValue));
        });

        out.println("</table>");
        out.println("</body></html>");
    }
}