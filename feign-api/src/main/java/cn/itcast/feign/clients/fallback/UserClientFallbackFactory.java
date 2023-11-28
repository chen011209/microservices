package cn.itcast.feign.clients.fallback;

import cn.itcast.feign.clients.UserClient;
import cn.itcast.feign.pojo.User;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserClientFallbackFactory implements FallbackFactory<UserClient> {
    @Override
    public UserClient create(Throwable throwable) {
        return new UserClient() {
            @Override
            public User findById(Long id) {
                log.error("查询用户异常", throwable);

                User user = new User();
                user.setUsername("错误用户");
                return user;
            }

            @Override
            public User testFind(Long id) {
                log.error("查询用户异常", throwable);

                User user = new User();
                user.setUsername("错误用户");
                return user;
            }
        };
    }
}
