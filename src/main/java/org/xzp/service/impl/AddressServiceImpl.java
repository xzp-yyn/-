package org.xzp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.xzp.entity.Address;
import org.xzp.mapper.AddressMapper;
import org.xzp.service.AddressService;

/**
 * @Author xuezhanpeng
 * @Date 2022/10/13 10:58
 * @Version 1.0
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {
}
