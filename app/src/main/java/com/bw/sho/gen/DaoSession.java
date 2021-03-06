package com.bw.sho.gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.bw.sho.bean.FindCarResclt;

import com.bw.sho.gen.FindCarRescltDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig findCarRescltDaoConfig;

    private final FindCarRescltDao findCarRescltDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        findCarRescltDaoConfig = daoConfigMap.get(FindCarRescltDao.class).clone();
        findCarRescltDaoConfig.initIdentityScope(type);

        findCarRescltDao = new FindCarRescltDao(findCarRescltDaoConfig, this);

        registerDao(FindCarResclt.class, findCarRescltDao);
    }
    
    public void clear() {
        findCarRescltDaoConfig.getIdentityScope().clear();
    }

    public FindCarRescltDao getFindCarRescltDao() {
        return findCarRescltDao;
    }

}
