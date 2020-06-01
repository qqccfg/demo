package com.test.tyrelocation.service;

import com.test.tyrelocation.entity.Address;

import java.util.List;

/**
 * @Date: 2019/12/17 16:08
 * @Author: JackLei
 * @Description:
 * @Version:
 */
public interface AddressService {

    Address queryToOne(Long userId, Long addressId);

    List<Address> queryToAll(Long userId);

    void addAddress(Long userId, Address address);

    void deleteAddress(Long userId, Long addressId);

    void updateAddress(Long userId, Address address);

    void setDefaultAddress(Long userId, Long id);
}
