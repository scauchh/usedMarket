package org.scau.service;

import org.scau.pojo.Book;
import org.scau.pojo.PageBean;

import java.util.List;

public interface BookService {

    // 添加新书籍
    void addBook(Book b);

    // 根据ID删除书籍
    void deleteBookByID(Integer bookID);

    // 根据书籍ID更新书籍
    void updateBookByID(Book b);

    // 获取所有的书籍
    List<Book> getAllBook();

    // 根据书籍标题获得书籍
    Book getBookByTitle(String title);

    // 根据图片查询书籍，用于判断图片是否有效
    Book getBookByImage(String image);

    // 分页获得所有的书籍
    PageBean<Book> getPageBook(Integer pageNum, Integer pageSize, String title);

    // 根据用户ID分页获得所有的书籍
    PageBean<Book> getPageBookByID(Integer pageNum, Integer pageSize);
}
