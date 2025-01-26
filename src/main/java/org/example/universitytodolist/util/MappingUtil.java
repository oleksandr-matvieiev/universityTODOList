package org.example.universitytodolist.util;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MappingUtil {

    public static <T, ID> List<ID> mapEntitiesToIds(List<T> entities, Function<T, ID> idExtractor) {
        if (entities == null || entities.isEmpty()) return List.of();

        return entities.stream().map(idExtractor).collect(Collectors.toList());
    }

    public static <ID, T> List<T> mapIdsToEntities(List<ID> ids, Function<List<ID>, List<T>> finder) {
        if (ids == null || ids.isEmpty()) return List.of();

        return finder.apply(ids);
    }
}
