package com.study.springmvc.case02.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.json.bind.JsonbConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.JsonObject;
import com.study.springmvc.case02.service.LottoService;

@Controller
@RequestMapping("/case02/lotto")
public class LottoController {
	
	@Autowired
	private LottoService lottoService;
	
	// lotto 主畫面
	@RequestMapping({"/"})
	public String index(Model model) {
		model.addAttribute("lottos", lottoService.getLottos());
		model.addAttribute("countNumRlt",lottoService.calNumapearCount());
		return "case02/show_lotto";
	}
	
	// 電腦選號
	@RequestMapping("/add")
	public String add() {
		lottoService.addLotto();
		return "redirect:./";
	}
	
	// 修改紀錄
	@RequestMapping("/update/{index}")  //接收要修改的是哪個位置的樂透組號碼
	public String update(@PathVariable("index") int index) {
		lottoService.updateLotto(index);
		//因路徑關係，這是要往回兩層
		return "redirect:../";
	}
	
	// 刪除紀錄
	@RequestMapping("/delete/{index}")
	public String delete(@PathVariable("index") int index) {
		lottoService.deleteLotto(index);
		//回到根目錄
		return "redirect:../";
	}
	
	
	//回家作業-統計數字出現的次數
	@RequestMapping(value="/countNum")
	@ResponseBody
	public Map<String,Integer> calNumapearCount() {	
		 Map<String,Integer> test=lottoService.calNumapearCount();
		//回傳給jsp
		 return test;
	}
	
	
}