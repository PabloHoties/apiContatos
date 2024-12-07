package br.com.cotiinformatica;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.Random;
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
	static UUID idCategoria;

	@Test
	@Order(1)
	public void criarContatoComSucessoTest() throws Exception {

		Faker faker = new Faker(new Locale("pt", "BR"));

		Random random = new Random();

		ContatoRequest request = new ContatoRequest();
		request.setNome(faker.name().fullName());
		request.setTelefone(faker.regexify("\\(\\d{2}\\) \\d{5}-\\d{4}"));

		MvcResult resultCategorias = mockMvc.perform(get("/api/categorias").contentType("application/json"))
				.andExpectAll(status().isOk()).andReturn();

		String contentCategoria = resultCategorias.getResponse().getContentAsString(StandardCharsets.UTF_8);

		List<Categoria> categorias = objectMapper.readValue(contentCategoria, new TypeReference<List<Categoria>>() {
		});

		request.setCategoriaId(categorias.get(random.nextInt(categorias.size())).getId());

		MvcResult resultContatos = mockMvc
				.perform(post("/api/contatos").contentType("application/json")
						.content(objectMapper.writeValueAsString(request)))
				.andExpectAll(status().isCreated()).andReturn();

		String contentContato = resultContatos.getResponse().getContentAsString(StandardCharsets.UTF_8);

		ContatoResponse response = objectMapper.readValue(contentContato, ContatoResponse.class);

		assertNotNull(response.getId());
		assertEquals(response.getNome(), request.getNome());
		assertEquals(response.getTelefone(), request.getTelefone());

		idContato = response.getId();
		idCategoria = categorias.get(random.nextInt(4)).getId();
	}

	@Test
	@Order(2)
	public void criarContatoComIdDaCategoriaInvalidoTest() throws Exception {

		Faker faker = new Faker(new Locale("pt", "BR"));

		ContatoRequest request = new ContatoRequest();
		request.setNome(faker.name().fullName());
		request.setTelefone(faker.regexify("\\(\\d{2}\\) \\d{5}-\\d{4}"));
		request.setCategoriaId(UUID.randomUUID());

		MvcResult result = mockMvc
				.perform(post("/api/contatos").contentType("application/json")
						.content(objectMapper.writeValueAsString(request)))
				.andExpectAll(status().isNotFound()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		assertTrue(content.contains("Categoria não encontrada. Verifique o ID informado."));
	}

	@Test
	@Order(3)
	public void criarContatoComDadosInvalidosTest() throws Exception {

		ContatoRequest request = new ContatoRequest();
		request.setNome("");
		request.setTelefone("");
		request.setCategoriaId(null);

		MvcResult result = mockMvc
				.perform(post("/api/contatos").contentType("application/json")
						.content(objectMapper.writeValueAsString(request)))
				.andExpectAll(status().isBadRequest()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		assertTrue(content.contains("categoriaId: Por favor, informe o id da categoria."));
		assertTrue(content.contains("nome: Por favor, informe o nome do contato."));
		assertTrue(content.contains("nome: Por favor, informe um nome de 8 a 100 caracteres."));
		assertTrue(content.contains("telefone: Por favor, informe o telefone do contato."));
		assertTrue(content.contains("telefone: Por favor, informe o telefone no formato: '(99) 99999-9999'."));
	}

	@Test
	@Order(4)
	public void atualizarContatoComSucessoTest() throws Exception {

		Faker faker = new Faker(new Locale("pt", "BR"));

		ContatoRequest request = new ContatoRequest();
		request.setNome(faker.name().fullName());
		request.setTelefone(faker.regexify("\\(\\d{2}\\) \\d{5}-\\d{4}"));
		request.setCategoriaId(idCategoria);

		MvcResult result = mockMvc.perform(put("/api/contatos/" + idContato).contentType("application/json")
				.content(objectMapper.writeValueAsString(request))).andExpectAll(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		ContatoResponse response = objectMapper.readValue(content, ContatoResponse.class);

		assertNotNull(response.getId());
		assertEquals(response.getNome(), request.getNome());
		assertEquals(response.getTelefone(), request.getTelefone());
		assertEquals(response.getCategoria().getId(), idCategoria);
	}

	@Test
	@Order(5)
	public void atualizarContatoComIdDoContatoInvalidoTest() throws Exception {

		Faker faker = new Faker(new Locale("pt", "BR"));

		ContatoRequest request = new ContatoRequest();
		request.setNome(faker.name().fullName());
		request.setTelefone(faker.regexify("\\(\\d{2}\\) \\d{5}-\\d{4}"));
		request.setCategoriaId(idCategoria);

		MvcResult result = mockMvc
				.perform(put("/api/contatos/" + UUID.randomUUID()).contentType("application/json")
						.content(objectMapper.writeValueAsString(request)))
				.andExpectAll(status().isNotFound()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		assertTrue(content.contains("Contato não encontrado. Verifique o ID informado."));
	}

	@Test
	@Order(6)
	public void atualizarContatoComIdDaCategoriaInvalidoTest() throws Exception {

		Faker faker = new Faker(new Locale("pt", "BR"));

		ContatoRequest request = new ContatoRequest();
		request.setNome(faker.name().fullName());
		request.setTelefone(faker.regexify("\\(\\d{2}\\) \\d{5}-\\d{4}"));
		request.setCategoriaId(UUID.randomUUID());

		MvcResult result = mockMvc
				.perform(put("/api/contatos/" + idContato).contentType("application/json")
						.content(objectMapper.writeValueAsString(request)))
				.andExpectAll(status().isNotFound()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		assertTrue(content.contains("Categoria não encontrada. Verifique o ID informado."));
	}

	@Test
	@Order(7)
	public void atualizarContatoComDadosInvalidosTest() throws Exception {

		ContatoRequest request = new ContatoRequest();
		request.setNome("");
		request.setTelefone("");
		request.setCategoriaId(null);

		MvcResult result = mockMvc
				.perform(put("/api/contatos/" + idContato).contentType("application/json")
						.content(objectMapper.writeValueAsString(request)))
				.andExpectAll(status().isBadRequest()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		assertTrue(content.contains("categoriaId: Por favor, informe o id da categoria."));
		assertTrue(content.contains("nome: Por favor, informe o nome do contato."));
		assertTrue(content.contains("nome: Por favor, informe um nome de 8 a 100 caracteres."));
		assertTrue(content.contains("telefone: Por favor, informe o telefone do contato."));
		assertTrue(content.contains("telefone: Por favor, informe o telefone no formato: '(99) 99999-9999'."));
	}

	@Test
	@Order(8)
	public void obterContatoComSucessoTest() throws Exception {

		MvcResult result = mockMvc.perform(get("/api/contatos/" + idContato).contentType("application/json"))
				.andExpectAll(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		assertTrue(content.contains(idContato.toString()));
	}

	@Test
	@Order(9)
	public void obterContatoComIdInvalidoTest() throws Exception {

		MvcResult result = mockMvc.perform(get("/api/contatos/" + UUID.randomUUID()).contentType("application/json"))
				.andExpectAll(status().isNotFound()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		assertTrue(content.contains("Contato não encontrado. Verifique o ID informado."));
	}

	@Test
	@Order(10)
	public void consultarContatosComSucessoTest() throws Exception {

		MvcResult result = mockMvc.perform(get("/api/contatos").contentType("application/json"))
				.andExpectAll(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		assertTrue(content.contains(idContato.toString()));
	}

	@Test
	@Order(11)
	public void excluirContatoComSucessoTest() throws Exception {

		mockMvc.perform(delete("/api/contatos/" + idContato).contentType("application/json"))
				.andExpectAll(status().isNoContent()).andReturn();
	}

	@Test
	@Order(12)
	public void excluirContatoComIdInvalidoTest() throws Exception {

		MvcResult result = mockMvc.perform(delete("/api/contatos/" + UUID.randomUUID()).contentType("application/json"))
				.andExpectAll(status().isNotFound()).andReturn();
		
		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		assertTrue(content.contains("Contato não encontrado. Verifique o ID informado."));
	}
}
