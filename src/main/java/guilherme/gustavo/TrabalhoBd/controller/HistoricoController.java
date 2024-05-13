package guilherme.gustavo.TrabalhoBd.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import guilherme.gustavo.TrabalhoBd.model.Aluno;
import guilherme.gustavo.TrabalhoBd.model.Matricula;
import guilherme.gustavo.TrabalhoBd.persistence.HistoricoDao;

@Controller
public class HistoricoController {
	
	@Autowired
	private HistoricoDao hDao;
	
	@RequestMapping(name = "historico", value = "/historico", method = RequestMethod.GET)
	public ModelAndView historicoGet(@RequestParam Map<String, String> param, ModelMap model) {
		return new ModelAndView("historico");
	}
	
	@RequestMapping(name = "historico", value = "/historico", method = RequestMethod.POST)
	public ModelAndView historicoPost(@RequestParam Map<String, String> param, ModelMap model) {
		String cmd = param.get("botao");
		String pesquisaRa = param.get("pesquisaRa");
		
		String saida = "";
		String erro = "";
		
		if(cmd.contains("Pesquisa Ra")) {
			if (pesquisaRa.trim().isEmpty()) {
				erro = "Por favor, informe o RA.";
			}
		}
		
		if (!erro.isEmpty()) {
			model.addAttribute("erro", erro);
			return new ModelAndView("historico");
		}
		
		Aluno aluno = new Aluno();
		Matricula matricula = new Matricula();
		List<Matricula> matriculas = new ArrayList<>();
		
		aluno.setRa(pesquisaRa);
		matricula.setAluno(aluno);
		
		try {
			if (aluno.getRa().length() == 9) {
				if(verificaRa(aluno) == 1) {
					if (cmd.contains("Pesquisa Ra")) {
						matricula = populaInfosAluno(matricula);
						matriculas = populaHistorico(aluno);
					}
				} else {
					erro = "RA inexistente";
				}
			} else {
				erro = "Tamanho de Ra invalido";
				matricula = new Matricula();
			}
		} catch (SQLException | ClassNotFoundException e) {
			erro = e.getMessage();
		} finally {
			model.addAttribute("saida", saida);
			model.addAttribute("erro", erro);
			model.addAttribute("matricula", matricula);
			model.addAttribute("matriculas", matriculas);
		}
		
		return new ModelAndView("historico");
	}

	private int verificaRa(Aluno aluno) throws SQLException, ClassNotFoundException{
		int saida = hDao.verificaRa(aluno);
		return saida;
	}

	private Matricula populaInfosAluno(Matricula matricula) throws SQLException, ClassNotFoundException{
		Matricula m = new Matricula();
		m = hDao.populaInfosAluno(matricula);
		return m;
	}

	private List<Matricula> populaHistorico(Aluno aluno) throws SQLException, ClassNotFoundException{
		List<Matricula> matriculas = new ArrayList<>();
		matriculas = hDao.populaHistorico(aluno);
		return matriculas;
	}
}
