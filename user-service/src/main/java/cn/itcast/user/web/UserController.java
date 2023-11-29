package cn.itcast.user.web;

import cn.itcast.user.config.PatternProperties;
import cn.itcast.user.pojo.User;
import cn.itcast.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
@RequestMapping("/user")
@RefreshScope
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 路径： /user/110
     *
     * @param id 用户id
     * @return 用户
     */
    @GetMapping("/{id}")
    public User queryById(@PathVariable("id") Long id, @RequestHeader(value = "Truth", required = false) String truth) throws InterruptedException {
        System.out.println(truth);

        if (id == 3) {
            // 休眠，触发熔断
            Thread.sleep(60);
        } else if (id == 4) {
            throw new RuntimeException("故意出错，触发熔断");
        }

        return userService.queryById(id);
    }

    @GetMapping("/test/{id}")
    User testFind(@PathVariable("id") Long id){
        User user = new User();
        user.setId(id);
        user.setUsername("测试返回用户");
        return user;
    }


//    @Autowired
//    private PatternProperties properties;

//    @GetMapping("prop")
//    public PatternProperties properties(){
//        return properties;
//    }
//
//
//     @Value("${pattern.dateformat}")
//     private String dateformat;
//    @GetMapping("now")
//    public String now(){
//        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(properties.getDateformat()));
//    }

}
