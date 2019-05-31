-- --------------------------------------------------------
-- 主机:                           rm-bp14419zgc8077s9hjo.mysql.rds.aliyuncs.com
-- 服务器版本:                        5.7.18-log - Source distribution
-- 服务器操作系统:                      Linux
-- HeidiSQL 版本:                  10.1.0.5464
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 online_judge 的数据库结构
CREATE DATABASE IF NOT EXISTS `online_judge` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;
USE `online_judge`;

-- 导出  表 online_judge.compile_info 结构
CREATE TABLE IF NOT EXISTS `compile_info` (
  `solution_id` varchar(16) COLLATE utf8_bin NOT NULL COMMENT '提交id，即RunID',
  `error` text COLLATE utf8_bin COMMENT '编译错误原因'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='记录编译错误的提交号（id）及原因';

-- 正在导出表  online_judge.compile_info 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `compile_info` DISABLE KEYS */;
INSERT INTO `compile_info` (`solution_id`, `error`) VALUES
	('de3faf0ad0f5', '/oj-home/judge/de3faf0ad0f5/code/main.cpp:1:2: error: invalid preprocessing directive #incceafggdsadf\n #incceafggdsadf\n  ^\n'),
	('c99fecb7403d', '/oj-home/judge/c99fecb7403d/code/main.cpp:1:22: fatal error: iostream.h: No such file or directory\ncompilation terminated.\n'),
	('26d5da2bd10a', '/oj-home/judge/26d5da2bd10a/code/main.cpp:1:2: error: invalid preprocessing directive #in\n #in\n  ^\n');
/*!40000 ALTER TABLE `compile_info` ENABLE KEYS */;

-- 导出  表 online_judge.contest 结构
CREATE TABLE IF NOT EXISTS `contest` (
  `contest_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '竞赛id',
  `title` varchar(1024) COLLATE utf8_bin NOT NULL COMMENT '竞赛标题',
  `access` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否可报名',
  `user_id` int(11) unsigned NOT NULL COMMENT '创建者Id',
  `start_at` datetime NOT NULL COMMENT '开始时间(年月日时分)',
  `end_at` datetime NOT NULL COMMENT '结束时间(年月日时分)',
  `describes` varchar(4096) COLLATE utf8_bin NOT NULL COMMENT '竞赛描述',
  `privates` tinyint(4) DEFAULT '0' COMMENT '内部/公开（0/1）',
  `organizer` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '主办方',
  `max` int(10) unsigned NOT NULL COMMENT '比赛人数上限',
  `now` int(10) unsigned DEFAULT '0' COMMENT '当前参加人数',
  `info` varchar(4096) COLLATE utf8_bin DEFAULT NULL COMMENT '竞赛tip',
  PRIMARY KEY (`contest_id`),
  KEY `FK_contest_users` (`user_id`),
  CONSTRAINT `FK_contest_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='竞赛表';

-- 正在导出表  online_judge.contest 的数据：~3 rows (大约)
/*!40000 ALTER TABLE `contest` DISABLE KEYS */;
INSERT INTO `contest` (`contest_id`, `title`, `access`, `user_id`, `start_at`, `end_at`, `describes`, `privates`, `organizer`, `max`, `now`, `info`) VALUES
	(18, '蓝桥杯', b'0', 12, '2019-05-03 00:00:00', '2019-05-24 00:00:00', '<p>为促进软件和信息领域专业技术人才培养，提升高校毕业生的就业竞争力，由教育部就业指导中心支持，工业和信息化部人才交流中心举办蓝桥杯大赛。九年来，包括北大、清华在内的超过 1200 余所院校，累计20万余名学子报名参赛，IBM、百度等知名企业全程参与，成为国内始终领跑的人才培养选拔模式并获得行业深度认可的IT类科技竞赛。</p>', 1, '教育部高等学校计算机科学与技术教学指导委员会', 100, 0, '可查阅资料，可询问他人，但禁止抄袭'),
	(19, 'ACM - ICPC', b'1', 12, '2019-05-25 00:00:00', '2019-05-31 00:00:00', '<p>以团队的形式代表各学校参赛，每队由至多3名队员组成。每位队员必须是在校学生，有一定的年龄限制，并且每年最多可以参加2站区域选拔赛。</p>', 1, '国际计算机协会', 300, 0, NULL),
	(20, 'PAT - 甲级', b'1', 12, '2019-05-24 00:00:00', '2019-05-31 00:00:00', '<p>浙江大学计算机程序设计能力考试（Programming Ability Test，简称PAT）是由浙江大学计算机科学与技术学院组织的统一考试。旨在培养和展现学生分析问题、解决问题和计算机程序设计的能力，科学评价计算机程序设计人才，并为企业选拔人才提供参考标准。</p>', 1, '浙江大学计算机科学与技术学院', 30, 0, NULL);
/*!40000 ALTER TABLE `contest` ENABLE KEYS */;

-- 导出  表 online_judge.contest_apply 结构
CREATE TABLE IF NOT EXISTS `contest_apply` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `contest_id` int(11) unsigned NOT NULL COMMENT '竞赛id',
  `user_id` int(11) unsigned NOT NULL COMMENT '用户id',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否加入(0等待确认/1已加入/2被拒绝)',
  `apply_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
  PRIMARY KEY (`id`),
  KEY `FK_contest_applyqueue_contest` (`contest_id`),
  KEY `FK_contest_applyqueue_users` (`user_id`),
  CONSTRAINT `FK_contest_applyqueue_contest` FOREIGN KEY (`contest_id`) REFERENCES `contest` (`contest_id`),
  CONSTRAINT `FK_contest_applyqueue_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='比赛申请表';

-- 正在导出表  online_judge.contest_apply 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `contest_apply` DISABLE KEYS */;
/*!40000 ALTER TABLE `contest_apply` ENABLE KEYS */;

-- 导出  表 online_judge.contest_problem 结构
CREATE TABLE IF NOT EXISTS `contest_problem` (
  `problem_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '题目id',
  `contest_id` int(11) unsigned DEFAULT NULL COMMENT '竞赛id',
  `title` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '标题',
  `num` int(11) DEFAULT NULL COMMENT '竞赛题目编号',
  KEY `FK_contest_problem_problems` (`problem_id`),
  KEY `FK_contest_problem_contest` (`contest_id`),
  CONSTRAINT `FK_contest_problem_contest` FOREIGN KEY (`contest_id`) REFERENCES `contest` (`contest_id`),
  CONSTRAINT `FK_contest_problem_problems` FOREIGN KEY (`problem_id`) REFERENCES `problems` (`problem_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='竞赛题目';

-- 正在导出表  online_judge.contest_problem 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `contest_problem` DISABLE KEYS */;
INSERT INTO `contest_problem` (`problem_id`, `contest_id`, `title`, `num`) VALUES
	(38, 18, NULL, NULL);
/*!40000 ALTER TABLE `contest_problem` ENABLE KEYS */;

-- 导出  表 online_judge.news 结构
CREATE TABLE IF NOT EXISTS `news` (
  `news_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '新闻id',
  `user_id` int(11) unsigned NOT NULL COMMENT '用户id',
  `title` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '标题',
  `content` text COLLATE utf8_bin NOT NULL COMMENT '内容',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `defunct` varchar(2) COLLATE utf8_bin NOT NULL COMMENT '公开状态(0:不公开 1:公开)',
  `contest_id` int(11) DEFAULT NULL COMMENT '所属竞赛',
  `create_at` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`news_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='公告';

-- 正在导出表  online_judge.news 的数据：~11 rows (大约)
/*!40000 ALTER TABLE `news` DISABLE KEYS */;
INSERT INTO `news` (`news_id`, `user_id`, `title`, `content`, `update_time`, `defunct`, `contest_id`, `create_at`) VALUES
	(1, 12, 'Mo Online Judge 已初步完成', '<p>Mo Online Judge 已初步完成<br></p>', '2019-05-01 03:14:18', '1', NULL, '2019-04-26 11:11:58'),
	(2, 12, 'V1.0 版本更新', '<p>V1.0 版本更新本版尚有漏洞,如遇Bug请联系XXXX</p>', '2019-04-26 11:31:55', '1', NULL, '2019-04-26 11:31:55'),
	(5, 10, '临近期中考试,请大家积极准备', '<p>临近期中考试,请大家积极准备</p>', '2019-04-26 14:49:12', '1', NULL, '2019-04-26 12:54:19'),
	(6, 12, '考试时间地点已公布', '<p>考试时间地点已公布,大家可以去<u><i><a href="http://www.xxxx.com" rel="nofollow">www.xxxx.com</a></i></u>查询<br></p>', '2019-04-26 12:55:16', '1', 20, '2019-04-26 12:55:16'),
	(10, 12, '报名条件', '<p>以校级为单位，限制两个队伍</p>', '2019-05-04 14:09:04', '1', 19, '2019-05-04 14:09:04'),
	(12, 12, '关于举办第三届蓝桥杯大赛德国国际赛的通知', '<p>各参赛院校：</p><p>由工业和信息化部人才交流中心举办，教育部全国高等学校学生信息咨询与就业指导中心为支持单位的蓝桥杯大赛，至今已成功举办九届。全国累计共有1400余所高校的近25万名学生参加，得到了各大院校的鼎力支持与高校学子的踊跃参与。首届与第二届蓝桥杯大赛国际赛已于美国成功举办。清华大学、北京大学、河海大学、麻省理工学院、罗格斯大学、波士顿大学等众多院校参加了赛事，呈现了较高的水平。</p><p>根据国务院《统筹推进世界一流大学和一流学科建设总体方案》，要求与世界高水平大学和学术机构有深度学术交流和科研合作。为创新体制机制与人才培养模式，统筹利用国内国际教育资源，广泛借鉴吸收国际先进教育经验，进一步提升教育对外开放水平，通过改革创新和对外开放解决难题、激发活力、推动发展，蓝桥杯组委会特举办第三届蓝桥杯大赛国际赛。本届比赛将在德国举办，同时美国将设分赛区。蓝桥杯国际赛为国际交流性赛事，组委会拟邀请承办国及其周边国家计算机领域大学生与国内获奖选手同场竞技。</p><p>现将第三届蓝桥杯大赛国际赛具体事宜公布如下：</p><p><strong>一、</strong><strong>比赛时间、地点：</strong></p><ol><li>时间：2019年10月25日-2019年10月31日；</li><li>地点：德国主赛场和美国分赛场。</li></ol><p><strong>二、参赛对象：</strong></p><p>凡具有正式全日制学籍的蓝桥杯历届大赛省赛（软件类）一等奖获奖选手均有资格报名参加第三届蓝桥杯大赛国际赛。</p><p><strong>三、参赛要求：</strong></p><p>比赛不分组别，学生以个人为单位参赛，每所参赛院校须派至少一名带队老师，带队老师要求为各参赛院校院长、系主任、教务处长、专业带头人、实训基地负责人、骨干教师、指导教师等。</p><p><strong>四、参赛收获</strong>：</p><ol><li>国内高校：该赛事将搭建与国际高校间专业交流的平台，通过蓝桥杯国际赛的举办，有效促进国内高校计算机课程教学内容和教学方法的改革。邀请德国著名高校知名教授或教师分享计算机专业建设、课程设置及人才培养等经验，开拓国内高校教学思路，提高学术能力水平，使不同教育文化得到进一步的交流和融合；</li><li>参赛选手：国内参赛选手不仅能够与各国高手过招比拼，磨练编程技能，还可以开阔自身视野，提高实践动手能力。同时，国际赛将向有意向留学的学生提供交流渠道；</li><li>获奖证书：获奖后，参赛选手及其指导教师将颁发蓝桥杯国际赛获奖证书。</li></ol><p><strong>五、比赛形式：</strong></p><p>比赛形式为上机编程比赛，比赛时长5小时。</p><p><strong>六、报名方式：</strong></p><p>各参赛院校须在2019年6月14日前将学生及其带队老师的报名表发到<a href="mailto:baoming@lanqiao.org">lanqiao@lanqiao.org</a>邮箱并完成缴费。若逾期未报名及缴费，视为高校自动放弃参加蓝桥杯国际赛资格，后续将不予安排国际赛报名（报名表见附录二）。</p><p><strong>七、收费标准：</strong></p><ol><li>蓝桥杯国际赛报名费：29800元/人（参赛师生均须缴纳报名费）；</li><li>费用包含：北京-德国往返机票、机场建设费、境外人身保险、在德国期间费用（含比赛、师生交流、院校参观、用餐、交通及住宿费）；</li><li>参赛师生由院校所在地赴北京首都机场集合发生的所有往返费用及其他费用自理。</li></ol><p><strong>八、缴费方式：</strong></p><p>1、报名费缴费方式：本届蓝桥杯国际赛报名只接受公对公转账。</p><p style="margin-left: 10px;">户&nbsp; 名：国信蓝桥教育科技(北京)股份有限公司</p><p style="margin-left: 10px;">账&nbsp; 号：110060836018150038530</p><p style="margin-left: 10px;">开户行：交通银行北京翠微路支行</p><p>2、发票申领：</p><p>本届蓝桥杯国际赛由工业和信息化部人才交流中心主办，大赛组委会秘书处设在工业和信息化部人才交流中心，相关费用由国信蓝桥教育科技(北京)股份有限公司统一收取，并为各参赛院校开具正式发票，发票一经开出，不退不换、不能更改。</p>', '2019-05-04 14:19:05', '1', 18, '2019-05-04 14:19:05'),
	(14, 12, '欢迎使用"蓝桥杯"练习系统', '<p>本系统面向参加"蓝桥杯"全国软件和信息技术专业人才大赛的老师和同学。</p><p>本系统将帮助参赛的同学熟悉比赛试题的形式和解题方式。通过使用本系统，同学可以学习到一些重要的编程方法，提高自己的编程水平，在大赛中取得更好的名次，提升自己的竞争力。</p><p>本系统的特点：</p><ol><li><p>入门引导：设置入门引导试题，帮助同学了解比赛的命题形式和解题方法。</p></li><li><p>试题分组：具有相同难度和特点的试题形成一组，同组的试题具有相关性，帮助同学学习与提高程序设计、算法、数据结构的知识。</p></li><li><p>不断更新的试题：系统不定期更新试题，保证同学的训练量。</p></li><li><p>测试管理：可以将练习系统的题目进行组卷，进行分数统计，方便院校在大赛报名中校内选拔出优秀选手。</p></li><li><p>查看评测数据：VIP用户允许查看评测数据，帮助你更有效的练习和提高。</p></li><li><p>比赛环境：使用和软件大赛相同的测试环境进行测试，有效的模拟大赛的评测。</p></li><li><p>即时评测：提交答案后马上进行评测并给出评测结果，方便同学了解自己程序的不足，对自己的程序进行改进。</p></li></ol>', '2019-05-04 14:21:58', '1', 18, '2019-05-04 14:21:58'),
	(15, 12, '2019年秋季PAT报名已经启动', '<p>2019 年秋季 PAT &amp; PATFEE（顶、甲、乙级）定于 2019 年 9 月 8 日 13:30-16:30 在杭州（包括临安、下沙、滨江、仓前）、宁波、福州、西安、郑州、青岛、嘉兴、南昌、兰州、苏州、上海、长春、徐州、贵阳、北京、怀化、呼和浩特、武汉、重庆、成都、吉首、台州、合肥、太原、南京、日照、呼伦贝尔、保定、秦皇岛、淄博、天津、深圳、威海、哈尔滨、石家庄、沈阳、绵阳、绍兴、厦门、温州、滨州、湖州、泉州、三亚等城市同时举办。</p><p>报名已经开启，截止时间为 2019 年 8 月 30 日 17:00。考生可从官网首页点击“进入报名”查看各个考点的报名、缴费、机房环境等详细信息。</p><p>建议报名的考生关注微信公众号：PATest-cn。所有考试相关重要通知都将由此公众号第一时间发布。</p><p>请注意：</p><ol><li><p>原 PAT 报名网站的用户请到<a href="https://www.patest.cn/bind_old_reg_user" target="_blank">https://www.patest.cn/bind_old_reg_user</a>将旧版账号与新版“拼题A”账号绑定；原 PAT 官网用户请到<a href="https://www.patest.cn/bind_old_pat_user" target="_blank">https://www.patest.cn/bind_old_pat_user</a>将旧版账号与新版“拼题A”账号绑定。</p></li><li><p>为避免恶意占位，PAT 系统将自动删除报名后超过 48 小时未付费的报名者；在报名截止前一周将删除报名后超过 24 小时未付费的报名者；缴费后再要求退考，考试中心将扣除所缴费用的 25% 作为人工处理的手续费；报名截止前 3 天将不接受退款请求。此外，由于支付宝有退款期限，超过三个月的报名将无法退款。</p></li><li><p>建议考生提前 10 分钟到达考场，查到自己的指定座位后，就座并将身份证件放在桌角待查，静待考试开始。考试迟到 20 分钟者禁止入场。</p></li><li><p>考试主服务器提供 31 种编程语言的编译\\解释器，但各考场只保证提供 C、C++、Java 的程序编译调试环境，题目时间上限一般根据 C 语言标准答案运行时间的 3~5 倍设定。</p></li></ol>', '2019-05-04 14:23:51', '1', 20, '2019-05-04 14:23:51'),
	(16, 12, '本站将在未来一周内随时迁移部署机器', '<p><b><i><u>本站将在未来一周内随时迁移部署机器，如有需求请提前申请</u></i></b><br></p>', '2019-05-19 06:08:11', '1', NULL, '2019-05-07 10:42:55'),
	(17, 12, '举办比赛请先申请', '<p>因为目前只有一台机器，而且机器负载一直较高，如举办比赛可能会遇到一些问题，所以请至少提前5天提出申请。</p><p>请使用密码<code>3US7JidxfIYWi1k5</code>填写<a href="http://www.baidu.com" target="_blank">表单</a></p>', '2019-05-19 06:15:29', '1', NULL, '2019-05-19 06:15:29'),
	(18, 12, '毕业设计查重已经可以使用', '<ol><li>近两周检查毕业设计成果，有五位同学软件成果已基本实现预期功能，请近期抓紧时间撰写论文，下周一前将论文初稿发给我，下周三见面交流。另三位同学消极怠工，到目前尚未实现预期，请考虑一下是否要缓答辩！</li><li>毕业论文纸质稿上交到学院的时间是6月10上午11点，在这之前必须将打印稿给我检查过，有问题修改重新打印。最后打印稿统一交给我，收齐后上交学院。</li><li>毕业答辩时间6月15日。论文上交学院后到答辩之前的时间，一是要制作答辩PPT，二是可以继续完善软件系统，论文若有较多改动，可以在答辩当天到答辩老师处调换论文。此外，答辩当天答辩老师也可能会提出修改意见，答辩后要抓紧时间按要求修改。重新打印的论文还是交给我。</li></ol>', '2019-05-24 12:13:23', '1', NULL, '2019-05-24 12:13:23');
/*!40000 ALTER TABLE `news` ENABLE KEYS */;

-- 导出  表 online_judge.privilege 结构
CREATE TABLE IF NOT EXISTS `privilege` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL COMMENT '用户帐号',
  `rightstr` char(30) COLLATE utf8_bin DEFAULT NULL COMMENT '分组',
  `defunct` char(1) COLLATE utf8_bin NOT NULL DEFAULT 'A' COMMENT '是否屏蔽(A:active I:inactive)',
  PRIMARY KEY (`id`),
  KEY `FK_privilege_users` (`user_id`),
  CONSTRAINT `FK_privilege_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户分组';

-- 正在导出表  online_judge.privilege 的数据：~6 rows (大约)
/*!40000 ALTER TABLE `privilege` DISABLE KEYS */;
INSERT INTO `privilege` (`id`, `user_id`, `rightstr`, `defunct`) VALUES
	(1, 9, 'admin', 'A'),
	(2, 10, 'admin_oh', 'A'),
	(3, 12, 'admin_bcfghjklo', 'A'),
	(5, 68, 'user', 'A'),
	(73, 52, 'user', 'A');
/*!40000 ALTER TABLE `privilege` ENABLE KEYS */;

-- 导出  表 online_judge.problems 结构
CREATE TABLE IF NOT EXISTS `problems` (
  `problem_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '问题ID',
  `title` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '问题标题',
  `defunct` varchar(2) COLLATE utf8_bin NOT NULL DEFAULT '1' COMMENT '屏蔽-公开-部分公开-绝对私有0/1/2/(3)',
  `create_by` int(11) unsigned NOT NULL COMMENT '创建者',
  `hint` text COLLATE utf8_bin COMMENT '暗示',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `source` varchar(1024) COLLATE utf8_bin DEFAULT NULL COMMENT '问题来源',
  `description` text COLLATE utf8_bin COMMENT '问题描述',
  `click` int(11) DEFAULT '0' COMMENT '问题点击量',
  `accepted` int(11) DEFAULT '0' COMMENT '解决次数',
  `submit` int(11) DEFAULT '0' COMMENT '提交次数',
  `sample_input` text COLLATE utf8_bin COMMENT '输入',
  `sample_output` text COLLATE utf8_bin COMMENT '输出',
  `memory_limit` int(11) unsigned NOT NULL COMMENT '空间限制(MB)',
  `time_limit` int(11) unsigned NOT NULL COMMENT '时间限制(ms)',
  `output` text COLLATE utf8_bin COMMENT '输出描述',
  `input` text COLLATE utf8_bin COMMENT '输入描述',
  `display_id` int(11) DEFAULT '1' COMMENT '显示序号',
  `description_image_url` varchar(1024) COLLATE utf8_bin DEFAULT NULL COMMENT '题目描述图片',
  PRIMARY KEY (`problem_id`),
  KEY `FK_problems_users` (`create_by`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='题目表';

-- 正在导出表  online_judge.problems 的数据：~15 rows (大约)
/*!40000 ALTER TABLE `problems` DISABLE KEYS */;
INSERT INTO `problems` (`problem_id`, `title`, `defunct`, `create_by`, `hint`, `created_at`, `source`, `description`, `click`, `accepted`, `submit`, `sample_input`, `sample_output`, `memory_limit`, `time_limit`, `output`, `input`, `display_id`, `description_image_url`) VALUES
	(11, '陶陶摘苹果', '2', 12, '', '2018-09-25 15:22:59', 'NOIP全国联赛普及组-2005年NOIP全国联赛普及组', '<h4>陶陶家的院子里有一棵苹果树，每到秋天树上就会结出10个苹果。苹果成熟的时候，陶陶就会跑去摘苹果。陶陶有个30厘米高的板凳，当她不能直接用手摘到苹果的时候，就会踩到板凳上再试试。</h4><h4>现在已知10个苹果到地面的高度，以及陶陶把手伸直的时候能够达到的最大高度，请帮陶陶算一下她能够摘到的苹果的数目。假设她碰到苹果，苹果就会掉下来。</h4>', 0, 0, 0, '100 200 150 140 129 134 167 198 200 111\n110', '5', 64, 1000, '<h4>每组输出包括一行，这一行只包含一个整数，表示陶陶能够摘到的苹果的数目。<br></h4>', '<h4>每组输入数据包括两行。第一行包含10个100到200之间（包括100和200）的整数（以厘米为单位）分别表示10个苹果到地面的高度，两个相邻的整数之间用一个空格隔开。第二行只包括一个100到120之间（包含100和120）的整数（以厘米为单位），表示陶陶把手伸直的时候能够达到的最大高度。</h4>', 1, NULL),
	(16, 'ISBN号码', '2', 12, '', '2019-02-27 16:15:58', 'NOIP全国联赛普及组-2008年NOIP全国联赛普及组 ', '<h4>每一本正式出版的图书都有一个ISBN号码与之对应，ISBN码包括9位数字、1位识别码和3位分隔符，其规定格式如“x-xxx-xxxxx-x”，其中符号“-”是分隔符（键盘上的减号），最后一位是识别码，例如0-670-82162-4就是一个标准的ISBN码。ISBN码的首位数字表示书籍的出版语言，例如0代表英语；第一个分隔符“-”之后的三位数字代表出版社，例如670代表维京出版社；第二个分隔之后的五位数字代表该书在出版社的编号；最后一位为识别码。</h4><h4>识别码的计算方法如下：</h4><h4>首位数字乘以1加上次位数字乘以2...以此类推，用所得的结果mod 11，所得的余数即为识别码，如果余数为10，则识别码为大写字母X。例如ISBN号码0-670-82162-4中的识别码4是这样得到的：对067082162这9个数字，从左至右，分别乘以1，2，...，9，再求和，即0×1+6×2+...+2×9=158，然后取158 mod 11的结果4作为识别码。</h4><h4>你的任务是编写程序判断输入的ISBN号码中识别码是否正确，如果正确，则仅输出“Right”；如果错误，则输出你认为是正确的ISBN号码。</h4><h4><br></h4>', 0, 0, 0, '0-670-82162-4\n\n0-670-82162-0', 'Right\n\n0-670-82162-4', 64, 1000, '<h4>每组输出共一行，假如输入的ISBN号码的识别码正确，那么输出“Right”，否则，按照规定的格式，输出正确的ISBN号码（包括分隔符“-”）。<br></h4>', '<h4>每组输入数据只有一行，是一个字符序列，表示一本书的ISBN号码（保证输入符合ISBN号码的格式要求）。<br></h4>', 1, NULL),
	(17, '方阵填数', '2', 12, '<p>6</p><p>-------------------------</p><p>16 17 18 19 20 1</p><p>15 30 31 32 21 2</p><p>14 29 36 33 22 3</p><p>13 28 35 34 23 4</p><p>12 27 26 25 24 5</p><p>11 10 9 8 7 6</p>', '2019-04-15 16:39:48', 'NOIP全国联赛普及组 1995年NOIP全国联赛普及组 ', '<p>在一个N*N的方阵中，填入1，2，……N*N个数，并要求构成如下的格式：</p><p>例如：</p><p>N=5</p><p>13 14 15 16 &nbsp;1</p><p>12 23 24 17 &nbsp;2</p><p>11 22 25 18 &nbsp;3</p><p>10 21 20 19 &nbsp;4</p><p>9 &nbsp;8 &nbsp;7 &nbsp;6 &nbsp;5</p><p>N=6</p><p>16 17 18 19 20 &nbsp;1</p><p>15 30 31 32 21 &nbsp;2</p><p>14 29 36 33 22 &nbsp;3</p><p>13 28 35 34 23 &nbsp;4</p><p>12 27 26 25 24 &nbsp;5</p><p>11 10 &nbsp;9 &nbsp;8 &nbsp;7 &nbsp;6</p>', 0, 0, 0, '5', '13 14 15 16 1\n12 23 24 17 2\n11 22 25 18 3\n10 21 20 19 4\n9 8 7 6 5', 64, 1000, '<h4>输出构成的方阵。<br></h4>', '<h4>每个测试文件只包含一组测试数据，每组输入一个N。<br></h4>', 1, NULL),
	(19, '二进制数问题', '2', 9, '', '2019-04-26 15:42:21', 'NOIP全国联赛普及组 1995年NOIP全国联赛普及组', '<p>若将一个正整数化为二进制数，在此二进制数中，我们将数字1的个数多于数字0的个数的这类二进制数称为A类数，否则就称其为B类数。</p><p>例如：</p><p>（13）10=（1101）2</p><pre><code>    其中1的个数为3，0的个数为1，则称此数为A类数； \n</code></pre><p>（10）10=（1010）2</p><pre><code>    其中1的个数为2，0的个数也为2，称此数为B类数； \n</code></pre><p>（24）10=（11000）2</p><pre><code>    其中1的个数为2，0的个数为3，则称此数为B类数； \n</code></pre><p>程序要求：</p><p>求出1～1000之中（包括1与1000），全部A、B两类数的个数。</p>', 0, 0, 0, '无', '本题结果是唯一的，所以不提供输出样例。', 64, 1000, '<p>在一行中输出两个整数A和B，A表示A类数的个数，B表示B类数的个数，AB之间由一个空格分隔，除此之外不要再输出其他多余的东西。</p>', '<p>无输入。</p>', 1, NULL),
	(21, '编码问题', '2', 10, '<p>6</p><h2>B=(0,0,0,3,1,2)</h2><p>A=(4,3,0,5,1,2)</p>', '2019-04-26 15:44:53', 'NOIP全国联赛普及组 1995年NOIP全国联赛普及组 ', '<p>设有一个数组 A:ARRAY[0..N-1] OF INTEGER；数组中存放的元素为0～N-1之间的整数，且A[i]≠A[j]（当i≠j时）。</p><p>例如：</p><pre><code>   N=6时，有：A=(4,3,0,5,1,2) \n\n   此时，数组A的编码定义如下： \n\n   A[0]的编码为0； \n\n   A[i]的编码为：在A[0]，A[1]，……A[i-1]中比A[i]的值小的个数（i=1，2……N-1） \n\n∴上面数组A的编码为：B=(0,0,0,3,1,2) \n</code></pre><p>程序要求解决以下问题：</p><p>①   给出数组A后，求出其编码；</p><p>②   给出数组A的编码后，求出A中的原数据。</p>', 0, 0, 0, '6\nA=(4,3,0,5,1,2)', 'B=(0,0,0,3,1,2)', 64, 1000, '<p>当输入的是A=(...)，则输出其编码。</p><p>当输入的是B=(...)，则输出A中的原数据。</p><p>输出数据的格式和输入数据的格式是一样的。</p>', '<p>每个测试文件只包含一组测试数据，每组输入包含三行。</p><p>第一行输入整数N；</p><p>第二行输入有两种可能：</p><p>例如：</p><p>A=(4,3,0,5,1,2)</p><p>或</p><p>B=(0,0,0,3,1,2)</p><p>其中输入中的逗号和括号都是英文状态下的。</p>', 1, NULL),
	(23, '乘法运算', '2', 10, '<p>16 8</p><hr><p>168128</p>', '2019-04-26 15:52:41', 'NOIP全国联赛普及组 1996年NOIP全国联赛普及组', '<p>从键盘读入2个100以内的正整数，进行乘法运算并以竖式输出。 例如，输入：89 13                     又如，输入：16 8</p><pre><code>           输出：       89                            输出：     16 \n\n                         × 13                                        ×   8 \n\n                           267                                         128 \n\n                           89  \n\n                         1157 \n</code></pre>', 0, 0, 0, '89 13', '89\n13\n267\n89\n1157', 64, 1000, '<p>对于每组输入数据，输出两个整数进行乘法运算的竖式。为了简单起见，只需从上到下输出竖式里面的数即可，具体格式见样例输出。</p>', '<p>每个测试文件只包含一组测试数据，每组输入数据为两个100以内的正整数，之间由一个空格分隔。</p>', 1, NULL),
	(24, '最大和', '2', 12, '', '2019-04-26 16:40:39', 'GZU ', '<p>现给定一串数N，求出连续的数相加的最大值，例如：1 6 9 -5 4，连续数的最大和就是1+6+9=16；6 -3 4 0</p><p>-1，连续数的最大和是6+（-3）+4=7；</p>', 0, 0, 0, '5\n1 6 9 -5 4\n5\n6 -3 4 0 -1', '16\n7', 64, 1000, '<p>对于每组测试数据输出连续数的最大和</p>', '<p>测试包含多组测试数据,每组测试数据包含两行，</p><p>第一行输入一个整数N</p><p>1&lt;=N&lt;=100</p><p>接下来一行含有N个数，保证每个数取值范围是（-100000，+100000），两个数中间用空格隔开</p>', 1, NULL),
	(25, '最长上升子序列', '2', 9, '', '2019-04-26 16:47:55', 'BJWC2018 高级 ', '<p>现在有一个长度为n的随机排列，求它的最长上升子序列长度的期望。 为了避免精度误差，你只需要输出答案模998244353的余数。</p>', 0, 0, 0, '2 ', '499122178\n【样例说明】\n这是3/2。 ', 64, 1000, '<p>输出只包含一个非负整数，表示答案模998244353的余数。 可以证明，答案一定为有理数，设其为a/b（a、b为互质的整数），你输出的整数为x， 则你需要保证0≤x&lt;998244353且a与bx模998244353同余。</p>', '<p>输入只包含一个正整数n。</p><p>对于 100%的数据，1≤n≤28。共有 25组数据  ，对于第 i组数据 （1≤i≤25）， n=i+3。</p>', 1, NULL),
	(27, '加分二叉树', '2', 9, '', '2019-04-26 16:54:26', 'NOIP全国联赛提高组 2003年NOIP全国联赛提高组 ', '<p>设一个n个节点的二叉树tree的中序遍历为（l，2，3，...，n），其中数字1，2，3，...，n为节点编号。每个节点都有一个分数（均为正整数），记第j个节点的分数为di，tree及它的每个子树都有一个加分，任一棵子树subtree（也包含tree本身）的加分计算方法如下：</p><pre><code>subtree的左子树的加分×subtree的右子树的加分＋subtree的根的分数 \n\n若某个子树为主，规定其加分为1，叶子的加分就是叶节点本身的分数。不考虑它的空子树。 \n</code></pre><p>试求一棵符合中序遍历为（1，2，3，...，n）且加分最高的二叉树tree。要求输出：    （1）tree的最高加分    （2）tree的前序遍历</p>', 0, 0, 0, '5\n5 7 1 2 10', '145\n3 1 2 4 5', 64, 1000, '<p>第1行：一个整数，为最高加分（结果不会超过4,000,000,000）。第2行：n个用空格隔开的整数，为该树的前序遍历。</p>', '<p>第1行：一个整数n（n＜30），为节点个数。第2行：n个用空格隔开的整数，为每个节点的分数（分数＜100）。</p>', 1, NULL),
	(32, '字符串编辑', '1', 9, '', '2019-04-28 18:32:17', 'NOIP全国联赛普及组 1996年NOIP全国联赛普及组 ', '<p>从键盘输入一个字符串（长度&lt;=40个字符），并以字符 \'.\'结束。</p><p>例如：\'This is a book.\' 现对该字符串进行编辑，编辑功能有：</p><p>D：删除一个字符，命令的方式为：</p><pre><code> D a  其中a为被删除的字符\n\n 例如：D s  表示删除字符 \'s\' ，若字符串中有多个 \'s\'，则删除第一次出现的。\n\n          如上例中删除的结果为： \'Thi is a book.\'\n</code></pre><p>I：插入一个字符，命令的格式为：</p><pre><code>I a1 a2  其中a1表示插入到指定字符前面，a2表示将要插入的字符。\n\n例如：I s d  表示在指定字符 \'s\' 的前面插入字符 \'d\' ，若原串中有多个 \'s\' ，则插入在最后一个字符的前面。\n\n         如上例中：\n               原串：\'This is a book.\'\n\n               插入后：\'This ids a book.\'\n</code></pre><p>R：替换一个字符，命令格式为：</p><pre><code> R a1 a2  其中a1为被替换的字符，a2为替换的字符，若在原串中有多个a1则应全部替换。\n\n 例如： 原串： \'This is a book.\'\n\n           输入命令：R o e\n\n           替换后的字符串为：\'This is a beek.\'\n</code></pre><p>在编辑过程中，若出现被改的字符不存在时，则给出提示信息"Not exist"。</p>', 0, 0, 0, 'This is a book.\nD s', 'Thi is a book.', 64, 1000, '<p>对于每组输入数据，输出编辑后的字符串，如果被改的字符不存在，则输出"Not exist"（引号不输出）。</p>', '<p>每个测试文件只包含一组测试数据，每组输入数据包含两行：</p><p>第一行，输入一个字符串，表示原串；</p><p>第二行，输入一个字符串，表示命令。</p>', 1, NULL),
	(33, '数制转换', '1', 9, '', '2019-04-28 18:38:44', 'NOIP全国联赛提高组 1996年NOIP全国联赛提高组 ', '<p>设有一个字符串A$的结构为：A$=\'mp\'</p><p>其中m为数字串（长度&lt;=20），而n,p均为1或2位的数字串（其中所表达的内容在2-10之间）。</p><p>程序要求：</p><p>从键盘上读入A$后（不用正确性检查），将A$中的数字串m（n进制），以p进制的形式输出。</p><p>例如：A$=\'48&lt;10&gt;8\'</p><pre><code>     其意义为：将10进制数48，转换成8进制数输出。\n\n\n     输出结果为：48&lt;10&gt;=60&lt;8&gt;\n</code></pre>', 0, 0, 0, '48<10>8', '48<10>=60<8>', 64, 1000, '<p>对于每组输入数据，输出数制转换后的结果，具体格式见样例输出。</p>', '<p>每个测试文件只包含一组测试数据，每组输入一个字符串，形式如mp。</p>', 1, NULL),
	(34, '排三角形', '2', 12, '', '2019-04-28 23:58:09', 'NOIP全国联赛普及组 1997年NOIP全国联赛普及组 ', '<p>将1，2，······,9共9个数排成下列形态的三角形。</p><pre><code>                    a\n\n                 b      c\n\n              d            e\n\n            f     g     h     i\n\n\n\n其中：a～i分别表示1，2，······,9中的一个数字，并要求同时满足下列条件：\n\n（1）a&lt;f&lt;i;\n\n（2）b&lt;d, g&lt;h, c&lt;e\n\n（3）a+b+d+f=f+g+h+i=i+e+c+a=P\n</code></pre><p>程序要求：</p><pre><code>  根据输入的边长之和P，输出所有满足上述条件的三角形的个数。\n</code></pre>', 0, 0, 0, '23', '2', 64, 1000, '<p>对于每组输入数据，输出所有满足上述条件的三角形的个数。</p><p>如果无解，则输出"Not exist"（引号不输出）。</p>', '<p>每个测试文件只包含一组测试数据，每组输入一个整数P，表示边长之和。</p>', 1, NULL),
	(35, '上下火车', '2', 12, '', '2019-05-23 14:35:16', 'NOIP全国联赛提高组 1998年NOIP全国联赛提高组', '<p>火车从始发站（称为第1站）开出，在始发站上车的人数为a，然后到达第2站，在第2站有人上、下车，但上、下车的人数相同，因此在第2站开出时（即在到达第3站之前）车上的人数保持为a人。从第3站起（包括第3站）上、下车的人数有一定规律：上车的人数都是前两站上车人数之和，而下车人数等于上一站上车人数，一直到终点站的前一站（第n-1站），都满足此规律。现给出的条件是：共有N个车站，始发站上车的人数为a，最后一站下车的人数是m（全部下车）。试问x站开出时车上的人数是多少？</p>', 0, 0, 0, '5 7 32 4', '13', 64, 1000, '<p>对于每组输入数据，输出从x站开出时车上的人数。</p>', '<p>每个测试文件只包含一组测试数据，每组输入四个整数a、n、m和x。</p>', 1, NULL),
	(38, '数字反转', '3', 12, '', '2019-05-23 17:41:56', 'NOIP全国联赛普及组-2011年NOIP全国联赛普及组 ', '<p>给定一个整数，请将该数各个位上数字反转得到一个新数。新数也应满足整数的常见形式，即除非给定的原数为零，否则反转后得到的新数的最高位数字不应为零。</p>', 0, 0, 0, '123\n\n-380', '321\n\n-83', 64, 1000, '<p>每组输出共1行，一个整数，表示反转后的新数。</p>', '<p>每组输入数据共1行，一个整数N（-1,000,000,000≤N≤1,000,000,000）。</p>', 1, NULL),
	(39, '计算(calc)', '3', 10, '<p>100 ％的数据满足：算式长度&lt;=30 其中所有数据在 2^31-1 的范围内。</p>', '2019-05-30 17:08:19', '一本通2018 数据结构 栈 ', '<p>小明在你的帮助下，破密了 Ferrari 设的密码门，正要往前走，突然又出现了一个密码门 ，门上有一个算式，其中只有<code>(</code>，<code>)</code>，<code>0-9</code>，<code>+</code>，<code>-</code>，<code>*</code>，<code>/</code>，<code>^</code>求出的值就是密码。小明数学学得不好，还需你帮他的忙。（<code>/</code>用整数除法）</p>', 0, 0, 0, '1+(3+2)*(7^2+6*9)/(2)', '258 ', 64, 1000, '<p>输出密码。</p>', '<p>输入算式。</p>', 1, NULL);
/*!40000 ALTER TABLE `problems` ENABLE KEYS */;

-- 导出  表 online_judge.problem_tag 结构
CREATE TABLE IF NOT EXISTS `problem_tag` (
  `problem_id` int(11) unsigned NOT NULL COMMENT '题目号',
  `tag_id` int(11) unsigned NOT NULL COMMENT '标签号',
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_PID_PTAG` (`problem_id`),
  KEY `FK_TID_PTAG` (`tag_id`),
  CONSTRAINT `FK_PID_PTAG` FOREIGN KEY (`problem_id`) REFERENCES `problems` (`problem_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_TID_PTAG` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`tag_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- 正在导出表  online_judge.problem_tag 的数据：~9 rows (大约)
/*!40000 ALTER TABLE `problem_tag` DISABLE KEYS */;
INSERT INTO `problem_tag` (`problem_id`, `tag_id`, `id`) VALUES
	(17, 5, 3),
	(24, 6, 4),
	(25, 7, 5),
	(27, 2, 7),
	(27, 1, 8),
	(11, 10, 9),
	(16, 10, 10),
	(17, 10, 11),
	(24, 1, 12),
	(38, 10, 13);
/*!40000 ALTER TABLE `problem_tag` ENABLE KEYS */;

-- 导出  表 online_judge.solution 结构
CREATE TABLE IF NOT EXISTS `solution` (
  `solution_id` varchar(16) COLLATE utf8_bin NOT NULL COMMENT '运行id',
  `problem_id` int(11) unsigned NOT NULL COMMENT '问题id',
  `user_id` int(11) unsigned NOT NULL COMMENT '用户id',
  `time` int(11) unsigned DEFAULT NULL COMMENT '用时（ms）',
  `memory` int(11) unsigned DEFAULT NULL COMMENT '所用空间',
  `create_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '加入时间',
  `result` smallint(6) unsigned NOT NULL DEFAULT '0' COMMENT '结果(4:AC 0:待评测)',
  `language` tinyint(4) unsigned NOT NULL COMMENT '语言(0:java/1:c/2:c++/3:python)',
  `ip` char(255) COLLATE utf8_bin NOT NULL COMMENT '用户ip',
  `contest_id` int(11) unsigned DEFAULT NULL COMMENT '所属于竞赛组',
  `valid` tinyint(4) unsigned DEFAULT '1' COMMENT '是否有效',
  `num` tinyint(4) unsigned DEFAULT '0' COMMENT '题目在竞赛中属于顺序号',
  `code_lenght` int(11) unsigned NOT NULL COMMENT '代码长度',
  `judgetime` datetime DEFAULT NULL COMMENT '判题时间',
  `point` int(11) DEFAULT NULL COMMENT 'AC则为0,错误一题即减一',
  PRIMARY KEY (`solution_id`),
  KEY `FK_solution_problems` (`problem_id`),
  KEY `FK_solution_users` (`user_id`),
  KEY `FK_solution_contest` (`contest_id`),
  CONSTRAINT `FK_solution_contest` FOREIGN KEY (`contest_id`) REFERENCES `contest` (`contest_id`),
  CONSTRAINT `FK_solution_problems` FOREIGN KEY (`problem_id`) REFERENCES `problems` (`problem_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_solution_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='程序运行结果记录';

-- 正在导出表  online_judge.solution 的数据：~3 rows (大约)
/*!40000 ALTER TABLE `solution` DISABLE KEYS */;
INSERT INTO `solution` (`solution_id`, `problem_id`, `user_id`, `time`, `memory`, `create_at`, `result`, `language`, `ip`, `contest_id`, `valid`, `num`, `code_lenght`, `judgetime`, `point`) VALUES
	('13441', 32, 54, NULL, NULL, '2014-08-08 08:08:08', 7, 0, '', 18, 1, 0, 0, NULL, NULL),
	('31242341', 32, 57, NULL, NULL, '2019-09-09 00:00:00', 3, 0, '', 18, 1, 0, 0, NULL, NULL),
	('fsfgdd', 32, 67, NULL, NULL, '2018-09-09 00:00:00', 6, 0, '', 18, 1, 0, 0, NULL, NULL);
/*!40000 ALTER TABLE `solution` ENABLE KEYS */;

-- 导出  表 online_judge.source_code 结构
CREATE TABLE IF NOT EXISTS `source_code` (
  `solution_id` varchar(16) COLLATE utf8_bin NOT NULL COMMENT '运行id',
  `source` text COLLATE utf8_bin NOT NULL COMMENT '源代码',
  PRIMARY KEY (`solution_id`),
  CONSTRAINT `FK_source_code_solution` FOREIGN KEY (`solution_id`) REFERENCES `solution` (`solution_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- 正在导出表  online_judge.source_code 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `source_code` DISABLE KEYS */;
/*!40000 ALTER TABLE `source_code` ENABLE KEYS */;

-- 导出  表 online_judge.tag 结构
CREATE TABLE IF NOT EXISTS `tag` (
  `tag_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '标签号',
  `tagname` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '标签名',
  PRIMARY KEY (`tag_id`,`tagname`) USING BTREE,
  UNIQUE KEY `tagname` (`tagname`) USING HASH,
  KEY `tag_id` (`tag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- 正在导出表  online_judge.tag 的数据：~7 rows (大约)
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
INSERT INTO `tag` (`tag_id`, `tagname`) VALUES
	(1, '简单'),
	(2, '入门'),
	(5, 'NOIP'),
	(6, '最大和'),
	(7, 'BJWC'),
	(9, '中等'),
	(10, '数字问题');
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;

-- 导出  表 online_judge.users 结构
CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户Id',
  `username` varchar(16) COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `nickname` varchar(18) COLLATE utf8_bin DEFAULT 'coder' COMMENT '用户昵称',
  `passwd` varchar(60) COLLATE utf8_bin NOT NULL COMMENT '用户密码',
  `school` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '用户学校',
  `github_url` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'github地址',
  `blog_url` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '博客地址',
  `own_words` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '用户签名',
  `email` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '用户邮箱',
  `remark` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '用户备注',
  `submit` int(10) unsigned DEFAULT '0' COMMENT '提交次数',
  `solved` int(10) unsigned DEFAULT '0' COMMENT '成功次数',
  `access_time` datetime(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '用户注册时间',
  `session_id` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'sessionId',
  `last_login` datetime(6) DEFAULT NULL COMMENT '用户上次登录时间',
  `disabled` bit(1) DEFAULT b'0' COMMENT '是否禁用',
  `head_img` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '头像路径',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `userName` (`username`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户表';

-- 正在导出表  online_judge.users 的数据：~29 rows (大约)
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`user_id`, `username`, `nickname`, `passwd`, `school`, `github_url`, `blog_url`, `own_words`, `email`, `remark`, `submit`, `solved`, `access_time`, `session_id`, `last_login`, `disabled`, `head_img`) VALUES
	(9, 'cloud', '吱吱云', '5cba13819e624f8dc0a991a7691f3f82', 'hyit', NULL, NULL, NULL, 'zhizhiyun@cloud.com', NULL, 0, 0, '2018-01-16 19:18:22.000000', 'D949A53B3670C6888929CD38D3AD8082', '2019-05-23 18:13:14.764000', b'0', NULL),
	(10, 'morizunzhu', '就当一次路过丶', '5cba13819e624f8dc0a991a7691f3f82', 'hyit', NULL, NULL, NULL, 'morizunzhu@hotmail.com', NULL, 0, 0, '2019-01-16 19:18:27.000000', 'ABD6452AE665D9A061906E713A0953A4', '2019-05-30 16:15:31.000000', b'0', NULL),
	(11, 'wu1jin2cheng3', 'mo', '5cba13819e624f8dc0a991a7691f3f82', 'hyit', NULL, NULL, NULL, NULL, NULL, 0, 0, '2017-01-16 19:18:28.000000', '659FF11FF655FFC7DA2E2BA56C85D47E', '2019-02-21 09:01:55.000000', b'0', NULL),
	(12, 'admin', 'admin', '5cba13819e624f8dc0a991a7691f3f82', 'hyit', NULL, NULL, NULL, 'wu1jin2cheng3@live.cn', NULL, 0, 0, '2015-01-16 19:18:29.000000', 'ABD6452AE665D9A061906E713A0953A4', '2019-05-30 16:14:19.916000', b'0', NULL),
	(51, 'guihujunzhu', 'coder', '5cba13819e624f8dc0a991a7691f3f82', '清华大学', NULL, NULL, NULL, '940406032@qq.com', NULL, 0, 0, '2019-05-19 16:29:09.000000', '5623D625C2CBF03FA1FFD127F999237A', '2019-05-19 16:28:12.305000', b'0', NULL),
	(52, 'orange', '橙子', '5cba13819e624f8dc0a991a7691f3f82', NULL, NULL, NULL, NULL, '940406032@qq.com', NULL, 0, 0, '2019-05-19 16:31:28.317595', '0E1282131E8AA0E823E8546875430D7E', '2019-05-19 17:12:34.999000', b'0', NULL),
	(53, 'cities', 'coder', '5cba13819e624f8dc0a991a7691f3f82', NULL, NULL, NULL, NULL, '739551319@qq.com', NULL, 0, 0, '2019-05-24 08:33:24.457462', '3EADE8FA836EC5C13506E2F7C90E9B17', '2019-05-24 08:33:23.557000', b'0', NULL),
	(54, 'ameame', 'coder', '5cba13819e624f8dc0a991a7691f3f82', NULL, NULL, NULL, NULL, '940406032@qq.com', NULL, 0, 0, '2019-05-24 08:47:24.601071', '3EADE8FA836EC5C13506E2F7C90E9B17', '2019-05-24 08:47:23.731000', b'0', NULL),
	(55, 'paparazi', 'coder', '5cba13819e624f8dc0a991a7691f3f82', NULL, NULL, NULL, NULL, '94040406032@qq.com', NULL, 0, 0, '2019-05-24 08:47:56.390988', '3EADE8FA836EC5C13506E2F7C90E9B17', '2019-05-24 08:47:55.536000', b'0', NULL),
	(56, 'maybe', 'coder', '5cba13819e624f8dc0a991a7691f3f82', NULL, NULL, NULL, NULL, '94040406032@qq.com', NULL, 0, 0, '2019-05-24 08:48:22.566817', '3EADE8FA836EC5C13506E2F7C90E9B17', '2019-05-24 08:48:21.678000', b'0', NULL),
	(57, 'chalice', 'coder', '5cba13819e624f8dc0a991a7691f3f82', NULL, NULL, NULL, NULL, '94040406032@qq.com', NULL, 0, 0, '2019-05-24 08:49:12.808063', '3EADE8FA836EC5C13506E2F7C90E9B17', '2019-05-24 08:49:11.924000', b'0', NULL),
	(58, 'xnova', 'coder', '5cba13819e624f8dc0a991a7691f3f82', NULL, NULL, NULL, NULL, '94040406032@qq.com', NULL, 0, 0, '2019-05-24 08:49:34.895125', '3EADE8FA836EC5C13506E2F7C90E9B17', '2019-05-24 08:49:33.981000', b'0', NULL),
	(59, 'miracle', 'coder', '5cba13819e624f8dc0a991a7691f3f82', NULL, NULL, NULL, NULL, '94040406032@qq.com', NULL, 0, 0, '2019-05-24 08:50:08.647156', '3EADE8FA836EC5C13506E2F7C90E9B17', '2019-05-24 08:50:07.740000', b'0', NULL),
	(60, 'eleven', 'coder', '5cba13819e624f8dc0a991a7691f3f82', NULL, NULL, NULL, NULL, '94040406032@qq.com', NULL, 0, 0, '2019-05-24 08:50:40.852176', '3EADE8FA836EC5C13506E2F7C90E9B17', '2019-05-24 08:50:39.953000', b'0', NULL),
	(61, 'topson', 'coder', '5cba13819e624f8dc0a991a7691f3f82', NULL, NULL, NULL, NULL, '940406032@qq.com', NULL, 0, 0, '2019-05-24 08:51:06.659052', '3EADE8FA836EC5C13506E2F7C90E9B17', '2019-05-24 08:51:05.763000', b'0', NULL),
	(62, 'notail', 'coder', '5cba13819e624f8dc0a991a7691f3f82', NULL, NULL, NULL, NULL, '94040406032@qq.com', NULL, 0, 0, '2019-05-24 08:51:32.394249', '3EADE8FA836EC5C13506E2F7C90E9B17', '2019-05-24 08:51:31.553000', b'0', NULL),
	(63, 'theshy', 'coder', '5cba13819e624f8dc0a991a7691f3f82', NULL, NULL, NULL, NULL, '94040406032@qq.com', NULL, 0, 0, '2019-05-24 08:53:53.531298', '3EADE8FA836EC5C13506E2F7C90E9B17', '2019-05-24 08:53:52.619000', b'0', NULL),
	(64, 'rookie', 'coder', '5cba13819e624f8dc0a991a7691f3f82', NULL, NULL, NULL, NULL, '94040406032@qq.com', NULL, 0, 0, '2019-05-24 08:54:15.536691', '3EADE8FA836EC5C13506E2F7C90E9B17', '2019-05-24 08:54:14.638000', b'0', NULL),
	(65, 'jackeylove', 'coder', '5cba13819e624f8dc0a991a7691f3f82', NULL, NULL, NULL, NULL, '94040406032@qq.com', NULL, 0, 0, '2019-05-24 08:54:35.693538', '3EADE8FA836EC5C13506E2F7C90E9B17', '2019-05-24 08:54:34.826000', b'0', NULL),
	(66, 'baolan', 'coder', '5cba13819e624f8dc0a991a7691f3f82', NULL, NULL, NULL, NULL, '94040406032@qq.com', NULL, 0, 0, '2019-05-24 08:54:58.332044', '3EADE8FA836EC5C13506E2F7C90E9B17', '2019-05-24 08:54:57.439000', b'0', NULL),
	(67, 'faker', '云上魔王', '5cba13819e624f8dc0a991a7691f3f82', NULL, NULL, NULL, NULL, '94040406032@qq.com', NULL, 0, 0, '2019-05-24 08:56:55.062974', '3EADE8FA836EC5C13506E2F7C90E9B17', '2019-05-24 08:56:54.175000', b'0', NULL),
	(68, 'liuzirui', '刘子瑞', '5cba13819e624f8dc0a991a7691f3f82', NULL, NULL, NULL, NULL, 'ruizhi@qq.com', NULL, 0, 0, '2019-05-24 09:10:23.350864', 'AE28EEDA43599601C030DF2F0B21D498', '2019-05-24 10:23:55.955000', b'1', NULL),
	(69, 'white', 'coder', '5cba13819e624f8dc0a991a7691f3f82', NULL, NULL, NULL, NULL, '940406032@qq.com', NULL, 0, 0, '2019-05-24 10:29:01.137155', 'C4A7546E972B8C55093131FFFC6EEA8F', '2019-05-24 10:29:00.209000', b'0', NULL),
	(70, 'xiao8', 'coder', '5cba13819e624f8dc0a991a7691f3f82', NULL, NULL, NULL, NULL, '940406032@qq.com', NULL, 0, 0, '2019-05-24 10:29:53.578665', 'C4A7546E972B8C55093131FFFC6EEA8F', '2019-05-24 10:29:52.662000', b'0', NULL),
	(71, 'sky_td', 'sky_td', '5cba13819e624f8dc0a991a7691f3f82', NULL, NULL, NULL, NULL, '940406032@qq.com', NULL, 0, 0, '2019-05-24 10:31:42.897076', '61349754023492BC8271B6087394F3E4', '2019-05-24 10:31:42.403000', b'0', NULL),
	(72, 'wusheng2009', 'wusheng2009', '5cba13819e624f8dc0a991a7691f3f82', NULL, NULL, NULL, NULL, '940406032@qq.com', NULL, 0, 0, '2019-05-24 10:32:26.059447', '61349754023492BC8271B6087394F3E4', '2019-05-24 10:32:25.131000', b'0', NULL),
	(73, 'uzi_dog', 'uzi_dog', '5cba13819e624f8dc0a991a7691f3f82', NULL, NULL, NULL, NULL, '940406032@qq.com', NULL, 0, 0, '2019-05-24 10:33:52.657423', '61349754023492BC8271B6087394F3E4', '2019-05-24 10:33:51.720000', b'0', NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
