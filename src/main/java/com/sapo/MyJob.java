package com.sapo;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sapo.model.AutoPrice;
import com.sapo.model.Category;
import com.sapo.model.Item;
import com.sapo.model.ItemPrice;
import com.sapo.model.Rival;
import com.sapo.model.Shop;
import com.sapo.repository.AutoPriceRepository;
import com.sapo.repository.CategoryRepository;
import com.sapo.repository.ItemPriceRepository;
import com.sapo.repository.ItemRepository;
import com.sapo.repository.RivalRepository;
import com.sapo.repository.ShopRepository;
import com.sapo.shopee.ShopeeApi;

@Component
public class MyJob {
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	ShopeeApi shopeeApi;
	@Autowired
	RivalRepository rivalRepository;
	@Autowired
	ItemRepository itemRepository;
	@Autowired
	ShopRepository shopRepository;
	@Autowired
	ItemPriceRepository itemPriceRepository;
	@Autowired
	AutoPriceRepository autoPriceRepository;

	@Scheduled(cron = "0 12 */12 * * ?")
//	@Scheduled(cron = "0 */15 * * * ?")
	public void scheduleFixedDelayTask() throws Exception {
		List<Rival> rivals = rivalRepository.findAll();
		for (Rival rival : rivals) {
			Item item = shopeeApi.getItemDetailsV2(rival.getRivalItemid(), rival.getRivalShopid());
			Shop shop = shopeeApi.shopInfor(rival.getRivalShopid());
			if (item != null) {
				Set<Category> categories = item.getCategories();
				Set<Category> all = categoryRepository.findByItem(item);
				if (all != null) {
					for (Category category : all) {
						categoryRepository.delete(category);
					}
					for (Category category : categories) {
						category.setItem(item);
					}
				}
				List<ItemPrice> priceList = itemPriceRepository.findByItem(item);
				priceList.add(item.getItemPrice());
				item.setItemPrices(priceList);
				Item myItem = shopeeApi.getItemDetailsV2(rival.getItemid(), rival.getShopid());
				if (rival.isAuto()) {
					if (myItem.getPrice() + rival.getPrice() != item.getPrice()
							&& item.getPrice() - rival.getPrice() <= rival.getMax()
							&& rival.getMin() <= item.getPrice() - rival.getPrice()) {
						if (shopeeApi.updatePrice(myItem.getItemid(), myItem.getShopid(),
								item.getPrice() - rival.getPrice()) != null) {
							Calendar cal = Calendar.getInstance();
							AutoPrice auto = new AutoPrice(cal, item.getPrice() - rival.getPrice(), myItem.getPrice(),
									rival.getItemid(), rival.getRivalItemid());
							auto.setShopRival(shop.getName());
							autoPriceRepository.save(auto);
						}
					}
				}
				itemRepository.save(item);
			}
		}
		List<Shop> shops = shopRepository.findAll();
		for (Shop shop : shops) {
			List<Item> items = shopeeApi.getItemListV2(shop.getShopid());
			for (Item item : items) {
				Set<Category> categories = item.getCategories();
				Set<Category> all = categoryRepository.findByItem(item);
				if (all != null) {
					for (Category category : all) {
						categoryRepository.delete(category);
					}
					for (Category category : categories) {
						category.setItem(item);
					}
				}
				List<ItemPrice> priceList = itemPriceRepository.findByItem(item);
				priceList.add(item.getItemPrice());
				item.setItemPrices(priceList);
				itemRepository.save(item);
			}
		}
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		System.out.println("Task1 - " + date);
	}
}