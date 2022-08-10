package com.study.springmvc.case03.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.study.springmvc.case03.entity.Exam;
import com.study.springmvc.case03.service.ExamService;

@Controller
@RequestMapping("/case03/exam")
public class ExamController {
	@Autowired
	private ExamService examService;
//取得所有報考資訊	
	@GetMapping("/")
	public String index(@ModelAttribute Exam exam, Model model) {
		model.addAttribute("_method", "POST");
		model.addAttribute("exams", examService.query());
		model.addAttribute("examSubjects",examService.queryExamSubjectList());  //加入所有考試項目
		model.addAttribute("examTimes",examService.queryExamTimesList());       //加入所有考試時間
		model.addAttribute("examPays",examService.queryExamPayList());           //加入所有的繳費狀態
		return "case03/exam";
	}
//取得特定報考資訊	
	@GetMapping("/{index}")
	public String get(@PathVariable("index") int index, Model model) {
		Optional<Exam> optExam = examService.get(index);
		if(optExam.isPresent()) {
			model.addAttribute("_method", "PUT");
			model.addAttribute("exams", examService.query());
			model.addAttribute("exam", optExam.get());
			model.addAttribute("examSubjects",examService.queryExamSubjectList());  //加入所有考試項目
			model.addAttribute("examTimes",examService.queryExamTimesList());       //加入所有考試時間
			model.addAttribute("examPays",examService.queryExamPayList());           //加入所有的繳費狀態
			return "case03/exam";
		}
		// 沒找到資料，應該要透過統一錯誤處理機制來進行...
		return "redirect:./";
	}
//新增報考資訊	
	@PostMapping("/")
	public String add(@Valid Exam exam,BindingResult result,Model model) {
		if(result.hasErrors()) {
			model.addAttribute("_method", "POST");
			model.addAttribute("exams", examService.query());
			model.addAttribute("examSubjects",examService.queryExamSubjectList());  //加入所有考試項目
			model.addAttribute("examTimes",examService.queryExamTimesList());       //加入所有考試時間
			model.addAttribute("examPays",examService.queryExamPayList());           //加入所有的繳費狀態
	     return "/case03/exam";	
		}
		examService.add(exam);
		return "redirect:./";
	}
//更新報考資訊	
	@PutMapping("/{index}")
	public String update(@PathVariable("index") int index, Exam exam) {
		exam.setExamNote(
				Optional.ofNullable(exam.getExamNote())
				.map(value->value.replaceAll("<","&lt;"))
				.map(value->value.replaceAll(">","&gt;"))
				.orElse(""));
		examService.update(index, exam);
		return "redirect:./";
	}
	
	//更改備註並濾掉角括號	
	@PutMapping("/{index}/exam_note")
	public String updateExamNote(@PathVariable("index") int index, Exam exam) {
		
		exam.setExamNote(
				Optional.ofNullable(exam.getExamNote())
				.map(value->value.replaceAll("<","&lt;"))
				.map(value->value.replaceAll(">","&gt;"))
				.orElse(""));
		examService.updateExamNote(index,exam.getExamNote());
		return "redirect:../";
	}
	
	@DeleteMapping("/{index}")
	public String delete(@PathVariable("index") int index) {
		examService.delete(index);
		return "redirect:./";
	}
	
	
}