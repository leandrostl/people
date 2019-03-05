package application.control;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import application.Messages;
import application.domain.Person;
import application.service.PeopleService;

@CrossOrigin
@Controller
@RequestMapping("/rest")
public class PeopleController {

	@Autowired
	private PeopleService service;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping(path = "/pessoas", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<PersonDto> getPeople() {
		return service.getPeople().stream().map(person -> modelMapper.map(person, PersonDto.class))
				.collect(Collectors.toList());
	}

	@GetMapping(path = "/pessoa/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody PersonDto getPerson(@PathVariable final Long id) {
		return modelMapper.map(service.getPerson(id), PersonDto.class);
	}

	@PostMapping(path = "/pessoa/save", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResultDto setPerson(@RequestBody final PersonDto person) {
		service.setPerson(modelMapper.map(person, Person.class));
		return new ResultDto(Messages.getString("success"));
	}

	@DeleteMapping(path = "/pessoa/remove/{id}")
	public @ResponseBody ResultDto removePerson(@PathVariable final Long id) {
		service.removePerson(id);
		return new ResultDto(Messages.getString("success"));
	}
}
