<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Mi Progreso"/>
</jsp:include>

<div class="container mx-auto px-4 sm:px-8 max-w-7xl">
    <h2 class="text-2xl font-semibold text-gray-800 mb-6">Tu Evolución</h2>

    <div class="grid grid-cols-1 lg:grid-cols-2 gap-6 items-start">

        <div class="space-y-6">

            <div class="bg-white p-6 rounded-lg shadow-sm">
                <c:choose>
                    <c:when test="${not empty progresoHoy}">
                        <%-- VISTA CUANDO YA SE REGISTRÓ HOY --%>
                        <h3 class="font-semibold text-xl mb-4">Peso Registrado Hoy</h3>
                        <p class="text-gray-700 mb-4">Ya has registrado tu peso hoy. ¡Buen trabajo!</p>
                        <form action="ProgresoServlet" method="POST">
                            <input type="hidden" name="action" value="update">
                            <input type="hidden" name="idProgreso" value="${progresoHoy.idProgreso}">
                            
                            <label for="pesoActual" class="block text-sm font-medium text-gray-700">Corregir Peso (kg)</label>
                            <div class="mt-1 flex items-center gap-4">
                                <input type="number" name="pesoActual" id="pesoActual" value="${progresoHoy.pesoSemana}" min="30" step="0.1" class="shadow-sm appearance-none border rounded w-full py-2 px-3 text-gray-700 focus:outline-none focus:ring-2 focus:ring-green-500">
                                <button type="submit" class="bg-blue-600 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-lg transition">Corregir</button>
                            </div>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <%-- VISTA PARA REGISTRAR POR PRIMERA VEZ EN EL DÍA --%>
                        <h3 class="font-semibold text-xl mb-4">Registrar Peso de Hoy</h3>
                        <form action="ProgresoServlet" method="POST">
                            <label for="pesoActual" class="block text-sm font-medium text-gray-700">Peso Actual (kg)</label>
                            <div class="mt-1">
                                <input type="number" name="pesoActual" id="pesoActual" required min="30" step="0.1" class="shadow-sm appearance-none border rounded w-full py-2 px-3 text-gray-700 focus:outline-none focus:ring-2 focus:ring-green-500">
                            </div>
                            <button type="submit" class="mt-4 w-full bg-green-600 hover:bg-green-700 text-white font-bold py-2 px-4 rounded-lg transition">Guardar Progreso</button>
                        </form>
                    </c:otherwise>
                </c:choose>
            </div>
            
            <c:if test="${not empty listaProgreso}">
                <div class="bg-white p-6 rounded-lg shadow-sm">
                    <h3 class="font-semibold text-xl mb-4">Progreso Hacia Tu Meta</h3>
                    <c:set var="progresoActual" value="${listaProgreso[0]}" />
                    <c:set var="progresoInicial" value="${listaProgreso[listaProgreso.size() - 1]}" />
                    <c:set var="pesoInicial" value="${progresoInicial.pesoSemana}" />
                    <c:set var="pesoActual" value="${progresoActual.pesoSemana}" />
                    <c:set var="pesoMeta" value="${progresoActual.pesoMeta}" />

                    <c:if test="${pesoMeta > 0}">
                         <c:choose>
                            <c:when test="${pesoInicial > pesoMeta}">
                                <c:set var="totalARecorrer" value="${pesoInicial - pesoMeta}" />
                                <c:set var="recorrido" value="${pesoInicial - pesoActual}" />
                            </c:when>
                            <c:otherwise>
                                <c:set var="totalARecorrer" value="${pesoMeta - pesoInicial}" />
                                <c:set var="recorrido" value="${pesoActual - pesoInicial}" />
                            </c:otherwise>
                        </c:choose>
                        
                        <c:set var="porcentaje" value="${totalARecorrer > 0 ? (recorrido / totalARecorrer) * 100 : 0}" />
                        <c:set var="porcentajeAncho" value="${porcentaje < 0 ? 0 : (porcentaje > 100 ? 100 : porcentaje)}" />

                        <div class="flex justify-between mb-1">
                            <span class="text-base font-medium text-blue-700">Progreso</span>
                            <span class="text-sm font-medium text-blue-700"><fmt:formatNumber value="${porcentaje}" maxFractionDigits="0"/>%</span>
                        </div>
                        <div class="w-full bg-gray-200 rounded-full h-4">
                            <div class="bg-blue-600 h-4 rounded-full" style="width: ${porcentajeAncho}%;"></div>
                        </div>
                        <div class="flex justify-between text-xs font-medium text-gray-500 mt-1">
                            <span>Inicio: ${pesoInicial} kg</span>
                            <span class="font-bold">Actual: ${pesoActual} kg</span>
                            <span>Meta: ${pesoMeta} kg</span>
                        </div>
                    </c:if>
                    
                    <!-- === INICIO: BOTÓN CON ICONO DE EXCEL INTEGRADO === -->
                    <!-- Usamos 'flex', 'items-center' y 'justify-center' para alinear icono y texto -->
                    <!-- Ajustamos el tamaño del SVG a width="24" height="24" -->
                    <a href="ExportarServlet" 
                       class="flex items-center justify-center gap-3 mt-6 w-full bg-green-600 hover:bg-green-700 text-white font-bold py-2 px-4 rounded-lg transition no-underline shadow-md">
                        
                        <!-- Tu SVG original, pero con el tamaño corregido y clases de estilo -->
                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 48 48" class="fill-current">
                            <path fill="#169154" d="M29,6H15.744C14.781,6,14,6.781,14,7.744v7.259h15V6z"></path>
                            <path fill="#18482a" d="M14,33.054v7.202C14,41.219,14.781,42,15.743,42H29v-8.946H14z"></path>
                            <path fill="#0c8045" d="M14 15.003H29V24.005000000000003H14z"></path>
                            <path fill="#17472a" d="M14 24.005H29V33.055H14z"></path>
                            <g>
                                <path fill="#29c27f" d="M42.256,6H29v9.003h15V7.744C44,6.781,43.219,6,42.256,6z"></path>
                                <path fill="#27663f" d="M29,33.054V42h13.257C43.219,42,44,41.219,44,40.257v-7.202H29z"></path>
                                <path fill="#19ac65" d="M29 15.003H44V24.005000000000003H29z"></path>
                                <path fill="#129652" d="M29 24.005H44V33.055H29z"></path>
                            </g>
                            <path fill="#0c7238" d="M22.319,34H5.681C4.753,34,4,33.247,4,32.319V15.681C4,14.753,4.753,14,5.681,14h16.638 C23.247,14,24,14.753,24,15.681v16.638C24,33.247,23.247,34,22.319,34z"></path>
                            <path fill="#fff" d="M9.807 19L12.193 19 14.129 22.754 16.175 19 18.404 19 15.333 24 18.474 29 16.123 29 14.013 25.07 11.912 29 9.526 29 12.719 23.982z"></path>
                        </svg>
                        
                        <span>Descargar Historial en Excel</span>
                    </a>
                    <!-- === FIN DEL BOTÓN === -->

                </div>
            </c:if>
        </div>

        <div class="bg-white p-6 rounded-lg shadow-sm">
            <h3 class="font-semibold text-xl mb-4">Gráfico de Progreso</h3>
            <div class="h-96">
                <canvas id="progresoChart"></canvas>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels@2.0.0"></script>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        const ctx = document.getElementById('progresoChart');
        if (!ctx) return;

        const labels = [];
        const data = [];

        // 1. Llenamos los arrays con el HISTORIAL de progreso que viene de la base de datos
        <c:forEach var="progreso" items="${listaProgreso}">
            var fecha = new Date('${progreso.fecha}');
            var fechaFormateada = fecha.toLocaleDateString('es-ES', { day: '2-digit', month: '2-digit', year: '2-digit' });
            labels.unshift(fechaFormateada); 
            data.unshift(${progreso.pesoSemana});
        </c:forEach>
        
        // 2. Añadimos SIEMPRE el punto de partida
        labels.unshift("Inicio");
        data.unshift(${sessionScope.usuario.pesoActual});

        Chart.register(ChartDataLabels);

        new Chart(ctx.getContext('2d'), {
            type: 'line',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Peso (kg)',
                    data: data,
                    borderColor: 'rgb(22, 163, 74)',
                    backgroundColor: 'rgba(22, 163, 74, 0.1)',
                    fill: true,
                    tension: 0.1
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                scales: { y: { beginAtZero: false } },
                plugins: {
                    legend: { display: true },
                    datalabels: {
                        anchor: 'end',
                        align: 'top',
                        backgroundColor: 'rgba(255, 255, 255, 0.8)',
                        borderRadius: 4,
                        padding: 4,
                        color: '#333',
                        font: {
                            weight: 'bold',
                            size: 12
                        },
                        formatter: function(value, context) {
                            const label = labels[context.dataIndex];
                            return value.toFixed(1) + ' kg\n' + label;
                        }
                    }
                }
            }
        });
    });
</script>

<%@ include file="footer.jsp" %>
```