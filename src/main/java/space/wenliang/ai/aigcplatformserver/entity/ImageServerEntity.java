package space.wenliang.ai.aigcplatformserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 图像模型服务配置实体
 * @author wenliang
 * @since 2024-03-17
 */
@Data
@TableName("image_server")
public class ImageServerEntity {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String name;
    
    private String interfaceType;
    
    private String host;
    
    private String path;
    
    private String apiKey;
    
    private String apiSecret;
    
    private String appId;
    
    private String model;
    
    private Double temperature;
    
    private Integer maxTokens;
    
    private Boolean active;
    
    private String templateName;
    
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
} 