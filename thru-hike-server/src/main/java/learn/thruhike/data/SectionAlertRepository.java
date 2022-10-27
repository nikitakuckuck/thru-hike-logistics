package learn.thruhike.data;

import learn.thruhike.models.SectionAlert;

import java.util.List;

public interface SectionAlertRepository {
    List<SectionAlert> findAll();
}
