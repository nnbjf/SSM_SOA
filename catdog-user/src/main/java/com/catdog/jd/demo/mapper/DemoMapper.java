package com.catdog.jd.demo.mapper;

import com.catdog.jd.demo.entity.TestTable;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Map;

@Repository
public interface DemoMapper {


	Map<String,Object> get();

	void add(TestTable testTable);

	void add2(@Param("arrayList") ArrayList<TestTable> arrayList);
}
