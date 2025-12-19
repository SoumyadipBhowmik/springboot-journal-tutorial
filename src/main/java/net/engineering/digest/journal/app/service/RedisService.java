package net.engineering.digest.journal.app.service;

public interface RedisService {

    <T> T get(String key, Class<T> entityClass);

    void set(String key, Object o, Long ttl);
}
