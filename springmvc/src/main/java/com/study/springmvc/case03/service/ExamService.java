package com.study.springmvc.case03.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.stereotype.Service;

import com.study.springmvc.case03.entity.Exam;
import com.study.springmvc.case03.entity.ExamPay;
import com.study.springmvc.case03.entity.ExamSubject;
import com.study.springmvc.case03.entity.ExamTime;

@Service
public class ExamService {
	// CopyOnWriteArrayList 是執行緒安全的集合-適合多執行緒操作
	//
	//
	//
	private List<Exam> exams = new CopyOnWriteArrayList<>(); // 註冊考試的紀錄集合
	private List<ExamSubject> examSubjects = new CopyOnWriteArrayList<>(); // 註冊考試的科目集合
	private List<ExamTime> examTimes = new CopyOnWriteArrayList<>(); // 註冊考試的時段集合
	private List<ExamPay> examPays = new CopyOnWriteArrayList<>(); // 註冊考試的繳費狀態集合

	// 新增考試項目
	{
		examSubjects.add(new ExamSubject("808", "JavaSE 8 OCP I"));
		examSubjects.add(new ExamSubject("809", "JavaSE 8 OCP II"));
		examSubjects.add(new ExamSubject("819", "JavaSE 11 OCP "));
		examSubjects.add(new ExamSubject("900", "JavaEE 7 OCP"));
	}
	// 新增考試時段
	{
		examTimes.add(new ExamTime("A", "上午(A)"));
		examTimes.add(new ExamTime("B", "下午(B)"));
		examTimes.add(new ExamTime("C", "晚上(C)"));
	}
	// 新增繳費狀態
	{
		examPays.add(new ExamPay("true", "已繳"));
		examPays.add(new ExamPay("false", "未繳"));

	}

	// 查詢所有 exam subject
	public List<ExamSubject> queryExamSubjectList() {
		return examSubjects;
	}

	// 查詢所有 examTime
	public List<ExamTime> queryExamTimesList() {
		return examTimes;
	}

	// 查詢所有 examPay
	public List<ExamPay> queryExamPayList() {
		return examPays;
	}

	// 首頁(查詢多筆)
	public List<Exam> query() {
		return exams;
	}

	// 查詢單筆
	public Optional<Exam> get(int index) {
		if (index < 0 || exams.size() == 0 || index >= exams.size()) {
			return Optional.ofNullable(null);
		}
		return Optional.ofNullable(exams.get(index));
	}

	// 新增
	public synchronized Boolean add(Exam exam) {
		int previousSize = exams.size();
		exams.add(exam);
		int nextSize = exams.size();
		return nextSize > previousSize;

	}

	// 修改
	public synchronized Boolean update(int index, Exam exam) {
		if (index < 0 || exams.size() == 0 || index >= exams.size()) {
			return false;
		}
		exams.set(index, exam);
		return true;
	}

	// 修改ExamNote單位欄位資料
	public synchronized Boolean updateExamNote(int index, String examNote) {
		if (index < 0 || exams.size() == 0 || index >= exams.size()) {
			return false;
		}
		Exam exam = exams.get(index);
		// 重新設定備註值
		exam.setExamNote(examNote);
		// exams.set(index, exam);
		return true;
	}

	// 刪除
	public synchronized Boolean delete(int index) {
		if (index < 0 || exams.size() == 0 || index >= exams.size()) {
			return false;
		}
		exams.remove(index);
		return true;
	}
}
