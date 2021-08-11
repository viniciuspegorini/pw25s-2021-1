"use strict";

//onload()
$(document).ready(function() {
    loadTable();
});

//Carregar a tabela com os dados das categorias
function loadTable() {
    const url  = '/categoria/data';
    let req  = new XMLHttpRequest();
    req.open('GET', url, true);

    req.onload = function () {
        let data = JSON.parse(req.responseText);
        if (req.readyState == 4 && req.status == "200") {
            createTableRows(data);
        } else {
            console.error('Falha ao carregar dados!');
        }
    }
    req.send(null);
}

function createTableRows(list) {
    $('#tableData tbody').empty();
    $.each(list, function(i, item) {
        $('#tableData tbody').append(
            $('<tr/>')
                .attr('id', 'row_' + item.id)
                .append($('<td/>').html(item.id))
                .append($('<td/>').html(item.nome))
                .append($('<td/>').html('<button class="btn btn-warning" onClick="edit(' + item.id + ')"> <i class="fa fa-edit ml-2"></i></button>'))
                .append($('<td/>').html('<button class="btn btn-danger" onClick="remove(' + item.id + ')"> <i class="fa fa-trash ml-2"></i></button>'))
        );
    });
}

//Carregar uma categoria no formulário
function edit(id) {
    const url = '/categoria/' + id;
    let req = new XMLHttpRequest();
    req.open('GET', url, true)
    req.onload = function () {
        let c = JSON.parse(req.responseText);
        if (req.readyState == 4 && req.status == "200") {
            $('#id').val( c.id );
            $('#nome').val( c.nome );
            $('#modalCadastro').modal('show');
        } else {
            console.error('Falha ao carregar a categoria');
        }
    }
    req.send(null);
}

$('#frm').submit( function(e) {
    e.preventDefault();

    const url = "/categoria";
    let req = new XMLHttpRequest();
    req.open("POST", url, true);
    req.setRequestHeader('Content-type', 'application/json; charset=utf-8');

    const categoria = { // cria-se o objeto genero com os atributos id e nome
        id : ($('#id').val() != '' ? $('#id').val() : null),
        nome : $('#nome').val()
    }
    let json = JSON.stringify(categoria);
    req.onload = function () {
        if (req.readyState == 4 && req.status == "200") {
            $('#modalCadastro').modal('toggle');
            loadTable();
            newEntity();
        } else {
            console.error('Falha ao salvar registro');
        }
    }
    req.send(json);
});

// Limpar o formulário de cadastro
function newEntity() {
    $('#id').val(null);
    $('#frm').trigger("reset");
}

// Remover uma categoria
function remove(id) {
    const url = '/categoria/' + id;
    let req = new XMLHttpRequest();
    req.open("DELETE", url, true);

    req.onload = function () {
        if (req.readyState == 4 && req.status == "200") {
            // loadTable();
            $('#row_' + id).remove();
        } else {
            console.error('Falha ao remover registro!');
        }
    }
    req.send(null);
}