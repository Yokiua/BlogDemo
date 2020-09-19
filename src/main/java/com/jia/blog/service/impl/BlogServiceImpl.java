package com.jia.blog.service.impl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jia.blog.excetion.NotFondExcetion;
import com.jia.blog.mapper.BlogMapper;
import com.jia.blog.pojo.Blog;
import com.jia.blog.pojo.Type;
import com.jia.blog.pojo.User;
import com.jia.blog.service.BlogService;
import com.jia.blog.service.TypeService;
import com.jia.blog.service.UserService;
import com.jia.blog.utils.MarkdownUtlis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 管理blog的用户实现类
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private TagServiceImpl tagService;

    @Autowired
    private TypeService typeService;

    /*--------------------Select-------------------*/
    /**
     * 查询
     * @param id 查询的id
     * @return 实体类
     */
    @Override
    public Blog getBlog(Long id) {
        Blog blog = blogMapper.getBlog(id);
        //判断博客是否存在
        if (blog == null){
            throw new NotFondExcetion("博客不存在");
        }
        User user = userService.findById(blog.getUser_id());
        //设置用户名密码为空,安全起见
        user.setUsername("");
        user.setPassword("");
        blog.setUser(user);
        return blog;
    }

    /**
     * 获取一个博客(博客详情的方法) 博客内容转换成html
     * @param id 查询id
     * @return 实体类
     */
    @Override
    public Blog getBlogConvert(Long id) {
        Blog blog = this.getBlog(id);
        //把博客里的内容,转换成html(使用Markdown转换工具类)
        String markdown = MarkdownUtlis.markdownToHtmlExtensions(blog.getContent());
        blog.setContent(markdown);
        //把 用户名 用户头像 分类名 存入实体类中
        blog.setTypeName(blog.getType().getName());
        blog.setUserNickname(blog.getUser().getNickname());
        blog.setUserAvatar(blog.getUser().getAvatar());
        //把字符串的tagid数组，转换成集合
        blog.setTags(tagService.listByIdsTag(blog.getTagIds()));
        return blog;
    }

    /**
     * 获取所有发布的博客数量
     * @return 个数
     */
    @Override
    public Integer getBlogCount() {
        return blogMapper.getBlogCount();
    }

    /**
     * 查询一个博客(前台展示的方法,并把内容转换成html)
     * @param id 查询id
     * @return 实体类
     */
    @Override
    public Blog getByIdBlog(Long id) {
        Blog blog = this.getBlog(id);
        //把 用户名 用户头像 分类名 存入实体类中
        blog.setTypeName(blog.getType().getName());
        blog.setUserNickname(blog.getUser().getNickname());
        blog.setUserAvatar(blog.getUser().getAvatar());
        blog.setContent(""); //并把博客的内容为空(节省内存)
        return blog;
    }

    /**
     * 分页查询
     * @param pageNum 页码
     * @param pageSize 每页显示个数
     * @return 分页插件封装的集合
     */
    @Override
    public PageInfo<Blog> getBlogList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Blog> blogs = blogMapper.getAllBlog();
        //添加对应的用户
        for (Blog blog : blogs) {
            User user = userService.findById(blog.getUser_id());
            //设置用户名密码为空,安全起见
            user.setUsername("");
            user.setPassword("");
            blog.setUser(user);
            blog.setContent(""); //并把博客的内容为空(节省内存)
        }
        return new PageInfo<Blog>(blogs);
    }

    /**
     * 分页条件查询
     * @param pageNum 第几页
     * @param pageSize 每页显示的数量
     * @param blog 查询的参数
     * @return 返回分页插件的对象
     */
    @Override
    public PageInfo<Blog> findByBlogList(int pageNum, int pageSize, Blog blog) {
        PageHelper.startPage(pageNum,pageSize);
        List<Blog> blogAndQuery = blogMapper.getBlogAndQuery(blog);
        for (Blog bb : blogAndQuery) {
            bb.setContent("");
        }
        return new PageInfo<Blog>(blogAndQuery);
    }

    /**
     * 查询所有发布的博客(用于前台展示)
     * @param pageNum 页码
     * @param pageSize 每页显示的个数
     * @return 分页插件封装的集合
     */
    @Override
    public PageInfo<Blog> getBlogsByPublished(int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Blog> blogs = blogMapper.getBlogByPublished();
        //添加对应的用户
        for (Blog blog : blogs) {
            User user = userService.findById(blog.getUser_id());
            //设置用户名密码为空,安全起见
            user.setUsername("");
            user.setPassword("");
            blog.setUser(user);
            blog.setContent(""); //并把博客的内容为空(节省内存)
        }
        return new PageInfo<Blog>(blogs);
    }

    /**
     * 通过分类的id,查询对应的博客(并实现分页功能)
     * @param pageNum 页码
     * @param pageSize 每页显示的个数
     * @param id 查询的id
     * @return 分页插件封装的集合
     */
    @Override
    public PageInfo<Blog> findByTypeId(int pageNum, int pageSize, Long id) {
        PageHelper.startPage(pageNum,pageSize);
        List<Blog> blogAndType = blogMapper.findByTypeId(id);
        for (Blog blog : blogAndType) {
            //存入用户
            User user = userService.findById(blog.getUser_id());
            blog.setUserNickname(user.getNickname());
            blog.setUserAvatar(user.getAvatar());
            //存入对应的分类名
            blog.setTypeName(typeService.getType(blog.getType_id()).getName());
            blog.setContent(""); //并把博客的内容为空(节省内存)
        }
        return new PageInfo<Blog>(blogAndType);
    }

    /**
     * 获取推荐的博客，并按照时间倒叙排序(最新在上)
     * @return 实体类集合
     */
    @Override
    public List<Blog> AllBlogSortDate() {
        List<Blog> blogs = blogMapper.AllBlogSortDate();
        for (Blog blog : blogs) {
            blog.setContent("");
        }
        return blogs;
    }

    /**
     * 条件模糊查询(查询博客的标题和内容)
     * @param query 查询的内容
     * @return 分页插件封装的集合
     */
    @Override
    public PageInfo<Blog> search(String query,Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Blog> blogs = blogMapper.search(query);
        for (Blog blog : blogs) {
            Type type = typeService.getType(blog.getType_id());
            User user = userService.findById(blog.getUser_id());
            blog.setTypeName(type.getName());
            blog.setUserAvatar(user.getAvatar());
            blog.setUserNickname(user.getNickname());
            blog.setContent(""); //并把博客的内容为空(节省内存)
        }
        return new PageInfo<Blog>(blogs);
    }

    /**
     * 根据创建年份查询博客
     * @param dataYY 查询的年
     * @return 实体类集合
     */
    @Override
    public List<Blog> archives(String dataYY) {
        List<Blog> archives = blogMapper.archives(dataYY);
        for (Blog b : archives) {
            Type type = typeService.getType(b.getType_id());
            b.setTypeName(type.getName());
            b.setContent(""); //并把博客的内容为空(节省内存)
        }
        return archives;
    }

    /**
     * 获取博客的所有访问量
     * @return 访问量
     */
    @Override
    public Integer findBlogViews() {
        return blogMapper.findBlogViews();
    }

    /*--------------------Insert-------------------*/
    /**
     * 新增(并添加 博客和标签的中间表 数据 )
     * @param blog 实体类
     * @return 影响的行数
     */
    @Override
    @Transactional
    public int saveBlog(Blog blog) {
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);

        int flag = blogMapper.saveBlog(blog);

        //再把博客对应的id存入到中间表
        List<Long> tagIds = tagService.convertToList(blog.getTagIds());
        for (Long tagId : tagIds) {
            //分别添加 博客id 标签id到中间表
            blogMapper.saveBlog_Tag(blog.getId(),tagId);
        }
        return flag;
    }

    /*--------------------Update-------------------*/
    /**
     * 更新博客的点击数
     * @param id 更新的博客id
     * @param views 点击数
     * @return 影响的行数
     */
    @Override
    @Transactional
    public int updateBlogViews(Long id, Integer views) {
        return blogMapper.updateBlogViews(id, views);
    }

    /**
     * 修改
     * @param blog 实体类
     * @return 影响的行数
     */
    @Override
    @Transactional
    public int updateBlog(Blog blog) throws JsonProcessingException {
        return blogMapper.updateBlog(blog);
    }

    /*--------------------Delete-------------------*/
    /**
     * 删除
     * @param id 删除的id
     * @return 影响的行数
     */
    @Override
    @Transactional
    public int removeBlog(Long id) {
        return blogMapper.removeBlog(id);
    }
}
