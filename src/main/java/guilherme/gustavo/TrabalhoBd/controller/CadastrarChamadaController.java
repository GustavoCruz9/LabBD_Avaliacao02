package guilherme.gustavo.TrabalhoBd.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CadastrarChamadaController {

	@RequestMapping(name = "cadastrarChamada", value = "/cadastrarChamada", method = RequestMethod.GET)
	public ModelAndView cadastrarChamadaGet(@RequestParam Map<String, String> param, ModelMap model) {
		return new ModelAndView("cadastrarChamada");
	}
	
	@RequestMapping(name = "cadastrarChamada", value = "/cadastrarChamada", method = RequestMethod.POST)
	public ModelAndView cadastrarChamadaPost(@RequestParam Map<String, String> param, ModelMap model) {
//		int quantidade = 2;
//		
//		model.addAttribute("quantidade", quantidade);
		
		return new ModelAndView("cadastrarChamada");
	}
	
}
