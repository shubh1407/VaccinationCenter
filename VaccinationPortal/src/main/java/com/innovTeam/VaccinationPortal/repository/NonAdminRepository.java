package com.innovTeam.VaccinationPortal.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.innovTeam.VaccinationPortal.models.User;

@Repository
public interface NonAdminRepository extends CrudRepository<User, String> {

}
