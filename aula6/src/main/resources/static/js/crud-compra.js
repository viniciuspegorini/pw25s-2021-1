let compraProdutos = [];

function adicionarProduto() {
	let compraProduto = new Object();
	compraProduto.id = new Object();
	compraProduto.id.produto = new Object();

	compraProduto.id.produto.id = $('#produto').val();
	compraProduto.id.produto.nome = $('#produto').find(':selected').text().split('|')[0];
	compraProduto.valor = $('#produto').find(':selected').data('valor') // $('#produto').attr('data-valor');
	compraProduto.quantidade = $('#quantidade').val();
	compraProdutos.push(compraProduto);

	adicionarLinhaCarrinho( criarLinha(compraProduto) );
}

function removerProduto() {
	// TO-DO
}

function criarLinha(compraProduto) {
	return $('<tr />')
		.attr('id', "${compraProduto.id.produto.id}")
		.append($('<td />').html("<span>" + compraProduto.id.produto.nome + "</span>"))
		.append($('<td />').html("<span>" + compraProduto.quantidade + "</span>"))
		.append($('<td />').html("<span>" + new Intl.NumberFormat('pt-BR',{ style: 'currency', currency: 'BRL'}).format(compraProduto.valor) + "</span>"))
		.append($('<td />').html("<span>" + new Intl.NumberFormat('pt-BR',{ style: 'currency', currency: 'BRL'}).format(compraProduto.valor * compraProduto.quantidade) + "</span>"))
		.append($('<td />').html("<a href='#'><i className='fa fa-trash ml-2' title='Remover produto' data-toggle='tooltip'></i></a>"));
}

function adicionarLinhaCarrinho(linha) {
	if ($('#tbCompraProdutos tbody') == 0)
		$('#tbCompraProdutos').append('<tbody> </tbody>');

	$('#tbCompraProdutos tbody').append(linha);
}

function finalizarCompra(urlDestino) {
	let compra = new Object();
	compra.compraProdutos = compraProdutos;
	compra.fornecedor = new Object();
	compra.fornecedor.id = $('#fornecedor').val();
	compra.notaFiscal = $('#notaFiscal').val();
	compra.observacoes = $('#observacoes').val();

	$.ajax({
		type: $('#frm').attr('method'),
		url: $('#frm').attr('action'),
		contentType : 'application/json',
		data : JSON.stringify(compra),
		success: function() {
			Swal.fire({
				title: 'Salvo!',
				text: 'Registro salvo com sucesso!',
				type: 'success'
				}).then((result) => {
					window.location = urlDestino;
				}
			);//FIM swal()
		},
		error: function(data) {
			console.log(data);
			Swal.fire('Errou!', 'Falha ao salvar registro!', 'error');
		}
	}); //FIM ajax()
	return false;
}

// Salvar as compras
$('#frm').submit(function (e) {

});


