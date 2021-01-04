package cn.linter.oasys.user.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.groups.ConvertGroup;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户实体类
 *
 * @author wangxiaoyang
 * @since 2020/11/01
 */
@Data
@ToString
@EqualsAndHashCode
public class User implements Serializable {

    private static final long serialVersionUID = -21242857091255014L;
    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空", groups = {Update.class})
    private Long id;
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空", groups = {Create.class})
    @Length(min = 2, max = 20, message = "用户名长度为 2 到 20 之间", groups = {Create.class, Update.class})
    private String username;
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空", groups = {Create.class})
    @Length(min = 6, max = 20, message = "密码长度为 6 到 20 之间", groups = {Create.class, Update.class})
    private String password;
    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空", groups = {Create.class})
    @Length(min = 2, max = 10, message = "姓名长度为 2 到 10 之间", groups = {Create.class, Update.class})
    private String fullName;
    /**
     * 性别
     */
    @NotBlank(message = "性别不能为空", groups = {Create.class})
    @Pattern(regexp = "^([男女])$", message = "性别只能为男或女", groups = {Create.class, Update.class})
    private String gender;
    /**
     * 部门
     */
    @NotNull(message = "部门不能为空", groups = {Create.class})
    @Valid
    @ConvertGroup.List({@ConvertGroup(from = Create.class, to = Dept.Update.class)})
    private Dept dept;
    /**
     * 邮箱地址
     */
    @NotBlank(message = "邮箱地址不能为空", groups = {Create.class})
    @Email(message = "邮箱地址格式不正确", groups = {Create.class, Update.class})
    private String emailAddress;
    /**
     * 手机号码
     */
    @NotBlank(message = "手机号码不能为空", groups = {Create.class})
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号码格式不正确", groups = {Create.class, Update.class})
    private String phoneNumber;
    /**
     * 头像链接
     */
    @NotBlank(message = "头像链接不能为空", groups = {Create.class})
    @URL(message = "头像链接必须是一个URL", groups = {Create.class, Update.class})
    private String profilePicture;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    public interface Create {
    }

    public interface Update {
    }

}