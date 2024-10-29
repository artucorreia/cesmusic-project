package com.blog.cesmusic.mapper;

import java.util.List;

public interface Mapper {
    <O, D> D map(O origin, Class<D> destiny);

    <O, D> List<D> map(List<O> origin, Class<D> destiny);
}
