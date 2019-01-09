package egovframework.lab.ajax.web;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sourceforge.ajaxtags.xml.AjaxXmlBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import egovframework.lab.ajax.service.DepartmentService;
import egovframework.lab.ajax.service.EmployeeService;
import egovframework.lab.com.view.AjaxXmlView;
import egovframework.lab.com.vo.Department;


@Controller
public class AjaxController {
	
	@Autowired
	@Qualifier("employeeService")
	private EmployeeService employeeService;

	
	//사원정보 리스트 페이지에서 검색입력창(사원이름)에 사용되는 자동완성기능
	@RequestMapping("/suggestName.do")
	protected ModelAndView suggestName(@RequestParam("searchName") String searchName){
		
		ModelAndView model = new ModelAndView(new AjaxXmlView());
		// TODO [Step 2-2-2] employeeService.getNameListForSuggest 메소드를 호출하여 결과를 가져온다.
		List<String> nameList = employeeService.getNameListForSuggest(searchName);
		
		AjaxXmlBuilder ajaxXmlBuilder = new AjaxXmlBuilder();
		
		for(String name:nameList){
			ajaxXmlBuilder.addItem(name, name, false);
		}
		model.addObject("ajaxXml",ajaxXmlBuilder.toString());
		return model;
	}
	

	@Resource(name="departmentService")
	private DepartmentService departmentService;
	
	//linked selectbox 처리 기능
	@RequestMapping("/autoSelectDept.do")
	protected ModelAndView getDeptInfoforSelectTag(HttpServletRequest request) throws Exception{
		
    	ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setViewName("jsonView");
		
    	String superdeptid = URLDecoder.decode(request.getParameter("superdeptid"),"utf-8");
    	String depth = URLDecoder.decode(request.getParameter("depth"),"utf-8");
    	
		Map<String, String> param = new HashMap<String, String>();
		param.put("superdeptid",superdeptid);
		param.put("depth",depth);
		List<Department> deptList = departmentService.getDepartmentList(param);
		
		modelAndView.addObject("depthList", deptList);
		
		return modelAndView;
	}
}
