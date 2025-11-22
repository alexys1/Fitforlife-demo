package com.fitforlife.dao;

import com.fitforlife.dto.UsuarioDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UsuarioDAOTest {

    // @Mock crea una simulación (un objeto falso) de las dependencias
    @Mock
    private Connection mockConnection;
    
    @Mock
    private PreparedStatement mockPreparedStatement;
    
    @Mock
    private ResultSet mockResultSet;

    // @InjectMocks crea una instancia de UsuarioDAO e intenta 
    // inyectar los @Mock (en este caso, el 'mockConnection') en él.
    @InjectMocks
    private UsuarioDAO usuarioDAO;

    @BeforeEach
    void setUp() throws Exception {
        // Inicializa los mocks antes de cada prueba
        MockitoAnnotations.openMocks(this);

        // Configuramos la simulación:
        // 1. Cuando se llame a mockConnection.prepareStatement()...
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        // 2. Cuando se llame a mockPreparedStatement.executeQuery()...
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
    }

    /**
     * Prueba (TDD Paso 1):
     * Probar que el login funciona cuando las credenciales son correctas.
     */
    @Test
    void testLoginExitoso() throws Exception {
        // (TDD Paso 2: Configurar la simulación)
        // Simulamos que el ResultSet SÍ encontró un usuario
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id_usuario")).thenReturn(1);
        when(mockResultSet.getString("nombre")).thenReturn("Carlos Pérez");
        when(mockResultSet.getString("email")).thenReturn("carlos@mail.com");
        when(mockResultSet.getString("foto_perfil")).thenReturn("foto.jpg");
        // ... (configura los otros campos: sexo, edad, altura, etc.)

        // (TDD Paso 3: Ejecutar el método)
        UsuarioDTO resultado = usuarioDAO.login("carlos@mail.com", "12345");

        // (TDD Paso 4: Validar el resultado)
        assertNotNull(resultado, "El usuario no debería ser nulo.");
        assertEquals("Carlos Pérez", resultado.getNombre(), "El nombre no coincide.");
        assertEquals("carlos@mail.com", resultado.getEmail(), "El email no coincide.");
        assertEquals("foto.jpg", resultado.getFotoPerfil(), "La foto de perfil no coincide.");
    }

    /**
     * Prueba (TDD Paso 1):
     * Probar que el login falla (devuelve null) cuando las credenciales son incorrectas.
     */
    @Test
    void testLoginFallido() throws Exception {
        // (TDD Paso 2: Configurar la simulación)
        // Simulamos que el ResultSet NO encontró ningún usuario
        when(mockResultSet.next()).thenReturn(false);

        // (TDD Paso 3: Ejecutar el método)
        UsuarioDTO resultado = usuarioDAO.login("usuario.incorrecto@mail.com", "pass_malo");

        // (TDD Paso 4: Validar el resultado)
        assertNull(resultado, "El resultado debería ser nulo para credenciales incorrectas.");
    }
}