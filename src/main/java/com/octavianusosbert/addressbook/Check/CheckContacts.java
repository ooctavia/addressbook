package com.octavianusosbert.addressbook.Check;

import com.octavianusosbert.addressbook.Contacts;

public class CheckContacts {
    public boolean check(Contacts contact) {
        CheckPhoneNumber checkNum = new CheckPhoneNumber();
        if(checkNum.check(contact.getPhone_number())) {
            return true;
        }
        return false;
    }
}
