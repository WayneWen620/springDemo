package com.example.demo.dao;

import com.example.demo.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    public List<Account> findByName(String name);
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
