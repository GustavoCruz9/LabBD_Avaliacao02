package guilherme.gustavo.TrabalhoBd.controller;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

import guilherme.gustavo.TrabalhoBd.model.Disciplina;
import guilherme.gustavo.TrabalhoBd.model.Matricula;
import guilherme.gustavo.TrabalhoBd.persistence.CadastrarChamadaDao;

@Controller
public class CadastrarChamadaController {

	@Autowired
	CadastrarChamadaDao ccDao;

	@RequestMapping(name = "cadastrarChamada", value = "/cadastrarChamada", method = RequestMethod.GET)
	public ModelAndView cadastrarChamadaGet(@RequestParam Map<String, String> param, ModelMap model) {
		return new ModelAndView("cadastrarChamada");
	}

	@RequestMapping(name = "cadastrarChamada", value = "/cadastrarChamada", method = RequestMethod.POST)
	public ModelAndView cadastrarChamadaPost(@RequestParam Map<String, String> param, ModelMap model) {

		String codDisciplina = param.get("codDisciplina");
		String cmd = param.get("botao");
		String dataChamada = param.get("dataChamada");

		String saida = "";
		String erro = "";
		String horasSemanais = "";

		List<Matricula> matriculas = new ArrayList<>();
		Disciplina d = new Disciplina();

		d.setCodigoDisciplina(Integer.parseInt(codDisciplina));


		if (!erro.isEmpty()) {
			model.addAttribute("erro", erro);
			return new ModelAndView("cadastrarChamada");
		}

		try {
			if (cmd.contains("Listar Alunos")) {
				if (!dataChamada.trim().isEmpty()) {
					matriculas = buscarAlunos(d);
					if (matriculas.isEmpty()) {
						erro = "Nao existem alunos matriculados nessa materia";
					}

					horasSemanais = matriculas.get(0).getDisciplina().getHorasSemanais().toString();

					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

					LocalDate dataChamadaLocalDate = LocalDate.parse(dataChamada, formatter);
					DayOfWeek diaDaSemana = dataChamadaLocalDate.getDayOfWeek();

					if (!traduzDiaSemana(diaDaSemana).equals(matriculas.get(0).getDisciplina().getDiaSemana())) {
						erro = "A data escolhida deve ser no dia da semana equivalente ao da disciplina";
						matriculas = new ArrayList<>();
					}
				} else {
					erro = "Insira a data da Chamada";
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			erro = e.getMessage();
		} finally {
			model.addAttribute("saida", saida);
			model.addAttribute("erro", erro);
			model.addAttribute("matriculas", matriculas);
			model.addAttribute("horasSemanais", horasSemanais);
			model.addAttribute("dataChamada", dataChamada);
		}

		return new ModelAndView("cadastrarChamada");
	}

	private List<Matricula> buscarAlunos(Disciplina d) throws SQLException, ClassNotFoundException {
		List<Matricula> matriculas = new ArrayList<>();
		matriculas = ccDao.buscarAlunos(d);
		return matriculas;
	}

	private String traduzDiaSemana(DayOfWeek diaDaSemana) {
		switch (diaDaSemana) {
		case MONDAY:
			return "Segunda-feira";
		case TUESDAY:
			return "Terça-feira";
		case WEDNESDAY:
			return "Quarta-feira";
		case THURSDAY:
			return "Quinta-feira";
		case FRIDAY:
			return "Sexta-feira";
		case SATURDAY:
			return "Sábado";
		case SUNDAY:
			return "Domingo";
		default:
			return "Dia inválido";
		}
	}

}
