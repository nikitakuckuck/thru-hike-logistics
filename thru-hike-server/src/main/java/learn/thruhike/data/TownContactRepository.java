package learn.thruhike.data;

import learn.thruhike.models.TownContact;

import java.util.List;

public interface TownContactRepository {
    List<TownContact> findAll();

    List<TownContact> findByTrailSection(int sectionId);

    TownContact findById(int id);

    TownContact add(TownContact contact);

    boolean update(TownContact contact);

    boolean deleteById(int id);
}
