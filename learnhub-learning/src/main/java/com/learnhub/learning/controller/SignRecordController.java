package com.learnhub.learning.controller;

import com.learnhub.learning.domain.vo.SignResultVO;
import com.learnhub.learning.service.ISignRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/15 11:02
 */
@RestController
@RequestMapping("sign")
@RequiredArgsConstructor
@Tag(name = "签到记录相关接口")
public class SignRecordController {

    private final ISignRecordService signRecordService;

    @PostMapping("addSignRecords")
    @Operation(summary = "签到功能接口")
    public SignResultVO addSignRecords(){
        return signRecordService.addSignRecords();
    }

    @GetMapping("querySignRecords")
    @Operation(summary = "查询签到记录")
    public Byte[] querySignRecords(){
        return signRecordService.querySignRecords();
    }
}
