package com.mediaSolutions.authentication.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mediaSolutions.authentication.model.User;

/**
 * Some javadoc.
 * 
 * Repository interface for managing User entities.
 * This interface provides CRUD (Create, Read, Update, Delete) operations for
 * User objects.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  /**
   * Some javadoc.
   * 
   * Get a "user" entity from this id.
   * 
   * @param id represent the identifier of user.
   * 
   * @return "user" entity.
   */
  User findById(int id);

  /**
   * Some javadoc.
   * 
   * Get a list of "user" entity from a list of "id".
   * 
   * @param ids list of identifiers.
   * 
   * @return List<User> the "user" entity list.
   */
  List<User> findAllByIdIn(List<Integer> ids);

  /**
   * Some javadoc.
   * 
   * Get a specific "user" entity from this email.
   * 
   * @param mail of the user.
   * 
   * @return the "user" entity.
   */
  User findByMail(String mail);

  /**
   * Some javadoc.
   * 
   * Update the user information with new balance valuer and a specific user id.
   * 
   * @param userId     is the identifier of the user.
   * @param newBalance is the new value of the user balance.
   * 
   */
  @Transactional
  @Modifying
  @Query("UPDATE User u SET u.bankBalance = :newBalance WHERE u.id = :userId")
  void updateBankBalance(@Param("userId") int userId, @Param("newBalance") BigDecimal newBalance);
}