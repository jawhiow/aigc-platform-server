package space.wenliang.ai.aigcplatformserver.controller.model;

import cn.hutool.core.codec.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import space.wenliang.ai.aigcplatformserver.bean.model.ChatTtsConfig;
import space.wenliang.ai.aigcplatformserver.common.ModelTypeEnum;
import space.wenliang.ai.aigcplatformserver.model.audio.AudioContext;
import space.wenliang.ai.aigcplatformserver.model.audio.AudioCreater;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("model/chat-tts")
public class ChatTtsController {

    private final AudioCreater audioCreater;

    public ChatTtsController(AudioCreater audioCreater) {
        this.audioCreater = audioCreater;
    }

    @PostMapping("playAudio")
    public ResponseEntity<byte[]> playAudio(@RequestBody ChatTtsConfig config) throws Exception {
        try {
            AudioContext audioContext = new AudioContext();

            audioContext.setType(ModelTypeEnum.chat_tts.getName());
            audioContext.setText(config.getText());
            audioContext.setChatTtsConfig(config);
            ResponseEntity<byte[]> audioResponse = audioCreater.createAudio(audioContext);
            HttpHeaders headers = audioResponse.getHeaders();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, headers.getFirst(HttpHeaders.CONTENT_TYPE))
                    .header("x-text-data", headers.getFirst("x-text-data"))
                    .body(audioResponse.getBody());
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .header("msg", Base64.encode(e.getMessage().getBytes(StandardCharsets.UTF_8)))
                    .body(null);
        }
    }

}