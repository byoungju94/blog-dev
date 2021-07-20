package com.byoungju94.blog.account;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, UUID>, AccountRepositoryCustom {

    @Override
    void deleteById(UUID id);

    @Override
    void delete(Account entity);

    @Override
    void deleteAll(Iterable<? extends Account> entities);

    @Override
    void deleteAll();

    Optional<Account> findByIdAndStateIn(UUID uuid, Set<AccountState> states);

    default Optional<Account> findByIdExcludeDeleted(UUID id) {
        return this.findByIdAndStateIn(id, EnumSet.of(AccountState.ACTIVE, AccountState.LOCKED));
    }
}
