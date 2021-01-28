package com.brewed.core.model.beer;

import com.brewed.core.model.brewery.Brewery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Beer {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    private String name;
    @ManyToOne(cascade = {CascadeType.DETACH})
    private Brewery brewery;
    private String barCode;
    private Double price;
    private Double alcoholPercentage;
    @ManyToOne(cascade = {CascadeType.DETACH})
    private BeerType beerType;
    @ManyToOne(cascade = {CascadeType.DETACH})
    private BeerNote beerNote;
    private Double servingTemperature;
    private Integer ibu;

    public Beer(UUID id) {
        this.id = id;
    }
}
