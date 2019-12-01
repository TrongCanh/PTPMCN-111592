package com.sapo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.sapo.service.ItemService;
import com.sapo.util.ItemThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sapo.dto.KeyItem;
import com.sapo.dto.ListRange;
import com.sapo.dto.Range;
import com.sapo.model.AutoPrice;
import com.sapo.model.Category;
import com.sapo.model.Item;
import com.sapo.model.ItemPrice;
import com.sapo.model.ItemRival;
import com.sapo.model.Rival;
import com.sapo.model.Shop;
import com.sapo.repository.AutoPriceRepository;
import com.sapo.repository.CategoryRepository;
import com.sapo.repository.ItemPriceRepository;
import com.sapo.repository.ItemRepository;
import com.sapo.repository.RivalRepository;
import com.sapo.repository.ShopRepository;
import com.sapo.shopee.ShopeeApi;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ItemController {
	@Autowired
	ShopeeApi shopeeApi;
	@Autowired
	ItemService itemService;
	@Autowired
	ItemRepository itemRepository;
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	ItemPriceRepository itemPriceRepository;
	@Autowired
	RivalRepository rivalRepository;
	@Autowired
	ShopRepository shopRepository;
	@Autowired
	AutoPriceRepository autoPriceRepository;

	@PutMapping("/getItems/{shop_id}")
	public List<Item> putItems(@PathVariable("shop_id") Long shopid) throws Exception {
		List<Item> items = shopeeApi.getItemListV2(shopid);
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
//			Set<ItemPrice> priceList = itemPriceRepository.findByItem(item);
//			priceList.add(item.getItemPrice());
//			item.setItemPrices(priceList);
			itemRepository.save(item);
		}
		return items;
	}

	@GetMapping("/getItems/{shop_id}")
	public List<Item> getItems(@PathVariable("shop_id") Long shopid) {
		List<Item> items = itemRepository.findByShopid(shopid);
		return items;
	}

	@GetMapping("/item/{shopid}/{itemid}")
	public Item getItem(@PathVariable("shopid") Long shopid, @PathVariable("itemid") Long itemid) throws Exception {
		Item item = shopeeApi.getItemDetailsV2(itemid, shopid);
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
//		Set<ItemPrice> priceList = itemPriceRepository.findByItem(item);
//		priceList.add(item.getItemPrice());
//		item.setItemPrices(priceList);
		itemRepository.save(item);

		return item;
	}

	@GetMapping("/getItem.v1/{shopid}/{itemid}")
	public String getItem1(@PathVariable("shopid") Long shopid, @PathVariable("itemid") Long itemid)
			throws IOException {
		return shopeeApi.getItemDetailsV1(itemid, shopid);
	}

	@GetMapping("/getItem.v2/{shop_id}/{item_id}")
	public Item getItem2(@PathVariable("shop_id") Long shopid, @PathVariable("item_id") Long itemid) throws Exception {
		Item item = shopeeApi.getItemDetailsV2(itemid, shopid);
//		itemRepository.save(item);
		Set<Category> categories = item.getCategories();
		Set<Category> all = categoryRepository.findByItem(item);
		if (all != null) {

			for (Category category : all) {
				categoryRepository.delete(category);
			}
			for (Category category : categories) {
				category.setItem(item);
				// categoryRepository.save(category);
			}
		}
		itemRepository.save(item);
		return item;
	}
	@GetMapping("/getRivals/{shop_id}/{item_id}")
	public List<Item> getRivals(@PathVariable("item_id") Long item_id, @PathVariable("shop_id") Long shop_id)
			throws Exception {
		List<KeyItem> keys = shopeeApi.getRivals(item_id, shop_id);
		return itemService.getRivals(keys);
	}
	@GetMapping("/getRivals1/{shop_id}/{item_id}")
	public List<Item> getRivals1(@PathVariable("item_id") Long item_id, @PathVariable("shop_id") Long shop_id)
			throws Exception {
		List<KeyItem> keys = shopeeApi.getRivals(item_id, shop_id);
        List<Item> items = new ArrayList<Item>();
        for (KeyItem key : keys) {
            Item item = shopeeApi.getItemDetailsV2(key.getItemid(), key.getShopid());
            items.add(item);
        }
        return items;
	}

	@GetMapping("/statistical/{shopid}/{itemid}")
	public ListRange statistical(@PathVariable("itemid") Long itemid, @PathVariable("shopid") Long shopid)
			throws Exception {
		ListRange ranges = new ListRange();
		Item myItem = shopeeApi.getItemDetailsV2(itemid, shopid);
		int[] rank = new int[20];
		int count = 0;
		double medium = 0;
		for (int i : rank) {
			rank[i] = 0;
		}
		List<KeyItem> keys = shopeeApi.getRivals(itemid, shopid);
		List<Item> items = itemService.getRivals(keys);
		for (Item item : items) {
			int r = (int) (item.getPrice() / myItem.getPrice() * 10)-5 ;
			if(r>0) {
				rank[r]++;
				count++;
				medium += item.getPrice();
			}
		}
		List<Range> list = new ArrayList<Range>();
		for (int i = 0; i < 10; i++) {
			list.add(new Range(i + 5, rank[i]));
		}
		ranges.setRanks(list);
		ranges.setMedium(medium / count);
		return ranges;
	}

	@PostMapping("/item")
	public Item postItem(@RequestBody Item item) throws Exception {
		itemRepository.save(item);
		return item;
	}
	@PutMapping("/rivalOff/{itemid}")
	public String putRival(@PathVariable("itemid") Long itemid) {
		List<Rival> rivals = rivalRepository.findByItemid(itemid);
		for (Rival rival : rivals) {
			rival.setAuto(false);
			rivalRepository.save(rival);
		}
		return "Off Auto";
	}
	@PostMapping("/rival")
	public Rival postRival(@RequestBody Rival rival) throws Exception {
		if (rivalRepository.findByItemidAndRivalItemid(rival.getItemid(), rival.getRivalItemid()) == null) {
			Rival newRival = new Rival(rival.getItemid(), rival.getShopid(), rival.getRivalShopid(),
					rival.getRivalItemid(), rival.isAuto(), rival.getPrice());
			if (rival.getMax() != 0) {
				newRival.setMax(rival.getMax());
			}
			if (rival.getMin() != 0) {
				newRival.setMin(rival.getMin());
			}
			if (rival.isAuto() == true) {
				List<Rival> rivalAuto = rivalRepository.findByItemidAndAuto(rival.getItemid(), true);
				for (Rival rival2 : rivalAuto) {
					rival2.setAuto(false);
					rivalRepository.save(rival2);
				}
			}
			newRival.setAuto(rival.isAuto());

			rivalRepository.save(newRival);
			return newRival;
		}
		Rival newRival = rivalRepository.findByItemidAndRivalItemid(rival.getItemid(), rival.getRivalItemid());
		if (rival.isAuto() == true) {
			List<Rival> rivalAuto = rivalRepository.findByItemidAndAuto(rival.getItemid(), true);
			for (Rival rival2 : rivalAuto) {
				rival2.setAuto(false);
				rivalRepository.save(rival2);
			}
		}
		newRival.setAuto(rival.isAuto());
		newRival.setPrice(rival.getPrice());
		if (rival.getMax() != 0) {
			newRival.setMax(rival.getMax());
		}
		if (rival.getMin() != 0) {
			newRival.setMin(rival.getMin());
		}
		rivalRepository.save(newRival);
		return newRival;
	}

	@DeleteMapping("/rival")
	public Item deleteRival(@RequestBody Rival rival) throws Exception {
		Rival rivalDetails = rivalRepository.findByItemidAndRivalItemid(rival.getItemid(), rival.getRivalItemid());
		Item item = null;
		if (rivalDetails != null) {
			item = itemRepository.findByItemid(rivalDetails.getRivalItemid());
			if (item != null) {
				Shop shop = shopRepository.findByShopid(item.getShopid());
				if (shop == null) {
					itemRepository.delete(item);
				}
			}
			rivalRepository.delete(rivalDetails);
		}
		return item;
	}

	@DeleteMapping("/rival/{itemid}")
	public String deleteRivals(@PathVariable("itemid") Long itemid) throws Exception {
		List<Rival> rivals = rivalRepository.findByItemid(itemid);
		for (Rival rival : rivals) {
			Item item = null;
			if (rival != null) {
				item = itemRepository.findByItemid(rival.getRivalItemid());
				if (item != null) {
					Shop shop = shopRepository.findByShopid(item.getShopid());
					if (shop == null) {
						itemRepository.delete(item);
					}
				}
				rivalRepository.delete(rival);
			}
		}
		return null;
	}
	@DeleteMapping("/item/{item_id}")
	public Item deleteItem(@PathVariable("item_id") Long item_id) {
		Item item = itemRepository.findByItemid(item_id);
		itemRepository.delete(item);
		return null;
	}

	@GetMapping("/itemPrice/{itemid}")
	public List<ItemPrice> getPriceRival(@PathVariable("itemid") Long itemid) {
//		Item item = itemRepository.findByItemid(itemid);
		Item item = itemRepository.findByItemid(itemid);
		if(item==null) {
			return null;
		}
//		List<ItemPrice> list = item.getItemPrices();
		List<ItemPrice> list = itemPriceRepository.findByItemOrderByIdDesc(item);
		return list;
	}

	@GetMapping("/rivals/{shopid}/{itemid}")
	public List<ItemRival> getRivals(@PathVariable("itemid") Long itemid) throws Exception {
		List<Rival> rivals = rivalRepository.findByItemid(itemid);
		List<ItemRival> list = new ArrayList<ItemRival>();
		for (Rival rival : rivals) {
			ItemRival itemRival = new ItemRival();
			itemRival.setItemRival(shopeeApi.getItemDetailsV2(rival.getRivalItemid(), rival.getRivalShopid()));
			itemRival.setRival(rival);
			itemRival.setItem(itemRepository.findByItemid(itemid));
			list.add(itemRival);
		}
		return list;
	}

	@GetMapping("rival/{itemid}/{rivalItemid}")
	public ItemRival getRival(@PathVariable("itemid") Long itemid, @PathVariable("rivalItemid") Long rivalItemid) {
		Rival rival = rivalRepository.findByItemidAndRivalItemid(itemid, rivalItemid);
		ItemRival itemRival = new ItemRival();
		itemRival.setItemRival(itemRepository.findByItemid(rivalItemid));
		itemRival.setRival(rival);
		itemRival.setItem(itemRepository.findByItemid(itemid));
		return itemRival;

	}

	@GetMapping("/autoUpdate/{itemid}")
	public List<AutoPrice> auto(@PathVariable("itemid") Long itemid) {
		List<AutoPrice> autoPrice = autoPriceRepository.findByItemidOrderByIdDesc(itemid);
		return autoPrice;
	}

	@GetMapping("/chosenItems/{shop}")
	public List<Item> chosenItems(@PathVariable("shop") Long shopid) throws Exception {
		List<Item> list = itemRepository.findByShopid(shopid);
		List<Item> items = new ArrayList<Item>();
		for (Item item : list) {
			int chosen = rivalRepository.findByShopidAndItemid(shopid, item.getItemid()).size();
			if (chosen != 0) {
				int bool = rivalRepository.findByItemidAndAuto(item.getItemid(), true).size();
				if (bool != 0) {
					item.setAuto(true);
				}
				item.setChosen(chosen);
				items.add(item);
			}
		}
		return items;
	}

	@PutMapping("/updatePrice/{shop_id}/{item_id}/{price}")
	public Item updatePrice(@PathVariable("shop_id") Long shopid, @PathVariable("item_id") Long itemid,
			@PathVariable("price") float price) throws Exception {
		Item item = shopeeApi.updatePrice(itemid, shopid, price);
		if (item != null) {
			List<Rival> rivals = rivalRepository.findByItemidAndAuto(itemid, true);
			for (Rival rival : rivals) {
				rival.setAuto(false);
				rivalRepository.save(rival);
			}
		}
		return item;
	}

}
