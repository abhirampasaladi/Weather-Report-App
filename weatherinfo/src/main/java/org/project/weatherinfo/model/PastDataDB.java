package org.project.weatherinfo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Entity
@Table(name = "historydb")
@Builder
@AllArgsConstructor
@Getter
public class PastDataDB {

    @Id
    @Column(name = "datetime")
    private LocalDateTime localDateTime;

    @Column(name = "postalcode")
    private String postalCode;

    @Column(name = "weathercondition")
    @Length(max = 1000)
    private String accuAndVCWeatherInfo;

    @Column(name="accudatetime")
    private String accuWeatherInfoDateTime;

    @Column(name="accucondition")
    private String accuWeatherInfoCondition;

    @Column(name="accutemp")
    private String accuWeatherInfoTemp;

    @Column(name="vcdatetime")
    private String vcWeatherInfoDateTime;

    @Column(name="vctemp")
    private String vcWeatherInfoTemp;

    @Column(name="vcfeelslike")
    private String vcWeatherInfoFeelsLike;

    @Column(name="vccondition")
    private String vcWeatherInfoCondition;

    public PastDataDB() {
 // show
    }
}
