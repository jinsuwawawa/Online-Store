package cn.lili.init;

import cn.lili.modules.search.service.EsGoodsIndexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EsGoodsIndexInitRunner implements ApplicationRunner {

    @Autowired
    private EsGoodsIndexService esGoodsIndexService;


    @Override
    public void run(ApplicationArguments args) {
        try {
            esGoodsIndexService.initIndex();
        } catch (Exception e) {
            log.error("检测ES商品索引失败", e);
        }
    }
}
