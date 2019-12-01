package com.sapo.util;

import com.google.gson.Gson;
import com.sapo.dto.ItemV2;
import com.sapo.dto.KeyItem;
import com.sapo.dto.ShopV2;
import com.sapo.dto.Shop_covers;
import com.sapo.model.*;
import com.sapo.shopee.ShopeeApi;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ShopThread extends  Thread {
    PoolingHttpClientConnectionManager pool = new PoolingHttpClientConnectionManager();
    final CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(pool).build();
    CloseableHttpResponse response = null;
    Gson gson = new Gson();

    @Autowired
    ShopeeApi shopeeApi;
    private List<KeyItem> keys;
    private List<Shop> shops;

    public ShopThread(List<KeyItem> keys, List<Shop> shops) {
        this.keys = keys;
        this.shops = shops;
    }
    public String callApi(String url) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        response = httpclient.execute(httpGet);
        HttpEntity httpEntity = response.getEntity();
        return EntityUtils.toString(httpEntity);
    }
    @Override
    public void run() {
        List<Shop> list = new ArrayList<Shop>();
        for (KeyItem key : keys) {
            String url = "https://shopee.vn/api/v2/shop/get?shopid=" + key.getShopid();
            Shop shop = null;
            try {
                shop = gson.fromJson(callApi(url), ShopV2.class).getData();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Account account = shop.getAccount();
            if(account.getPortrait()!=null) {
                shop.setPortrait("http://cf.shopee.vn/file/"+account.getPortrait());
            }
            Rating rating = shop.getBuyer_rating();
            if (rating != null) {
                shop.setRating_count(rating.getRating_count());
                shop.setRating_star(rating.getRating_star());
            }
            List<Shop_covers> cover = shop.getShop_covers();
            if (cover != null) {
                String coverImg[] = new String[cover.size()];
                for (int i = 0; i < cover.size(); i++) {
                    coverImg[i] = "https://cf.shopee.vn/file/" + cover.get(i).getImage_url();
                }
                shop.setImages(coverImg);
            }
            list.add(shop);
    }
        shops.addAll(list);
}

}
