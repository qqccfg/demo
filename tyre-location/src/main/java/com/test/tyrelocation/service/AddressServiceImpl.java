package com.test.tyrelocation.service;

import com.test.tyrelocation.common.Exception.TyreException;
import com.test.tyrelocation.common.emum.ExceptionEnum;
import com.test.tyrelocation.entity.Address;
import com.test.tyrelocation.repository.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @Date: 2019/12/17 16:11
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Service
public class AddressServiceImpl implements AddressService{

    private final int UN_DEFAULT = 0;

    private final int DEFAULT = 1;

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public Address queryToOne(Long userId, Long addressId) {
        Address address = addressMapper.selectByPrimaryKeyAndUserId(userId, addressId);
        if (Objects.isNull(address)){
            throw new TyreException(ExceptionEnum.PARAMETER_ILLEGALITY);
        }
        return address;
    }

    @Override
    public List<Address> queryToAll(Long userId) {
        List<Address> addresses = addressMapper.selectAllByUserId(userId);
        return addresses;
    }

    @Override
    public void addAddress(Long userId, Address address) {
        Integer count = addressMapper.selectCountByUserId(userId);
        if (count!=null && count>=10){
            throw new TyreException(ExceptionEnum.ADDRESS_UPPER_LIMIT);
        }
        address.setUserId(userId);
        addressMapper.insertSelective(address);
    }

    @Override
    public void deleteAddress(Long userId, Long addressId) {
        checkAddress(userId, addressId);
        addressMapper.deleteByPrimaryKey(addressId);
    }

    @Override
    public void updateAddress(Long userId, Address address) {
        checkAddress(userId, address.getId());
        addressMapper.updateByPrimaryKeySelective(address);
    }

    @Override
    @Transactional
    public void setDefaultAddress(Long userId, Long id) {
        checkAddress(userId, id);
        Address address = addressMapper.selectNowDefaultByUserId(userId);
        if (Objects.nonNull(address)){
            address.setIsDefault(UN_DEFAULT);
            addressMapper.updateByPrimaryKeySelective(address);
        }
        Address address2 = addressMapper.selectByPrimaryKeyAndUserId(userId, id);
        address2.setIsDefault(DEFAULT);
        addressMapper.updateByPrimaryKeySelective(address2);
    }

    private void checkAddress(Long userId, Long id){
        Address address = addressMapper.selectByPrimaryKeyAndUserId(userId, id);
        if (Objects.isNull(address)){
            throw new TyreException(ExceptionEnum.PARAMETER_ILLEGALITY);
        }
    }
}
