package com.LibraryCT.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookPojo {
    private String name;
    private String isbn;
    private int year;
    private String author;
    private int book_category_id;
    private String description;
//public BookPojo(){
//
//}
//    public BookPojo(String name, String isbn, int year, String author, int book_category_id, String description) {
//        this.name = name;
//        this.isbn = isbn;
//        this.year = year;
//        this.author = author;
//        this.book_category_id = book_category_id;
//        this.description = description;
//    }
}
