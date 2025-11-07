package com.example.demo.modules.account.dao;

import com.example.demo.modules.account.domain.Account;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> , JpaSpecificationExecutor<Account> {
    @EntityGraph(attributePaths = {"role", "role.authorities"})
    public Optional<Account> findByName(String name);
    public List<Account> findByNameAndGender(String name,String gender);
    public List<Account> findByTelephoneLike(String telephone);

    @Query("from Account where name = :name")
    public List<Account> queryName(@Param("name") String name);

    @Query(value = "select * from Account where name =:name",nativeQuery = true)
    public List<Account> queryName2(@Param("name") String name);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update Account set address =:address where id = :id ")
    public void updateAddressById(@Param("address") String address,@Param("id") Long id);
}
