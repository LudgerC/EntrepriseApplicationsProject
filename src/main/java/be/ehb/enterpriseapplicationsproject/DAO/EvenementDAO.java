package be.ehb.enterpriseapplicationsproject.DAO;

import be.ehb.enterpriseapplicationsproject.Model.Evenement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvenementDAO extends JpaRepository<Evenement, Long> {
    List<Evenement> findTop10ByOrderByIdDesc();
    List<Evenement> findAllByOrderByTitelAsc();
}