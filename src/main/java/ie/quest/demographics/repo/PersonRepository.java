package ie.quest.demographics.repo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ie.quest.demographics.entity.Person;

@Repository
public interface PersonRepository
extends JpaRepository<Person, String> {

	Optional<Person> findByPpsn(@Param("ppsn") String ppsn);
	
	@Query("SELECT p FROM Person p ORDER BY p.createdDtm DESC")
	List<Person> findAllByCreatedDtmAsc();
	
	List<Person> findAllByDob(LocalDate dob);

	@Query("SELECT p FROM Person p WHERE p.dob >= :dobDateStart AND p.dob <= :dobDateEnd")
	List<Person> findAllByDOBBetween(
			@Param("dobDateStart") LocalDate dobDateStart,
			@Param("dobDateEnd") LocalDate dobDateEnd);

	@Query("SELECT p FROM Person p WHERE p.dob <= :dobDate")
	List<Person> findAllWithDOBDateBefore(
			@Param("dobDate") LocalDate dobDate);
	
	@Query("SELECT p FROM Person p WHERE p.createdDtm <= :creationDateTime ORDER BY p.createdDtm DESC")
	List<Person> findAllWithCreationDateTimeBefore(
			@Param("creationDateTime") LocalDateTime creationDateTime);

	void deleteByPpsn(String ppsn);
}
