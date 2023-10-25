package cn.itcast.feign.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;

public class DefaultFeignConfiguration {

    //配在这的类配置可以生效,配在resources的yaml不生效,因为没有spring读取? 还没试过怎么生效

//    @Bean
//    public Logger.Level logLevel(){
//        return Logger.Level.BASIC;
//    }
}
