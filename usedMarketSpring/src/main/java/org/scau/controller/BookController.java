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

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/book")
public class BookController {

    final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    BookService bookService;

    // 检验数据
    private String verifyData(Book book){
        String title = book.getTitle();
        String picture = book.getPicture();
        Double price = book.getPrice();
        String type = book.getType();
        String notes = book.getNotes();

        // 检验标题
        if (title == null || title.isEmpty()) {
            return "标题不能为空";
        }
        if (title.length() < 3 || title.length() > 25) {
            return "书籍标题的长度必须为3~25位";
        }

        // 检验图片
        if (picture == null || picture.isEmpty()) {
            return "图片不能为空";
        }

        // 检验价格
        if (price == null) {
            return "价格不能为空";
        }

        // 检验类型
        if (type == null || type.isEmpty()) {
            return "类型不能为空";
        }

        // 检验备注
        if (notes == null) book.setNotes("");
        if (notes != null && notes.length() > 200) {
            return "备注的长度不能超过100位";
        }

        // 获取当前时间
        book.setDatetime(LocalDateTime.now());

        return "success";
    }

    // 添加书籍
    @RequestMapping("/addBook")
    public Result addBook(Book book) {
        String result = verifyData(book);
        if(!result.equals("success")) return Result.error(result);

        try {
            Map<String, Object> claim = ThreadLocalUtil.get();
            book.setUserID(Integer.valueOf(claim.get("id").toString()));

            bookService.addBook(book);
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
    public Result updateBookByID(Book book) {
        String result = verifyData(book);
        if(!result.equals("success")) return Result.error(result);

        try {
            bookService.updateBookByID(book);
            return Result.success();
        } catch (Exception e) {
            logger.error(e.toString());
            return Result.error("修改失败");
        }
    }

    // 分页获得所有的书籍
    @RequestMapping("/getPageBook")
    public Result getPageBook(String pageNum, String pageSize, String title, String type) {
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
