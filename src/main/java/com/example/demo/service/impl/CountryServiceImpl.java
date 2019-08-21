package com.example.demo.service.impl;

import com.example.demo.common.entity.Country;
import com.example.demo.dao.mapper.CountryMapper;
import com.example.demo.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author weianlai
 * @date 2019-01-17 20:02
 */
@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryMapper countryMapper;

    @Override
    public void mapperDemo() {
        // 查询所有数据
        List<Country> countryList = countryMapper.selectAll();
        System.out.println("countryList:" + countryList);
        //根据主键查询
        Country country = countryMapper.selectByPrimaryKey(1);
        System.out.println(country);
    }


}
