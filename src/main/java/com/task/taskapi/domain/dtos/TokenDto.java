package com.task.taskapi.domain.dtos;

import com.task.taskapi.domain.models.Token;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenDto {
    private String token;

    public TokenDto(Token token) {
        this.token = token.getContent();
    }
}
