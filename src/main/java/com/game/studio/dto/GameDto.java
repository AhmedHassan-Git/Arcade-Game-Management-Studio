package com.game.studio.dto;

import lombok.Data;

@Data
public class GameDto {
    private String arcade;
    private Long p1Id;
    private Long p2Id;
    private Long winnerId;
}
