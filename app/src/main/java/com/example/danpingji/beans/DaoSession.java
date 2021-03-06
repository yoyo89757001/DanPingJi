package com.example.danpingji.beans;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.example.danpingji.beans.BaoCunBean;
import com.example.danpingji.beans.DaYingXinXiBean;

import com.example.danpingji.beans.BaoCunBeanDao;
import com.example.danpingji.beans.DaYingXinXiBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig baoCunBeanDaoConfig;
    private final DaoConfig daYingXinXiBeanDaoConfig;

    private final BaoCunBeanDao baoCunBeanDao;
    private final DaYingXinXiBeanDao daYingXinXiBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        baoCunBeanDaoConfig = daoConfigMap.get(BaoCunBeanDao.class).clone();
        baoCunBeanDaoConfig.initIdentityScope(type);

        daYingXinXiBeanDaoConfig = daoConfigMap.get(DaYingXinXiBeanDao.class).clone();
        daYingXinXiBeanDaoConfig.initIdentityScope(type);

        baoCunBeanDao = new BaoCunBeanDao(baoCunBeanDaoConfig, this);
        daYingXinXiBeanDao = new DaYingXinXiBeanDao(daYingXinXiBeanDaoConfig, this);

        registerDao(BaoCunBean.class, baoCunBeanDao);
        registerDao(DaYingXinXiBean.class, daYingXinXiBeanDao);
    }
    
    public void clear() {
        baoCunBeanDaoConfig.clearIdentityScope();
        daYingXinXiBeanDaoConfig.clearIdentityScope();
    }

    public BaoCunBeanDao getBaoCunBeanDao() {
        return baoCunBeanDao;
    }

    public DaYingXinXiBeanDao getDaYingXinXiBeanDao() {
        return daYingXinXiBeanDao;
    }

}
