<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>FitForLife - Transforma tu Vida</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700;800&display=swap" rel="stylesheet">
    <style>
        body { font-family: 'Poppins', sans-serif; }
    </style>
</head>
<body class="bg-gray-50 text-gray-800">

    <header class="bg-white shadow-sm py-4">
    <div class="container mx-auto flex flex-col sm:flex-row justify-between items-center px-6 space-y-4 sm:space-y-0">
        <h1 class="text-3xl font-bold text-green-600">FitForLife</h1>
        <nav class="space-x-2">
            <a href="login.jsp" class="text-gray-700 hover:text-green-600 font-medium px-4 py-2 rounded-lg hover:bg-green-50 transition">Iniciar Sesión</a>
            <a href="registro.jsp" class="bg-green-600 text-white px-5 py-2 rounded-lg hover:bg-green-700 font-medium transition">Regístrate Gratis</a>
        </nav>
    </div>
</header>

    <main>
        <section class="relative text-white" style="height: 70vh;">
            <div class="absolute inset-0">
                <img src="https://images.pexels.com/photos/1640777/pexels-photo-1640777.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2" alt="Comida saludable" class="w-full h-full object-cover">
                <div class="absolute inset-0 bg-black bg-opacity-50"></div>
            </div>
            <div class="container mx-auto px-6 h-full flex flex-col justify-center items-start relative">
                <div class="max-w-xl">
                    <h2 class="text-4xl md:text-5xl font-extrabold leading-tight">Transforma tu relación con la comida. Un registro a la vez.</h2>
                    <p class="mt-4 text-lg opacity-90">Con FitForLife, toma el control de tu nutrición, entiende tus hábitos y alcanza tus metas de bienestar.</p>
                    <a href="registro.jsp" class="mt-8 inline-block bg-green-600 text-white font-semibold px-8 py-3 rounded-lg hover:bg-green-700 transition text-lg shadow-lg">Comienza tu viaje</a>
                </div>
            </div>
        </section>
    </main>

    <section class="py-20 bg-white">
        <div class="container mx-auto px-6 text-center">
            <h3 class="text-4xl font-bold mb-4">¿Cómo te ayudará FitForLife?</h3>
            <p class="text-xl text-gray-600 mb-12">Tres simples pasos hacia una vida más saludable.</p>
            <div class="grid grid-cols-1 md:grid-cols-3 gap-10">
                <div class="p-8 rounded-lg shadow-lg bg-gray-50 transform hover:scale-105 transition duration-300">
                    <div class="bg-green-100 text-green-600 rounded-full h-16 w-16 flex items-center justify-center mx-auto mb-6 text-3xl">🍔</div>
                    <h4 class="text-2xl font-bold mb-4">Registra tu Consumo</h4>
                    <p class="text-gray-700">Lleva un control detallado de cada alimento. Conoce tus calorías, macros y más, con recomendaciones personalizadas.</p>
                </div>
                <div class="p-8 rounded-lg shadow-lg bg-gray-50 transform hover:scale-105 transition duration-300">
                    <div class="bg-green-100 text-green-600 rounded-full h-16 w-16 flex items-center justify-center mx-auto mb-6 text-3xl">📊</div>
                    <h4 class="text-2xl font-bold mb-4">Monitoriza tu Progreso</h4>
                    <p class="text-gray-700">Visualiza tu evolución en peso e IMC con gráficos interactivos que te motivan a seguir adelante día a día.</p>
                </div>
                <div class="p-8 rounded-lg shadow-lg bg-gray-50 transform hover:scale-105 transition duration-300">
                    <div class="bg-green-100 text-green-600 rounded-full h-16 w-16 flex items-center justify-center mx-auto mb-6 text-3xl">🎯</div>
                    <h4 class="text-2xl font-bold mb-4">Alcanza tus Metas</h4>
                    <p class="text-gray-700">Recibe un plan calórico y metas de peso personalizadas para mantenerte en el camino correcto y lograr tus objetivos.</p>
                </div>
            </div>
        </div>
    </section>

    <section class="py-20 bg-gray-100">
        <div class="container mx-auto px-6 text-center">
            <h3 class="text-4xl font-bold mb-16">Lo que dicen nuestros usuarios</h3>
            <div class="grid md:grid-cols-3 gap-12">
                <div class="bg-white p-8 rounded-lg shadow-lg">
                    <img class="w-24 h-24 rounded-full mx-auto -mt-20 border-4 border-white object-cover" src="https://images.pexels.com/photos/91227/pexels-photo-91227.jpeg?auto=compress&cs=tinysrgb&w=400" alt="Foto de Carlos V." alt="Foto de Carlos V.">
                    <p class="text-gray-600 mt-6 italic">"¡Perdí 10kg y aprendí a nutrir mi cuerpo de forma inteligente! La recomiendo totalmente."</p>
                    <p class="font-bold text-lg mt-4 text-green-600">Carlos V.</p>
                </div>
                <div class="bg-white p-8 rounded-lg shadow-lg">
                    <img class="w-24 h-24 rounded-full mx-auto -mt-20 border-4 border-white object-cover" src="https://images.pexels.com/photos/774909/pexels-photo-774909.jpeg?auto=compress&cs=tinysrgb&w=400" alt="Foto de Ana L." alt="Foto de Ana L.">
                    <p class="text-gray-600 mt-6 italic">"Una aplicación muy fácil de usar que de verdad cambió mi vida y mi relación con la comida."</p>
                    <p class="font-bold text-lg mt-4 text-green-600">Ana L.</p>
                </div>
                <div class="bg-white p-8 rounded-lg shadow-lg">
                    <img class="w-24 h-24 rounded-full mx-auto -mt-20 border-4 border-white object-cover" src="https://images.unsplash.com/photo-1544005313-94ddf0286df2?q=80&w=400&auto=format&fit=crop" alt="Foto de María F.">
                    <p class="text-gray-600 mt-6 italic">"¡La mejor y más simple herramienta para mi viaje fitness! Me mantiene motivada todos los días."</p>
                    <p class="font-bold text-lg mt-4 text-green-600">María F.</p>
                </div>
            </div>
        </div>
    </section>

    <section class="bg-green-700 text-white">
    <div class="container mx-auto px-6 py-20">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-12 items-center">
            
            <div class="text-center md:text-left">
                <h3 class="text-4xl font-bold mb-4">¿Listo para empezar tu transformación?</h3>
                <p class="text-xl mb-8 opacity-90">Únete a miles de personas que están mejorando su salud con FitForLife.</p>
                <a href="registro.jsp" class="inline-block bg-white text-green-700 px-8 py-4 rounded-lg text-xl font-semibold hover:bg-gray-200 transition duration-300 shadow-lg transform hover:scale-105">
                    Empieza Ahora, es Gratis
                </a>
            </div>

            <div class="flex justify-center">
                <img src="img/fotoApp.png" 
                     alt="Persona usando una app de fitness en su teléfono" 
                     class="rounded-lg shadow-2xl max-w-sm w-full transform md:rotate-3">
            </div>

        </div>
    </div>
</section>

    <%@ include file="footer.jsp" %>
    
</body>
</html>