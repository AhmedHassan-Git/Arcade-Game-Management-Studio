package com.game.studio.models.game;

import javax.persistence.*;

import com.game.studio.models.persons.Person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Game {
    @Id
    @GeneratedValue
    private Long id;
    private String A;

    @ManyToOne(cascade = CascadeType.ALL)
    private Person P1;

    @ManyToOne(cascade = CascadeType.ALL)
    private Person P2;

    @ManyToOne(cascade = CascadeType.ALL)
    private Person Winner;

}
