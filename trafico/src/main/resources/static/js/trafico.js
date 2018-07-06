$(function(){
	$('#btnExportar').click(function(event){
		event.preventDefault();
		$('#divResultados table').hide();
		
		var reporte = $("#slctReporte").val();
		switch (reporte) {
			case "1":
				usabilidad();break;
			case "2":
				despliegue();break;
			case "3":
				adopcion();break;
			case "4":
				convertibilidad();break;
			case "5":
				evolutivo();break;
			default:
				break;
		}
	});
	$("#slctReporte").change(function(){
		var reporte = $("#slctReporte").val();
		switch (reporte) {
			case "2":
				$("div[grupo='mes']").show();break;
			case "3":
				$("div[grupo='mes']").show();break;
			case "4":
				$("div[grupo='mes']").show();break;
			default:
				$("div[grupo='mes']").hide();break;
		}
	});
	$("#slctReporte").trigger("change");
})


// usabilidad
function usabilidad(){
	$.ajax({
		url:"trafico/usabilidad",
		type:'post',
		data:{},
		beforeSend:function(){$(".loading").show();},
		success:function(obj){
			$(".loading").hide();
			fillUsabilidad(obj.data);           
		},
		error:function(){$(".loading").hide();}
	});
}
function fillUsabilidad(data){
	var html="<thead class='thead-dark'><tr><th scope='col'>FECHA LLAMADA</th><th scope='col'>CANAL VENTA</th><th scope='col'>APP</th><th scope='col'>WEB</th><th scope='col'>OTROS</th><th scope='col'>TOTAL</th><th scope='col'>USABILIDAD APP</th><th scope='col'>USABILIDAD WEB</th><th scope='col'>USABILIDAD TOTAL</th></tr></thead><tbody>";
	
	var datax = hallarSubtotales(data);
	
	$.each(datax,function(k,v){
		var total = (parseInt(v.app) + parseInt(v.web) + parseInt(v.otros));
		var usabilidadApp = Math.round( (v.app*100/total) * 100 )/100;
		var usabilidadWeb = Math.round( (v.web*100/total) * 100 )/100;
		var usabilidadTotal = Math.round( (usabilidadApp + usabilidadWeb) * 100 ) / 100;
		
		
		if(v.fechaLlamada!=""){
			html+="<tr class='table-warning'>";
		}else{
			html+="<tr>";
		}
		html+="<td>"+v.fechaLlamada+"</td>";
		html+="<td>"+v.canalVentaAgrup+"</td>";
		html+="<td>"+v.app+"</td>";
		html+="<td>"+v.web+"</td>";
		html+="<td>"+v.otros+"</td>";
		// html+="<td>"+v.total+"</td>";
		html+="<td>"+ total +"</td>";
		// html+="<td>"+v.usabilidadApp+"</td>";
		html+="<td>"+ usabilidadApp +"%</td>";
		//html+="<td>"+v.usabilidadWeb+"</td>";
		html+="<td>"+ usabilidadWeb +"%</td>";
		html+="<td>"+ usabilidadTotal +"%</td>";
		
		html+="</tr>";
	})
	html+="</tbody>";
	$('#tblUsabilidad').html(html).show();	
}

function hallarSubtotales(datax){
	// data invertida y ordenada
	var dataI = [];
	// acumulador subtotales, inicializado en suma del ultimo registro
	var sumaApp=0,sumaWeb=0,sumaOtros=0,sumaTotal=0,sumaUsaApp=0,sumaUsaWeb=0;
	// recorriendo arreglo, invertiendo orden tabla
	for(var i=(datax.length-1);i>=0;i--){
		// limpiando fechaLlamada
		var dataT = JSON.parse(JSON.stringify(datax[i]));
		dataT.fechaLlamada="";
		dataI.push(dataT);
		// sumas
		sumaApp += parseInt(datax[i].app);
		sumaWeb += parseInt(datax[i].web);
		sumaOtros += parseInt(datax[i].otros);
		sumaTotal += parseInt(datax[i].total);
		sumaUsaApp += parseFloat(datax[i].usabilidadApp);
		sumaUsaWeb += parseFloat(datax[i].usabilidadWeb);
		if(i>0 && datax[i].fechaLlamada!=datax[(i-1)].fechaLlamada){
			dataI.push({"fechaLlamada":datax[i].fechaLlamada,"canalVentaAgrup":"","app":sumaApp,"web":sumaWeb,"otros":sumaOtros,"total":sumaTotal,"usabilidadApp":sumaUsaApp,"usabilidadWeb":sumaUsaWeb});
			sumaApp=0,sumaWeb=0,SumaOtros=0,sumaTotal=0,sumaUsaApp=0,sumaUsaWeb=0;
		}else if(i==0){
			dataI.push({"fechaLlamada":datax[i].fechaLlamada,"canalVentaAgrup":"","app":sumaApp,"web":sumaWeb,"otros":sumaOtros,"total":sumaTotal,"usabilidadApp":sumaUsaApp,"usabilidadWeb":sumaUsaWeb});
		}		
	}
	// invertir nuevamente array para retornar
	var data = [];
	for(var i=(dataI.length-1);i>=0;i--){
		data.push(dataI[i]);
	}
	return data;
}

