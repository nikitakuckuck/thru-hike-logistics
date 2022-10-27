package learn.thruhike.data;

import learn.thruhike.models.TrailSection;

import java.util.List;

public interface TrailSectionRepository {
    List<TrailSection> findAll();

    List<TrailSection> findAllUpcoming();

    List<TrailSection> findByTrailId(int id);

    TrailSection findActive();

    TrailSection findById(int id);

    TrailSection findBySectionNickname(String nickname);

    TrailSection add(TrailSection section);

    boolean update(TrailSection section);

    boolean deleteById(int id);
}
