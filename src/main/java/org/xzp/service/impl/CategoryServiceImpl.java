package org.xzp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.xzp.entity.Category;
import org.xzp.mapper.CategoryMapper;
import org.xzp.service.CategoryService;

/**
 * @Author xuezhanpeng
 * @Date 2022/10/10 17:11
 * @Version 1.0
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
}
