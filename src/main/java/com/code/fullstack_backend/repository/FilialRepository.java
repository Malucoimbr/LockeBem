package com.code.fullstack_backend.repository;

import com.code.fullstack_backend.model.Filial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilialRepository extends JpaRepository<Filial, Long> {
    boolean existsByCodigoFilial(String codigoFilial);
}
