package org.project.weatherinfo.dao;

import org.project.weatherinfo.model.PastDataDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrudRepo extends JpaRepository<PastDataDB, String> {
}
