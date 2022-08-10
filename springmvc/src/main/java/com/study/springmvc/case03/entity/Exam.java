package com.study.springmvc.case03.entity;

import java.util.Arrays;
import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Exam {

   @NotEmpty
   private String studentId;  //學員代號
   @NotEmpty
   private String examId;     //考試代號
   
   @Future
   @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")  //返回日期型態 ->返回給表單
   @DateTimeFormat(pattern="yyyy-MM-dd")                 //設定接收表單傳來的類型
   private Date examDate;     //考試日期
   
   @NotEmpty
   private String[] examSlot;     //考試時段
   @NotNull
   private Boolean examPay;   //繳費狀況: true (已繳費) 、 false (未繳費)
   private String examNote;   //考況備註
public String getStudentId() {
	return studentId;
}
public void setStudentId(String studentId) {
	this.studentId = studentId;
}
public String getExamId() {
	return examId;
}
public void setExamId(String examId) {
	this.examId = examId;
}
public Date getExamDate() {
	return examDate;
}
public void setExamDate(Date examDate) {
	this.examDate = examDate;
}
public String[] getExamSlot() {
	return examSlot;
}
public void setExamSlot(String[] examSlot) {
	this.examSlot = examSlot;
}
public Boolean getExamPay() {
	return examPay;
}
public void setExamPay(Boolean examPay) {
	this.examPay = examPay;
}
public String getExamNote() {
	return examNote;
}
public void setExamNote(String examNote) {
	this.examNote = examNote;
}
@Override
public String toString() {
	return "Exam [studentId=" + studentId + ", examId=" + examId + ", examDate=" + examDate + ", examSlot="
			+ Arrays.toString(examSlot) + ", examPay=" + examPay + ", examNote=" + examNote + "]";
}
   

}
