package com.jia.blog.pojo;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "t_user")
public class User {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String nickname; //用户昵称
    private String username; //用户账号名
    private String password; //用户密码
    private String email; //邮箱
    private String avatar; //头像
    private Integer type; //用户的类型
    private Date createTime; //创建时间
    private Date updateTime; //更新时间

}
