package com.fitforlife.servlet;

import com.fitforlife.dao.ConsumoDiarioDAO;
import com.fitforlife.dto.ConsumoDiarioDTO;
import com.fitforlife.dto.UsuarioDTO;
import com.fitforlife.util.ConexionBD;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;

@WebServlet("/ConsumoServlet")
public class ConsumoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession sesion = req.getSession(false);
        if (sesion == null || sesion.getAttribute("usuario") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        try {
            UsuarioDTO usuario = (UsuarioDTO) sesion.getAttribute("usuario");
            int idAlimento = Integer.parseInt(req.getParameter("idAlimento"));
            double cantidad = Double.parseDouble(req.getParameter("cantidad"));

            ConsumoDiarioDTO consumo = new ConsumoDiarioDTO();
            consumo.setIdUsuario(usuario.getIdUsuario());
            consumo.setIdAlimento(idAlimento);
            consumo.setCantidad(cantidad);
            consumo.setFecha(new Timestamp(System.currentTimeMillis()));

            try (Connection conn = ConexionBD.getConnection()) {
                ConsumoDiarioDAO dao = new ConsumoDiarioDAO(conn);
                dao.insertarConsumo(consumo);
            }

            // Redirigir de vuelta al panel para ver la actualización
            resp.sendRedirect("PanelServlet");

        } catch (Exception e) {
            e.printStackTrace();
            // Considera redirigir a una página de error más amigable
            resp.sendRedirect("error.jsp");
        }
    }
}
