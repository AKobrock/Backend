package com.usuarios.Demo.repository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.usuarios.Demo.model.PapaModel;

@Repository
public interface IPapaModelRepository extends JpaRepository<PapaModel, UUID>{
    Optional<PapaModel> findByRut(String rut);
}
