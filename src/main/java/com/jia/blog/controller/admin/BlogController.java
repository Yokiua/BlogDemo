package com.jia.blog.controller.admin;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.jia.blog.pojo.Blog;
import com.jia.blog.pojo.User;
import com.jia.blog.service.BlogService;
import com.jia.blog.service.TagService;
import com.jia.blog.service.TypeService;
import com.jia.blog.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * 后台管理博客的处理器类 【Redis优化】
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private RedisUtil redisUtil;

    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * 返回blogs管理主页
     * @return 返回html页面
     */
    @GetMapping("/blogs")
    public String list(){
        return "admin/blog/blogs";
    }

    /**
     * 返回新增blog页面
     * @return html
     */
    @GetMapping("/blogs/inputBlog")
    public String inputBlog(){
        return "admin/blog/blogs-input";
    }

    /*--------------------Select-------------------*/
    /**
     * 获取分类的全部内容
     * @return 返回json
     */
    @GetMapping("/blogs/AllTypes")
    @ResponseBody
    public String Alltypes() throws JsonProcessingException {
        return mapper.writeValueAsString(typeService.listAllType());
    }

    /**
     * 获取标签的全部内容
     * @return 返回json
     * @throws JsonProcessingException json处理的异常
     */
    @GetMapping("/blogs/AllTags")
    @ResponseBody
    public String AllTags() throws JsonProcessingException {
        return mapper.writeValueAsString(tagService.listAllTag());
    }

    /**
     * 获取全部的博客列表
     * @return 返回json
     */
    @GetMapping("/blogs/page")
    @ResponseBody
    public String findAllBlog(Integer pageNum,Integer pageSize) throws JsonProcessingException {
        //若前台不传递参数,后台默认赋值为每页显示6条
        if (pageSize == null){
            pageSize = 6;
        }
        PageInfo<Blog> blogs = blogService.getBlogList(pageNum, pageSize);

        return mapper.writeValueAsString(blogs);
    }

    /**
     * 条件查询并分页
     * @param blog 接收前台的数据并封装
     * @param pageNum 页码
     * @return json
     * @throws JsonProcessingException json处理异常
     */
    @GetMapping("/blogs/queryFrom")
    @ResponseBody
    public String queryFrom(Blog blog, Integer pageNum) throws JsonProcessingException {

        if ("".equals(blog.getTitle())){
            blog.setTitle(null);
        }
        PageInfo<Blog> blogList = blogService.findByBlogList(pageNum, 6, blog);
        return mapper.writeValueAsString(blogList);
    }

    /*--------------------Delete-------------------*/
    /**
     * 删除博客的方法
     * @return 返回布尔(是否删除成功)
     */
    @GetMapping("/blogs/removeBlog")
    @ResponseBody
    public boolean removeBlog(Long id){
        int i = blogService.removeBlog(id);
        if (i != 0 ){
            //删除redis的数据 (获取的全部、最新推荐、分类、标签、博客数量、博客归档)
            redisUtil.del("allBlogs");
            redisUtil.del("allTypes");
            redisUtil.del("allTags");
            redisUtil.del("newBlogs");
            redisUtil.del("blogCount");
        }
        return i != 0;
    }

    /*--------------------Insert-------------------*/
    /**
     * 新增博客
     * @return json
     */
    @PostMapping("/blogs/saveBlog")
    @ResponseBody
    public Boolean saveBlog(Blog blog, HttpSession session){
        //从session中获取用户对象
        User user = (User) session.getAttribute("user");
        //将所属用户赋值给博客实体类
        blog.setUser(user);
        blog.setUser_id(user.getId());
        //通过表单提交的id，将对应的分类加入博客
        blog.setType(typeService.getType(blog.getType_id()));
        //通过表单提交的id数组，将查询的结果添加到blog实体类中
        blog.setTags(tagService.listByIdsTag(blog.getTagIds()));
        //将博客内容存储到数据库
        int i = blogService.saveBlog(blog);
        if (i!=0){
            //删除redis的数据 (获取的全部、最新推荐、分类、标签、博客数量)
            for (int y = 0; y < 10; y++) { //默认删除10条
                redisUtil.del("allBlogs_"+y);
            }
            redisUtil.del("allTypes");
            redisUtil.del("allTags");
            redisUtil.del("newBlogs");
            redisUtil.del("blogCount");
        }
        return i != 0;
    }

    /*--------------------Update-------------------*/
    /**
     * 修改博客(跳转到修改页面,并携带数据)
     * @param id 修改博客的id
     * @return 返回是否修改成功(使用引擎模板渲染)
     */
    @GetMapping("/blogs/updateBlog")
    public String updateBlog(Long id,Model model){
        Blog blog = blogService.getBlog(id);
        //分类查询
        model.addAttribute("types",typeService.listAllType());
        //标签查询
        model.addAttribute("tags",tagService.listAllTag());
        //存储博客
        model.addAttribute("blog",blog);
        return "/admin/blog/blogs-update";
    }

    @GetMapping("/blog/update")
    public String update(){
        return "/admin/blog/blogs-update";
    }

    /**
     * 修改博客(操作数据库)
     * @return 返回布尔(是否修改成功)
     */
    @PostMapping("/blogs/updateBlog2")
    @ResponseBody
    public Boolean updateBlog2(Blog blog) throws JsonProcessingException {
        blog.setUpdateTime(new Date());
        int i = blogService.updateBlog(blog);
        //修改完毕之后，把redis中的数据删掉
        if (i!=0){
            //删除redis的数据 (获取的全部、最新推荐、分类、标签)
            redisUtil.del("allBlogs");
            redisUtil.del("allTypes");
            redisUtil.del("allTags");
            redisUtil.del("newBlogs");
        }
        return i != 0;
    }
}
