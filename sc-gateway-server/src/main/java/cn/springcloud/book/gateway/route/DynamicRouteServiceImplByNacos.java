package cn.springcloud.book.gateway.route;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.concurrent.Executor;

@Component
public class DynamicRouteServiceImplByNacos {

    @Autowired
    private DynamicRouteServiceImpl dynamicRouteService;

    public DynamicRouteServiceImplByNacos() {
        dynamicRouteBylistenerNacos("sc-gateway","xujin_test");
    }
    public void dynamicRouteBylistenerNacos (String dataId, String group){
        try {
            Properties properties = new Properties();
            //properties.put("nacos.server-addr", "");
            properties.put(PropertyKeyConst.SERVER_ADDR, "127.0.0.1:8848");
            ConfigService configService=NacosFactory.createConfigService(properties);
            //ConfigService configService=NacosFactory.createConfigService("127.0.0.1:8848");
            String content = configService.getConfig(dataId, group, 5000);
            System.out.println(content);
            configService.addListener(dataId, group, new Listener()  {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    RouteDefinition definition= JSON.parseObject(configInfo,RouteDefinition.class);
                    dynamicRouteService.update(definition);
                }
                @Override
                public Executor getExecutor() {
                    return null;
                }
            });
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

}
