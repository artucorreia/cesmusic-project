package com.blog.cesmusic.mapper.implementations;

import com.blog.cesmusic.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Primary
public class ModelMapperImpl implements Mapper {
    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    @Override
    public <O, D> D map(O object, Class<D> destiny) {
        return MODEL_MAPPER.map(object, destiny);
    }

    @Override
    public <O, D> List<D> map(List<O> origin, Class<D> destiny) {
        List<D> destinyList = new ArrayList<>();
        for (O object : origin) {
            destinyList.add(MODEL_MAPPER.map(object, destiny));
        }
        return destinyList;
    }
}
