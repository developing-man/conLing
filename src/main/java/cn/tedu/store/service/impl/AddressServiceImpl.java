package cn.tedu.store.service.impl;

import cn.tedu.store.entity.Address;
import cn.tedu.store.mapper.AddressMapper;
import cn.tedu.store.service.IAddressService;
import cn.tedu.store.service.IDistrictService;
import cn.tedu.store.service.ex.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements IAddressService {

    @Resource
    private AddressMapper addressMapper;
    @Resource
    private IDistrictService districtService;
    @Value("${project.address.max-count}")
    private Integer addressMaxCount;

    @Override
    public void addnew(Integer uid, String username, Address address) {
        // 根据参数uid查询该用户的收货地址数量
        Integer count = countByUid(uid);
        // 判断该用户的收货地址数量是否已经达到上限：count >= 20
        if (count >= addressMaxCount) {
            // 是：AddressCountLimitException
            throw new AddressCountLimitException(
                    "您创建的收货地址数据的数量已经达到上限(" + addressMaxCount + ")");
        }

        // 补全数据：根据参数address中省/市/区的代码获取名称
        String provinceName = getNameByCode(address.getProvinceCode());
        String cityName = getNameByCode(address.getCityCode());
        String areaName = getNameByCode(address.getAreaCode());
        address.setProvinceName(provinceName);
        address.setCityName(cityName);
        address.setAreaName(areaName);

        // 判断查询到的收货地址数量是否为0，得到isDefault
        Integer isDefault = count == 0 ? 1 : 0;
        // 补全数据：uid
        address.setUid(uid);
        // 补全数据：isDefault
        address.setIsDefault(isDefault);

        // 创建当前时间对象
        Date now = new Date();
        
        // 补全数据：4个日志
        address.setCreatedUser(username);
        address.setCreatedTime(now);
        address.setModifiedUser(username);
        address.setModifiedTime(now);

        // 调用持久层对象插入数据
        insert(address);
    }


    @Override
    public List<Address> getByUid(Integer uid) {
        List<Address> list = findByUid(uid);
        for (Address address : list) {
            address.setUid(null);
            address.setProvinceCode(null);
            address.setCityCode(null);
            address.setAreaCode(null);
            address.setCreatedUser(null);
            address.setCreatedTime(null);
            address.setModifiedUser(null);
            address.setModifiedTime(null);
        }
        return list;
    }

    @Override
    @Transactional
    public void setDefault(Integer aid, Integer uid, String username) {
        // 根据aid查询数据
        Address result = findByAid(aid);
        // 判断查询结果是否为null
        if (result == null) {
            // 是：AddressNotFoundException
            throw new AddressNotFoundException(
                    "尝试访问的收货地址数据不存在");
        }

        // 检查归属是否正确
        // 判断查询结果中的uid与当前登录的用户uid(参数uid)是否不同
        if (!result.getUid().equals(uid)) {
            // 是：AccessDeniedException
            throw new AccessDeniedException("非法访问");
        }

        // 将该用户的所有地址设置为非默认
        updateNonDefaultByUid(uid);

        // 将指定地址设置为默认
        updateDefaultByAid(aid, username, new Date());
    }

    @Override
    @Transactional
    public void delete(Integer aid, Integer uid, String username) {
        // 根据aid查询数据
        Address result = findByAid(aid);
        // 判断查询结果是否为null
        if (result == null) {
            // 是：AddressNotFoundException
            throw new AddressNotFoundException(
                    "尝试访问的收货地址数据不存在");
        }

        // 检查归属是否正确
        // 判断查询结果中的uid与当前登录的用户uid(参数uid)是否不同
        if (!result.getUid().equals(uid)) {
            // 是：AccessDeniedException
            throw new AccessDeniedException("非法访问");
        }

        // 根据参数aid执行删除
        deleteByAid(aid);

        // 判断查询结果中的isDefault是否为0
        if (result.getIsDefault() == 0) {
            return;
        }

        // 通过countByUid(uid)统计还有多少收货地址
        Integer count = countByUid(uid);
        // 判断统计结果是否为0
        if (count == 0) {
            return;
        }

        // 通过findLastModifiedByUid(uid)找出最后修改的收货地址
        Address lastModified = findLastModifiedByUid(uid);
        // 取出这条收货地址的aid
        Integer lastModifiedAid = lastModified.getAid();
        // 执行设置默认收货地址
        updateDefaultByAid(lastModifiedAid, username, new Date());
    }

    @Override
    public Address getByAid(Integer aid, Integer uid) {
        Address address = findByAid(aid);
        if (address == null) {
            throw new AddressNotFoundException(
                    "尝试访问的收货地址数据不存在");
        }

        if (!address.getUid().equals(uid)) {
            throw new AccessDeniedException("非法访问");
        }

        address.setCreatedUser(null);
        address.setCreatedTime(null);
        address.setModifiedUser(null);
        address.setModifiedTime(null);
        return address;
    }

    /**
     * 根据省/市/区的代号，获取省/市/区的名称
     *
     * @param code 省/市/区的代号
     * @return 匹配的省/市/区的名称，如果没有匹配的数据，则返回null
     */
    private String getNameByCode(String code) {
        String name = districtService.getNameByCode(code);
        return name == null ? null : name;
    }

    /**
     * 插入收货地址数据
     *
     * @param address 收货地址数据
     */
    private void insert(Address address) {
        // 调用持久层对象插入数据，并获取返回值
        Integer rows = addressMapper.insert(address);
        // 判断受影响的行数是否不为1
        if (rows != 1) {
            // 是：抛出InsertException
            throw new InsertException(
                    "插入收货地址数据时出现未知错误，请联系系统管理员");
        }
    }

    /**
     * 根据收货地址数据的id删除数据
     *
     * @param aid 收货地址数据的id
     */
    private void deleteByAid(Integer aid) {
        // 根据参数aid执行删除，获取返回值
        Integer rows = addressMapper.deleteByAid(aid);
        // 判断返回值是否不为1
        if (rows != 1) {
            // 是：DeleteException
            throw new DeleteException(
                    "删除收货地址数据时出现未知错误，请联系系统管理员");
        }
    }

    /**
     * 将指定的收货地址数据设置为默认
     *
     * @param aid      收货地址数据的id
     * @param username 修改执行人
     * @param date     修改时间
     */
    private void updateDefaultByAid(Integer aid, String username, Date date) {
        // 通过updateDefaultByAid(aid, username, new Date())以上找到的收货地址设置为默认，获取返回值
        Integer rows = addressMapper.updateDefaultByAid(aid, username, date);
        // 判断返回值是否不为1
        if (rows < 1) {
            // 是：UpdateException
            throw new UpdateException(
                    "更新收货地址数据时出现未知错误，请联系系统管理员");
        }
    }

    /**
     * 将某用户的所有收货地址全部设置为非默认
     *
     * @param uid 用户的id
     */
    private void updateNonDefaultByUid(Integer uid) {
        // 将该用户的所有地址设置为非默认，获取返回值
        Integer rows = addressMapper.updateNonDefaultByUid(uid);
        // 判断返回的受影响行数是否小于1
        if (rows < 1) {
            // 是：UpdateException
            throw new UpdateException(
                    "更新收货地址数据时出现未知错误，请联系系统管理员[1]");
        }
    }

    /**
     * 统计某用户的收货地址数据的数量
     *
     * @param uid 用户的id
     * @return 该用户的收货地址数据的数量
     */
    private Integer countByUid(Integer uid) {
        return addressMapper.countByUid(uid);
    }

    /**
     * 根据收货地址数据的id，查询收货地址详情
     *
     * @param aid 收货地址数据的id
     * @return 匹配的收货地址详情，如果没有匹配的数据，则返回null
     */
    private Address findByAid(Integer aid) {
        return addressMapper.findByAid(aid);
    }

    /**
     * 查询某用户最后修改的收货地址详情
     *
     * @param uid 用户的id
     * @return 匹配的收货地址详情，如果没有匹配的数据，则返回null
     */
    private Address findLastModifiedByUid(Integer uid) {
        return addressMapper.findLastModifiedByUid(uid);
    }

    /**
     * 获取某用户的收货地址列表
     *
     * @param uid 用户的id
     * @return 该用户的收货地址列表
     */
    private List<Address> findByUid(Integer uid) {
        return addressMapper.findByUid(uid);
    }


}