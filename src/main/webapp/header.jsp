<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels@2.0.0"></script>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">
    <title>${param.pageTitle} - FitForLife</title>
    <style> body { font-family: 'Poppins', sans-serif; } </style>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/cropperjs/1.5.12/cropper.min.css" rel="stylesheet">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/cropperjs/1.5.12/cropper.min.js"></script>
</head>
<body class="bg-gray-100">

<div class="relative flex min-h-screen">
    
    <div id="sidebarOverlay" class="fixed inset-0 bg-black bg-opacity-50 z-20 hidden md:hidden"></div>

    <!-- Sidebar: Añadido 'flex flex-col' para organizar el contenido verticalmente -->
    <aside id="sidebar" class="bg-white shadow-md w-64 flex-shrink-0 absolute inset-y-0 left-0 transform -translate-x-full md:relative md:translate-x-0 transition-transform duration-300 ease-in-out z-30 flex flex-col">
        
        <div class="flex items-center justify-between p-6">
            <h1 class="text-3xl font-bold text-green-600">FitForLife</h1>
            <button id="closeSidebarBtn" class="md:hidden text-gray-500 hover:text-gray-800">
                <svg class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" /></svg>
            </button>
        </div>
        
        <!-- Menú de Navegación (ocupa el espacio disponible con flex-grow) -->
        <nav class="mt-6 flex-grow">
            <a href="PanelServlet" class="flex items-center px-6 py-3 text-gray-700 hover:bg-green-50 hover:text-green-600">
                <svg class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2H6a2 2 0 01-2-2V6zM14 6a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2h-2a2 2 0 01-2-2V6zM4 16a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2H6a2 2 0 01-2-2v-2zM14 16a2 2 0 012-2h2a2 2 0 01-2 2h-2a2 2 0 01-2-2v-2z"/></svg>
                <span class="mx-3">Panel Principal</span>
            </a>
            <a href="AlimentoServlet" class="flex items-center px-6 py-3 text-gray-700 hover:bg-green-50 hover:text-green-600">
                <svg class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 6.253v11.494m-9-5.747h18"/></svg>
                <span class="mx-3">Registrar Alimento</span>
            </a>
            <a href="ProgresoServlet" class="flex items-center px-6 py-3 text-gray-700 hover:bg-green-50 hover:text-green-600">
                <svg class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 7h8m0 0v8m0-8l-8 8-4-4-6 6"/></svg>
                <span class="mx-3">Mi Progreso</span>
            </a>
            <a href="PerfilServlet" class="flex items-center px-6 py-3 text-gray-700 hover:bg-green-50 hover:text-green-600">
                <svg class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"/></svg>
                <span class="mx-3">Mi Perfil</span>
            </a>
        </nav>

        <!-- Botón de Cerrar Sesión al final de la barra lateral -->
        <div class="p-6 border-t border-gray-200">
            <a href="LogoutServlet" class="flex items-center justify-center w-full px-4 py-2 text-sm font-medium text-white bg-red-600 rounded-lg hover:bg-red-700 transition-colors duration-200 shadow-sm">
                <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1"></path></svg>
                Cerrar Sesión
            </a>
        </div>

    </aside>

    <div class="flex-1 flex flex-col overflow-hidden">
        <header class="flex justify-between items-center p-4 bg-white border-b z-10">
            <button id="hamburgerBtn" class="md:hidden text-gray-600 hover:text-gray-800 focus:outline-none">
                <svg class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16m-7 6h7" /></svg>
            </button>
            
            <div class="flex-grow"></div> 
            <div class="flex items-center">
                <span class="mr-3 font-semibold text-gray-700 hidden sm:inline">Hola, ${sessionScope.usuario.nombre}</span>
                <c:choose>
                    <c:when test="${not empty sessionScope.usuario.fotoPerfil}">
                        <img class="h-10 w-10 rounded-full object-cover border-2 border-green-500" src="imagenes/${sessionScope.usuario.fotoPerfil}" alt="Foto de perfil">
                    </c:when>
                    <c:otherwise>
                        <div class="h-10 w-10 rounded-full bg-green-500 flex items-center justify-center text-white font-bold text-xl border-2 border-green-600">
                            ${sessionScope.usuario.nombre.substring(0,1)}
                        </div>
                    </c:otherwise>
                </c:choose>
                <!-- Eliminado el enlace de Cerrar Sesión de aquí arriba -->
            </div>
        </header>
        <main class="flex-1 overflow-x-hidden overflow-y-auto bg-gray-100 p-6">
            <div class="container mx-auto">

<script>
    document.addEventListener('DOMContentLoaded', function() {
        const hamburgerBtn = document.getElementById('hamburgerBtn');
        const closeSidebarBtn = document.getElementById('closeSidebarBtn');
        const sidebar = document.getElementById('sidebar');
        const overlay = document.getElementById('sidebarOverlay');

        // Función para abrir el menú
        const openSidebar = () => {
            sidebar.classList.remove('-translate-x-full');
            overlay.classList.remove('hidden');
        };

        // Función para cerrar el menú
        const closeSidebar = () => {
            sidebar.classList.add('-translate-x-full');
            overlay.classList.add('hidden');
        };

        // Asignamos los eventos a los botones y al overlay
        hamburgerBtn.addEventListener('click', openSidebar);
        closeSidebarBtn.addEventListener('click', closeSidebar);
        overlay.addEventListener('click', closeSidebar);
    });
</script>