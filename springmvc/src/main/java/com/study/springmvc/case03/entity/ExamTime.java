package com.study.springmvc.case03.entity;

public class ExamTime {
 private String T_id;
 private String T_name;
 
 public ExamTime () {
	 
 }

public ExamTime(String id, String name) {
	this.T_id = id;
	this.T_name = name;
}

public String getT_id() {
	return T_id;
}

public void setT_id(String t_id) {
	T_id = t_id;
}

public String getT_name() {
	return T_name;
}

public void setT_name(String t_name) {
	T_name = t_name;
}

@Override
public String toString() {
	return "ExamTime [T_id=" + T_id + ", T_name=" + T_name + "]";
}


}
