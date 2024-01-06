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
    public void closeConnection() {
        if(session != null){
            session.close();
        }
    }


}