// adopcion
function adopcion(){
	$.ajax({
		url:"trafico/adopcion",
		type:'post',
		data:{
			mes:$("#slctMes").val()
		},
		beforeSend:function(){$(".loading").show();},
		success:function(obj){
			$(".loading").hide();
			fillAdopcion(obj.data);           
		},
		error:function(){$(".loading").hide();}
	});
}
function fillAdopcion(data){
	// acumulador totales
	var sumaVendApp=0,sumaVendWeb=0,sumaVentApp=0,sumaVentWeb=0,sumaVentOtros=0,sumaTotal=0,sumaAdopcion=0;
	
	var html="<thead class='thead-dark'><tr><th scope='col'>CANAL VENTA</th><th scope='col'>VEND APP</th><th scope='col'>VEND WEB</th><th scope='col'>VENT APP</th><th scope='col'>VENT WEB</th><th scope='col'>VENT OTROS</th><th scope='col'>TOTAL</th><th scope='col'>ADOPCION</th></tr></thead><tbody>";
	$.each(data,function(k,v){
		var total = (parseInt(v.ventasApp) + parseInt(v.ventasWeb)+ parseInt(v.ventasOtras));
		var adopcion = Math.floor( ( parseInt(v.ventasApp)/(parseInt(v.ventasApp) + parseInt(v.ventasWeb)+ parseInt(v.ventasOtras)) ) * 100) / 100;
		
		html+="<tr>";
		html+="<td>"+v.canalVentaAgrup+"</td>";
		html+="<td>"+v.totalVend+"</td>";
		html+="<td>"+v.vendWeb+"</td>";
		html+="<td>"+v.ventasApp+"</td>";
		html+="<td>"+v.ventasWeb+"</td>";
		html+="<td>"+v.ventasOtras+"</td>";
		html+="<td>"+ total +"</td>";
		html+="<td>"+ adopcion +"</td>";
		html+="</tr>";
		// suma totales
		sumaVendApp += parseInt(v.totalVend);
		sumaVendWeb += parseInt(v.vendWeb);
		sumaVentApp += parseInt(v.ventasApp);
		sumaVentWeb += parseInt(v.ventasWeb);
		sumaVentOtros += parseInt(v.ventasOtras);
		sumaTotal += parseInt(total);
		sumaAdopcion += parseFloat(adopcion);
	})
	// TOTALES
	html+="<tr class='table-warning'>";
	html+="<td>TOTALES</td>";
	html+="<td>"+ sumaVendApp +"</td>";
	html+="<td>"+ sumaVendWeb +"</td>";
	html+="<td>"+ sumaVentApp +"</td>";
	html+="<td>"+ sumaVentWeb +"</td>";
	html+="<td>"+ sumaVentOtros +"</td>";
	html+="<td>"+ sumaTotal +"</td>";
	html+="<td>"+ sumaAdopcion +"</td>";
	html+="</tr>";
	
	html+="</tbody>";
	$('#tblAdopcion').html(html).show();	
}

