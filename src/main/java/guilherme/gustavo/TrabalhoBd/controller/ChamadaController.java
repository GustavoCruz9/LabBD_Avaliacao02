package guilherme.gustavo.TrabalhoBd.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ChamadaController {

	@RequestMapping(name = "chamada", value = "/chamada", method = RequestMethod.GET)
	public ModelAndView chamadaGet(@RequestParam Map<String, String> param, ModelMap model) {
		return new ModelAndView("chamada");
	}
	
	@RequestMapping(name = "chamada", value = "/chamada", method = RequestMethod.POST)
	public ModelAndView chamadaPost(@RequestParam Map<String, String> param, ModelMap model) {
		return new ModelAndView("chamada");
	}
	
}
