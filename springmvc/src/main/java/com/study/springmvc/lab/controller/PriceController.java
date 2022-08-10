package com.study.springmvc.lab.controller;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

@RestController
@RequestMapping("/lab/price")
public class PriceController {	
	// 範例：symbol = ^TWII、2330.TW ,加上.+是因為symbol中可能會有或沒有.結尾的
	@GetMapping("/histquotes/{symbol:.+}")
	public List<HistoricalQuote> queryHistoricalQuotes(@PathVariable("symbol") String symbol) {		
		Calendar from = Calendar.getInstance();  //開始日期
		Calendar to = Calendar.getInstance();    //結束日期
		from.add(Calendar.MONTH, -1); // 從1年前
		List<HistoricalQuote> googleHistQuotes = null;
		try {
			//取得股票資料
			System.out.println("123");		
			Stock google = YahooFinance.get(symbol);
			googleHistQuotes = google.getHistory(from, to, Interval.DAILY);
			System.out.println(googleHistQuotes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return googleHistQuotes;
	}
}