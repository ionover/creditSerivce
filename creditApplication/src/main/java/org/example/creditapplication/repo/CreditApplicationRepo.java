package org.example.creditapplication.repo;

import org.example.creditapplication.model.CreditApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditApplicationRepo extends JpaRepository<CreditApplicationEntity, Long> {
}
