package com.sami.plant_ecom.repository;

import com.sami.plant_ecom.entity.Plant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Long> {

    @Query(value = """
        SELECT p FROM Plant p
        WHERE (:search IS NULL OR :search = '' OR LOWER(p.plantName) LIKE LOWER(CONCAT('%', :search, '%')))
        """,
            countQuery = """
        SELECT COUNT(p) FROM Plant p
        WHERE (:search IS NULL OR :search = '' OR LOWER(p.plantName) LIKE LOWER(CONCAT('%', :search, '%')))
        """)
    Page<Plant> search(String search, Pageable pageable);


    List<Plant> findByCategory(String category);

}
