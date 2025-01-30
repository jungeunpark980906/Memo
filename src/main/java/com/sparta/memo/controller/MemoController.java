package com.sparta.memo.controller;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class MemoController {

    //key에는 id, Memo에는 메모객체
    private final Map<Long, Memo> memoList = new HashMap<>();

    @PostMapping("/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto) {

        //RequestDto -> Entity
        Memo memo = new Memo(requestDto);

        //Memo Max ID Check!
        //memoList의 사이즈가 0보다 크면, key값중 가장 큰 값의 +1로 id설정
        Long maxId = memoList.size() > 0 ? Collections.max(memoList.keySet()) + 1 : 1;
        memo.setId(maxId);

        //DB저장
        memoList.put(memo.getId(), memo);

        //Entity -> ResponseDTO로 변경
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

        return memoResponseDto;

    }

    @GetMapping("/memos")
    public List<MemoResponseDto> getMemos() {
        // Map To List
        List<MemoResponseDto> responseList = memoList.values()//values(): list안의 모든 메모를 가지고와요
                .stream() //for문처럼 메모를 하나씩 돌려줘요
                .map(MemoResponseDto::new) //하나씩 나오는 메모를 MemoResponseDto의 생성자를 사용해서 만들어줘요
                .toList(); //다시 list로 만들어줘요 (map을 list로 바꾸기 완료)

        return responseList;
    }

    @PutMapping("/memos/{id}") //바로 아래@PathVariable Long id에서 받음
    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
        // 해당 id의 메모가 DB에 존재하는지 확인
        if (memoList.containsKey(id)) {
            // 해당 id의 메모 가져오기
            Memo memo = memoList.get(id);

            // memo 수정
            memo.update(requestDto);
            return memo.getId();
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }

    @DeleteMapping("/memos/{id}")
    public Long deleteMemo(@PathVariable Long id) {
        // 해당 메모가 DB에 존재하는지 확인
        if (memoList.containsKey(id)) {
            // 해당 메모 삭제하기
            memoList.remove(id);
            return id;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }


}