// despliegue
function despliegue(){
	$.ajax({
		url:"trafico/despliegue",
		type:'post',
		data:{
			mes:$("#slctMes").val()
		},
		beforeSend:function(){$(".loading").show();},
		success:function(obj){
			$(".loading").hide();
			fillDespliegue(obj.data);           
		},
		error:function(){$(".loading").hide();}
	});
}
function fillDespliegue(data){
	// acumulador totales
	var sumaApp=0,sumaWeb=0,sumaOtros=0,sumaTotal=0,sumaDesplApp=0,sumaDesplWeb=0;
	
	var html="<thead class='thead-dark'><tr><th scope='col'>CANAL VENTA AGRUP</th><th scope='col'>VEND APP</th><th scope='col'>VEND WEB</th><th scope='col'>OTROS</th><th scope='col'>TOTAL</th><th scope='col'>DESPL APP</th><th scope='col'>DESPL WEB</th></tr></thead><tbody>";
	$.each(data,function(k,v){
		var total = parseInt(v.vendApp)+parseInt(v.vendWeb)+parseInt(v.otros);
		var desplApp = Math.round( (v.vendApp*100/total) * 100 )/100;
		var desplWeb = Math.round( (v.vendWeb*100/total) * 100 )/100;
		
		html+="<tr>";
		html+="<td>"+ v.canalVentaAgrup +"</td>";
		html+="<td>"+ v.vendApp +"</td>";
		html+="<td>"+ v.vendWeb +"</td>";
		html+="<td>"+ v.otros +"</td>";
		html+="<td>"+ total +"</td>";
		html+="<td>"+ desplApp +"%</td>";
		html+="<td>"+ desplWeb +"%</td>";
		html+="</tr>";
		// suma totales
		sumaApp += parseInt(v.vendApp);
		sumaWeb += parseInt(v.vendWeb);
		sumaOtros += parseInt(v.otros);
		sumaTotal += parseInt(total);
		sumaDesplApp += parseFloat(desplApp);
		sumaDesplWeb += parseFloat(desplWeb);
	})
	// TOTALES
	html+="<tr class='table-warning'>";
	html+="<td>TOTALES</td>";
	html+="<td>"+ sumaApp +"</td>";
	html+="<td>"+ sumaWeb +"</td>";
	html+="<td>"+ sumaOtros +"</td>";
	html+="<td>"+ sumaTotal +"</td>";
	html+="<td>"+ Math.round( (sumaApp*100/sumaTotal) * 100 )/100 +"%</td>";
	html+="<td>"+ Math.round( (sumaWeb*100/sumaTotal) * 100 )/100 +"%</td>";
	html+="</tr>";
	
	html+="</tbody>";
	$('#tblDespliegue').html(html).show();	
}

// convertibilidad
function convertibilidad(){
	$.ajax({
		url:"trafico/convertibilidad",
		type:'post',
		data:{
			mes:$("#slctMes").val()
		},
		beforeSend:function(){$(".loading").show();},
		success:function(obj){
			$(".loading").hide();
			fillConvertibilidad(obj.dataC);  
			fillConvertibilidadRegistro(obj.dataCR);
		},
		error:function(){$(".loading").hide();}
	});
}
function fillConvertibilidad(data){
	// HALLANDO TOTALES
	var sumaVentApp=0,sumaVentWeb=0,sumaOtros=0,sumaTotal=0;
	$.each(data,function(k,v){
		var total = (parseFloat(v.ventasApp) + parseFloat(v.ventasWeb) + parseFloat(v.otros));
		sumaVentApp += parseInt(v.ventasApp);
		sumaVentWeb += parseInt(v.ventasWeb);
		sumaOtros += parseInt(v.otros);
		sumaTotal += parseInt(total);
	})
	// PINTANDO TABLA
	var html="<thead class='thead-dark'><tr><th scope='col'>ESTADO GESTION</th><th scope='col'>VENTAS APP</th><th scope='col'>VENTAS WEB</th><th scope='col'>VENTAS OTROS</th><th scope='col'>TOTAL</th></tr></thead><tbody>";		
	$.each(data,function(k,v){
		var total = (parseFloat(v.ventasApp) + parseFloat(v.ventasWeb) + parseFloat(v.otros));
		html+="<tr>";
		html+="<td>"+ v.estadoGestion +"</td>";
		html+="<td title='"+v.ventasApp+"'>"+ Math.round( (v.ventasApp * 100 / sumaVentApp) * 100) / 100 +"%</td>";
		html+="<td title='"+v.ventasWeb+"'>"+ Math.round( (v.ventasWeb * 100 / sumaVentWeb) * 100) / 100 +"%</td>";
		html+="<td title='"+v.otros+"'>"+ Math.round( (v.otros * 100 / sumaOtros) * 100) / 100 +"%</td>";
		html+="<td title='"+total+"'>"+ Math.round( (total * 100 / sumaTotal) * 100) / 100 +"%</td>";
		html+="</tr>";
	})
	// TOTALES
	html+="<tr class='table-warning'>";
	html+="<td>TOTALES</td>";
	html+="<td>"+ sumaVentApp +"</td>";
	html+="<td>"+ sumaVentWeb +"</td>";
	html+="<td>"+ sumaOtros +"</td>";
	html+="<td>"+ sumaTotal +"</td>";
	html+="</tr>";
	html+="</tbody>";
	$('#tblConvertibilidad').html(html).show();	
}
function fillConvertibilidadRegistro(data){
	var html="<thead class=''><tr class='table-primary'><th scope='col'>VENTAS APP</th><th scope='col'>CAIDAS APP</th><th scope='col'>VENTAS OTRAS</th><th scope='col'>CAIDAS OTRAS</th></tr></thead><tbody>";
	$.each(data,function(k,v){
		if(v!=null){
		html+="<tr>";
		html+="<td>"+ v.ventasApp +"</td>";
		html+="<td>"+ v.caidasApp +"</td>";
		html+="<td>"+ v.ventasOtras +"</td>";
		html+="<td>"+ v.caidasOtras +"</td>";
		html+="</tr>";
		}
	})
	html+="</tbody>";
	$('#tblConvertibilidadRegistros').html(html).show();	
}

