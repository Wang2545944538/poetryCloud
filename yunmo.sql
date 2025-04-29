/*
 Navicat Premium Data Transfer

 Source Server         : user
 Source Server Type    : MySQL
 Source Server Version : 80035 (8.0.35)
 Source Host           : localhost:3306
 Source Schema         : yunmo

 Target Server Type    : MySQL
 Target Server Version : 80035 (8.0.35)
 File Encoding         : 65001

 Date: 24/09/2024 08:47:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity`  (
  `active_id` int NOT NULL AUTO_INCREMENT,
  `active_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `active_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `active_type_id` tinyint NULL DEFAULT NULL,
  `active_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `active_profile` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL,
  PRIMARY KEY (`active_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of activity
-- ----------------------------
INSERT INTO `activity` VALUES (1, '诗词知识快问快答', '2023-09-26 00:00:00', 1, '1.jpg', '快速回答关于诗词的问题，测试反应速度和知识面。');
INSERT INTO `activity` VALUES (2, '唐诗宋词问答', '2023-09-27 00:00:00', 1, '2.jpg', '通过问答形式深入探讨唐诗宋词的魅力。');
INSERT INTO `activity` VALUES (3, '古诗词拼图大赛', '2023-09-28 00:00:00', 2, '3.jpg', '参与者需要完成一幅由古诗词组成的拼图，最快完成者获胜。');
INSERT INTO `activity` VALUES (4, '经典诗词拼图挑战', '2023-09-29 00:00:00', 2, '4.jpg', '尝试复原一系列经典诗词的拼图，享受视觉与思维的乐趣。');
INSERT INTO `activity` VALUES (5, '兰亭雅集', '2023-09-30 00:00:00', 3, '5.jpg', '仿效王羲之《兰亭序》的精神，举行一场文人雅士的聚会。');
INSERT INTO `activity` VALUES (6, '书法与诗词工作坊', '2023-10-01 00:00:00', 3, '6.jpg', '结合书法练习与诗词创作，体验传统文化之美。');
INSERT INTO `activity` VALUES (7, '墨香诗会', '2023-10-02 00:00:00', 4, '7.jpg', '邀请诗人和爱好者共聚一堂，交流分享最新的诗词作品。');
INSERT INTO `activity` VALUES (8, '诗歌朗诵之夜', '2023-10-03 00:00:00', 4, '8.jpg', '在宁静的夜晚，聆听优美的诗歌朗诵，感受文字的力量。');
INSERT INTO `activity` VALUES (9, '即兴作诗比赛', '2023-10-04 00:00:00', 5, '9.jpg', '现场给出关键词，参赛者需迅速创作出一首诗。');
INSERT INTO `activity` VALUES (10, '主题诗创作工作坊', '2023-10-05 00:00:00', 5, '10.jpg', '围绕特定主题进行创作，指导如何构思并写出优秀的诗句。');

-- ----------------------------
-- Table structure for activity_type
-- ----------------------------
DROP TABLE IF EXISTS `activity_type`;
CREATE TABLE `activity_type`  (
  `active_type_id` int NOT NULL AUTO_INCREMENT,
  `activity_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`active_type_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of activity_type
-- ----------------------------
INSERT INTO `activity_type` VALUES (1, '答题');
INSERT INTO `activity_type` VALUES (2, '拼图');
INSERT INTO `activity_type` VALUES (3, '兰亭序语');
INSERT INTO `activity_type` VALUES (4, '墨香雅集');
INSERT INTO `activity_type` VALUES (5, '点字成诗');

-- ----------------------------
-- Table structure for author
-- ----------------------------
DROP TABLE IF EXISTS `author`;
CREATE TABLE `author`  (
  `author_id` int NOT NULL AUTO_INCREMENT COMMENT '作者id',
  `author_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '姓名',
  `author_gender` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '性别',
  `dynasty_id` int NULL DEFAULT NULL COMMENT '朝代id',
  `author_intro` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '简介',
  `author_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '头像图片',
  `author_style` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '诗风',
  PRIMARY KEY (`author_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of author
-- ----------------------------
INSERT INTO `author` VALUES (1, '屈原', '男', 0, '屈原是中国战国时期的著名诗人，以其忧国忧民的精神和卓越的文学成就而闻名。', 'quyuan.jpg', '楚辞');
INSERT INTO `author` VALUES (2, '司马迁', '男', 2, '司马迁是西汉著名的史学家、文学家，著有《史记》。', 'simaqian.jpg', '史学家');
INSERT INTO `author` VALUES (3, '司马相如', '男', 2, '司马相如是西汉著名的文学家，以辞赋著称。', 'simaxiangru.jpg', '辞赋家');
INSERT INTO `author` VALUES (4, '陶渊明', '男', 4, '陶渊明是东晋时期的著名诗人，以其田园诗著称，被誉为“田园诗人之祖”。', 'taoyuanming.jpg', '田园诗人');
INSERT INTO `author` VALUES (5, '杜甫', '男', 7, '杜甫是唐代著名的诗人，以其深沉的诗风和关注社会现实的作品而著称。', 'dufu.jpg', '现实主义');
INSERT INTO `author` VALUES (6, '白居易', '男', 7, '白居易是唐代著名的诗人，以其通俗易懂的诗风和关注社会现实的作品而著称。', 'baijuyi.jpg', '白居易诗风');
INSERT INTO `author` VALUES (7, '王维', '男', 7, '王维是唐代著名的诗人，以其山水田园诗著称，被誉为“诗中有画，画中有诗”。', 'wangwei.jpg', '山水田园');
INSERT INTO `author` VALUES (8, '苏轼', '男', 9, '苏轼是宋代著名的文学家、政治家、画家，以其豪放的诗风和才华横溢的作品而著称。', 'sushi.jpg', '豪放派');
INSERT INTO `author` VALUES (9, '李清照', '女', 9, '李清照是宋代著名的女词人，以其婉约词风和情感真挚的作品而著称。', 'liqingzhao.jpg', '婉约派');
INSERT INTO `author` VALUES (10, '陆游', '男', 9, '陆游是南宋时期的著名诗人，以其爱国诗篇和关注社会现实的作品而著称。', 'luyou.jpg', '爱国诗篇');
INSERT INTO `author` VALUES (11, '杨万里', '男', 9, '杨万里是南宋时期的著名诗人，以其清新脱俗的诗风和关注自然风光的作品而著称。', 'yangwanli.jpg', '清新脱俗');
INSERT INTO `author` VALUES (12, '李白', '男', 7, '李白是唐代著名的诗人，以其豪放的诗风和丰富的想象力而闻名。', 'libai.jpg', '浪漫主义');
INSERT INTO `author` VALUES (13, '韩愈', '男', 7, '韩愈是唐代著名的文学家，以其倡导古文运动和文章雄浑有力而著称。', 'hanyu.jpg', '古文运动');
INSERT INTO `author` VALUES (14, '柳宗元', '男', 7, '柳宗元是唐代著名的文学家，以其散文和政论文章而闻名，与韩愈并称“韩柳”。', 'liuzongyuan.jpg', '散文家');
INSERT INTO `author` VALUES (15, '欧阳修', '男', 9, '欧阳修是北宋著名的文学家、政治家，以其散文和诗歌成就而著称。', 'ouyangxiu.jpg', '文学家');
INSERT INTO `author` VALUES (16, '苏洵', '男', 9, '苏洵是北宋著名的文学家，与其子苏轼、苏辙并称“三苏”，以散文著称。', 'suxun.jpg', '散文家');
INSERT INTO `author` VALUES (17, '苏辙', '男', 9, '苏辙是北宋著名的文学家，与其父苏洵、兄苏轼并称“三苏”，以散文和政论文章著称。', 'suzhe.jpg', '散文家');
INSERT INTO `author` VALUES (18, '王安石', '男', 9, '王安石是北宋著名的政治家、文学家、改革家，以其变法和诗歌成就而著称。', 'wanganshi.jpg', '政治家');
INSERT INTO `author` VALUES (19, '曾巩', '男', 9, '曾巩是北宋著名的文学家，以散文和政论文章著称，是“唐宋八大家”之一。', 'zenggong.jpg', '散文家');

-- ----------------------------
-- Table structure for buy_type
-- ----------------------------
DROP TABLE IF EXISTS `buy_type`;
CREATE TABLE `buy_type`  (
  `buy_id` int NOT NULL AUTO_INCREMENT,
  `buy_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`buy_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of buy_type
-- ----------------------------
INSERT INTO `buy_type` VALUES (1, '积分兑换');
INSERT INTO `buy_type` VALUES (2, '直售');

-- ----------------------------
-- Table structure for collection
-- ----------------------------
DROP TABLE IF EXISTS `collection`;
CREATE TABLE `collection`  (
  `collection_id` int NOT NULL AUTO_INCREMENT,
  `collection_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `collection_avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '诗集头像',
  `collection_count` int NULL DEFAULT 0,
  PRIMARY KEY (`collection_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of collection
-- ----------------------------
INSERT INTO `collection` VALUES (1, '李白诗集', 'author_img_12.jpg', 3);
INSERT INTO `collection` VALUES (2, '杜甫诗集', 'author_img_5.jpg', 3);
INSERT INTO `collection` VALUES (3, '白居易诗集', 'author_img_6.jpg', 3);
INSERT INTO `collection` VALUES (4, '王维诗集', 'author_img_7.jpg', 3);
INSERT INTO `collection` VALUES (5, '苏轼诗集', 'author_img_8.jpg', 2);
INSERT INTO `collection` VALUES (6, '李清照词集', 'author_img_9.jpg', 3);
INSERT INTO `collection` VALUES (7, '陆游诗集', 'author_img_10.jpg', 2);
INSERT INTO `collection` VALUES (8, '屈原诗集', 'author_img_11.jpg', 2);
INSERT INTO `collection` VALUES (9, '司马相如诗集', 'author_img_3.jpg', 1);
INSERT INTO `collection` VALUES (10, '陶渊明诗集', 'author_img_4.jpg', 2);
INSERT INTO `collection` VALUES (11, '杨万里诗集', 'author_img_11.jpg', 3);
INSERT INTO `collection` VALUES (18, '4545', 'defaultPoems.jpg', 0);
INSERT INTO `collection` VALUES (19, '565656', 'defaultPoems.jpg', 0);

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `comment_id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '内容',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `blog_id` int NULL DEFAULT NULL COMMENT '帖子id',
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of comment
-- ----------------------------

-- ----------------------------
-- Table structure for dynasty
-- ----------------------------
DROP TABLE IF EXISTS `dynasty`;
CREATE TABLE `dynasty`  (
  `dynasty_id` int NOT NULL AUTO_INCREMENT,
  `dynasty_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`dynasty_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of dynasty
-- ----------------------------
INSERT INTO `dynasty` VALUES (0, '先秦');
INSERT INTO `dynasty` VALUES (1, '秦朝');
INSERT INTO `dynasty` VALUES (2, '汉朝');
INSERT INTO `dynasty` VALUES (3, '三国');
INSERT INTO `dynasty` VALUES (4, '晋朝');
INSERT INTO `dynasty` VALUES (5, '南北朝');
INSERT INTO `dynasty` VALUES (6, '隋朝');
INSERT INTO `dynasty` VALUES (7, '唐朝');
INSERT INTO `dynasty` VALUES (8, '五代十国');
INSERT INTO `dynasty` VALUES (9, '宋朝');
INSERT INTO `dynasty` VALUES (10, '元朝');
INSERT INTO `dynasty` VALUES (11, '明朝');
INSERT INTO `dynasty` VALUES (12, '清朝');

-- ----------------------------
-- Table structure for genre
-- ----------------------------
DROP TABLE IF EXISTS `genre`;
CREATE TABLE `genre`  (
  `genre_id` int NOT NULL AUTO_INCREMENT,
  `g_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`genre_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of genre
-- ----------------------------
INSERT INTO `genre` VALUES (1, '诗');
INSERT INTO `genre` VALUES (2, '词');
INSERT INTO `genre` VALUES (3, '曲');
INSERT INTO `genre` VALUES (4, '赋');

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `good_id` int NOT NULL AUTO_INCREMENT,
  `good_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `good_count` int NULL DEFAULT NULL,
  `price` double(10, 2) NULL DEFAULT NULL,
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `Typeid` int NULL DEFAULT NULL,
  `point_price` double(10, 2) NULL DEFAULT NULL,
  `buy_id` int NULL DEFAULT NULL,
  `detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL,
  PRIMARY KEY (`good_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (1, '精美书签', 100, NULL, '../src/images/img/书签.jpg', 1, 1000.00, 1, '古风诗词书签套装，采用优质黄铜材质，表面刻有古典诗词和唯美图案，如山水、花鸟等。书签边缘打磨光滑，不伤书籍，既实用又具有浓厚的文艺气息。');
INSERT INTO `goods` VALUES (2, '伴手礼', 150, 400.00, '../src/images/img/包装.jpg', 3, NULL, 2, '精选各地特色手工艺品，承载着浓厚的地方文化，是赠送亲朋好友的绝佳选择。每一份伴手礼都蕴含着匠人的心血与智慧，传递着深深的情谊。');
INSERT INTO `goods` VALUES (3, '古诗书签', 111, 90.00, '../src/images/img/古诗书签.jpg', 1, NULL, 1, '将古典诗词与书签完美结合，既实用又具有文化内涵。让阅读成为一种享受，沉浸在诗意的世界中，感受文字的魅力。');
INSERT INTO `goods` VALUES (4, '古风梳子', 199, NULL, '../src/images/img/古风梳子.webp', 3, 1235.00, 1, '采用优质木材制作，造型典雅，手感光滑。古风梳子不仅梳理秀发，更能传递古典美学的韵味，让您时刻保持优雅气质。');
INSERT INTO `goods` VALUES (5, '古风家具', 88, 500.00, '../src/images/img/家具.webp', 3, NULL, 2, '融合传统工艺与现代审美，打造出独具匠心的家具作品。古风家具为您的生活空间增添一抹古韵，让您在家中也能体验到诗意的生活。');
INSERT INTO `goods` VALUES (6, '手扇', 100, NULL, '../src/images/img/手扇.jpg', 3, 3000.00, 1, '选用优质面料，手工绘制精美图案，兼具实用与美观。手扇摇曳间，清风徐来，为您带来夏日清凉。');
INSERT INTO `goods` VALUES (7, '折扇', 100, NULL, '../src/images/img/折扇.jpg', 1, 3500.00, 1, '精湛的工艺，折叠自如，展开后呈现一幅美丽的画卷。折扇不仅是纳凉神器，更是文人雅士品位的象征。');
INSERT INTO `goods` VALUES (8, '清芳摆饰', 348, 350.00, '../src/images/img/清芳摆饰.jpg', 1, NULL, 2, '以自然花卉为主题，采用环保材料制作，为您的生活空间增添一抹清新气息，让家更具温馨氛围。');
INSERT INTO `goods` VALUES (9, '灯', 100, 500.00, '../src/images/img/灯.jpg', 3, NULL, 1, '古典造型与现代光源相结合，为您带来温馨的照明体验。灯光下，家的每一个角落都充满诗意。');
INSERT INTO `goods` VALUES (10, '画册', 100, NULL, '../src/images/img/画册.webp', 2, 3400.00, 1, '精选历代名家画作，高清印刷，让您近距离欣赏艺术之美，提升审美情趣。');
INSERT INTO `goods` VALUES (11, '笔墨纸砚', 120, 120.00, '../src/images/img/笔墨纸砚.webp', 3, NULL, 2, '文房四宝，传承千年文化。一套优质的笔墨纸砚，是书法爱好者的必备之物，让您挥毫泼墨，尽享书写之趣。');
INSERT INTO `goods` VALUES (12, '茶 叶', 100, 500.00, '../src/images/img/茶 叶.jpg', 3, NULL, 1, '精选优质茶叶，香气四溢，口感醇厚。一壶好茶，让您在忙碌的生活中找到片刻宁静。');
INSERT INTO `goods` VALUES (13, '装饰', 99, NULL, '../src/images/img/装饰.jpg', 1, 3900.00, 1, '融合传统元素与现代设计，为您的生活空间增添艺术气息，让家更具个性。');
INSERT INTO `goods` VALUES (14, '诗集', 100, NULL, '../src/images/img/诗集.jpg', 2, 1200.00, 1, '收录历代诗人佳作，让您在阅读中感受诗词的魅力，领略传统文化的精髓。');
INSERT INTO `goods` VALUES (15, '趣味诗人墙贴', 100, NULL, '../src/images/img/趣味诗人墙贴.webp', 3, 3700.00, 1, '以诗人形象为主题，生动有趣，为您的家居环境增添一抹文化色彩。');
INSERT INTO `goods` VALUES (16, '轻柔手扇', 250, 250.00, '../src/images/img/轻柔手扇.png', 1, NULL, 2, '细腻柔软的材质，轻盈便携，为您带来舒适的清凉体验。手扇上的图案充满诗意，彰显品味。');

-- ----------------------------
-- Table structure for like
-- ----------------------------
DROP TABLE IF EXISTS `like`;
CREATE TABLE `like`  (
  `lid` int NOT NULL AUTO_INCREMENT,
  `collection_id` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`lid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of like
-- ----------------------------

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `message_id` int NOT NULL AUTO_INCREMENT,
  `from_id` int NULL DEFAULT NULL,
  `to_id` int NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`message_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of message
-- ----------------------------

-- ----------------------------
-- Table structure for models
-- ----------------------------
DROP TABLE IF EXISTS `models`;
CREATE TABLE `models`  (
  `model_id` int NOT NULL AUTO_INCREMENT,
  `model_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `parent_models` int NULL DEFAULT NULL,
  PRIMARY KEY (`model_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of models
-- ----------------------------
INSERT INTO `models` VALUES (1, '系统管理', '/systemAdministration', 0);
INSERT INTO `models` VALUES (2, '诗词创作管理', '/poetryCreation', 0);
INSERT INTO `models` VALUES (3, '诗词库管理', '/poetryLibrary', 0);
INSERT INTO `models` VALUES (4, '市集管理', '/goods', 0);
INSERT INTO `models` VALUES (5, '诗人管理', '/poet', 0);
INSERT INTO `models` VALUES (7, '诗会管理', '/poetrySociety', 0);
INSERT INTO `models` VALUES (9, '用户列表', '/userList', 1);
INSERT INTO `models` VALUES (12, '诗词发布审核', '/poetryPublish', 2);
INSERT INTO `models` VALUES (13, '诗词信息', '/poetryInformation', 3);
INSERT INTO `models` VALUES (14, '诗词搜索', '/poetrySearch', 3);
INSERT INTO `models` VALUES (15, '商品列表', '/goodsList', 4);
INSERT INTO `models` VALUES (16, '商品分类', '/goodsCategory', 4);
INSERT INTO `models` VALUES (17, '订单管理', '/orderManagement', 4);
INSERT INTO `models` VALUES (18, '诗人列表', '/poetList', 5);
INSERT INTO `models` VALUES (19, '添加诗人', '/poetAdd', 5);
INSERT INTO `models` VALUES (23, '题库管理', '/questionBank', 7);
INSERT INTO `models` VALUES (24, '活动管理', '/activity', 7);

-- ----------------------------
-- Table structure for options
-- ----------------------------
DROP TABLE IF EXISTS `options`;
CREATE TABLE `options`  (
  `options_id` int NOT NULL AUTO_INCREMENT,
  `A` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `B` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `C` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `D` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`options_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of options
-- ----------------------------
INSERT INTO `options` VALUES (1, '白日依山尽', '黄河入海流', '欲穷千里目', '更上一层楼');
INSERT INTO `options` VALUES (2, '床前明月光', '地上鞋两双', '疑是地上霜', '举头望明月');
INSERT INTO `options` VALUES (3, '千山鸟飞绝', '万径人踪灭', '孤舟蓑笠翁', '独钓寒江雪');
INSERT INTO `options` VALUES (4, '会当凌绝顶', '一览众山小', '举世皆浊我独清', '众人皆醉我独醒');
INSERT INTO `options` VALUES (5, '春眠不觉晓', '处处闻啼鸟', '夜来风雨声', '花落知多少');
INSERT INTO `options` VALUES (6, '海内存知己', '天涯若比邻', '无为在歧路', '儿女共沾巾');
INSERT INTO `options` VALUES (7, '大漠孤烟直', '长河落日圆', '萧关逢候骑', '都护在燕然');
INSERT INTO `options` VALUES (8, '月落乌啼霜满天', '江枫渔火对愁眠', '姑苏城外寒山寺', '夜半钟声到客船');
INSERT INTO `options` VALUES (9, '独在异乡为异客', '每逢佳节倍思亲', '遥知兄弟登高处', '遍插茱萸少一人');
INSERT INTO `options` VALUES (10, '青青园中葵', '朝露待日晞', '阳春布德泽', '万物生光辉');
INSERT INTO `options` VALUES (11, '故人西辞黄鹤楼', '烟花三月下扬州', '孤帆远影碧空尽', '唯见长江天际流');
INSERT INTO `options` VALUES (12, '两个黄鹂鸣翠柳', '一行白鹭上青天', '窗含西岭千秋雪', '门泊东吴万里船');
INSERT INTO `options` VALUES (13, '红豆生南国', '春来发几枝', '愿君多采撷', '此物最相思');
INSERT INTO `options` VALUES (14, '床前明月光', '疑是地上霜', '举头望明月', '低头思故乡');
INSERT INTO `options` VALUES (15, '千山鹤唳', '万径人踪', '孤云独去闲', '相望始登高');
INSERT INTO `options` VALUES (16, '诗仙', '诗圣', '诗鬼', '诗豪');
INSERT INTO `options` VALUES (17, '屈原', '陶渊明', '曹操', '王之涣');
INSERT INTO `options` VALUES (18, '杜甫', '白居易', '李白', '王维');
INSERT INTO `options` VALUES (19, '王勃', '贺知章', '王之涣', '陈子昂');
INSERT INTO `options` VALUES (20, '李白', '杜甫', '陶渊明', '孟浩然');
INSERT INTO `options` VALUES (21, '李清照', '朱淑真', '薛涛', '晏殊');
INSERT INTO `options` VALUES (22, '杜甫', '王维', '白居易', '韩愈');
INSERT INTO `options` VALUES (23, '《诗经》', '《楚辞》', '《乐府诗集》', '《全唐诗》');
INSERT INTO `options` VALUES (24, '陈子昂', '高适', '王昌龄', '贾岛');
INSERT INTO `options` VALUES (25, '苏轼', '辛弃疾', '晏殊', '李清照');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `buy_count` int NULL DEFAULT NULL,
  `ispay` int NULL DEFAULT 0,
  `good_id` int NULL DEFAULT NULL,
  `buy_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of orders
-- ----------------------------

-- ----------------------------
-- Table structure for personalinformation
-- ----------------------------
DROP TABLE IF EXISTS `personalinformation`;
CREATE TABLE `personalinformation`  (
  `personal_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `age` tinyint NULL DEFAULT NULL,
  `money` double(10, 2) NULL DEFAULT NULL,
  `point` int NULL DEFAULT NULL COMMENT '积分',
  PRIMARY KEY (`personal_id`) USING BTREE,
  CONSTRAINT `personalinformation_chk_1` CHECK (`sex` in (_utf8mb4'男',_utf8mb4'女'))
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of personalinformation
-- ----------------------------

-- ----------------------------
-- Table structure for poem
-- ----------------------------
DROP TABLE IF EXISTS `poem`;
CREATE TABLE `poem`  (
  `poem_id` int NOT NULL AUTO_INCREMENT,
  `author_id` int NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL,
  `genre_id` int NULL DEFAULT NULL COMMENT '体裁id',
  `theme_id` int NULL DEFAULT NULL COMMENT '题材id',
  `pf_id` int NULL DEFAULT NULL COMMENT '诗词信息id',
  `status` int NULL DEFAULT NULL COMMENT '诗词状态(0/1)0为未审核',
  `isself` int NULL DEFAULT NULL COMMENT '是否自创(0/1)1为自创',
  `intro` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`poem_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 99 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of poem
-- ----------------------------
INSERT INTO `poem` VALUES (1, 1, '楚辞 · 九歌 · 其一 · 东皇太一', '吉日兮辰良，穆将愉兮上皇。抚长剑兮玉珥，璆锵鸣兮琳琅。瑶席兮玉瑱，盍将把兮琼芳。蕙肴蒸兮兰藉，奠桂酒兮椒浆。扬枹兮拊鼓，疏缓节兮安歌。陈竽瑟兮浩倡。灵偃蹇兮姣服，芳菲菲兮满堂。五音纷兮繁会，君欣欣兮乐康。', 4, 11, 1, NULL, 0, '东皇太一是《九歌》中的第一首诗，祭祀东皇太一，表达了诗人对神灵的敬仰。');
INSERT INTO `poem` VALUES (2, 1, '楚辞 · 九歌 · 其二 · 云中君', '浴兰汤兮沐芳，华采衣兮若英。灵连蜷兮既留，烂昭昭兮未央。蹇将憺兮寿宫，与日月兮齐光。龙驾兮帝服，聊翱游兮周章。灵皇皇兮既降，猋远举兮云中。览冀州兮有馀，横四海兮焉穷。思夫君兮太息，极劳心兮忡忡。', 4, 11, 2, NULL, 0, '云中君是《九歌》中的第二首诗，祭祀云中君，赞美了云中的神灵。');
INSERT INTO `poem` VALUES (3, 3, '凤囚凰', '凤兮凤兮归故乡，遨游四海求其凰。时未遇兮无所将，何悟今兮升斯堂。有艳淑女在闺房，室迩人遐毒我肠。何缘交颈为鸳鸯，胡颉颃兮共翱翔。凤兮凤兮从我栖，得托孳尾永为好。交情通意心和谐，中夜相从知者谁？女萝发馨香，菟丝断人肠。山有木兮木有枝，心悦君兮君不知。', 3, 7, 3, NULL, 0, '《凤囚凰》是司马相如的一篇著名辞赋，表达了作者对自由和爱情的向往。');
INSERT INTO `poem` VALUES (4, 4, '归园田居（其一）', '少无适俗韵，性本爱丘山。误落尘网中，一去三十年。羁鸟恋旧林，池鱼思故渊。开荒南野际，守拙归园田。方宅十余亩，草屋八九间。榆柳荫后檐，桃李罗堂前。暧暧远人村，依依墟里烟。狗吠深巷中，鸡鸣桑树颠。户庭无尘杂，虚室有余闲。久在樊笼里，复得返自然。', 1, 9, 4, NULL, 0, '《归园田居》是陶渊明的一首著名诗，表达了诗人对田园生活的向往和归隐的决心。');
INSERT INTO `poem` VALUES (5, 4, '饮酒（其五）', '结庐在人境，而无车马喧。问君何能尔？心远地自偏。采菊东篱下，悠然见南山。山气日夕佳，飞鸟相与还。此中有真意，欲辨已忘言。', 1, 9, 5, NULL, 0, '《饮酒》是陶渊明的一首著名诗，表达了诗人对自然和宁静生活的向往。');
INSERT INTO `poem` VALUES (6, 5, '春望', '国破山河在，城春草木深。感时花溅泪，恨别鸟惊心。烽火连三月，家书抵万金。白头搔更短，浑欲不胜簪。', 1, 10, 6, NULL, 0, '《春望》是杜甫的一首著名诗，表达了他对国家命运的忧虑和对亲人的思念。');
INSERT INTO `poem` VALUES (7, 5, '月夜忆舍弟', '戍鼓断人行，边秋一雁声。露从今夜白，月是故乡明。有弟皆分散，无家问死生。寄书长不达，况乃未休兵。', 1, 4, 7, NULL, 0, '《月夜忆舍弟》是杜甫的一首著名诗，表达了他对弟弟的思念和对战争带来的苦难的感慨。');
INSERT INTO `poem` VALUES (8, 5, '登高', '风急天高猿啸哀，渚清沙白鸟飞回。无边落木萧萧下，不尽长江滚滚来。万里悲秋常作客，百年多病独登台。艰难苦恨繁霜鬓，潦倒新停浊酒杯。', 1, 4, 8, NULL, 0, '《登高》是杜甫的一首著名诗，表达了他对人生苦难和时光流逝的感慨。');
INSERT INTO `poem` VALUES (9, 6, '赋得古原草送别', '离离原上草，一岁一枯荣。野火烧不尽，春风吹又生。远芳侵古道，晴翠接荒城。又送王孙去，萋萋满别情。', 1, 6, 9, NULL, 0, '《赋得古原草送别》是白居易的一首著名诗，表达了诗人对离别的感慨和对生命的坚韧不拔的赞美。');
INSERT INTO `poem` VALUES (10, 6, '问刘十九', '绿蚁新醅酒，红泥小火炉。晚来天欲雪，能饮一杯无？', 1, 4, 10, NULL, 0, '《问刘十九》是白居易的一首著名诗，表达了他对朋友的思念和对简单生活的向往。');
INSERT INTO `poem` VALUES (11, 6, '钱塘湖春行', '孤山寺北贾亭西，水面初平云脚低。几处早莺争暖树，谁家新燕啄春泥。乱花渐欲迷人眼，浅草才能没马蹄。最爱湖东行不足，绿杨阴里白沙堤。', 1, 5, 11, NULL, 0, '《钱塘湖春行》是白居易的一首著名诗，描绘了春天钱塘湖的美景，表达了对自然之美的喜爱。');
INSERT INTO `poem` VALUES (12, 7, '山居秋暝', '空山新雨后，天气晚来秋。明月松间照，清泉石上流。竹喧归浣女，莲动下渔舟。随意春芳歇，王孙自可留。', 1, 9, 12, NULL, 0, '《山居秋暝》是王维的一首著名诗，描绘了山居秋天的宁静景色，表达了诗人对自然的热爱和对隐逸生活的向往。');
INSERT INTO `poem` VALUES (13, 7, '相思', '红豆生南国，春来发几枝。愿君多采撷，此物最相思。', 1, 7, 13, NULL, 0, '《相思》是王维的一首著名诗，通过红豆寄托了诗人对远方亲友的思念之情。');
INSERT INTO `poem` VALUES (14, 7, '鹿柴', '空山不见人，但闻人语响。返景入深林，复照青苔上。', 1, 2, 14, NULL, 0, '《鹿柴》是王维的一首著名诗，通过描写空山中的景象，表达了诗人对自然之美的赞叹和对隐逸生活的向往。');
INSERT INTO `poem` VALUES (15, 8, '惠崇春江晓景', '竹外桃花三两枝，春江水暖鸭先知。蒌蒿满地芦芽短，正是河豚欲上时。', 1, 5, 15, NULL, 0, '《惠崇春江晓景》是苏轼的一首七言绝句，描绘了春天江边的景色，表达了诗人对自然的观察和感受。');
INSERT INTO `poem` VALUES (16, 8, '饮湖上初晴后雨', '水光潋滟晴方好，山色空蒙雨亦奇。欲把西湖比西子，淡妆浓抹总相宜。', 1, 5, 16, NULL, 0, '《饮湖上初晴后雨》是苏轼的一首七言绝句，通过对西湖的描绘，表达了诗人对自然景色的赞美和对美的追求。');
INSERT INTO `poem` VALUES (17, 9, '如梦令', '昨夜雨疏风骤，浓睡不消残酒。试问卷帘人，却道海棠依旧。知否？知否？应是绿肥红瘦。', 2, 4, 17, NULL, 0, '《如梦令》是李清照的一首著名词，表达了诗人对春景的感慨和对逝去时光的追忆。');
INSERT INTO `poem` VALUES (18, 9, '声声慢', '寻寻觅觅，冷冷清清，凄凄惨惨戚戚。乍暖还寒时候，最难将息。三杯两盏淡酒，怎敌他、晚来风急？雁过也，正伤心，却是旧时相识。', 2, 7, 18, NULL, 0, '《声声慢》是李清照的一首著名词，通过对秋天景色的描绘，表达了诗人对逝去爱情的怀念和对生活的感慨。');
INSERT INTO `poem` VALUES (19, 9, '一剪梅', '红藕香残玉簟秋。轻解罗裳，独上兰舟。云中谁寄锦书来？雁字回时，月满西楼。花自飘零水自流。一种相思，两处闲愁。此情无计可消除，才下眉头，却上心头。', 2, 7, 19, NULL, 0, '《一剪梅》是李清照的一首著名词，通过描绘秋天的景色，表达了诗人对远方爱人的思念和对爱情的深切感受。');
INSERT INTO `poem` VALUES (20, 11, '晓出净慈寺送林子方', '毕竟西湖六月中，风光不与四时同。接天莲叶无穷碧，映日荷花别样红。', 1, 5, 20, NULL, 0, '《晓出净慈寺送林子方》是杨万里的一首著名诗，描绘了西湖六月的美景，表达了诗人对自然之美的赞美。');
INSERT INTO `poem` VALUES (21, 11, '小池', '泉眼无声惜细流，树阴照水爱晴柔。小荷才露尖尖角，早有蜻蜓立上头。', 1, 5, 21, NULL, 0, '《小池》是杨万里的一首著名诗，通过对小池塘的描绘，表达了诗人对自然景色的细腻观察和喜爱。');
INSERT INTO `poem` VALUES (22, 11, '宿新市徐公店', '篱落疏疏一径深，树头花落未成阴。儿童急走追黄蝶，飞入菜花无处寻。', 1, 2, 22, NULL, 0, '《宿新市徐公店》是杨万里的一首著名诗，通过对乡村景色的描绘，表达了诗人对自然和生活的热爱。');
INSERT INTO `poem` VALUES (23, 12, '静夜思', '床前明月光，疑是地上霜。举头望明月，低头思故乡。', 1, 4, 23, NULL, 0, '《静夜思》是李白的一首著名诗，表达了他对故乡的思念和对月光的感慨。');
INSERT INTO `poem` VALUES (24, 12, '望庐山瀑布', '日照香炉生紫烟，遥看瀑布挂前川。飞流直下三千尺，疑是银河落九天。', 1, 1, 24, NULL, 0, '《望庐山瀑布》是李白的一首著名诗，通过对庐山瀑布的描绘，表达了诗人对自然景色的赞美和对壮丽景色的震撼。');
INSERT INTO `poem` VALUES (25, 12, '将进酒', '君不见黄河之水天上来，奔流到海不复回。君不见高堂明镜悲白发，朝如青丝暮成雪。人生得意须尽欢，莫使金樽空对月。天生我材必有用，千金散尽还复来。烹羊宰牛且为乐，会须一饮三百杯。岑夫子，丹丘生，将进酒，杯莫停。与君歌一曲，请君为我倾耳听。钟鼓馔玉不足贵，但愿长醉不复醒。古来圣贤皆寂寞，惟有饮者留其名。陈王昔时宴平乐，斗酒十千恣欢谑。主人何为言少钱，径须沽取对君酌。五花马，千金裘，呼儿将出换美酒，与尔同销万古愁。', 1, 1, 25, NULL, 0, '《将进酒》是李白的一首著名诗，表达了他对人生的豁达态度和对豪放生活的向往。');
INSERT INTO `poem` VALUES (26, 10, '秋夜将晓出篱门迎凉有感二首', '三万里河东入海，五千仞岳上摩天。遗民泪尽胡尘里，南望王师又一年。', 1, 10, 26, NULL, 0, '《秋夜将晓出篱门迎凉有感二首》是陆游的一首著名诗，表达了他对国家的忧虑和对收复失地的渴望。');
INSERT INTO `poem` VALUES (27, 10, '示儿', '死去元知万事空，但悲不见九州同。王师北定中原日，家祭无忘告乃翁。', 1, 10, 27, NULL, 0, '《示儿》是陆游的一首著名诗，表达了他对国家的忧虑和对后代子孙的期望。');
INSERT INTO `poem` VALUES (28, 31, '春日忆李白', '白也诗无敌，飘然思不群。清新庾开府，俊逸鲍参军。渭北春天树，江东日暮云。何时一樽酒，重与细论文。', 1, 1, 1, 1, 1, '春日怀念诗仙李白');
INSERT INTO `poem` VALUES (29, 31, '夏日绝句', '生当作人杰，死亦为鬼雄。至今思项羽，不肯过江东。', 2, 2, 2, 1, 1, '表达豪情壮志的绝句');
INSERT INTO `poem` VALUES (30, 31, '秋夜将晓出篱门迎凉有感', '三更灯火五更鸡，正是男儿读书时。黑发不知勤学早，白发方悔读书迟。', 3, 3, 3, 1, 1, '劝学诗，抒发对时光流逝的感慨');
INSERT INTO `poem` VALUES (31, 31, '冬日归乡', '故园东望路漫漫，双袖龙钟泪不干。马上相逢无纸笔，凭君传语报平安。', 4, 4, 4, 1, 1, '描写冬日归乡的景象及思乡之情');
INSERT INTO `poem` VALUES (56, 31, '5645', '45454', 0, 0, 0, 1, 1, NULL);
INSERT INTO `poem` VALUES (74, 31, '123', '1245', 0, 0, 0, 1, 1, NULL);
INSERT INTO `poem` VALUES (77, 31, '1111', '222333', 0, 0, 0, 1, 1, NULL);
INSERT INTO `poem` VALUES (78, 31, '22222', '11111222zaa', 0, 0, 0, 1, 1, NULL);
INSERT INTO `poem` VALUES (85, 31, '11', '666666', 0, 0, 0, 1, 1, NULL);
INSERT INTO `poem` VALUES (86, 31, '66666', '666667778888', 0, 0, 0, 2, 1, NULL);
INSERT INTO `poem` VALUES (87, 31, '123456', '123456', 0, 0, 0, 2, 1, NULL);
INSERT INTO `poem` VALUES (88, 31, '111', '1112223334444', 0, 0, 0, 1, 1, NULL);
INSERT INTO `poem` VALUES (89, 31, '2222', '222233', 0, 0, 0, 0, 1, NULL);
INSERT INTO `poem` VALUES (90, 31, '123', '123', 0, 0, 0, 2, 1, NULL);
INSERT INTO `poem` VALUES (91, 31, '1234', '2345', 0, 0, 0, 2, 1, NULL);
INSERT INTO `poem` VALUES (92, 31, '2345', '3456', 0, 0, 0, 0, 1, NULL);
INSERT INTO `poem` VALUES (93, 31, '56789876', '345876', 0, 0, 0, 0, 1, NULL);
INSERT INTO `poem` VALUES (94, 31, '24356', '6567876', 0, 0, 0, 0, 1, NULL);
INSERT INTO `poem` VALUES (95, 31, '3546688', '34567', 0, 0, 0, 0, 1, NULL);
INSERT INTO `poem` VALUES (96, 31, '456789', '5678', 0, 0, 0, 1, 1, NULL);
INSERT INTO `poem` VALUES (97, 31, '09876', '54676', 0, 0, 0, 1, 1, NULL);
INSERT INTO `poem` VALUES (98, 31, '56789', '84567', 0, 0, 0, 0, 1, NULL);

-- ----------------------------
-- Table structure for poem_collection
-- ----------------------------
DROP TABLE IF EXISTS `poem_collection`;
CREATE TABLE `poem_collection`  (
  `collection_id` int NULL DEFAULT NULL,
  `poem_id` int NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of poem_collection
-- ----------------------------
INSERT INTO `poem_collection` VALUES (1, 23);
INSERT INTO `poem_collection` VALUES (1, 23);
INSERT INTO `poem_collection` VALUES (1, 24);
INSERT INTO `poem_collection` VALUES (1, 25);
INSERT INTO `poem_collection` VALUES (2, 6);
INSERT INTO `poem_collection` VALUES (2, 7);
INSERT INTO `poem_collection` VALUES (2, 8);
INSERT INTO `poem_collection` VALUES (3, 9);
INSERT INTO `poem_collection` VALUES (3, 10);
INSERT INTO `poem_collection` VALUES (3, 11);
INSERT INTO `poem_collection` VALUES (4, 12);
INSERT INTO `poem_collection` VALUES (4, 13);
INSERT INTO `poem_collection` VALUES (4, 14);
INSERT INTO `poem_collection` VALUES (5, 15);
INSERT INTO `poem_collection` VALUES (5, 16);
INSERT INTO `poem_collection` VALUES (7, 26);
INSERT INTO `poem_collection` VALUES (7, 27);
INSERT INTO `poem_collection` VALUES (8, 1);
INSERT INTO `poem_collection` VALUES (8, 2);
INSERT INTO `poem_collection` VALUES (9, 3);
INSERT INTO `poem_collection` VALUES (10, 4);
INSERT INTO `poem_collection` VALUES (10, 5);
INSERT INTO `poem_collection` VALUES (11, 20);
INSERT INTO `poem_collection` VALUES (11, 21);
INSERT INTO `poem_collection` VALUES (11, 22);
INSERT INTO `poem_collection` VALUES (6, 17);
INSERT INTO `poem_collection` VALUES (6, 18);
INSERT INTO `poem_collection` VALUES (6, 19);

-- ----------------------------
-- Table structure for poeminformation
-- ----------------------------
DROP TABLE IF EXISTS `poeminformation`;
CREATE TABLE `poeminformation`  (
  `pf_id` int NOT NULL AUTO_INCREMENT,
  `pingxi` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `comments` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `translation` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `jiping` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`pf_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of poeminformation
-- ----------------------------
INSERT INTO `poeminformation` VALUES (1, '这是《九歌》的开首篇，是祭祀东皇太一的祭歌。全诗自始至终只是对祭礼仪式和祭神场面的描述，充分表达了人们对春神东皇太一的敬重、欢迎与祈望，希望春神多多赐福人间，给人类的生命繁衍、农作物生长带来福音。此诗篇幅虽短，却层次清晰，描写生动，场面隆重，气氛热烈。', '九歌：《楚辞》篇名。原为传说中的一种远古歌曲的名称，屈原据民间祭神乐歌改作或加工而成，共十一篇。东皇太一：天神名，具体是何种神祇，历代学者说法不一。吉日：吉祥的日子。辰良：即良辰。穆：恭敬肃穆。愉：同“娱”，此处指娱神，使神灵愉快、欢乐。上皇：即东皇太一。珥：指剑柄上端像两耳突出的饰品。璆（qiú）：形容玉石相悬击的样子。锵：象声词，此处指佩玉相碰撞而发出的声响。瑶席：珍贵华美的席垫。瑶，美玉。玉瑱（zhèn）：同“镇”，用玉做的压席器物。盍（hé）：同“合”，聚集在一起。琼芳：指赤玉般美丽的花朵。琼，赤色玉。蕙：香草名，兰科植物。肴蒸：大块的肉。藉（jiè）：垫底用的东西。椒浆：用有香味的椒浸泡的美酒。枹（fú）：鼓槌。拊（fǔ）：敲击。安歌：歌声徐缓安详。陈：此处指乐器声大作。浩倡：倡同“唱”；浩倡指大声唱，气势浩荡。灵：楚人称神、巫为灵，这里指以歌舞娱神的群巫。偃蹇：指舞姿优美的样子。姣服：美丽的服饰。芳菲菲：香气浓郁的样子。五音：指宫、商、角、徵、羽五种音调。繁会：众音汇成一片，指齐奏。君：此处指东皇太一。', '吉祥日子好时辰，恭敬肃穆娱上皇。手抚长剑玉为环，佩玉铿锵声清亮。华贵坐席玉镇边，满把香花吐芬芳。蕙草裹肉兰为垫，祭奠美酒飘桂香。高举鼓槌把鼓敲，节拍疏缓歌声响，竽瑟齐奏乐音强。群巫娇舞服饰美，香气四溢香满堂。众音齐会响四方，上皇欢欣乐安康。', '洪兴祖《楚辞补注》：五臣云：每篇之目皆楚之神名。所以列于篇后者，亦犹《毛诗》题章之趣。太一，星名，天之尊神。祠在楚东，以配东帝，故云东皇。朱熹《楚辞集注》：东皇太一，一本上有祠字，下诸篇同。太一，神名，天之尊神，祠在楚东，以配东帝，故云东皇。《汉书》云：“天神贵者太一，太一佐曰五帝。中宫天极星，其一明者，太一常居也。”《淮南子》曰：“太微者，太一之庭。紫宫者，太一之居。”此篇言其竭诚尽礼以事神，而愿神之欣悦安宁，以寄人臣尽忠竭力，爱君无已之意，所谓全篇之比也。');
INSERT INTO `poeminformation` VALUES (2, '《九歌·云中君》是屈原的组诗《九歌》中的一首楚辞。云中君为男性，是云中之神，在神话中云神名叫豐隆，又名屏翳。诗中对云的飘忽回环的形象给予了生动的描写。这首诗无论人的唱词、神的唱词，都从不同角度表现出云神的特征，表现出人对云神的乞盼、思念，与神对人礼敬的报答。一往深情，溢于言表。', '兰汤：兰草沁入其中而带有香味的热水。此下四句为祭巫所唱。华采：使之华丽。若英：像花朵一样。灵：灵子，祭祀中有神灵附身的巫觋。连蜷：回环婉曲的样子，此处指舞蹈时身体婀娜摆动的姿态。既留：已经留下来。烂昭昭：光明灿烂的样子。闻一多《九歌解诂》：「昭昭，小光。」烂，分散的光。央：尽。蹇（jiǎn）：《康熙字典》引《離騷》注：「蹇，詞也。」憺：安。寿宫：供神之处。此下四句扮云中君的巫所唱。龙驾：龙车。此指驾龙车。帝服：指五方帝之服，言服有青黄赤白黑之五色。周章：王逸《楚辞章句》：「犹周流也。言云神居无常处，动则翱翔，周流往来且游戏也。」聊：姑且。周章：周游。灵：此处指云中君。此下二句祭巫所唱。皇皇：同「煌煌」，光明灿烂的样子。猋（biāo）：形容词，疾速。举：高飞。览：看。冀州：古代中国分为九州，冀州为九州之首，因此以代指全中国。此下二句云中君所唱。横：横布或横行。焉：怎么。焉穷：无穷无尽。穷，完、尽。君：云中君。此下二句祭巫所唱。', '用兰汤沐浴带上一身芳香，让衣服鲜艳多彩像花朵一样。灵子盘旋起舞神灵仍然附身，他身上不断地放出闪闪神光。我将在寿宫逗留安乐宴享，与太阳和月亮一样放射光芒。乘驾龙车上插五方之帝的旌旗，姑且在人间遨游观览四方。辉煌的云神已经降临，突然间像旋风一样升向云中。俯览中原我目光及于九州之外，横行四海我的踪迹无尽无穷。思念你云神啊我只有叹息，无比的愁思真让人忧心忡忡！', '中国屈原学会常务理事汤漳平：在《九歌》中，除了《礼魂》这个尾歌只有二十七字外，《云中君》是最短的一首。然而就在这短短的诗中，作者细腻地描绘了云朵在广阔的天宇中的各种形态，而把这种形态又转换成拟人的云神的形象：连蜷的云朵，炫目的光彩，煌煌的服舆。翱游周章的举止，急速升降、去留的动态，以至铺天盖地而来，「览冀州」而「横四海」的气势，在人们眼前连续展现。这一幕幕形象生动的行云图画．给人留下极其深刻的印象。在这里，天上的云彩与想象中的云神融合而一，是那样鲜明、生动，然而又是那样神奇、变幻莫测。状物之妙，令人叹为观止。');
INSERT INTO `poeminformation` VALUES (3, '《凤求凰》传说是汉代的古琴曲，演绎了司马相如与卓文君的爱情故事。以「凤求凰」为通体比兴，不仅包含了热烈的求偶，而且也象征着男女主人公理想的非凡，志趣的高尚，知音的默契等丰富的意蕴。', '无', '凤鸟啊凤鸟啊回到了家乡，行踪无定游览天下寻求心中凰鸟。未遇凰鸟之时啊不知所往，怎能悟解今日登门后心中之所感！有美丽娴静的女子在居室，居处虽近人却远而残虐我的心肠。如何能做恩爱的交颈鸳鸯，使我这凤鸟与你这凰鸟一同翔游！凰鸟啊凰鸟啊愿你我相依，共同哺育生子并永远做我的配偶。情投意合而两心和睦谐顺，半夜与我互相追随又有谁能知晓？展开双翼一起远走而高飞，徒然为你感念相思而使我心悲伤。', '无');
INSERT INTO `poeminformation` VALUES (4, '此诗是东晋末期南朝宋初期大诗人陶渊明创作的组诗《归园田居五首》的第一首。全诗从对官场生活的强烈厌倦，写到田园风光的美好动人，农村生活的舒心愉快，流露了一种如释重负的心情，表达了对自然和自由的热爱。', '少无适俗韵：少年时就没有迎合世俗的本性。少，指少年时代。适俗，适应世俗，俗，世俗。韵，本性、气质，一作「愿」。性本爱丘山：天性原本热爱山川田园（生活）。性，天性，本性。尘网：指尘世，官府生活污浊而又拘束，犹如网罗。这里指仕途。三十年：有人认为是「十三年」之误（陶渊明做官十三年）。一说，此处是三又十年之意（习惯说法是十又三年），诗人意感「一去十三年」音调嫌平，故将十三年改为倒文。羁（ji）鸟：笼中之鸟。恋：一作「眷」。池鱼：池塘之鱼。鸟恋旧林、鱼思故渊，借喻自己怀恋旧居。野：一作「亩」。际：间。守拙（zhuō）归园田：固守住愚拙，回乡过田园生活。持守愚拙的本性，即不学巧伪，不争名利。方宅：宅地方圆。一说，「方」通「旁」。荫（yìn）：荫蔽。罗：罗列。暧暧（ài）：昏暗、模糊。依依：轻柔而缓慢的飘升。墟里：村落。户庭：门庭。尘杂：尘俗杂事。虚室：空室。余闲：闲暇。樊（fán）笼：蓄鸟工具，这里比喻官场生活。樊，藩篱，栅栏。返自然：指归耕园田。', '少小时就没有随俗气韵，自己的天性是热爱自然。偶失足落入了仕途罗网，转眼间离田园已十馀年。笼中鸟常依恋往日山林，池里鱼向往着从前深渊。我愿在南野际开垦荒地，保持着拙朴性归耕田园。绕房宅方圆有十馀亩地，还有那茅屋草舍八九间。榆柳树荫盖着房屋后檐，争春的桃与李列满院前。远处的邻村舍依稀可见，村落里飘荡着袅袅炊烟。深巷中传来了几声狗吠，桑树顶有雄鸡不停啼唤。庭院内没有那尘杂干扰，静室里有的是安适悠闲。久困于樊笼里毫无自由，我今日总算又归返林山。', '宋代苏轼：外枯而中膏，似淡而实美。现代朱光潜：如秋谭月影，彻底澄莹。现代龚望《陶渊明集评议》：起首一句：名言；凡人百思千虑，不能得。');
INSERT INTO `poeminformation` VALUES (5, '本篇是《饮酒》二十首中的第五首。诗中写了悠然自得的情，也写了幽美淡远的景，在情景交融的境界中蕴含着万物各得其所、委运任化的哲理；这哲理又被诗人提炼、浓缩到“心远地自偏”、“此中有真意”等警句，整首诗的韵调也更显得隽秀深长。', '结庐：构筑房屋。结，建造、构筑。庐：简陋的房屋。人境：人聚居的地方。尔：这样。日夕：傍晚。相与：相伴。', '居住在人世间，却没有车马的喧嚣。问我为何能如此，只要心志高远，自然就会觉得所处地方僻静了。在东篱之下采摘菊花，悠然间，那远处的南山映入眼帘。山中的气息与傍晚的景色十分好，有飞鸟结着伴儿归来。这里面蕴含着人生的真正意义，想要辨识，却不知怎样表达。', '叶梦得《石林诗话》：晋人多言饮酒，有至沉醉者，此未必意真在酒。盖时方艰难，人各罹祸，惟托于醉，可以粗远世故。谭元春《古诗归》：妙在题是饮酒，只当感遇诗、杂诗，所以为远。黄文焕《陶诗析义》：陶诗凡数首相连者，章法必深于布置。《饮酒》二十首尤为淋漓变化，义多对竖，意则环应。康发祥：《饮酒》诗，昌黎谓其有托而逃，盖靖节退归后，世变日甚，故得酒必尽醉。其卒章曰：“但恨多谬误，君当恕醉人。”观此二语，则以醉而逃世网，洵可知也。');
INSERT INTO `poeminformation` VALUES (6, '天宝十四年（西元七五五年）十一月，安禄山起兵叛唐。次年六月，叛军攻陷潼关，唐玄宗匆忙逃往四川。七月，太子李亨即位于灵武（今属宁夏），世称肃宗，改元至德。杜少陵闻讯，即将家属安顿在都州，隻身一人投奔肃宗朝廷，结果不幸在途中被叛军俘获，解送至长安，后因官职卑微才未被囚禁。至德二年春，身处沦陷区的少陵目睹了长安城一片萧条零落的景象，百感交集，便写下了这首传诵千古的名作。这首诗的前四句写春日长安凄惨破败的景象，饱含着兴衰感慨；后四句写诗人挂念亲人、心系国事的情怀，充溢着凄苦哀思。这首诗格律严整，颔联分别以「感时花溅泪」应首联国破之叹，以「恨别鸟惊心」应颈联思家之忧，尾联则强调忧思之深导致发白而稀疏，对仗精巧，声情悲壮，表现了诗人爱国之情。', '国：国都，指长安（今陕西西安）。破：陷落。山河在：旧日的山河仍然存在。城：长安城。草木深：指人烟稀少。感时：为国家的时局而感伤。溅泪：流泪。恨别：怅恨离别。烽火：古时边防报警的烟火，这里指安史之乱的战火。三月：正月、二月、三月。抵：值，相当。白头：这里指白髮。搔：用手指轻轻的抓。浑：简直。欲：想，要，就要。不胜：受不住，不能。簪：一种束发的首饰。古代男子蓄长髮，成年后束髮于头顶，用簪子横插住，以免散开。', '长安沦陷，国家破碎，只有山河依旧；春天来了，人烟稀少的长安城里草木茂密。感于战败的时局，看到花开而潸然泪下，内心惆怅怨恨，听到鸟鸣而心惊胆战。连绵的战火已经延续了一个春天，家书难得，一封抵得上万两黄金。愁绪缠绕，搔头思考，白发越搔越短，简直要不能插簪了。', '宋·司马温公《温公续诗话》：古人为诗，贵于意在言外，使人思而得之，故言之者无罪，闻之者足以耐也。近世诗人，唯杜子美最得诗人之体，如「国破山河在，城春草木深。感时花溅泪，恨别鸟惊心」。山河在，明无余物矣；草木深，明无人矣：花鸟，平时可娱之物，见之而泣，闻之而悲，则时可知矣。他皆类此，不可遍举。宋·方虛谷《瀛奎律髓》：此第一等好诗。想天宝、至德以至大历之乱，不忍读也。明·鍾退谷《唐诗归》：所谓愁思，看春不当春也。明·胡赤城《唐音癸签》：对偶未尝不精，而纵横变幻，尽越陈规，浓淡浅深，动夺天巧，百代而下，当无复继。');
INSERT INTO `poeminformation` VALUES (7, '无', '无', '无', '无');
INSERT INTO `poeminformation` VALUES (8, '无', '无', '无', '无');
INSERT INTO `poeminformation` VALUES (9, '无', '无', '无', '无');
INSERT INTO `poeminformation` VALUES (10, '无', '无', '无', '无');
INSERT INTO `poeminformation` VALUES (11, '无', '无', '无', '无');
INSERT INTO `poeminformation` VALUES (12, '无', '无', '无', '无');
INSERT INTO `poeminformation` VALUES (13, '无', '无', '无', '无');
INSERT INTO `poeminformation` VALUES (14, '无', '无', '无', '无');
INSERT INTO `poeminformation` VALUES (15, '无', '无', '无', '无');
INSERT INTO `poeminformation` VALUES (16, '无', '无', '无', '无');
INSERT INTO `poeminformation` VALUES (17, '无', '无', '无', '无');
INSERT INTO `poeminformation` VALUES (18, '无', '无', '无', '无');
INSERT INTO `poeminformation` VALUES (19, '无', '无', '无', '无');
INSERT INTO `poeminformation` VALUES (20, '无', '无', '无', '无');
INSERT INTO `poeminformation` VALUES (21, '无', '无', '无', '无');
INSERT INTO `poeminformation` VALUES (22, '无', '无', '无', '无');
INSERT INTO `poeminformation` VALUES (23, '无', '无', '无', '无');
INSERT INTO `poeminformation` VALUES (24, '无', '无', '无', '无');
INSERT INTO `poeminformation` VALUES (25, '无', '无', '无', '无');
INSERT INTO `poeminformation` VALUES (26, '无', '无', '无', '无');
INSERT INTO `poeminformation` VALUES (27, '无', '无', '无', '无');

-- ----------------------------
-- Table structure for product_type
-- ----------------------------
DROP TABLE IF EXISTS `product_type`;
CREATE TABLE `product_type`  (
  `Typeid` int NOT NULL AUTO_INCREMENT,
  `Typename` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`Typeid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of product_type
-- ----------------------------

-- ----------------------------
-- Table structure for questionbank
-- ----------------------------
DROP TABLE IF EXISTS `questionbank`;
CREATE TABLE `questionbank`  (
  `questionBank_id` int NOT NULL AUTO_INCREMENT,
  `question` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `options_id` int NULL DEFAULT NULL,
  `answer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`questionBank_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of questionbank
-- ----------------------------
INSERT INTO `questionbank` VALUES (1, '“白日依山尽”的下一句是什么？', 1, '黄河入海流');
INSERT INTO `questionbank` VALUES (2, '“床前明月光”的下一句是什么？', 2, '疑是地上霜');
INSERT INTO `questionbank` VALUES (3, '“千山鸟飞绝”的下一句是什么？', 3, '万径人踪灭');
INSERT INTO `questionbank` VALUES (4, '“会当凌绝顶”的下一句是什么？', 4, '一览众山小');
INSERT INTO `questionbank` VALUES (5, '“春眠不觉晓”的下一句是什么？', 5, '处处闻啼鸟');
INSERT INTO `questionbank` VALUES (6, '“海内存知己”的下一句是什么？', 6, '天涯若比邻');
INSERT INTO `questionbank` VALUES (7, '“大漠孤烟直”的下一句是什么？', 7, '长河落日圆');
INSERT INTO `questionbank` VALUES (8, '“月落乌啼霜满天”的下一句是什么？', 8, '江枫渔火对愁眠');
INSERT INTO `questionbank` VALUES (9, '“独在异乡为异客”的下一句是什么？', 9, '每逢佳节倍思亲');
INSERT INTO `questionbank` VALUES (10, '“青青园中葵”的下一句是什么？', 10, '朝露待日晞');
INSERT INTO `questionbank` VALUES (11, '“故人西辞黄鹤楼”的下一句是什么？', 11, '烟花三月下扬州');
INSERT INTO `questionbank` VALUES (12, '“两个黄鹂鸣翠柳”的下一句是什么？', 12, '一行白鹭上青天');
INSERT INTO `questionbank` VALUES (13, '“红豆生南国”的下一句是什么？', 13, '春来发几枝');
INSERT INTO `questionbank` VALUES (14, '“千山鹤唳”的下一句是什么？', 14, '万径人踪');
INSERT INTO `questionbank` VALUES (15, '“孤云独去闲”的下一句是什么？', 15, '相望始登高');
INSERT INTO `questionbank` VALUES (16, '唐代著名诗人杜甫被尊称为什么？', 16, '诗圣');
INSERT INTO `questionbank` VALUES (17, '“青青子衿，悠悠我心”出自哪位古代诗人的作品？', 17, '曹操');
INSERT INTO `questionbank` VALUES (18, '“桃花潭水深千尺，不及汪伦送我情”中的“我”是指哪位诗人？', 18, '李白');
INSERT INTO `questionbank` VALUES (19, '哪位诗人被称为“初唐四杰”之一？', 19, '王勃');
INSERT INTO `questionbank` VALUES (20, '“采菊东篱下，悠然见南山”是哪位诗人的名句？', 20, '陶渊明');
INSERT INTO `questionbank` VALUES (21, '“至今思项羽，不肯过江东”出自哪位宋代女诗人的作品？', 21, '李清照');
INSERT INTO `questionbank` VALUES (22, '哪位诗人被誉为“诗中有画，画中有诗”？', 22, '王维');
INSERT INTO `questionbank` VALUES (23, '“窈窕淑女，君子好逑”出自哪部古代文学作品？', 23, '《诗经》');
INSERT INTO `questionbank` VALUES (24, '哪位诗人被称为“诗骨”？', 24, '陈子昂');
INSERT INTO `questionbank` VALUES (25, '“大江东去，浪淘尽，千古风流人物”是哪位诗人的名句？', 25, '苏轼');

-- ----------------------------
-- Table structure for relation
-- ----------------------------
DROP TABLE IF EXISTS `relation`;
CREATE TABLE `relation`  (
  `blog_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NULL DEFAULT NULL,
  `zan` int NULL DEFAULT NULL COMMENT '是否点赞(0/1) 0是没点',
  `sc` int NULL DEFAULT NULL COMMENT '是否收藏(0/1)',
  PRIMARY KEY (`blog_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of relation
-- ----------------------------

-- ----------------------------
-- Table structure for theme
-- ----------------------------
DROP TABLE IF EXISTS `theme`;
CREATE TABLE `theme`  (
  `theme_id` int NOT NULL AUTO_INCREMENT,
  `t_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`theme_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of theme
-- ----------------------------
INSERT INTO `theme` VALUES (1, '怀古');
INSERT INTO `theme` VALUES (2, '赠别');
INSERT INTO `theme` VALUES (3, '咏物');
INSERT INTO `theme` VALUES (4, '抒情');
INSERT INTO `theme` VALUES (5, '言志');
INSERT INTO `theme` VALUES (6, '讽刺');
INSERT INTO `theme` VALUES (7, '写景');
INSERT INTO `theme` VALUES (8, '农事');
INSERT INTO `theme` VALUES (9, '边塞');
INSERT INTO `theme` VALUES (10, '战争');
INSERT INTO `theme` VALUES (11, '闺怨');
INSERT INTO `theme` VALUES (12, '闲适');
INSERT INTO `theme` VALUES (13, '田园');
INSERT INTO `theme` VALUES (14, '悼亡');
INSERT INTO `theme` VALUES (15, '爱国');
INSERT INTO `theme` VALUES (16, '乡土');
INSERT INTO `theme` VALUES (17, '行旅');
INSERT INTO `theme` VALUES (18, '感慨');
INSERT INTO `theme` VALUES (19, '咏史');
INSERT INTO `theme` VALUES (20, '婉约');

-- ----------------------------
-- Table structure for type
-- ----------------------------
DROP TABLE IF EXISTS `type`;
CREATE TABLE `type`  (
  `type_id` int NOT NULL,
  `type_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `model_id` int NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of type
-- ----------------------------
INSERT INTO `type` VALUES (1, '普通用户', 1);
INSERT INTO `type` VALUES (3, '管理员', 1);
INSERT INTO `type` VALUES (3, '管理员', 2);
INSERT INTO `type` VALUES (3, '管理员', 3);
INSERT INTO `type` VALUES (3, '管理员', 4);
INSERT INTO `type` VALUES (3, '管理员', 5);
INSERT INTO `type` VALUES (3, '管理员', 6);
INSERT INTO `type` VALUES (3, '管理员', 7);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `money` double(10, 2) NULL DEFAULT 0.00,
  `type_id` int NULL DEFAULT 1,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `phoneNumber` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `point` double(10, 2) NULL DEFAULT 0.00,
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE,
  UNIQUE INDEX `phoneNumber`(`phoneNumber` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'admin', '21232f297a57a5a743894a0e4a801fc3', '管理员', 0.00, 3, 'admin.png', NULL, 11.00);
INSERT INTO `users` VALUES (2, 'wwj', 'e10adc3949ba59abbe56e057f20f883e', '阿杰', 0.00, 1, 'admin.png', '19906570571', 0.00);
INSERT INTO `users` VALUES (31, 'fyq', 'e10adc3949ba59abbe56e057f20f883e', '小付', 0.00, 1, 'zhangsan.png', '17356968206', 0.00);
INSERT INTO `users` VALUES (32, 'zhangsan', 'e10adc3949ba59abbe56e057f20f883e', '张三', 0.00, 1, 'zhangsan.png', '18827388587', 0.00);

-- ----------------------------
-- Table structure for users_collection
-- ----------------------------
DROP TABLE IF EXISTS `users_collection`;
CREATE TABLE `users_collection`  (
  `collection_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`collection_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of users_collection
-- ----------------------------
INSERT INTO `users_collection` VALUES (1, 1);
INSERT INTO `users_collection` VALUES (2, 1);
INSERT INTO `users_collection` VALUES (3, 1);
INSERT INTO `users_collection` VALUES (4, 1);
INSERT INTO `users_collection` VALUES (5, 1);
INSERT INTO `users_collection` VALUES (6, 1);
INSERT INTO `users_collection` VALUES (7, 1);
INSERT INTO `users_collection` VALUES (8, 1);
INSERT INTO `users_collection` VALUES (9, 1);
INSERT INTO `users_collection` VALUES (10, 1);
INSERT INTO `users_collection` VALUES (11, 2);
INSERT INTO `users_collection` VALUES (18, 1);
INSERT INTO `users_collection` VALUES (19, 2);

-- ----------------------------
-- Table structure for wordsintopoems
-- ----------------------------
DROP TABLE IF EXISTS `wordsintopoems`;
CREATE TABLE `wordsintopoems`  (
  `wordsIntoPoems_id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `correctPoem` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`wordsIntoPoems_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of wordsintopoems
-- ----------------------------
INSERT INTO `wordsintopoems` VALUES (1, '春江花月夜间清泉石', '春江花月夜');
INSERT INTO `wordsintopoems` VALUES (2, '山川河流云石竹梅松', '山川河流');
INSERT INTO `wordsintopoems` VALUES (3, '明月松间照柳云雪风', '明月松间照');
INSERT INTO `wordsintopoems` VALUES (4, '野旷天低树树江清月', '野旷天低树');
INSERT INTO `wordsintopoems` VALUES (5, '白日依山尽尽黄河入', '白日依山尽');

SET FOREIGN_KEY_CHECKS = 1;
