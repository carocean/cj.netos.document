- 第一级为频道，不挂实体		
  第二级为分类，可挂实体		
  第三级为品牌类，可挂实体，品牌类可无		
  
- 地理频道
public class GeoChannel {
    String id;
    String title;
    String leading;
    int sort;
- 地物分类
public class GeoCategory {
    String id;
    String title;
    String leading;
    String channel;//所属频到标识
    String creator;
    boolean isHot;//是否热点类别
    int sort;
    long ctime;
    double defaultRadius;
    GeoCategoryMoveMode moveMode;
- 地物品牌
public class GeoBrand {
    String id;
    String title;
    String channel;
    String category;
    boolean isHot;//是否热点品牌
    int sort;
- 移动模式
  public enum GeoCategoryMoveMode {
      /**
       * 固定位置感知器
       */
      unmoveable,
      /**
       * 可自行移动感知器
       */
      moveableSelf,
      /**
       * 需要依赖于感知器创建者的设备感知器定位的
       */
      moveableDependon,
      
- 初始化数据
```json
[
  {
    "id": "restaurants",
    "title": "吃",
    "leading": "",
    "sort": 0,
    "categories": [
      {
        "id": "kuaican",
        "title": "快餐",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
          {
            "id": "kfc",
            "title": "肯德基",
            "isHot": false,
            "sort": 0
          },
          {
            "id": "mcdonalds",
            "title": "麦当劳",
            "isHot": false,
            "sort": 0
          }
        ]
      },
      {
        "id": "zhongcan",
        "title": "中餐",
        "leading": "",
        "isHot": true,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "huoguo",
        "title": "火锅",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "kafeiting",
        "title": "咖啡厅",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "foods",
        "title": "美食",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "xiaochi",
        "title": "小吃",
        "leading": "",
        "isHot": true,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "zizhucan",
        "title": "自助餐",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "chacanting",
        "title": "茶餐厅",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "dangaodian",
        "title": "糕饼店",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "lucai",
        "title": "鲁菜",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "chuancai",
        "title": "川菜",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "yuecai",
        "title": "粤菜",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "huaiyangcai",
        "title": "淮扬菜",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "mincai",
        "title": "闽菜",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "zhejiangcai",
        "title": "浙江菜",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "xiangcai",
        "title": "湘菜",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "huicai",
        "title": "徽菜",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "jincai",
        "title": "京菜",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "ecai",
        "title": "鄂菜",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "xibeicai",
        "title": "西北菜",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "haixian",
        "title": "海鲜",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "dongbeicai",
        "title": "东北菜",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "shushi",
        "title": "素食",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "chuanyicai",
        "title": "创意菜",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "xican",
        "title": "西餐",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "chayiguan",
        "title": "茶艺馆",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "ribenliaoli",
        "title": "日本料理",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "hanguoliaoli",
        "title": "韩国料理",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "qingzhen",
        "title": "清真",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "dangaofang",
        "title": "蛋糕房",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "tianpindian",
        "title": "甜品店",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "lengyidian",
        "title": "冷饮店",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      }
    ]
  },
  {
    "id": "accommodations",
    "title": "住",
    "leading": "",
    "sort": 0,
    "categories": [
      {
        "id": "binguan",
        "title": "宾馆",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "xingjibinguan",
        "title": "星级宾馆",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "jiudian",
        "title": "酒店",
        "leading": "",
        "isHot": true,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "kuanjiejiudian",
        "title": "快捷酒店",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "zhaodaisuo",
        "title": "招待所",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "zhongdianfang",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "rujia",
        "title": "如家",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "hanting",
        "title": "汉庭",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "hotels",
        "title": "旅馆",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "sanxingji",
        "title": "三星级",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "四星级",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "五星级",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "速8",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "布丁",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "莫泰",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "格林豪泰",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "锦江之星",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "7天",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      }
    ]
  },
  {
    "id": "transits",
    "title": "行",
    "leading": "",
    "sort": 0,
    "categories": [
      {
        "id": "",
        "title": "加油站",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "汽车站",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "火车站",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "公交站",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "客运站",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "汽车维修",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "火车票代售",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "停车场",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "中石化",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "修车",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "洗车",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "驾校",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "4S店",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "加气站",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "公交卡充值",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "自行车租赁",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "服务区",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "充电站",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "mobiles",
        "title": "行人",
        "leading": "",
        "isHot": true,
        "sort": 0,
        "moveMode": "moveableSelf",
        "brands": [
        ]
      },
      {
        "id": "taxis",
        "title": "出租车",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "moveableDependon",
        "brands": [
        ]
      },
      {
        "id": "bus",
        "title": "巴士",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "moveableDependon",
        "brands": [
        ]
      }
    ]
  },
  {
    "id": "recreations",
    "title": "玩",
    "leading": "",
    "sort": 0,
    "categories": [
      {
        "id": "",
        "title": "网吧",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "洗浴",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "足疗",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "KTV",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "夜店",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "电影院",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "酒吧",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "农家乐",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "体育馆",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "游泳馆",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "台球",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "按摩",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "桑拿",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "运动场",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "娱乐",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "游戏厅",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "棋牌室",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "舞厅",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "溜冰场",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "桌游",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "儿童乐园",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "温泉",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "轰趴馆",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      }
    ]
  },
  {
    "id": "travels",
    "title": "游",
    "leading": "",
    "sort": 0,
    "categories": [
      {
        "id": "",
        "title": "著名景点",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "游乐园",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "公园",
        "leading": "",
        "isHot": true,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "博物馆",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "动物园",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "植物园",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "广场",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "展览馆",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "纪念馆",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "教堂",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "水族馆",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "寺庙",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      }
    ]
  },
  {
    "id": "logistics",
    "title": "生活",
    "leading": "",
    "sort": 0,
    "categories": [
      {
        "id": "",
        "title": "医院",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "银行",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
          {
            "id": "",
            "title": "工商银行",
            "leading": "",
            "isHot": false,
            "sort": 0,
            "moveMode": "unmoveable"
          },
          {
            "id": "",
            "title": "农业银行",
            "leading": "",
            "isHot": false,
            "sort": 0,
            "moveMode": "unmoveable"
          },
          {
            "id": "",
            "title": "建设银行",
            "leading": "",
            "isHot": false,
            "sort": 0,
            "moveMode": "unmoveable"
          },
          {
            "id": "",
            "title": "邮政储蓄",
            "leading": "",
            "isHot": false,
            "sort": 0,
            "moveMode": "unmoveable"
          },
          {
            "id": "",
            "title": "中国银行",
            "leading": "",
            "isHot": false,
            "sort": 0,
            "moveMode": "unmoveable"
          }
        ]
      },
      {
        "id": "",
        "title": "快递",
        "leading": "",
        "isHot": true,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
          {
            "id": "",
            "title": "圆通",
            "leading": "",
            "isHot": false,
            "sort": 0,
            "moveMode": "unmoveable"
          },
          {
            "id": "",
            "title": "申通",
            "leading": "",
            "isHot": false,
            "sort": 0,
            "moveMode": "unmoveable"
          },
          {
            "id": "",
            "title": "顺丰",
            "leading": "",
            "isHot": false,
            "sort": 0,
            "moveMode": "unmoveable"
          },
          {
            "id": "",
            "title": "韵达",
            "leading": "",
            "isHot": false,
            "sort": 0,
            "moveMode": "unmoveable"
          },
          {
            "id": "",
            "title": "中通",
            "leading": "",
            "isHot": false,
            "sort": 0,
            "moveMode": "unmoveable"
          }
        ]
      },
      {
        "id": "",
        "title": "诊所",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "美容美发",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "电讯营业厅",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
          {
            "id": "",
            "title": "移动营业厅",
            "leading": "",
            "isHot": false,
            "sort": 0,
            "moveMode": "unmoveable",
            "brands": [
            ]
          },
          {
            "id": "",
            "title": "联通营业厅",
            "leading": "",
            "isHot": false,
            "sort": 0,
            "moveMode": "unmoveable",
            "brands": [
            ]
          },
          {
            "id": "",
            "title": "电信营业厅",
            "leading": "",
            "isHot": false,
            "sort": 0,
            "moveMode": "unmoveable",
            "brands": [
            ]
          }
        ]
      },
      {
        "id": "",
        "title": "厕所",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "ATM",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "照相馆",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "理发店",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "学校",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "宠物",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "邮局",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "幼儿园",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "旅行社",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "健身房",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "派出所",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "图书馆",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "早教中心",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "婴儿游泳",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      }
    ]
  },
  {
    "id": "shopings",
    "title": "购",
    "leading": "",
    "sort": 0,
    "categories": [
      {
        "id": "",
        "title": "超市",
        "leading": "",
        "isHot": true,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "商场",
        "leading": "",
        "isHot": true,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "药店",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "步行街",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "便利店",
        "leading": "",
        "isHot": true,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "建材市场",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "五金店",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "书店",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "花店",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "服装城",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "批发市场",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "花卉市场",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "菜市场",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "水果批发市场",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "母婴",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "家具城",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "花鸟市场",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "体育用品",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "文具店",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      },
      {
        "id": "",
        "title": "眼镜店",
        "leading": "",
        "isHot": false,
        "sort": 0,
        "moveMode": "unmoveable",
        "brands": [
        ]
      }
    ]
  }
]
```