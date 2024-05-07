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
import guilherme.gustavo.TrabalhoBd.model.Disciplina;
import guilherme.gustavo.TrabalhoBd.model.Matricula;
import guilherme.gustavo.TrabalhoBd.persistence.MatriculaDao;
import jakarta.servlet.RequestDispatcher;

@Controller
public class MatriculaSecretariaController {

	@Autowired
	private MatriculaDao mDao;

	@RequestMapping(name = "matriculaSecretaria", value = "/matriculaSecretaria", method = RequestMethod.GET)
	public ModelAndView matriculaSecretariaGet(@RequestParam Map<String, String> param, ModelMap model) {

		String pesquisaRa = param.get("pesquisaRa");
		String saida = "";
		String erro = "";

		Aluno a = new Aluno();
		List<Matricula> disciplinas = new ArrayList<>();

		if (pesquisaRa != null) {
			a.setRa(pesquisaRa);
			try {
				if (a.getRa().length() == 9) {
					if (verificaRa(a) == 1) {
						disciplinas = listarDisciplinas(a);
						if (disciplinas.isEmpty()) {
							erro = "Aluno concluiu todas as disciplinas de seu curso";
						}
					} else {
						erro = "RA invalido";
					}
				} else {
					erro = "Tamanho de RA incorreto";
				}

			} catch (SQLException | ClassNotFoundException e) {
				erro = e.getMessage();
			} finally {
				model.addAttribute("saida", saida);
				model.addAttribute("erro", erro);
				model.addAttribute("disciplinas", disciplinas);
				model.addAttribute("pesquisaRa", pesquisaRa);

			}
		}

		return new ModelAndView("matriculaSecretaria");
	}

	@RequestMapping(name = "matriculaSecretaria", value = "/matriculaSecretaria", method = RequestMethod.POST)
	public ModelAndView matriculaSecretariaPost(@RequestParam Map<String, String> param, ModelMap model) {
		String cmd = param.get("botao");
		String pesquisaRa = param.get("pesquisaRa");

		String saida = "";
		String erro = "";

		List<Matricula> disciplinas = new ArrayList<>();

		Aluno a = new Aluno();
		Matricula m = new Matricula();
		Disciplina d = new Disciplina();

		a.setRa(pesquisaRa);

		String aux = "";

		if (!cmd.contains("Pesquisa RA")) {

			if (cmd.contains(".")) {
				aux = cmd.substring(0, cmd.length() - 1);
				d.setCodigoDisciplina(Integer.parseInt(aux));
			} else {
				d.setCodigoDisciplina(Integer.parseInt(cmd));
			}

			m.setAluno(a);
			m.setDisciplina(d);
		}
		
		try {
			saida = cadastrarMatricula(m);
			disciplinas = listarDisciplinas(a);
		} catch (SQLException | ClassNotFoundException e) {
			erro = e.getMessage();

			try {
				disciplinas = listarDisciplinas(a);
			} catch (SQLException | ClassNotFoundException e1) {
				erro = e1.getMessage();
			}

		} finally {
			model.addAttribute("saida", saida);
			model.addAttribute("erro", erro);
			model.addAttribute("disciplinas", disciplinas);
		}

		return new ModelAndView("matriculaSecretaria");
	}

	private int verificaRa(Aluno a) throws SQLException, ClassNotFoundException {
		return mDao.verificaRa(a);
	}

	private String cadastrarMatricula(Matricula m) throws SQLException, ClassNotFoundException {
		return mDao.iMatricula(m);
	}

	private List<Matricula> listarDisciplinas(Aluno a) throws SQLException, ClassNotFoundException {
		List<Matricula> disciplinas = new ArrayList<>();
		disciplinas = mDao.listarDisciplinas(a);
		return disciplinas;
	}

}
