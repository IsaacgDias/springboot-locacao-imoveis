package com.lcm.Locadora.repository;

import com.lcm.Locadora.model.Imovel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocadoraRepository extends JpaRepository<Imovel, Long> {
}
