package org.scau.controller;

import org.scau.pojo.Book;
import org.scau.pojo.PageBean;
import org.scau.pojo.Result;
import org.scau.service.BookService;
import org.scau.utils.ThreadLocalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/book")
public class BookController {

    final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    BookService bookService;

    // 添加书籍
    @RequestMapping("/addBook")
    public Result addBook(Book b) {
        try {

            Map<String, Object> claim = ThreadLocalUtil.get();
            b.setUserID(Integer.valueOf(claim.get("id").toString()));

            bookService.addBook(b);
            return Result.success();
        } catch (Exception e) {
            logger.error(e.toString());
            return Result.error("添加书籍失败");
        }
    }

    // 删除书籍
    @RequestMapping("/deleteBook")
    public Result deleteBook(Integer bookID) {
        try {
            bookService.deleteBookByID(bookID);
            return Result.success();
        } catch (Exception e) {
            logger.error(e.toString());
            return Result.error("删除书籍失败");
        }
    }

    // 更新书籍
    @RequestMapping("/updateBook")
    public Result updateBookByID(Book b) {
        try {
            System.out.println("b: " + b);
            bookService.updateBookByID(b);
            return Result.success();
        } catch (Exception e) {
            logger.error(e.toString());
            return Result.error("修改失败");
        }
    }

    // 分页获得所有的书籍
    @RequestMapping("/getPageBook")
    public Result getPageBook(String pageNum, String pageSize, String title, String type) {
        System.out.println("title:"+title);
        System.out.println("type:"+type);
        try {
            PageBean<Book> pb = bookService.getPageBook(Integer.parseInt(pageNum), Integer.parseInt(pageSize), title, type);
            return Result.success(pb);
        } catch (Exception e) {
            logger.error(e.toString());
            return Result.error("获取失败");
        }
    }

    // 根据用户ID分页获得所有的书籍
    @RequestMapping("/getPageBookByID")
    public Result getPageBookByID(String pageNum, String pageSize) {
        try {
            PageBean<Book> pb = bookService.getPageBookByID(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
            return Result.success(pb);
        } catch (Exception e) {
            logger.error(e.toString());
            return Result.error("获取失败");
        }
    }
}
