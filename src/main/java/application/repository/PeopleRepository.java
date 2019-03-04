package application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import application.domain.Person;

public interface PeopleRepository extends JpaRepository<Person, Long> {

}
