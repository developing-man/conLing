package cn.tedu.store.service;

import cn.tedu.store.entity.District;

import java.util.List;

public interface IDistrictService {
    /**
     * 根据省代号获取省市或市代号获取区代号
     *
     * @param parent
     * @return
     */
    List<District> getByParent(String parent);

    /**
     * 根据代号获取其名字
     *
     * @param code
     * @return
     */
    String getNameByCode(String code);



}
