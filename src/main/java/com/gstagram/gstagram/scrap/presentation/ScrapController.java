package com.gstagram.gstagram.scrap.presentation;

import com.gstagram.gstagram.auth.component.JwtTokenProvider;
import com.gstagram.gstagram.course.domain.Course;
import com.gstagram.gstagram.scrap.application.ScrapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/scrap")
public class ScrapController {
    private final ScrapService scrapService;
    private final JwtTokenProvider jwtTokenProvider;

    // 스크랩
    @PostMapping("/{courseId}")
    public void scrap(@RequestHeader String accessToken, @PathVariable Long courseId) {
        String userId = jwtTokenProvider.getUserPk(accessToken);
        scrapService.scrap(userId, courseId);
    }

    // 스크랩 취소
    @PostMapping("/{courseId}")
    public void unscrap(@RequestHeader String accessToken, @PathVariable Long courseId) {
        String userId = jwtTokenProvider.getUserPk(accessToken);
        scrapService.unscrap(userId, courseId);
    }

    // 스크랩한 코스 조회
    @PostMapping("/course") //TODO: 이 부분은 ResponseCourseDto로 변경해야함
    public List<Course> getScrappedCourseList(@RequestHeader String accessToken) {
        String userId = jwtTokenProvider.getUserPk(accessToken);
        return scrapService.getScrappedCourseList(userId);
    }
}
