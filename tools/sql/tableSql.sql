drop table if EXISTS t_s_enhance_level;
create TABLE t_s_enhance_level(`ID` int(11) NOT NULL  COMMENT '精炼表ID',`Name` varchar(200) NOT NULL  COMMENT '名称',`EnhancedTimes` int(11) NOT NULL  COMMENT '精炼等级',`PropertyId` int(11) NOT NULL  COMMENT '强化加成属性
1攻击，2生命，8护甲，9速度，29神圣伤害',`LevelLimit` int(11) NOT NULL  COMMENT '强化所需突破等级
见t_s_enhance_refine.xls表UpLevel字段',`CostItem` varchar(200) NOT NULL  COMMENT '消耗道具ID读取item表格式：ItemId,Num|ItemId,Num…',`CostGold` varchar(200) NOT NULL  COMMENT '消耗货币读取item表
格式：ItemId,Num|ItemId,Num…',`SuccessRate` int(11) NOT NULL  COMMENT '强化成功率',`PercentValue` int(11) NOT NULL  COMMENT '百分比加成值',`FixValue` int(11) NOT NULL  COMMENT '固定值加成值', PRIMARY KEY (ID) ) ENGINE=InnoDB CHARACTER set utf8;
drop table if EXISTS t_s_enhance_refine;
create TABLE t_s_enhance_refine(`ID` int(11) NOT NULL  COMMENT '����',`PropertyId` int(11) NOT NULL  COMMENT '��������
1������2������8������9������29��������',`UpLevel` int(11) NOT NULL  COMMENT '������������',`EnhanceLvLimit` int(11) NOT NULL  COMMENT '����������������
��t_s_enhance_level.xls��',`NextUpId` int(11) NOT NULL  COMMENT '������������',`SuccessRate` int(11) NOT NULL  COMMENT '��������',`CostItem` varchar(200) NOT NULL  COMMENT '��������������
itemID,Num|itemID,Num',`CostMoney` varchar(200) NOT NULL  COMMENT '��������������
itemID,Num|itemID,Num', PRIMARY KEY (ID) ) ENGINE=InnoDB CHARACTER set utf8;
