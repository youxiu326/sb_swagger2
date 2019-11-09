package com.youxiu326.controller;

import com.youxiu326.entity.Book;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import java.util.*;

@RestController
@RequestMapping(value = "/bookcurd")
@Api(value="书籍controller",tags={"书籍操作接口"})
public class BookController {

    /**
     *
     @Api：修饰整个类，描述Controller的作用
     @ApiOperation：描述一个类的一个方法，或者说一个接口
     @ApiParam：单个参数描述
     @ApiModel：用对象来接收参数
     @ApiProperty：用对象接收参数时，描述对象的一个字段
     @ApiResponse：HTTP响应其中1个描述
     @ApiResponses：HTTP响应整体描述
     @ApiIgnore：使用该注解忽略这个API
     @ApiError ：发生错误返回的信息
     @ApiParamImplicitL：一个请求参数
     @ApiParamsImplicit 多个请求参数
     */

    /**Content-type常见类型：
     * 1.application/x-www-form-urlencoded
     * 2.application/json
     * 3.multipart/form-data
     * 4.text/xml
     * consumes = "application/x-www-form-urlencoded;charset=utf-8"     指定处理请求的提交内容类型（Content-Type），例如application/json, text/html
     * produces = "application/json;charset=utf-8"     返回json格式数据
     */


    Map<Long, Book> books = Collections.synchronizedMap(new HashMap<Long, Book>());

    @ApiOperation(value="获取图书列表", notes="获取图书列表")
    @RequestMapping(value={""}, method= RequestMethod.GET)
    public List<Book> getBook() {
        List<Book> book = new ArrayList<>(books.values());
        return book;
    }

    @ApiOperation(value="form格式提交创建图书", notes="form格式提交创建图书(哈哈哈)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "名称", required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "price", value = "价格", required = true, dataType = "float",paramType = "query")
    })
    @RequestMapping(value = "/saveBook",method = RequestMethod.POST,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String saveBook(Book book){
        Book book1 = books.get(book.getId());
        if(book1 == null){
            book1 = new Book();
            book1.setId(new Random().nextLong());
        }
        book1.setName(book.getName());
        book1.setPrice(book.getPrice());
        books.put(book1.getId(), book1);
        // 这里返回json字符串
        return "{\"msg\":\"SUCCESS！\",\"state\":1}";
    }


    @ApiOperation(value="json格式提交创建图书", notes="json格式提交创建图书")
    @ApiImplicitParam(name = "book", value = "图书详细实体", required = true, dataType = "Book")
    @RequestMapping(value="", method=RequestMethod.POST)
    public String postBook(@RequestBody Book book) {
        books.put(book.getId(), book);
        return "SUCCESS";
    }
    @ApiOperation(value="获图书细信息", notes="根据url的id来获取详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long",paramType = "path")
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public Book getBook(@PathVariable Long id) {
        return books.get(id);
    }

    @ApiOperation(value="更新信息", notes="根据url的id来指定更新图书信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "图书ID", required = true, dataType = "Long",paramType = "path"),
            @ApiImplicitParam(name = "book", value = "图书实体book", required = true, dataType = "Book")
    })
    @RequestMapping(value="/{id}", method= RequestMethod.PUT,produces = "application/json")
    public String putUser(@PathVariable Long id, @RequestBody Book book) {
        Book book1 = books.get(id);
        book1.setName(book.getName());
        book1.setPrice(book.getPrice());
        books.put(id, book1);
        return "SUCCESS";
    }

    @ApiOperation(value="删除图书", notes="根据url的id来指定删除图书")
    @ApiImplicitParam(name = "id", value = "图书ID", required = true, dataType = "Long",paramType = "path")
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public String deleteUser(@PathVariable Long id) {
        books.remove(id);
        return "SUCCESS";
    }

    @ApiIgnore//使用该注解忽略这个API
    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    public String jsonTest() {
        return " hi you!";
    }
}