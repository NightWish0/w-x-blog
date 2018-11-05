/*
Navicat MySQL Data Transfer

Source Server         : 192.168.123.180
Source Server Version : 50640
Source Host           : 192.168.123.180:3306
Source Database       : wxblog_development

Target Server Type    : MYSQL
Target Server Version : 50640
File Encoding         : 65001

Date: 2018-11-05 17:32:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for blog_comment
-- ----------------------------
DROP TABLE IF EXISTS `blog_comment`;
CREATE TABLE `blog_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `receiver_id` bigint(20) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `topic_id` bigint(20) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of blog_comment
-- ----------------------------
INSERT INTO `blog_comment` VALUES ('1', 'ee', 'e', null, null, '86', 'eee', '2018-11-02 15:20:57');
INSERT INTO `blog_comment` VALUES ('2', 'cc', 'c', '1', '1', '86', 'SELECT c.*,c2.*,c3.name FROM blog_comment c LEFT JOIN blog_comment c2 ON c.id=c2.parent_id LEFT JOIN blog_comment c3 ON c2.receiver_id=c3.id WHERE c.topic_id=86 AND ISNULL(c.parent_id)', '2018-11-02 15:21:34');
INSERT INTO `blog_comment` VALUES ('3', 'qq', 'q', '1', '1', '86', 'qqq', '2018-11-02 15:21:49');
INSERT INTO `blog_comment` VALUES ('4', 'aa', 'a', '2', '1', '86', 'aaa', '2018-11-02 15:22:28');
INSERT INTO `blog_comment` VALUES ('5', 'NightWish', '787940468@qq.com', null, null, '86', '使用时th:each，Thymeleaf提供了一种机制，可用于跟踪迭代的状态：状态变量。使用时th:each，Thymeleaf提供了一种机制，可用于跟踪迭代的状态：状态变量。使用时th:each，Thymeleaf提供了一种机制，可用于跟踪迭代的状态：状态变量。使用时th:each，Thymeleaf提供了一种机制，可用于跟踪迭代的状态：状态变量。使用时th:each，Thymeleaf提供了一种机制，可用于跟踪迭代的状态：状态变量。', '2018-11-02 16:48:00');
INSERT INTO `blog_comment` VALUES ('6', 'qq', 'qq@qq.com', null, null, '85', 'ceshi', '2018-11-05 17:17:43');
INSERT INTO `blog_comment` VALUES ('7', 'qq3', 'qq@qq.com', null, null, '85', 'ceshi', '2018-11-05 17:12:27');
INSERT INTO `blog_comment` VALUES ('8', 'qq2', 'qq@qq.com', null, null, '85', 'ceshi', '2018-11-05 17:12:50');
INSERT INTO `blog_comment` VALUES ('9', 'vc', 'vc@qq.com', null, null, '85', 'qq', '2018-11-05 17:24:38');
INSERT INTO `blog_comment` VALUES ('10', 'bnn', '1eq@qq.com', null, null, '85', 'qadad', '2018-11-05 17:27:18');
INSERT INTO `blog_comment` VALUES ('11', 'qeq', 'qw@qq.com', null, null, '85', 'cesada', '2018-11-05 17:28:11');

-- ----------------------------
-- Table structure for blog_label
-- ----------------------------
DROP TABLE IF EXISTS `blog_label`;
CREATE TABLE `blog_label` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of blog_label
-- ----------------------------
INSERT INTO `blog_label` VALUES ('1', 'Spring', '2018-10-19 11:53:04');
INSERT INTO `blog_label` VALUES ('2', 'springmvc', '2018-10-19 11:53:07');
INSERT INTO `blog_label` VALUES ('24', 'dubbo', '2018-10-26 11:29:18');
INSERT INTO `blog_label` VALUES ('25', 'redis', '2018-10-26 11:29:18');
INSERT INTO `blog_label` VALUES ('26', 'rabbitmq', '2018-10-26 11:31:13');
INSERT INTO `blog_label` VALUES ('27', 'springboot', '2018-10-26 11:32:58');
INSERT INTO `blog_label` VALUES ('28', 'zk', '2018-10-26 11:37:16');
INSERT INTO `blog_label` VALUES ('29', 'shiro', '2018-10-29 15:32:29');
INSERT INTO `blog_label` VALUES ('31', 'nodejs', '2018-10-29 15:38:52');
INSERT INTO `blog_label` VALUES ('32', 'ruby', '2018-10-29 15:38:52');

-- ----------------------------
-- Table structure for blog_label_association
-- ----------------------------
DROP TABLE IF EXISTS `blog_label_association`;
CREATE TABLE `blog_label_association` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `topic_id` bigint(20) DEFAULT NULL,
  `label_id` bigint(20) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of blog_label_association
-- ----------------------------
INSERT INTO `blog_label_association` VALUES ('1', '26', '1', '2018-10-19 11:52:32');
INSERT INTO `blog_label_association` VALUES ('2', '27', '2', '2018-10-19 11:52:39');
INSERT INTO `blog_label_association` VALUES ('3', '26', '2', '2018-10-19 11:52:52');
INSERT INTO `blog_label_association` VALUES ('32', '86', '1', '2018-10-29 07:46:05');
INSERT INTO `blog_label_association` VALUES ('33', '86', '31', '2018-10-29 07:46:05');
INSERT INTO `blog_label_association` VALUES ('34', '86', '32', '2018-10-29 07:46:05');

-- ----------------------------
-- Table structure for blog_topic
-- ----------------------------
DROP TABLE IF EXISTS `blog_topic`;
CREATE TABLE `blog_topic` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `content` text,
  `user_id` bigint(20) DEFAULT NULL,
  `category_id` bigint(20) DEFAULT NULL,
  `read_count` int(11) DEFAULT '0',
  `like_count` int(11) DEFAULT '0',
  `created_at` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL COMMENT '1-正常；\r\n            0-草稿；\r\n            -1-删除；',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of blog_topic
-- ----------------------------
INSERT INTO `blog_topic` VALUES ('26', '测试', '<p><strong>阿达</strong></p>\r\n', '1', null, '0', '0', '2018-10-16 11:50:55', '1');
INSERT INTO `blog_topic` VALUES ('27', '驱蚊器二', '<p><strong>驱蚊器<em>**</em></strong></p>\r\n', '2', null, '0', '0', '2018-10-16 13:44:58', '1');
INSERT INTO `blog_topic` VALUES ('28', 'qwqwe', '<p>qweqw</p>\r\n', '2', null, '0', '0', '2018-10-17 14:24:52', '1');
INSERT INTO `blog_topic` VALUES ('29', '123123', '<p>123123</p>\r\n', '2', null, '0', '0', '2018-10-17 14:37:59', '1');
INSERT INTO `blog_topic` VALUES ('30', 'wdqwdqd', '<p>wqeqwe</p>\r\n', '1', null, '0', '0', '2018-10-17 14:38:04', '1');
INSERT INTO `blog_topic` VALUES ('31', 'qw13', '<p>1232</p>\r\n', '1', null, '0', '0', '2018-10-17 14:41:43', '1');
INSERT INTO `blog_topic` VALUES ('32', '1313', '<p>11231</p>\r\n', '1', null, '0', '0', '2018-10-17 14:41:47', '1');
INSERT INTO `blog_topic` VALUES ('33', '123123', '<p>12313</p>\r\n', '1', null, '0', '0', '2018-10-17 14:41:52', '1');
INSERT INTO `blog_topic` VALUES ('34', '2131141', '<p>12421414<em>**</em></p>\r\n', '1', null, '0', '0', '2018-10-17 14:41:58', '1');
INSERT INTO `blog_topic` VALUES ('35', '1231141414', '<p>141414</p>\r\n', '1', null, '0', '0', '2018-10-17 14:42:03', '1');
INSERT INTO `blog_topic` VALUES ('36', '313141', '<p>CRUD 接口</p>\r\n<h1 id=\"h1-mapper-crud-\"><a name=\"Mapper CRUD 接口\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>Mapper CRUD 接口</h1><p>说明:</p>\r\n<p>通用 CRUD 封装BaseMapper接口，为 Mybatis-Plus 启动时自动解析实体表关系映射转换为 Mybatis 内部对象注入容器<br>泛型 T 为任意实体对象<br>参数 Serializable 为任意类型主键 Mybatis-Plus 不推荐使用复合主键约定每一张表都有自己的唯一 id 主键<br>对象 Wrapper 为 条件构造器\r\n<h1 id=\"h1-insert\"><a name=\"insert\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>insert</h1><p>/**</p>\r\n<ul>\r\n<li><p></p><li>插入一条记录</li><li><p></p></li><br>*<li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> entity 实体对象</li><li><a href=\"https://github.com/return\" title=\"@return\" class=\"at-link\">@return</a> 插入成功记录数<br>*/<br>int insert(T entity);<h1 id=\"h1-deletebyid\"><a name=\"deleteById\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>deleteById</h1>/**</li><li><p></p><li>根据 ID 删除</li><li><p></p></li><br>*<li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> id 主键ID</li><li><a href=\"https://github.com/return\" title=\"@return\" class=\"at-link\">@return</a> 删除成功记录数<br>*/<br>int deleteById(Serializable id);<h1 id=\"h1-deletebymap\"><a name=\"deleteByMap\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>deleteByMap</h1>/**</li><li><p></p><li>根据 columnMap 条件，删除记录</li><li><p></p></li><br>*<li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> columnMap 表字段 map 对象</li><li><a href=\"https://github.com/return\" title=\"@return\" class=\"at-link\">@return</a> 删除成功记录数<br>*/<br>int deleteByMap(<a href=\"https://github.com/Param\" title=\"@Param\" class=\"at-link\">@Param</a>(Constants.COLUMN_MAP) Map<String, Object> columnMap);<h1 id=\"h1-delete\"><a name=\"delete\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>delete</h1>/**</li><li><p></p><li>根据 entity 条件，删除记录</li><li><p></p></li><br>*<li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> queryWrapper 实体对象封装操作类（可以为 null）</li><li><a href=\"https://github.com/return\" title=\"@return\" class=\"at-link\">@return</a> 删除成功记录数<br>*/<br>int delete(<a href=\"https://github.com/Param\" title=\"@Param\" class=\"at-link\">@Param</a>(Constants.WRAPPER) Wrapper<T> queryWrapper);<h1 id=\"h1-deletebatchids\"><a name=\"deleteBatchIds\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>deleteBatchIds</h1>/**</li><li><p></p><li>删除（根据ID 批量删除）</li><li><p></p></li><br>*<li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> idList 主键ID列表(不能为 null 以及 empty)</li><li><a href=\"https://github.com/return\" title=\"@return\" class=\"at-link\">@return</a> 删除成功记录数<br>*/<br>int deleteBatchIds(<a href=\"https://github.com/Param\" title=\"@Param\" class=\"at-link\">@Param</a>(Constants.COLLECTION) Collection&lt;? extends Serializable&gt; idList);<h1 id=\"h1-updatebyid\"><a name=\"updateById\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>updateById</h1>/**</li><li><p></p><li>根据 ID 修改</li><li><p></p></li><br>*<li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> entity 实体对象</li><li><a href=\"https://github.com/return\" title=\"@return\" class=\"at-link\">@return</a> 修改成功记录数<br>*/<br>int updateById(<a href=\"https://github.com/Param\" title=\"@Param\" class=\"at-link\">@Param</a>(Constants.ENTITY) T entity);<h1 id=\"h1-update\"><a name=\"update\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>update</h1>/**</li><li><p></p><li>根据 whereEntity 条件，更新记录</li><li><p></p></li><br>*<li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> entity        实体对象 (set 条件值,不能为 null)</li><li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> updateWrapper 实体对象封装操作类（可以为 null,里面的 entity 用于生成 where 语句）</li><li><a href=\"https://github.com/return\" title=\"@return\" class=\"at-link\">@return</a> 修改成功记录数<br>*/<br>int update(<a href=\"https://github.com/Param\" title=\"@Param\" class=\"at-link\">@Param</a>(Constants.ENTITY) T entity, <a href=\"https://github.com/Param\" title=\"@Param\" class=\"at-link\">@Param</a>(Constants.WRAPPER) Wrapper<T> updateWrapper);<h1 id=\"h1-selectbyid\"><a name=\"selectById\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>selectById</h1>/**</li><li><p></p><li>根据 ID 查询</li><li><p></p></li><br>*<li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> id 主键ID</li><li><a href=\"https://github.com/return\" title=\"@return\" class=\"at-link\">@return</a> 实体<br>*/<br>T selectById(Serializable id);<h1 id=\"h1-selectbatchids\"><a name=\"selectBatchIds\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>selectBatchIds</h1>/**</li><li><p></p><li>查询（根据ID 批量查询）</li><li><p></p></li><br>*<li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> idList 主键ID列表(不能为 null 以及 empty)</li><li><a href=\"https://github.com/return\" title=\"@return\" class=\"at-link\">@return</a> 实体集合<br>*/<br>List<T> selectBatchIds(<a href=\"https://github.com/Param\" title=\"@Param\" class=\"at-link\">@Param</a>(Constants.COLLECTION) Collection&lt;? extends Serializable&gt; idList);<h1 id=\"h1-selectbymap\"><a name=\"selectByMap\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>selectByMap</h1>/**</li><li><p></p><li>查询（根据 columnMap 条件）</li><li><p></p></li><br>*<li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> columnMap 表字段 map 对象</li><li><a href=\"https://github.com/return\" title=\"@return\" class=\"at-link\">@return</a> 实体集合<br>*/<br>List<T> selectByMap(<a href=\"https://github.com/Param\" title=\"@Param\" class=\"at-link\">@Param</a>(Constants.COLUMN_MAP) Map<string object=\"\"> columnMap);</string></ul>\r\n<h1 id=\"h1-selectone\"><a name=\"selectOne\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>selectOne</h1><p>/**</p>\r\n<ul>\r\n<li><p></p><li>根据 entity 条件，查询一条记录</li><li><p></p></li><br>*<li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> queryWrapper 实体对象</li><li><a href=\"https://github.com/return\" title=\"@return\" class=\"at-link\">@return</a> 实体<br>*/<br>T selectOne(<a href=\"https://github.com/Param\" title=\"@Param\" class=\"at-link\">@Param</a>(Constants.WRAPPER) Wrapper<T> queryWrapper);<h1 id=\"h1-selectcount\"><a name=\"selectCount\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>selectCount</h1>/**</li><li><p></p><li>根据 Wrapper 条件，查询总记录数</li><li><p></p></li><br>*<li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> queryWrapper 实体对象</li><li><a href=\"https://github.com/return\" title=\"@return\" class=\"at-link\">@return</a> 满足条件记录数<br>*/<br>Integer selectCount(<a href=\"https://github.com/Param\" title=\"@Param\" class=\"at-link\">@Param</a>(Constants.WRAPPER) Wrapper<T> queryWrapper);<h1 id=\"h1-selectlist\"><a name=\"selectList\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>selectList</h1>/**</li><li><p></p><li>根据 entity 条件，查询全部记录</li><li><p></p></li><br>*<li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> queryWrapper 实体对象封装操作类（可以为 null）</li><li><a href=\"https://github.com/return\" title=\"@return\" class=\"at-link\">@return</a> 实体集合<br>*/<br>List<T> selectList(<a href=\"https://github.com/Param\" title=\"@Param\" class=\"at-link\">@Param</a>(Constants.WRAPPER) Wrapper<T> queryWrapper);<h1 id=\"h1-selectmaps\"><a name=\"selectMaps\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>selectMaps</h1>/**</li><li><p></p><li>根据 Wrapper 条件，查询全部记录</li><li><p></p></li><br>*<li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> queryWrapper 实体对象封装操作类（可以为 null）</li><li><a href=\"https://github.com/return\" title=\"@return\" class=\"at-link\">@return</a> 字段映射对象 Map 集合<br>*/<br>List<Map<String, Object>&gt; selectMaps(<a href=\"https://github.com/Param\" title=\"@Param\" class=\"at-link\">@Param</a>(Constants.WRAPPER) Wrapper<T> queryWrapper);<h1 id=\"h1-selectobjs\"><a name=\"selectObjs\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>selectObjs</h1>/**</li><li><p></p><li>根据 Wrapper 条件，查询全部记录</li><li>注意： 只返回第一个字段的值</li><li><p></p></li><br>*<li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> queryWrapper 实体对象封装操作类（可以为 null）</li><li><a href=\"https://github.com/return\" title=\"@return\" class=\"at-link\">@return</a> 字段映射对象集合<br>*/<br>List<Object> selectObjs(<a href=\"https://github.com/Param\" title=\"@Param\" class=\"at-link\">@Param</a>(Constants.WRAPPER) Wrapper<T> queryWrapper);<h1 id=\"h1-selectpage\"><a name=\"selectPage\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>selectPage</h1>/**</li><li><p></p><li>根据 entity 条件，查询全部记录（并翻页）</li><li><p></p></li><br>*<li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> page         分页查询条件（可以为 RowBounds.DEFAULT）</li><li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> queryWrapper 实体对象封装操作类（可以为 null）</li><li><a href=\"https://github.com/return\" title=\"@return\" class=\"at-link\">@return</a> 实体分页对象<br>*/<br>IPage<T> selectPage(IPage<T> page, <a href=\"https://github.com/Param\" title=\"@Param\" class=\"at-link\">@Param</a>(Constants.WRAPPER) Wrapper<T> queryWrapper);<h1 id=\"h1-selectmapspage\"><a name=\"selectMapsPage\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>selectMapsPage</h1>/**</li><li><p></p><li>根据 Wrapper 条件，查询全部记录（并翻页）</li><li><p></p></li><br>*<li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> page         分页查询条件</li><li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> queryWrapper 实体对象封装操作类</li><li><a href=\"https://github.com/return\" title=\"@return\" class=\"at-link\">@return</a> 字段映射对象 Map 分页对象<br>*/<br>IPage<Map<String, Object>&gt; selectMapsPage(IPage<T> page, <a href=\"https://github.com/Param\" title=\"@Param\" class=\"at-link\">@Param</a>(Constants.WRAPPER) Wrapper<T> queryWrapper);<h1 id=\"h1-service-crud-\"><a name=\"Service CRUD 接口\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>Service CRUD 接口</h1>说明:</li></ul>\r\n<p>通用 Service CRUD 封装IService接口，进一步封装 CRUD 采用 get 查询单行 remove 删除 list 查询集合 page 分页 前缀命名方式区分 Mapper 层避免混淆，<br>泛型 T 为任意实体对象<br>建议如果存在自定义通用 Service 方法的可能，请创建自己的 IBaseService 继承 Mybatis-Plus 提供的基类<br>对象 Wrapper 为 条件构造器\r\n<h1 id=\"h1-save\"><a name=\"save\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>save</h1><p>/**</p>\r\n<ul>\r\n<li><p></p><li>插入一条记录（选择字段，策略插入）</li><li><p></p></li><br>*<li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> entity 实体对象<br>*/<br>boolean save(T entity);<h1 id=\"h1-savebatch\"><a name=\"saveBatch\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>saveBatch</h1>/**</li><li><p></p><li>插入一条记录（选择字段，策略插入）</li><li><p></p></li><br>*<li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> entity 实体对象<br>*/<br>boolean save(T entity);<h1 id=\"h1-saveorupdatebatch\"><a name=\"saveOrUpdateBatch\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>saveOrUpdateBatch</h1>/**</li><li><p></p><li>批量修改插入</li><li><p></p></li><br>*<li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> entityList 实体对象集合<br>*/<br>boolean saveOrUpdateBatch(Collection<T> entityList);<h1 id=\"h1-saveorupdatebatch\"><a name=\"saveOrUpdateBatch\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>saveOrUpdateBatch</h1>/**</li><li><p></p><li>批量修改插入</li><li><p></p></li><br>*<li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> entityList 实体对象集合</li><li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> batchSize  每次的数量<br>*/<br>boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize);<h1 id=\"h1-removebyid\"><a name=\"removeById\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>removeById</h1>/**</li><li><p></p><li>根据 ID 删除</li><li><p></p></li><br>*<li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> id 主键ID<br>*/<br>boolean removeById(Serializable id);<h1 id=\"h1-removebymap\"><a name=\"removeByMap\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>removeByMap</h1>/**</li><li><p></p><li>根据 columnMap 条件，删除记录</li><li><p></p></li><br>*<li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> columnMap 表字段 map 对象<br>*/<br>boolean removeByMap(Map<String, Object> columnMap);<h1 id=\"h1-remove\"><a name=\"remove\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>remove</h1>/**</li><li><p></p><li>根据 entity 条件，删除记录</li><li><p></p></li><br>*<li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> queryWrapper 实体包装类 {<a href=\"https://github.com/link\" title=\"@link\" class=\"at-link\">@link</a> com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}<br>*/<br>boolean remove(Wrapper<T> queryWrapper);<h1 id=\"h1-removebyids\"><a name=\"removeByIds\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>removeByIds</h1>/**</li><li><p></p><li>删除（根据ID 批量删除）</li><li><p></p></li><br>*<li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> idList 主键ID列表<br>*/<br>boolean removeByIds(Collection&lt;? extends Serializable&gt; idList);<h1 id=\"h1-updatebyid\"><a name=\"updateById\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>updateById</h1>/**</li><li><p></p><li>根据 ID 选择修改</li><li><p></p></li><br>*<li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> entity 实体对象<br>*/<br>boolean updateById(T entity);<h1 id=\"h1-update\"><a name=\"update\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>update</h1>/**</li><li><p></p><li>根据 whereEntity 条件，更新记录</li><li><p></p></li><br>*<li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> entity        实体对象</li><li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> updateWrapper 实体对象封装操作类 {<a href=\"https://github.com/link\" title=\"@link\" class=\"at-link\">@link</a> com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper}<br>*/<br>boolean update(T entity, Wrapper<T> updateWrapper);<h1 id=\"h1-updatebatchbyid\"><a name=\"updateBatchById\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>updateBatchById</h1>/**</li><li><p></p><li>根据ID 批量更新</li><li><p></p></li><br>*<li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> entityList 实体对象集合</li><li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> batchSize  更新批次数量<br>*/<br>boolean updateBatchById(Collection<T> entityList, int batchSize);<h1 id=\"h1-saveorupdate\"><a name=\"saveOrUpdate\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>saveOrUpdate</h1>/**</li><li><p></p><li>TableId 注解存在更新记录，否插入一条记录</li><li><p></p></li><br>*<li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> entity 实体对象<br>*/<br>boolean saveOrUpdate(T entity);<h1 id=\"h1-getbyid\"><a name=\"getById\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>getById</h1>/**</li><li><p></p><li>根据 ID 查询</li><li><p></p></li><br>*<li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> id 主键ID<br>*/<br>T getById(Serializable id);<h1 id=\"h1-listbyids\"><a name=\"listByIds\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>listByIds</h1>/**</li><li><p></p><li>查询（根据ID 批量查询）</li><li><p></p></li><br>*<li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> idList 主键ID列表<br>*/<br>Collection<T> listByIds(Collection&lt;? extends Serializable&gt; idList);<h1 id=\"h1-listbymap\"><a name=\"listByMap\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>listByMap</h1>/**</li><li><p></p><li>查询（根据 columnMap 条件）</li><li><p></p></li><br>*<li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> columnMap 表字段 map 对象<br>*/<br>Collection<T> listByMap(Map<String, Object> columnMap);<h1 id=\"h1-getone\"><a name=\"getOne\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>getOne</h1>/**</li><li><p></p><li>根据 Wrapper，查询一条记录</li><li><p></p></li><br>*<li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> queryWrapper 实体对象封装操作类 {<a href=\"https://github.com/link\" title=\"@link\" class=\"at-link\">@link</a> com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}</li><li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> throwEx      有多个 result 是否抛出异常<br>*/<br>T getOne(Wrapper<T> queryWrapper, boolean throwEx);<h1 id=\"h1-getmap\"><a name=\"getMap\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>getMap</h1>/**</li><li><p></p><li>根据 Wrapper，查询一条记录</li><li><p></p></li><br>*<li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> queryWrapper 实体对象封装操作类 {<a href=\"https://github.com/link\" title=\"@link\" class=\"at-link\">@link</a> com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}<br>*/<br>Map<String, Object> getMap(Wrapper<T> queryWrapper);<h1 id=\"h1-getobj\"><a name=\"getObj\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>getObj</h1>/**</li><li><p></p><li>根据 Wrapper，查询一条记录</li><li><p></p></li><br>*<li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> queryWrapper 实体对象封装操作类 {<a href=\"https://github.com/link\" title=\"@link\" class=\"at-link\">@link</a> com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}<br>*/<br>Object getObj(Wrapper<T> queryWrapper);<h1 id=\"h1-count\"><a name=\"count\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>count</h1>/**</li><li><p></p><li>根据 Wrapper 条件，查询总记录数</li><li><p></p></li><br>*<li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> queryWrapper 实体对象封装操作类 {<a href=\"https://github.com/link\" title=\"@link\" class=\"at-link\">@link</a> com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}<br>*/<br>int count(Wrapper<T> queryWrapper);<h1 id=\"h1-list\"><a name=\"list\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>list</h1>/**</li><li><p></p><li>查询列表</li><li><p></p></li><br>*<li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> queryWrapper 实体对象封装操作类 {<a href=\"https://github.com/link\" title=\"@link\" class=\"at-link\">@link</a> com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}<br>*/<br>List<T> list(Wrapper<T> queryWrapper);<h1 id=\"h1-page\"><a name=\"page\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>page</h1>/**</li><li><p></p><li>翻页查询</li><li><p></p></li><br>*<li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> page         翻页对象</li><li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> queryWrapper 实体对象封装操作类 {<a href=\"https://github.com/link\" title=\"@link\" class=\"at-link\">@link</a> com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}<br>*/<br>IPage<T> page(IPage<T> page, Wrapper<T> queryWrapper);<h1 id=\"h1-listmaps\"><a name=\"listMaps\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>listMaps</h1>/**</li><li><p></p><li>查询列表</li><li><p></p></li><br>*<li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> queryWrapper 实体对象封装操作类 {<a href=\"https://github.com/link\" title=\"@link\" class=\"at-link\">@link</a> com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}<br>*/<br>List<Map<String, Object>&gt; listMaps(Wrapper<T> queryWrapper);<h1 id=\"h1-listobjs\"><a name=\"listObjs\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>listObjs</h1>/**</li><li><p></p><li>根据 Wrapper 条件，查询全部记录</li><li><p></p></li><br>*<li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> queryWrapper 实体对象封装操作类 {<a href=\"https://github.com/link\" title=\"@link\" class=\"at-link\">@link</a> com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}<br>*/<br>List<Object> listObjs(Wrapper<T> queryWrapper);<h1 id=\"h1-pagemaps\"><a name=\"pageMaps\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>pageMaps</h1>/**</li><li><p></p><li>翻页查询</li><li><p></p></li><br>*<li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> page         翻页对象</li><li><a href=\"https://github.com/param\" title=\"@param\" class=\"at-link\">@param</a> queryWrapper 实体对象封装操作类 {<a href=\"https://github.com/link\" title=\"@link\" class=\"at-link\">@link</a> com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}<br>*/<br>IPage<Map<String, Object>&gt; pageMaps(IPage<T> page, Wrapper<t> queryWrapper);</t></ul>\r\n', '1', null, '2', '0', '2018-10-17 14:42:16', '1');
INSERT INTO `blog_topic` VALUES ('37', '31214', '<p>141414</p>\r\n', '1', null, '0', '0', '2018-10-17 14:42:21', '0');
INSERT INTO `blog_topic` VALUES ('38', '测试', '<p><strong>阿达</strong></p>\r\n', '1', null, '0', '0', '2018-10-16 11:50:55', '1');
INSERT INTO `blog_topic` VALUES ('39', '测试', '<p><strong>阿达</strong></p>\r\n', '1', null, '0', '0', '2018-10-16 11:50:55', '1');
INSERT INTO `blog_topic` VALUES ('40', '测试', '<p><strong>阿达</strong></p>\r\n', '1', null, '0', '0', '2018-10-16 11:50:55', '1');
INSERT INTO `blog_topic` VALUES ('41', '测试', '<p><strong>阿达</strong></p>\r\n', '1', null, '0', '0', '2018-10-16 11:50:55', '1');
INSERT INTO `blog_topic` VALUES ('42', '测试', '<p><strong>阿达</strong></p>\r\n', '1', null, '0', '0', '2018-10-16 11:50:55', '1');
INSERT INTO `blog_topic` VALUES ('43', '测试', '<p><strong>阿达</strong></p>\r\n', '1', null, '0', '0', '2018-10-16 11:50:55', '1');
INSERT INTO `blog_topic` VALUES ('44', '测试', '<p><strong>阿达</strong></p>\r\n', '1', null, '0', '0', '2018-10-16 11:50:55', '1');
INSERT INTO `blog_topic` VALUES ('45', '测试', '<p><strong>阿达</strong></p>\r\n', '1', null, '0', '0', '2018-10-16 11:50:55', '1');
INSERT INTO `blog_topic` VALUES ('46', '测试', '<p><strong>阿达</strong></p>\r\n', '1', null, '0', '0', '2018-10-16 11:50:55', '1');
INSERT INTO `blog_topic` VALUES ('47', '测试', '<p><strong>阿达</strong></p>\r\n', '1', null, '0', '0', '2018-10-16 11:50:55', '1');
INSERT INTO `blog_topic` VALUES ('48', '测试', '<p><strong>阿达</strong></p>\r\n', '1', null, '0', '0', '2018-10-16 11:50:55', '1');
INSERT INTO `blog_topic` VALUES ('49', '测试', '<p><strong>阿达</strong></p>\r\n', '1', null, '0', '0', '2018-10-16 11:50:55', '1');
INSERT INTO `blog_topic` VALUES ('50', '测试', '<p><strong>阿达</strong></p>\r\n', '1', null, '0', '0', '2018-10-16 11:50:55', '1');
INSERT INTO `blog_topic` VALUES ('51', '测试', '<p><strong>阿达</strong></p>\r\n', '1', null, '0', '0', '2018-10-16 11:50:55', '1');
INSERT INTO `blog_topic` VALUES ('52', '测试', '<p><strong>阿达</strong></p>\r\n', '1', null, '0', '0', '2018-10-16 11:50:55', '1');
INSERT INTO `blog_topic` VALUES ('53', '测试', '<p><strong>阿达</strong></p>\r\n', '1', null, '0', '0', '2018-10-16 11:50:55', '1');
INSERT INTO `blog_topic` VALUES ('54', '测试', '<p><strong>阿达</strong></p>\r\n', '1', null, '0', '0', '2018-10-16 11:50:55', '1');
INSERT INTO `blog_topic` VALUES ('55', '测试', '<p><strong>阿达</strong></p>\r\n', '1', null, '0', '0', '2018-10-16 11:50:55', '1');
INSERT INTO `blog_topic` VALUES ('56', '测试', '<p><strong>阿达</strong></p>\r\n', '1', null, '0', '0', '2018-10-16 11:50:55', '1');
INSERT INTO `blog_topic` VALUES ('57', '测试', '<p><strong>阿达</strong></p>\r\n', '1', null, '0', '0', '2018-10-16 11:50:55', '1');
INSERT INTO `blog_topic` VALUES ('58', '测试', '<p><strong>阿达</strong></p>\r\n', '1', null, '0', '0', '2018-10-16 11:50:55', '1');
INSERT INTO `blog_topic` VALUES ('59', '测试', '<p><strong>阿达</strong></p>\r\n', '1', null, '0', '0', '2018-10-16 11:50:55', '1');
INSERT INTO `blog_topic` VALUES ('60', '测试', '<p><strong>阿达</strong></p>\r\n', '1', null, '0', '0', '2018-10-16 11:50:55', '1');
INSERT INTO `blog_topic` VALUES ('61', '测试', '<p><strong>阿达</strong></p>\r\n', '1', null, '0', '0', '2018-10-16 11:50:55', '1');
INSERT INTO `blog_topic` VALUES ('62', '测试', '<p><strong>阿达</strong></p>\r\n', '1', null, '0', '0', '2018-10-16 11:50:55', '1');
INSERT INTO `blog_topic` VALUES ('63', '测试', '<p><strong>阿达</strong></p>\r\n', '1', null, '0', '0', '2018-10-16 11:50:55', '1');
INSERT INTO `blog_topic` VALUES ('64', '测试', '<p><strong>阿达</strong></p>\r\n', '1', null, '0', '0', '2018-10-16 11:50:55', '1');
INSERT INTO `blog_topic` VALUES ('65', '测试', '<p><strong>阿达</strong></p>\r\n', '1', null, '0', '0', '2018-10-16 11:50:55', '1');
INSERT INTO `blog_topic` VALUES ('66', '测试', '<p><strong>阿达</strong></p>\r\n', '1', null, '0', '0', '2018-10-16 11:50:55', '1');
INSERT INTO `blog_topic` VALUES ('67', '测试', '<p><strong>阿达</strong></p>\r\n', '1', null, '0', '0', '2018-10-16 11:50:55', '1');
INSERT INTO `blog_topic` VALUES ('68', '测试', '<p><strong>阿达</strong></p>\r\n', '1', null, '0', '0', '2018-10-16 11:50:55', '1');
INSERT INTO `blog_topic` VALUES ('69', '测试', '<p><strong>阿达</strong></p>\r\n', '1', null, '0', '0', '2018-10-16 11:50:55', '1');
INSERT INTO `blog_topic` VALUES ('70', '测试', '<p><strong>阿达</strong></p>\r\n', '1', null, '0', '0', '2018-10-16 11:50:55', '1');
INSERT INTO `blog_topic` VALUES ('71', '测试', '<p><strong>阿达</strong></p>\r\n', '1', null, '0', '0', '2018-10-16 11:50:55', '1');
INSERT INTO `blog_topic` VALUES ('72', '测试', '<p><strong>阿达</strong></p>\r\n', '1', null, '0', '0', '2018-10-16 11:50:55', '1');
INSERT INTO `blog_topic` VALUES ('73', '测试', '<p><strong>阿达</strong></p>\r\n', '1', null, '0', '0', '2018-10-16 11:50:55', '1');
INSERT INTO `blog_topic` VALUES ('74', '驱蚊器二', '<p>请问额群</p>\r\n', '1', '4', '0', '0', '2018-10-26 09:37:19', '-1');
INSERT INTO `blog_topic` VALUES ('78', '这不是测试', '<p><code>Do you remember me？</code></p>\r\n', '1', '4', '0', '0', '2018-10-26 11:13:02', '1');
INSERT INTO `blog_topic` VALUES ('81', '这不是测试', '<h4 id=\"h4-u554Au5927u5927\"><a name=\"啊大大\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>啊大大</h4>', '1', '4', '0', '0', '2018-10-26 11:29:18', '1');
INSERT INTO `blog_topic` VALUES ('82', 'qweq', '<p>wqewq</p>\r\n', '1', '4', '0', '0', '2018-10-26 11:30:44', '1');
INSERT INTO `blog_topic` VALUES ('83', 'wqqwq', '<p>qweq</p>\r\n', '1', '4', '0', '0', '2018-10-26 11:31:13', '1');
INSERT INTO `blog_topic` VALUES ('84', '好吧 这是测试', '<p>驱蚊器翁</p>\r\n', '1', null, '0', '0', '2018-10-26 11:32:58', '1');
INSERT INTO `blog_topic` VALUES ('85', 'qwewq', '<p>wqeqwe</p>\r\n', '1', '5', '84', '0', '2018-10-26 11:34:41', '1');
INSERT INTO `blog_topic` VALUES ('86', '委屈委屈二', '<p>请问额群</p>\r\n', '1', '4', '150', '0', '2018-10-26 11:37:16', '1');

