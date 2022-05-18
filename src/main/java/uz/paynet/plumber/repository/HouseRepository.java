package uz.paynet.plumber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.paynet.plumber.entity.House;

public interface HouseRepository extends JpaRepository<House, Long> {
}