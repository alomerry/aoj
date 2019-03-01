-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.7.24-log - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.5.0.5196
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
  `solution_id` int(11) NOT NULL COMMENT '提交id，即RunID',
  `error` text COLLATE utf8_bin COMMENT '编译错误原因'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='记录编译错误的提交号（id）及原因';

-- 正在导出表  online_judge.compile_info 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `compile_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `compile_info` ENABLE KEYS */;

-- 导出  表 online_judge.contest 结构
CREATE TABLE IF NOT EXISTS `contest` (
  `contest_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '竞赛id',
  `title` varchar(1024) COLLATE utf8_bin NOT NULL COMMENT '竞赛标题',
  `access` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否可报名',
  `user_id` int(11) unsigned NOT NULL,
  `start_at` datetime NOT NULL COMMENT '开始时间(年月日时分)',
  `end_at` datetime NOT NULL COMMENT '结束时间(年月日时分)',
  `describes` varchar(4096) COLLATE utf8_bin NOT NULL COMMENT '竞赛描述',
  `privates` tinyint(4) DEFAULT '0' COMMENT '公开/内部（0/1）',
  `organizer` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '主办方',
  `max` tinyint(6) unsigned NOT NULL COMMENT '比赛人数上限',
  `now` tinyint(6) unsigned DEFAULT '0' COMMENT '当前参加人数',
  PRIMARY KEY (`contest_id`),
  KEY `FK_contest_users` (`user_id`),
  CONSTRAINT `FK_contest_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='竞赛表';

-- 正在导出表  online_judge.contest 的数据：~8 rows (大约)
/*!40000 ALTER TABLE `contest` DISABLE KEYS */;
INSERT INTO `contest` (`contest_id`, `title`, `access`, `user_id`, `start_at`, `end_at`, `describes`, `privates`, `organizer`, `max`, `now`) VALUES
	(1, 'Codeforces Round #534 (Div. 1)', b'1', 12, '2019-01-25 22:59:26', '2019-10-01 16:33:32', 'Codeforces是一家为计算机编程爱好者提供在线评测系统的俄罗斯网站。该网站由萨拉托夫国立大学的\r\n一个团体创立并负责运营。参赛范围：各大学本科生', 1, 'Codeforces ', 40, 2),
	(2, 'Educational Codeforces Round 59 (Rated for Div. 2)', b'1', 12, '2019-01-11 17:12:18', '2019-02-21 17:12:19', 'Codeforces是一家为计算机编程爱好者提供在线评测系统的俄罗斯网站。该网站由萨拉托夫国立大学的\r\n一个团体创立并负责运营。参赛范围：各大学本科生', 0, 'Codeforces ', 40, 0),
	(3, '	Codeforces Round #536 (Div. 2)', b'0', 10, '2018-01-21 17:17:19', '2018-05-21 17:17:19', 'Codeforces是一家为计算机编程爱好者提供在线评测系统的俄罗斯网站。该网站由萨拉托夫国立大学的\r\n一个团体创立并负责运营。参赛范围：各大学本科生', 0, 'Codeforces ', 40, 0),
	(4, '	Codeforces Round #535 (Div. 3)', b'0', 12, '2019-01-25 17:17:33', '2019-03-03 11:12:00', 'Codeforces是一家为计算机编程爱好者提供在线评测系统的俄罗斯网站。该网站由萨拉托夫国立大学的\r\n一个团体创立并负责运营。参赛范围：各大学本科生', 0, 'Codeforces ', 40, 0),
	(5, 'PAT | 计算机程序设计能力考试', b'1', 12, '2019-01-10 22:22:00', '2019-05-18 22:22:00', '', 0, '陈越', 40, 0),
	(7, 'ACM-ICPC', b'1', 10, '2019-01-27 09:09:00', '2019-01-31 00:00:00', 'ACM国际大学生程序设计竞赛的历史可以上溯到1970年，当时在美国德克萨斯A&M大学举办了首届比赛。当时的主办方是the Alpha Chapter of the UPE Computer Science Honor Society。作为一种全新的发现和培养计算机科学顶尖学生的方式，竞赛很快得到美国和加拿大各大学的积极响应。1977年，在ACM计算机科学会议期间举办了首次总决赛，并演变成为一年一届的多国参与的国际性比赛。', 0, '国际计算机协会', 40, 0),
	(13, 'ANOJ', b'1', 12, '2019-06-09 11:01:00', '2019-07-21 11:01:00', 'Welcome to Algorithm Note Online Judge', 0, 'Algorithm Note', 40, 0),
	(14, 'ZOJ', b'1', 10, '2019-04-20 01:01:00', '2019-05-19 01:01:00', 'Zhejiang University ACM/ICPC Team, All rights reserved.', 0, 'Welcome to ZOJ', 40, 0);
/*!40000 ALTER TABLE `contest` ENABLE KEYS */;

-- 导出  表 online_judge.contest_apply 结构
CREATE TABLE IF NOT EXISTS `contest_apply` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `contest_id` int(11) unsigned NOT NULL COMMENT '竞赛id',
  `user_id` int(11) unsigned NOT NULL COMMENT '用户id',
  `status` bit(1) DEFAULT b'0' COMMENT '是否加入(0等待确认/1已加入)',
  PRIMARY KEY (`id`),
  KEY `FK_contest_applyqueue_contest` (`contest_id`),
  KEY `FK_contest_applyqueue_users` (`user_id`),
  CONSTRAINT `FK_contest_applyqueue_contest` FOREIGN KEY (`contest_id`) REFERENCES `contest` (`contest_id`),
  CONSTRAINT `FK_contest_applyqueue_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='比赛申请表';

