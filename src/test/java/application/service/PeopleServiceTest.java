package application.service;

import static java.util.Optional.of;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import application.Messages;
import application.domain.Person;
import application.repository.PeopleRepository;
import application.service.exception.PersonNotFoundException;
import application.service.implementation.PeopleServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
public class PeopleServiceTest {

	@Mock
	private PeopleRepository repository;

	@InjectMocks
	private final PeopleService service = new PeopleServiceImpl();

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void testGetPeople() throws Exception {
		final List<Person> people = Arrays.asList(
				Person.builder().id(0L).name("P1").city("Belo Horizonte").state("Minas Gerais").build(),
				Person.builder().id(1L).name("P2").city("Rio de Janeiro").state("Rio de Janeiro").build(),
				Person.builder().id(2L).name("P3").city("São Paulo").state("São Paulo").build(),
				Person.builder().id(3L).name("P4").city("Salvador").state("Bahia").build());
		Mockito.when(repository.findAll()).thenReturn(people);
		org.junit.Assert.assertThat(service.getPeople(), equalTo(people));
	}

	@Test
	public void testGetPerson() throws Exception {
		final Person person = Person.builder().id(0L).name("P1").city("Belo Horizonte").state("Minas Gerais").build();
		Mockito.when(repository.findById(0L)).thenReturn(of(person));
		assertThat(service.getPerson(0L), equalTo(person));
	}

	@Test
	public void testGetPerson_nonExistent() throws Exception {
		Mockito.when(repository.findById(0L)).thenReturn(Optional.empty());
		expectedEx.expect(PersonNotFoundException.class);
		expectedEx.expectMessage(Messages.getString("error_not_found", 0L));
		service.getPerson(0L);
	}

	@Test
	public void testSetPerson() throws Exception {
		final Person person = Person.builder().name("P1").city("Belo Horizonte").state("Minas Gerais").build();
		Mockito.when(repository.saveAndFlush(person)).thenReturn(person);
		assertThat(service.setPerson(person), equalTo(person));
	}

	@Test
	public void testSetPerson_alreadyExists() throws Exception {
		final Person person = Person.builder().id(0L).name("P1").city("Belo Horizonte").state("Minas Gerais").build();
		Mockito.when(repository.saveAndFlush(person)).thenReturn(person);
		assertThat(service.setPerson(person), equalTo(person));
	}

	@Test(expected = Test.None.class)
	public void testRemovePerson() throws Exception {
		service.removePerson(0L);
	}

}
