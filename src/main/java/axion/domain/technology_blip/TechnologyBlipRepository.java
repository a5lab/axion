package axion.domain.technology_blip;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnologyBlipRepository extends JpaRepository<TechnologyBlip, Long> {
}
