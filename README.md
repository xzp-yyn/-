# TakeOut Platform 

#### 介绍
外卖平台项目
基于O2O模式，为用户提供点餐服务。项目前端系统提供用户登录，菜品展示和用户点餐，下单。项目后端系统提供给管理员和员工对菜品，套餐的操作。旨在方便用户点餐和商家管理商品

#### 软件架构
springboot
mysql
mybatis-plus
vue+elementUI
nginx
redis
git
# 项目页面：
## 前端登录页
![前端登录页](showimgs/%E5%89%8D%E7%AB%AF%E7%99%BB%E5%BD%95.png)
## 前端首页
![前端首页](showimgs/%E5%89%8D%E7%AB%AF%E8%8F%9C%E5%93%81%E9%A1%B5%E9%9D%A2.png)
## 地址信息
![地址信息](showimgs/%E5%9C%B0%E5%9D%80%E7%AE%A1%E7%90%86.png)
## 员工管理
![员工管理](showimgs/%E5%91%98%E5%B7%A5%E4%BF%A1%E6%81%AF.png)
## 分类管理
![分类管理](showimgs/%E5%88%86%E7%B1%BB%E7%AE%A1%E7%90%86.png)
## 套餐管理
![套餐管理](showimgs/%E5%A5%97%E9%A4%90%E7%AE%A1%E7%90%86.png)
## 菜品管理
![菜品管理](showimgs/%E8%8F%9C%E5%93%81%E7%AE%A1%E7%90%86.png)
## 订单信息
![订单信息](showimgs/%E8%AE%A2%E5%8D%95%E4%BF%A1%E6%81%AF.png)


# 安装教程

1.  准备环境jdk11,maven3.6+,tomcat,git
2.  使用git工具克隆git@gitee.com:xue-zhanpeng/ruiji.git
3.  在yml文件更改数据库配置，redis地址
4.  使用maven编译打包，运行jar

# 使用说明

1.  如果出现打包不成功，检查maven配置是否有本地仓库，以及镜像是否是阿里云
2.  在linux上部署jdk8编译是不能成功的
3.  配置中有本地图片地址，修改为自己的地址

