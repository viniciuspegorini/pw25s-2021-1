"use strict";

$(document).ready(function () {
    loadTable();
});

//Carregar a tabela com os dados das marcas usando jQuery
function loadTable() {
    const url = '/marca/data';
    $.getJSON(url, function (data) {
        createTableRows(data);
    });
}

function createTableRows(list) {
    $('#tableData tbody').empty();
    $.each(list, function (i, item) {
        $('#tableData tbody').append($('<tr/>')
            .attr('id', 'row_' + item.id)
            .append($('<td/>').html(item.id))
            .append($('<td/>').html(item.nome))
            .append($('<td/>').html('<button class="btn btn-warning" onClick="edit(' + item.id + ')"> <i class="fa fa-edit ml-2"></i></button>'))
            .append($('<td/>').html('<button class="btn btn-danger" onClick="remove(' + item.id + ')"> <i class="fa fa-trash ml-2"></i></button>'))
        );
    });
}

//Carregar uma marca no formulário
function edit(id) {
    const url = '/marca/' + id;
    $.getJSON(url, function (data) {
        $('#id').val(data.id);
        $('#nome').val(data.nome);
        $('#modalCadastro').modal('show');
    });
}

//Salvar uma marca
$('#frm').submit(function (e) {
    e.preventDefault();
    const marca = { // cria-se o objeto genero com os atributos id e nome
        id: ($('#id').val() != '' ? $('#id').val() : null),
        nome: $('#nome').val()
    }
    let json = JSON.stringify(marca);
    $.ajax({
        type: $('#frm').attr('method'),
        url: $('#frm').attr('action'),
        contentType: 'application/json',
        data: json,
        success: function () {
            Swal.fire({
                title: 'Salvo!',
                text: 'Registro salvo com sucesso!',
                type: 'success'
            }).then((result) => {
                $('#modalCadastro').modal('toggle');
                loadTable();
                newEntity();
            });//FIM swal()
        },
        error: function () {
            Swal.fire('Errou!', 'Falha ao salvar registro!', 'error');
        }
    }); //FIM ajax()
});

// Limpar o formulário de cadastro
function newEntity() {
    $('#id').val(null);
    $('#frm').trigger("reset");
}

// Remover uma marca
function remove(id) {
    const url = '/marca/' + id;
    Swal.fire({
        title: "Deseja realmente remover o registro?!",
        text: "Esta ação não poderá ser desfeita!",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        cancelButtonText: "Cancelar",
        confirmButtonText: "Remover",
        closeOnConfirm: false
    }).then((result) => {
            if (result.isConfirmed) {
                $.ajax({
                    type: 'DELETE',
                    url: url,
                    success: function () {
                        Swal.fire({
                            title: 'Removido!',
                            text: 'Registro removido com sucesso!',
                            type: 'success'
                        }).then((result) => {
                            $('#row_' + id).remove();
                        }); //FIM swal()
                    },
                    error: function () {
                        Swal.fire('Erro!',
                            'Falha ao remover registro!',
                            'error');
                    }
                }); //FIM ajax()
            }
        } //FIM funcion swal
    ); //FIM swal();
}