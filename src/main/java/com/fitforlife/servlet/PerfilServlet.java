package com.fitforlife.servlet;

import com.fitforlife.dto.UsuarioDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

// NOTA: Para la funcionalidad de actualizar, necesitarás añadir un método "actualizarUsuario" en tu UsuarioDAO.
// Este servlet prepara la lógica, pero la llamada a la base de datos está comentada.

@WebServlet("/PerfilServlet")
public class PerfilServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession sesion = req.getSession(false);
        if (sesion == null || sesion.getAttribute("usuario") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }
        req.getRequestDispatcher("perfil.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession sesion = req.getSession(false);
        if (sesion == null || sesion.getAttribute("usuario") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        try {
            UsuarioDTO usuario = (UsuarioDTO) sesion.getAttribute("usuario");

            // Actualizar los datos del objeto en sesión
            usuario.setNombre(req.getParameter("nombre"));
            usuario.setEdad(Integer.parseInt(req.getParameter("edad")));
            usuario.setAltura(Double.parseDouble(req.getParameter("altura")));
            usuario.setPesoActual(Double.parseDouble(req.getParameter("peso")));

            // --- LÓGICA DE ACTUALIZACIÓN EN LA BASE DE DATOS (requiere método en DAO) ---
            /*
            try (Connection conn = ConexionBD.getConnection()) {
                UsuarioDAO dao = new UsuarioDAO(conn);
                dao.actualizarUsuario(usuario); // <-- Necesitarás crear este método en UsuarioDAO
            }
            */

            // Guardar el objeto actualizado en la sesión
            sesion.setAttribute("usuario", usuario);
            req.setAttribute("mensaje", "¡Perfil actualizado con éxito!");
            doGet(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Error al actualizar el perfil.");
            doGet(req, resp);
        }
    }
}
