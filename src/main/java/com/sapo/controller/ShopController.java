package com.sapo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.sapo.service.ItemService;
import com.sapo.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.sapo.config.JwtTokenUtil;
import com.sapo.dto.KeyItem;
import com.sapo.model.Shop;
import com.sapo.model.User;
import com.sapo.repository.ShopRepository;
import com.sapo.repository.UserRepository;
import com.sapo.shopee.ShopeeApi;
import com.sapo.util.Util;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ShopController {
	@Autowired
	ShopeeApi shopeeApi;
	@Autowired
	ItemService itemService;
	@Autowired
	ShopService shopService;
	@Autowired
	ShopRepository shopRepository;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private UserRepository userRepository;
	Gson gson = new Gson();

	@PostMapping("/shop/{shopid}")
	public String addshop(@PathVariable("shopid") Long shopid, @RequestHeader("Authorization") String token)
			throws Exception {
		if (token.startsWith("Bearer ")) {
			token = token.substring(7);
		}
		String username = jwtTokenUtil.getUsernameFromToken(token);
		User user = userRepository.findByUsername(username);
		Shop newShop = shopeeApi.shopInfor(shopid);
		if (shopRepository.findByShopid(shopid) != null) {
			if (shopRepository.findByShopid(shopid).getUser() != user) {
				return "Shop đã được thêm cho tài khoản khác";
			}
			newShop.setUser(user);
			shopRepository.save(newShop);
			return "Shop đã được thêm";
		}
		newShop.setUser(user);
		shopRepository.save(newShop);
//		userRepository.save(user);
		return "Thêm thành công";
	}

	@GetMapping("/shop/{shopid}")
	public Shop getshop(@PathVariable("shopid") Long shopid,@RequestHeader("Authorization") String token) {
		if (token.startsWith("Bearer ")) {
			token = token.substring(7);
		}
		String username = jwtTokenUtil.getUsernameFromToken(token);
		User user = userRepository.findByUsername(username);

		return shopRepository.findByShopidAndUser(shopid, user);
	}

	@GetMapping("/shopInfor/{shopid}")
	public Shop shopInfor(@PathVariable("shopid") Long shopid) throws Exception {
		return shopeeApi.shopInfor(shopid);
	}

	@GetMapping("/shopRival/{shop_id}/{item_id}")
	public List<Shop> shopRival(@PathVariable("item_id") Long item_id, @PathVariable("shop_id") Long shop_id)
			throws Exception {
		List<KeyItem> keys = shopeeApi.getRivals(item_id, shop_id);
		List<Shop> shops = new ArrayList<Shop>();
		return shopService.getRivals(keys);
	}

	@GetMapping("/shop")
	public Set<Shop> shops(@RequestHeader("Authorization") String token) {
		if (token.startsWith("Bearer ")) {
			token = token.substring(7);
		}
		String username = jwtTokenUtil.getUsernameFromToken(token);
		User user = userRepository.findByUsername(username);
		Set<Shop> shops = user.getShops();
//		userRepository.save(user);
		return shops;
	}

	@GetMapping("/shopee")
	public String shopee() {
		String token = calToken(Util.redirectURL, Util.KEY);
		String urlShopee = "https://partner.shopeemobile.com/api/v1/shop/auth_partner?id=" + Util.PARTNER_ID + "&token="
				+ token + "&redirect=" + Util.redirectURL;
		return urlShopee;
	}

	public static String calToken(String redirectURL, String partnerKey) {
		String baseStr = partnerKey + redirectURL;
		return org.apache.commons.codec.digest.DigestUtils.sha256Hex(baseStr);
	}
}
