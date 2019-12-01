package com.sapo.service;

import com.sapo.dto.KeyItem;
import com.sapo.model.Item;
import com.sapo.model.Shop;
import com.sapo.shopee.ShopeeApi;
import com.sapo.util.ItemThread;
import com.sapo.util.ShopThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopService {
    @Autowired
    ShopeeApi shopeeApi;
    public List<Shop> getRivals(List<KeyItem> keys) throws InterruptedException {
        List<Shop> shops = new ArrayList<>();
        List<KeyItem> keys1= keys.subList(0,keys.size()/8);
        List<KeyItem> keys2= keys.subList(keys.size()/8*1+1,keys.size()/8*2);
        List<KeyItem> keys3= keys.subList(keys.size()/8*2+1,keys.size()/8*3);
        List<KeyItem> keys4= keys.subList(keys.size()/8*3+1,keys.size()/8*4);
        List<KeyItem> keys5= keys.subList(keys.size()/8*4+1,keys.size()/8*5);
        List<KeyItem> keys6= keys.subList(keys.size()/8*5+1,keys.size()/8*6);
        List<KeyItem> keys7= keys.subList(keys.size()/8*6+1,keys.size()/8*7);
        List<KeyItem> keys8= keys.subList(keys.size()/8*7+1,keys.size()/8*8);
        ShopThread thread1 = new ShopThread(keys1,shops);
        ShopThread thread2 = new ShopThread(keys2,shops);
        ShopThread thread3 = new ShopThread(keys3,shops);
        ShopThread thread4 = new ShopThread(keys4,shops);
        ShopThread thread5 = new ShopThread(keys5,shops);
        ShopThread thread6 = new ShopThread(keys6,shops);
        ShopThread thread7 = new ShopThread(keys7,shops);
        ShopThread thread8 = new ShopThread(keys8,shops);
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();
        thread7.start();
        thread8.start();
        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();
        thread5.join();
        thread6.join();
        thread7.join();
        thread8.join();
        return shops;

    }

}
