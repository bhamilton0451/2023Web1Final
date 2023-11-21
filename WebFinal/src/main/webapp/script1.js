/**
 * 
 */

function readEntrada(item){
	switch(item){
		case 'entrada1':
			setImg("entr_img", "pao artesanal.jpeg")
			setAltText("entr_img", "Pão Artesanal")
			break
						
		case 'entrada2':
			setImg("entr_img", "misto quente.jpeg")
			setAltText("entr_img", "Misto Quente")
			break
					
		case 'entrada3':
			setImg("entr_img", "cafe com leite.jpeg")
			setAltText("entr_img", "Café com Leite")
			break
						
		default:
			break
	}
}
			
function readPrato(item){
	switch(item){
		case 'prato1':
			setImg("prt_img", "lasanha.jpeg")
			setAltText("prt_img", "Lasanha")
			break
						
		case 'prato2':
			setImg("prt_img", "macarrao.jpeg")
			setAltText("prt_img", "Macarrão")
			break
					
		case 'prato3':
			setImg("prt_img", "moqueca.jpeg")
			setAltText("prt_img", "Moqueca de Camarão")
			break
						
		case 'prato4':
			setImg("prt_img", "peixe frito.jpeg")
			setAltText("prt_img", "Peixe Frito")
			break
						
		case 'prato5':
			setImg("prt_img", "carne do sol.jpeg")
			setAltText("prt_img", "Carne do Sol")
			break
						
		default:
			//Error case
			break
	}
}
			
function readSobremesa(item){
	switch(item){
		case 'sobremesa1':
			setImg("sbr_img", "sorvete.jpeg")
			setAltText("sbr_img", "Sorvete")
			break
						
		case 'sobremesa2':
			setImg("sbr_img", "picole.jpeg")
			setAltText("sbr_img", "Picolé")
			break
					
		case 'sobremesa3':
			setImg("sbr_img", "mousse.jpeg")
			setAltText("sbr_img", "Mousse")
			break
						
		default:
			//Error case
			break
	}
}
			
function setImg(field, item){
	document.getElementById(field).src = makePath(item)
}
			
function setAltText(field, text){
	document.getElementById(field).alt = text
}
			
function makePath(item){
	var text = "imagens/" + item;
	return text
}
			
function getText(a){
	var text = document.getElementById(a).value;
	return String(text);
}