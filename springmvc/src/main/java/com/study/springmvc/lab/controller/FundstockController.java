package com.study.springmvc.lab.controller;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

import java.util.Comparator;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.springmvc.lab.entity.Fund;
import com.study.springmvc.lab.entity.Fundstock;
import com.study.springmvc.lab.repository.FundDao;
import com.study.springmvc.lab.repository.FundstockDao;

@Controller
@RequestMapping("/lab/fundstock")
public class FundstockController {
	@Autowired
	private FundstockDao fundstockDao;
	
	@Autowired
	private FundDao fundDao;
	
	private int pageNumber = -1;
	
	@GetMapping("/")
	public String index(@ModelAttribute Fundstock fundstock, Model model) {
		return "redirect:./page/" + pageNumber;
	}
	
	@GetMapping("/page/{pageNumber}")
	public String page(@PathVariable("pageNumber") int pageNumber, @ModelAttribute Fundstock fundstock, Model model) {
		this.pageNumber = pageNumber;
		int offset = (pageNumber-1) * FundstockDao.LIMIT;
		List<Fundstock> fundstocks =  fundstockDao.queryPage(offset);
		List<Fund> funds = fundDao.queryAll();
		int pageTotalCount = 
			(fundstockDao.count() % FundstockDao.LIMIT)>0?
					fundstockDao.count() / FundstockDao.LIMIT+1:fundstockDao.count() / FundstockDao.LIMIT;
		model.addAttribute("_method", "POST");
		model.addAttribute("fundstocks", fundstocks);
		model.addAttribute("funds", funds);
		model.addAttribute("pageTotalCount", pageTotalCount);
		model.addAttribute("groupMap", getGroupMap());
		return "lab/fundstock";
	}
//取得單筆股票資訊	
	@GetMapping("/{sid}")
	//@ResponseBody
	public String get(@PathVariable("sid") Integer sid,Model model) {
		int offset = (pageNumber-1) * FundstockDao.LIMIT;
		List<Fundstock> fundstocks =  fundstockDao.queryPage(offset);
		List<Fund> funds = fundDao.queryAll();
		int pageTotalCount = fundstockDao.count() / FundstockDao.LIMIT;
		model.addAttribute("_method", "PUT");
		model.addAttribute("fundstock", fundstockDao.get(sid));
		model.addAttribute("fundstocks", fundstocks);
		model.addAttribute("funds", funds);
		model.addAttribute("pageTotalCount", pageTotalCount);
		model.addAttribute("groupMap", getGroupMap());
		return "lab/fundstock";
	}
	//新增股票資訊
	@PostMapping("/addFundstock")
	public String addFundstock(Fundstock fundstock) {
		fundstockDao.add(fundstock);
		return "redirect:./";
	}
	//更新股票資訊
	@PutMapping("/{sid}")
		public String update(@PathVariable("sid")Integer sid,Fundstock fundstock) {
			fundstockDao.update(fundstock);
			return "redirect:./";
		}
	//刪除股票資訊
	@DeleteMapping("/{sid}")
	public String delete(@PathVariable("sid")Integer sid) {
		fundstockDao.delete(sid);
		return "redirect:./";
	}
	
	//依股票代號分類並加總股數再排序，給圖表用
	private Map<String, Integer> getGroupMap() {
		// select s.symbol, sum(s.share) as share
		// from fundstock s
		// group by s.symbol
		List<Fundstock> fundstocks = fundstockDao.queryAll();
		Map<String, Integer> Ordertemp=fundstocks.stream()
						 .collect(groupingBy(Fundstock::getSymbol, 
											 summingInt(Fundstock::getShare)));
		return Ordertemp.entrySet().stream()
				.sorted(Entry.comparingByKey(Comparator.naturalOrder()))
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
	}
}