package com.medilaboSolutions.diabeteDetect.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.medilaboSolutions.diabeteDetect.modeles.Patient;

/**
 * Some Javadoc :
 * 
 * Repository inteface for Patient entities.
 * Extends JpaRepository to provide basic CRUD operations for BidList objects.
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer>{
}