package com.comba.android.combacommon.contacts;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenhailin on 2017/6/1.
 */

public class ContactsOperationUtil implements  IContactOperations<Contact> {
    private  Context cxt;
    private List<Contact> contacts=new ArrayList<Contact>();

    /**
     * 查询手机所有联系人
     * @return
     */
    @Override
    public List<Contact> querryAllContacts() {
        return getAllContacts();
    }

    /**
     * 更新联系人
     * @param contacts
     */
    @Override
    public void updateContacts(List<Contact> contacts) {
        updateContactsIps(contacts);
    }

    /**
     * 删除联系人
     * @param contacts
     */
    @Override
    public void deleteContacts(List<Contact> contacts) {
        deleteContactsWithName(contacts);
    }

    /**
     * 添加联系人
     * @param contacts
     */
    @Override
    public void insertContacts(List<Contact> contacts) {
        insertMultiContacts(contacts);
    }


    public ContactsOperationUtil(Context context) {
        cxt=context;
    }


    public List<Contact> getAllContacts(){
        contacts.clear();
        Uri uri = Uri.parse("content://com.android.contacts/contacts"); //访问raw_contacts表
        ContentResolver resolver = cxt.getContentResolver();
        //获得_id属性
        Cursor cursor = resolver.query(uri, new String[]{ContactsContract.RawContacts.Data._ID}, null, null, null);
        while(cursor.moveToNext()){
           Contact cat=new Contact();
            //获得id并且在data中寻找数据
            int id = cursor.getInt(0);
            cat.setRaw_id(id+"");
            uri = Uri.parse("content://com.android.contacts/contacts/"+id+"/data");
            //data1存储各个记录的总数据，mimetype存放记录的类型，如电话、email等
            Cursor cursor2 = resolver.query(uri, new String[]{ContactsContract.Contacts.Data.DATA1, ContactsContract.Contacts.Data.MIMETYPE}, null,null, null);
            while(cursor2.moveToNext()){
                String data = cursor2.getString(cursor2.getColumnIndex("data1"));
                if(cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/name")){       //如果是名字
                   cat.setName(data);
                }
                else if(cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/phone_v2")){  //如果是电话
                  cat.setPhoneNUm(data);
                }
                else if(cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/email_v2")){  //如果是email
                    cat.setEmial(data);
                }
                else if(cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/postal-address_v2")){ //如果是地址
                    cat.setAddress(data);
                }
                else if(cursor2.getString(cursor2.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/organization")){  //如果是组织
                    cat.setOrganisation(data);
                }
            }
            contacts.add(cat);
            cat=null;
        }
        return contacts;
    }


    public void updateContactsIps(List<Contact> contacts) {
                    for(Contact cat:contacts){
                        updateContactIp(cat);
                    }
    }


    public void updateContactIp(Contact contact ){
        int id = Integer.parseInt(contact.getRaw_id());
        ContentValues values= new ContentValues();;
        String mimetype="vnd.android.cursor.item/phone_v2";
                for(Field filed:Contact.class.getDeclaredFields()){
                    values.clear();
                  boolean isnull=  isPropertyNullHere(contact,filed.getName());
                    if(isnull){
                        continue;
                    }
                    if(filed.getName().equals("phoneNUm")){
                         mimetype="vnd.android.cursor.item/phone_v2";
                        values.put("data1", contact.getPhoneNUm());
                    }else if(filed.getName().equals("name")){
                       mimetype= "vnd.android.cursor.item/name";
                        values.put("data1", contact.getName());
                    }else if(filed.getName().equals("emial")){
                        mimetype= "vnd.android.cursor.item/email_v2";
                        values.put("data1", contact.getEmial());
                    }else if(filed.getName().equals("address")){
                        mimetype="vnd.android.cursor.item/postal-address_v2";
                        values.put("data1", contact.getAddress());
                    }else if(filed.getName().equals("photo")){
                        mimetype="vnd.android.cursor.item/photo";
                        values.put("data1", contact.getPhoto());
                    }else if(filed.getName().equals("organisation")){
                        mimetype="vnd.android.cursor.item/organization";
                        values.put("data1", contact.getOrganisation());
                    }

                    Uri uri = Uri.parse("content://com.android.contacts/data");//对data表的所有数据操作
                    ContentResolver resolver = cxt.getContentResolver();
                    resolver.update(uri, values, "mimetype=? and raw_contact_id=?", new String[]{mimetype,contact.getRaw_id()});
                }
    }



    private boolean isPropertyNullHere(Contact contact, String name) {
        try {
            name = name.substring(0,1).toUpperCase()+name.substring(1);
            Method getMethod= contact.getClass().getMethod("get"+name);
                 String valuses=(String) getMethod.invoke(contact);
            if(valuses!=null){
                return false;
            }else
                return true;
        } catch (Exception e) {
            e.printStackTrace();
            return  true;
        }
    }

    /**
     * delete single contact data
     * @param contact
     */
    private  void deleteItemWithName(Contact contact){
        String name = contact.getName();
        if(name==null){
            return;
        }
        //根据姓名求id
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        ContentResolver resolver = cxt.getContentResolver();
        Cursor cursor = resolver.query(uri, new String[]{ContactsContract.RawContacts.Data._ID},"display_name=?", new String[]{name}, null);
        if(cursor.moveToFirst()){
            int id = cursor.getInt(0);
            //根据id删除data中的相应数据
            resolver.delete(uri, "display_name=?", new String[]{name});
            uri = Uri.parse("content://com.android.contacts/data");
            resolver.delete(uri, "raw_contact_id=?", new String[]{id+""});
        }

    }

    /**
     * delete contacts
     * @param contacts
     */
      private void deleteContactsWithName(List<Contact> contacts){
        for(Contact cat :contacts){
            deleteItemWithName(cat);
        }
    }


    private  void insertOneContactData(Contact contact){
        //插入raw_contacts表，并获取_id属性
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        ContentResolver resolver = cxt.getContentResolver();
        ContentValues values = new ContentValues();
        long contact_id = ContentUris.parseId(resolver.insert(uri, values));
        //插入data表
        uri = Uri.parse("content://com.android.contacts/data");
        //add Name
        //往data表入姓名数据
        values.clear();
        values.put(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, contact_id);
        values.put(ContactsContract.RawContacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, contact.getName()==null?"":contact.getName());
        cxt.getContentResolver().insert(
                android.provider.ContactsContract.Data.CONTENT_URI, values);

        //往data表入电话数据
        values.clear();
        values.put(android.provider.ContactsContract.Contacts.Data.RAW_CONTACT_ID, contact_id);
        values.put(ContactsContract.RawContacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, contact.getPhoneNUm()==null?"":contact.getPhoneNUm());
        values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
       cxt.getContentResolver().insert(
                android.provider.ContactsContract.Data.CONTENT_URI, values);

        //往data表入Email数据
        values.clear();
        values.put(android.provider.ContactsContract.Contacts.Data.RAW_CONTACT_ID, contact_id);
        values.put(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.CommonDataKinds.Email.DATA, contact.getEmial()==null?"":contact.getEmial());
        values.put(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK);
        cxt.getContentResolver().insert(
                android.provider.ContactsContract.Data.CONTENT_URI, values);
    }



    private  void insertMultiContacts(List<Contact> contacts){
        for(Contact contact : contacts){
            insertOneContactData(contact);
        }
    }


}
