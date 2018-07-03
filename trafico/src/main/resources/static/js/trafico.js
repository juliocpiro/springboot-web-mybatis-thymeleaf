$(function(){
	$('#btnExportar').click(function(event){
		event.preventDefault();
		$('#divResultados table').hide();
		
		var reporte = $("#slctReporte").val();
		switch (reporte) {
			case "1":
				resumenHistorico();
				break;
			case "2":
				resumenGeneral();
				break;
			case "3":
				convertibilidad();
				break;
			default:
				break;
		}
	});
})

//resumenHistorico
function resumenHistorico(){
	$.ajax({
		url:"trafico/resumenHistorico",
		type:'post',
		data:{},
		beforeSend:function(){$(".loading").show();},
		success:function(obj){
			$(".loading").hide();
			fillResumenHistorico(obj.data);           
		},
		error:function(){$(".loading").hide();}
	});
}
function fillResumenHistorico(data){
	var html="<thead class='thead-dark'><tr><th scope='col'>FECHA LLAMADA</th><th scope='col'>CANAL VENTA AGRUP</th><th scope='col'>APP</th><th scope='col'>WEB</th><th scope='col'>OTROS</th><th scope='col'>USABILIDAD APP</th><th scope='col'>USABILIDAD WEB</th><th scope='col'>TOTAL</th></tr></thead><tbody>";
	$.each(data,function(k,v){
		html+="<tr>";
		html+="<td>"+v.fecha+"</td>";
		html+="<td>"+v.canalVentaAgrup+"</td>";
		html+="<td>"+v.app+"</td>";
		html+="<td>"+v.web+"</td>";
		html+="<td>"+v.otros+"</td>";
		html+="<td>"+v.usabilidadApp+"</td>";
		html+="<td>"+v.usabilidadWeb+"</td>";
		html+="<td>"+v.total+"</td>";
		html+="</tr>";
	})
	html+="</tbody>";
	$('#tblResumenHistorico').html(html).show();	
}

//resumenGeneral
function resumenGeneral(){
	$.ajax({
		url:"trafico/resumenGeneral",
		type:'post',
		data:{},
		beforeSend:function(){$(".loading").show();},
		success:function(obj){
			$(".loading").hide();
			fillResumenGeneral(obj.data);           
		},
		error:function(){$(".loading").hide();}
	});
}
function convertibilidad(data){
	var html="<thead class='thead-dark'><tr><th scope='col'>CANAL VENTA AGRUP</th><<th scope='col'>TOTAL</th></tr></thead><tbody>";
	$.each(data,function(k,v){
		html+="<tr>";
		html+="<td>"+v.canalVentaAgrup+"</td>";
		html+="<td>"+v.total+"</td>";
		html+="</tr>";
	})
	html+="</tbody>";
	$('#tblResumenGeneral').html(html).show();	
}

//convertibilidad
function fillResumenGeneral(){
	$.ajax({
		url:"trafico/convertibilidad",
		type:'post',
		data:{},
		beforeSend:function(){$(".loading").show();},
		success:function(obj){
			$(".loading").hide();
			fillConvertibilidad(obj.data);           
		},
		error:function(){$(".loading").hide();}
	});
}
function fillConvertibilidad(data){
	var html="<thead class='thead-dark'><tr><th scope='col'>CANAL VENTA AGRUP</th><th scope='col'>TOTAL VEND</th><th scope='col'>VENT APP</th><th scope='col'>VENT WEB</th><th scope='col'>TOTAL</th><th scope='col'>ADOPCION</th></tr></thead><tbody>";
	$.each(data,function(k,v){
		html+="<tr>";
		html+="<td>"+v.canalVentaAgrup+"</td>";
		html+="<td>"+v.totalVend+"</td>";
		html+="<td>"+v.ventasApp+"</td>";
		html+="<td>"+v.ventasWeb+"</td>";
		html+="<td>"+(v.ventasApp + v.ventasWeb)+"</td>";
		html+="<td>"+v.ventasApp/(v.ventasApp + v.ventasWeb)+"</td>";
		html+="</tr>";
	})
	html+="</tbody>";
	$('#tblConvertibilidad').html(html).show();	
}