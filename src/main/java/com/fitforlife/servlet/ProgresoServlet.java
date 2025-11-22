package com.fitforlife.servlet;

import com.fitforlife.dao.ProgresoDAO;
import com.fitforlife.dto.ProgresoDTO;
import com.fitforlife.dto.UsuarioDTO;
import com.fitforlife.util.ConexionBD;
import com.fitforlife.dao.IMCRegistroDAO; // <-- Importante: Añadir este import
import com.fitforlife.dto.IMCRegistroDTO; // <-- Importante: Añadir este import

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/ProgresoServlet")
public class ProgresoServlet extends HttpServlet {

    /**
     * Carga todos los datos necesarios para la página "Mi Progreso":
     * 1. El historial de progreso para el gráfico.
     * 2. El registro específico del día de hoy (si existe) para el formulario condicional.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession sesion = req.getSession(false);
        if (sesion == null || sesion.getAttribute("usuario") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }
        UsuarioDTO usuario = (UsuarioDTO) sesion.getAttribute("usuario");

        try (Connection conn = ConexionBD.getConnection()) {
            ProgresoDAO dao = new ProgresoDAO(conn);

            // Obtenemos el historial completo para el gráfico y la barra de progreso
            List<ProgresoDTO> listaProgreso = dao.listarProgresoPorUsuario(usuario.getIdUsuario());
            req.setAttribute("listaProgreso", listaProgreso);

            // Verificamos si ya hay un registro para el día de hoy
            ProgresoDTO progresoHoy = dao.obtenerProgresoDeHoy(usuario.getIdUsuario());
            req.setAttribute("progresoHoy", progresoHoy); // Será null si no hay registro hoy

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("error.jsp");
        }
        req.getRequestDispatcher("progreso.jsp").forward(req, resp);
    }

    /**
     * Procesa la creación o actualización de un registro de peso.
     * AHORA CALCULA EL PESO META AUTOMÁTICAMENTE al crear un nuevo registro.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession sesion = req.getSession(false);
        if (sesion == null || sesion.getAttribute("usuario") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }
        UsuarioDTO usuario = (UsuarioDTO) sesion.getAttribute("usuario");
        
        String action = req.getParameter("action");
        double pesoActual = Double.parseDouble(req.getParameter("pesoActual"));

        try (Connection conn = ConexionBD.getConnection()) {
            ProgresoDAO dao = new ProgresoDAO(conn);

            if ("update".equals(action)) {
                // Lógica para ACTUALIZAR el registro de hoy (se mantiene igual)
                int idProgreso = Integer.parseInt(req.getParameter("idProgreso"));
                dao.actualizarProgreso(idProgreso, pesoActual);
            } else {
                // Lógica para CREAR un nuevo registro con PESO META INTELIGENTE
                
                // --- INICIO DE LA NUEVA LÓGICA PARA CALCULAR PESO META ---
                double pesoMeta;
                
                // 1. Obtenemos la altura del usuario en metros
                double alturaEnMetros = usuario.getAltura() / 100.0;

                // 2. Obtenemos su condición actual (estado de IMC)
                IMCRegistroDAO imcDAO = new IMCRegistroDAO(conn);
                IMCRegistroDTO ultimoIMC = imcDAO.obtenerUltimoIMCporUsuario(usuario.getIdUsuario());
                String condicion = (ultimoIMC != null) ? ultimoIMC.getEstado() : "Normal";
                
                // 3. Calculamos el peso meta según la condición
                switch (condicion) {
                    case "Sobrepeso":
                    case "Obesidad":
                        // Meta: Alcanzar un IMC de 24.9 (límite superior de "Normal")
                        pesoMeta = 24.9 * (alturaEnMetros * alturaEnMetros);
                        break;
                    case "Bajo peso":
                        // Meta: Alcanzar un IMC de 18.5 (límite inferior de "Normal")
                        pesoMeta = 18.5 * (alturaEnMetros * alturaEnMetros);
                        break;
                    default: // "Normal"
                        // Meta: Apuntar a un IMC ideal de 22
                        pesoMeta = 22.0 * (alturaEnMetros * alturaEnMetros);
                        break;
                }
                // --- FIN DE LA NUEVA LÓGICA ---
                
                List<ProgresoDTO> historial = dao.listarProgresoPorUsuario(usuario.getIdUsuario());
                int semana = historial.isEmpty() ? 1 : historial.get(0).getSemana() + 1;
                
                ProgresoDTO nuevoProgreso = new ProgresoDTO(0, usuario.getIdUsuario(), pesoMeta, pesoActual, semana, null);
                dao.registrarProgreso(nuevoProgreso);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Redirigimos siempre al servlet para que recargue todos los datos actualizados
        resp.sendRedirect("ProgresoServlet");
    }
}