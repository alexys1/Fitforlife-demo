package com.fitforlife.servlet;

import com.fitforlife.dao.UsuarioDAO;
import com.fitforlife.dto.UsuarioDTO;
import com.fitforlife.util.ConexionBD;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;

@WebServlet("/FotoServlet")
@MultipartConfig // ¡Anotación CLAVE para la subida de archivos!
public class FotoServlet extends HttpServlet {

    // IMPORTANTE: Cambia esta ruta a la carpeta que creaste en el Paso 1
    private static final String UPLOAD_DIR = "C:/fitforlife_uploads/";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sesion = request.getSession(false);
        if (sesion == null || sesion.getAttribute("usuario") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        UsuarioDTO usuario = (UsuarioDTO) sesion.getAttribute("usuario");
        Part filePart = request.getPart("foto"); // "foto" es el name del input en el JSP
        String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        if (originalFileName != null && !originalFileName.isEmpty()) {
            try (Connection conn = ConexionBD.getConnection()) {
                // Generar un nombre de archivo único para evitar colisiones
                String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
                String nuevoNombreArchivo = "user_" + usuario.getIdUsuario() + "_" + System.currentTimeMillis() + extension;

                // Guardar el archivo en el disco
                File uploads = new File(UPLOAD_DIR);
                if (!uploads.exists()) {
                    uploads.mkdirs(); // Crea la carpeta si no existe
                }
                File file = new File(uploads, nuevoNombreArchivo);
                filePart.write(file.getAbsolutePath());

                // Actualizar la base de datos con el nuevo nombre de archivo
                UsuarioDAO dao = new UsuarioDAO(conn);
                dao.actualizarFotoPerfil(usuario.getIdUsuario(), nuevoNombreArchivo);

                // Actualizar el objeto usuario en la sesión actual para reflejar el cambio inmediatamente
                usuario.setFotoPerfil(nuevoNombreArchivo);
                sesion.setAttribute("usuario", usuario);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        response.sendRedirect("perfil.jsp"); // Redirigir de vuelta al perfil
    }
}