package com.sapo.service;

import com.sapo.dto.KeyItem;
import com.sapo.model.Item;
import com.sapo.util.ItemThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapo.repository.ItemRepository;
import com.sapo.shopee.ShopeeApi;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {
	
	@Autowired
	ItemRepository itemRepository;
	@Autowired
	ShopeeApi shopeeApi;
	public List<Item> getRivals(List<KeyItem> keys) throws InterruptedException {
		List<Item> items = new ArrayList<>();
		List<KeyItem> keys1= keys.subList(0,keys.size()/8);
		List<KeyItem> keys2= keys.subList(keys.size()/8*1+1,keys.size()/8*2);
		List<KeyItem> keys3= keys.subList(keys.size()/8*2+1,keys.size()/8*3);
		List<KeyItem> keys4= keys.subList(keys.size()/8*3+1,keys.size()/8*4);
		List<KeyItem> keys5= keys.subList(keys.size()/8*4+1,keys.size()/8*5);
		List<KeyItem> keys6= keys.subList(keys.size()/8*5+1,keys.size()/8*6);
		List<KeyItem> keys7= keys.subList(keys.size()/8*6+1,keys.size()/8*7);
		List<KeyItem> keys8= keys.subList(keys.size()/8*7+1,keys.size()/8*8);
		ItemThread thread1 = new ItemThread(keys1,items);
		ItemThread thread2 = new ItemThread(keys2,items);
		ItemThread thread3 = new ItemThread(keys3,items);
		ItemThread thread4 = new ItemThread(keys4,items);
		ItemThread thread5 = new ItemThread(keys5,items);
		ItemThread thread6 = new ItemThread(keys6,items);
		ItemThread thread7 = new ItemThread(keys7,items);
		ItemThread thread8 = new ItemThread(keys8,items);
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
		return items;

	}
}
