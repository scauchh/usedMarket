package org.scau.service.impl;

import org.scau.mapper.BookMapper;
import org.scau.pojo.Book;
import org.scau.pojo.PageBean;
import org.scau.service.BookService;
import org.scau.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookMapper bookMapper;

    // 添加新书籍
    @Override
    public void addBook(Book b) {
        bookMapper.addBook(b);
    }

    // 根据ID删除书籍
    @Override
    public void deleteBookByID(Integer bookID) {
        bookMapper.deleteBookByID(bookID);
    }

    // 根据书籍ID更新书籍
    @Override
    public void updateBookByID(Book b) {
        bookMapper.updateBookByID(b);
    }

    // 根据ID查找书籍
    @Override
    public Book getBookByID(Integer bookID) {
        return bookMapper.getBookByID(bookID);
    }

    // 获取所有的书籍
    @Override
    public List<Book> getAllBook() {
        return bookMapper.getAllBook();
    }

    // 根据书籍标题获得书籍
    @Override
    public Book getBookByTitle(String title) {
        return bookMapper.getBookByTitle(title);
    }

    // 根据图片查询书籍，用于判断图片是否有效
    @Override
    public Book getBookByImage(String image) {
        return bookMapper.getBookByImage(image);
    }

    // 根据类型查询书籍，用于判断类型是否可以删除
    @Override
    public Integer getBookNumByType(String typeName) {
        return bookMapper.getBookNumByType(typeName);
    }

    // 分页获得所有的书籍
    @Override
    public PageBean<Book> getPageBook(Integer pageNum, Integer pageSize, String title, String type) {
        PageBean<Book> pb = new PageBean<>();

        // 查询分页结果
        int total = bookMapper.getAllBookNum(title, type);
        List<Book> books = bookMapper.getPageBook(pageSize*(pageNum-1), pageSize, title, type);

        // 返回分页查询结果
        pb.setTotal(total);
        pb.setItems(books);
        return pb;
    }

    // 根据用户ID分页获得所有的书籍
    @Override
    public PageBean<Book> getPageBookByID(Integer pageNum, Integer pageSize) {
        PageBean<Book> pb = new PageBean<>();

        // 获取userID
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userID = (Integer) map.get("id");

        // 通过userID查询分页结果
        int total = bookMapper.getBookNumByID(userID);
        List<Book> books = bookMapper.getPageBookByID(userID, pageSize*(pageNum-1), pageSize);

        // 返回分页查询结果
        pb.setTotal(total);
        pb.setItems(books);
        return pb;
    }
}
