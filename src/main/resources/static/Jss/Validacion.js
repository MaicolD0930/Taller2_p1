document.addEventListener("input", e => {
    if(e.target.classList.contains("cantidadInput")){
        const div = e.target.closest(".detalle");
        const select = div.querySelector(".productoSelect");

        // JSON con info del producto
        const productoInfo = {
            stock: parseInt(select.selectedOptions[0].dataset.stock || 0),
            cantidad: parseInt(e.target.value || 0)
        };

        if(productoInfo.cantidad > productoInfo.stock){
            alert(`Cantidad ingresada no esta permitida, Cantidad máxima permitida = ${productoInfo.stock}`);
            e.target.value = productoInfo.stock;
        }
    }
});

function agregarDetalle(){
    const cont = document.getElementById("detalles");
    const html = `
    <div class="detalle row g-2 align-items-end mb-2">
        <div class="col-md-6">
            <label class="form-label">Producto</label>
            <select name="productoId[]" class="form-select productoSelect" required>
                ${document.querySelector(".productoSelect").innerHTML}
            </select>
        </div>
        <div class="col-md-3">
            <label class="form-label" style="margin-left: 40px;">Cantidad</label>
            <input type="number" name="cantidad[]" style="margin-left: 40px;" class="form-control cantidadInput" min="1" required>
            <span class="alert-stock"></span>
        </div>
        <div class="col-md-3">
            <button type="button" class="btn btn-danger mt-4" onclick="this.parentElement.parentElement.remove()" style="margin-left: 40px;">Quitar</button>
        </div>
    </div>`;
    cont.insertAdjacentHTML("beforeend", html);
}