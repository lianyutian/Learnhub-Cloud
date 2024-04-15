package com.learnhub.remark.controller;

import com.learnhub.remark.domain.dto.LikeRecordFormDTO;
import com.learnhub.remark.service.ILikedRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/1 16:05
 */
@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
@Tag(name = "LikedRecordController", description = "点赞业务相关接口")
public class LikedRecordController {
    private final ILikedRecordService likedRecordService;

    @PostMapping
    @Operation(summary = "点赞或取消点赞")
    public void addLikeRecord(@Valid @RequestBody LikeRecordFormDTO likeRecordFormDTO) {
        likedRecordService.addLikeRecord(likeRecordFormDTO);
    }

    @GetMapping("list")
    @Operation(summary = "查询当前用户是否点赞了指定的业务")
    public Set<Long> isBizLiked(@RequestParam("bizIds") List<Long> bizIds){
        return likedRecordService.isBizLiked(bizIds);
    }
}
