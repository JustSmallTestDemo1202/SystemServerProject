/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.icee.myth.network.protobufmessage;

/**
 *
 * @author lidonglin
 */
public class ProtobufMessageType {
    public static final int C2S_LOGIN = 1;
    public static final int C2S_CREATECHAR = 2;

    public static final int C2S_GUIDE_NEXT = 5;         // 下一步新手引导

    public static final int C2S_STAGE_ENTER = 10;       // 进入副本
    public static final int C2S_STAGE_CONTINUE = 11;    // 继续副本
    public static final int C2S_STAGE_LEAVE = 12;       // 离开副本
    public static final int C2S_STARTPVEBATTLE = 13;    // 启动战斗
    public static final int C2S_STAGE_REVIVE = 14;      // 复活

    public static final int C2S_CARD_STRENGTHEN = 20;       // 卡片强化
    public static final int C2S_CARD_TRANSFORM = 21;        // 卡片变身
    public static final int C2S_CARD_SOLD = 22;             // 出售卡片
    public static final int C2S_CARD_DRAW = 23;             // 抽取卡片

//    public static final int C2S_BIGSTAGE_REFRESH = 25;  // 刷新精英副本

    public static final int C2S_MAIL_GET_LIST = 30;       // 获取邮件列表
    public static final int C2S_MAIL_GET_INFO = 31;       // 获取邮件信息
    public static final int C2S_MAIL_GET_REWARD = 32;     // 获取邮件奖励
    public static final int C2S_MAIL_REMOVE = 33;         // 删除邮件

    public static final int C2S_QUEST_SUBMIT = 35;      // 交任务

    public static final int C2S_TALK = 40;     //聊天

    public static final int C2S_CONTSIGN_RECEIVE_CUMULATIVE_SIGN_REWARD = 44;       // 领取累计签到奖励
    public static final int C2S_CONTSIGN_RECEIVE_CONSECUTIVE_SIGN_REWARD = 45;      // 领取连续签到奖励
    public static final int C2S_CONTSIGN_RECEIVE_LIVENESS_REWARD = 46;      // 领取活跃度奖励

    public static final int C2S_VIPGIFT_RECEIVE = 48;   // 领取VIP奖励

    public static final int C2S_SANDBOX_DEPLOY = 50;    // 部署阵型

    public static final int C2S_HEGEMONY_ONE_TOKEN_FIGHT = 55;      // 争霸：一军令战斗
    public static final int C2S_HEGEMONY_THREE_TOKEN_FIGHT = 56;    // 争霸：三军令战斗
    public static final int C2S_HEGEMONY_REFRESH = 57;              // 争霸：刷新
    public static final int C2S_HEGEMONY_GET_PAY = 58;              // 争霸：领军饷

    public static final int C2S_SOCIAL_ADD_CONCERN = 60;        // 添加关注
    public static final int C2S_SOCIAL_REMOVE_CONCERN = 61;     // 删除关注
    public static final int C2S_SOCIAL_OTHERPLAYERINFO = 62;    // 查看他人信息
    public static final int C2S_SOCIAL_CONCERN_NOTE = 63;       // 通知关注

    public static final int C2S_BUY_ENERGY = 70;            // 购买神域
    public static final int C2S_BUY_TOKEN = 71;             // 购买军令

    public static final int C2S_ITEM_COMBINE = 75;      // 物品合成

    public static final int C2S_GET_BILL = 80;      //刷新提取充值金额
    public static final int C2S_GET_COUPON = 81;    // 领取Coupon

    public static final int C2S_NORMAL_ACTIVITY_GET_LIST = 85; // 获取活动列表
    public static final int C2S_NORMAL_ACTIVITY_GET_ITEM_LIST = 86;     // 获得活动项列表
    public static final int C2S_NORMAL_ACTIVITY_ENTER_STAGE = 87;   // 进入活动关卡
    public static final int C2S_NORMAL_ACTIVITY_GET_REWARD = 88;   // 获取普通活动奖励

    public static final int C2S_CARD_DRAW_ACTIVITY_GET_LIST = 90;   // 获取卡包活动（商城活动）
    public static final int C2S_STAGE_ACTIVITY_GET_LIST = 91;       // 获取关卡活动（关卡掉卡概率翻倍或体力减半）

    public static final int C2S_BASE_BARRACK_DEPLOY = 94;           // 部署兵营
    public static final int C2S_BASE_ORDNANCE_DEPLOY = 95;          // 部署军械所
    public static final int C2S_BASE_COUNCIL_DEPLOY = 96;           // 部署军机处
    public static final int C2S_BASE_TRAINING_DEPLOY = 98;          // 部署校场
    public static final int C2S_BASE_MINE_DEPLOY = 99;              // 部署银矿
    public static final int C2S_BASE_GET_MINE_INCOME_INFO = 100;    // 获取银矿产出信息
    public static final int C2S_BASE_HARVEST_SELF_MINE_INCOME = 101;// 收获自己银矿产出
    public static final int C2S_BASE_HARVEST_CAPE_MINE_INCOME = 102;// 收获臣属银矿产出

