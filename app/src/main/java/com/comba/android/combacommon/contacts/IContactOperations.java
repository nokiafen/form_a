package com.comba.android.combacommon.contacts;

import java.util.List;

/**
 * Created by chenhailin on 2017/6/1.
 */

public interface IContactOperations<T> {

    /**
     * 查询所有联系人
     * @return allcontacts
     */
   List<T>  querryAllContacts();

    /**
     * 更新联系人信息
     * @param contacts
     */
    void updateContacts(List<T> contacts);

    /**
     * 删除联系人
     * @param contacts
     */
    void deleteContacts(List<T> contacts);

    /**
     * 添加联系人
     * @param contacts
     */
    void insertContacts(List<T> contacts);

}
