package com.example.demo.dao;

import com.example.demo.modules.account.dao.AccountRepository;
import com.example.demo.modules.account.domain.Account;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void testSaveAndFind() {
        // 寫測試邏輯
        Account account =new Account();
        account.setName("Steven");
        account.setGender("男");
        account.setTelephone("0911111111");
        account.setAddress("New Taipei");
        accountRepository.save(account);

        Account found = accountRepository.findById(account.getId()).orElse(null);
        assert found != null;
        assert found.getName().equals("Steven");
    }

//    @Test
//    public void findByName() {
//        // 寫測試邏輯
//        Account account =new Account();
//        account.setName("Steven");
//        account.setGender("男");
//        account.setTelephone("0911111111");
//        account.setAddress("New Taipei");
//        accountRepository.save(account);
//
//        Account found = accountRepository.findByName("Steven").orElse(new Account());
////        assert found != null;
//        assert found.getName().equals("Steven");
//    }

    @Test
    public void findByNameAndGender() {
        // 寫測試邏輯
        Account account =new Account();
        account.setName("Steven");
        account.setGender("男");
        account.setTelephone("0911111111");
        account.setAddress("New Taipei");
        accountRepository.save(account);

        List<Account> found = accountRepository.findByNameAndGender("Steven","男");
        assert found != null;
        System.out.println(found.get(0).getName());
        assert found.get(0).getName().equals("Steven");
    }

    @Test
    public void findByTelephoneLike() {
        // 寫測試邏輯
        Account account =new Account();
        account.setName("Steven");
        account.setGender("男");
        account.setTelephone("0911111111");
        account.setAddress("New Taipei");
        accountRepository.save(account);

        List<Account> found = accountRepository.findByTelephoneLike("%111%");
        assert found != null;
        System.out.println(found.get(0).getName()+":tel:"+found.get(0).getTelephone());
        assert found.get(0).getName().equals("Steven");
    }

    @Test
    public void queryName() {
        // 寫測試邏輯
        Account account =new Account();
        account.setName("Steven");
        account.setGender("男");
        account.setTelephone("0911111111");
        account.setAddress("New Taipei");
        accountRepository.save(account);

        List<Account> found = accountRepository.queryName("Steven");
        assert found != null;
        System.out.println(found.get(0).getName()+":tel:"+found.get(0).getTelephone());
        assert found.get(0).getName().equals("Steven");
    }

    @Test
    public void queryName2() {
        // 寫測試邏輯
        Account account =new Account();
        account.setName("Steven");
        account.setGender("男");
        account.setTelephone("0911111111");
        account.setAddress("New Taipei");
        accountRepository.save(account);

        List<Account> found = accountRepository.queryName2("Steven");
        assert found != null;
        System.out.println(found.get(0).getName()+":tel:"+found.get(0).getTelephone());
        assert found.get(0).getName().equals("Steven");
    }

    @Test
    @Transactional
    public void updateAddressById() {
        // 寫測試邏輯
        Account account =new Account();
        account.setName("Steven");
        account.setGender("男");
        account.setTelephone("0911111111");
        account.setAddress("New Taipei");
        Long id = accountRepository.save(account).getId();

        accountRepository.updateAddressById("Taipei",id);
        // 強制 flush session 確保資料已更新
        accountRepository.flush();

        Account found = accountRepository.findById(account.getId()).orElse(null);
        assert found != null;
        System.out.println(found.getName()+":tel:"+found.getTelephone()+":Addr:"+found.getAddress());
        assert found.getAddress().equals("Taipei");
    }

    @Test
    public void testSort() {
        // 寫測試邏輯
        Account account =new Account();
        account.setName("Steven");
        account.setGender("男");
        account.setTelephone("0911111111");
        account.setAddress("New Taipei");
        accountRepository.save(account).getId();
        account =new Account();
        account.setName("Jack");
        account.setGender("男");
        account.setTelephone("0911111111");
        account.setAddress("New Taipei");
        accountRepository.save(account).getId();

        Sort sort = Sort.by(Order.desc("id"));
        List<Account> accounts = accountRepository.findAll(Sort.by(Order.desc("id")));
        for(Account acct:accounts){
            System.out.println(acct.getId()+":name:"+acct.getName());
        }
// 驗證 list 不為空
        assertNotNull(accounts);
        assertEquals(2, accounts.size());

        // 驗證第一筆 id 大於第二筆
        assertTrue(accounts.get(0).getId() > accounts.get(1).getId());

        // 驗證資料名稱對應
        assertEquals("Jack", accounts.get(0).getName());
        assertEquals("Steven", accounts.get(1).getName());

        // 印出結果（可選）
        accounts.forEach(acct ->
                System.out.println(acct.getId() + ": name: " + acct.getName())
        );
    }

    @Test
    public void testPage() {
        // 寫測試邏輯
        Account account =new Account();
        account.setName("Steven");
        account.setGender("男");
        account.setTelephone("0911111111");
        account.setAddress("New Taipei");
        accountRepository.save(account).getId();
        account =new Account();
        account.setName("Jack");
        account.setGender("男");
        account.setTelephone("0911111111");
        account.setAddress("New Taipei");
        accountRepository.save(account).getId();

        // 設定分頁：pageIndex = 0, pageSize = 1, 降序排序
        Pageable pageable = PageRequest.of(0, 1, Sort.by(Sort.Order.desc("id")));

        // 查詢分頁資料
        Page<Account> page = accountRepository.findAll(pageable);

        // 驗證分頁資訊
        assertNotNull(page);
        assertEquals(2, page.getTotalElements());  // 總筆數
        assertEquals(2, page.getTotalPages());    // 總頁數
        assertEquals(1, page.getContent().size()); // 當前頁筆數

        // 印出當前頁資料
        page.getContent().forEach(acct ->
                System.out.println(acct.getId() + ": name:" + acct.getName())
        );

        // 驗證排序
        assertEquals("Jack", page.getContent().get(0).getName()); // id 最大的應該是 Jack

    }
    //增加繼承 JpaSpecificationExecutor 一個條件
    @Test
    @Transactional
    public void testFindAllJPASpecificationExecutor() {
        // 寫測試邏輯
        Account account =new Account();
        account.setName("Steven");
        account.setGender("男");
        account.setTelephone("0911111111");
        account.setAddress("New Taipei");
       accountRepository.save(account);
        account =new Account();
        account.setName("Jack");
        account.setGender("男");
        account.setTelephone("0911111111");
        account.setAddress("New Taipei");
        accountRepository.save(account);
        accountRepository.flush();
        //Specification 用於封裝條件數據的對象
        Specification<Account> spec = new Specification<Account>() {
            //Predicate 用於封裝條件

            /**
             *
             * @param root              根對象,用於查詢對象的屬性
             * @param query             執行普通的查詢
             * @param criteriaBuilder   查詢條件構造器,用於完成不同條件的查詢
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                /**
                 * 參數 1:查詢的屬性(需要使用root進行查詢)
                 * 參數 2 條件值
                 */
                Predicate pre = criteriaBuilder.equal(root.get("name"),"Jack");

                return pre;
            }
        };
        List<Account> list=accountRepository.findAll(spec);
        for(Account acc: list){
            System.out.println("acc:"+acc.getId()+"_"+acc.getName());
        }
    }
    //增加繼承 JpaSpecificationExecutor 多個條件
    @Test
    @Transactional
    public void testFindAllJPASpecificationExecutor2() {
        // 寫測試邏輯
        Account account =new Account();
        account.setName("Steven");
        account.setGender("男");
        account.setTelephone("0911111111");
        account.setAddress("New Taipei");
        accountRepository.save(account);
        account =new Account();
        account.setName("Jack");
        account.setGender("男");
        account.setTelephone("0911111111");
        account.setAddress("New Taipei");
        accountRepository.save(account);
        accountRepository.flush();
        //Specification 用於封裝條件數據的對象
        Specification<Account> spec = new Specification<Account>() {
            //Predicate 用於封裝條件

            /**
             *
             * @param root              根對象,用於查詢對象的屬性
             * @param query             執行普通的查詢
             * @param criteriaBuilder   查詢條件構造器,用於完成不同條件的查詢
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                List<Predicate> preList = new ArrayList<Predicate>();
                preList.add(criteriaBuilder.equal(root.get("name"),"Jack"));
                preList.add(criteriaBuilder.equal(root.get("gender"),"男"));
                Predicate[] preArray = new Predicate[preList.size()];
                return criteriaBuilder.and(preList.toArray(preArray));
            }
        };
        List<Account> list=accountRepository.findAll(spec);
        for(Account acc: list){
            System.out.println("acc:"+acc.getId()+"_"+acc.getName());
        }
    }
}