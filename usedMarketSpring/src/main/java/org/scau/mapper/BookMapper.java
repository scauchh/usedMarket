package org.scau.mapper;

import org.apache.ibatis.annotations.*;
import org.scau.pojo.Book;

import java.util.List;

@Mapper
public interface BookMapper {

    // 添加新书籍
    @Insert("INSERT INTO book (user_id, title, picture, price, type, datetime, notes) " +
            "VALUES (#{userID}, #{title}, #{picture}, #{price}, #{type}, #{datetime}, #{notes})")
    void addBook(Book b);

    // 根据ID删除书籍
    @Delete("DELETE FROM book WHERE book_id = #{bookID}")
    void deleteBookByID(Integer bookID);

    // 根据书籍ID更新书籍
    @Update("UPDATE book SET title = #{title}, picture = #{picture}, price = #{price}, " +
            "type = #{type}, datetime = #{datetime}, notes = #{notes} WHERE book_id = #{bookID}")
    void updateBookByID(Book b);

    // 根据ID查找书籍
    @Select("SELECT * FROM book WHERE book_id = #{bookID}")
    Book getBookByID(Integer bookID);

    // 获取所有的书籍
    @Select("SELECT * FROM book;")
    List<Book> getAllBook();

    // 获取所有书籍的总数
    Integer getAllBookNum(String title, String type);

    // 获取当前用户所有书籍的数量
    @Select("SELECT COUNT(*) FROM book WHERE user_id = #{userID}")
    Integer getBookNumByID(Integer userID);

    // 根据书籍标题获得书籍
    @Select("SELECT * FROM book WHERE title = #{title}")
    Book getBookByTitle(String title);

    // 根据图片查询书籍，用于判断图片是否有效
    @Select("SELECT * FROM book WHERE picture = #{image}")
    Book getBookByImage(String image);

    // 根据类型查询书籍，用于判断类型是否可以删除
    @Select("SELECT COUNT(*) FROM book WHERE type = #{typeName}")
    Integer getBookNumByType(String typeName);

    // 分页获得所有的书籍
    List<Book> getPageBook(Integer offset, Integer pageSize, String title, String type);

    // 根据用户ID分页获得所有的书籍
    List<Book> getPageBookByID(Integer userID, Integer offset, Integer pageSize);
}
