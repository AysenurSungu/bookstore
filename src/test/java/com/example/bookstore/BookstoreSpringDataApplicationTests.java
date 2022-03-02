package com.example.bookstore;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.example.bookstore.dto.BookResponse;
import com.example.bookstore.service.business.StandardBookCatalogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


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



}
