package org.mateusz.domain.drawdate;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class InMemoryDrawDateRepositoryTestImpl implements DrawDateRepository {

    Map<String, DrawDate> inMemoryDatabase = new ConcurrentHashMap<>();

    @Override
    public <S extends DrawDate> S save(S entity) {
        inMemoryDatabase.put(entity.id(), entity);
        return entity;
    }

    @Override
    public List<DrawDate> findAll() {
        return new ArrayList<>(inMemoryDatabase.values());
    }

    @Override
    public Optional<DrawDate> findByDate(LocalDateTime date) {
        return inMemoryDatabase.values()
                .stream()
                .filter(drawDate -> drawDate.date().equals(date))
                .findFirst();
    }

    @Override
    public Optional<DrawDate> findFirstByOrderByNumberDesc() {
        return inMemoryDatabase.values().stream()
                .max(Comparator.comparing(DrawDate::number));
    }

    @Override
    public <S extends DrawDate> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<DrawDate> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<DrawDate> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(DrawDate entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends DrawDate> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends DrawDate> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends DrawDate> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends DrawDate> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends DrawDate> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends DrawDate> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends DrawDate> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends DrawDate> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends DrawDate> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends DrawDate, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<DrawDate> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<DrawDate> findAll(Pageable pageable) {
        return null;
    }
}
