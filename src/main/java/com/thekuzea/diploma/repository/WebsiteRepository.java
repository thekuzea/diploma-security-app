package com.thekuzea.diploma.repository;

import com.thekuzea.diploma.model.Website;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebsiteRepository extends MongoRepository<Website, String> {

}
