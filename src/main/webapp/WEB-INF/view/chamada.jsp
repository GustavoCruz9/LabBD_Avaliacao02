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
	
		<form method="post" action="chamada" class="formChamada">
			<div class="cabecalhoChamada">
				<div class="pesquisaProfessor">
					<input type="text" pattern="[0-9]*" name="codigo" id="codigo" placeholder="Codigo Professor" > 
		            <input type="submit" value="Buscar Disciplinas"  id="botao" name="botao">
				</div>
	            
	            <div class="disciplinas">
		            <select name="disciplina" id="disciplina">
		            	<option value="" disabled selected>Selecione a Disciplina</option>
		            	<option>REDES</option>
		            	<option>LP</option>
		            	<option>Tanembaum</option>
		            </select>
		            
		            <input type="submit" value="Buscar Chamadas"  id="botao" name="botao">
	            </div>
	            
	            <input type="submit" value="Nova Chamada"  id="botao" name="botao">
			</div>
            
            <div class="conteudoChamada">
	            <h2>Banco de Dados</h2>
	            <table>
	            	<thead>
	            		<tr>
	            			<th colspan="3">Data</th>
	            			
	            		</tr>
	            	</thead>
	            	<tbody>
	            		<tr>
	            			<td>23/02/2004</td>
	            			<td>
	            				<input type="submit" value="Visualizar" id="botao" name="botao">
	            			</td>
	            			<td>
	            				<input type="submit" value="Alterar" id="botao" name="botao">
	            			</td>
	            		</tr>
	            		<tr>
	            			<td>23/02/2004</td>
	            			<td>
	            				<input type="submit" value="Visualizar" id="botao" name="botao">
	            			</td>
	            			<td>
	            				<input type="submit" value="Alterar" id="botao" name="botao">
	            			</td>
	            		</tr>
	            		<tr>
	            			<td>23/02/2004</td>
	            			<td>
	            				<input type="submit" value="Visualizar" id="botao" name="botao">
	            			</td>
	            			<td>
	            				<input type="submit" value="Alterar" id="botao" name="botao">
	            			</td>
	            		</tr>
	            	</tbody>
	            </table>
            </div>
		</form>
	
	</main>

</body>

</html>