//evolutivo
function evolutivo(){
	$.ajax({
		url:"trafico/evolutivo",
		type:'post',
		data:{},
		beforeSend:function(){$(".loading").show();},
		success:function(obj){
			$(".loading").hide();
			fillEvolutivoTotal(obj.dataT);  
			fillEvolutivoApp(obj.dataA);
			fillEvolutivoWeb(obj.dataW);
		},
		error:function(){$(".loading").hide();}
	});
}
function fillEvolutivoTotal(data){
	var html="<thead class='thead-dark'>" +
			"<tr class='table-primary'><th colspan='6'>TOTAL</th></tr>" +
			"<tr class='table-primary'>" +
			"<th scope='col'>MES</th>" +
			"<th scope='col'>USABILIDAD</th>" +
			"<th scope='col'>DESPLIEGUE</th>" +
			"<th scope='col'>VENTAS APP</th>" +
			"<th scope='col'>VENTAS OTRAS</th>" +
			"<th scope='col'>ADOPCION</th>" +
			"</tr></thead><tbody>";
	$.each(data,function(k,v){
		var total = parseInt(v.ventasApp) + parseInt(v.ventasOtras);
		var adopcion = Math.round( (parseInt(v.ventasApp)*100/total) * 100)/100;
		
		html+="<tr>";
		html+="<td>"+ getMes(v.mes) +"</td>";
		html+="<td>"+ v.usabilidad +"</td>";
		html+="<td>"+ v.despliegue +"</td>";
		html+="<td>"+ v.ventasApp +"</td>";
		html+="<td>"+ v.ventasOtras +"</td>";
		html+="<td>"+ adopcion +"%</td>";
		html+="</tr>";		
	})
	html+="</tbody>";
	$('#tblEvolutivoTotal').html(html).show();	
}
function fillEvolutivoApp(data){
	var html="<thead class='thead-dark'>" +
			"<tr class='table-primary'><th colspan='10'>APP</th></tr>" +
			"<tr class='table-primary'>" +
			"<th scope='col'>MES</th>" +
			"<th scope='col'>APP</th>" +
			"<th scope='col'>OTROS</th>" +
			"<th scope='col'>USABILIDAD APP</th>" +
			"<th scope='col'>VENDEDORES APP</th>" +
			"<th scope='col'>VENDEDORES OTROS</th>" +
			"<th scope='col'>DESPLIEGUE</th>" +
			"<th scope='col'>VENTAS APP</th>" +
			"<th scope='col'>VENTAS OTRAS</th>" +
			"<th scope='col'>ADOPCION</th>" +
			"</tr></thead><tbody>";
	$.each(data,function(k,v){
		var total= parseInt(v.app) + parseInt(v.otros);
		var usabilidadApp = Math.round( (parseInt(v.app)*100/total) * 100 )/100;
		var totalVend= parseInt(v.vendApp) + parseInt(v.vendOtros);
		var despliegue = Math.round( (parseInt(v.vendApp)*100/totalVend) * 100 )/100;
		var totalVentas= parseInt(v.ventasApp) + parseInt(v.ventasOtras);
		var adopcion = Math.round( (parseInt(v.ventasApp)*100/totalVentas) * 100 )/100;
		
		html+="<tr>";
		html+="<td>"+ getMes(v.mes) +"</td>";
		html+="<td>"+ v.app +"</td>";
		html+="<td>"+ v.otros +"</td>";
		html+="<td>"+ usabilidadApp +"%</td>";
		html+="<td>"+ v.vendApp +"</td>";
		html+="<td>"+ v.vendOtros +"</td>";
		html+="<td>"+ despliegue +"%</td>";
		html+="<td>"+ v.ventasApp +"</td>";
		html+="<td>"+ v.ventasOtras +"</td>";
		html+="<td>"+ adopcion +"%</td>";
		html+="</tr>";		
	})
	html+="</tbody>";
	$('#tblEvolutivoApp').html(html).show();	
}
function fillEvolutivoWeb(data){
	var html="<thead class='thead-dark'>" +
			"<tr class='table-primary'><th colspan='10'>WEB</th></tr>" +
			"<tr class='table-primary'>" +
			"<th scope='col'>MES</th>" +
			"<th scope='col'>WEB</th>" +
			"<th scope='col'>OTROS</th>" +
			"<th scope='col'>USABILIDAD WEB</th>" +
			"<th scope='col'>VENDEDORES WEB</th>" +
			"<th scope='col'>VENDEDORES OTROS</th>" +
			"<th scope='col'>DESPLIEGUE</th>" +
			"<th scope='col'>VENTAS WEB</th>" +
			"<th scope='col'>VENTAS OTRAS</th>" +
			"<th scope='col'>ADOPCION</th>" +
			"</tr></thead><tbody>";
	$.each(data,function(k,v){
		var total= parseInt(v.web) + parseInt(v.otros);
		var usabilidadWeb = Math.round( (parseInt(v.web)*100/total) * 100 )/100;
		var totalVend= parseInt(v.vendWeb) + parseInt(v.vendOtros);
		var despliegue = Math.round( (parseInt(v.vendWeb)*100/totalVend) * 100 )/100;
		var totalVentas= parseInt(v.ventasWeb) + parseInt(v.ventasOtras);
		var adopcion = Math.round( (parseInt(v.ventasWeb)*100/totalVentas) * 100 )/100;
		
		html+="<tr>";
		html+="<td>"+ getMes(v.mes) +"</td>";
		html+="<td>"+ v.web +"</td>";
		html+="<td>"+ v.otros +"</td>";
		html+="<td>"+ usabilidadWeb +"%</td>";
		html+="<td>"+ v.vendWeb +"</td>";
		html+="<td>"+ v.vendOtros +"</td>";
		html+="<td>"+ despliegue +"%</td>";
		html+="<td>"+ v.ventasWeb +"</td>";
		html+="<td>"+ v.ventasOtras +"</td>";
		html+="<td>"+ adopcion +"%</td>";
		html+="</tr>";		
	})
	html+="</tbody>";
	$('#tblEvolutivoWeb').html(html).show();	
}
function getMes(numero){
	var mes = "";
	switch (numero) {
	case "1":mes="ENERO";break;
	case "2":mes="FEBRERO";break;
	case "3":mes="MARZO";break;
	case "4":mes="ABRIL";break;
	case "5":mes="MAYO";break;
	case "6":mes="JUNIO";break;
	case "7":mes="JULIO";break;
	case "8":mes="AGOSTO";break;
	case "9":mes="SETIEMBRE";break;
	case "10":mes="OCTUBRE";break;
	case "11":mes="NOVIEMBRE";break;
	case "12":mes="DICIEMBRE";break;
	default:break;
	}
	return mes;
}