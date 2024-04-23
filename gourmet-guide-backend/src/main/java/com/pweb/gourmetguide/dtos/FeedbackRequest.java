package com.pweb.gourmetguide.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FeedbackRequest {
    private String name;
    private String email;
    private String message;
    private String selectOption;
    private String radioButtonOption;
    private boolean checkBoxOption;
}
