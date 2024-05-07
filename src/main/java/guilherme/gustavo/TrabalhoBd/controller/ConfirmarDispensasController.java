package guilherme.gustavo.TrabalhoBd.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ConfirmarDispensasController {

	@RequestMapping(name = "confirmarDispensa", value = "/confirmarDispensa", method = RequestMethod.GET)
	public ModelAndView confirmarDispensaGet(@RequestParam Map<String, String> param, ModelMap model) {
		return new ModelAndView("confirmarDispensa");
	}
	
	@RequestMapping(name = "confirmarDispensa", value = "/confirmarDispensa", method = RequestMethod.POST)
	public ModelAndView confirmarDispensaPost(@RequestParam Map<String, String> param, ModelMap model) {
		return new ModelAndView("confirmarDispensa");
	}
	
}