-- ----------------------------
-- Table structure for blog_user
-- ----------------------------
DROP TABLE IF EXISTS `blog_user`;
CREATE TABLE `blog_user` (
  `id` bigint(20) NOT NULL,
  `login_name` varchar(50) DEFAULT NULL,
  `user_name` varchar(50) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `salt` varchar(50) DEFAULT NULL,
  `avatar` varchar(100) DEFAULT NULL,
  `profile` varchar(255) DEFAULT NULL,
  `last_login_at` datetime DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of blog_user
-- ----------------------------
INSERT INTO `blog_user` VALUES ('1', '18156816237', '管理员', 'G/nHaBOamdpKJY0Nh9ptmQ==', '27fea06199fdb62cf486792cba05fa59', null, null, '2018-10-30 14:54:42', '2018-10-11 06:40:51');
INSERT INTO `blog_user` VALUES ('2', '18856496310', '管理员', 'G/nHaBOamdpKJY0Nh9ptmQ==', '126a04a19c620cd9c578356e359c76e2', null, null, null, '2018-10-22 06:59:49');

-- ----------------------------
-- Table structure for blog_user_category
-- ----------------------------
DROP TABLE IF EXISTS `blog_user_category`;
CREATE TABLE `blog_user_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of blog_user_category
-- ----------------------------
INSERT INTO `blog_user_category` VALUES ('4', 'ces', '1', '2', '2018-10-26 17:25:53');
INSERT INTO `blog_user_category` VALUES ('5', '生活', '1', '3', '2018-10-29 10:17:42');
INSERT INTO `blog_user_category` VALUES ('6', '技术', '1', '4', '2018-10-29 10:17:56');

-- ----------------------------
-- Table structure for blog_user_outreach
-- ----------------------------
DROP TABLE IF EXISTS `blog_user_outreach`;
CREATE TABLE `blog_user_outreach` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='GitHub/邮箱/Gitee/等等';

-- ----------------------------
-- Records of blog_user_outreach
-- ----------------------------
