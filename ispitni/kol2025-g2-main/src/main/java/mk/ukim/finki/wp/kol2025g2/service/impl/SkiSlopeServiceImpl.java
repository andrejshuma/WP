package mk.ukim.finki.wp.kol2025g2.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.kol2025g2.model.SkiSlope;
import mk.ukim.finki.wp.kol2025g2.model.SlopeDifficulty;
import mk.ukim.finki.wp.kol2025g2.model.exceptions.InvalidSkiSlopeIdException;
import mk.ukim.finki.wp.kol2025g2.repository.SkiSlopeRepository;
import mk.ukim.finki.wp.kol2025g2.service.SkiResortService;
import mk.ukim.finki.wp.kol2025g2.service.SkiSlopeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static mk.ukim.finki.wp.kol2025g2.service.FieldFilterSpecification.*;

@Service
@AllArgsConstructor
public class SkiSlopeServiceImpl implements SkiSlopeService {
    private final SkiSlopeRepository skiSlopeRepository;
    private final SkiResortService skiResortService;
    @Override
    public List<SkiSlope> listAll() {
        return skiSlopeRepository.findAll();
    }

    @Override
    public SkiSlope findById(Long id) {
        return skiSlopeRepository.findById(id).orElseThrow(InvalidSkiSlopeIdException::new);
    }

    @Override
    public SkiSlope create(String name, Integer length, SlopeDifficulty difficulty, Long skiResort) {
        SkiSlope skiSlope = new SkiSlope(name,length,difficulty,skiResortService.findById(skiResort));
        skiSlopeRepository.save(skiSlope);
        return skiSlope;
    }

    @Override
    public SkiSlope update(Long id, String name, Integer length, SlopeDifficulty difficulty, Long skiResort) {
        SkiSlope byId = findById(id);
        byId.setName(name);
        byId.setLength(length);
        byId.setDifficulty(difficulty);
        byId.setSkiResort(skiResortService.findById(skiResort));
        skiSlopeRepository.save(byId);
        return byId;
    }

    @Override
    public SkiSlope delete(Long id) {
        SkiSlope byId = findById(id);
        skiSlopeRepository.delete(byId);
        return byId;
    }

    @Override
    public SkiSlope close(Long id) {
        SkiSlope byId = findById(id);
        byId.setClosed(true);
        skiSlopeRepository.save(byId);
        return byId;
    }

    @Override
    public Page<SkiSlope> findPage(String name, Integer length, SlopeDifficulty difficulty, Long skiResort, int pageNum, int pageSize) {
        Specification<SkiSlope> specification = Specification.allOf(
                filterContainsText(SkiSlope.class, "name", name),
                greaterThan(SkiSlope.class, "length", length),
                filterEqualsV(SkiSlope.class, "difficulty", difficulty),
                filterEquals(SkiSlope.class, "skiResort.id", skiResort)
        );

        return this.skiSlopeRepository.findAll(
                specification,
                PageRequest.of(pageNum, pageSize));
    }
}
