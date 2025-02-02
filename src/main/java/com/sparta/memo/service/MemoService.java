package com.sparta.memo.service;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import com.sparta.memo.repository.MemoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemoService {

    // 멤버 변수 선언
    private final MemoRepository memoRepository;

    // 생성자: MemoService(JdbcTemplate jdbcTemplate)가 생성될 때 호출됨
    @Autowired
    public MemoService(MemoRepository memoRepository) {
        // 멤버 변수 생성
        this.memoRepository = memoRepository;
    }

    public MemoResponseDto createMemo(MemoRequestDto requestDto) {
        // RequestDto -> Entity
        Memo memo = new Memo(requestDto);

        // DB 저장
        Memo saveMemo = memoRepository.save(memo);

        // Entity -> ResponseDto
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);
        return memoResponseDto;
    }


    public List<MemoResponseDto> getMemos() {
        // DB 조회
        return memoRepository.findAll().stream().map(MemoResponseDto::new).toList();
        //findAll로 가져와서
        //stream.map(MemoResponseDTO::new)를 사용하여 하나씩 MemoResponseDTO로 변경하고
        //toList()를 이용하여 list타입으로 바꿔준다
    }

    //update는 변경감지를 위해 @Transaction을 걸어줘야함!!!
    @Transactional
    public Long updateMemo(Long id, MemoRequestDto requestDto) {


        // 해당 메모가 DB에 존재하는지 확인
        Memo memo = findMemo(id);


        // memo 내용 수정
        memo.update(requestDto);
        return id;

    }


    public Long deleteMemo(Long id) {


        // 해당 메모가 DB에 존재하는지 확인
        Memo memo = findMemo(id);


        // memo 삭제
        memoRepository.delete(memo);
        return id;


    }

    private Memo findMemo(Long id) {
        // 해당 메모가 DB에 존재하는지 확인
        return memoRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
        );//orElseThrow()를 한 이유 : findById에 null값이 오는 경우 예외처리를 해줘야함 (Optional이니까)
    }


}
