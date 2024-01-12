package com.appsdeveloperblog.app.ws.io.dao.impl;

import com.appsdeveloperblog.app.ws.io.dao.DAO;
import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;
import com.appsdeveloperblog.app.ws.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.BeanUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/*
 *
 * @author: Sandeep prajapati
 *
 */
public class MySQLDAO implements DAO {

    Session session;

    @Override
    public void openConnection() {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        session = sessionFactory.openSession();
    }

    @Override
    public List<UserDTO> getUsers(int start, int limit) {
        CriteriaBuilder cb = session.getCriteriaBuilder();

        // Create Criteria against a particular class
        CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);

        // Query root always reference entities
        Root<UserEntity> profileRoot = criteria.from(UserEntity.class);
        criteria.select(profileRoot);

        // fetch results from start to a number of "limit"
        List<UserEntity> searchResults = session
                .createQuery(criteria)
                .setFirstResult(start)
                .setMaxResults(limit)
                .getResultList();

        List<UserDTO> returnValue = null;

        if(searchResults !=null && !searchResults.isEmpty()){
            returnValue = new ArrayList<>();
            for(UserEntity userEntity: searchResults){
                UserDTO userDTO = new UserDTO();
                BeanUtils.copyProperties(userEntity, userDTO);
                returnValue.add(userDTO);
            }
        }

        return returnValue;
    }

    @Override
    public UserDTO getUserByUserId(String userId) {
        UserDTO userDTO = null;

        CriteriaBuilder cb = session.getCriteriaBuilder();

        // Create Criteria against a particular class
        CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);

        // Query root always reference entities
        Root<UserEntity> profileRoot = criteria.from(UserEntity.class);
        criteria.select(profileRoot);
        criteria.where(cb.equal(profileRoot.get("userId"), userId));

        // fetch single result
        UserEntity userEntity = session.createQuery(criteria).getSingleResult();

        userDTO = new UserDTO();
        BeanUtils.copyProperties(userEntity, userDTO);
        return userDTO;
    }

    @Override
    public UserDTO getUserByUserName(String userName) {
        UserDTO userDTO = null;

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);

        Root<UserEntity> profileRoot = criteria.from(UserEntity.class);
        criteria.select(profileRoot);
        criteria.where(cb.equal(profileRoot.get("email"), userName));

        Query<UserEntity> query = session.createQuery(criteria);
        List<UserEntity> resultList = query.getResultList();

        if(resultList !=null && !resultList.isEmpty()){
            UserEntity userEntity = resultList.get(0);
            userDTO = new UserDTO();
            BeanUtils.copyProperties(userEntity, userDTO);
        }
        return userDTO;
    }

    @Override
    public UserDTO saveUser(UserDTO user) {
        UserDTO returnValue = null;

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);

        session.beginTransaction();
        session.save(userEntity);
        session.getTransaction().commit();

        returnValue = new UserDTO();
        BeanUtils.copyProperties(userEntity, returnValue);

        return returnValue;
    }

    @Override
    public void updateUser(UserDTO user) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        session.beginTransaction();
        session.update(userEntity);
        session.getTransaction().commit();
    }

    @Override
    public void closeConnection() {
        if(session != null){
            session.close();
        }
    }

}
