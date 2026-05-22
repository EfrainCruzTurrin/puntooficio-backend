package com.puntooficio.puntooficio;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.puntooficio.puntooficio.auth.dtos.request.ClienteRegisterRequestDto;
import com.puntooficio.puntooficio.auth.dtos.request.LoginRequestDto;
import com.puntooficio.puntooficio.auth.dtos.request.TrabajadorRegisterRequestDto;
import com.puntooficio.puntooficio.resena.dtos.request.ResenaRequestDto;
import com.puntooficio.puntooficio.shared.enums.Role;
import com.puntooficio.puntooficio.trabajador.models.Trabajador;
import com.puntooficio.puntooficio.trabajador.repositories.TrabajadorRepository;
import com.puntooficio.puntooficio.cliente.models.Cliente;
import com.puntooficio.puntooficio.cliente.repositories.ClienteRepository;
import com.puntooficio.puntooficio.resena.models.Resena;
import com.puntooficio.puntooficio.resena.repositories.ResenaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests para endpoints críticos de PuntoOficio.
 * Cubre: autenticación, trabajadores, reseñas, rutas protegidas.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class IntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TrabajadorRepository trabajadorRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ResenaRepository resenaRepository;

    private String trabajadorToken;
    private String clienteToken;
    private String adminToken;
    private Long trabajadorId;
    private Long clienteId;

    @BeforeEach
    public void setUp() throws Exception {
        // Limpiar datos
        resenaRepository.deleteAll();
        clienteRepository.deleteAll();
        trabajadorRepository.deleteAll();

        // Registrar trabajador de prueba
        TrabajadorRegisterRequestDto trabajadorReg = new TrabajadorRegisterRequestDto();
        trabajadorReg.setEmail("trabajador@test.com");
        trabajadorReg.setPassword("Prueba123");
        trabajadorReg.setCiudadId(1L);
        trabajadorReg.setCategoriaId(1L);

        MvcResult trabajadorResult = mockMvc.perform(post("/api/auth/register/trabajador")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(trabajadorReg)))
                .andReturn();

        String trabajadorResponseBody = trabajadorResult.getResponse().getContentAsString();
        trabajadorToken = objectMapper.readTree(trabajadorResponseBody).get("token").asText();
        trabajadorId = objectMapper.readTree(trabajadorResponseBody).get("userId").asLong();

        // Registrar cliente de prueba
        ClienteRegisterRequestDto clienteReg = new ClienteRegisterRequestDto();
        clienteReg.setEmail("cliente@test.com");
        clienteReg.setPassword("Prueba123");

        MvcResult clienteResult = mockMvc.perform(post("/api/auth/register/cliente")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteReg)))
                .andReturn();

        String clienteResponseBody = clienteResult.getResponse().getContentAsString();
        clienteToken = objectMapper.readTree(clienteResponseBody).get("token").asText();
        clienteId = objectMapper.readTree(clienteResponseBody).get("userId").asLong();

        // Login como admin (asumimos que existe en BD de inicio)
        LoginRequestDto adminLogin = new LoginRequestDto();
        adminLogin.setEmail("admin@puntooficio.com");
        adminLogin.setPassword("admin1234");

        MvcResult adminResult = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(adminLogin)))
                .andExpect(status().isOk())
                .andReturn();

        String adminResponseBody = adminResult.getResponse().getContentAsString();
        adminToken = objectMapper.readTree(adminResponseBody).get("token").asText();
    }

    // ======================== AUTH TESTS ========================

    @Test
    public void testRegistroTrabajadorExitoso() throws Exception {
        TrabajadorRegisterRequestDto dto = new TrabajadorRegisterRequestDto();
        dto.setEmail("newtrabajador@test.com");
        dto.setPassword("Nuevo123");
        dto.setCiudadId(1L);
        dto.setCategoriaId(1L);

        mockMvc.perform(post("/api/auth/register/trabajador")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.role").value("TRABAJADOR"))
                .andExpect(jsonPath("$.userId").isNumber());
    }

    @Test
    public void testRegistroTrabajadorEmailDuplicado() throws Exception {
        TrabajadorRegisterRequestDto dto = new TrabajadorRegisterRequestDto();
        dto.setEmail("trabajador@test.com"); // Ya existe
        dto.setPassword("Nuevo123");
        dto.setCiudadId(1L);
        dto.setCategoriaId(1L);

        mockMvc.perform(post("/api/auth/register/trabajador")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    public void testRegistroClienteExitoso() throws Exception {
        ClienteRegisterRequestDto dto = new ClienteRegisterRequestDto();
        dto.setEmail("newcliente@test.com");
        dto.setPassword("Nuevo123");

        mockMvc.perform(post("/api/auth/register/cliente")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.role").value("CLIENTE"));
    }

    @Test
    public void testRegistroClienteEmailDuplicado() throws Exception {
        ClienteRegisterRequestDto dto = new ClienteRegisterRequestDto();
        dto.setEmail("cliente@test.com"); // Ya existe
        dto.setPassword("Nuevo123");

        mockMvc.perform(post("/api/auth/register/cliente")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isConflict());
    }

    @Test
    public void testLoginTrabajadorExitoso() throws Exception {
        LoginRequestDto dto = new LoginRequestDto();
        dto.setEmail("trabajador@test.com");
        dto.setPassword("Prueba123");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.role").value("TRABAJADOR"));
    }

    @Test
    public void testLoginClienteExitoso() throws Exception {
        LoginRequestDto dto = new LoginRequestDto();
        dto.setEmail("cliente@test.com");
        dto.setPassword("Prueba123");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.role").value("CLIENTE"));
    }

    @Test
    public void testLoginCredencialesIncorrectas() throws Exception {
        LoginRequestDto dto = new LoginRequestDto();
        dto.setEmail("trabajador@test.com");
        dto.setPassword("IncorrectPassword");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testLoginEmailNoExiste() throws Exception {
        LoginRequestDto dto = new LoginRequestDto();
        dto.setEmail("noexiste@test.com");
        dto.setPassword("Prueba123");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetMeAutenticado() throws Exception {
        mockMvc.perform(get("/api/auth/me")
                .header("Authorization", "Bearer " + trabajadorToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.role").value("TRABAJADOR"))
                .andExpect(jsonPath("$.userId").isNumber());
    }

    @Test
    public void testGetMeSinToken() throws Exception {
        mockMvc.perform(get("/api/auth/me"))
                .andExpect(status().isUnauthorized());
    }

    // ======================== TRABAJADORES TESTS ========================

    @Test
    public void testListarTrabajadoresPublico() throws Exception {
        mockMvc.perform(get("/api/trabajadores/lista"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.totalElements").isNumber())
                .andExpect(jsonPath("$.totalPages").isNumber());
    }

    @Test
    public void testListarTrabajadoresFiltrarPorCategoria() throws Exception {
        mockMvc.perform(get("/api/trabajadores/lista?categoriaId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    public void testListarTrabajadoresFiltrarPorCiudad() throws Exception {
        mockMvc.perform(get("/api/trabajadores/lista?ciudad=Las Varillas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    public void testListarTrabajadoresFiltrarPorAmbos() throws Exception {
        mockMvc.perform(get("/api/trabajadores/lista?categoriaId=1&ciudad=Las Varillas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    public void testObtenerPerfilTrabajadorPublico() throws Exception {
        mockMvc.perform(get("/api/trabajadores/" + trabajadorId + "/perfil"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(trabajadorId))
                .andExpect(jsonPath("$.email").isNotEmpty());
    }

    @Test
    public void testObtenerPerfilTrabajadorNoExiste() throws Exception {
        mockMvc.perform(get("/api/trabajadores/99999/perfil"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testObtenerMiPerfilTrabajador() throws Exception {
        mockMvc.perform(get("/api/trabajadores/mi-perfil")
                .header("Authorization", "Bearer " + trabajadorToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(trabajadorId));
    }

    @Test
    public void testObtenerMiPerfilSinToken() throws Exception {
        mockMvc.perform(get("/api/trabajadores/mi-perfil"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testObtenerMiPerfilClienteNoAutorizado() throws Exception {
        mockMvc.perform(get("/api/trabajadores/mi-perfil")
                .header("Authorization", "Bearer " + clienteToken))
                .andExpect(status().isForbidden());
    }

    // ======================== RESEÑAS TESTS ========================

    @Test
    public void testCrearReseñaExitosa() throws Exception {
        ResenaRequestDto dto = new ResenaRequestDto();
        dto.setTrabajadorId(trabajadorId);
        dto.setPuntaje(5);
        dto.setComentario("Excelente servicio, muy recomendado");

        mockMvc.perform(post("/api/resenas")
                .header("Authorization", "Bearer " + clienteToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.puntaje").value(5))
                .andExpect(jsonPath("$.comentario").value("Excelente servicio, muy recomendado"));
    }

    @Test
    public void testCrearReseñaSinToken() throws Exception {
        ResenaRequestDto dto = new ResenaRequestDto();
        dto.setTrabajadorId(trabajadorId);
        dto.setPuntaje(5);
        dto.setComentario("Excelente servicio");

        mockMvc.perform(post("/api/resenas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testCrearReseñaTrabajadorNoAutorizado() throws Exception {
        ResenaRequestDto dto = new ResenaRequestDto();
        dto.setTrabajadorId(trabajadorId);
        dto.setPuntaje(5);
        dto.setComentario("Excelente servicio");

        mockMvc.perform(post("/api/resenas")
                .header("Authorization", "Bearer " + trabajadorToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testCrearResenaResenaDuplicada() throws Exception {
        // Primera reseña
        ResenaRequestDto dto1 = new ResenaRequestDto();
        dto1.setTrabajadorId(trabajadorId);
        dto1.setPuntaje(5);
        dto1.setComentario("Excelente");

        mockMvc.perform(post("/api/resenas")
                .header("Authorization", "Bearer " + clienteToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto1)))
                .andExpect(status().isCreated());

        // Segunda reseña del mismo cliente al mismo trabajador
        ResenaRequestDto dto2 = new ResenaRequestDto();
        dto2.setTrabajadorId(trabajadorId);
        dto2.setPuntaje(4);
        dto2.setComentario("Bueno");

        mockMvc.perform(post("/api/resenas")
                .header("Authorization", "Bearer " + clienteToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto2)))
                .andExpect(status().isConflict());
    }

    @Test
    public void testCrearResenaTrabajadorNoExiste() throws Exception {
        ResenaRequestDto dto = new ResenaRequestDto();
        dto.setTrabajadorId(99999L);
        dto.setPuntaje(5);
        dto.setComentario("Excelente");

        mockMvc.perform(post("/api/resenas")
                .header("Authorization", "Bearer " + clienteToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testObtenerResenasPorTrabajador() throws Exception {
        // Crear una reseña
        ResenaRequestDto dto = new ResenaRequestDto();
        dto.setTrabajadorId(trabajadorId);
        dto.setPuntaje(5);
        dto.setComentario("Excelente");

        mockMvc.perform(post("/api/resenas")
                .header("Authorization", "Bearer " + clienteToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        // Obtener reseñas
        mockMvc.perform(get("/api/resenas/trabajador/" + trabajadorId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].puntaje").value(5));
    }

    @Test
    public void testPuntajePromedioSeActualiza() throws Exception {
        // Crear reseña 1: puntaje 5
        ResenaRequestDto dto1 = new ResenaRequestDto();
        dto1.setTrabajadorId(trabajadorId);
        dto1.setPuntaje(5);
        dto1.setComentario("Excelente");

        mockMvc.perform(post("/api/resenas")
                .header("Authorization", "Bearer " + clienteToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto1)))
                .andExpect(status().isCreated());

        // Crear segundo cliente
        ClienteRegisterRequestDto clienteReg2 = new ClienteRegisterRequestDto();
        clienteReg2.setEmail("cliente2@test.com");
        clienteReg2.setPassword("Prueba123");

        MvcResult result = mockMvc.perform(post("/api/auth/register/cliente")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteReg2)))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String clienteToken2 = objectMapper.readTree(responseBody).get("token").asText();

        // Crear reseña 2: puntaje 3
        ResenaRequestDto dto2 = new ResenaRequestDto();
        dto2.setTrabajadorId(trabajadorId);
        dto2.setPuntaje(3);
        dto2.setComentario("Bueno");

        mockMvc.perform(post("/api/resenas")
                .header("Authorization", "Bearer " + clienteToken2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto2)))
                .andExpect(status().isCreated());

        // Verificar puntaje promedio
        mockMvc.perform(get("/api/trabajadores/" + trabajadorId + "/perfil"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.puntajePromedio").value(4.0));
    }

    // ======================== RUTAS PROTEGIDAS TESTS ========================

    @Test
    public void testRutaProtegidaClienteNoAccedePanelTrabajador() throws Exception {
        // Las URLs de panel pueden variar, pero probamos que cliente no accede con token de trabajador
        mockMvc.perform(get("/api/trabajadores/mi-perfil")
                .header("Authorization", "Bearer " + clienteToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testRutaProtegidaTrabajadorNoAccedePanelAdmin() throws Exception {
        // Asumiendo que hay un endpoint admin protegido
        // Ejemplo: GET /api/admin/trabajadores (listar como admin)
        mockMvc.perform(get("/api/admin/trabajadores")
                .header("Authorization", "Bearer " + trabajadorToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testTokenExpiradoOInvalido() throws Exception {
        mockMvc.perform(get("/api/auth/me")
                .header("Authorization", "Bearer invalid.token.here"))
                .andExpect(status().isUnauthorized());
    }

    // ======================== CATEGORIAS Y SUBCATEGORIAS TESTS ========================

    @Test
    public void testObtenerCategorias() throws Exception {
        mockMvc.perform(get("/api/categorias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testObtenerSubcategorias() throws Exception {
        mockMvc.perform(get("/api/subcategorias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testObtenerSubcategoriasPorCategoria() throws Exception {
        mockMvc.perform(get("/api/subcategorias?categoriaId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testObtenerCiudades() throws Exception {
        mockMvc.perform(get("/api/ciudades"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[*]", hasItem("Las Varillas")));
    }

    // ======================== VALIDACIONES DE DATOS TESTS ========================

    @Test
    public void testRegistroTrabajadorFaltaCiudad() throws Exception {
        TrabajadorRegisterRequestDto dto = new TrabajadorRegisterRequestDto();
        dto.setEmail("test@test.com");
        dto.setPassword("Prueba123");
        dto.setCategoriaId(1L);
        // Sin ciudad

        mockMvc.perform(post("/api/auth/register/trabajador")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRegistroTrabajadorFaltaCategoria() throws Exception {
        TrabajadorRegisterRequestDto dto = new TrabajadorRegisterRequestDto();
        dto.setEmail("test@test.com");
        dto.setPassword("Prueba123");
        dto.setCiudadId(1L);
        // Sin categoría

        mockMvc.perform(post("/api/auth/register/trabajador")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRegistroTrabajadorContraseñaCorta() throws Exception {
        TrabajadorRegisterRequestDto dto = new TrabajadorRegisterRequestDto();
        dto.setEmail("test@test.com");
        dto.setPassword("Pass"); // < 8 caracteres
        dto.setCiudadId(1L);
        dto.setCategoriaId(1L);

        mockMvc.perform(post("/api/auth/register/trabajador")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRegistroClienteContraseñaCorta() throws Exception {
        ClienteRegisterRequestDto dto = new ClienteRegisterRequestDto();
        dto.setEmail("test@test.com");
        dto.setPassword("Pass"); // < 8 caracteres

        mockMvc.perform(post("/api/auth/register/cliente")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCrearResenaPuntajeInvalido() throws Exception {
        ResenaRequestDto dto = new ResenaRequestDto();
        dto.setTrabajadorId(trabajadorId);
        dto.setPuntaje(10); // Mayor a 5
        dto.setComentario("Excelente");

        mockMvc.perform(post("/api/resenas")
                .header("Authorization", "Bearer " + clienteToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCrearResenaComentarioVacio() throws Exception {
        ResenaRequestDto dto = new ResenaRequestDto();
        dto.setTrabajadorId(trabajadorId);
        dto.setPuntaje(5);
        dto.setComentario(""); // Vacío

        mockMvc.perform(post("/api/resenas")
                .header("Authorization", "Bearer " + clienteToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }
}
