
function carregaEdicaoProduto(id) {
	$.ajax({
		type : "GET",
		url : "/carregaEdicaoProduto/" + id
	}).done(function(produto) {
		product = jQuery.parseJSON(produto);
		document.getElementById('id').value = product.id;
		document.getElementById('nome').value = product.nome;
	});
}

function carregaEdicaoCategoria(id) {
	$.ajax({
		type : "GET",
		url : "/categorias/carregaEdicaoCategoria/" + id
	}).done(function(categoria) {
		categoric = jQuery.parseJSON(categoria);
		document.getElementById('id').value = categoric.id;
		document.getElementById('estoqueMinimo').value = categoric.estoqueMinimo;
		document.getElementById('nome').value = categoric.nome;
	});
}

function carregaEdicaoLoja(id) {
	$.ajax({
		type : "GET",
		url : "/loja/carregaEdicaoLoja/" + id
	}).done(function(categoria) {
		categoric = jQuery.parseJSON(categoria);
		document.getElementById('id').value = categoric.id;
		document.getElementById('nome').value = categoric.nome;
	});
}
