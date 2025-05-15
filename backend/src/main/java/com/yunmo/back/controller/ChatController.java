package com.yunmo.back.controller;

import com.yunmo.back.service.impl.DeepSeekServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/ai")
public class ChatController {

    private final DeepSeekServiceImpl aiService;

    @Autowired
    public ChatController(DeepSeekServiceImpl aiService) {
        this.aiService = aiService;
    }

    /**
     * 诗意解读
     *
     * @param content 具体诗歌
     * @return 输出信息
     */
    @PostMapping("/interpret")
    public String interpretPoem(@RequestParam String content) {
        try {
            if (content == null || content.trim().isEmpty()) {
                Map<String, Object> response = new HashMap<>();
                response.put("error", "内容不能为空");
                response.put("timestamp", System.currentTimeMillis());
                return String.valueOf(ResponseEntity.badRequest().body(response));
            }

            String prompt = "请用简单概括解析以下诗歌，分析其意象、韵律和情感表达：\n" + content;
            return aiService.generateResponse(prompt);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "服务器处理请求时发生错误: " + e.getMessage());
            errorResponse.put("timestamp", System.currentTimeMillis());
            return String.valueOf(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse));
        }
    }


    /**
     * AI作诗
     *
     * @param style 体裁
     * @return 输出信息
     */
    @PostMapping("/generate")
    public String generatePoem(@RequestParam String style, @RequestParam String title) {
        String prompt = String.format(
                "请创作一首%s，主题是“%s”。要求符合该体裁的格律规范，运用自然意象，表达真挚情感，整体意境优美。",
                style, title
        );
        return aiService.generateResponse(prompt);
    }

    /**
     * 藏头诗生成
     *
     * @param input 藏头
     * @return 输出信息
     */
    @PostMapping("/generate-acrostic")
    public String generateAcrostic(@RequestParam String input) {
        String prompt = String.format("请以'%s'为藏头生成一首诗，每句首字按顺序使用给定文字，要求意境连贯：",
                input);
        return aiService.generateResponse(prompt);
    }

    /**
     * AI对话
     *
     * @param message 输入信息
     * @return 输出信息
     */
    @PostMapping("/ai2")
    public String generate(@RequestParam String message) {
        return aiService.generateResponse(message);
    }

    @PostMapping("/f")
    public void generate(@RequestParam("file") MultipartFile file) throws IOException {
        // 读取上传文件的内容
        StringBuilder messageBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                messageBuilder.append(line).append("\n");
            }
        }
        String message = messageBuilder.toString().trim();

        // 调用openAiChatModel并获取结果
        String generationResult = this.aiService.generateResponse(message);

        // 将结果写入到当前目录下的output.txt文件中
        String outputPath = "output.txt";
        Files.write(Paths.get(outputPath), generationResult.getBytes());
    }

    /**
     * 诗词智能问答
     *
     * @param question 问题
     * @return 输出信息
     */
    @PostMapping("/poetry-qa")
    public String answerQuestion(@RequestParam String question) {
        String prompt = String.format("请按照以下结构解析问题：\n1.识别诗词出处/作者\n2.分析关键意象（如网页4提到的意象分析法）\n3.说明创作背景\n4.文学手法解析\n问题：%s", question);
        return aiService.generateResponse(prompt);
    }

    /**
     * 诗歌对对子
     *
     * @param topLine 上联
     * @return 输出信息
     */
    @PostMapping("/generate-couplet")
    public String generateCouplet(@RequestParam String topLine) {
        String prompt = String.format("请根据网页7的对仗规则生成下联：\n1.字数相等\n2.平仄相对\n3.词性对应\n上联：%s\n下联要求：", topLine);
        return aiService.generateResponse(prompt);
    }
}
