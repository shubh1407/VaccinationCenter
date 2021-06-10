package com.innovTeam.VaccinationPortal.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.innovTeam.VaccinationPortal.models.Admin;

@Repository
public interface AdminRepository extends CrudRepository<Admin, String>{

}
