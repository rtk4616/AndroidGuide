package com.ssyijiu.criminalintent.bean;

import com.ssyijiu.common.log.MLog;
import com.ssyijiu.common.util.ToastUtil;
import com.ssyijiu.criminalintent.util.RealmUtil;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by ssyijiu on 2017/4/21.
 * Github: ssyijiu
 * E-mail: lxmyijiu@163.com
 */

public class CrimeLab {

    private static final CrimeLab INSTANCE = new CrimeLab();

    private Realm realm;


    public static CrimeLab instance() {
        return INSTANCE;
    }


    private CrimeLab() {
        realm = RealmUtil.getRealm();
    }


    /**
     * 查询所有
     */
    public RealmResults<Crime> getAllCrimes() {
        return realm.where(Crime.class).findAll();
    }


    /**
     * 根据 id 查询 Item
     */
    public Crime getCrime(String id) {
        return realm.where(Crime.class).equalTo("id", id).findFirst();
    }


    /**
     * 插入 or 更新
     */
    public void insertOrUpdateCrime(final Crime crime) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(crime);
            }
        });
    }


    /**
     * 删除 crime
     */
    public void deleteCrime(final String id) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override public void execute(Realm realm) {
                getCrime(id).deleteFromRealm();
            }
        });
    }


    public void gc() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override public void execute(Realm realm) {
                RealmResults<Crime> allCrimes = getAllCrimes();
                for (Crime crime : allCrimes.createSnapshot()) {
                    if(crime.couldDelete()) {
                        getCrime(crime.id).deleteFromRealm();
                    }
                }
            }
        });
    }


    public int size() {
        return getAllCrimes().size();
    }

    private class RealmAsyncSuccess implements Realm.Transaction.OnSuccess {
        String message;
        RealmAsyncSuccess(String message) {
            this.message = message;
        }

        @Override public void onSuccess() {
            ToastUtil.show(this.message);
        }
    }


    private class RealmAsyncError implements Realm.Transaction.OnError {
        @Override public void onError(Throwable error) {
            MLog.e("delete error!",error);
            ToastUtil.show(error.getMessage());
        }
    }
}
