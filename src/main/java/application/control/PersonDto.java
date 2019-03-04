package application.control;

import lombok.Data;

public @Data class PersonDto {
	private Long id;

	private String name;

	private String street;

	private int number;

	private String neighborhood;

	private String city;

	private String state;

	private String cellphone;

	private String phone;
}
