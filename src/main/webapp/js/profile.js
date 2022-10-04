var username = new URL(location.href).searchParams.get("username");
var user;

$(document).ready(function () {

    fillUsuario().then(function () {

        $("#user-saldo").html("$" + user.saldo.toFixed());

        getReservas(user.username);
    
    });
    
    //Botón reservar
    $("#reservar-btn").attr("href", `home.html?username=${username}`);

    //Modificar datos del usuario
    $("#form-modificar").on("submit", function (event) {

        event.preventDefault();
        modificarUsuario();
        
    });

    //Eliminar cuenta
    $("#aceptar-eliminar-cuenta-btn").click(function () {

        eliminarCuenta().then(function () {
            location.href = "index.html";
        })
    })

});

//MOSTRAR DATOS DEL USUARIO
async function fillUsuario() {
    
    await $.ajax({
        type: "GET",
        dataType: "html",
        url: "./ServletUsuarioPedir",
        data: $.param({
            username: username,
        }),
        success: function (result) {
            let parsedResult = JSON.parse(result);

            if (parsedResult != false) {
                user = parsedResult;

                $("#input-username").val(parsedResult.username);
                $("#input-contrasena").val(parsedResult.contrasena);
                $("#input-nombre").val(parsedResult.nombre);
                $("#input-apellido").val(parsedResult.apellido);
                $("#input-documento").val(parsedResult.documento);
                $("#input-email").val(parsedResult.email);
                $("#input-departamento").val(parsedResult.departamento);
                $("#input-ciudad").val(parsedResult.ciudad);
                $("#input-saldo").val(parsedResult.saldo.toFixed(2));
                $("#input-premium").prop("checked", parsedResult.premium);

            } else {
                console.log("Error recuperando los datos del usuario.");
            }
        }
    });

}

//RESERVA
function getReservas(username) {

    $.ajax({
        type: "GET",
        dataType: "html",
        url: "./ServletReservasListar",
        data: $.param({
            username: username
        }),
        success: function (result) {
            let parsedResult = JSON.parse(result);

            if (parsedResult != false) {

                mostrarHistorial(parsedResult);

            } else {
                console.log("Error recuperando los datos de las reservas.");
            }
        }
    });

}

//MOSTRAR HISTORIAL DE RESERVAS
function mostrarHistorial(ofertantes) {
    let contenido = "";
    if (ofertantes.length >= 1) {
        $.each(ofertantes, function (index, ofertante) {
            ofertante = JSON.parse(ofertante);

            contenido += '<tr><th scope="row">' + ofertante.id_ofertante + '</th>' +
                    '<td>' + ofertante.of_nombre + '</td>' +
                    '<td>' + ofertante.nombre_servicio + '</td>' +
                    '<td>' + ofertante.fecha_reserva + '</td>' +
                    '<td><button id="devolver-btn" onclick= "devolverOfertante(' + ofertante.id_ofertante
                    + ');" class="btn btn-danger">Devolver</button></td></tr>';

        });
        $("#historial-tbody").html(contenido);
        $("#historial-table").removeClass("d-none");
        $("#historial-vacio").addClass("d-none");

    } else {
        $("#historial-vacio").removeClass("d-none");
        $("#historial-table").addClass("d-none");
    }
}

//DEVOLVER OFERTANTE
function devolverOfertante(id_ofertante) {

    $.ajax({
        type: "GET",
        dataType: "html",
        url: "./ServletOfertanteDevolver",
        data: $.param({
            username: username,
            id_ofertante: id_ofertante,
        }),
        success: function (result) {

            if (result != false) {

                location.reload();

            } else {
                console.log("Error devolviendo el ofertante.");
            }
        }
    });

}

//MODIFICAR USUARIO
function modificarUsuario() {

    let username = $("#input-username").val();
    let contrasena = $("#input-contrasena").val();
    let nombre = $("#input-nombre").val();
    let apellido = $("#input-apellido").val();
    let documento = $("#input-documento").val();
    let email = $("#input-email").val();
    let departamento = $("#input-departamento").val();
    let ciudad = $("#input-ciudad").val();
    let saldo = $("#input-saldo").val();
    let premium = $("#input-premium").prop('checked');
    
    $.ajax({
        type: "GET",
        dataType: "html",
        url: "./ServletUsuarioModificar",
        data: $.param({
            username: username,
            contrasena: contrasena,
            nombre: nombre,
            apellido: apellido,
            documento: documento,
            email: email,
            departamento: departamento,
            ciudad: ciudad,
            saldo: saldo,
            premium: premium,
        }),
        success: function (result) {

            if (result != false) {
                $("#modificar-error").addClass("d-none");
                $("#modificar-exito").removeClass("d-none");
            } else {
                $("#modificar-error").removeClass("d-none");
                $("#modificar-exito").addClass("d-none");
            }

            setTimeout(function () {
                location.reload();
            }, 3000);

        }
    });

}

//ELIMINAR CUENTA
async function eliminarCuenta() {

    await $.ajax({
        type: "GET",
        dataType: "html",
        url: "./ServletUsuarioEliminar",
        data: $.param({
            username: username
        }),
        success: function (result) {

            if (result != false) {
                console.log("Usuario eliminado.");
            } else {
                console.log("Error eliminando el usuario.");
            }
        }
    });

}
