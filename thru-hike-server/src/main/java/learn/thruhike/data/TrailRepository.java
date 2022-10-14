package learn.thruhike.data;

import learn.thruhike.models.Trail;

import java.util.List;

public interface TrailRepository {
    List<Trail> findAll();

    Trail findById(int id);

    Trail add(Trail trail);

    boolean update(Trail trail);

    boolean deleteById(int id);
}
