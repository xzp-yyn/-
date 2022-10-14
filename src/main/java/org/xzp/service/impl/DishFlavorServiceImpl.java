package org.xzp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.xzp.entity.DishFlavor;
import org.xzp.mapper.DishFlavorMapper;
import org.xzp.service.DishFlavorService;

/**
 * @Author xuezhanpeng
 * @Date 2022/10/11 12:08
 * @Version 1.0
 */
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper,DishFlavor> implements DishFlavorService {
}
