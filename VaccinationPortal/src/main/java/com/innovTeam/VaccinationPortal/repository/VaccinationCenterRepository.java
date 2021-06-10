package com.innovTeam.VaccinationPortal.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.innovTeam.VaccinationPortal.models.Vaccination_Center;

@Repository
public interface VaccinationCenterRepository extends CrudRepository<Vaccination_Center, String>{

}
