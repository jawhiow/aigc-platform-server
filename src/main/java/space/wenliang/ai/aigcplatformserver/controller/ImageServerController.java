package space.wenliang.ai.aigcplatformserver.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import space.wenliang.ai.aigcplatformserver.entity.ImageServerEntity;
import space.wenliang.ai.aigcplatformserver.service.ImageServerService;

/**
 * 图像模型服务控制器
 * @author wenliang
 * @since 2024-03-17
 */
@RestController
@RequestMapping("/api/v1/image-servers")
public class ImageServerController {

    @Autowired
    private ImageServerService imageServerService;

    @GetMapping
    public Page<ImageServerEntity> list(@RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "10") Integer size) {
        return imageServerService.page(new Page<>(page, size));
    }

    @PostMapping
    public ImageServerEntity create(@RequestBody ImageServerEntity imageServer) {
        imageServerService.save(imageServer);
        return imageServer;
    }

    @PutMapping("/{id}")
    public ImageServerEntity update(@PathVariable Long id, @RequestBody ImageServerEntity imageServer) {
        imageServer.setId(id);
        imageServerService.updateById(imageServer);
        return imageServer;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        imageServerService.removeById(id);
    }

    @PutMapping("/{id}/activate")
    public void activate(@PathVariable Long id) {
        // 先取消所有激活状态
        LambdaQueryWrapper<ImageServerEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ImageServerEntity::getActive, true);
        ImageServerEntity activeServer = imageServerService.getOne(wrapper);
        if (activeServer != null) {
            activeServer.setActive(false);
            imageServerService.updateById(activeServer);
        }
        
        // 激活指定服务
        ImageServerEntity imageServer = imageServerService.getById(id);
        if (imageServer != null) {
            imageServer.setActive(true);
            imageServerService.updateById(imageServer);
        }
    }

    @PostMapping("/generate")
    public String generateImage(@RequestBody GenerateImageRequest request) {
        return imageServerService.generateImage(
            request.getPrompt(),
            request.getNegativePrompt(),
            request.getWidth(),
            request.getHeight(),
            request.getSteps(),
            request.getCfgScale(),
            request.getSampler(),
            request.getSeed()
        );
    }

    @Data
    public static class GenerateImageRequest {
        private String prompt;
        private String negativePrompt;
        private Integer width;
        private Integer height;
        private Integer steps;
        private Double cfgScale;
        private String sampler;
        private Long seed;
    }
} 