package guilherme.gustavo.TrabalhoBd.controller;

import java.sql.SQLException;
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

import guilherme.gustavo.TrabalhoBd.model.Aluno;
import guilherme.gustavo.TrabalhoBd.model.Disciplina;
import guilherme.gustavo.TrabalhoBd.model.Dispensa;
import guilherme.gustavo.TrabalhoBd.persistence.DispensaDao;

@Controller
public class SolicitarDispensa {

	@Autowired
	private DispensaDao dDao;

	@RequestMapping(name = "solicitarDispensa", value = "/solicitarDispensa", method = RequestMethod.POST)
	public ModelAndView solicitarDispensaPost(@RequestParam Map<String, String> param, ModelMap model) {
//		String cpf = param.get("cpf");
//		String cmd = param.get("botao");
//
//		String saida = "";
//		String erro = "";
//
//		List<Disciplina> disciplinas = new ArrayList<>();
//		Aluno aluno = new Aluno();
//		
//		if(cmd != null) {
//			if(cmd.contains("Buscar")) {
//				aluno.setCpf(cpf);
//			}
//			
//			try {
//				if(cmd.contains("Buscar")) {
//					disciplinas = popularDisciplinas(aluno);
//				}
//			} catch (SQLException | ClassNotFoundException e) {
//				erro = e.getMessage();
//			} finally {
//				model.addAttribute("saida", saida);
//				model.addAttribute("erro", erro);
//				model.addAttribute("disciplinas", disciplinas);
//			}
//		}

		return new ModelAndView("solicitarDispensa");
	}

	@RequestMapping(name = "solicitarDispensa", value = "/solicitarDispensa", method = RequestMethod.GET)
	public ModelAndView solicitarDispensaGet(@RequestParam Map<String, String> param, ModelMap model) {
		String cmd = param.get("botao");
		String cpf = param.get("cpf");
		String disciplinaInput = param.get("disciplina");
		String instituicao = param.get("instituicao");

		String saida = "";
		String erro = "";

		if (cmd != null) {
			if (cmd.contains("Buscar")) {
				if (cpf.trim().isEmpty()) {
					erro = "Por favor, informe o CPF.";
				}
			} else if (cmd.contains("Solicitar")) {
				if (cpf.trim().isEmpty() || instituicao.trim().isEmpty()
						|| disciplinaInput.contains("Selecione a Disciplina")) {
					erro = "Por favor, preencha todos os campos obrigatorios.";
				}
			}

			if (!erro.isEmpty()) {
				model.addAttribute("erro", erro);
				return new ModelAndView("solicitarDispensa");
			}

			List<Disciplina> disciplinas = new ArrayList<>();
			List<Dispensa> dispensas = new ArrayList<>();
			Aluno aluno = new Aluno();
			Disciplina disciplina = new Disciplina();
			Dispensa dispensa = new Dispensa();

			aluno.setCpf(cpf);

			if (cmd.contains("Solicitar")) {
				disciplina.setCodigoDisciplina(Integer.parseInt(disciplinaInput));

				dispensa.setDisciplina(disciplina);
				dispensa.setAluno(aluno);
				dispensa.setInstituicao(instituicao);
			}

			try {
				if (aluno.getCpf().length() == 11) {
					if (cmd.contains("Buscar")) {
						if (buscaAluno(aluno) == 0) {
							disciplinas = popularDisciplinas(aluno);
						} else {
							erro = "CPF nao cadastrado";
						}
					}
					if (cmd.contains("Solicitar")) {
						saida = cadastrarDispensa(dispensa);
					}
					if (cmd.contains("Listar Dispensas")) {
						dispensas = listarDispensas(cpf);
						if (dispensas.isEmpty()) {
							erro = "Voce ainda nao solicitou nenhuma dispensa";
						}
					}
				} else {
					erro = "Tamanho de CPF invalido";
				}
			} catch (SQLException | ClassNotFoundException e) {
				erro = e.getMessage();
			} finally {
				model.addAttribute("saida", saida);
				model.addAttribute("erro", erro);
				model.addAttribute("disciplinas", disciplinas);
				model.addAttribute("dispensas", dispensas);
			}

		}

		return new ModelAndView("solicitarDispensa");
	}

	private List<Dispensa> listarDispensas(String cpf) throws SQLException, ClassNotFoundException {
		List<Dispensa> dispensas = new ArrayList<>();
		dispensas = dDao.listarDispensas(cpf);
		return dispensas;
	}

	private String cadastrarDispensa(Dispensa dispensa) throws SQLException, ClassNotFoundException {
		String saida = dDao.cadastraDispensa(dispensa);
		return saida;
	}

	private int buscaAluno(Aluno aluno) throws SQLException, ClassNotFoundException {
		int saida = dDao.verificaCpf(aluno);
		return saida;
	}

	private List<Disciplina> popularDisciplinas(Aluno aluno) throws SQLException, ClassNotFoundException {
		List<Disciplina> disciplinas = new ArrayList<>();
		disciplinas = dDao.listarDisciplinas(aluno);
		return disciplinas;
	}

}
