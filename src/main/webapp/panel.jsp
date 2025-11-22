<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Panel Principal"/>
</jsp:include>

<div class="container mx-auto px-4 sm:px-8 max-w-7xl">
    <h2 class="text-2xl font-semibold text-gray-800 mb-6">Resumen de Hoy</h2>

    <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">

        <!-- Columna Izquierda: Gráfico y Acciones -->
        <div class="lg:col-span-2 bg-white p-6 rounded-lg shadow-sm">
            <h3 class="font-semibold text-xl mb-4">Consumo de Macronutrientes</h3>
            <p class="text-gray-500 mb-6">Registra tus comidas para ver tu progreso aquí. ¡Todo empieza con el primer bocado!</p>
            <div class="h-64">
                <canvas id="macrosChart"></canvas>
            </div>
            <div class="mt-8 border-t pt-6">
                <h4 class="font-semibold text-lg mb-4">Añadir Comida</h4>
                <div class="flex flex-wrap gap-4">
                    <a href="AlimentoServlet?tipo=Desayuno" class="bg-green-500 hover:bg-green-600 text-white font-bold py-2 px-4 rounded-lg transition">Añadir Desayuno</a>
                    <a href="AlimentoServlet?tipo=Almuerzo" class="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded-lg transition">Añadir Almuerzo</a>
                    <a href="AlimentoServlet?tipo=Cena" class="bg-indigo-500 hover:bg-indigo-600 text-white font-bold py-2 px-4 rounded-lg transition">Añadir Cena</a>
                    <a href="AlimentoServlet?tipo=Snack" class="bg-yellow-500 hover:bg-yellow-600 text-white font-bold py-2 px-4 rounded-lg transition">Añadir Snack</a>
                </div>
            </div>
        </div>

        <!-- Columna Derecha: Progreso del Usuario -->
        <div class="bg-white p-6 rounded-lg shadow-sm">
            <h3 class="font-semibold text-xl mb-4">Tu Progreso</h3>
            
            <c:if test="${not empty ultimoIMC}">
                <c:set var="colorIMC" value="text-gray-800" />
                <c:if test="${ultimoIMC.estado == 'Bajo peso'}"><c:set var="colorIMC" value="text-blue-500" /></c:if>
                <c:if test="${ultimoIMC.estado == 'Normal'}"><c:set var="colorIMC" value="text-green-500" /></c:if>
                <c:if test="${ultimoIMC.estado == 'Sobrepeso'}"><c:set var="colorIMC" value="text-yellow-500" /></c:if>
                <c:if test="${ultimoIMC.estado == 'Obesidad'}"><c:set var="colorIMC" value="text-red-500" /></c:if>
            </c:if>

            <div class="space-y-4">
                <div>
                    <p class="text-sm text-gray-500">Peso Inicial</p>
                    <p class="text-2xl font-bold text-gray-800">${sessionScope.usuario.pesoActual} kg</p>
                </div>
                
                <!-- === SECCIÓN ACTUALIZADA: PESO ACTUAL DINÁMICO === -->
                <div>
                    <p class="text-sm text-gray-500">Peso Actual</p>
                    <!-- Aquí mostramos la variable que calculamos en el PanelServlet -->
                    <p class="text-2xl font-bold text-green-600">
                        <fmt:formatNumber value="${pesoActualCalculado}" maxFractionDigits="1" /> kg
                    </p>
                    <p class="text-xs text-gray-400">Registra tu peso en "Mi Progreso"</p>
                </div>
                <!-- ================================================ -->

                <div class="border-t pt-4">
                    <p class="text-sm text-gray-500">Tu IMC y Condición</p>
                    <c:choose>
                        <c:when test="${not empty ultimoIMC}">
                            <p class="text-2xl font-bold ${colorIMC}"><fmt:formatNumber value="${ultimoIMC.imc}" maxFractionDigits="1" /></p>
                            <p class="font-semibold ${colorIMC}">${ultimoIMC.estado}</p>
                        </c:when>
                        <c:otherwise>
                            <p class="text-lg text-gray-400">No hay registros</p>
                        </c:otherwise>
                    </c:choose>
                </div>

                <div class="border-t pt-4">
                    <p class="text-sm text-gray-500">Plan y Calorías del Día</p>
                    <c:choose>
                        <c:when test="${not empty planAsignado and planAsignado.caloriasRecomendadas > 0}">
                            <p class="text-lg font-bold text-gray-800 mb-2">${planAsignado.nombre}</p>

                            <c:set var="caloriasConsumidas" value="${not empty resumenConsumo.totalCalorias ? resumenConsumo.totalCalorias : 0}" />
                            <c:set var="caloriasMeta" value="${planAsignado.caloriasRecomendadas}" />
                            <c:set var="porcentaje" value="${(caloriasConsumidas / caloriasMeta) * 100}" />
                            <c:set var="porcentajeAncho" value="${porcentaje > 100 ? 100 : porcentaje}" />

                            <c:choose>
                                <c:when test="${porcentaje > 100}">
                                    <c:set var="progressBarColor" value="bg-red-500" />
                                    <c:set var="feedbackEmoji" value="😥" />
                                    <c:set var="feedbackMessage" value="¡Cuidado! Te has pasado de tu meta." />
                                    <c:set var="textColor" value="text-red-600" />
                                </c:when>
                                <c:when test="${porcentaje >= 90}">
                                    <c:set var="progressBarColor" value="bg-yellow-500" />
                                    <c:set var="feedbackEmoji" value="👍" />
                                    <c:set var="feedbackMessage" value="¡Ya casi llegas a tu meta!" />
                                    <c:set var="textColor" value="text-gray-600" />
                                </c:when>
                                <c:otherwise>
                                    <c:set var="progressBarColor" value="bg-green-500" />
                                    <c:set var="feedbackEmoji" value="😊" />
                                    <c:set var="feedbackMessage" value="¡Vas por buen camino!" />
                                    <c:set var="textColor" value="text-gray-600" />
                                </c:otherwise>
                            </c:choose>

                            <div class="w-full bg-gray-200 rounded-full h-2.5 mb-1">
                                <div class="${progressBarColor} h-2.5 rounded-full" style="width: <fmt:formatNumber value="${porcentajeAncho}" maxFractionDigits="0"/>%;"></div>
                            </div>

                            <div class="flex justify-between text-sm font-medium text-gray-600">
                                <span><fmt:formatNumber value="${caloriasConsumidas}" maxFractionDigits="0"/> kcal</span>
                                <span>Meta: <fmt:formatNumber value="${caloriasMeta}" maxFractionDigits="0"/> kcal</span>
                            </div>

                            <div class="mt-2 text-center">
                                <span class="text-2xl">${feedbackEmoji}</span>
                                <p class="text-sm font-medium ${textColor}">${feedbackMessage}</p>
                            </div>

                        </c:when>
                        <c:otherwise>
                            <p class="text-lg font-bold text-gray-500">Sin plan calórico asignado</p>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Script de Chart.js -->
