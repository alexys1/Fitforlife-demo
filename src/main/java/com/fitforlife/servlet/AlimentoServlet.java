package com.fitforlife.servlet;

import com.fitforlife.dao.AlimentoDAO;
import com.fitforlife.dao.IMCRegistroDAO;
import com.fitforlife.dto.AlimentoDTO;
import com.fitforlife.dto.IMCRegistroDTO;
import com.fitforlife.dto.UsuarioDTO;
import com.fitforlife.util.ConexionBD;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/AlimentoServlet")
public class AlimentoServlet extends HttpServlet {

    /**
     * Este método ahora se encarga de mostrar la lista de alimentos,
     * priorizando las recomendaciones basadas en la condición del usuario.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession sesion = req.getSession(false);
        if (sesion == null || sesion.getAttribute("usuario") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        UsuarioDTO usuario = (UsuarioDTO) sesion.getAttribute("usuario");
        String categoria = req.getParameter("tipo");

        // Si no se especifica una categoría en la URL, por defecto mostramos "Almuerzo"
        if (categoria == null || categoria.isEmpty()) {
            categoria = "Almuerzo";
        }

        try (Connection conn = ConexionBD.getConnection()) {
            // 1. Obtenemos la condición actual del usuario (ej: "Sobrepeso") desde la tabla de IMC
            IMCRegistroDAO imcDAO = new IMCRegistroDAO(conn);
            IMCRegistroDTO ultimoIMC = imcDAO.obtenerUltimoIMCporUsuario(usuario.getIdUsuario());
            String condicionUsuario = (ultimoIMC != null) ? ultimoIMC.getEstado() : "Normal";
            
            // 2. Pasamos la condición a la vista (JSP) para que pueda mostrar la estrellita
            req.setAttribute("condicionUsuario", condicionUsuario);

            // 3. Llamamos al nuevo método del DAO que ordena por recomendación
            AlimentoDAO dao = new AlimentoDAO(conn);
            List<AlimentoDTO> lista = dao.listarAlimentosRecomendados(categoria, condicionUsuario);
            
            req.setAttribute("alimentos", lista);
            req.getRequestDispatcher("listarAlimentos.jsp").forward(req, resp);
            
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("error.jsp");
        }
    }

    /**
     * Este método se usa para registrar un NUEVO alimento en la base de datos.
     * Se mantiene sin cambios.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8"); // Buena práctica para aceptar acentos
        String nombre = req.getParameter("nombre");
        int calorias = Integer.parseInt(req.getParameter("calorias"));
        double proteinas = Double.parseDouble(req.getParameter("proteinas"));
        double carbohidratos = Double.parseDouble(req.getParameter("carbohidratos"));
        double grasas = Double.parseDouble(req.getParameter("grasas"));
        String porcion = req.getParameter("porcion");
        String categoria = req.getParameter("categoria");
        // Asumimos que la etiqueta de salud se manejaría en un formulario más avanzado
        // Por ahora, le asignamos "Normal" por defecto al crear uno nuevo.
        String etiquetaSalud = "Normal";

        // Creamos el DTO. NOTA: Asegúrate que tu DTO tenga un constructor compatible.
        // Si no, puedes usar los setters: alimento.setNombre(nombre), etc.
        AlimentoDTO alimento = new AlimentoDTO();
        alimento.setNombre(nombre);
        alimento.setCalorias(calorias);
        alimento.setProteinas(proteinas);
        alimento.setCarbohidratos(carbohidratos);
        alimento.setGrasas(grasas);
        alimento.setPorcion(porcion);
        alimento.setCategoria(categoria);
        alimento.setEtiquetaSalud(etiquetaSalud);


        try (Connection conn = ConexionBD.getConnection()) {
            AlimentoDAO dao = new AlimentoDAO(conn);
            // NOTA: Puede que necesites actualizar tu método registrarAlimento en el DAO
            // para que también guarde la nueva columna "etiqueta_salud".
            dao.registrarAlimento(alimento); 
            resp.sendRedirect("AlimentoServlet");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("error.jsp");
        }
    }
}

