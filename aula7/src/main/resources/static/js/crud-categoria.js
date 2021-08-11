"use strict";

//datatables - lista de categorias
$(document).ready(function() {
    loadTable();
});

function loadTable() {
    moment.locale('pt-BR');
    var table = $('#table-data').DataTable({
        "language": {
            "url": "//cdn.datatables.net/plug-ins/1.10.25/i18n/Portuguese-Brasil.json"
        },
        searching : true,
        lengthMenu : [ 5, 10 ],
        processing : true,
        serverSide : true,
        responsive : true,
        ajax : {
            url : '/categoria/datatables/data',
            data : 'data'
        },
        columns : [
            {data : 'id'},
            {data : 'nome'},
            {data : 'id',
                render : function(id) {
                    return ''.concat('<a class="btn btn-warning btn-sm btn-block"', ' ')
                        .concat('href="').concat('#"')
                        .concat('onclick="edit(').concat(id, ')"', ' ')
                        .concat('role="button" title="Editar" data-toggle="tooltip" data-placement="right">', ' ')
                        .concat('<i class="fa fa-edit"></i></a>');
                },
                orderable : false
            },
            {	data : 'id',
                render : function(id) {
                    return ''.concat('<a class="btn btn-danger btn-sm btn-block"', ' ')
                        .concat('id="row_').concat(id).concat('"', ' ')
                        .concat('onclick="remove(').concat(id, ')"', ' ')
                        .concat('role="button" title="Remover" data-toggle="tooltip" data-placement="right">', ' ')
                        .concat('<i class="fa fa-trash"></i></a>');
                },
                orderable : false
            }
        ]
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
            // loadTable();
            $('#table-data').DataTable().ajax.reload();
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
            $('#table-data').DataTable().ajax.reload();
        } else {
            console.error('Falha ao remover registro!');
        }
    }
    req.send(null);
}