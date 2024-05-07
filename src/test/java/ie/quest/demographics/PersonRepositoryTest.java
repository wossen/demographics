package ie.quest.demographics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import ie.quest.demographics.entity.Person;
import ie.quest.demographics.repo.PersonRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.assertj.core.api.Assertions.offset;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PersonRepositoryTest {

	/**
	 * @DataJpaTest
	 * 1. It scans the `@Entity` classes and Spring Data JPA repositories.
	 * 2. Set the `spring.jpa.show-sql` property to true and enable the SQL queries logging.
	 * 3. Default, JPA test data are transactional and roll back at the end of each test;
	 * it means we do not need to clean up saved or modified table data after each test.
	 * 4. Replace the application DataSource, run and configure the embedded database on classpath.
	 */
	@Autowired
	private PersonRepository personRepo;

	@Test
	public void testSave() {

		Person zachary = new Person();
		zachary.setPpsn("1234567A");
		zachary.setName("Zachary Abate");
		zachary.setDob(LocalDate.of(2013, 9, 29));
		zachary.setMobilePhone("+353 862245465");
		zachary.setCreatedDtm(LocalDateTime.now());
		zachary.setModifiedDtm(LocalDateTime.now());

		//Save to database
		personRepo.save(zachary);

		String ppsn = zachary.getPpsn();

		//find by ppsn
		Optional<Person> savedPerson = personRepo.findByPpsn(ppsn);
		assertEquals(ppsn, savedPerson.get().getPpsn());
		assertEquals("Zachary Abate", savedPerson.get().getName());
		assertEquals(LocalDate.of(2013, 9, 29), savedPerson.get().getDob());
	}

	@Test
	public void testFindByDob() {
		Person zachary = new Person();
		zachary.setPpsn("1234567A");
		zachary.setName("Zachary Abate");
		zachary.setDob(
				LocalDate.of(2013, 9, 29)
				);
		zachary.setMobilePhone("+353 862245465");
		zachary.setCreatedDtm(LocalDateTime.now());
		zachary.setModifiedDtm(LocalDateTime.now());

		//Save to database
		personRepo.save(zachary);

		LocalDate dob = zachary.getDob();

		//find by dob, returns list
		List<Person> savedPerson = personRepo.findAllByDob(dob);
		assertEquals(LocalDate.of(2013, 9, 29), savedPerson.get(0).getDob());
	}

	@Test
	public void testUpdate() {

		Person zachary = new Person();
		zachary.setPpsn("1234567A");
		zachary.setName("Zachary Abate");
		zachary.setDob(LocalDate.of(2013, 9, 29));
		zachary.setMobilePhone("+353 862245465");
		zachary.setCreatedDtm(LocalDateTime.now());
		zachary.setModifiedDtm(LocalDateTime.now());

		// Insert to database
		personRepo.save(zachary);

		zachary.setMobilePhone("+353 861111111");

		// update database
		personRepo.save(zachary);

		Optional<Person> savedPerson = personRepo.findByPpsn("1234567A");

		assertNotNull(savedPerson.get().getPpsn());
		assertEquals("Zachary Abate", savedPerson.get().getName());
		assertEquals(LocalDate.of(2013, 9, 29), savedPerson.get().getDob());
		assertEquals("+353 861111111", savedPerson.get().getMobilePhone());

	}


	@Test
	public void testFindAll() {

		Person zachary = new Person();
		zachary.setPpsn("1234567A");
		zachary.setName("Zachary Abate");
		zachary.setDob(LocalDate.of(2013, 9, 29));
		zachary.setMobilePhone("+353 862245465");
		zachary.setCreatedDtm(LocalDateTime.now());
		zachary.setModifiedDtm(LocalDateTime.now());

		Person paul = new Person();
		paul.setPpsn("345567W");
		paul.setName("Paul Miller");
		paul.setDob(LocalDate.of(1975, 4, 21));
		paul.setMobilePhone("+353 835562134");
		paul.setCreatedDtm(LocalDateTime.now());
		paul.setModifiedDtm(LocalDateTime.now());

		Person mary = new Person();
		mary.setPpsn("4556477T");
		mary.setName("Mary Byrne");
		mary.setDob(LocalDate.of(1992, 5, 14));
		mary.setMobilePhone("+353 898882177");
		mary.setCreatedDtm(LocalDateTime.now());
		mary.setModifiedDtm(LocalDateTime.now());

		personRepo.saveAll(List.of(zachary, paul, mary));

		List<Person> result = personRepo.findAllByCreatedDtmAsc();
		assertEquals(3, result.size());

	}

	@Test
	public void testFindAllWithDOBBeetweenDates() {

		Person zachary = new Person();
		zachary.setPpsn("1234567A");
		zachary.setName("Zachary Abate");
		zachary.setDob(LocalDate.of(2013, 9, 29));
		zachary.setMobilePhone("+353 862245465");
		zachary.setCreatedDtm(LocalDateTime.now());
		zachary.setModifiedDtm(LocalDateTime.now());

		Person paul = new Person();
		paul.setPpsn("345567W");
		paul.setName("Paul Miller");
		paul.setDob(LocalDate.of(1975, 4, 21));
		paul.setMobilePhone("+353 835562134");
		paul.setCreatedDtm(LocalDateTime.now());
		paul.setModifiedDtm(LocalDateTime.now());

		Person mary = new Person();
		mary.setPpsn("4556477T");
		mary.setName("Mary Byrne");
		mary.setDob(LocalDate.of(1992, 5, 14));
		mary.setMobilePhone("+353 898882177");
		mary.setCreatedDtm(LocalDateTime.now());
		mary.setModifiedDtm(LocalDateTime.now());

		personRepo.saveAll(List.of(zachary, paul, mary));


		List<Person> result = personRepo.findAllByDOBBetween(LocalDate.of(1980, 1, 1), LocalDate.of(2000, 1, 1));
		assertEquals(1, result.size());
		assertNotNull(result.get(0));
		Person person = result.get(0);

		assertEquals(LocalDate.of(1992, 5, 14), person.getDob());

	}

	@Test
	public void testDeleteByPPSN() {

		Person zachary = new Person();
		zachary.setPpsn("1234567A");
		zachary.setName("Zachary Abate");
		zachary.setDob(LocalDate.of(2013, 9, 29));
		zachary.setMobilePhone("+353 862245465");
		zachary.setCreatedDtm(LocalDateTime.now());
		zachary.setModifiedDtm(LocalDateTime.now());

		String ppsn = zachary.getPpsn();
		personRepo.deleteByPpsn(ppsn);

		Optional<Person> result = personRepo.findByPpsn(ppsn);
		assertTrue(result.isEmpty());
	}

}