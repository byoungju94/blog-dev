package com.byoungju94.blog.post;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, UUID>, PostRepositoryCustom {
    
    @Override
    void deleteById(UUID id);

    @Override
    void delete(Post entity);

    @Override
    void deleteAll(Iterable<? extends Post> entities);

    Optional<Post> findByIdAndStateIn(UUID uuid, Set<PostState> states);

    default Optional<Post> findByIdExcludeDeleted(UUID id) {
        return this.findByIdAndStateIn(id, EnumSet.of(PostState.ACTIVE, PostState.LOCKED));
    }
}
