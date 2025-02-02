package com.blackcompany.eeos.program.persistence;

import com.blackcompany.eeos.program.application.model.ProgramAttendMode;
import java.sql.Timestamp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProgramRepository extends JpaRepository<ProgramEntity, Long> {

	@Query(
			"SELECT p FROM ProgramEntity p WHERE p.programDate < :now AND p.programCategory =:category AND p.isDeleted=false ORDER BY p.programDate DESC, p.title ASC ")
	Page<ProgramEntity> findAllByEndAndCategory(
			@Param("category") ProgramCategory category, @Param("now") Timestamp now, Pageable pageable);

	@Query(
			"SELECT p FROM ProgramEntity p WHERE p.programDate >= :now AND p.programCategory =:category AND p.isDeleted=false ORDER BY p.programDate ASC, p.title ASC")
	Page<ProgramEntity> findAllByIngAndCategory(
			@Param("category") ProgramCategory category, @Param("now") Timestamp now, Pageable pageable);

	@Query(
			"SELECT p FROM ProgramEntity p WHERE p.programDate < :now AND p.isDeleted=false ORDER BY p.programDate DESC, p.title ASC ")
	Page<ProgramEntity> findAllByEnd(@Param("now") Timestamp now, Pageable pageable);

	@Query(
			"SELECT p FROM ProgramEntity p WHERE p.programDate >=:now AND p.isDeleted=false ORDER BY p.programDate DESC, p.title ASC ")
	Page<ProgramEntity> findAllByIng(@Param("now") Timestamp now, Pageable pageable);

	@Modifying
	@Query("UPDATE ProgramEntity p SET p.attendMode=:attendMode WHERE p.id=:programId")
	Integer changeAttendMode(
			@Param("programId") Long programId, @Param("attendMode") ProgramAttendMode attendMode);
}
