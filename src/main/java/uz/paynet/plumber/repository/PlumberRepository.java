package uz.paynet.plumber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.paynet.plumber.entity.Plumber;

public interface PlumberRepository extends JpaRepository<Plumber, Long> {
}