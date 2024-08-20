package br.com.cotiinformatica;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
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
import com.github.javafaker.Faker;

import br.com.cotiinformatica.dtos.ContatoRequest;
import br.com.cotiinformatica.dtos.ContatoResponse;
import br.com.cotiinformatica.entities.Categoria;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ContatosTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	static UUID idContato;

	@Test
	@Order(1)
	public void criarContatoComSucessoTest() throws Exception {

		Locale locale = new Locale("pt", "BR");

		Faker faker = new Faker(locale);

		ContatoRequest request = new ContatoRequest();
		request.setNome(faker.name().fullName());
		request.setTelefone(faker.regexify("\\(\\d{2}\\)\\s\\d{5}-\\d{4}"));

		MvcResult resultCategorias = mockMvc.perform(get("/api/categorias").contentType("application/json"))
				.andExpectAll(status().isOk()).andReturn();

		String contentCategoria = resultCategorias.getResponse().getContentAsString(StandardCharsets.UTF_8);

		List<Categoria> categorias = objectMapper.readValue(contentCategoria, new TypeReference<List<Categoria>>() {
		});

		request.setCategoriaId(categorias.get(0).getId());

		MvcResult resultContatos = mockMvc
				.perform(post("/api/contatos").contentType("application/json")
						.content(objectMapper.writeValueAsString(request)))
				.andExpectAll(status().isCreated()).andReturn();

		String contentContato = resultContatos.getResponse().getContentAsString(StandardCharsets.UTF_8);

		ContatoResponse response = objectMapper.readValue(contentContato, ContatoResponse.class);

		assertNotNull(response.getId());
		assertEquals(response.getNome(), request.getNome());
		assertEquals(response.getTelefone(), request.getTelefone());
	}

}