-- 正在导出表  online_judge.contest_apply 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `contest_apply` DISABLE KEYS */;
INSERT INTO `contest_apply` (`id`, `contest_id`, `user_id`, `status`) VALUES
	(3, 1, 9, b'1'),
	(4, 7, 9, b'1');
/*!40000 ALTER TABLE `contest_apply` ENABLE KEYS */;

-- 导出  表 online_judge.contest_problem 结构
CREATE TABLE IF NOT EXISTS `contest_problem` (
  `problem_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '题目id',
  `contest_id` int(11) unsigned DEFAULT NULL COMMENT '竞赛id',
  `title` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '标题',
  `num` int(11) NOT NULL COMMENT '竞赛题目编号',
  KEY `FK_contest_problem_problems` (`problem_id`),
  KEY `FK_contest_problem_contest` (`contest_id`),
  CONSTRAINT `FK_contest_problem_contest` FOREIGN KEY (`contest_id`) REFERENCES `contest` (`contest_id`),
  CONSTRAINT `FK_contest_problem_problems` FOREIGN KEY (`problem_id`) REFERENCES `problems` (`problem_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='竞赛题目';

-- 正在导出表  online_judge.contest_problem 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `contest_problem` DISABLE KEYS */;
INSERT INTO `contest_problem` (`problem_id`, `contest_id`, `title`, `num`) VALUES
	(2, 1, '1', 2);
/*!40000 ALTER TABLE `contest_problem` ENABLE KEYS */;

-- 导出  表 online_judge.news 结构
CREATE TABLE IF NOT EXISTS `news` (
  `new_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '新闻id',
  `user_id` int(11) unsigned NOT NULL COMMENT '用户id',
  `title` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '标题',
  `content` text COLLATE utf8_bin NOT NULL COMMENT '内容',
  `time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`new_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='新闻(首页显示)';

-- 正在导出表  online_judge.news 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `news` DISABLE KEYS */;
/*!40000 ALTER TABLE `news` ENABLE KEYS */;

-- 导出  表 online_judge.privilege 结构
CREATE TABLE IF NOT EXISTS `privilege` (
  `user_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户帐号',
  `rightstr` char(30) COLLATE utf8_bin NOT NULL COMMENT '分组',
  `defunct` char(1) COLLATE utf8_bin NOT NULL DEFAULT 'A' COMMENT '是否屏蔽(A:active I:inactive)',
  PRIMARY KEY (`user_id`),
  CONSTRAINT `FK_privilege_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户分组';

-- 正在导出表  online_judge.privilege 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `privilege` DISABLE KEYS */;
INSERT INTO `privilege` (`user_id`, `rightstr`, `defunct`) VALUES
	(10, 'admin_ce', 'A'),
	(12, 'admin_bcefghjkl', 'A'),
	(25, 'admin', 'A');
/*!40000 ALTER TABLE `privilege` ENABLE KEYS */;

-- 导出  表 online_judge.problems 结构
CREATE TABLE IF NOT EXISTS `problems` (
  `problem_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '问题ID',
  `title` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '问题标题',
  `defunct` char(1) COLLATE utf8_bin NOT NULL DEFAULT '1' COMMENT '屏蔽-公开-部分公开-绝对私有0/1/2/3',
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
  PRIMARY KEY (`problem_id`),
  KEY `FK_problems_users` (`create_by`),
  CONSTRAINT `FK_problems_users` FOREIGN KEY (`create_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='题目表';

-- 正在导出表  online_judge.problems 的数据：~3 rows (大约)
/*!40000 ALTER TABLE `problems` DISABLE KEYS */;
INSERT INTO `problems` (`problem_id`, `title`, `defunct`, `create_by`, `hint`, `created_at`, `source`, `description`, `click`, `accepted`, `submit`, `sample_input`, `sample_output`, `memory_limit`, `time_limit`, `output`, `input`) VALUES
	(1, 'A+B 输入输出练习I', '3', 12, '', '2017-01-28 09:05:58', 'Codeup', '你的任务是计算a+b。这是为了acm初学者专门设计的题目。你肯定发现还有其他题目跟这道题的标题类似，这些问题也都是专门为初学者提供的。', 0, 0, 0, '<pre>1 5\r\n10 20</pre>', '<pre><span>6\r\n30</span></pre>', 16, 500, '对于输入的每对a和b，你需要依次输出a、b的和。<br>\r\n如对于输入中的第二对a和b，在输出中它们的和应该也在第二行。', '输入包含一系列的a和b对，通过空格隔开。一对a和b占一行。'),
	(2, 'A+B', '1', 12, '', '2018-01-28 09:06:01', NULL, '666', 0, 0, 0, '666', '666', 16, 500, '666', '666'),
	(6, '陶陶摘苹果', '1', 12, '', '2019-01-28 09:06:04', NULL, '陶陶家的院子里有一棵苹果树，每到秋天树上就会结出10个苹果。苹果成熟的时候，陶陶就会跑去摘苹果。陶陶有个30厘米高的板凳，当她不能直接用手摘到苹果的时候，就会踩到板凳上再试试。', 0, 0, 0, '<pre>100 200 150 140 129 134 167 198\r\n200 111\r\n110\r\n</pre>', '<pre>5\r\n</pre>', 16, 500, '每组输出包括一行，这一行只包含一个整数，表示陶陶能够摘到的苹果的数目。', '每组输入数据包括两行。第一行包含10个100到200之间（包括100和200）的整数（以厘米为单位）分别表示10个苹果到地面的高度，两个相邻的整数之间用一个空格隔开。第二行只包括一个100到120之间（包含100和120）的整数（以厘米为单位），表示陶陶把手伸直的时候能够达到的最大高度。');
/*!40000 ALTER TABLE `problems` ENABLE KEYS */;

-- 导出  表 online_judge.solution 结构
CREATE TABLE IF NOT EXISTS `solution` (
  `solution_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '运行id',
  `problem_id` int(11) unsigned NOT NULL COMMENT '问题id',
  `user_id` int(11) unsigned NOT NULL COMMENT '用户id',
  `time` int(11) unsigned NOT NULL COMMENT '用时（ms）',
  `memory` int(11) unsigned NOT NULL COMMENT '所用空间',
  `in_date` datetime NOT NULL COMMENT '加入时间',
  `result` smallint(6) unsigned NOT NULL COMMENT '结果（4：AC）',
  `language` tinyint(4) unsigned NOT NULL COMMENT '语言',
  `ip` char(255) COLLATE utf8_bin NOT NULL COMMENT '用户ip',
  `contest_id` int(11) unsigned DEFAULT NULL COMMENT '所属于竞赛组',
  `valid` tinyint(4) unsigned NOT NULL COMMENT '是否有效',
  `num` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '题目在竞赛中属于顺序号',
  `code_lenght` int(11) unsigned NOT NULL COMMENT '代码长度',
  `judgetime` datetime DEFAULT NULL COMMENT '判题时间',
  PRIMARY KEY (`solution_id`),
  KEY `FK_solution_problems` (`problem_id`),
  KEY `FK_solution_users` (`user_id`),
  KEY `FK_solution_contest` (`contest_id`),
  CONSTRAINT `FK_solution_contest` FOREIGN KEY (`contest_id`) REFERENCES `contest` (`contest_id`),
  CONSTRAINT `FK_solution_problems` FOREIGN KEY (`problem_id`) REFERENCES `problems` (`problem_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_solution_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='程序运行结果记录';

-- 正在导出表  online_judge.solution 的数据：~4 rows (大约)
/*!40000 ALTER TABLE `solution` DISABLE KEYS */;
INSERT INTO `solution` (`solution_id`, `problem_id`, `user_id`, `time`, `memory`, `in_date`, `result`, `language`, `ip`, `contest_id`, `valid`, `num`, `code_lenght`, `judgetime`) VALUES
	(25, 6, 10, 500, 16, '2019-01-17 09:14:32', 1, 1, '172.20.10.8', NULL, 1, 0, 128, '2019-01-17 09:14:32'),
	(26, 6, 10, 500, 16, '2019-01-17 09:14:35', 5, 1, '172.20.10.8', NULL, 1, 0, 128, '2019-01-17 09:14:35'),
	(27, 6, 10, 500, 16, '2019-01-17 09:19:14', 6, 1, '172.20.10.8', NULL, 1, 0, 128, '2019-01-17 09:19:14'),
	(28, 6, 10, 500, 16, '2019-01-17 09:19:42', 4, 1, '172.20.10.8', NULL, 1, 0, 128, '2019-01-17 09:19:42');
/*!40000 ALTER TABLE `solution` ENABLE KEYS */;

-- 导出  表 online_judge.source_code 结构
CREATE TABLE IF NOT EXISTS `source_code` (
  `solution_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '运行id',
  `source` text COLLATE utf8_bin NOT NULL COMMENT '源代码',
  PRIMARY KEY (`solution_id`),
  CONSTRAINT `FK_source_code_solution` FOREIGN KEY (`solution_id`) REFERENCES `solution` (`solution_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- 正在导出表  online_judge.source_code 的数据：~4 rows (大约)
/*!40000 ALTER TABLE `source_code` DISABLE KEYS */;
INSERT INTO `source_code` (`solution_id`, `source`) VALUES
	(25, 'int main(){\r\n    return 0;\r\n}'),
	(26, 'int main(){\r\n    return 0;\r\n}'),
	(27, 'ddd'),
	(28, 'int main {\r\n    reutnasd   dfa \r\n}');
/*!40000 ALTER TABLE `source_code` ENABLE KEYS */;

-- 导出  表 online_judge.users 结构
CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户Id',
  `username` varchar(16) COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `nickname` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '用户昵称',
  `passwd` varchar(60) COLLATE utf8_bin NOT NULL COMMENT '用户密码',
  `school` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '用户学校',
  `email` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '用户邮箱',
  `remark` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '用户备注',
  `submit` int(10) unsigned DEFAULT '0' COMMENT '提交次数',
  `solved` int(10) unsigned DEFAULT '0' COMMENT '成功次数',
  `access_time` datetime DEFAULT NULL COMMENT '用户注册时间',
  `session_id` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'sessionId',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `userName` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户表';

-- 正在导出表  online_judge.users 的数据：~14 rows (大约)
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`user_id`, `username`, `nickname`, `passwd`, `school`, `email`, `remark`, `submit`, `solved`, `access_time`, `session_id`) VALUES
	(9, 'cloud', '吱吱云', '5cba13819e624f8dc0a991a7691f3f82', 'hyit', '3122172709@qq.com', NULL, 0, 0, '2018-01-16 19:18:22', 'c4fe251f-2d59-4be7-9222-4e42643e4e1c'),
	(10, 'morizunzhu', '就当一次路过丶', '5cba13819e624f8dc0a991a7691f3f82', 'hyit', 'morizunzhu@163.com', NULL, 0, 0, '2019-01-16 19:18:27', '3daacd79-622d-406d-a210-bd516556b0bf'),
	(11, 'wu1jin2cheng3', '末日尊主恋火', '5cba13819e624f8dc0a991a7691f3f82', 'hyit', 'wu1jin2cheng3@live.cn', NULL, 0, 0, '2017-01-16 19:18:28', NULL),
	(12, 'admin', 'admin', '5cba13819e624f8dc0a991a7691f3f82', 'hyit', '940406032@qq.com', NULL, 0, 0, '2015-01-16 19:18:29', '5cc9a29c-20b6-4ae9-9bec-0464f518d626'),
	(15, 'wolves', '狼live', '5cba13819e624f8dc0a991a7691f3f82', 'hyit', '110@110.com', NULL, 0, 0, '2019-01-23 11:15:48', '9669503A6D6842B8032EE3BA8AFAB103'),
	(17, 'static', '无忧无虑丶', '5cba13819e624f8dc0a991a7691f3f82', '淮阴工学院', '940406032@qq.com', NULL, 0, 0, NULL, '7930086920F4D7430C753F9338F60BB2'),
	(25, '452111', NULL, '11145', NULL, NULL, NULL, 0, 0, NULL, NULL),
	(40, 'sdfdsa', 'sadsa', 'sdasds', NULL, NULL, NULL, 0, 0, NULL, NULL),
	(41, 'sadsa', 'fdasdf', 'dsafd', NULL, NULL, NULL, 0, 0, NULL, NULL),
	(42, 'dasf', 'dafdsa', 'fgsdfdfdsg', NULL, NULL, NULL, 0, 0, NULL, NULL),
	(43, 'safdsaf', 'sdafdasf', 'sdafsdafsad', NULL, NULL, NULL, 0, 0, NULL, NULL),
	(44, 'dsdss', 'saaaa', 'asFDSAFDS', NULL, NULL, NULL, 0, 0, NULL, NULL),
	(46, 'dasfds', NULL, 'dsfsf', NULL, NULL, NULL, 0, 0, NULL, NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
