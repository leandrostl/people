package application.control;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.Messages;
import application.domain.Person;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class PeopleControllerTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void testGetPeople() throws Exception {
		mvc.perform(get("/rest/pessoas")).andExpect(status().isOk()).andExpect(jsonPath("$").isEmpty());
	}

	@Test
	public void testSetPerson() throws Exception {
		final String person = createPerson();
		mvc.perform(postPerson(person)).andExpect(status().isOk()).andDo(result -> mvc.perform(get("/rest/pessoas"))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].name").value(equalTo("P1"))));
	}

	@Test
	public void testGetPerson() throws Exception {
		final Long id = postPersonAndGetId();
		mvc.perform(get("/rest/pessoa/" + id)).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(equalTo("P1")));
	}

	@Test
	public void testGetPerson_nonExistent() throws Exception {
		final String message = mvc.perform(get("/rest/pessoa/1")).andExpect(status().isBadRequest()).andReturn()
				.getResolvedException().getMessage();
		assertThat(message, equalTo(Messages.getString("error_not_found", 1)));
	}

	@Test
	public void testRemovePerson() throws Exception {
		final Long id = postPersonAndGetId();
		mvc.perform(delete("/rest/pessoa/remove/" + id)).andExpect(status().isOk());
		mvc.perform(get("/rest/pessoas")).andExpect(status().isOk()).andExpect(jsonPath("$").isEmpty());
	}

	@Test
	public void testRemovePerson_nonExistent() throws Exception {
		final String message = mvc.perform(delete("/rest/pessoa/remove/1")).andExpect(status().isBadRequest())
				.andReturn().getResolvedException().getMessage();
		assertThat(message, equalTo(Messages.getString("error_not_found", 1)));
	}

	private Long postPersonAndGetId() throws JsonProcessingException, Exception, IOException, JsonParseException,
			JsonMappingException, UnsupportedEncodingException {

		mvc.perform(postPerson(createPerson()));
		final String result = mvc.perform(get("/rest/pessoas")).andDo(print()).andReturn().getResponse()
				.getContentAsString();
		return new ObjectMapper().readValue(result, Person[].class)[0].getId();
	}

	private MockHttpServletRequestBuilder postPerson(final String person) {
		return post("/rest/pessoa/save").contentType(MediaType.APPLICATION_JSON_VALUE).content(person);
	}

	private String createPerson() throws JsonProcessingException {
		return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(
				Person.builder().id(0L).name("P1").city("Belo Horizonte").state("Minas Gerais").build());
	}
}
