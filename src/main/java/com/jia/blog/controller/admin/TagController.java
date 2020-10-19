package com.jia.blog.controller.admin;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jia.blog.pojo.Tag;
import com.jia.blog.service.TagService;
import com.jia.blog.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 后台管理标签的处理器类 【Redis优化】
 */
@Controller
@RequestMapping("/admin")
public class TagController {

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private TagService tagService;

    @Autowired
    private RedisUtil redisUtil;

    /*--------------------跳转到html页面-------------------*/
    /**
     * 返回管理标签主页
     * @return html
     */
    @GetMapping("/tags")//访问html页面的
    public String tags(){
        return "admin/tag/tags";
    }

    /**
     * 返回更新页面
     * @return html
     */
    @GetMapping("/tags/update")
    public String update(){
        return "admin/tag/tags-update";
    }

    /**
     * 返回新增页面
     * @return html
     */
    @GetMapping("/tags/input")
    public String input(){
        return "admin/tag/tags-input";
    }

    /*--------------------Select-------------------*/
    /**
     * 分页列表展示
     * @param pageNum 页码
     * @return 返回json
     * @throws JsonProcessingException json处理的异常
     */
    @GetMapping("/tags/page")
    @ResponseBody
    public String listTag(int pageNum) throws JsonProcessingException {
        PageHelper.startPage(pageNum,6);
        PageInfo<Tag> info = new PageInfo<Tag>(tagService.listAllTag());
        return mapper.writeValueAsString(info);
    }


    /*--------------------Delete-------------------*/
    /**
     * 删除标签
     * @param id 要删除标签的id
     * @return 返回布尔(是否删除成功)
     */
    @GetMapping("/tags/remove")
    @ResponseBody
    public Boolean removeTag(Long id){
        int i = tagService.reomveTag(id);
        //修改完毕之后，把redis中的数据删掉
        if (i!=0){
            //清空Redis数据库
            redisUtil.delectFindAll();
            return true;
        }
        return false;
    }

    /*--------------------Insert-------------------*/
    /**
     * 【使用模板引擎渲染前台页面】
     * 新增标签的方法
     * @param tag 前台接收数据，封装到实体类
     * @param attributes (模板引擎渲染的数据)
     * @return 返回管理标签页(携带参数的)
     */
    @PostMapping("/tags/saveTag")
    public String saveTag(Tag tag, RedirectAttributes attributes){

        if (tag.getName() == null || "".equals(tag.getName())){
            attributes.addFlashAttribute("message","分类名不能为空");
            return "redirect:/admin/tags";
        }

        if (tagService.getTagByName(tag.getName()) != null){
            attributes.addFlashAttribute("message","分类名重复");
            return "redirect:/admin/tags";
        }

        int i = tagService.saveTag(tag);
        if (i == 0){
            attributes.addFlashAttribute("message","新增失败");
        }else {
            attributes.addFlashAttribute("message","新增成功");
            //修改完毕之后,清空Redis数据库
            redisUtil.delectFindAll();
        }
        return "redirect:/admin/tags";
    }

    /*--------------------Update-------------------*/
    /**
     * 【使用模板引擎渲染前台页面】
     * 跳转到修改页面，并携带标签的id
     * @param id 修改标签的id
     * @param model model存储数据
     * @return 返回标签管理页(并携带参数)
     */
    @GetMapping("/tags/updateInput/{id}")
    public String update(@PathVariable Long id, Model model){
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tag/tags-update";
    }

    /**
     * 【使用模板引擎渲染前台页面】
     * 修改标签的方法
     * @param id 要修改标签的id
     * @param tag 前台接收的数据封装到实体类
     * @param attributes 存入提示信息
     * @return 返回标签管理页(携带提示信息)
     */
    @PostMapping("/tags/updateTag/{id}")
    public String updateTag(@PathVariable Long id, Tag tag,RedirectAttributes attributes){
        if (tag.getName() == null || "".equals(tag.getName())){
            attributes.addFlashAttribute("message","分类名不能为空");
            return "redirect:/admin/tags";
        }

        if (tagService.getTagByName(tag.getName()) != null){
            attributes.addFlashAttribute("message","分类名重复");
            return "redirect:/admin/tags";
        }

        int i = tagService.updateTag(id,tag);
        if (i == 0){
            attributes.addFlashAttribute("message","更新失败");
        }else {
            attributes.addFlashAttribute("message","更新成功");
            //修改完毕之后,清空Redis数据库
            redisUtil.delectFindAll();
        }
        return "redirect:/admin/tags";
    }
}
