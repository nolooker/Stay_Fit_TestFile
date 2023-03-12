package kh.project.stayfit.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import kh.project.stayfit.admin.model.service.AdminBoardService;
import kh.project.stayfit.admin.model.vo.AdminBoard;

@Controller
@RequestMapping("/admin")
public class AdminBoardController {
	
	@Autowired
	private AdminBoardService service;
	
	@GetMapping("/board")
	public ModelAndView board(ModelAndView mv, HttpServletRequest request) throws Exception {
		
		// 로그인을 하지 않은 경우나 admin이 아닌 사용자가 url로 접근하는 것 막는 코드
		if(request.getSession().getAttribute("mrole") == null || !('A' == (char)request.getSession().getAttribute("mrole"))){
			mv.addObject("func", "adminLogin");
			mv.addObject("msg", "관리자만 접근 가능한 페이지입니다.");
			mv.setViewName("/admin/resultAlert");
		} else {
			mv.addObject("currentMenu", 4);
			mv.setViewName("/admin/board");
		}
		return mv;
	}
	
	// 목록 출력
	@GetMapping("/boardlist")
	@ResponseBody
	public String selectBoard(@RequestParam(name="searchword", required=false) String searchword,
								@RequestParam("category") String category,
								@RequestParam(name="state", required=false) String state
								) throws Exception {
		
		List<AdminBoard> list = service.selectBoard(searchword, category, state);

		return new Gson().toJson(list);
	}
	
	// 게시물 삭제
	@PostMapping("/boarddelete")
	@ResponseBody
	public int deleteBoard(@RequestParam("bidList[]") List<String> bidList) throws Exception {
		
		int result = service.deleteBoard(bidList);

		return result;
	}
	
}