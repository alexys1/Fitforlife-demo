<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Registrar Consumo"/>
</jsp:include>

<div class="container mx-auto px-4 sm:px-8 max-w-7xl">

    <%-- Título y botones de filtro --%>
    <h2 class="text-2xl font-semibold text-gray-800 mb-2">Añadir ${param.tipo != null ? param.tipo : 'Almuerzo'}</h2>
    <p class="text-gray-500 mb-6">Busca un alimento de nuestra base de datos y añádelo a tu registro diario.</p>
    
    <div class="flex flex-wrap gap-4 mb-6">
        <a href="AlimentoServlet?tipo=Desayuno" class="bg-green-500 hover:bg-green-600 text-white font-bold py-2 px-4 rounded-lg transition">Desayuno</a>
        <a href="AlimentoServlet?tipo=Almuerzo" class="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded-lg transition">Almuerzo</a>
        <a href="AlimentoServlet?tipo=Cena" class="bg-indigo-500 hover:bg-indigo-600 text-white font-bold py-2 px-4 rounded-lg transition">Cena</a>
        <a href="AlimentoServlet?tipo=Snack" class="bg-yellow-500 hover:bg-yellow-600 text-white font-bold py-2 px-4 rounded-lg transition">Snack</a>
    </div>

    <div class="bg-white p-6 rounded-lg shadow-sm">
        <%-- Buscador --%>
        <div class="mb-4">
            <input type="text" id="searchInput" onkeyup="filterContent()" placeholder="Buscar alimento..." class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:ring-2 focus:ring-green-500">
        </div>

        <%-- ===================================================================== --%>
        <%-- == VISTA PARA MÓVILES (TARJETAS) - Visible en pantallas pequeñas == --%>
        <%-- ===================================================================== --%>
        <div id="card-container" class="space-y-4 md:hidden">
            <c:forEach var="alimento" items="${alimentos}">
                <div class="searchable-item border rounded-lg p-4">
                    <%-- Nombre del alimento y estrella --%>
                    <div class="font-bold text-lg text-gray-800 mb-2 food-name">
                        <c:set var="esRecomendado" value="${false}" />
                        <c:if test="${(condicionUsuario == 'Bajo peso' and alimento.etiquetaSalud == 'Alto en Proteína')}"><c:set var="esRecomendado" value="${true}" /></c:if>
                        <c:if test="${(condicionUsuario == 'Sobrepeso' or condicionUsuario == 'Obesidad') and (alimento.etiquetaSalud == 'Bajo en Grasa' or alimento.etiquetaSalud == 'Bajo en Carbs')}"><c:set var="esRecomendado" value="${true}" /></c:if>
                        <c:if test="${condicionUsuario == 'Normal' and alimento.etiquetaSalud == 'Normal'}"><c:set var="esRecomendado" value="${true}" /></c:if>
                        
                        <c:if test="${esRecomendado}">
                            <span class="text-yellow-500 mr-2" title="Recomendado para ti">⭐</span>
                        </c:if>
                        ${alimento.nombre}
                    </div>
                    
                    <%-- Detalles del alimento --%>
                    <div class="flex justify-between text-sm text-gray-600 border-t border-b py-2 my-2">
                        <div>
                            <span class="font-semibold">Calorías:</span> ${alimento.calorias} kcal
                        </div>
                        <div>
                            <span class="font-semibold">Porción:</span> ${alimento.porcion}
                        </div>
                    </div>

                    <%-- Formulario de acción --%>
                    <form action="ConsumoServlet" method="POST" class="flex items-center justify-between gap-2">
                        <div>
                            <label for="cantidad-movil-${alimento.idAlimento}" class="text-sm font-medium text-gray-700">Cantidad:</label>
                            <input type="number" id="cantidad-movil-${alimento.idAlimento}" name="cantidad" value="1" min="0.1" step="0.1" class="w-20 shadow-sm border rounded py-1 px-2 text-gray-700 focus:outline-none focus:ring-2 focus:ring-green-500">
                        </div>
                        <input type="hidden" name="idAlimento" value="${alimento.idAlimento}">
                        <button type="submit" class="bg-green-500 hover:bg-green-600 text-white font-bold py-1 px-3 rounded text-sm transition">Añadir</button>
                    </form>
                </div>
            </c:forEach>
        </div>

        <%-- ====================================================================== --%>
        <%-- == VISTA PARA ESCRITORIO (TABLA) - Oculta en pantallas pequeñas == --%>
        <%-- ====================================================================== --%>
        <div class="overflow-x-auto hidden md:block">
            <table id="alimentosTable" class="min-w-full bg-white">
                <thead class="bg-gray-50">
                    <tr>
                        <th class="py-2 px-4 text-left text-sm font-semibold text-gray-600 uppercase">Alimento</th>
                        <th class="py-2 px-4 text-left text-sm font-semibold text-gray-600 uppercase">Calorías</th>
                        <th class="py-2 px-4 text-left text-sm font-semibold text-gray-600 uppercase">Porción</th>
                        <th class="py-2 px-4 text-left text-sm font-semibold text-gray-600 uppercase" colspan="2">Cantidad y Acción</th>
                    </tr>
                </thead>
                <tbody class="divide-y divide-gray-200">
                    <c:forEach var="alimento" items="${alimentos}">
                        <tr class="searchable-item hover:bg-gray-50">
                            <td class="py-3 px-4 font-medium text-gray-800 food-name">
                                <c:set var="esRecomendado" value="${false}" />
                                <c:if test="${(condicionUsuario == 'Bajo peso' and alimento.etiquetaSalud == 'Alto en Proteína')}"><c:set var="esRecomendado" value="${true}" /></c:if>
                                <c:if test="${(condicionUsuario == 'Sobrepeso' or condicionUsuario == 'Obesidad') and (alimento.etiquetaSalud == 'Bajo en Grasa' or alimento.etiquetaSalud == 'Bajo en Carbs')}"><c:set var="esRecomendado" value="${true}" /></c:if>
                                <c:if test="${condicionUsuario == 'Normal' and alimento.etiquetaSalud == 'Normal'}"><c:set var="esRecomendado" value="${true}" /></c:if>
                                
                                <c:if test="${esRecomendado}">
                                    <span class="text-yellow-500 mr-2" title="Recomendado para ti">⭐</span>
                                </c:if>
                                ${alimento.nombre}
                            </td>
                            <td class="py-3 px-4 text-gray-600">${alimento.calorias} kcal</td>
                            <td class="py-3 px-4 text-gray-600">${alimento.porcion}</td>
                            <td class="py-3 px-4" colspan="2">
                                <form action="ConsumoServlet" method="POST" class="flex items-center gap-2">
                                    <input type="number" name="cantidad" value="1" min="0.1" step="0.1" class="w-20 shadow-sm border rounded py-1 px-2 text-gray-700 focus:outline-none focus:ring-2 focus:ring-green-500">
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
</div>

<script>
    function filterContent() {
        let input, filter, items, i, nameElement, txtValue;
        input = document.getElementById("searchInput");
        filter = input.value.toUpperCase();
        
        // Seleccionamos todos los elementos que se pueden buscar (tanto filas de tabla como tarjetas)
        items = document.querySelectorAll('.searchable-item');

        for (i = 0; i < items.length; i++) {
            // Buscamos el nombre del alimento dentro del item (ya sea en la tarjeta o en la fila de la tabla)
            nameElement = items[i].querySelector('.food-name');
            if (nameElement) {
                txtValue = nameElement.textContent || nameElement.innerText;
                if (txtValue.toUpperCase().indexOf(filter) > -1) {
                    items[i].style.display = ""; // Muestra el elemento si coincide
                } else {
                    items[i].style.display = "none"; // Oculta el elemento si no coincide
                }
            }
        }
    }
</script>

<%@ include file="footer.jsp" %>