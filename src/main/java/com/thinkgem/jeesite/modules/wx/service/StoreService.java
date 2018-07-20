package com.thinkgem.jeesite.modules.wx.service;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.zxing.WriterException;
import com.thinkgem.jeesite.modules.wx.entity.FoodCategory;
import com.thinkgem.jeesite.modules.wx.utils.QRCodeFactory;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.wx.entity.Store;
import com.thinkgem.jeesite.modules.wx.dao.StoreDao;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

/**
 * 店铺Service
 *
 * @author tgp
 * @version 2018-06-04
 */
@Service
@Transactional(readOnly = true)
public class StoreService extends CrudService<StoreDao, Store> {

    @Autowired
    private StoreDao storeDao;

    /**
     * 获取所有的店铺
     * 后台使用机构作为店铺，返回给小程序的是机构的code和name。其他表关联的都是机构的code
     */
    public List<Store> listAllStore() {
        return storeDao.listAllStore();
    }

    /**
     * 根据id获取店铺
     * 后台使用机构作为店铺，返回给小程序的是机构的code和name。其他表关联的都是机构的code
     */
    public Store findStoreById(String storeId) {
        return storeDao.findStoreById(storeId);
    }

    public Store get(String id) {
        return super.get(id);
    }

    public List<Store> findList(Store store) {
        return super.findList(store);
    }

    public Page<Store> findPage(Page<Store> page, Store store) {
        return super.findPage(page, store);
    }

    @Transactional(readOnly = false)
    public void save(Store store) {
        if (StringUtils.isEmpty(store.getId())) {
            store.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            storeDao.insert(store);
        } else {
            storeDao.update(store);
        }
    }

    @Transactional(readOnly = false)
    public void delete(Store store) {
        super.delete(store);
    }

    public Store getByName(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }

        Store store = storeDao.getByName(name);
        return store;
    }

    public Store getByUserId(String userId) {
        if (StringUtils.isEmpty(userId)) {
            return null;
        }

        Store store = storeDao.getByUserId(userId);
        return store;
    }


    /**
     * 一键生成2维码
     */
    public String gennarate2WeiMa(String fileName) {
        getminiqrQr("",fileName);
        return fileName;
    }

    public void getminiqrQr(String accessToken,String fileName) {
        RestTemplate rest = new RestTemplate();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            String url = "https://api.weixin.qq.com/wxa/getwxacode?access_token="+accessToken;
            Map<String,Object> param = new HashMap<>();
            param.put("page", "pages/index/index");
            param.put("width", 430);
            param.put("auto_color", false);
            Map<String,Object> line_color = new HashMap<>();
            line_color.put("r", 0);
            line_color.put("g", 0);
            line_color.put("b", 0);
            param.put("line_color", line_color);
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            HttpEntity requestEntity = new HttpEntity(param, headers);
            ResponseEntity<byte[]> entity = rest.exchange(url, HttpMethod.POST, requestEntity, byte[].class, new Object[0]);
            byte[] result = entity.getBody();
            inputStream = new ByteArrayInputStream(result);
            File file = new File(fileName);
            if (!file.exists()){
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read(buf, 0, 1024)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.flush();
        } catch (Exception e) {
        } finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}