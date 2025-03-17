package space.wenliang.ai.aigcplatformserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import space.wenliang.ai.aigcplatformserver.entity.ImageServerEntity;

/**
 * 图像模型服务Mapper接口
 * @author wenliang
 * @since 2024-03-17
 */
@Mapper
public interface ImageServerMapper extends BaseMapper<ImageServerEntity> {
} 