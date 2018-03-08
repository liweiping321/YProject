package com.game.protobuf;

/**
 * @since  2017-06-08 09:55:23 
 */
public class MsgCode {

    /***** 公共模块。消息号范围：[1,500]。 所属： Common.proto ****/

    /** 心跳客户端请求 1*/
    public static final int PingReq = 1;
    /** 心跳服务端响应 2*/
    public static final int PingResp = 2;
    /** 通用提示消息 4*/
    public static final int TipMsgResp = 4;

    /***** 玩家模块。消息号范围：[501,600]。 所属： Logic.proto ****/

    /** 用户登录请求 501*/
    public static final int LoginReq = 501;
    /** 用户登录响应 502*/
    public static final int LoginResp = 502;

    /***** 游戏匹配模块。消息号范围：[601,700]。 所属： Match.proto ****/

    /** 请求撮合加入游戏 601*/
    public static final int StartMatchReq = 601;
    /** 准备撮合 602*/
    public static final int StartMatchResp = 602;

    /***** 游戏战斗模块。消息号范围：[701,900]。 所属： Fighting.proto ****/

    /** 游戏加载进度 702*/
    public static final int GameLoadingResp = 702;
    /** 加载进度 703*/
    public static final int LoadingProcessReq = 703;
    /** 加载进度 704*/
    public static final int LoadingProcessResp = 704;
    /** 游戏开始(所有玩家加载完成) 706*/
    public static final int GameStartResp = 706;
    /** 位移消息 707*/
    public static final int MoveReq = 707;
    /** 位移消息 708*/
    public static final int MoveResp = 708;
    /** 停止移动 709*/
    public static final int MoveStopReq = 709;
    /** 停止移动 710*/
    public static final int MoveStopResp = 710;
    /** 属性值同步 712*/
    public static final int PropValueUpdateResp = 712;
    /** 移动同步点测试专用 714*/
    public static final int MoveCurPointResp = 714;
    /** 生物死亡消息 716*/
    public static final int LivingDieResp = 716;
    /** 生物移除消息 718*/
    public static final int LivingRemoveResp = 718;
    /** 同步朝向 719*/
    public static final int FaceDirReq = 719;
    /** 同步朝向 720*/
    public static final int FaceDirResp = 720;
    /** Buff添加或更新 722*/
    public static final int BuffUpdateResp = 722;
    /** Buff移除 724*/
    public static final int BuffRemoveResp = 724;
    /** 使用技能 725*/
    public static final int SkillUseReq = 725;
    /** 使用技能 726*/
    public static final int SkillUseResp = 726;
    /** 技能释放结果 728*/
    public static final int SkillResultResp = 728;
    /** 技能CD更新 730*/
    public static final int SkillCdResp = 730;
    /** 武器换弹夹 732*/
    public static final int WeaponReloadResp = 732;
    /** 技能中断 734*/
    public static final int SkillStopResp = 734;
    /** 技能更新 736*/
    public static final int SkillUpdateResp = 736;
    /** 生物出生 738*/
    public static final int LivingBornResp = 738;
    /** 英雄复活时间 740*/
    public static final int HeroReLiveTimeResp = 740;
    /** 拾取掉落成功 742*/
    public static final int PickDropResp = 742;
    /** 发送排名数据 744*/
    public static final int RankBoardResp = 744;
    /** 部分战斗统计数据更新 746*/
    public static final int StatisticUpdateResp = 746;
    /** 战斗结束时发送 748*/
    public static final int GameOverResp = 748;
    /** 天赋升级列表 750*/
    public static final int TalentUpLevelListResp = 750;
    /** 天赋升级 751*/
    public static final int TalentUpLevelReq = 751;
    /** 天赋升级成功 752*/
    public static final int TalentUpLevelSucResp = 752;
    /** 英雄击杀敌人后发送连续击杀人数和一段时间间隔内连续杀人数 754*/
    public static final int KillAchieveResp = 754;

}