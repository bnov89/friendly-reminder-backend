package com.archyle.fra.friendlyreminderbackend.output.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountEntity {
  @Id @GeneratedValue private Long id;
  private String username;
  private String password;
}
