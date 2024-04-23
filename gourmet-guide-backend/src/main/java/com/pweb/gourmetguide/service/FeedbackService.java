package com.pweb.gourmetguide.service;

import com.pweb.gourmetguide.dtos.FeedbackRequest;
import com.pweb.gourmetguide.dtos.FeedbackResponse;
import com.pweb.gourmetguide.model.Feedback;
import com.pweb.gourmetguide.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public FeedbackResponse addFeedback(FeedbackRequest feedbackRequest) {
        Feedback createdFeedback = new Feedback();
        createdFeedback.setEmail(feedbackRequest.getEmail());
        createdFeedback.setName(feedbackRequest.getName());
        createdFeedback.setMessage(feedbackRequest.getMessage());
        createdFeedback.setSelectOption(feedbackRequest.getSelectOption());
        createdFeedback.setRadioButtonOption(feedbackRequest.getRadioButtonOption());
        createdFeedback.setCheckBoxOption(feedbackRequest.isCheckBoxOption());

        feedbackRepository.save(createdFeedback);

        return new FeedbackResponse(createdFeedback.getId(),
                createdFeedback.getName(), createdFeedback.getEmail(), createdFeedback.getMessage(),
                createdFeedback.getSelectOption(), createdFeedback.getRadioButtonOption(),
                createdFeedback.isCheckBoxOption());
    }
}
