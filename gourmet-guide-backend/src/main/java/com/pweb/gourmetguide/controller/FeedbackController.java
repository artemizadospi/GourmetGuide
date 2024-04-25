package com.pweb.gourmetguide.controller;

import com.pweb.gourmetguide.dtos.FeedbackRequest;
import com.pweb.gourmetguide.dtos.FeedbackResponse;
import com.pweb.gourmetguide.model.Feedback;
import com.pweb.gourmetguide.repository.FeedbackRepository;
import com.pweb.gourmetguide.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final FeedbackRepository feedbackRepository;

    @PostMapping
    public FeedbackResponse saveFeedback(@RequestBody FeedbackRequest feedbackRequest) {
        return feedbackService.addFeedback(feedbackRequest);
    }
}
