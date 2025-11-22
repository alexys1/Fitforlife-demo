package com.fitforlife.servlet;

import com.fitforlife.dao.UsuarioDAO;
import com.fitforlife.dto.UsuarioDTO;
import com.fitforlife.util.ConexionBD;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try (Connection conn = ConexionBD.getConnection()) {
            UsuarioDAO dao = new UsuarioDAO(conn);
            UsuarioDTO usuario = dao.login(email, password);

            if (usuario != null) {
                HttpSession sesion = req.getSession();
                sesion.setAttribute("usuario", usuario);
                resp.sendRedirect("PanelServlet");
            } else {
                req.setAttribute("error", "Credenciales inválidas");
                req.getRequestDispatcher("login.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("error.jsp");
        }
    }
}
