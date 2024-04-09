package pogodynka.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TrackedLocations {
    private  List<Location> locations;

    public TrackedLocations() {
        locations = new ArrayList<>();
    }


    public List<Location> getLocations() {
        return this.locations;
    }

    public List<Location> addLocation(Location loc) {
        UUID id = loc.getId();
        if (id == null)
            id = UUID.randomUUID();
        loc.setId(id);
        this.locations.add(loc);
        return this.locations;
    }


    public List<Location> removeLocation(UUID id) {
        this.locations.removeIf(location -> location.getId().equals(id));
        return this.locations;
    }


    public List<Location> removeLocation(String name) {
        List<Location> updatedLocations = new ArrayList<>(this.locations);
        updatedLocations.removeIf(location -> location.getCity().equals(name));
        this.locations = updatedLocations;
        return updatedLocations;
    }
}