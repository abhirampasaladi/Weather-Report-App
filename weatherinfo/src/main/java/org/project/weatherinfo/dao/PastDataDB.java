package org.project.weatherinfo.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "historydb")
@Setter
@Getter
public class PastDataDB {

    @Id
    @Column(name = "date")
    private LocalDateTime localDateTime;

    @Column(name = "weatherCondition")
    private String accuAndVCWeatherInfoDB;

    public PastDataDB(LocalDateTime localDateTime, String accuAndVCWeatherInfoDB) {
        this.localDateTime= localDateTime;
        this.accuAndVCWeatherInfoDB = accuAndVCWeatherInfoDB;
    }

    public PastDataDB() {

    }
}
