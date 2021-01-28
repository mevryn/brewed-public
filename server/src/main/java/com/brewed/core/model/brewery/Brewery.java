package com.brewed.core.model.brewery;

import com.brewed.core.model.beer.Beer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "craftedBeers")
@EqualsAndHashCode(exclude = "craftedBeers")
@Entity
public class Brewery {
    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "brewery", cascade = {CascadeType.DETACH})
    private final Set<Beer> craftedBeers = new HashSet<>();
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    private String name;
    private String address;
    private String description;

    public Brewery(String name) {
        this.name = name;
    }

    public Brewery(UUID id) {
        this.id = id;
    }

    public Brewery(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    @PrePersist
    public void initializeUUID() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }

}
