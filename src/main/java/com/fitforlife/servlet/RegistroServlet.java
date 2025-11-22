package com.fitforlife.servlet;

// Se importa la clase de Guava y una lista para los errores
import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.List;

import com.fitforlife.dao.*;
import com.fitforlife.dto.*;
import com.fitforlife.util.ConexionBD;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/RegistroServlet")
public class RegistroServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // --- INICIO DE LA VALIDACIÓN AVANZADA ---

        // 1. Obtenemos todos los datos como texto para poder validarlos.
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String sexo = request.getParameter("sexo");
        String edadStr = request.getParameter("edad");
        String alturaStr = request.getParameter("altura");
        String pesoStr = request.getParameter("peso");

        // 2. Creamos una lista para acumular todos los errores de validación.
        List<String> errores = new ArrayList<>();

        // 3. Validación de campos de texto con Google Guava.
        if (Strings.isNullOrEmpty(nombre)) {
            errores.add("El campo 'Nombre' es obligatorio.");
        }
        if (Strings.isNullOrEmpty(email)) {
            errores.add("El campo 'Correo Electrónico' es obligatorio.");
        }
        if (Strings.isNullOrEmpty(password)) {
            errores.add("El campo 'Contraseña' es obligatorio.");
        }
        if (Strings.isNullOrEmpty(sexo)) {
            errores.add("Debe seleccionar un 'Sexo'.");
        }

        // 4. Validación de campos numéricos (dos pasos: vacío y formato).
        int edad = 0;
        if (Strings.isNullOrEmpty(edadStr)) {
            errores.add("El campo 'Edad' es obligatorio.");
        } else {
            try {
                edad = Integer.parseInt(edadStr);
                if (edad <= 0) {
                    errores.add("La 'Edad' debe ser un número positivo.");
                }
            } catch (NumberFormatException e) {
                errores.add("El campo 'Edad' debe ser un número válido.");
            }
        }

        double altura = 0.0;
        if (Strings.isNullOrEmpty(alturaStr)) {
            errores.add("El campo 'Altura' es obligatorio.");
        } else {
            try {
                altura = Double.parseDouble(alturaStr);
                 if (altura <= 0) {
                    errores.add("La 'Altura' debe ser un número positivo.");
                }
            } catch (NumberFormatException e) {
                errores.add("El campo 'Altura' debe ser un número válido.");
            }
        }

        double peso = 0.0;
        if (Strings.isNullOrEmpty(pesoStr)) {
            errores.add("El campo 'Peso' es obligatorio.");
        } else {
            try {
                peso = Double.parseDouble(pesoStr);
                if (peso <= 0) {
                    errores.add("El 'Peso' debe ser un número positivo.");
                }
            } catch (NumberFormatException e) {
                errores.add("El campo 'Peso' debe ser un número válido.");
            }
        }

        // 5. Si encontramos CUALQUIER error en la lista, los mostramos y detenemos el proceso.
        if (!errores.isEmpty()) {
            // Unimos todos los errores en un solo mensaje, separados por saltos de línea.
            String mensajeError = String.join("<br>", errores);
            request.setAttribute("error", mensajeError);
            request.getRequestDispatcher("registro.jsp").forward(request, response);
            return; // Detenemos la ejecución.
        }

        // --- FIN DE LA VALIDACIÓN ---
        
        // Si llegamos aquí, significa que todos los datos son válidos y podemos continuar.
        UsuarioDTO nuevoUsuario = new UsuarioDTO();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setEmail(email);
        nuevoUsuario.setPassword(password);
        nuevoUsuario.setSexo(sexo);
        nuevoUsuario.setEdad(edad); // Usamos las variables ya convertidas
        nuevoUsuario.setAltura(altura);
        nuevoUsuario.setPesoActual(peso);


        try (Connection conn = ConexionBD.getConnection()) {
            UsuarioDAO usuarioDAO = new UsuarioDAO(conn);
            int nuevoUsuarioId = usuarioDAO.registrarUsuario(nuevoUsuario);

            if (nuevoUsuarioId > 0) {
                // (El resto de tu lógica para calcular IMC, planes, etc. sigue aquí...)
                // ...
                
                double alturaEnMetros = altura / 100.0;
                double imcInicial = peso / (alturaEnMetros * alturaEnMetros);
                String condicion;
                if (imcInicial < 18.5) {
                    condicion = "Bajo peso";
                } else if (imcInicial < 25) {
                    condicion = "Normal";
                } else if (imcInicial < 30) {
                    condicion = "Sobrepeso";
                } else {
                    condicion = "Obesidad";
                }

                IMCRegistroDAO imcDAO = new IMCRegistroDAO(conn);
                IMCRegistroDTO primerIMC = new IMCRegistroDTO(0, nuevoUsuarioId, peso, altura, imcInicial, condicion, null);
                imcDAO.registrarIMC(primerIMC);

                double pesoMeta;
                switch (condicion) {
                    case "Sobrepeso":
                    case "Obesidad":
                        pesoMeta = 24.9 * (alturaEnMetros * alturaEnMetros);
                        break;
                    case "Bajo peso":
                        pesoMeta = 18.5 * (alturaEnMetros * alturaEnMetros);
                        break;
                    default:
                        pesoMeta = 22.0 * (alturaEnMetros * alturaEnMetros);
                        break;
                }

                ProgresoDTO primerProgreso = new ProgresoDTO(0, nuevoUsuarioId, pesoMeta, peso, 1, null);
                ProgresoDAO progresoDAO = new ProgresoDAO(conn);
                progresoDAO.registrarProgreso(primerProgreso);

                PlanDAO planDAO = new PlanDAO(conn);
                String tipoPlan = condicion.equals("Obesidad") ? "Sobrepeso" : condicion;
                PlanDTO planRecomendado = planDAO.obtenerPlanPorTipo(tipoPlan);
                
                if (planRecomendado != null) {
                    UsuarioPlanDAO usuarioPlanDAO = new UsuarioPlanDAO(conn);
                    UsuarioPlanDTO nuevoUsuarioPlan = new UsuarioPlanDTO();
                    nuevoUsuarioPlan.setIdUsuario(nuevoUsuarioId);
                    nuevoUsuarioPlan.setIdPlan(planRecomendado.getIdPlan());
                    usuarioPlanDAO.asignarPlan(nuevoUsuarioPlan);
                }

                response.sendRedirect("login.jsp");
            } else {
                request.setAttribute("error", "No se pudo completar el registro. El email ya podría estar en uso.");
                request.getRequestDispatcher("registro.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}