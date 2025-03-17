package space.wenliang.ai.aigcplatformserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import space.wenliang.ai.aigcplatformserver.entity.ImageServerEntity;
import space.wenliang.ai.aigcplatformserver.mapper.ImageServerMapper;
import space.wenliang.ai.aigcplatformserver.service.ImageServerService;
import space.wenliang.ai.aigcplatformserver.service.ImageGenerateService;

/**
 * 图像模型服务实现类
 * @author wenliang
 * @since 2024-03-17
 */
@Slf4j
@Service
public class ImageServerServiceImpl extends ServiceImpl<ImageServerMapper, ImageServerEntity> implements ImageServerService {

    @Autowired
    @Qualifier("siliconFlowImageService")
    private ImageGenerateService siliconFlowImageService;

    @Override
    public String generateImage(String prompt, String negativePrompt, Integer width, Integer height,
                              Integer steps, Double cfgScale, String sampler, Long seed) {
        // 获取激活的图像服务
        LambdaQueryWrapper<ImageServerEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ImageServerEntity::getActive, true);
        ImageServerEntity imageServer = this.getOne(wrapper);
        
        if (imageServer == null) {
            throw new RuntimeException("未找到激活的图像服务");
        }
        
        // 根据不同的接口类型调用不同的实现
        switch (imageServer.getInterfaceType()) {
            case "SILICON_FLOW":
                return siliconFlowImageService.generateImage(
                    prompt, negativePrompt, width, height, steps, cfgScale, sampler, seed,
                    imageServer.getHost(), imageServer.getPath(), imageServer.getApiKey(),
                    imageServer.getApiSecret(), imageServer.getModel()
                );
            default:
                throw new RuntimeException("不支持的接口类型：" + imageServer.getInterfaceType());
        }
    }
} 