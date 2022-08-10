package com.study.springmvc.case03.entity;

public class ExamPay {
 private String Pay_id;
 private String Pay_name;
 
 public ExamPay () {
	 
 }

public ExamPay(String id, String name) {
	this.Pay_id = id;
	this.Pay_name = name;
}

public String getPay_id() {
	return Pay_id;
}

public void setPay_id(String pay_id) {
	Pay_id = pay_id;
}

public String getPay_name() {
	return Pay_name;
}

public void setPay_name(String pay_name) {
	Pay_name = pay_name;
}

@Override
public String toString() {
	return "ExamPay [Pay_id=" + Pay_id + ", Pay_name=" + Pay_name + "]";
}


}
