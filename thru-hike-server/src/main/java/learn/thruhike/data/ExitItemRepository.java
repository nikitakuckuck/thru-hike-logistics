package learn.thruhike.data;

import learn.thruhike.models.ExitItem;

import java.util.List;

public interface ExitItemRepository {
    List<ExitItem> findAll();

    List<ExitItem> findAllIncomplete();

    ExitItem findById(int id);

    ExitItem add(ExitItem townExit);

    boolean update(ExitItem townExit);

    boolean deleteById(int id);
}
