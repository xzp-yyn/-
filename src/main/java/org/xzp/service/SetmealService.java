package org.xzp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.xzp.dto.SetmealDto;
import org.xzp.entity.Setmeal;
import org.xzp.entity.SetmealDish;

/**
 * @Author xuezhanpeng
 * @Date 2022/10/11 18:13
 * @Version 1.0
 */
public interface SetmealService extends IService<Setmeal> {

    public SetmealDto getmealdto(Long id);

    //更新套餐以及菜品
    public void UpdateSetAndDish(SetmealDto setmealDto);

}
