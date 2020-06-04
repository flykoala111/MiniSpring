/*
Navicat MySQL Data Transfer

Source Server         : fzj_mysql
Source Server Version : 50617
Source Host           : 172.16.3.82:3306
Source Database       : fzj

Target Server Type    : MYSQL
Target Server Version : 50617
File Encoding         : 65001

Date: 2020-06-04 09:46:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for minispring_modules
-- ----------------------------
DROP TABLE IF EXISTS `minispring_modules`;
CREATE TABLE `minispring_modules` (
  `id_obj` int(3) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin NOT NULL,
  `detail` varchar(1000) COLLATE utf8_bin DEFAULT '',
  `imgpath` varchar(100) COLLATE utf8_bin DEFAULT '',
  `dt_crea` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `type` varchar(20) COLLATE utf8_bin DEFAULT '',
  `linkurl` varchar(100) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`id_obj`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of minispring_modules
-- ----------------------------
INSERT INTO `minispring_modules` VALUES ('1', 'MiniSpring Core', 'MiniSpring的内核。主要包含了各种容器。比如类实例容器Ioc,路径映射器HandlerMapping，全局容器ClassNames,静态资源容器StaticResources，mapper接口，sql容器等。框架的初始化操作会将对应的数据添加到容器里，奠定了框架运作的基础', '/javaee_minispring/utils/images/minispringcore.png', '2020-05-27 11:22:30', 'modules', ' ');
INSERT INTO `minispring_modules` VALUES ('2', 'MiniSpring Jdbc', '数据库操作模块，拥有mapper工厂和sql工厂，mapper工厂实现了对mapper接口的代理，接口代理实例会被添加到Ioc容器里。调用mapper接口时会从sql工厂里取得sql语句，通过解析和参数加载，最终实现对数据库的操作', '/javaee_minispring/utils/images/minispringjdbc.png', '2020-05-27 11:22:39', 'modules', ' ');
INSERT INTO `minispring_modules` VALUES ('3', 'MiniSpring Mvc', '请求接受与响应模块。包含了一个中央处理器，用于对请求的分发，中央处理器里判断请求的实静态资源或者controller里的映射，如果是静态资源，会去查找StaticResources容器，通过配置的静态资源的读取方式去读取。如果是controller里的映射，那么会通过HandlerMapping容器找到对应的方法。完成方法的反射。通过方法的返回值和方法注解判断出是视图还是数据。视图的话，最后通过html或者freemarker解析器去解析', '/javaee_minispring/utils/images/minispringmvc.png', '2020-05-27 11:22:47', 'modules', ' ');
INSERT INTO `minispring_modules` VALUES ('4', 'Controller', '控制器。被@Anno_fzjcontroller注解的类可被认为是控制器。控制器用于对请求的控制。里面可以通过@Anno_fzjautowired注入mapper层或者servuce层的类', ' /javaee_minispring/utils/images/controller.png', '2020-05-27 12:50:14', 'functions', '/javaee_minispring/test/controllerindex');
INSERT INTO `minispring_modules` VALUES ('5', 'Service', '业务层。被@Anno_service注解的类可以被认为是业务类。业务层主要是对业务逻辑的处理。里面可以注入mapper层的类', '/javaee_minispring/utils/images/service.png', '2020-05-27 13:14:32', 'functions', '/javaee_minispring/test/serviceindex');
INSERT INTO `minispring_modules` VALUES ('6', 'Mapper', '数据持久层。被@Anno_fzjmapper注解的类可以被认为是数据库接口类。该层用于对数据库的操作。可被注入到service层或者controller层', '/javaee_minispring/utils/images/mapper.png', '2020-05-27 13:14:53', 'functions', '/javaee_minispring/test/mapperindex');
INSERT INTO `minispring_modules` VALUES ('7', 'Xml', 'sql文件。存储了操作数据库的语句，mapper层的接口通过调用sql文件里的sql语句实现对数据库的操作', '/javaee_minispring/utils/images/xml.png', '2020-05-27 13:15:01', 'functions', '/javaee_minispring/test/xmlindex');
INSERT INTO `minispring_modules` VALUES ('8', 'MiniSpring Aop', '切面编程。对于某一个特定的代码层去执行一套统一的操作，减少代码冗余，提高项目的扩展性。', '/javaee_minispring/utils/images/minispringaop.png', '2020-06-03 13:12:49', 'modules', '');
INSERT INTO `minispring_modules` VALUES ('9', 'MiniSpring MvcInteceptor', '拦截器。在访问映射方法之前对某个请求做预处理，通过处理去决定是否可以让该请求进行后续的操作。拦截是在mvc里进行的，故而称为mvcinteceptor.', '/javaee_minispring/utils/images/minispringmvcinteceptor.png', '2020-06-03 13:16:20', 'modules', '');
