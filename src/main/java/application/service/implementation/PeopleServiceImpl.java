package application.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import application.domain.Person;
import application.repository.PeopleRepository;
import application.service.PeopleService;

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
		return null;
	}

	@Override
	public Person setPerson(final Person person) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removePerson(final Long id) {
		// TODO Auto-generated method stub

	}

}
