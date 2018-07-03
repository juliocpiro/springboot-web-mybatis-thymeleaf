$(function(){
	$('#btnExportar').click(function(event){
		event.preventDefault();
		$('#divResultados table').hide();
		
		var reporte = $("#slctReporte").val();
		switch (reporte) {
			case "1":
				usabilidad();
				break;
			case "2":
				despliegue();
				break;
			case "3":
				adopcion();
				break;
			case "4":
				convertibilidad();
				break;
			default:
				break;
		}
	});
})


//usabilidad
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
	var html="<thead class='thead-dark'><tr><th scope='col'>FECHA LLAMADA</th><th scope='col'>CANAL VENTA AGRUP</th><th scope='col'>APP</th><th scope='col'>WEB</th><th scope='col'>OTROS</th><th scope='col'>TOTAL</th><th scope='col'>USABILIDAD APP</th><th scope='col'>USABILIDAD WEB</th><th scope='col'>USABILIDAD TOTAL</th></tr></thead><tbody>";
	$.each(data,function(k,v){
		html+="<tr>";
		html+="<td>"+v.fechaLlamada+"</td>";
		html+="<td>"+v.canalVentaAgrup+"</td>";
		html+="<td>"+v.app+"</td>";
		html+="<td>"+v.web+"</td>";
		html+="<td>"+v.otros+"</td>";
		html+="<td>"+v.total+"</td>";
		html+="<td>"+v.usabilidadApp+"</td>";
		html+="<td>"+v.usabilidadWeb+"</td>";
		html+="<td>"+ v.usabilidadApp + v.usabilidadWeb +"</td>";
		
		html+="</tr>";
	})
	html+="</tbody>";
	$('#tblUsabilidad').html(html).show();	
}

//adopcion
function adopcion(){
	$.ajax({
		url:"trafico/adopcion",
		type:'post',
		data:{},
		beforeSend:function(){$(".loading").show();},
		success:function(obj){
			$(".loading").hide();
			fillAdopcion(obj.data);           
		},
		error:function(){$(".loading").hide();}
	});
}
function fillAdopcion(data){
	var html="<thead class='thead-dark'><tr><th scope='col'>CANAL VENTA AGRUP</th><th scope='col'>VEND APP</th><th scope='col'>VEND WEB</th><th scope='col'>VENT APP</th><th scope='col'>VENT WEB</th><th scope='col'>VENT OTRS</th><th scope='col'>TOTAL</th><th scope='col'>ADOPCION</th></tr></thead><tbody>";
	$.each(data,function(k,v){
		html+="<tr>";
		html+="<td>"+v.canalVentaAgrup+"</td>";
		html+="<td>"+v.totalVend+"</td>";
		html+="<td>"+v.vendWeb+"</td>";
		html+="<td>"+v.ventasApp+"</td>";
		html+="<td>"+v.ventasWeb+"</td>";
		html+="<td>"+v.ventasOtras+"</td>";
		html+="<td>"+(parseInt(v.ventasApp) + parseInt(v.ventasWeb)+ parseInt(v.ventasOtras))+"</td>";
		html+="<td>"+ Math.floor( ( parseInt(v.ventasApp)/(parseInt(v.ventasApp) + parseInt(v.ventasWeb)+ parseInt(v.ventasOtras)) ) * 100) / 100 +"</td>";
		html+="</tr>";
	})
	html+="</tbody>";
	$('#tblAdopcion').html(html).show();	
}

//despliegue
function despliegue(){
	$.ajax({
		url:"trafico/despliegue",
		type:'post',
		data:{},
		beforeSend:function(){$(".loading").show();},
		success:function(obj){
			$(".loading").hide();
			fillDespliegue(obj.data);           
		},
		error:function(){$(".loading").hide();}
	});
}
function fillDespliegue(data){
	var html="<thead class='thead-dark'><tr><th scope='col'>CANAL VENTA AGRUP</th><th scope='col'>VEND APP</th><th scope='col'>VEND WEB</th><th scope='col'>OTROS</th><th scope='col'>TOTAL</th><th scope='col'>DESPL APP</th><th scope='col'>DESPL WEB</th></tr></thead><tbody>";
	$.each(data,function(k,v){
		html+="<tr>";
		html+="<td>"+v.canalVentaAgrup+"</td>";
		html+="<td>"+v.vendApp+"</td>";
		html+="<td>"+v.vendWeb+"</td>";
		html+="<td>"+v.otros+"</td>";
		html+="<td>"+v.total+"</td>";
		html+="<td>"+ Math.round( (v.vendApp/ v.total ) * 100) / 100 +"</td>";
		html+="<td>"+ Math.round( (v.vendWeb/ v.total ) * 100) / 100 +"</td>";
		html+="</tr>";
	})
	html+="</tbody>";
	$('#tblDespliegue').html(html).show();	
}

//convertibilidad
function convertibilidad){
	$.ajax({
		url:"trafico/convertibilidad",
		type:'post',
		data:{},
		beforeSend:function(){$(".loading").show();},
		success:function(obj){
			$(".loading").hide();
			fillDespliegue(obj.data);           
		},
		error:function(){$(".loading").hide();}
	});
}
function fillConvertibilidad(data){
	var html="<thead class='thead-dark'><tr><th scope='col'>CANAL VENTA AGRUP</th></tr></thead><tbody>";
	$.each(data,function(k,v){
		html+="<tr>";
		html+="<td>"+v.estadoGestion+"</td>";
		html+="<td>"+v.ventasApp+"</td>";
		html+="<td>"+v.ventasWeb+"</td>";
		html+="<td>"+v.otros+"</td>";
		html+="</tr>";
	})
	html+="</tbody>";
	$('#tblConvertibilidad').html(html).show();	
}