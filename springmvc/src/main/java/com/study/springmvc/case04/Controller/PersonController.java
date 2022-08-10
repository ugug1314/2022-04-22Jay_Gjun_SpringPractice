package com.study.springmvc.case04.Controller;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.study.springmvc.case04.entity.Person;

@Controller
@RequestMapping("/case04/person")
public class PersonController {
	private List<Person> people = new CopyOnWriteArrayList<>(); // Person 資料的紀錄集合
	
	@GetMapping("/")
	public String index(@ModelAttribute Person person, Model model) {
		model.addAttribute("_method", "POST");
		model.addAttribute("people", people);
		return "case04/person";
	}
	
	@GetMapping("/{index}")
	public String get(@PathVariable("index") int index, Model model) {
		// @PathVariable("index") int index 會自動將 index 參數傳遞給 view
		model.addAttribute("_method", "PUT");
		model.addAttribute("people", people);
		model.addAttribute("person", people.get(index));
		return "case04/person";
	}
	
	@PostMapping("/")
	public String add(@Valid Person person, BindingResult result, Model model) {
		if(result.hasErrors()) { // 是否有錯誤發生
			model.addAttribute("_method", "POST");
			model.addAttribute("people", people);
			System.out.println("有錯誤發生");
			return "case04/person";
		}
		people.add(person);
		return "redirect:./";
	}
	
	@PutMapping("/{index}")
	public String update(@PathVariable("index") int index, @Valid Person person, BindingResult result, Model model) {
		if(result.hasErrors()) { // 是否有錯誤發生
			model.addAttribute("_method", "PUT");
			model.addAttribute("people", people);
			System.out.println("有錯誤發生");
			return "case04/person";
		}
		people.set(index, person);
		return "redirect:./";
	}
	@DeleteMapping("/{index}")
	public String delete(@PathVariable("index") int index, Model model) {
		model.addAttribute("_method", "PUT");
		model.addAttribute("people", people);	
		people.remove(index);
		return "redirect:./";
	
	}
	
}