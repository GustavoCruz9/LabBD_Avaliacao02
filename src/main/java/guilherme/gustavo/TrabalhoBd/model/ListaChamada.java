package guilherme.gustavo.TrabalhoBd.model;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ListaChamada {

	private int codChamada;
	private int presenca;
	private int ausencia;
	private LocalDate dataChamada;
	private Matricula matricula;
	
}

