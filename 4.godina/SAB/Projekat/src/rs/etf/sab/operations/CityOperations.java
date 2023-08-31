package rs.etf.sab.operations;

import java.util.List;

public interface CityOperations {
    int	connectCities(int cityId1, int cityId2, int distance);
    int	createCity(String name);
    List<Integer> getCities();
    List<Integer>	getConnectedCities(int cityId);
    List<Integer>	getShops(int cityId);
}
