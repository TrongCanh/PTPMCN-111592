package com.sapo.util;

import com.google.gson.Gson;
import com.sapo.dto.ItemV2;
import com.sapo.dto.KeyItem;
import com.sapo.model.Item;
import com.sapo.model.ItemPrice;
import com.sapo.model.Rating;
import com.sapo.shopee.ShopeeApi;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ItemThread extends  Thread {
    PoolingHttpClientConnectionManager pool = new PoolingHttpClientConnectionManager();
    final CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(pool).build();
    CloseableHttpResponse response = null;
    Gson gson = new Gson();

    @Autowired
    ShopeeApi shopeeApi;
    private List<KeyItem> keys;
    private List<Item> items;

    public ItemThread(List<KeyItem> keys, List<Item> items) {
        this.keys = keys;
        this.items = items;
    }

    public String callApi(String url) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        response = httpclient.execute(httpGet);
        HttpEntity httpEntity = response.getEntity();
        return EntityUtils.toString(httpEntity);
    }
    @Override
    public void run() {
        List<Item> list = new ArrayList<Item>();
        for (KeyItem key : keys) {
            String uri = "https://shopee.vn/api/v2/item/get?itemid=" + key.getItemid() + "&shopid=" + key.getShopid();
            Item item = null;
            try {
                item = gson.fromJson(callApi(uri), ItemV2.class).getItem();
            } catch (Exception e) {
                System.out.println(e.getMessage()+"getItemDetailsV2");

            }
            if (item != null) {
                Rating item_rating = item.getItem_rating();
                if (item_rating != null) {
                    item.setRating_count(item_rating.getRating_count());
                    item.setRating_star(item_rating.getRating_star());
                }
                // itemRepository.save(item);
                String[] image = item.getImages();
                for (int i = 0; i < image.length; i++) {
                    image[i] = "https://cf.shopee.vn/file/" + image[i];
                }
                Calendar cal = Calendar.getInstance();
                item.setPrice(item.getPrice() / 100000);
                item.setPrice_max(item.getPrice_max() / 100000);
                item.setPrice_min(item.getPrice_min() / 100000);
                ItemPrice itemPrice = new ItemPrice();
                itemPrice.setDate(cal);
                itemPrice.setItem(item);
                itemPrice.setPrice(item.getPrice());
                item.setItemPrice(itemPrice);

                list.add(item);
            }
        }
        items.addAll(list);
    }
}
