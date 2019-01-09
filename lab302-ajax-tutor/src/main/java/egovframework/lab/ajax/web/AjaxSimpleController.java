package egovframework.lab.ajax.web;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import egovframework.lab.com.view.AjaxSimpleView;


@Controller
public class AjaxSimpleController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AjaxSimpleController.class);

	private String[] keywords = { "김길동", "김나리", "김길수", "김엔젤" };

	@SuppressWarnings("unchecked")
	private List<String> search(String keyword) {
		if (keyword == null || keyword.equals(""))
			return java.util.Collections.EMPTY_LIST;
		keyword = keyword.toUpperCase();
		List<String> result = new ArrayList<String>(8);
		for (int i = 0; i < keywords.length; i++) {
			if (((String) keywords[i]).startsWith(keyword)) {
				result.add(keywords[i]);
			}
		}
		return result;
	}

	//TODO [Step 2-1-1] simpleAjax 메소드 완성하기 
	@RequestMapping(value = "/autocomplete.do")
	public ModelAndView simpleAjax(@RequestParam("keyword") String keyword) throws Exception{

    	ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setViewName("jsonView");
    	
    	String decode_keyword = URLDecoder.decode(keyword,"utf-8");

		List<?> keywordList = search(decode_keyword);
		
		LOGGER.debug("result >" + keywordList.toString());
		
		modelAndView.addObject("resultList", keywordList);

		return modelAndView;
	}
}
