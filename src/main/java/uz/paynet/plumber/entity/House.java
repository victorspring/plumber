package uz.paynet.plumber.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private HouseAddress address;

    @ManyToOne
    @JoinColumn(name = "plumber_id")
    private Plumber plumber;


}