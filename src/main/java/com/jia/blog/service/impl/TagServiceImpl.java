package com.jia.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jia.blog.excetion.NotFondExcetion;
import com.jia.blog.mapper.TagMapper;
import com.jia.blog.pojo.Blog;
import com.jia.blog.pojo.Tag;
import com.jia.blog.pojo.t_blog_tags;
import com.jia.blog.service.BlogService;
import com.jia.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 管理标签的用户实现类
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private BlogService blogService;

    /*--------------------Select-------------------*/
    /**
     * 通过id查询
     * @param id 查询id
     * @return 实体类
     */
    @Override
    public Tag getTag(Long id) {
        return tagMapper.getTag(id);
    }

    /**
     * 查询所有的标签(并查询标签对应博客的数量)
     * @return 实体类集合
     */
    @Override
    public List<Tag> listAllTag() {
        return tagMapper.listAllTag();
    }

    /**
     * 根据name查询数据库
     * @param name 查询的name
     * @return 实体类
     */
    @Override
    public Tag getTagByName(String name) {
        return tagMapper.getTagByName(name);
    }

    /**
     * 根据id数组查询(前台的数据为 1,2,3 这种)
     * @param ids id数据
     * @return 实体类集合
     */
    @Override
    public List<Tag> listByIdsTag(String ids) {
        List<Long> listId = this.convertToList(ids);
        List<Tag> tags = new ArrayList<Tag>();
        //增强for
        for (Long value : listId) {
            //调用通过id查询的方法
            Tag tag = this.getTag(value);
            tags.add(tag);
        }
        return tags;
    }

    /**
     * 查询标签 获取对应的博客个数
     * @return 实体类集合
     */
    @Override
    public List<Tag> findByBlog_Tags_Count() {
        List<Tag> tags = tagMapper.listAllTag();
        for (Tag tag : tags) {
            Integer count = tagMapper.findByBlog_Tags_Count(tag.getId());
            tag.setCount(count);
        }
        return tags;
    }

    /**
     * 查询中间表,获得数据的实体类(包括分类名、用户昵称、用户头像地址、标签名集合)
     * (分别为blog的id和tag的id，多对一)
     * @param id 查询的id
     * @return 分页插件封装1
     */
    @Override
    public PageInfo<Blog> findByBlog_Tags_List(Long id, Integer pageNum, Integer pageSize) {
        //多对一的实体类 (多个blog 对应一个 tag)
        List<t_blog_tags> byBlog_tags_list = tagMapper.findByBlog_Tags_List(id);

        PageHelper.startPage(pageNum,pageSize);
        List<Blog> blogList = new ArrayList<Blog>();  //存放标签对应的博客
        List<String> tagNames; //作用域提升，防止复用

        for (t_blog_tags bt : byBlog_tags_list) {
            //存放博客对应的标签名
            tagNames = new ArrayList<String>();
            Blog blog = blogService.getByIdBlog(bt.getBlogsId());
            //将字符串的标签转为集合
            List<Long> tagIds = this.convertToList(blog.getTagIds());
            for (Long tagId : tagIds) {
                Tag tag = tagMapper.getTag(tagId);
                tagNames.add(tag.getName());
            }
            blog.setTagName(tagNames);
            blogList.add(blog);
        }

        return new PageInfo<Blog>(blogList);
    }

    /*--------------------Insert-------------------*/
    /**
     * 添加分类(事务控制)
     * @param tag 实体类
     * @return 影响的行数
     */
    @Transactional
    @Override
    public int saveTag(Tag tag) {
        int i = tagMapper.saveTag(tag);
        if (i == 0){
            return 0;
        }
        return Math.toIntExact(tag.getId()); //添加后的id
    }

    /*--------------------Update-------------------*/
    /**
     * 修改分类(事务控制)
     * @param id 修改的id
     * @param tag 实体类
     * @return 影响的行数
     */
    @Transactional
    @Override
    public int updateTag(Long id, Tag tag){
        if (tagMapper.getTag(id) == null){
            throw new NotFondExcetion("不存在该类型");

        }
        return tagMapper.updateTag(id, tag);
    }

    /*--------------------Delete-------------------*/
    /**
     * 删除分类(事务控制)
     * @param id 要删除的id
     * @return 影响的行数
     */
    @Transactional
    @Override
    public int reomveTag(Long id) {
        return tagMapper.reomveTag(id);
    }

    /*--------------------自定义方法-------------------*/
    /**
     * 把字符串转换成数组的方法(,分割)
     * @param ids 字符串数组
     * @return 返回集合
     */
    public List<Long> convertToList(String ids){
        List<Long> list = new ArrayList<Long>();
        if (!"".equals(ids) && ids != null){
            String[] idArray = ids.split(",");
            for (int i = 0; i < idArray.length; i++) {
                list.add(new Long(idArray[i]));
            }
        }
        return list;
    }


}
