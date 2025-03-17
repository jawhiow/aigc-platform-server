package space.wenliang.ai.aigcplatformserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import space.wenliang.ai.aigcplatformserver.entity.ImageServerEntity;

/**
 * 图像模型服务接口
 * @author wenliang
 * @since 2024-03-17
 */
public interface ImageServerService extends IService<ImageServerEntity> {
    
    /**
     * 生成图像
     * @param prompt 提示词
     * @param negativePrompt 反向提示词
     * @param width 宽度
     * @param height 高度
     * @param steps 步数
     * @param cfgScale CFG比例
     * @param sampler 采样器
     * @param seed 随机种子
     * @return 图像URL
     */
    String generateImage(String prompt, String negativePrompt, Integer width, Integer height, 
                        Integer steps, Double cfgScale, String sampler, Long seed);
} 