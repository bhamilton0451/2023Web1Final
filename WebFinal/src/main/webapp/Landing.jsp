<%@ page language="java" contentType="text/html; UTF-8"
    pageEncoding="UTF-8"
    import="controller.HtmlStringBuilder"%>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<meta name="author" content="Bryan Montgomery Hamilton, ">
		<meta name="keywords" content="TED, HTML, CSS, JSP">
		
		<link rel="stylesheet" href="estilos.css">
		
		<title>Main</title>
	</head>
	
	
	<body>
		
	    <h1 id="title">Bem vindo ao Restaurante!</h1><br>
	    <h2 id="link_pedido">Fazer um Pedido</h2><br>
	    
	    <form action="FazerPedido" method="post">
	    	<% 
		    	String select = HtmlStringBuilder.buildTipoSelect();
		    	out.print(select);
	   		%>
	    	<input type="submit" value="Enviar">
	    </form>
	    
	   	<%
			String msg = (String) request.getAttribute("msg");
			if(msg!= null && !msg.trim().equals("")){
				out.print(msg);
			}
		%>
	    
	    <h2 id="link_admin">Entrar como Administrador</h2>
	    
	    <form action="Autenticador" method="post">
			<label for="login">Login: </label>
			<input type="text" id="login" name="login" size=15><br>
			
			<label for="senha">Senha: </label>
			<input type="text" id="senha" name="senha" size=15><br>
			
			<input type="submit" value="Enviar">
	    </form>
	    
	    <br>
	</body>
</html>