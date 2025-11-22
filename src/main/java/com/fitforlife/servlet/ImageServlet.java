package com.fitforlife.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/imagenes/*")
public class ImageServlet extends HttpServlet {

    // IMPORTANTE: Debe ser la misma ruta que en FotoServlet
    private static final String UPLOAD_DIR = "C:/fitforlife_uploads/";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String filename = request.getPathInfo().substring(1);
        File file = new File(UPLOAD_DIR, filename);

        if (file.exists() && !file.isDirectory()) {
            response.setContentType(getServletContext().getMimeType(filename));
            response.setContentLength((int) file.length());

            try (FileInputStream in = new FileInputStream(file);
                 OutputStream out = response.getOutputStream()) {

                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404 si la imagen no existe
        }
    }
}