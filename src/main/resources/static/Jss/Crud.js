$(function() {
    let jorgeToastShown = false;
    $('#nombre').on('input', function() {
        if ($(this).val().trim().toLowerCase() === 'jorge') {
        mostrarAlertaJorge();
        }
    });
});

function mostrarAlertaJorge() {
    Swal.fire({
        html: `
        <div style="font-family: 'Trebuchet MS', 'Lucida Sans Unicode', 'Lucida Grande', 'Lucida Sans', Arial, sans-serif;">
            <h1 style="color: red;">¡Usuario Betado Detectado!</h1>
            Este Usuario está expulsado de nuestro sistema por las siguientes faltas:
         </div>
            <ul style="text-align:left;">
                <li>1). Traición a los principios fundamentales del sistema, demostrando lealtad dudosa y acciones contrarias al bien común.</li>
                <li>2). Participación en conductas excéntricas e inaceptables, entre ellas hechos que, por su naturaleza de costeño frente a la fauna animal, no pueden ser tolerados ni justificados bajo ningún reglamento.</li>
                <li>3). Actos ofensivos y grotescos contra la moral colectiva y el sentido común, que incluyen situaciones que jamás debieron ocurrir y que marcan un precedente negativo.</li>
            </ul>
       
        `,
        imageUrl: '/img/jorge.png',
        imageWidth: 150,
        imageAlt: 'Jorge'
    });
}