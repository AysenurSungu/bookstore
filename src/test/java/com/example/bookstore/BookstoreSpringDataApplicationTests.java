package com.example.bookstore;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import com.example.bookstore.dto.BookRequest;
import com.example.bookstore.dto.BookResponse;
import com.example.bookstore.service.business.StandardBookCatalogService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class BookstoreSpringDataApplicationTests {

	@Autowired
	MockMvc mockMvc;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	StandardBookCatalogService standardBookCatalogService;

	@DisplayName("get request with identity should return status ok")
	@ParameterizedTest
	@CsvFileSource(resources = "book.csv")
	void getBookByIsbnShoudlReturnOk(String isbn,
									 String author,
									 String title,
									 double price) throws Throwable {

		var bookResponse = new BookResponse();
		bookResponse.setIsbn(isbn);
		bookResponse.setAuthor(author);
		bookResponse.setTitle(title);
		bookResponse.setPrice(price);
		Mockito.when(standardBookCatalogService.findBookByIsbn(isbn))
				.thenReturn(bookResponse);

		mockMvc.perform(get("/books/"+isbn).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.isbn",is(isbn)))
				.andExpect(jsonPath("$.author",is(author)))
				.andExpect(jsonPath("$.title",is(title)))
				.andExpect(jsonPath("$.price",is(price)));

	}
	@ParameterizedTest
	@CsvFileSource(resources = "book.csv")
	void addBookShoudlReturnOk(String isbn,
							   String author,
							   String title,
							   double price) throws Throwable {

		var request = new BookRequest();
		request.setIsbn(isbn);
		request.setAuthor(author);
		request.setTitle(title);
		request.setPrice(price);
		var response = modelMapper.map(request,BookResponse.class);
		Mockito.when(standardBookCatalogService.addBook(request))
				.thenReturn(response);

		mockMvc.perform(post("/books").accept(MediaType.APPLICATION_JSON)
						 			  .contentType(MediaType.APPLICATION_JSON)
									  .content(objectMapper.writeValueAsString(request))
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.isbn",is(isbn)))
				.andExpect(jsonPath("$.author",is(author)))
				.andExpect(jsonPath("$.title",is(title)))
				.andExpect(jsonPath("$.price",is(price)));

	}

	@ParameterizedTest
	@CsvFileSource(resources = "book.csv")
	void removeBookByIsbnShoudlReturnOk(String isbn,
										String author,
										String title,
										double price) throws Throwable {

		var bookResponse = new BookResponse();
		bookResponse.setIsbn(isbn);
		bookResponse.setAuthor(author);
		bookResponse.setTitle(title);
		bookResponse.setPrice(price);
		Mockito.when(standardBookCatalogService.deleteBook(isbn))
				.thenReturn(bookResponse);
		// 2. Call exercise method
		mockMvc.perform(delete("/books/"+isbn).accept(MediaType.APPLICATION_JSON)
				)
				// 3. Verification
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.isbn",is(isbn)))
				.andExpect(jsonPath("$.author",is(author)))
				.andExpect(jsonPath("$.title",is(title)))
				.andExpect(jsonPath("$.price",is(price)));
		// 4. Tear-down
	}



}
