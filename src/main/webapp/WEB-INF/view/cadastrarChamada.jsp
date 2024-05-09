<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sistema AGIS - Professor</title>
<link rel="stylesheet" type="text/css" href='<c:url value = "./resources/css/styleProfessor.css"/>'>
</head>

<body>

	<script src="./resources/js/headerProfessor.js"></script>
	
	<main>
		
		<form method="post" action="cadastrarChamada" class="formCadastrarChamada">
			<div class="dataContainer">
				<label>Data da Chamada:</label>
				<input type="date" id="data" name="data">
			</div>
			
			<h1>Redes</h1>
			
			<table>
				<thead>
					<tr>
						<th colspan="5">Nome</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>
							Gustavo da Cruz Santos
						</td>
						<td>
							<input type="checkbox" id="checkbox" name="checkbox">
						</td>
						<td>
							<input type="checkbox" id="checkbox" name="checkbox">
						</td>
						<td>
							<input type="checkbox" id="checkbox" name="checkbox">
						</td>
						<td>
							<input type="checkbox" id="checkbox" name="checkbox">
						</td>
					</tr>
					<tr>
						<td>
							João da Silva Sauro
						</td>
						<td>
							<input type="checkbox" id="checkbox" name="checkbox">
						</td>
						<td>
							<input type="checkbox" id="checkbox" name="checkbox">
						</td>
						<td>
							<input type="checkbox" id="checkbox" name="checkbox">
						</td>
						<td>
							<input type="checkbox" id="checkbox" name="checkbox">
						</td>
					</tr>
					<tr>
						<td>
							Vini Junior
						</td>
						<td>
							<input type="checkbox" id="checkbox" name="checkbox">
						</td>
						<td>
							<input type="checkbox" id="checkbox" name="checkbox">
						</td>
						<td>
							<input type="checkbox" id="checkbox" name="checkbox">
						</td>
						<td>
							<input type="checkbox" id="checkbox" name="checkbox">
						</td>
					</tr>
					<tr>
						<td>
							Tony Kross
						</td>
						<td>
							<input type="checkbox" id="checkbox" name="checkbox">
						</td>
						<td>
							<input type="checkbox" id="checkbox" name="checkbox">
						</td>
						<td>
							<input type="checkbox" id="checkbox" name="checkbox">
						</td>
						<td>
							<input type="checkbox" id="checkbox" name="checkbox">
						</td>
					</tr>
				</tbody>
			</table>
			
			<input type="submit" id="botao" name="botao" value="Cadastrar Chamada" class="btnCadastrarChamada">
			
		</form>
		
	</main>

</body>
</html>