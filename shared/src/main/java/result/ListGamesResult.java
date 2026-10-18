package result;

import model.GameData;

import java.util.List;
import java.util.Map;

public record ListGamesResult(List<ListGamesResultList> games) {
}
