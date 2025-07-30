package org.project.weatherinfo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface CrudRepo extends JpaRepository<PastDataDB, String> {
    List<PastDataDB> findByLocalDateTime(LocalDateTime localDateTime);
}
