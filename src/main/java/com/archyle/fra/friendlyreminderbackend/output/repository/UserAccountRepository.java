package com.archyle.fra.friendlyreminderbackend.output.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends CrudRepository<UserAccountEntity, Long> {
  Optional<UserAccountEntity> findByUsernameAndPassword(String username, String password);
  Optional<UserAccountEntity> findByUsername(String username);
  Optional<UserAccountEntity> findByUserAccountNumber(String userAccountNumber);

}
