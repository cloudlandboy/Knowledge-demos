package cn.clboy.demo.shiro.springboot.web.controller;

import cn.clboy.demo.shiro.springboot.dto.LoginInfoDTO;
import cn.clboy.demo.shiro.springboot.dto.UserInfoDTO;
import cn.clboy.demo.shiro.springboot.entity.User;
import cn.clboy.demo.shiro.springboot.service.UserService;
import cn.clboy.demo.shiro.springboot.utils.AuthenticationFailedUtil;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author clboy
 * @Date 2021/2/1 下午3:51
 * @Since 1.0.0
 */

@Controller
public class SiteController {

    @Autowired
    UserService userService;

    /**
     * 由shiro框架负责登录，get请求直接走该方法，post请求shiro框架会负责登录，登陆失败会来到这里并设置shiroLoginFailure（错误的类签名）
     * 具体实现看 org.apache.shiro.web.filter.authc.FormAuthenticationFilter
     *
     * @param shiroLoginFailure
     * @param model
     * @return java.lang.String
     * @Author clboy
     * @Date 2021/2/1 下午10:18
     * @since 1.0.0
     */
    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public String login(@RequestAttribute(value = "shiroLoginFailure", required = false) String shiroLoginFailure, Model model) {
        if (shiroLoginFailure != null) {
            model.addAttribute("failureInfo", AuthenticationFailedUtil.getMsg(shiroLoginFailure));
        }
        return "loginPage";
    }

    @GetMapping("/register")
    public String register() {
        return "registerPage";
    }

    @PostMapping("/register")
    public String register(User user, String rePassword, Model model) {
        //参数校验
        if (!paramCorrect(user, rePassword, model)) {
            return "registerPage";
        }
        userService.createUser(user);
        return "redirect:/login";
    }


    @GetMapping("/loginInfo")
    @RequiresUser
    public ResponseEntity loginInfo() {
        final Subject subject = SecurityUtils.getSubject();
        User principal = (User) subject.getPrincipal();
        LoginInfoDTO loginInfo = new LoginInfoDTO(principal.getUsername());
        return ResponseEntity.ok(JSON.toJSONString(loginInfo));
    }

    @GetMapping("/loginUserInfo")
    @RequiresUser
    public String loginUserInfo(Model model) {
        final Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        //角色
        final Set<String> roles = userService.findRoles(user.getUsername());
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUsername(user.getUsername());
        userInfoDTO.setLocked(user.getLocked());
        userInfoDTO.setRoles(roles);
        model.addAttribute("userInfo", userInfoDTO);
        return "loginUserInfo";
    }

    /**
     * 注册校验
     *
     * @param user
     * @param rePassword
     * @param model
     * @return
     */
    public boolean paramCorrect(User user, String rePassword, Model model) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<User>> result = validator.validate(user);
        final Set<String> failureInfo = new HashSet<>();
        model.addAttribute("failureInfo", failureInfo);

        if (!CollectionUtils.isEmpty(result)) {
            result.stream().map(err -> err.getMessage()).forEach(msg -> failureInfo.add(msg));
            return false;
        }
        if (!user.getPassword().equals(rePassword)) {
            failureInfo.add("两次密码输入不一致");
            return false;
        }

        boolean exists = userService.existsUserByName(user.getUsername());
        if (exists) {
            failureInfo.add("用户名已经存在");
            return false;
        }

        return true;
    }
}