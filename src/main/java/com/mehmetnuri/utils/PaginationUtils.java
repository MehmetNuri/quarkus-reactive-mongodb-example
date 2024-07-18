package com.mehmetnuri.utils;

import com.mehmetnuri.common.PageResult;
import io.smallrye.mutiny.Uni;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PaginationUtils {

    public static <T, R> Uni<PageResult<R>> createPageResult(Uni<Long> countUni, Uni<List<T>> itemsUni,
                                                             Function<T, R> mapper, int page, int size) {
        return Uni.combine().all().unis(countUni, itemsUni).asTuple()
                .map(tuple -> {
                    long totalItems = tuple.getItem1();
                    List<R> items = tuple.getItem2().stream()
                            .map(mapper)
                            .collect(Collectors.toList());
                    int totalPages = (int) Math.ceil((double) totalItems / size);
                    return new PageResult<>(items, totalItems, totalPages, page);
                });
    }

}
