package application.control;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import application.domain.Person;
import application.service.PeopleService;

@Controller
@RequestMapping("/rest")
public class PeopleController {

	@Autowired
	private PeopleService service;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping(path = "/pessoas", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PersonDto>> getPeople() {
		return new ResponseEntity<>(service.getPeople().stream().map(person -> modelMapper.map(person, PersonDto.class))
				.collect(Collectors.toList()), HttpStatus.OK);
	}

	@GetMapping(path = "/pessoa/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PersonDto> getPerson(@PathVariable final Long id) {
		return new ResponseEntity<>(modelMapper.map(service.getPerson(id), PersonDto.class), HttpStatus.OK);
	}

	@PostMapping(path = "/pessoa/save", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PersonDto>> setPerson(@RequestBody final PersonDto person) {
		service.setPerson(modelMapper.map(person, Person.class));
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(path = "/pessoa/remove/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PersonDto>> removePerson(@PathVariable final Long id) {
		service.removePerson(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
