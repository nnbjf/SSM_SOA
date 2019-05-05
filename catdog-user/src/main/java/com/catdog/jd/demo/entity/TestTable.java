package com.catdog.jd.demo.entity;


import java.time.LocalDate;
import java.util.Date;

public class TestTable {
	private Integer id;
	private String name;
	private String pwd;
	private Date date;

	public Integer getId() {
		return id;
	}

	public TestTable setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public TestTable setName(String name) {
		this.name = name;
		return this;
	}

	public String getPwd() {
		return pwd;
	}

	public TestTable setPwd(String pwd) {
		this.pwd = pwd;
		return this;
	}

	public Date getDate() {
		return date;
	}

	public TestTable setDate(Date date) {
		this.date = date;
		return this;
	}

	@Override
	public String toString() {
		return "TestTable{" +
				"id=" + id +
				", name='" + name + '\'' +
				", pwd='" + pwd + '\'' +
				", date=" + date +
				'}';
	}
}
