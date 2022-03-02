package com.example.bookstore.dto;

import java.util.Objects;

public class BookResponse{
	private Long id;
	private String isbn;
	private String author;
	private String title;
	private int pages;
	private int year;
	private double price;
	private String cover;

	public BookResponse() {
	}

	public Long getId() {
		return id;
	}

	public String getIsbn() {
		return isbn;
	}

	public String getAuthor() {
		return author;
	}

	public String getTitle() {
		return title;
	}

	public int getPages() {
		return pages;
	}

	public int getYear() {
		return year;
	}

	public double getPrice() {
		return price;
	}

	public String getCover() {
		return cover;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BookResponse that = (BookResponse) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}