package guilherme.gustavo.TrabalhoBd.persistence;

import java.sql.SQLException;

public interface IConsultar<T> {
	
	public T consultar(T t) throws SQLException, ClassNotFoundException;
	
}