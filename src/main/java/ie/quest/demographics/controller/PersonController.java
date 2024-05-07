package ie.quest.demographics.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ie.quest.demographics.entity.Person;
import ie.quest.demographics.repo.PersonRepository;
import jakarta.transaction.Transactional;

@Controller
@RequestMapping("/demographics")
public class PersonController {
	
	Log log = LogFactory.getLog(PersonController.class);

	@Autowired
	PersonRepository personRepo;

	@GetMapping("/persons")
	public String getAll(Model model) {
		try {
			List<Person> personsList = new ArrayList<Person>();
			personRepo.findAllByCreatedDtmAsc().forEach(personsList::add);

			if(personsList.isEmpty())
				model.addAttribute("message", "No records have been created");

			model.addAttribute("persons", personsList);
		} catch (Exception e) {
			log.error("Exception when calling findAll. " + e.getMessage());
			model.addAttribute("message", e.getMessage());
		}
		return "index";
	}

	@GetMapping("/persons/new")
	public String addPerson(Model model) {
		Person person = new Person();

		model.addAttribute("person", person);
		model.addAttribute("pageTitle", "Create new Person");
		return "person_form";
	}

	@PostMapping(value="/persons/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String addNewPerson(Person person, RedirectAttributes redirectAttributes) {
		try {
			if(person.getDob() == null) {
				redirectAttributes.addFlashAttribute("message", "Date of birth cannot be empty");
				return "redirect:/demographics/persons/new";
			}
				
			//Add created dtm only when new person added, then keep the value
			if(personRepo.findByPpsn(person.getPpsn()).isEmpty())
				person.setCreatedDtm(LocalDateTime.now());

			//Here we modify modified dtm every time a person created or updated
			person.setModifiedDtm(LocalDateTime.now());
			personRepo.save(person);

			redirectAttributes.addFlashAttribute("message", "Person has been saved successfully!");
		} catch (Exception e) {
			log.error("Exception when adding new Person. " + e.getMessage());
			redirectAttributes.addAttribute("message", e.getMessage());
		}

		return "redirect:/demographics/persons";
	}

	@Transactional
	@GetMapping("/persons/delete/{ppsn}")
	public String delete(@PathVariable String ppsn, Model model, RedirectAttributes redirectAttributes) {

		try {
			log.info("Deleting person with PPSN: " + ppsn);
			personRepo.deleteByPpsn(ppsn);
			redirectAttributes.addFlashAttribute("message", "Person with PPSN=" + ppsn + " has been deleted successfully!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			log.error("Exception when deleting person with PPSN: " + ppsn + ". " + e.getMessage());
		}
		return "redirect:/demographics/persons";
	}

	@GetMapping("/persons/update/{ppsn}")
	public String update( @PathVariable String ppsn, Model model, RedirectAttributes redirectAttributes) {

		try {
			Person person = personRepo.findByPpsn(ppsn).get();

			model.addAttribute("person", person);
			model.addAttribute("pageTitle", "Edit Person (PPSN: " + ppsn + ")");
			log.info("Updating person: " + person);

			return "person_form";
		} catch (Exception e) {
			log.error("Exception when updating person with PPSN: " + ppsn + ". " + e.getMessage());
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			return "redirect:/demographics/persons";
		}
	}
}