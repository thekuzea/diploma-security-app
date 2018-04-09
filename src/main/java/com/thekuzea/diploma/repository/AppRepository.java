package com.thekuzea.diploma.repository;

import com.thekuzea.diploma.model.App;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppRepository extends MongoRepository<App, String> {

}
