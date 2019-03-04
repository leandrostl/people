package application.service;

import java.util.List;

import application.domain.Person;

public interface PeopleService {

	List<Person> getPeople();

	Person getPerson(final Long id);

	Person setPerson(final Person person);

	void removePerson(final Long id);
}
