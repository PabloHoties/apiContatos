package br.com.cotiinformatica;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.cotiinformatica.entities.Categoria;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoriasTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	static UUID idCategoria;

	@Test
	@Order(1)
	public void consultarCategoriasComSucessoTest() throws Exception {

		MvcResult result = mockMvc.perform(get("/api/categorias")).andExpectAll(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		List<Categoria> categorias = objectMapper.readValue(content, new TypeReference<List<Categoria>>() {});
		assertFalse(categorias.isEmpty());
		idCategoria = categorias.get(0).getId();	
	}

	@Test
	@Order(2)
	public void obterCategoriaComSucessoTest() throws Exception {
		
		MvcResult result = mockMvc.perform(get("/api/categorias/" + idCategoria).contentType("application/json"))
				.andExpectAll(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		assertTrue(content.contains(idCategoria.toString()));
	}

	@Test
	@Order(3)
	public void obterCategoriaComIdInvalidoTest() throws Exception {

		MvcResult result = mockMvc.perform(get("/api/categorias/" + UUID.randomUUID()).contentType("application/json"))
				.andExpectAll(status().isBadRequest()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		assertTrue(content.contains("Categoria não encontrada. Verifique o ID informado."));
	}

}
