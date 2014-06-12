package com.amos.android_db.test;

import android.test.AndroidTestCase;
import android.util.Log;
import com.amos.android_db.dao.Person;
import com.amos.android_db.dao.PersonDao;

import java.util.List;

/**
 * Created by amosli on 14-6-13.
 */
public class TestPersonDao extends AndroidTestCase{

        public void testAdd() throws Exception{
            PersonDao personDao = new PersonDao(this.getContext());
            personDao.add("amosli",10);
            personDao.add("amosli",10);
            for(int i=0;i<10;i++){
                personDao.add("amos"+i,10+i);
            }

        }
        public void testUpdate() throws Exception{
            PersonDao personDao = new PersonDao(this.getContext());
            personDao.update("amos0","0amos",35);

        }
        public void testDelete() throws Exception{
            PersonDao personDao = new PersonDao(this.getContext());
            personDao.delete("amosli");
        }
        public void testFind() throws Exception{
            PersonDao personDao = new PersonDao(this.getContext());
            assertEquals(true,personDao.find("amos1"));
        }
        public void testFindAll() throws Exception{
            PersonDao personDao = new PersonDao(getContext());
            List<Person> personList = personDao.findAll();
            for(Person person:personList){
                Log.d("person:",person.toString());
            }

        }

        public void testTransaction() throws Exception{
            PersonDao personDao = new PersonDao(getContext());
            personDao.transferMoney();
        }



}
