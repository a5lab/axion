package axion.domain.radar_type;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RadarTypeService {

  Collection<RadarType> findAll();

  Page<RadarType> findAll(Pageable pageable);

  Optional<RadarType> findById(Long id);
}
