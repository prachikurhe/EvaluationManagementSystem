package com.yash.ems.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yash.ems.model.Skill;



@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer> {

}
