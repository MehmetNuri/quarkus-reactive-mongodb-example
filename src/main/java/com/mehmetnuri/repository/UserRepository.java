package com.mehmetnuri.repository;

import com.mehmetnuri.entity.User;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.regex.Pattern;

@ApplicationScoped
public class UserRepository implements ReactivePanacheMongoRepository<User> {

    private static final Logger LOGGER = Logger.getLogger(UserRepository.class);

    public Uni<List<User>> findByName(String name) {
        Pattern pattern = Pattern.compile(name, Pattern.CASE_INSENSITIVE);
        LOGGER.info("Executing findByName with regex pattern: " + pattern.pattern());
        return list("{'name': {$regex: ?1}}", pattern.pattern());
    }

    public Uni<List<User>> findByAge(int age) {
        return list("{'age': ?1}", age);
    }

    public Uni<List<User>> findByEmailDomain(String domain) {
        Pattern pattern = Pattern.compile(domain, Pattern.CASE_INSENSITIVE);
        return list("{'email': {$regex: ?1}}", pattern.pattern());
    }
}
