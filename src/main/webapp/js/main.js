//alert("Mensaje de prueba.");
var username = new URL(location.href).searchParams.get("username");
var user;

$(document).ready(function () {

    $(function () {
        $('[data-bs-toggle="tooltip"]').tooltip();
    });

    getUsuario().then(function () {

        //Botón mi perfil
        $("#btn-mi-perfil").attr("href", "profile.html?username=" + username);

        //Muestra el saldo disponible en la cuenta
        $("#user-saldo").html(user.saldo.toFixed(2) + "$");

        //Mostrar ofertantes
        getOfertantes(false, "ASC");

        //Ordenar ofertantes según el nombre del servicio al dar click en el ícono
        $("#ordenar-servicio").click(ordenarServicios);

    });

});

//PEDIR USUARIO
async function getUsuario() {

    await $.ajax({
        type: "GET",
        dataType: "html",
        url: "./ServletUsuarioPedir",
        data: $.param({
            username: username
        }),
        success: function (result) {
            let parsedResult = JSON.parse(result);

            if (parsedResult != false) {
                user = parsedResult;
            } else {
                console.log("Error recuperando los datos del usuario");
            }
        }

    });

}

//LISTAR OFERTANTES
function getOfertantes(ordenar, orden) {

    $.ajax({
        type: "GET",
        dataType: "html",
        url: "./ServletOfertanteListar",
        data: $.param({
            ordenar: ordenar,
            orden: orden
        }),
        success: function (result) {
            let parsedResult = JSON.parse(result);

            if (parsedResult != false) {
                mostrarOfertantes(parsedResult);
            } else {
                console.log("Error recuperando los datos de las peliculas");
            }
        }
    });
}

//MOSTRAR OFERTANTES
function mostrarOfertantes(ofertantes) {
    let contenido = "";

    $.each(ofertantes, function (index, ofertante) {

        ofertante = JSON.parse(ofertante);

        let precio;

        //Mostrar los ofertantes según el departamento del usuario
        if (user.departamento == ofertante.of_departamento){

            //Si la cantidad de horas disponibles es menor 0 no se mostrará en el listado de ofertantes
            if (ofertante.horas_disponibles > 0) {
            //Si el usuario es premium se descuenta un 15% al coste del servicio
                if (user.premium) {
                    if (ofertante.horas_disponibles) {
                        precio = ofertante.costo_servicio - (ofertante.costo_servicio * 0.15);
                    }
                } else {
                    if (ofertante.horas_disponibles) {
                        precio = ofertante.costo_servicio;
                    }
                }
                
                //Tabla con la información de los ofertantes
                contenido += '<tr>' +
                        '<th class="d-none" scope="row">' + ofertante.id_ofertante + '</th>' +
                        '<td>' + ofertante.of_nombre + ' ' + ofertante.of_apellido + '</td>' +
                        '<td class="d-none">' + ofertante.of_email + '</td>' +
                        '<td class="d-none">' + ofertante.of_departamento + '</td>' +
                        '<td>' + ofertante.of_ciudad + '</td>' +
                        '<td>' + ofertante.nombre_servicio + '</td>' +
                        '<td>' + ofertante.descripcion_servicio + '</td>' +
                        '<td>' + precio + '</td>' +
                        '<td class="d-none"><input type="checkbox" name="horas_disponibles" id="horas_disponibles' 
                        + ofertante.id_ofertante + '" disabled ';
                if (ofertante.horas_disponibles) {
                    contenido += 'checked';
                }
                contenido += '></td>'
                        + '<td><button onclick="reservarOfertantes(' + ofertante.id_ofertante + ',' + precio + ');" class="btn btn-success" ';
                if (user.saldo < precio) {
                    contenido += ' disabled ';
                }
                contenido += '>Reservar</button></td></tr>'

            }

        }
    });
    $("#ofertantes-tbody").html(contenido);

}

//ORDENAR OFERTANTES POR NOMBRE DE SERVICIOS
function ordenarServicios() {

    if ($("#icono-ordenar").hasClass("fa-sort")) {
        getOfertantes(true, "ASC");
        $("#icono-ordenar").removeClass("fa-sort");
        $("#icono-ordenar").addClass("fa-sort-down");
    } else if ($("#icono-ordenar").hasClass("fa-sort-down")) {
        getOfertantes(true, "DESC");
        $("#icono-ordenar").removeClass("fa-sort-down");
        $("#icono-ordenar").addClass("fa-sort-up");
    } else if ($("#icono-ordenar").hasClass("fa-sort-up")) {
        getOfertantes(false, "ASC");
        $("#icono-ordenar").removeClass("fa-sort-up");
        $("#icono-ordenar").addClass("fa-sort");
    }
}

//RESERVAR OFERTANTE
function reservarOfertantes(id_ofertante, precio) {

    $.ajax({
        type: "GET",
        dataType: "html",
        url: "./ServletOfertanteReservar",
        data: $.param({
            id_ofertante: id_ofertante,
            username: username
        }),
        success: function (result) {
            let parsedResult = JSON.parse(result);

            if (parsedResult != false) {
                restarDinero(precio).then(function () {
                    location.reload();
                })
            } else {
                console.log("Error en la reserva del ofertante.");
            }
        }
    });
    
}

//RESTAR DINERO
async function restarDinero(precio) {

    await $.ajax({
        type: "GET",
        dataType: "html",
        url: "./ServletUsuarioRestarDinero",
        data: $.param({
            username: username,
            saldo: parseFloat(user.saldo - precio)
        }),
        success: function (result) {
            let parsedResult = JSON.parse(result);

            if (parsedResult != false) {
                console.log("Saldo actualizado.");
            } else {
                console.log("Error en el proceso de pago.");
            }
        }
    });
}
