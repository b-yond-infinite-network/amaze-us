package com.beyond.microservice.bus.driver;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "DRIVERTABLE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Driver{
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
}
