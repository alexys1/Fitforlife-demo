<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro - FitForLife</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">
    <style>
        body { font-family: 'Poppins', sans-serif; }
    </style>
</head>
<body class="bg-gray-100 flex items-center justify-center min-h-screen py-12">

    <div class="w-full max-w-lg bg-white rounded-lg shadow-md p-8">
        <h1 class="text-3xl font-bold text-green-600 text-center mb-2">FitForLife</h1>
        <h2 class="text-2xl font-bold text-gray-800 text-center mb-6">Crea tu Cuenta</h2>

        <c:if test="${not empty error}">
            <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative mb-4" role="alert">
                <span class="block sm:inline">${error}</span>
            </div>
        </c:if>

        <form action="RegistroServlet" method="POST">
            <div class="mb-4">
                <label for="nombre" class="block text-gray-700 text-sm font-bold mb-2">Nombre Completo</label>
                <input type="text" id="nombre" name="nombre" required class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 focus:outline-none focus:ring-2 focus:ring-green-500">
            </div>
            <div class="mb-4">
                <label for="email" class="block text-gray-700 text-sm font-bold mb-2">Correo Electrónico</label>
                <input type="email" id="email" name="email" required class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 focus:outline-none focus:ring-2 focus:ring-green-500">
            </div>
            <div class="mb-4">
                <label for="password" class="block text-gray-700 text-sm font-bold mb-2">Contraseña</label>
                <input type="password" id="password" name="password" required class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 focus:outline-none focus:ring-2 focus:ring-green-500">
            </div>
            <div class="grid grid-cols-2 gap-4 mb-4">
                <div>
                    <label for="sexo" class="block text-gray-700 text-sm font-bold mb-2">Sexo</label>
                    <select id="sexo" name="sexo" class="shadow border rounded w-full py-2 px-3 text-gray-700 focus:outline-none focus:ring-2 focus:ring-green-500">
                        <option value="M">Masculino</option>
        <option value="F">Femenino</option>
                    </select>
                </div>
                <div>
                    <label for="edad" class="block text-gray-700 text-sm font-bold mb-2">Edad</label>
                    <input type="number" id="edad" name="edad" required min="1" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 focus:outline-none focus:ring-2 focus:ring-green-500">
                </div>
            </div>
            <div class="grid grid-cols-2 gap-4 mb-6">
                <div>
                    <label for="altura" class="block text-gray-700 text-sm font-bold mb-2">Altura (cm)</label>
                    <input type="number" step="0.1" id="altura" name="altura" required class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 focus:outline-none focus:ring-2 focus:ring-green-500">
                </div>
                <div>
                    <label for="peso" class="block text-gray-700 text-sm font-bold mb-2">Peso (kg)</label>
                    <input type="number" step="0.1" id="peso" name="peso" required class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 focus:outline-none focus:ring-2 focus:ring-green-500">
                </div>
            </div>
            <div class="flex items-center justify-between">
                <button type="submit" class="bg-green-600 hover:bg-green-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline w-full">
                    Crear Cuenta
                </button>
            </div>
        </form>
         <p class="text-center text-gray-500 text-sm mt-6">
            ¿Ya tienes una cuenta? <a href="login.jsp" class="font-bold text-green-600 hover:text-green-800">Inicia Sesión</a>
        </p>
    </div>

</body>
</html>
