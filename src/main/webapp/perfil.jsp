<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Mi Perfil"/>
</jsp:include>

<div class="container mx-auto px-4 sm:px-8 max-w-4xl">
    <h2 class="text-2xl font-semibold text-gray-800 mb-6">Configuración del Perfil</h2>

    <%-- Bloques para mostrar mensajes de éxito o error --%>
    <c:if test="${not empty mensaje}">
        <div class="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded relative mb-4" role="alert">
            <span class="block sm:inline">${mensaje}</span>
        </div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative mb-4" role="alert">
            <span class="block sm:inline">${error}</span>
        </div>
    </c:if>

    <div class="bg-white p-8 rounded-lg shadow-sm">
        
        <!-- SECCIÓN DE FOTO DE PERFIL (CON RECORTADOR) -->
        <div class="flex flex-col items-center space-y-4 mb-8">
            <c:choose>
                <c:when test="${not empty sessionScope.usuario.fotoPerfil}">
                    <img class="h-32 w-32 rounded-full object-cover" src="imagenes/${sessionScope.usuario.fotoPerfil}" alt="Foto de perfil">
                </c:when>
                <c:otherwise>
                    <div class="h-32 w-32 rounded-full bg-gray-200 flex items-center justify-center">
                        <span class="text-5xl text-gray-500">${sessionScope.usuario.nombre.substring(0,1)}</span>
                    </div>
                </c:otherwise>
            </c:choose>
            <h3 class="text-2xl font-semibold">${sessionScope.usuario.nombre}</h3>
            <label for="foto-upload" class="cursor-pointer bg-gray-100 hover:bg-gray-200 text-gray-700 font-semibold py-2 px-4 rounded-lg transition">
                Cambiar foto
            </label>
            <input id="foto-upload" name="foto" type="file" class="hidden" accept="image/*">
        </div>
        
        <!-- FORMULARIO PARA DATOS DEL PERFIL -->
        <form action="PerfilServlet" method="POST">
            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div>
                    <label for="nombre" class="block text-sm font-medium text-gray-700">Nombre</label>
                    <input type="text" name="nombre" id="nombre" value="${sessionScope.usuario.nombre}" class="mt-1 block w-full shadow-sm sm:text-sm border-gray-300 rounded-md focus:ring-green-500 focus:border-green-500">
                </div>
                <div>
                    <label for="email" class="block text-sm font-medium text-gray-700">Email (no se puede cambiar)</label>
                    <input type="email" name="email" id="email" value="${sessionScope.usuario.email}" disabled class="mt-1 block w-full shadow-sm sm:text-sm border-gray-300 rounded-md bg-gray-50 text-gray-500">
                </div>
                <div>
                    <label for="edad" class="block text-sm font-medium text-gray-700">Edad</label>
                    <input type="number" name="edad" id="edad" value="${sessionScope.usuario.edad}" class="mt-1 block w-full shadow-sm sm:text-sm border-gray-300 rounded-md focus:ring-green-500 focus:border-green-500">
                </div>
                <div>
                    <label for="sexo" class="block text-sm font-medium text-gray-700">Sexo (no se puede cambiar)</label>
                    <input type="text" name="sexo" id="sexo" value="${sessionScope.usuario.sexo == 'M' ? 'Masculino' : 'Femenino'}" disabled class="mt-1 block w-full shadow-sm sm:text-sm border-gray-300 rounded-md bg-gray-50 text-gray-500">
                </div>
                <div>
                    <label for="altura" class="block text-sm font-medium text-gray-700">Altura (cm)</label>
                    <input type="number" step="0.1" name="altura" id="altura" value="${sessionScope.usuario.altura}" class="mt-1 block w-full shadow-sm sm:text-sm border-gray-300 rounded-md focus:ring-green-500 focus:border-green-500">
                </div>
                <div>
                    <label for="peso" class="block text-sm font-medium text-gray-700">Peso (kg)</label>
                    <input type="number" step="0.1" name="peso" id="peso" value="${sessionScope.usuario.pesoActual}" class="mt-1 block w-full shadow-sm sm:text-sm border-gray-300 rounded-md focus:ring-green-500 focus:border-green-500">
                </div>
            </div>
            <div class="mt-8 text-right">
                <button type="submit" class="inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-green-600 hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-green-500">
                    Guardar Cambios
                </button>
            </div>
        </form>
    </div>
