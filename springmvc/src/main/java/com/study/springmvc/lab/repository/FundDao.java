package com.study.springmvc.lab.repository;

import java.util.List;

import com.study.springmvc.lab.entity.Fund;

public interface FundDao {
	// 每頁5筆
	int LIMIT = 5;

	// 全部查詢
	List<Fund> queryAll();

	// 分頁查詢
	List<Fund> queryPage(int offset);

	// 取得單筆
	Fund get(Integer fid);
	
	// 查詢所有筆數
	int count();
		
	// 新增
	int add(Fund fund);

	// 修改
	int update(Fund fund);

	// 刪除
	int delete(Integer fid);
}