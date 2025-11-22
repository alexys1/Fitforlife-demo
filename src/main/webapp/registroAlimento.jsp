<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Registrar Consumo"/>
</jsp:include>

<h2 class="text-2xl font-semibold text-gray-800 mb-2">Añadir ${param.tipo}</h2>
<p class="text-gray-500 mb-6">Busca un alimento de nuestra base de datos y añádelo a tu registro diario.</p>

<div class="bg-white p-6 rounded-lg shadow-sm">
    <div class="mb-4">
        <input type="text" id="searchInput" onkeyup="filterTable()" placeholder="Buscar alimento..." class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:ring-2 focus:ring-green-500">
    </div>

    <div class="overflow-x-auto">
        <table id="alimentosTable" class="min-w-full bg-white">
            <thead class="bg-gray-50">
                <tr>
                    <th class="py-2 px-4 text-left">Alimento</th>
                    <th class="py-2 px-4 text-left">Calorías</th>
                    <th class="py-2 px-4 text-left">Porción</th>
                    <th class="py-2 px-4 text-left">Cantidad</th>
                    <th class="py-2 px-4 text-left">Acción</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="alimento" items="${alimentos}">
                    <tr class="border-b hover:bg-gray-50">
                        <td class="py-2 px-4 font-medium">${alimento.nombre}</td>
                        <td class="py-2 px-4">${alimento.calorias} kcal</td>
                        <td class="py-2 px-4">${alimento.porcion}</td>
                        <td class="py-2 px-4">
                             <form action="ConsumoServlet" method="POST" class="flex items-center gap-2">
                                <input type="number" name="cantidad" value="1" min="0.1" step="0.1" class="w-20 shadow-sm appearance-none border rounded py-1 px-2 text-gray-700 focus:outline-none focus:ring-2 focus:ring-green-500">
                                <input type="hidden" name="idAlimento" value="${alimento.idAlimento}">
                                <button type="submit" class="bg-green-500 hover:bg-green-600 text-white font-bold py-1 px-3 rounded text-sm transition">Añadir</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<script>
function filterTable() {
    let input, filter, table, tr, td, i, txtValue;
    input = document.getElementById("searchInput");
    filter = input.value.toUpperCase();
    table = document.getElementById("alimentosTable");
    tr = table.getElementsByTagName("tr");

    for (i = 1; i < tr.length; i++) { // Empezar en 1 para saltar el header
        td = tr[i].getElementsByTagName("td")[0]; // Columna de nombre del alimento
        if (td) {
            txtValue = td.textContent || td.innerText;
            if (txtValue.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}
</script>

<%@ include file="footer.jsp" %>
