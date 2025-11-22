package com.fitforlife.servlet;

import com.fitforlife.dto.UsuarioDTO;
import com.fitforlife.dao.IMCRegistroDAO; 
import com.fitforlife.dto.IMCRegistroDTO;
import com.fitforlife.dao.PlanDAO;
import com.fitforlife.dto.PlanDTO;
import com.fitforlife.dao.ConsumoDiarioDAO;
import com.fitforlife.dao.ProgresoDAO; // <-- IMPORTANTE: Nuevo Import
import com.fitforlife.dto.ProgresoDTO; // <-- IMPORTANTE: Nuevo Import
import com.fitforlife.util.ConexionBD;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.List; // <-- IMPORTANTE: Nuevo Import
import java.util.Map;

@WebServlet("/PanelServlet")
public class PanelServlet extends HttpServlet {
   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    HttpSession sesion = req.getSession(false);
    if (sesion == null || sesion.getAttribute("usuario") == null) {
        resp.sendRedirect("login.jsp");
        return;
    }

    UsuarioDTO usuario = (UsuarioDTO) sesion.getAttribute("usuario");
    
    try (Connection conn = ConexionBD.getConnection()) {
        
        // 1. Lógica para Plan
        PlanDAO planDAO = new PlanDAO(conn);
        PlanDTO planAsignado = planDAO.obtenerPlanPorUsuario(usuario.getIdUsuario());
        req.setAttribute("planAsignado", planAsignado);
        
        // 2. Lógica para IMC
        IMCRegistroDAO imcDAO = new IMCRegistroDAO(conn);
        IMCRegistroDTO ultimoIMC = imcDAO.obtenerUltimoIMCporUsuario(usuario.getIdUsuario());
        req.setAttribute("ultimoIMC", ultimoIMC);
        
        // 3. Lógica de Consumo Diario
        ConsumoDiarioDAO consumoDAO = new ConsumoDiarioDAO(conn);
        Map<String, Double> resumenConsumo = consumoDAO.obtenerResumenConsumoHoy(usuario.getIdUsuario());
        req.setAttribute("resumenConsumo", resumenConsumo);

        // ========================================================================
        // 4. NUEVA LÓGICA: OBTENER PESO ACTUAL DEL HISTORIAL
        // ========================================================================
        ProgresoDAO progresoDAO = new ProgresoDAO(conn);
        // Obtenemos todo el historial del usuario
        List<ProgresoDTO> historial = progresoDAO.listarProgresoPorUsuario(usuario.getIdUsuario());
        
        // Por defecto, asumimos que el peso actual es el peso inicial del usuario
        double pesoActualCalculado = usuario.getPesoActual(); 
        
        // Si el historial NO está vacío, tomamos el primer elemento.
        // Como tu DAO ordena por fecha descendente (ORDER BY fecha DESC), 
        // el primer elemento (índice 0) es siempre el registro más reciente.
        if (historial != null && !historial.isEmpty()) {
            pesoActualCalculado = historial.get(0).getPesoSemana();
        }
        
        // Enviamos este dato calculado al JSP con el nombre "pesoActualCalculado"
        req.setAttribute("pesoActualCalculado", pesoActualCalculado);
        // ========================================================================

    } catch (Exception e) {
        e.printStackTrace();
    }
    
    req.getRequestDispatcher("panel.jsp").forward(req, resp);
}
}