<script>
    document.addEventListener('DOMContentLoaded', function () {
        const ctx = document.getElementById('macrosChart').getContext('2d');
        
        const calorias = ${not empty resumenConsumo.totalCalorias ? resumenConsumo.totalCalorias : 0};
        const proteinas = ${not empty resumenConsumo.totalProteinas ? resumenConsumo.totalProteinas : 0};
        const carbohidratos = ${not empty resumenConsumo.totalCarbohidratos ? resumenConsumo.totalCarbohidratos : 0};
        const grasas = ${not empty resumenConsumo.totalGrasas ? resumenConsumo.totalGrasas : 0};

        // Registramos el plugin para mostrar las etiquetas
        Chart.register(ChartDataLabels);

        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: ['Calorías', 'Proteínas', 'Carbs', 'Grasas'],
                datasets: [{
                    label: 'Consumo de Hoy',
                    data: [calorias, proteinas, carbohidratos, grasas],
                    backgroundColor: [
                        'rgba(255, 99, 132, 0.5)',
                        'rgba(54, 162, 235, 0.5)',
                        'rgba(255, 206, 86, 0.5)',
                        'rgba(75, 192, 192, 0.5)'
                    ],
                    borderColor: [
                        'rgba(255, 99, 132, 1)',
                        'rgba(54, 162, 235, 1)',
                        'rgba(255, 206, 86, 1)',
                        'rgba(75, 192, 192, 1)'
                    ],
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                scales: {
                    y: {
                        beginAtZero: true
                    }
                },
                plugins: {
                    legend: {
                        display: false
                    },
                    datalabels: {
                        anchor: 'end',
                        align: 'top',
                        color: '#555',
                        font: {
                            weight: 'bold'
                        },
                        formatter: function(value, context) {
                            const roundedValue = Math.round(value);
                            if (context.dataIndex === 0) {
                                return roundedValue + ' kcal'; // Etiqueta para calorías
                            }
                            return roundedValue + ' g'; // Etiqueta para gramos
                        }
                    }
                }
            }
        });
    });
</script>

<%@ include file="footer.jsp" %>