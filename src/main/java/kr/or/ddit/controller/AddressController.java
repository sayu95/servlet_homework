package kr.or.ddit.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.dto.AddressDTO;
import kr.or.ddit.service.address.AddressService;
import kr.or.ddit.service.address.AddressServiceImpl;
import kr.or.ddit.validate.ValidateUtils;
import kr.or.ddit.validate.groups.DeleteGroup;

@WebServlet("/address")
public class AddressController extends HttpServlet {
    
    private AddressService service = new AddressServiceImpl();

    // 1. 조회 (목록 및 이름 검색)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = getUserName(req, resp);
        if (username == null) return;

        // 이름 검색 파라미터 확인 (Optional)
        String searchName = req.getParameter("searchName");

        try {
            List<AddressDTO> addressList;
            if (searchName != null && !searchName.trim().isEmpty()) {
            	
            	AddressDTO dto = AddressDTO.builder()
                        .memId(username)
                        .adrsName(searchName) 
                        .build();
            	
                addressList = service.readAddress(dto);
            } else {
                addressList = service.readAddressList(username);
            }

            if (addressList == null || addressList.isEmpty()) {
                req.setAttribute("message", "조회된 주소록 정보가 없습니다.");
            } else {
                req.setAttribute("addressList", addressList);
            }
            
            req.getRequestDispatcher("/WEB-INF/views/address.jsp").forward(req, resp);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    // 2. 생성 (등록)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = getUserName(req, resp);
        
        AddressDTO dto = AddressDTO.builder()
                .memId(username)
                .adrsName(req.getParameter("adrsName"))
                .adrsTel(req.getParameter("adrsTel"))
                .adrsAdd(req.getParameter("adrsAdd"))
                .adrsMail(req.getParameter("adrsMail"))
                .build();

        boolean success = service.createAddress(dto); // 서비스 구현 필요
        if(success) {
            resp.sendRedirect(req.getContextPath() + "/address"); // 등록 후 목록으로 이동
        } else {
            resp.sendError(500, "등록 실패");
        }
    }

    // 3. 수정 (Update)
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	String username = getUserName(req, resp);
        if (username == null) return;
        resp.setStatus(HttpServletResponse.SC_OK);
        
        AddressDTO updateDto = AddressDTO.builder()
                .adrsNo(Integer.parseInt(req.getParameter("adrsNo")))
                .memId(username)
                .adrsName(req.getParameter("adrsName"))
                .adrsTel(req.getParameter("adrsTel"))
                .adrsAdd(req.getParameter("adrsAdd"))
                .adrsMail(req.getParameter("adrsMail"))
                .build();
        try {
            boolean success = service.modifyAddress(updateDto);
            if (success) {
                resp.setStatus(HttpServletResponse.SC_OK); // 200 성공
            } else {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "수정 권한이 없습니다.");
            }
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    // 4. 삭제 (Delete)
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String adrsNo = req.getParameter("adrsNo");
        String username = getUserName(req, resp);
        
        AddressDTO dto = AddressDTO.builder()
                .adrsNo(Integer.parseInt(adrsNo))
                .memId(username) // 본인 확인용
                .build();
        
        boolean success = service.removeAddress(dto);
        
        if(success) {
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT); // 204 성공
        } else {
            resp.sendError(500);
        }
    }

    // 인증 공통 로직 추출
    private String getUserName(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Principal user = req.getUserPrincipal();
        if (user == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "인증 정보 만료");
            return null;
        }
        return user.getName();
    }
}