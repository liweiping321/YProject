package com.road.fire.cfg.provider;

import java.util.HashSet;
import java.util.Set;

import com.game.cfg.BaseCfgEntiyProvider;
import com.game.consts.GameConsts;
import com.game.utils.CollectionUtil;
import com.road.fire.entity.cfg.NameCfg;

/**
 * @author 工具生成
 * @date 2017-04-25 03:05:37
 */
public class NameCfgProvider extends BaseCfgEntiyProvider<Integer, NameCfg> {
	// 姓氏列表
	private Set<String> xingshiList = new HashSet<String>();
	// 男名列表
	private Set<String> maleNameList = new HashSet<String>();
	// 女名列表
	private Set<String> femaleNameList = new HashSet<String>();
	
 
	@Override
	public void afterReload(NameCfg value) {
		if (value.getType() == GameConsts.NameTypeFamily) {
			xingshiList.add(value.getValue());
		} else if (value.getType() == GameConsts.NameTypeMale) {
			maleNameList.add(value.getValue());
		} else if (value.getType() == GameConsts.NameTypeFemale) {
			femaleNameList.add(value.getValue());
		}
	}

	public String createRoleName(int sex) {
		String xingshi = CollectionUtil.randomGetFromCollection(xingshiList);
		String name = "";
		if (sex == GameConsts.SexFemale) {
			name = CollectionUtil.randomGetFromCollection(femaleNameList);
		} else {
			name = CollectionUtil.randomGetFromCollection(maleNameList);
		}
		String roleName = xingshi + name;

		return roleName;
	}

	public Class<?> getCfgClass() {

		return NameCfg.class;
	}

	private static final NameCfgProvider instance = new NameCfgProvider();

	public static NameCfgProvider getInstance() {
		return instance;
	}

}
