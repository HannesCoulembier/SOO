package entity;

import java.util.Collection;
import java.util.HashSet;

public class Inventory {

    Collection<Entity> detectedEntities = null;

    private static final Inventory instance = new Inventory();

    private Inventory() {
        detectedEntities = new HashSet<>();
    }

    public boolean addEntity(Entity entity) {
        return this.detectedEntities.add(entity);
    }

    public Collection<Entity> getDetectedEntities() {
        return detectedEntities;
    }

    public static Inventory getInstance() {
        return instance;
    }
}
