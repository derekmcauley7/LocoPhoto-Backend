package com.loco.photo.locophoto.repository

import com.loco.photo.locophoto.bean.Image
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ImageRepository extends JpaRepository<Image, String> {

    @Query("SELECT e FROM Image e WHERE e.city LIKE %?1% AND (e.deleted is null or e.deleted=false)")
    List<Image> findByCityContaining(city);

    @Query("SELECT e FROM Image e WHERE e.userID =?1 AND (e.deleted is null or e.deleted=false)")
    List<Image> findByUserID(id);
}
