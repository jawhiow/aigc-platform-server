package space.wenliang.ai.aigcplatformserver.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import space.wenliang.ai.aigcplatformserver.service.ImageGenerateService;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service("siliconFlowImageService")
public class SiliconFlowImageServiceImpl implements ImageGenerateService {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public SiliconFlowImageServiceImpl(RestClient restClient, ObjectMapper objectMapper) {
        this.restClient = restClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public String generateImage(String prompt, String negativePrompt, Integer width, Integer height,
                              Integer steps, Double cfgScale, String sampler, Long seed,
                              String host, String path, String apiKey, String apiSecret, String model) {
        try {
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("prompt", prompt);
            requestBody.put("negative_prompt", negativePrompt);
            requestBody.put("width", width);
            requestBody.put("height", height);
            requestBody.put("steps", steps);
            requestBody.put("cfg_scale", cfgScale);
            requestBody.put("sampler", sampler);
            if (seed != null) {
                requestBody.put("seed", seed);
            }
            requestBody.put("model", model);

            String url = host + path;
            Map<String, Object> response = restClient.post()
                    .uri(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .header("Authorization", "Bearer " + apiKey)
                    .body(requestBody)
                    .retrieve()
                    .body(Map.class);

            if (response != null && response.containsKey("images")) {
                Object images = response.get("images");
                if (images instanceof String) {
                    return (String) images;
                }
            }

            throw new RuntimeException("生成图像失败：未获取到图像URL");
        } catch (Exception e) {
            log.error("生成图像失败", e);
            throw new RuntimeException("生成图像失败：" + e.getMessage());
        }
    }
} 