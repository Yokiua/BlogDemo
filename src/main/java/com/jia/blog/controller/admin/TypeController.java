package com.jia.blog.controller.admin;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jia.blog.pojo.Type;
import com.jia.blog.service.TypeService;
import com.jia.blog.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * 管理分类的处理器类 【Redis优化】
 */
@Controller
@RequestMapping("/admin")
public class TypeController {

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private TypeService typeService;

    @Autowired
    private RedisUtil redisUtil;

    /*--------------------跳转html页面-----------------*/
    /**
     * 返回分类管理页面
     * @return html
     */
    @GetMapping("/types")
    public String types(){
        return "admin/type/types";
    }

    /**
     * 返回更新分类的页面
     * @return html
     */
    @GetMapping("/types/update")
    public String update(){
        return "admin/type/types-update";
    }

    /**
     * 返回新增分类页面
     * @return html
     */
    @GetMapping("/types/input")
    public String input(){
        return "admin/type/types-input";
    }

    /*--------------------Select-------------------*/
    /**
     * 查询所有的分类内容
     * @return json
     * @throws JsonProcessingException json异常
     */
    @GetMapping("/types/findAll")
    @ResponseBody
    public String findAllType() throws JsonProcessingException {
        List<Type> types = typeService.listAllType();
        return mapper.writeValueAsString(types);
    }

    /**
     * 分页显示 分类列表
     * @param pageNum 页码
     * @return json
     * @throws JsonProcessingException json异常
     */
    @GetMapping("/types/page")
    @ResponseBody
    public String listType(int pageNum) throws JsonProcessingException {
        PageHelper.startPage(pageNum,6);
        PageInfo<Type> info = new PageInfo<Type>(typeService.listAllType());
        return mapper.writeValueAsString(info);
    }

    /*--------------------Delete-------------------*/
    /**
     * 删除分类
     * @param id 要删除分类的id
     * @return 返回布尔(删除是否成功)
     */
    @GetMapping("/types/remove")
    @ResponseBody
    public Boolean removeType(Long id){
        int i = typeService.reomveType(id);
        if (i != 0){
            //修改完毕之后,清空Redis数据库
            redisUtil.delectFindAll();
        }
        return i != 0;
    }

    /*--------------------Insert-------------------*/
    /**【使用模板引擎渲染前台页面】
     * 新增一个分类
     * @param type 前台接收的数据封装到实体类
     * @param attributes 用于存入提示信息
     * @return 返回分类管理页面(携带提示信息)
     */
    @PostMapping("/types/saveType")
    public String saveType(Type type, RedirectAttributes attributes){

        if (type.getName() == null || "".equals(type.getName())){
            attributes.addFlashAttribute("message","分类名不能为空");
            return "redirect:/admin/types";
        }

        if (typeService.getTypeByName(type.getName()) != null){
            attributes.addFlashAttribute("message","分类名重复");
            return "redirect:/admin/types";
        }

        int i = typeService.saveType(type);
        if (i == 0){
            attributes.addFlashAttribute("message","新增失败");
        }else {
            attributes.addFlashAttribute("message","新增成功");
            //修改完毕之后,清空Redis数据库
            redisUtil.delectFindAll();
        }
        return "redirect:/admin/types";
    }

    /* --------------------Update-----------------*/
    /**
     * 【使用模板引擎渲染前台页面】
     * 跳转到修改页面,并携带修改分类的id
     * @param id 要修改分类的id
     * @param model 存入修改分类id(发送到前台)
     * @return html (返回修改页面，携带model)
     */
    @GetMapping("/types/updateInput/{id}")
    public String update(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/type/types-update";
    }

    /**
     * 修改分类
     * @param id 修改分类的id
     * @param type 前台接收数据,并封装到实体类
     * @param attributes 用于存入提示信息
     * @return html (返回分类管理页面，并携带提示信息)
     */
    @PostMapping("/types/updateType/{id}")
    public String updateType(@PathVariable Long id, Type type,RedirectAttributes attributes){

        if (type.getName() == null || "".equals(type.getName())){
            attributes.addFlashAttribute("message","分类名不能为空");
            return "redirect:/admin/types";
        }

        if (typeService.getTypeByName(type.getName()) != null){
            attributes.addFlashAttribute("message","分类名重复");
            return "redirect:/admin/types";
        }

        int i = typeService.updateType(id,type);
        if (i == 0){
            attributes.addFlashAttribute("message","更新失败");
        }else {
            attributes.addFlashAttribute("message","更新成功");
            //修改完毕之后,清空Redis数据库
            redisUtil.delectFindAll();
        }
        return "redirect:/admin/types";
    }
}
