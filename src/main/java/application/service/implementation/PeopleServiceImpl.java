package application.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import application.Messages;
import application.domain.Person;
import application.repository.PeopleRepository;
import application.service.PeopleService;
import application.service.exception.PersonNotFoundException;

@Service
public class PeopleServiceImpl implements PeopleService {

	@Autowired
	private PeopleRepository repository;

	@Override
	public List<Person> getPeople() {
		return repository.findAll();
	}

	@Override
	public Person getPerson(final Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new PersonNotFoundException(Messages.getString("error_not_found", id)));
	}

	@Override
	public Person setPerson(final Person person) {
		return repository.saveAndFlush(person);
	}

	@Override
	public void removePerson(final Long id) {
		repository.deleteById(id);
	}

}
