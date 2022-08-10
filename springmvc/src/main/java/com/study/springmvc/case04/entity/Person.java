package com.study.springmvc.case04.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Person {
 @Size(min=2,max=50,message="{person.name.size}")
  private String name;   //姓名
  
@NotNull(message="{person.age.empty}")
@Range(min=0,max=150,message="{person.age.range}")
  private Integer age;  //年齡
  
@NotNull(message="{person.member.empty}") 
  private Boolean member;  //是否為會員true false

@NotNull(message="{person.birth.empty}") 
@Past(message="{person.birth.past}")
  @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")  //返回日期型態 ->返回給表單
  @DateTimeFormat(pattern="yyyy-MM-dd")                 //設定接收表單傳來的類型
  private Date birth;    //生日

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public Integer getAge() {
	return age;
}

public void setAge(Integer age) {
	this.age = age;
}

public Boolean getMember() {
	return member;
}

public void setMember(Boolean member) {
	this.member = member;
}

public Date getBirth() {
	return birth;
}

public void setBirth(Date birth) {
	this.birth = birth;
}

@Override
public String toString() {
	return "Person [name=" + name + ", age=" + age + ", member=" + member + ", birth=" + birth + "]";
}

}
