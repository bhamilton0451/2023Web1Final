<%@ page language="java" contentType="text/html; UTF-8"
    pageEncoding="UTF-8"
    import="model.Usuario, java.util.ArrayList, model.Prato, controller.AdmCRUD"%>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<meta name="author" content="Bryan Montgomery Hamilton, ">
		<meta name="keywords" content="TED, HTML, CSS, JSP">
		
		<title>Home</title>
		
		
	</head>
	
	
	<body>
			<h1>Bem-vindo(a), Admin</h1><br>
			
			<% 
				String display = AdmCRUD.getDisplay();
				if(display != null){
					out.print(display);
				}
			%>
			
			<br>
			<h4>Opções</h4>
			
			<form action="AdminCrud" method="post">
				<input type="hidden" name="state" value="state_0">
				<select name="menu_0">
					<option value="menu_1_see">Menu de Pratos</option>
					<option value="menu_2_see">Menu de Pedidos</option>
				</select>
				<br>
				<input type="submit" value="Enviar">
			</form>

	</body>
</html>