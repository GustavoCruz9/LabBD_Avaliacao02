package guilherme.gustavo.TrabalhoBd.model;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Dispensa {

	private Aluno aluno;
	private Disciplina disciplina;
	private LocalDate dataSolicitacao;
	private String statusDispensa;
	private String instituicao;
	
}
