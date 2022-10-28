package learn.thruhike.data;

import learn.thruhike.models.SectionAlert;

import java.util.List;

public interface SectionAlertRepository {
    List<SectionAlert> findAll();

    List<SectionAlert> findByTrailSectionId(int id);

    SectionAlert findById(int id);

    SectionAlert add(SectionAlert sectionAlert);
}
