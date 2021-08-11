let compraProdutos = [];

function adicionarProdutoDto() {
	let compraProduto = new Object();
	compraProduto.produtoId = $('#produto').val();
	compraProduto.produtoNome = $('#produto').find(':selected').text().split('|')[0];
	compraProduto.valor = $('#produto').find(':selected').data('valor') // $('#produto').attr('data-valor');
	compraProduto.quantidade = $('#quantidade').val();
	compraProdutos.push(compraProduto);
	adicionarLinhaCarrinho( criarLinhaDto(compraProduto) );
}

function criarLinhaDto(compraProduto) {
	return `
    <tr id="${compraProduto.produtoId}">
    	<td name="produto"><span>${compraProduto.produtoNome}</span></td>
		<td name="quantidade" style="text-align: right"><span>${compraProduto.quantidade}</span></td>
    	<td name="valor" style="text-align: right">
    		<span>${new Intl.NumberFormat('pt-BR',{ style: 'currency', currency: 'BRL'})
		.format(compraProduto.valor)}</span>
    	</td>
    	<td name="valor" style="text-align: right">
    		<span>${new Intl.NumberFormat('pt-BR',{ style: 'currency', currency: 'BRL'})
		.format(compraProduto.valor * compraProduto.quantidade)}</span>
    	</td>
    	<td><a onclick="removerProduto(this, event);"><i class="fa fa-trash ml-2" title="Remover produto" data-toggle="tooltip"></i></a></td>
    </tr>`;
}

function removerProduto() {
	// TO-DO
}

function adicionarLinhaCarrinho(linha) {
	if ($('#tbCompraProdutos tbody') == 0)
		$('#tbCompraProdutos').append('<tbody> </tbody>');

	$('#tbCompraProdutos tbody').append(linha);
}

// Salvar as compras
$('#frm').submit(function (e) {
	e.preventDefault();
	let compra = new Object();
	compra.compraProdutos = compraProdutos;
	compra.fornecedorId = $('#fornecedor').val();
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
					window.location = '/compra';
			});//FIM swal()
		},
		error: function(data) {
			console.log(data);
			Swal.fire('Errou!', 'Falha ao salvar registro!', 'error');
		}
	}); //FIM ajax()
});