</div>

<!-- ===================================================================== -->
<!-- == MODAL PARA RECORTAR LA IMAGEN (AHORA DENTRO DEL CONTENIDO) == -->
<!-- ===================================================================== -->
<div id="cropperModal" class="fixed inset-0 bg-gray-900 bg-opacity-75 flex items-center justify-center z-50 hidden">
    <div class="bg-white p-6 rounded-lg shadow-xl w-full max-w-md mx-4">
        <h3 class="text-xl font-semibold mb-4">Ajustar Foto de Perfil</h3>
        <div class="img-container h-64 mb-4">
            <img id="imageToCrop" src="" alt="Imagen para recortar">
        </div>
        <div class="flex justify-end gap-4">
            <button id="cancelCrop" type="button" class="py-2 px-4 bg-gray-200 text-gray-800 rounded-lg hover:bg-gray-300">Cancelar</button>
            <button id="saveCrop" type="button" class="py-2 px-4 bg-green-600 text-white rounded-lg hover:bg-green-700">Guardar</button>
        </div>
    </div>
</div>


<!-- ===================================================================== -->
<!-- == SCRIPT DEL RECORTADOR (AHORA DENTRO DEL CONTENIDO) == -->
<!-- ===================================================================== -->
<script>
document.addEventListener('DOMContentLoaded', function () {
    const fileInput = document.getElementById('foto-upload');
    const modal = document.getElementById('cropperModal');
    const image = document.getElementById('imageToCrop');
    const saveButton = document.getElementById('saveCrop');
    const cancelButton = document.getElementById('cancelCrop');
    let cropper;

    // 1. Cuando el usuario selecciona un archivo
    fileInput.addEventListener('change', e => {
        const files = e.target.files;
        if (files && files.length > 0) {
            const file = files[0];
            const reader = new FileReader();
            reader.onload = () => {
                image.src = reader.result;
                modal.classList.remove('hidden');
                
                // Destruir instancia anterior de Cropper si existe
                if(cropper) {
                   cropper.destroy();
                }

                // Iniciar Cropper.js
                cropper = new Cropper(image, {
                    aspectRatio: 1, // Recorte cuadrado
                    viewMode: 1,
                    background: false,
                });
            };
            reader.readAsDataURL(file);
        }
    });

    // 2. Cuando el usuario hace clic en "Guardar"
    saveButton.addEventListener('click', () => {
        if (cropper) {
            const canvas = cropper.getCroppedCanvas({
                width: 256,
                height: 256,
            });

            canvas.toBlob(blob => {
                const formData = new FormData();
                formData.append('foto', blob, 'profile.jpg');

                // Enviamos el archivo recortado al FotoServlet
                fetch('FotoServlet', {
                    method: 'POST',
                    body: formData,
                })
                .then(response => {
                    if (response.ok) {
                        window.location.reload(); // Recargamos para ver la nueva foto
                    } else {
                        alert('Hubo un error al subir la imagen.');
                        closeModal();
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('Hubo un error de conexión.');
                    closeModal();
                });

            }, 'image/jpeg');
        }
    });
    
    // Función para cerrar el modal
    const closeModal = () => {
        modal.classList.add('hidden');
        if (cropper) {
            cropper.destroy();
        }
        fileInput.value = ''; // Reseteamos el input para poder seleccionar el mismo archivo otra vez
    };

    // 3. Cuando el usuario hace clic en "Cancelar"
    cancelButton.addEventListener('click', closeModal);
});
</script>

<%@ include file="footer.jsp" %>