    public static final int C2S_BASE_GET_OCCUPY_INFO = 105;         // 获得大帐信息
    public static final int C2S_BASE_GET_TARGETS = 106;             // 获取可征服列表
    public static final int C2S_BASE_ATTACK = 107;                  // 攻击他人据点
    public static final int C2S_BASE_ONE_TOKEN_RESISTANCE = 108;    // 反抗(1军令)
    public static final int C2S_BASE_THREE_TOKEN_RESISTANCE = 109;  // 反抗(3军令)

    //上述功能需要通过配置文件开启
    public static final int C2S_GM_IGNORE_GUIDE_STEP = 200;         //GM命令跳过新手引导
    public static final int C2S_GM_ADD_ENERGY = 201;                //GM命令增加体力

    //values of type
//    public static final int S2C_ERRORRESULT = 0;       //错误，和错误码一起确定错误原因
    public static final int S2C_ENTERGAMERET = 1;
    public static final int S2C_LOGINERROR = 2;
    public static final int S2C_CREATECHARERROR = 3;
    public static final int S2C_NOCHARRET = 4;

    public static final int S2C_GUIDE_STEP_CHANGE = 7;  // 新手引导步骤改变

    public static final int S2C_CARD_ADD = 10;      // 获得卡片
    public static final int S2C_CARD_REMOVE = 11;   // 失去卡片
    public static final int S2C_CARD_LEVEL_EXPERIENCE_CHANGE = 12;    // 卡片等级经验改变
    public static final int S2C_CARD_TRANSFORM = 13;    // 卡片变身
    public static final int S2C_CARD_DRAWED = 14;   // 抽卡

    public static final int S2C_STAGE_STATE = 20;   // 关卡状态
    public static final int S2C_STAGE_LEAVE = 21;   // 离开关卡
    public static final int S2C_STAGE_REVIVED = 22; // 关卡复活

    public static final int S2C_ENERGY_CHANGE = 30; // 神谕改变
    public static final int S2C_TOKEN_CHANGE = 31;  // 军令改变

    public static final int S2C_BIGSTAGE_STATUS_CHANGE = 40; // 精英关卡状态改变
    public static final int S2C_CURRENT_BIG_STAGE_ID_CHANGE = 41;     // 当前精英关卡号改变
    public static final int S2C_CURRENT_NORMAL_STAGE_ID_CHANGE = 42;  // 当前普通关卡号改变

    public static final int S2C_EXPERIENCE_CHANGE = 50; // 经验改变
    public static final int S2C_SILVER_CHANGE = 51;
    public static final int S2C_GOLD1_CHANGE = 52;
    public static final int S2C_GOLD2_CHANGE = 53;

    public static final int S2C_RANK_EXPERIENCE_CHANGE = 55;    //军衔经验（功勋）改变

    public static final int S2C_LEVELUP = 60;   // 升级
    public static final int S2C_RANK_LEVELUP = 61;  // 升军衔

    public static final int S2C_PVEBATTLERESULT = 65;  // 打怪战斗结果

    public static final int S2C_BAGSIZE_CHANGE = 70;              //背包数量改变

    public static final int S2C_TALK = 75;   // 聊天

    public static final int S2C_SANDBOX_INFO = 80;   // 沙盘信息

    public static final int S2C_QUEST_FINISHED = 85;    // 任务完成
    public static final int S2C_QUEST_SUBMITTED = 86;    // 任务已提交
    public static final int S2C_QUEST_ADD_LIST = 87;    // 新增任务

    public static final int S2C_RELATION_INFO = 90;                   // 关系信息
    public static final int S2C_CONCERNS_INFO = 91;                    // 关注信息
    public static final int S2C_SOCIAL_OTHERPLAYERDETAIL = 92;           //他人信息查看
    public static final int S2C_SOCIAL_REMOVE_CONCERN = 93;               //删除关注
    public static final int S2C_FRIEND_ENTERED = 94;        //好友上线
    public static final int S2C_FRIEND_LEFT = 95;           // 好友下线
    public static final int S2C_SOCIAL_BECOME_FRIEND = 96;  // 成为好友（关注变好友）
    public static final int S2C_SOCIAL_BECOME_CONCERN = 97; // 变为关注（好友变关注）
    public static final int S2C_STRANGERS = 98; // 陌生人信息
    public static final int S2C_FRIEND_HELP_COOLDOWN = 99;  // 朋友帮助冷却
    public static final int S2C_FRIEND_LEADER_CARD_CHANGE = 100;    // 朋友阵型队长改变
    public static final int S2C_FRIEND_LEVEL_CHANGE = 101;  // 朋友等级改变
    public static final int S2C_ADD_CONCERN_SUCCESS = 102;  // 添加关注成功
    public static final int S2C_ADD_CONCERN_FAIL = 103;     // 添加关注失败

    public static final int S2C_NOTIFICATION = 105;    //发通知

