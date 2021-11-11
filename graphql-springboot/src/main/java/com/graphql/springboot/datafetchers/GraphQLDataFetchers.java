package com.graphql.springboot.datafetchers;

import com.graphql.springboot.entity.Author;
import com.graphql.springboot.entity.Book;
import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class GraphQLDataFetchers {

    private static Author author1 = Author.builder().id(1).name("Mitch Albom").build();
    private static Author author2 = Author.builder().id(2).name("Stephen Hawking").build();
    private static List<Author> authors = new ArrayList<>(Arrays.asList(author1, author2));

    private static Book book1 = Book.builder().id(1).title("Tuesdays with Morrie").pageCount(192).author(author1).build();
    private static Book book2 = Book.builder().id(2).title("A Brief History of Time").pageCount(256).author(author2).build();
    private static Book book3 = Book.builder().id(3).title("Brief Answers to the Big Questions").pageCount(256).author(author1).build();
    private static List<Book> books = new ArrayList<>(Arrays.asList(book1, book2, book3));


    public DataFetcher getBookByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            int bookId = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
            return books
                    .stream()
                    .filter(book -> book.getId().equals(bookId))
                    .findFirst()
                    .orElse(null);
        };
    }

    public DataFetcher getAuthorDataFetcher() {
        return dataFetchingEnvironment -> {
            Book book = dataFetchingEnvironment.getSource();
            int authorId = book.getAuthor().getId();
            return authors
                    .stream()
                    .filter(author -> author.getId().equals(authorId))
                    .findFirst()
                    .orElse(null);
        };
    }

    public DataFetcher getPageCountDataFetcher() {
        return dataFetchingEnvironment -> {
            Book book = dataFetchingEnvironment.getSource();
            return book.getPageCount();
        };
    }

    public DataFetcher getBooksDataFetcher() {
        return dataFetchingEnvironment -> books;
    }

    public DataFetcher getAuthorsDataFetcher() {
        return dataFetchingEnvironment -> authors;
    }

    public DataFetcher addAuthorDataFetcher() {
        return dataFetchingEnvironment -> {
            Author author = new Author();
            author.setId(Integer.parseInt((String) dataFetchingEnvironment.getArguments().get("id")));
            author.setName((String) dataFetchingEnvironment.getArguments().get("name"));
            authors.add(author);
            return author;
        };
    }
}
