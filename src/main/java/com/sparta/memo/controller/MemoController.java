package com.sparta.memo.controller;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.service.MemoService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MemoController {

    private final JdbcTemplate jdbcTemplate; //외부에서 받아온 jdbs탬플릿

    public MemoController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto) {

        MemoService memoService = new MemoService(jdbcTemplate);
        return memoService.createMemo(requestDto);

    }

    @GetMapping("/memos")
    public List<MemoResponseDto> getMemos() {

        MemoService memoService = new MemoService(jdbcTemplate);
        return memoService.getMemos(); //가져오는 메모들이라 보내는 데이터x


    }

    @PutMapping("/memos/{id}")
    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {

        MemoService memoService = new MemoService(jdbcTemplate);
        return memoService.updateMemo(id, requestDto);


    }

    @DeleteMapping("/memos/{id}")
    public Long deleteMemo(@PathVariable Long id) {

        MemoService memoService = new MemoService(jdbcTemplate);
        return memoService.deleteMemo(id);

    }


}

