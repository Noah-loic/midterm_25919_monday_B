package auca.ac.rw.restfullApiAssignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import auca.ac.rw.restfullApiAssignment.modal.StudentEntity;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, String> {
    List<StudentEntity> findByNameContainingIgnoreCase(String name);
    List<StudentEntity> findByEmailContainingIgnoreCase(String email);
    Boolean existsByEmail(String email);
    
    @Query("SELECT DISTINCT s FROM StudentEntity s " +
           "WHERE s.location.code = :provinceCode " +
           "OR s.location.parent.code = :provinceCode " +
           "OR s.location.parent.parent.code = :provinceCode " +
           "OR s.location.parent.parent.parent.code = :provinceCode " +
           "OR s.location.parent.parent.parent.parent.code = :provinceCode")
    List<StudentEntity> findByProvinceCode(@Param("provinceCode") String provinceCode);
    
    @Query("SELECT DISTINCT s FROM StudentEntity s " +
           "WHERE s.location.name = :provinceName " +
           "OR s.location.parent.name = :provinceName " +
           "OR s.location.parent.parent.name = :provinceName " +
           "OR s.location.parent.parent.parent.name = :provinceName " +
           "OR s.location.parent.parent.parent.parent.name = :provinceName")
    List<StudentEntity> findByProvinceName(@Param("provinceName") String provinceName);
    
    @Query("SELECT DISTINCT s FROM StudentEntity s " +
           "WHERE s.location.code = :locationCode " +
           "OR s.location.parent.code = :locationCode " +
           "OR s.location.parent.parent.code = :locationCode " +
           "OR s.location.parent.parent.parent.code = :locationCode " +
           "OR s.location.parent.parent.parent.parent.code = :locationCode")
    List<StudentEntity> findByLocationCode(@Param("locationCode") String locationCode);
}