    public static final int S2C_BUY_ENERGY_COUNT_CHANGE = 110; // 买了多少次神域

    public static final int S2C_VIP_CHANGE = 115;      //vip改变

    public static final int S2C_ITEM_NUM_CHANGE = 120;  // 物品数量改变
    public static final int S2C_ITEM_COMBINED = 121;    // 物品合成成功

    public static final int S2C_GET_COUPON = 125;    // 领取Coupon
    public static final int S2C_GET_COUPON_ERROR = 126;    // 领取Coupon失败

    public static final int S2C_REALTIME = 130;    // 对时

    public static final int S2C_MAIL_NUM_CHANGE = 134;     // 邮件数
    public static final int S2C_MAIL_LIST = 135;    // 邮件列表
    public static final int S2C_MAIL_INFO = 136;    // 邮件信息
    public static final int S2C_MAIL_GOTTEN = 137;  // 邮件领取
    public static final int S2C_MAIL_REMOVE = 138;  // 删除邮件

    public static final int S2C_NORMAL_ACTIVITY_GOTTEN = 140;  // 领取活动奖励
    public static final int S2C_NORMAL_ACTIVITY_LIST = 141;         // 活动列表
    public static final int S2C_NORMAL_ACTIVITY_ITEM_LIST = 142;    // 活动项目表
    public static final int S2C_NORMAL_ACTIVITY_OPERATE_TIME = 143; // 活动操作时间

    public static final int S2C_CARD_DRAW_ACTIVITY_LIST = 145;  // 卡包活动列表(商城列表)
    public static final int S2C_STAGE_ACTIVITY_LIST = 146;      // 关卡活动列表（掉卡概率翻倍和体力减半活动）

    public static final int S2C_CONTSIGN_CUMULATIVE_REWARD_RECEIVED = 149;   // 已领取累计签到奖
    public static final int S2C_CONTSIGN_CONSECUTIVE_REWARD_RECEIVED = 150;  // 签到奖
    public static final int S2C_CONTSIGN_LIVENESSREWARD = 151;  // 签到活跃度奖
    public static final int S2C_CONTSIGN = 152; // 签到信息改变
    public static final int S2C_CONTSIGN_LIVENESS_CHANGE = 153; // 签到活跃度改变

    public static final int S2C_VIPGIFT_RECEIVE = 155;  // vip奖励

    public static final int S2C_HEGEMONY_BATTLERESULT = 160;    // 争霸结果
    public static final int S2C_HEGEMONY_TARGET_STATUS_CHANGE = 161;    // 争霸目标状态改变
    public static final int S2C_REFRESH_HEGEMONY_COUNT_CHANGE = 162;    // 争霸刷新次数改变
    public static final int S2C_HEGEMONY_TARGETS_CHANGE = 163;  // 争霸列表刷新
    public static final int S2C_HEGEMONY_GET_PAY = 164;         // 争霸领军饷

    public static final int S2C_BASE_TRAINING_INFO = 170;   // 校场部署信息
    public static final int S2C_BASE_MINE_INFO = 171;       // 银矿部署信息
    public static final int S2C_BASE_BARRACK_INFO = 172;    // 兵营部署信息
    public static final int S2C_BASE_ORDNANCE_INFO = 173;   // 军械所部署信息
    public static final int S2C_BASE_COUNCIL_INFO = 174;    // 军机处部署信息
    public static final int S2C_BASE_MINE_INCOME_INFO = 175;// 银矿生产信息
    public static final int S2C_BASE_OCCUPY_INFO = 176;     // 大帐信息
    public static final int S2C_BASE_TARGETS = 177;     // 据点可征服列表
    public static final int S2C_BASE_REMOVE_CAPECOLLINSON = 178;    // 删除臣属
    public static final int S2C_BASE_ADD_CAPECOLLINSON = 179;       // 增加臣属
    public static final int S2C_BASE_ATTACK_BATTLE_RESULT = 180;    // 征服战结果
    public static final int S2C_BASE_RESISTANCE_BATTLE_RESULT = 181;// 反抗战结果
    public static final int S2C_BASE_FREE = 182;                    // 自由

    public static final int S2C_FORBIDTALK = 200;

    public static final int MANAGER2SERVER_HEARTBEAT = 14000;
    public static final int MANAGER2SERVER_SHUTDOWN = 14001;
    public static final int MANAGER2SERVER_GM = 14002;
    public static final int MANAGER2SERVER_BILLNOTIFY = 14003;
    public static final int MANAGER2CLUSTER_GETSERVERVERSION = 14004;

    public static final int SERVER2MANAGER_HEARTBEAT = 15000;
    public static final int SERVER2MANAGER_BATTLE_WIN_RATE_RESULT = 15001;
    public static final int SERVER2MANAGER_CARD_DRAW_RATE_RESULT = 15002;
    public static final int SERVER2MANAGER_UNINIT_OCCUPY_INFO = 15003;
    public static final int SERVER2MANAGER_FIGHTING_OCCUPY_INFO = 15004;
}
