package org.project.weatherinfo.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Entity
@Table(name = "historydb")
@Setter
@Getter
public class PastDataDB {

    @Id
    @Column(name = "datetime")
    private LocalDateTime localDateTime;

    @Column(name = "weathercondition")
    @Length(max = 1000)
    private String accuAndVCWeatherInfoDB;

    public PastDataDB(LocalDateTime localDateTime, String accuAndVCWeatherInfoDB) {
        this.localDateTime= localDateTime;
        this.accuAndVCWeatherInfoDB = accuAndVCWeatherInfoDB;
    }

    public PastDataDB() {

    }
}
